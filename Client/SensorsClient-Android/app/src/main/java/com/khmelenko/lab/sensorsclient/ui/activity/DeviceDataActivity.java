package com.khmelenko.lab.sensorsclient.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.khmelenko.lab.sensorsclient.R;
import com.khmelenko.lab.sensorsclient.SensorsApp;
import com.khmelenko.lab.sensorsclient.network.response.WeatherData;
import com.khmelenko.lab.sensorsclient.ui.fragment.DeviceCurrentDataFragment;
import com.khmelenko.lab.sensorsclient.ui.fragment.DeviceHistoryDataFragment;
import com.khmelenko.lab.sensorsclient.ui.presenter.DeviceDataActivityPresenter;
import com.khmelenko.lab.sensorsclient.ui.view.DeviceDataActivityView;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Device data activity
 *
 * @author Dmytro Khmelenko (d.khmelenko@gmail.com)
 */
public final class DeviceDataActivity extends BaseActivity<DeviceDataActivityPresenter>
        implements DeviceDataActivityView, DeviceHistoryDataFragment.DeviceHistoryDataListener, DeviceCurrentDataFragment.DeviceCurrentDataListener {

    public static final String DEVICE_NAME_KEY = "DeviceNameKey";

    @Inject
    DeviceDataActivityPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_data);
        SensorsApp.instance().getPresenterComponent().inject(this);
        ButterKnife.bind(this);

        initToolbar();

        // setting view pager
        ViewPager vpPager = (ViewPager) findViewById(R.id.device_data_view_pager);
        PagerAdapter adapterViewPager = new PagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        vpPager.setOffscreenPageLimit(PagerAdapter.ITEMS_COUNT);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.device_data_tabs);
        tabLayout.setupWithViewPager(vpPager);
    }

    @Override
    protected DeviceDataActivityPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected void attachPresenter() {
        getPresenter().attach(this);

        getPresenter().loadCurrentData();
        getPresenter().loadHistory();
    }

    /**
     * Initializes toolbar
     */
    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }

    @Override
    public void loadHistory() {
        // TODO
        mPresenter.loadHistory();
    }

    @Override
    public void showErrorToast(String errorMsg) {
        // TODO
    }

    @Override
    public void setCurrentData(WeatherData currentData) {
        // TODO
    }

    @Override
    public void setHistoryData(List<WeatherData> historyData) {
        // TODO
    }

    /**
     * Custom adapter for view pager
     */
    private class PagerAdapter extends FragmentPagerAdapter {
        private static final int INDEX_CURRENT = 0;
        private static final int INDEX_HISTORY = 1;
        private static final int ITEMS_COUNT = 2;

        public PagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return ITEMS_COUNT;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case INDEX_CURRENT:
                    return DeviceCurrentDataFragment.newInstance();
                case INDEX_HISTORY:
                    return DeviceHistoryDataFragment.newInstance();
                default:
                    return null;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case INDEX_CURRENT:
                    return getString(R.string.device_data_tab_current);
                case INDEX_HISTORY:
                    return getString(R.string.device_data_tab_history);
                default:
                    return null;
            }
        }

    }
}
