package com.khmelenko.lab.sensorsclient.ui.presenter;

import com.khmelenko.lab.sensorsclient.network.RestClient;
import com.khmelenko.lab.sensorsclient.network.response.WeatherData;
import com.khmelenko.lab.sensorsclient.ui.view.DeviceDataActivityView;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Presenter for device data activity
 *
 * @author Dmytro Khmelenko (d.khmelenko@gmail.com)
 */
public final class DeviceDataActivityPresenter extends BasePresenter<DeviceDataActivityView> {

    private final RestClient mRestClient;

    private Subscription mCurrentDataSubscription;
    private Subscription mHistoryDataSubscription;
    private Subscription mCurrentDataAndHistorySubscription;

    @Inject
    public DeviceDataActivityPresenter(RestClient restClient) {
        mRestClient = restClient;
    }

    @Override
    public void onAttach() {
        // do nothing
    }

    @Override
    public void onDetach() {
        if (mCurrentDataAndHistorySubscription != null && !mCurrentDataAndHistorySubscription.isUnsubscribed()) {
            mCurrentDataSubscription.unsubscribe();
        }

        if (mCurrentDataSubscription != null && !mCurrentDataSubscription.isUnsubscribed()) {
            mCurrentDataSubscription.unsubscribe();
        }

        if (mHistoryDataSubscription != null && !mHistoryDataSubscription.isUnsubscribed()) {
            mHistoryDataSubscription.unsubscribe();
        }
    }

    /**
     * Starts loading history data
     *
     * @param deviceName Device name
     */
    public void loadHistory(String deviceName) {
        Subscriber<List<WeatherData>> subscriber = historyDataSubscriber();
        mHistoryDataSubscription = getHistoryData(deviceName)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * Starts loading current data
     *
     * @param deviceName Device name
     */
    public void loadCurrentData(final String deviceName) {
        loadCurrentData(deviceName, Schedulers.computation());
    }

    /**
     * Starts loading current data
     *
     * @param deviceName Device name
     * @param scheduler  Scheduler
     */
    public void loadCurrentData(final String deviceName, Scheduler scheduler) {
        final long initialDelay = 5; // 5 seconds
        final long period = 10; // 10 seconds

        mCurrentDataSubscription = Observable.interval(initialDelay, period, TimeUnit.SECONDS, scheduler)
                .flatMap(new Func1<Long, Observable<WeatherData>>() {
                    @Override
                    public Observable<WeatherData> call(Long aLong) {
                        return getCurrentData(deviceName);
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .retry()
                .subscribe(currentDataSubscriber());
    }

    /**
     * Loads current data and history
     *
     * @param deviceName Device name
     */
    public void loadCurrentDataAndHistory(final String deviceName) {
        Observable<WeatherData> currentData = getCurrentData(deviceName);
        Observable<List<WeatherData>> history = getHistoryData(deviceName);

        mCurrentDataAndHistorySubscription = Observable.zip(currentData, history,
                new Func2<WeatherData, List<WeatherData>,
                        AbstractMap.SimpleImmutableEntry<WeatherData, List<WeatherData>>>() {
                    @Override
                    public AbstractMap.SimpleImmutableEntry<WeatherData, List<WeatherData>> call(WeatherData weatherData, List<WeatherData> history) {
                        return new HashMap.SimpleImmutableEntry<>(weatherData, history);
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(currentDataAndHistorySubscriber());
    }

    /**
     * Gets observable for current data
     *
     * @param deviceName Device name
     * @return Observable
     */
    private Observable<WeatherData> getCurrentData(String deviceName) {
        return mRestClient.getService().getCurrentData(deviceName);
    }

    /**
     * Gets observable for history data
     *
     * @param deviceName Device name
     * @return Observable
     */
    private Observable<List<WeatherData>> getHistoryData(String deviceName) {
        return mRestClient.getService().getHistory(deviceName);
    }

    /**
     * Prepares subscriber for the current data and history
     *
     * @return Subscriber
     */
    private Subscriber<AbstractMap.SimpleImmutableEntry<WeatherData, List<WeatherData>>> currentDataAndHistorySubscriber() {
        return new Subscriber<AbstractMap.SimpleImmutableEntry<WeatherData, List<WeatherData>>>() {

            @Override
            public void onCompleted() {
                // do nothing
            }

            @Override
            public void onError(Throwable e) {
                getView().showErrorToast(e.getMessage());
                getView().handleFailedLoadingCurrentData();
                getView().handleFailedLoadingHistory();
            }

            @Override
            public void onNext(AbstractMap.SimpleImmutableEntry<WeatherData, List<WeatherData>> weatherDataListMap) {
                WeatherData current = weatherDataListMap.getKey();
                getView().setCurrentData(current);

                List<WeatherData> history = weatherDataListMap.getValue();
                getView().setHistoryData(history);
            }
        };
    }

    /**
     * Prepares subscriber for the current data
     *
     * @return Subscriber
     */
    private Subscriber<WeatherData> currentDataSubscriber() {
        return new Subscriber<WeatherData>() {

            @Override
            public void onCompleted() {
                // do nothing
            }

            @Override
            public void onError(Throwable e) {
                getView().showErrorToast(e.getMessage());
                getView().handleFailedLoadingCurrentData();
            }

            @Override
            public void onNext(WeatherData currentData) {
                getView().setCurrentData(currentData);
            }
        };
    }

    /**
     * Prepares subscriber for the history data
     *
     * @return Subscriber
     */
    private Subscriber<List<WeatherData>> historyDataSubscriber() {
        return new Subscriber<List<WeatherData>>() {
            @Override
            public void onCompleted() {
                // do nothing
            }

            @Override
            public void onError(Throwable e) {
                getView().showErrorToast(e.getMessage());
                getView().handleFailedLoadingHistory();
            }

            @Override
            public void onNext(List<WeatherData> history) {
                getView().setHistoryData(history);
            }
        };
    }

}
