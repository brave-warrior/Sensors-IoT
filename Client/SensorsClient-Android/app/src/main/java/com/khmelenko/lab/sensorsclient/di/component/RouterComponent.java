package com.khmelenko.lab.sensorsclient.di.component;

import com.khmelenko.lab.sensorsclient.di.module.PresenterModule;
import com.khmelenko.lab.sensorsclient.di.module.RouterModule;
import com.khmelenko.lab.sensorsclient.di.scope.ActivityScope;

import dagger.Component;

/**
 * Component for routing
 *
 * @author Dmytro Khmelenko (d.khmelenko@gmail.com)
 */
@ActivityScope
@Component(dependencies = BaseComponent.class, modules = RouterModule.class)
public interface RouterComponent {


}
