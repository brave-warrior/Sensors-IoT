package com.khmelenko.lab.sensorsclient.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.khmelenko.lab.sensorsclient.R;
import com.khmelenko.lab.sensorsclient.SensorsApp;
import com.khmelenko.lab.sensorsclient.network.response.WeatherData;
import com.khmelenko.lab.sensorsclient.ui.adapter.SmartFragmentStatePagerAdapter;
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

    private WeatherData mCurrentData;
    private List<WeatherData> mHistoryData;

    @Inject
    DeviceDataActivityPresenter mPresenter;

    private SmartFragmentStatePagerAdapter mAdapterViewPager;
    private String mDeviceName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_data);
        SensorsApp.instance().getPresenterComponent().inject(this);
        ButterKnife.bind(this);

        initToolbar();

        mDeviceName = getIntent().getStringExtra(DEVICE_NAME_KEY);

        // setting view pager
        ViewPager vpPager = (ViewPager) findViewById(R.id.device_data_view_pager);
        mAdapterViewPager = new PagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(mAdapterViewPager);
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

        showLoadingProgress(true);

        getPresenter().loadCurrentDataAndHistory(mDeviceName);
        getPresenter().loadCurrentData(mDeviceName);
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
        mPresenter.loadHistory(mDeviceName);
    }

    @Override
    public void showErrorToast(String errorMsg) {
        showLoadingProgress(false);
        Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setCurrentData(WeatherData currentData) {
        showLoadingProgress(false);

        mCurrentData = currentData;
        mAdapterViewPager.notifyDataSetChanged();
    }

    @Override
    public void setHistoryData(List<WeatherData> historyData) {
        showLoadingProgress(false);

        mHistoryData = historyData;
        mAdapterViewPager.notifyDataSetChanged();
    }

    @Override
    public void handleFailedLoadingCurrentData() {
        DeviceCurrentDataFragment fragment =
                (DeviceCurrentDataFragment) mAdapterViewPager.getRegisteredFragment(PagerAdapter.INDEX_CURRENT);
        fragment.handleLoadingFailed();
    }

    @Override
    public void handleFailedLoadingHistory() {
        DeviceHistoryDataFragment fragment =
                (DeviceHistoryDataFragment) mAdapterViewPager.getRegisteredFragment(PagerAdapter.INDEX_HISTORY);
        fragment.handleLoadingFailed();
    }

    /**
     * Custom adapter for view pager
     */
    private class PagerAdapter extends SmartFragmentStatePagerAdapter {
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

        @Override
        public int getItemPosition(Object object) {
            if (object instanceof DeviceCurrentDataFragment && mCurrentData != null) {
                DeviceCurrentDataFragment fragment = (DeviceCurrentDataFragment) object;
                fragment.setWeatherData(mCurrentData);
            }

            if (object instanceof DeviceHistoryDataFragment && mHistoryData != null) {
                DeviceHistoryDataFragment fragment = (DeviceHistoryDataFragment) object;
                fragment.setHistory(mHistoryData);
            }

            return super.getItemPosition(object);
        }
    }
}
