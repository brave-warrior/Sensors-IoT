package com.khmelenko.lab.sensorsclient.di.module;

import android.app.Application;

import com.khmelenko.lab.sensorsclient.SensorsApp;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Application module
 *
 * @author Dmytro Khmelenko (d.khmelenko@gmail.com)
 */
@Module
public class ApplicationModule {

    private final SensorsApp mApp;

    public ApplicationModule(SensorsApp app) {
        mApp = app;
    }

    @Provides
    @Singleton
    public Application provideApplication() {
        return mApp;
    }
}
