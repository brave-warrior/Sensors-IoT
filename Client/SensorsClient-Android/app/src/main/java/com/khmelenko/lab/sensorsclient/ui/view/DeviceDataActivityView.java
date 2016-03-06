package com.khmelenko.lab.sensorsclient.ui.view;

import com.khmelenko.lab.sensorsclient.network.response.WeatherData;

import java.util.List;

/**
 * View for Device data activity
 *
 * @author Dmytro Khmelenko (d.khmelenko@gmail.com)
 */
public interface DeviceDataActivityView extends BaseView {

    /**
     * Shows toast error message
     *
     * @param errorMsg Error message
     */
    void showErrorToast(String errorMsg);

    /**
     * Sets current data
     *
     * @param currentData Current data
     */
    void setCurrentData(WeatherData currentData);

    /**
     * Sets history data
     *
     * @param historyData History data
     */
    void setHistoryData(List<WeatherData> historyData);
}
