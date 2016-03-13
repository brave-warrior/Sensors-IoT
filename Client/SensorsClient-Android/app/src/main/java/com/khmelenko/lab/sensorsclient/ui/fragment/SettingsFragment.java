package com.khmelenko.lab.sensorsclient.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.khmelenko.lab.sensorsclient.R;

/**
 * @author Dmytro Khmelenko (d.khmelenko@gmail.com)
 */
public final class SettingsFragment extends PreferenceFragment {

    public static final String SERVER_URL_KEY = "ServerUrl";

    private static final String SERVER_URL_SETTING = "ServerUrl";

    private EditTextPreference mServerUrl;

    private SettingsFragmentListener mListener;

    public SettingsFragment() {

    }

    public static SettingsFragment newInstance(String serverUrl) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(SERVER_URL_KEY, serverUrl);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);

        String serverUrl = "";
        if (getArguments() != null) {
            serverUrl = getArguments().getString(SERVER_URL_KEY);
        }

        mServerUrl = (EditTextPreference) findPreference(SERVER_URL_SETTING);
        mServerUrl.setSummary(serverUrl);
        mServerUrl.setText(serverUrl);
        mServerUrl.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                mServerUrl.setSummary(newValue.toString());
                mListener.serverUrlChanged(newValue.toString());
                return true;
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (SettingsFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement SettingsFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * Interface for communication with activity
     */
    public interface SettingsFragmentListener {

        /**
         * Called when server URL changed
         *
         * @param newServerUrl Server URL
         */
        void serverUrlChanged(String newServerUrl);
    }
}
