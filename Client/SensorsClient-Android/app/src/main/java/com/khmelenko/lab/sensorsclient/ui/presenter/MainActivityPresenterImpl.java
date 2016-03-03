package com.khmelenko.lab.sensorsclient.ui.presenter;

import com.khmelenko.lab.sensorsclient.network.RestClient;
import com.khmelenko.lab.sensorsclient.network.response.Device;
import com.khmelenko.lab.sensorsclient.ui.view.MainActivityView;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

/**
 * Implementation of the main activity presenter
 *
 * @author Dmytro Khmelenko (d.khmelenko@gmail.com)
 */
public class MainActivityPresenterImpl extends BasePresenter<MainActivityView> {

    private final RestClient mRestClient;

    private Subscription mLoadDevicesSubsciption;

    @Inject
    public MainActivityPresenterImpl(RestClient restClient) {
        mRestClient = restClient;
    }

    @Override
    public void onAttach() {

    }

    @Override
    public void onDetach() {

    }

    /**
     * Starts loading list of devices
     */
    public void loadDevices() {
        Subscriber<List<Device>> subscriber = prepareSubscriber();
        mLoadDevicesSubsciption = getObservable().subscribe(subscriber);
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
                // TODO Notify view on successful completion
            }

            @Override
            public void onError(Throwable e) {
                // TODO improve error handling
                getView().showErrorToast(e.getMessage());
            }

            @Override
            public void onNext(List<Device> devices) {
                getView().setDevices(devices);
            }
        };
    }


}
