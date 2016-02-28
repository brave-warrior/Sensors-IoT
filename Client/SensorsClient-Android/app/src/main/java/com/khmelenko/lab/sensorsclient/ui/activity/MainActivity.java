package com.khmelenko.lab.sensorsclient.ui.activity;

import android.os.Bundle;

import com.khmelenko.lab.sensorsclient.R;
import com.khmelenko.lab.sensorsclient.SensorsApp;
import com.khmelenko.lab.sensorsclient.network.response.Device;
import com.khmelenko.lab.sensorsclient.ui.presenter.MainActivityPresenterImpl;
import com.khmelenko.lab.sensorsclient.ui.view.MainActivityView;

import java.util.List;

import javax.inject.Inject;

/**
 * Main activity
 *
 * @author Dmytro Khmelenko (d.khmelenko@gmail.com)
 */
public class MainActivity extends BaseActivity<MainActivityPresenterImpl> implements MainActivityView {

    @Inject
    MainActivityPresenterImpl mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SensorsApp.instance().getPresenterComponent().inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().attach(this);
    }

    @Override
    protected MainActivityPresenterImpl getPresenter() {
        return mPresenter;
    }

    @Override
    public void setDevices(List<Device> devices) {
        // TODO
    }

    @Override
    public void showErrorToast(String errorMsg) {
        // TODO
    }
}
