package com.khmelenko.lab.sensorsclient.ui.view;

import com.khmelenko.lab.sensorsclient.network.response.Device;

import java.util.List;

/**
 * View for the main activity
 *
 * @author Dmytro Khmelenko (d.khmelenko@gmail.com)
 */
public interface DevicesActivityView extends BaseView {

    /**
     * Sets the list of devices
     *
     * @param devices Devices
     */
    void setDevices(List<Device> devices);

    /**
     * Shows a toast with error message
     *
     * @param errorMsg Error message
     */
    void showErrorToast(String errorMsg);

}
