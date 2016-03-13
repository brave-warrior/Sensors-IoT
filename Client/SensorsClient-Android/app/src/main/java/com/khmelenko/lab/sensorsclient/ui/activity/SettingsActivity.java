package com.khmelenko.lab.sensorsclient.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.khmelenko.lab.sensorsclient.R;
import com.khmelenko.lab.sensorsclient.SensorsApp;
import com.khmelenko.lab.sensorsclient.ui.fragment.SettingsFragment;
import com.khmelenko.lab.sensorsclient.ui.presenter.SettingsActivityPresenter;
import com.khmelenko.lab.sensorsclient.ui.view.SettingsActivityView;

import javax.inject.Inject;

/**
 * Settings activity
 *
 * @author Dmytro Khmelenko (d.khmelenko@gmail.com)
 */
public final class SettingsActivity extends BaseActivity<SettingsActivityPresenter>
        implements SettingsActivityView {

    @Inject
    SettingsActivityPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        SensorsApp.instance().getPresenterComponent().inject(this);

        initToolbar();

        getFragmentManager().beginTransaction().replace(R.id.settings_content, new SettingsFragment()).commit();
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
    protected SettingsActivityPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected void attachPresenter() {
        getPresenter().attach(this);
    }
}
