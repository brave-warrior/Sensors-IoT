package com.khmelenko.lab.sensorsclient.ui.activity;

import android.support.v7.app.AppCompatActivity;

import com.khmelenko.lab.sensorsclient.ui.presenter.BasePresenter;

/**
 * Base activity
 *
 * @author Dmytro Khmelenko (d.khmelenko@gmail.com)
 */
public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity {

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().onAttach();
    }

    @Override
    protected void onPause() {
        super.onPause();
        getPresenter().onDettach();
    }

    protected abstract T getPresenter();

}
