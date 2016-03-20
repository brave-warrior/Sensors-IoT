package com.khmelenko.lab.sensorsclient;

import com.khmelenko.lab.sensorsclient.network.ApiService;
import com.khmelenko.lab.sensorsclient.network.RestClient;
import com.khmelenko.lab.sensorsclient.network.response.Device;
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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.observers.TestSubscriber;
import rx.plugins.RxJavaPlugins;
import rx.plugins.RxJavaSchedulersHook;
import rx.plugins.RxJavaTestPlugins;
import rx.plugins.RxJavaTestRunner;
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
        enableTestSchedulers();
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
        resetTestSchedulers();
    }

    /**
     * Enables test schedulers
     */
    private void enableTestSchedulers() {
        RxJavaTestPlugins.resetPlugins();
        RxJavaPlugins.getInstance().registerSchedulersHook(new RxJavaSchedulersHook() {
            @Override
            public Scheduler getNewThreadScheduler() {
                return Schedulers.immediate();
            }
        });
    }

    /**
     * Resets test schedulers
     */
    private void resetTestSchedulers() {
        RxJavaTestPlugins.resetPlugins();
    }

    @Test
    public void testLoadCurrentDataSuccess() {
        final String deviceName = "test";

        Observable<WeatherData> weatherData = Observable.just(new WeatherData());
        when(mRestClient.getService().getCurrentData(deviceName)).thenReturn(weatherData);

        TestScheduler testScheduler = Schedulers.test();
        mPresenter.loadCurrentData(deviceName, testScheduler);
        testScheduler.advanceTimeBy(10, TimeUnit.SECONDS);

        verify(mRestClient.getService()).getCurrentData(deviceName);
    }

    @Test
    public void testLoadCurrentDataFailed() {
        final String deviceName = "test";

        final NullPointerException npe = new NullPointerException();
        Observable<WeatherData> currentDataObservable = Observable.create(new Observable.OnSubscribe<WeatherData>() {
            @Override
            public void call(Subscriber<? super WeatherData> subscriber) {
                subscriber.onError(npe);
            }
        });

        when(mRestClient.getService().getCurrentData(deviceName)).thenReturn(currentDataObservable);

        TestSubscriber<WeatherData> subscriber = new TestSubscriber<>();
        currentDataObservable.subscribe(subscriber);

        TestScheduler testScheduler = Schedulers.test();
        mPresenter.loadCurrentData(deviceName, testScheduler);
        testScheduler.advanceTimeBy(5, TimeUnit.SECONDS);

        subscriber.assertError(npe);
    }

    @Test
    public void testLoadCurrentDataAndHistorySuccess() {
        final String deviceName = "test";

        WeatherData weather = new WeatherData();

        Observable<WeatherData> weatherData = Observable.just(weather);
        when(mRestClient.getService().getCurrentData(deviceName)).thenReturn(weatherData);

        List<WeatherData> historyData = new ArrayList<>();
        historyData.add(weather);
        Observable<List<WeatherData>> history = Observable.just(historyData);
        when(mRestClient.getService().getHistory(deviceName)).thenReturn(history);

        mPresenter.loadCurrentDataAndHistory(deviceName);

        verify(mRestClient.getService()).getCurrentData(deviceName);
        verify(mRestClient.getService()).getHistory(deviceName);
    }

    @Test
    public void testLoadCurrentDataAndHistoryFailed() {
        final String deviceName = "test";

        final NullPointerException npe = new NullPointerException();
        Observable<WeatherData> currentData = Observable.create(new Observable.OnSubscribe<WeatherData>() {
            @Override
            public void call(Subscriber<? super WeatherData> subscriber) {
                subscriber.onError(npe);
            }
        });

        when(mRestClient.getService().getCurrentData(deviceName)).thenReturn(currentData);
        mPresenter.loadCurrentDataAndHistory(deviceName);

        TestSubscriber<WeatherData> subscriber = new TestSubscriber<>();
        currentData.subscribe(subscriber);
        subscriber.assertError(npe);
    }

    @Test
    public void testLoadHistorySuccess() {
        final String deviceName = "test";

        List<WeatherData> historyData = new ArrayList<>();
        Observable<List<WeatherData>> history = Observable.just(historyData);
        when(mRestClient.getService().getHistory(deviceName)).thenReturn(history);

        mPresenter.loadHistory(deviceName);

        verify(mRestClient.getService()).getHistory(deviceName);
    }

    @Test
    public void testLoadHistoryFailed() {
        final String deviceName = "test";

        final NullPointerException npe = new NullPointerException();
        Observable<List<WeatherData>> historyObservable = Observable.create(new Observable.OnSubscribe<List<WeatherData>>() {
            @Override
            public void call(Subscriber<? super List<WeatherData>> subscriber) {
                subscriber.onError(npe);
            }
        });

        TestSubscriber<List<WeatherData>> subscriber = new TestSubscriber<>();
        historyObservable.subscribe(subscriber);

        when(mRestClient.getService().getHistory(deviceName)).thenReturn(historyObservable);
        mPresenter.loadHistory(deviceName);

        subscriber.assertError(npe);
    }
}
