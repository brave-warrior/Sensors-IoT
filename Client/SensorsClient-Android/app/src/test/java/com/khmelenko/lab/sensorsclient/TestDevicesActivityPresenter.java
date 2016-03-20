package com.khmelenko.lab.sensorsclient;


import com.khmelenko.lab.sensorsclient.network.ApiService;
import com.khmelenko.lab.sensorsclient.network.RestClient;
import com.khmelenko.lab.sensorsclient.network.response.Device;
import com.khmelenko.lab.sensorsclient.ui.presenter.DevicesActivityPresenter;
import com.khmelenko.lab.sensorsclient.ui.view.DevicesActivityView;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.functions.Action1;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Testing DevicesActivityPresenter class
 *
 * @author Dmytro Khmelenko (d.khmelenko@gmail.com)
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*"})
@PrepareForTest({RestClient.class, DevicesActivityPresenter.class})
public class TestDevicesActivityPresenter {

    @Rule
    public PowerMockRule rule = new PowerMockRule();

    private DevicesActivityPresenter mPresenter;
    private RestClient mRestClient;

    @Before
    public void setupMock() {
        RxAndroidPlugins.getInstance().registerSchedulersHook(new RxAndroidSchedulersHook() {
            @Override
            public Scheduler getMainThreadScheduler() {
                return Schedulers.immediate();
            }
        });

        mRestClient = mock(RestClient.class);

        ApiService apiService = mock(ApiService.class);
        when(mRestClient.getService()).thenReturn(apiService);

        mPresenter = spy(new DevicesActivityPresenter(mRestClient));

        DevicesActivityView view = mock(DevicesActivityView.class);
        when(mPresenter.getView()).thenReturn(view);
    }

    @After
    public void tearDown() {
        RxAndroidPlugins.getInstance().reset();
    }

    @Test
    public void testLoadDevicesSuccess() {
        List<Device> devices = new ArrayList<>();
        Observable<List<Device>> devicesObservable = Observable.just(devices);
        when(mRestClient.getService().getDevices()).thenReturn(devicesObservable);

        mPresenter.loadDevices();
        verify(mRestClient.getService()).getDevices();
    }

    @Test
    public void testLoadDevicesFailed() {
        final NullPointerException npe = new NullPointerException();
        Observable<List<Device>> devicesObservable = Observable.create(new Observable.OnSubscribe<List<Device>>() {
            @Override
            public void call(Subscriber<? super List<Device>> subscriber) {
                subscriber.onError(npe);
            }
        });

        when(mRestClient.getService().getDevices()).thenReturn(devicesObservable);
        mPresenter.loadDevices();

        TestSubscriber<List<Device>> subscriber = new TestSubscriber<>();
        devicesObservable.subscribe(subscriber);
        subscriber.assertError(npe);
    }
}
