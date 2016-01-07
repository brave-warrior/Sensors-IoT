package com.khmelenko.lab.sensorsclient;

import android.app.Application;
import android.content.Context;

/**
 * Application instance
 *
 * @author Dmytro Khmelenko
 */
public class SensorsApp extends Application {

    private static Context sContext;

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
}
