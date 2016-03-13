package com.khmelenko.lab.sensorsclient.di.module;

import com.khmelenko.lab.sensorsclient.network.RestClient;
import com.khmelenko.lab.sensorsclient.ui.presenter.DeviceDataActivityPresenter;
import com.khmelenko.lab.sensorsclient.ui.presenter.DevicesActivityPresenter;
import com.khmelenko.lab.sensorsclient.ui.presenter.SettingsActivityPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Presenter module
 *
 * @author Dmytro Khmelenko (d.khmelenko@gmail.com)
 */
@Module
public class PresenterModule {

    @Provides
    DevicesActivityPresenter providesMainActivityPresenter(RestClient restClient) {
        return new DevicesActivityPresenter(restClient);
    }

    @Provides
    DeviceDataActivityPresenter providesDeviceDataActivityPresenter(RestClient restClient) {
        return new DeviceDataActivityPresenter(restClient);
    }

    @Provides
    SettingsActivityPresenter providesSettingsActivityPresenter(RestClient restClient) {
        return new SettingsActivityPresenter(restClient);
    }
}
