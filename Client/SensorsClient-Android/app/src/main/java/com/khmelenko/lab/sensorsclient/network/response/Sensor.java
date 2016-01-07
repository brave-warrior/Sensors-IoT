package com.khmelenko.lab.sensorsclient.network.response;

import com.google.gson.annotations.SerializedName;

/**
 * Sensor data
 *
 * @author Dmytro Khmelenko (d.khmelenko@gmail.com)
 */
public class Sensor {

    @SerializedName("clientId")
    private String mClientId;

    public Sensor() {

    }

    public Sensor(String clientId) {
        mClientId = clientId;
    }

    public String getClientId() {
        return mClientId;
    }
}
