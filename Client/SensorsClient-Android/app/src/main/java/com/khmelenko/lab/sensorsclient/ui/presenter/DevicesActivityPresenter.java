package com.khmelenko.lab.sensorsclient.ui.presenter;

import com.khmelenko.lab.sensorsclient.network.RestClient;
import com.khmelenko.lab.sensorsclient.network.response.Device;
import com.khmelenko.lab.sensorsclient.ui.view.DevicesActivityView;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Implementation of the main activity presenter
 *
 * @author Dmytro Khmelenko (d.khmelenko@gmail.com)
 */
public final class DevicesActivityPresenter extends BasePresenter<DevicesActivityView> {

    private final RestClient mRestClient;

    private Subscription mLoadDevicesSubscription;

    @Inject
    public DevicesActivityPresenter(RestClient restClient) {
        mRestClient = restClient;
    }

    @Override
    public void onAttach() {
        // do nothing
    }

    @Override
    public void onDetach() {
        if(mLoadDevicesSubscription != null && mLoadDevicesSubscription.isUnsubscribed()) {
            mLoadDevicesSubscription.unsubscribe();
        }
    }

    /**
     * Starts loading list of devices
     */
    public void loadDevices() {
        Subscriber<List<Device>> subscriber = prepareSubscriber();
        mLoadDevicesSubscription = getObservable()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * Gets observable for the task
     *
     * @return Observable
     */
    private Observable<List<Device>> getObservable() {
        return mRestClient.getService().getDevices();
    }

    /**
     * Prepares subscriber for the list of devices
     *
     * @return Subscriber
     */
    private Subscriber<List<Device>> prepareSubscriber() {
        return new Subscriber<List<Device>>() {

            @Override
            public void onCompleted() {
                // do nothing
            }

            @Override
            public void onError(Throwable e) {
                getView().showErrorToast(e.getMessage());
            }

            @Override
            public void onNext(List<Device> devices) {
                getView().setDevices(devices);
            }
        };
    }


}
