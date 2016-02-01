package com.khmelenko.lab.sensorsclient.ui.activity;

import android.os.Bundle;

import com.khmelenko.lab.sensorsclient.R;
import com.khmelenko.lab.sensorsclient.di.HasComponent;
import com.khmelenko.lab.sensorsclient.di.component.ApplicationComponent;
import com.khmelenko.lab.sensorsclient.di.component.DaggerMainActivityComponent;
import com.khmelenko.lab.sensorsclient.di.component.MainActivityComponent;
import com.khmelenko.lab.sensorsclient.di.module.MainActivityModule;
import com.khmelenko.lab.sensorsclient.ui.presenter.MainActivityPresenterImpl;
import com.khmelenko.lab.sensorsclient.ui.view.MainActivityView;

import javax.inject.Inject;

/**
 * Main activity
 *
 * @author Dmytro Khmelenko (d.khmelenko@gmail.com)
 */
public class MainActivity extends BaseActivity implements MainActivityView, HasComponent<MainActivityComponent> {

    @Inject
    MainActivityPresenterImpl mPresenter;

    private MainActivityComponent mMainActivityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void setupComponent(ApplicationComponent appComponent) {
        mMainActivityComponent = DaggerMainActivityComponent.builder()
                .applicationComponent(appComponent)
                .mainActivityModule(new MainActivityModule(this))
                .build();
        mMainActivityComponent.inject(this);
    }


    @Override
    public MainActivityComponent getComponent() {
        return mMainActivityComponent;
    }
}
