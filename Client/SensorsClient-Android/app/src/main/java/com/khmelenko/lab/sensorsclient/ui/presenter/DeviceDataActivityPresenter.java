package com.khmelenko.lab.sensorsclient.ui.presenter;

import com.khmelenko.lab.sensorsclient.network.RestClient;
import com.khmelenko.lab.sensorsclient.ui.view.DeviceDataActivityView;

import javax.inject.Inject;

/**
 * Presenter for device data activity
 *
 * @author Dmytro Khmelenko (d.khmelenko@gmail.com)
 */
public final class DeviceDataActivityPresenter extends BasePresenter<DeviceDataActivityView> {

    private final RestClient mRestClient;

    @Inject
    public DeviceDataActivityPresenter(RestClient restClient) {
        mRestClient = restClient;
    }

    @Override
    public void onAttach() {

    }

    @Override
    public void onDetach() {

    }
}
