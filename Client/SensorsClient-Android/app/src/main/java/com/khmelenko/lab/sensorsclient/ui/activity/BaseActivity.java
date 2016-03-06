package com.khmelenko.lab.sensorsclient.ui.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;

import com.khmelenko.lab.sensorsclient.R;
import com.khmelenko.lab.sensorsclient.ui.presenter.BasePresenter;

/**
 * Base activity
 *
 * @author Dmytro Khmelenko (d.khmelenko@gmail.com)
 */
public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity {

    private ProgressDialog mProgressDialog;

    @Override
    protected void onResume() {
        super.onResume();
        attachPresenter();
    }

    @Override
    protected void onPause() {
        super.onPause();
        showLoadingProgress(false);
        getPresenter().detach();
    }

    /**
     * Shows the progress of the loading
     *
     * @param isLoading True, if loading is in progress. False otherwise
     */
    protected void showLoadingProgress(boolean isLoading) {
        if (isLoading) {
            mProgressDialog = ProgressDialog.show(this, "", getString(R.string.loading_msg));
        } else {
            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }
        }
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
