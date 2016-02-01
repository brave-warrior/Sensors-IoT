package com.khmelenko.lab.sensorsclient.di.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Activity scope
 *
 * @author Dmytro Khmelenko (d.khmelenko@gmail.com)
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityScope {
}
