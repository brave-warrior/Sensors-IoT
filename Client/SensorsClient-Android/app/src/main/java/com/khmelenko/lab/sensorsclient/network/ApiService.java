package com.khmelenko.lab.sensorsclient.network;

import com.khmelenko.lab.sensorsclient.network.response.Sensor;

import retrofit.http.GET;
import rx.Observable;

/**
 * API Service
 *
 * @author Dmytro Khmelenko
 */
public interface ApiService {

    @GET("/sensors")
    Observable<Sensor> getSensors();

}
