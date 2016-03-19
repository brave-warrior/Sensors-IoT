package com.khmelenko.lab.sensorsclient;


import com.khmelenko.lab.sensorsclient.network.ApiService;
import com.khmelenko.lab.sensorsclient.network.RestClient;
import com.khmelenko.lab.sensorsclient.network.response.Device;
import com.khmelenko.lab.sensorsclient.ui.presenter.DeviceDataActivityPresenter;
import com.khmelenko.lab.sensorsclient.ui.presenter.DevicesActivityPresenter;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rx.Observable;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Testing TaskManager class
 *
 * @author Dmytro Khmelenko (d.khmelenko@gmail.com)
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
@PowerMockIgnore({ "org.mockito.*", "org.robolectric.*", "android.*" })
@PrepareForTest({RestClient.class, DevicesActivityPresenter.class})
public class TestDevicesActivityPresenter {

    @Rule
    public PowerMockRule rule = new PowerMockRule();

    private DevicesActivityPresenter mPresenter;
    private RestClient mRestClient;

    @Before
    public void setupMock() {
        mRestClient = mock(RestClient.class);

        ApiService apiService = mock(ApiService.class);
        when(mRestClient.getService()).thenReturn(apiService);

        mPresenter = new DevicesActivityPresenter(mRestClient);
    }

    @Test
    public void testLoadDevices() {
        List<Device> devices = new ArrayList<>();
        Observable<List<Device>> devicesObservable = Observable.just(devices);
        when(mRestClient.getService().getDevices()).thenReturn(devicesObservable);

        mPresenter.loadDevices();
        verify(mRestClient.getService()).getDevices();
    }
}
