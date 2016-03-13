package com.khmelenko.lab.sensorsclient.di.component;

import com.khmelenko.lab.sensorsclient.di.module.PresenterModule;
import com.khmelenko.lab.sensorsclient.di.scope.ActivityScope;
import com.khmelenko.lab.sensorsclient.ui.activity.DeviceDataActivity;
import com.khmelenko.lab.sensorsclient.ui.activity.DevicesActivity;
import com.khmelenko.lab.sensorsclient.ui.activity.SettingsActivity;

import dagger.Component;

/**
 * Presenter component
 *
 * @author Dmytro Khmelenko (d.khmelenko@gmail.com)
 */
@ActivityScope
@Component(dependencies = BaseComponent.class, modules = PresenterModule.class)
public interface PresenterComponent {

    void inject(DevicesActivity activity);
    void inject(DeviceDataActivity activity);
    void inject(SettingsActivity activity);

}
