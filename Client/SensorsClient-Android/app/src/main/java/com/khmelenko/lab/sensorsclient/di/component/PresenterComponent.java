package com.khmelenko.lab.sensorsclient.di.component;

import com.khmelenko.lab.sensorsclient.di.module.PresenterModule;
import com.khmelenko.lab.sensorsclient.di.scope.ActivityScope;
import com.khmelenko.lab.sensorsclient.ui.activity.MainActivity;

import dagger.Component;

/**
 * Presenter component
 *
 * @author Dmytro Khmelenko (d.khmelenko@gmail.com)
 */
@ActivityScope
@Component(dependencies = BaseComponent.class, modules = PresenterModule.class)
public interface PresenterComponent {

    void inject(MainActivity activity);

}
