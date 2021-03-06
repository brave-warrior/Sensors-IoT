package com.khmelenko.lab.sensorsclient.di.component;

import android.app.Application;
import android.content.Context;

import com.khmelenko.lab.sensorsclient.di.module.ApplicationModule;
import com.khmelenko.lab.sensorsclient.di.module.NetworkModule;
import com.khmelenko.lab.sensorsclient.network.RestClient;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Base component
 *
 * @author Dmytro Khmelenko (d.khmelenko@gmail.com)
 */
@Singleton
@Component(modules = {ApplicationModule.class, NetworkModule.class})
public interface BaseComponent {

    Context context();

    RestClient restClient();
}