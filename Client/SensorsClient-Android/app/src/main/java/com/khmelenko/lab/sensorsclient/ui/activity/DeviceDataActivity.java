package com.khmelenko.lab.sensorsclient.ui.activity;

import android.os.Bundle;

import com.khmelenko.lab.sensorsclient.R;
import com.khmelenko.lab.sensorsclient.SensorsApp;
import com.khmelenko.lab.sensorsclient.ui.presenter.DeviceDataActivityPresenter;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Device data activity
 *
 * @author Dmytro Khmelenko (d.khmelenko@gmail.com)
 */
public final class DeviceDataActivity extends BaseActivity<DeviceDataActivityPresenter> {

    @Inject
    DeviceDataActivityPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_data);
        SensorsApp.instance().getPresenterComponent().inject(this);
        ButterKnife.bind(this);
    }

    @Override
    protected DeviceDataActivityPresenter getPresenter() {
        return mPresenter;
    }
}
