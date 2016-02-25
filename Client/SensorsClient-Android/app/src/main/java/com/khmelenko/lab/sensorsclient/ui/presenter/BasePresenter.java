package com.khmelenko.lab.sensorsclient.ui.presenter;

import android.support.annotation.NonNull;

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
    public abstract void onDetach();

    /**
     * Attaches presenter
     */
    public void attach(@NonNull T view) {
        setView(view);
        onAttach();
    }

    /**
     * Detaches presenter
     */
    public void detach() {
        onDetach();
        mView = null;
    }

    /**
     * Sets the view for the presenter
     *
     * @param view View
     */
    public void setView(@NonNull T view) {
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
