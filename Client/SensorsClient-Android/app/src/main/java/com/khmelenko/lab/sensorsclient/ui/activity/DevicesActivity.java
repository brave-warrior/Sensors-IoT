package com.khmelenko.lab.sensorsclient.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.khmelenko.lab.sensorsclient.R;
import com.khmelenko.lab.sensorsclient.SensorsApp;
import com.khmelenko.lab.sensorsclient.network.response.Device;
import com.khmelenko.lab.sensorsclient.ui.fragment.SensorsFragment;
import com.khmelenko.lab.sensorsclient.ui.presenter.DevicesActivityPresenter;
import com.khmelenko.lab.sensorsclient.ui.view.DevicesActivityView;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Devices activity
 *
 * @author Dmytro Khmelenko (d.khmelenko@gmail.com)
 */
public final class DevicesActivity extends BaseActivity<DevicesActivityPresenter>
        implements DevicesActivityView, SensorsFragment.SensorsFragmentListener {

    private SensorsFragment mFragment;

    @Inject
    DevicesActivityPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        SensorsApp.instance().getPresenterComponent().inject(this);

        mFragment = (SensorsFragment) getSupportFragmentManager().findFragmentById(R.id.main_fragment);

        initToolbar();
    }

    @Override
    protected void attachPresenter() {
        getPresenter().attach(this);

        showLoadingProgress(true);
        getPresenter().loadDevices();
    }

    @Override
    protected DevicesActivityPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void setDevices(List<Device> devices) {
        mFragment.setDevices(devices);
        showLoadingProgress(false);
    }

    @Override
    public void showErrorToast(String errorMsg) {
        mFragment.handleLoadingFailed();
        showLoadingProgress(false);

        String msg = getString(R.string.error_failed_loading_sensors, errorMsg);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * Initializes toolbar
     */
    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onDeviceSelected(Device device) {
        Intent intent = new Intent(this, DeviceDataActivity.class);
        intent.putExtra(DeviceDataActivity.DEVICE_NAME_KEY, device.getName());
        startActivity(intent);
    }

    @Override
    public void onRefreshData() {
        getPresenter().loadDevices();
    }
}
