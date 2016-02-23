package com.khmelenko.lab.sensorsclient;

import android.app.Application;
import android.content.Context;

import com.khmelenko.lab.sensorsclient.di.component.BaseComponent;
import com.khmelenko.lab.sensorsclient.di.component.DaggerBaseComponent;
import com.khmelenko.lab.sensorsclient.di.component.DaggerPresenterComponent;
import com.khmelenko.lab.sensorsclient.di.component.PresenterComponent;
import com.khmelenko.lab.sensorsclient.di.module.PresenterModule;

/**
 * Application instance
 *
 * @author Dmytro Khmelenko
 */
public class SensorsApp extends Application {

    private static Context sContext;

    private BaseComponent mBaseComponent;
    private PresenterComponent mPresenterComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();

        buildInjections();
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
    public BaseComponent getBaseComponent() {
        return mBaseComponent;
    }

    /**
     * Gets presenter component
     *
     * @return Presenter component
     */
    public PresenterComponent getPresenterComponent() {
        return mPresenterComponent;
    }

    /**
     * Builds injection components
     */
    private void buildInjections() {
        mBaseComponent = DaggerBaseComponent.create();

        mPresenterComponent = DaggerPresenterComponent.builder()
                .presenterModule(new PresenterModule())
                .baseComponent(mBaseComponent)
                .build();
    }
}
