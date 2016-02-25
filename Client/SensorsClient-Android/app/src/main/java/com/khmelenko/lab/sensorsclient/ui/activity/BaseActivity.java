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
    protected void onPause() {
        super.onPause();
        getPresenter().detach();
    }

    protected abstract T getPresenter();

}
