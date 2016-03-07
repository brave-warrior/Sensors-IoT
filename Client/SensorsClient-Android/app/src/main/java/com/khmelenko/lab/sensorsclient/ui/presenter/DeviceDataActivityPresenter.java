package com.khmelenko.lab.sensorsclient.ui.presenter;

import com.khmelenko.lab.sensorsclient.network.RestClient;
import com.khmelenko.lab.sensorsclient.network.response.WeatherData;
import com.khmelenko.lab.sensorsclient.ui.view.DeviceDataActivityView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
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
        mCurrentDataSubscription = Observable.interval(0, 10, TimeUnit.SECONDS)
                .flatMap(new Func1<Long, Observable<WeatherData>>() {
                    @Override
                    public Observable<WeatherData> call(Long aLong) {
                        return getCurrentData(deviceName);
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(currentDataSubscriber());
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
            }

            @Override
            public void onNext(List<WeatherData> history) {
                getView().setHistoryData(history);
            }
        };
    }

}
