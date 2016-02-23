package com.khmelenko.lab.sensorsclient.di.module;

import com.khmelenko.lab.sensorsclient.network.RestClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Network module
 *
 * @author Dmytro Khmelenko (d.khmelenko@gmail.com)
 */
@Module
public class NetworkModule {

    @Provides
    @Singleton
    RestClient providesRestClient() {
        return RestClient.getInstance();
    }

}
