package com.khmelenko.lab.sensorsclient.di.component;

import com.khmelenko.lab.sensorsclient.di.module.MainActivityModule;
import com.khmelenko.lab.sensorsclient.di.scope.ActivityScope;
import com.khmelenko.lab.sensorsclient.ui.activity.MainActivity;

import dagger.Component;

/**
 * Main activity component
 *
 * @author Dmytro Khmelenko (d.khmelenko@gmail.com)
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = MainActivityModule.class)
public interface MainActivityComponent {
    void inject(MainActivity activity);
}
