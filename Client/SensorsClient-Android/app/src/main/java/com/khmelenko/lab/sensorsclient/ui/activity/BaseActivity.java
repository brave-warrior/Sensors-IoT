package com.khmelenko.lab.sensorsclient.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.khmelenko.lab.sensorsclient.SensorsApp;
import com.khmelenko.lab.sensorsclient.di.component.ApplicationComponent;

/**
 * Base activity
 *
 * @author Dmytro Khmelenko (d.khmelenko@gmail.com)
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupComponent(SensorsApp.instance().getAppComponent());
    }

    protected abstract void setupComponent(ApplicationComponent appComponent);
}
