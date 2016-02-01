package com.khmelenko.lab.sensorsclient.ui.presenter;

import com.khmelenko.lab.sensorsclient.ui.view.MainActivityView;

import javax.inject.Inject;

/**
 * Implementation of the main activity presenter
 *
 * @author Dmytro Khmelenko (d.khmelenko@gmail.com)
 */
public class MainActivityPresenterImpl implements MainActivityPresenter {

    private MainActivityView mView;

    @Inject
    public MainActivityPresenterImpl(MainActivityView view) {
        mView = view;
    }
}
