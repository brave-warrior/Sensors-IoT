package com.khmelenko.lab.sensorsclient.storage;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.khmelenko.lab.sensorsclient.SensorsApp;

/**
 * Application settings
 *
 * @author Dmytro Khmelenko
 */
public final class AppSettings {

    private static final String SERVER_URL = "ServerUrl";

    /**
     * Saves server URL to settings
     *
     * @param serverUrl Server URL
     */
    public static void setServerUrl(String serverUrl) {
        SharedPreferences pref = getPreferences();
        pref.edit().putString(SERVER_URL, serverUrl).apply();
    }

    /**
     * Gets server URL from app settings
     *
     * @return Server URL
     */
    public static String getServerUrl() {
        SharedPreferences pref = getPreferences();
        return pref.getString(SERVER_URL, "");
    }

    /**
     * Gets shared preferences instance
     *
     * @return Shared preferences
     */
    private static SharedPreferences getPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(SensorsApp.getAppContext());
    }
}
