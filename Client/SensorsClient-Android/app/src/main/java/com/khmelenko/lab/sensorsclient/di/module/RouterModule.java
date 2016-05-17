package com.khmelenko.lab.sensorsclient.di.module;

import android.content.Context;

import com.khmelenko.lab.sensorsclient.ui.router.DevicesRouter;

import dagger.Module;
import dagger.Provides;

/**
 * Module for routers
 *
 * @author Dmytro Khmelenko (d.khmelenko@gmail.com)
 */
@Module
public class RouterModule {

    @Provides
    DevicesRouter providesMainActivityPresenter(Context context) {
        return new DevicesRouter(context);
    }
}
