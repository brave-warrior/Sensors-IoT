package com.khmelenko.lab.sensorsclient.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.khmelenko.lab.sensorsclient.R;
import com.khmelenko.lab.sensorsclient.network.response.Device;

/**
 * Fragment for showing available sensors
 *
 * @author Dmytro Khmelenko
 */
public class SensorsFragment extends Fragment {
    private SensorsFragmentListener mListener;

    /**
     * Creates new instance of the fragment
     *
     * @return Fragment instance
     */
    public static SensorsFragment newInstance() {
        return new SensorsFragment();
    }

    public SensorsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sensors, container, false);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (SensorsFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement MainFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * Fragment listener
     */
    public interface SensorsFragmentListener {

        /**
         * Handles sensor selection
         *
         * @param device Selected sensor
         */
        void onSensorSelected(Device device);

        /**
         * Handles request for refreshing data
         */
        void onRefreshData();
    }
}
