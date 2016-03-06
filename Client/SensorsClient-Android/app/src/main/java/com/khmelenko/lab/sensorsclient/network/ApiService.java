package com.khmelenko.lab.sensorsclient.network;

import com.khmelenko.lab.sensorsclient.network.response.Device;
import com.khmelenko.lab.sensorsclient.network.response.WeatherData;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * API Service
 *
 * @author Dmytro Khmelenko
 */
public interface ApiService {

    @GET("/devices")
    Observable<List<Device>> getDevices();

    @GET("/devices/{name}/current")
    Observable<WeatherData> getCurrentData(@Path("name") String deviceName);

    @GET("/devices/{name}/history")
    Observable<List<WeatherData>> getHistory(@Path("name") String deviceName);

}
