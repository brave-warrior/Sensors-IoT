package com.khmelenko.lab.sensorsclient.di.module;

import com.khmelenko.lab.sensorsclient.network.RestClient;
import com.khmelenko.lab.sensorsclient.ui.presenter.MainActivityPresenter;
import com.khmelenko.lab.sensorsclient.ui.presenter.MainActivityPresenterImpl;

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
    MainActivityPresenter providesMainActivityPresenter(RestClient restClient) {
        return new MainActivityPresenterImpl(restClient);
    }

}
