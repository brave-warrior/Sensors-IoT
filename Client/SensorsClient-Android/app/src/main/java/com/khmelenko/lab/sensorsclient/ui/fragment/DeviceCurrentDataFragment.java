package com.khmelenko.lab.sensorsclient.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.khmelenko.lab.sensorsclient.R;
import com.khmelenko.lab.sensorsclient.network.response.WeatherData;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Device current data
 *
 * @author Dmytro Khmelenko (d.khmelenko@gmail.com)
 */
public final class DeviceCurrentDataFragment extends Fragment {

    @Bind(R.id.device_current_timestamp)
    TextView mTimestamp;

    @Bind(R.id.device_current_temperature)
    TextView mTemperature;

    @Bind(R.id.device_current_humidity)
    TextView mHumidity;

    private DeviceCurrentDataListener mListener;

    public DeviceCurrentDataFragment() {
        // Required empty public constructor
    }

    /**
     * Creates new instance of the fragment
     *
     * @return Fragment instance
     */
    public static DeviceCurrentDataFragment newInstance() {
        DeviceCurrentDataFragment fragment = new DeviceCurrentDataFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_device_current_data, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DeviceCurrentDataListener) {
            mListener = (DeviceCurrentDataListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement DeviceCurrentDataListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * Sets weather data
     *
     * @param weatherData Weather data
     */
    public void setWeatherData(WeatherData weatherData) {
        // TODO apply formatting
        mTimestamp.setText(weatherData.getDate());
        mTemperature.setText(weatherData.getTemperature());
        mTemperature.setText(weatherData.getHumidity());
    }

    /**
     * Interface for communication with the fragment
     */
    public interface DeviceCurrentDataListener {

    }
}