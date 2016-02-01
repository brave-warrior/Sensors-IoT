package com.khmelenko.lab.sensorsclient.di.module;

import com.khmelenko.lab.sensorsclient.ui.view.MainActivityView;

import dagger.Module;
import dagger.Provides;

/**
 * Main activity module
 *
 * @author Dmytro Khmelenko (d.khmelenko@gmail.com)
 */
@Module
public class MainActivityModule {

    private MainActivityView mView;

    public MainActivityModule(MainActivityView view) {
        mView = view;
    }

    @Provides
    public MainActivityView provideView() {
        return mView;
    }

}
