package com.khmelenko.lab.sensorsclient.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.khmelenko.lab.sensorsclient.R;
import com.khmelenko.lab.sensorsclient.SensorsApp;
import com.khmelenko.lab.sensorsclient.network.response.Device;
import com.khmelenko.lab.sensorsclient.ui.fragment.SensorsFragment;
import com.khmelenko.lab.sensorsclient.ui.presenter.MainActivityPresenterImpl;
import com.khmelenko.lab.sensorsclient.ui.view.MainActivityView;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Main activity
 *
 * @author Dmytro Khmelenko (d.khmelenko@gmail.com)
 */
public class MainActivity extends BaseActivity<MainActivityPresenterImpl>
        implements MainActivityView, SensorsFragment.SensorsFragmentListener {

    private SensorsFragment mFragment;

    @Inject
    MainActivityPresenterImpl mPresenter;

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
    protected void onResume() {
        super.onResume();
        getPresenter().attach(this);

        mFragment.setLoadingProgress(true);
        getPresenter().loadDevices();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mFragment.setLoadingProgress(false);
    }

    @Override
    protected MainActivityPresenterImpl getPresenter() {
        return mPresenter;
    }

    @Override
    public void setDevices(List<Device> devices) {
        mFragment.setDevices(devices);
        mFragment.setLoadingProgress(false);
    }

    @Override
    public void showErrorToast(String errorMsg) {
        mFragment.setLoadingProgress(false);
        // TODO
    }

    /**
     * Initializes toolbar
     */
    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onDeviceSelected(Device device) {
        // TODO Handle device selection
    }

    @Override
    public void onRefreshData() {
        // TODO handle request Refresh
    }
}
