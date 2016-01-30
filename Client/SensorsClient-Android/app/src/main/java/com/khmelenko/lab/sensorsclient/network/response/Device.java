package com.khmelenko.lab.sensorsclient.network.response;

import com.google.gson.annotations.SerializedName;

/**
 * Device data
 *
 * @author Dmytro Khmelenko (d.khmelenko@gmail.com)
 */
public final class Device {

    @SerializedName("name")
    private String mName;

    @SerializedName("weatherData")
    private WeatherData mWeatherData;

    public Device() {

    }

    public String getName() {
        return mName;
    }

    public WeatherData getWeatherData() {
        return mWeatherData;
    }

}
