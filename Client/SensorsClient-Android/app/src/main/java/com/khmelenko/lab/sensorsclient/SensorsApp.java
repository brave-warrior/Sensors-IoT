package com.khmelenko.lab.sensorsclient;

import android.app.Application;
import android.content.Context;

import com.khmelenko.lab.sensorsclient.di.component.ApplicationComponent;
import com.khmelenko.lab.sensorsclient.di.component.DaggerApplicationComponent;
import com.khmelenko.lab.sensorsclient.di.module.ApplicationModule;

/**
 * Application instance
 *
 * @author Dmytro Khmelenko
 */
public class SensorsApp extends Application {

    private static Context sContext;

    private ApplicationComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
    }

    /**
     * Gets application context
     *
     * @return Application context
     */
    public static Context getAppContext() {
        return sContext;
    }

    /**
     * Gets application instance
     *
     * @return Application instance
     */
    public static SensorsApp instance() {
        return (SensorsApp) sContext;
    }

    /**
     * Gets application component
     *
     * @return Application component
     */
    public ApplicationComponent getAppComponent() {
        return mAppComponent;
    }

    /**
     * Builds and injects application component
     */
    public void buildAndInjectApp() {
        mAppComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        mAppComponent.inject(this);
    }
}
