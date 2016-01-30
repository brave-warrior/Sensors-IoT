package com.khmelenko.lab.sensorsclient.network.response;

import com.google.gson.annotations.SerializedName;

/**
 * Weather data
 *
 * @author Dmytro Khmelenko (d.khmelenko@gmail.com)
 */
public final class WeatherData {

    @SerializedName("temperature")
    private String mTemperature;

    @SerializedName("humidity")
    private String mHumidity;

    @SerializedName("modified")
    private String mDate;

    public String getTemperature() {
        return mTemperature;
    }

    public String getHumidity() {
        return mHumidity;
    }

    public String getDate() {
        return mDate;
    }
}
