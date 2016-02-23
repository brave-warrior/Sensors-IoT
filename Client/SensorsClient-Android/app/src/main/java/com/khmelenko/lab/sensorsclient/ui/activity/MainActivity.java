package com.khmelenko.lab.sensorsclient.ui.activity;

import android.os.Bundle;

import com.khmelenko.lab.sensorsclient.R;
import com.khmelenko.lab.sensorsclient.SensorsApp;
import com.khmelenko.lab.sensorsclient.di.component.DaggerPresenterComponent;
import com.khmelenko.lab.sensorsclient.di.component.PresenterComponent;
import com.khmelenko.lab.sensorsclient.di.module.PresenterModule;
import com.khmelenko.lab.sensorsclient.ui.presenter.MainActivityPresenter;
import com.khmelenko.lab.sensorsclient.ui.view.MainActivityView;

import javax.inject.Inject;

/**
 * Main activity
 *
 * @author Dmytro Khmelenko (d.khmelenko@gmail.com)
 */
public class MainActivity extends BaseActivity<MainActivityPresenter> implements MainActivityView {

    @Inject
    MainActivityPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SensorsApp.instance().getPresenterComponent().inject(this);
        mPresenter.registerView(this);
    }

    @Override
    protected MainActivityPresenter getPresenter() {
        return mPresenter;
    }

}
