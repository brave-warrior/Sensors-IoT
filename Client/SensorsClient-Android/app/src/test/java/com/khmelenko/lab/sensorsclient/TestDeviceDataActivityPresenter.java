package com.khmelenko.lab.sensorsclient;

import com.khmelenko.lab.sensorsclient.network.ApiService;
import com.khmelenko.lab.sensorsclient.network.RestClient;
import com.khmelenko.lab.sensorsclient.network.response.WeatherData;
import com.khmelenko.lab.sensorsclient.ui.presenter.DeviceDataActivityPresenter;
import com.khmelenko.lab.sensorsclient.ui.view.DeviceDataActivityView;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.plugins.RxJavaPlugins;
import rx.plugins.RxJavaSchedulersHook;
import rx.plugins.RxJavaTestPlugins;
import rx.schedulers.Schedulers;
import rx.schedulers.TestScheduler;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests DeviceDataActivityPresenter
 *
 * @author Dmytro Khmelenko (d.khmelenko@gmail.com)
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*"})
@PrepareForTest({RestClient.class, DeviceDataActivityPresenter.class})
public class TestDeviceDataActivityPresenter {

    @Rule
    public PowerMockRule rule = new PowerMockRule();

    private DeviceDataActivityPresenter mPresenter;
    private RestClient mRestClient;

    @Before
    public void setupMock() {
        RxJavaTestPlugins.resetPlugins();
        RxJavaPlugins.getInstance().registerSchedulersHook(new RxJavaSchedulersHook() {
            @Override
            public Scheduler getIOScheduler() {
                return Schedulers.immediate();
            }

            @Override
            public Scheduler getNewThreadScheduler() {
                return Schedulers.immediate();
            }

            @Override
            public Scheduler getComputationScheduler() {
                return Schedulers.immediate();
            }
        });

        RxAndroidPlugins.getInstance().registerSchedulersHook(new RxAndroidSchedulersHook() {
            @Override
            public Scheduler getMainThreadScheduler() {
                return Schedulers.immediate();
            }
        });

        mRestClient = mock(RestClient.class);

        ApiService apiService = mock(ApiService.class);
        when(mRestClient.getService()).thenReturn(apiService);

        mPresenter = spy(new DeviceDataActivityPresenter(mRestClient));

        DeviceDataActivityView view = mock(DeviceDataActivityView.class);
        when(mPresenter.getView()).thenReturn(view);
    }

    @After
    public void tearDown() {
        RxAndroidPlugins.getInstance().reset();
    }


    @Test
    public void testLoadCurrentData() {
        final String deviceName = "test";

        Observable<WeatherData> weatherData = Observable.just(new WeatherData());
        when(mRestClient.getService().getCurrentData(deviceName)).thenReturn(weatherData);

        TestScheduler testScheduler = new TestScheduler();
        mPresenter.loadCurrentData(deviceName, testScheduler);
        testScheduler.advanceTimeBy(10, TimeUnit.SECONDS);

        verify(mRestClient.getService()).getCurrentData(deviceName);
    }

}
