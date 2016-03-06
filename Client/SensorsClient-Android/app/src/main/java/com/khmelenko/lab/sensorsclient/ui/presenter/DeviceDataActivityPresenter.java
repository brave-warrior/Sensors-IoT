package com.khmelenko.lab.sensorsclient.ui.presenter;

import com.khmelenko.lab.sensorsclient.network.RestClient;
import com.khmelenko.lab.sensorsclient.network.response.WeatherData;
import com.khmelenko.lab.sensorsclient.ui.view.DeviceDataActivityView;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
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
        // do nothing
    }

    /**
     * Starts loading history data
     */
    public void loadHistory() {
        Subscriber<List<WeatherData>> subscriber = historyDataSubscriber();
        mHistoryDataSubscription = getHistoryData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * Starts loading current data
     */
    public void loadCurrentData() {
        Subscriber<WeatherData> subscriber = currentDataSubscriber();
        mCurrentDataSubscription = getCurrentData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * Gets observable for current data
     *
     * @return Observable
     */
    private Observable<WeatherData> getCurrentData() {
        return mRestClient.getService().getCurrentData();
    }

    /**
     * Gets observable for history data
     *
     * @return Observable
     */
    private Observable<List<WeatherData>> getHistoryData() {
        return mRestClient.getService().getHistory();
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
                // TODO Notify view on successful completion
            }

            @Override
            public void onError(Throwable e) {
                // TODO improve error handling
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
                // TODO
            }

            @Override
            public void onError(Throwable e) {
                // TODO
                getView().showErrorToast(e.getMessage());
            }

            @Override
            public void onNext(List<WeatherData> history) {
                // TODO
                getView().setHistoryData(history);
            }
        };
    }

}
