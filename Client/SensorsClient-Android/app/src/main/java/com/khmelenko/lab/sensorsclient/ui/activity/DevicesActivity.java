package com.khmelenko.lab.sensorsclient.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.khmelenko.lab.sensorsclient.R;
import com.khmelenko.lab.sensorsclient.SensorsApp;
import com.khmelenko.lab.sensorsclient.network.response.Device;
import com.khmelenko.lab.sensorsclient.ui.fragment.DevicesFragment;
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
        implements DevicesActivityView, DevicesFragment.SensorsFragmentListener {

    private DevicesFragment mFragment;

    @Inject
    DevicesActivityPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devices);
        ButterKnife.bind(this);
        SensorsApp.instance().getPresenterComponent().inject(this);

        mFragment = (DevicesFragment) getSupportFragmentManager().findFragmentById(R.id.sensors_fragment);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.devices_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.devices_menu_action_preferences:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
