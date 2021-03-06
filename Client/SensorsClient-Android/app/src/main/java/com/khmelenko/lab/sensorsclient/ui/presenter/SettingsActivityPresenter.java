package com.khmelenko.lab.sensorsclient.ui.presenter;

import com.khmelenko.lab.sensorsclient.network.RestClient;
import com.khmelenko.lab.sensorsclient.ui.view.SettingsActivityView;

import javax.inject.Inject;

/**
 * Presenter for settings activity
 *
 * @author Dmytro Khmelenko (d.khmelenko@gmail.com)
 */
public final class SettingsActivityPresenter extends BasePresenter<SettingsActivityView> {

    private final RestClient mRestClient;

    @Inject
    public SettingsActivityPresenter(RestClient restClient) {
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
     * Updates server URL
     *
     * @param newServerUrl New server URL
     */
    public void updateServerUrl(String newServerUrl) {
        mRestClient.updateEndpoint(newServerUrl);
    }
}
