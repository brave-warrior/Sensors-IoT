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
        attachPresenter();
    }

    @Override
    protected void onPause() {
        super.onPause();
        getPresenter().detach();
    }

    /**
     * Gets attached presenter
     *
     * @return Presenter
     */
    protected abstract T getPresenter();

    /**
     * Does presenter attachment
     */
    protected abstract void attachPresenter();

}
