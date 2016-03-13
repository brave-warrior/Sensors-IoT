package com.khmelenko.lab.sensorsclient.ui.fragment;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.khmelenko.lab.sensorsclient.R;
import com.khmelenko.lab.sensorsclient.storage.AppSettings;

/**
 * @author Dmytro Khmelenko (d.khmelenko@gmail.com)
 */
public final class SettingsFragment extends PreferenceFragment {

    private static final String SERVER_URL = "ServerUrl";
    private EditTextPreference mServerUrl;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);

        mServerUrl = (EditTextPreference) findPreference(SERVER_URL);
        mServerUrl.setSummary(AppSettings.getServerUrl());
        mServerUrl.setText(AppSettings.getServerUrl());
        mServerUrl.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                mServerUrl.setSummary(newValue.toString());
                return true;
            }
        });
    }
}
