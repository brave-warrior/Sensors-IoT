package com.khmelenko.lab.sensorsclient.storage;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.khmelenko.lab.sensorsclient.SensorsApp;

/**
 * Application settings
 *
 * @author Dmytro Khmelenko
 */
public class AppPreferences {

    private static final String AUTH = "AuthHeader";

    /**
     * Saves authentication header to settings
     *
     * @param authorizationHeader Auth header
     */
    public static void saveAuthorizationHeader(String authorizationHeader) {
        SharedPreferences pref = getPreferences();
        pref.edit().putString(AUTH, authorizationHeader).commit();
    }

    /**
     * Gets auth header from app settings
     *
     * @return Auth header
     */
    public static String getAuthorizationHeader() {
        SharedPreferences pref = getPreferences();
        return pref.getString(AUTH, "");
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
