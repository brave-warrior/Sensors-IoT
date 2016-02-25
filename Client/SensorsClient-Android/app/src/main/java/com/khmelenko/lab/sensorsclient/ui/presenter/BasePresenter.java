package com.khmelenko.lab.sensorsclient.ui.presenter;

import com.khmelenko.lab.sensorsclient.ui.view.BaseView;

/**
 * Base presenter interface
 *
 * @author Dmytro Khmelenko (d.khmelenko@gmail.com)
 */
public abstract class BasePresenter<T extends BaseView> {

    private T mView;

    /**
     * Notifies on attach presenter to view
     */
    public abstract void onAttach();

    /**
     * Notifies on detach presenter from view
     */
    public abstract void onDettach();

    /**
     * Sets the view for the presenter
     *
     * @param view View
     */
    public void setView(T view) {
        mView = view;
    }

    /**
     * Gets the view
     *
     * @return View
     */
    public T getView() {
        return mView;
    }
}
