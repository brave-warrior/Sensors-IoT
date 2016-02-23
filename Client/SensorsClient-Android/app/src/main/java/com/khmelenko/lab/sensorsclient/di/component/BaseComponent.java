package com.khmelenko.lab.sensorsclient.di.component;

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
@Component(modules = {NetworkModule.class})
public interface BaseComponent {

    RestClient restClient();
}