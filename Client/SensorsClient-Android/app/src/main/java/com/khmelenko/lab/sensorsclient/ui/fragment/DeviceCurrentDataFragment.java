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
import com.khmelenko.lab.sensorsclient.utils.DateTimeUtils;
import com.khmelenko.lab.sensorsclient.utils.StringUtils;

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

    private WeatherData mLastWeather;

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
        String dateTime = DateTimeUtils.parseAndFormatDateTime(weatherData.getDate());
        mTimestamp.setText(dateTime);

        String temperature = StringUtils.formatDecimalDigits(weatherData.getTemperature(), 2);
        temperature = getString(R.string.device_data_temperature, temperature);
        mTemperature.setText(temperature);

        String humidity = StringUtils.formatDecimalDigits(weatherData.getHumidity(), 2);
        humidity = getString(R.string.device_data_humidity, humidity);
        mHumidity.setText(humidity);

        mLastWeather = weatherData;
    }

    /**
     * Handles the case when loading data failed
     */
    public void handleLoadingFailed() {
        if (mLastWeather == null) {
            mTimestamp.setText(R.string.device_data_current_empty_text);
        }
    }

    /**
     * Interface for communication with the fragment
     */
    public interface DeviceCurrentDataListener {

    }
}
