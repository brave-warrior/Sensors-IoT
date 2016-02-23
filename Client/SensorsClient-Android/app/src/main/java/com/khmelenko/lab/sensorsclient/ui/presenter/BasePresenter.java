package com.khmelenko.lab.sensorsclient.ui.presenter;

import com.khmelenko.lab.sensorsclient.ui.view.BaseView;

/**
 * Base presenter interface
 *
 * @author Dmytro Khmelenko (d.khmelenko@gmail.com)
 */
public interface BasePresenter<T extends BaseView> {

    /**
     * Notifies on attach presenter to view
     */
    void onAttach();

    /**
     * Notifies on detach presenter from view
     */
    void onDettach();

    /**
     * Registers view for the presenter
     *
     * @param view View
     */
    void registerView(T view);
}
