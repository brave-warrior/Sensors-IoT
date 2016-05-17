package com.khmelenko.lab.sensorsclient.ui.router;

import android.content.Context;
import android.content.Intent;

import com.khmelenko.lab.sensorsclient.ui.activity.DeviceDataActivity;

import javax.inject.Inject;

/**
 * Router for devices view
 *
 * @author Dmytro Khmelenko (d.khmelenko@gmail.com)
 */
public final class DevicesRouter implements Router {

    private final Context mContext;

    @Inject
    public DevicesRouter(Context context) {
        mContext = context;
    }

    void deviceSelected(String deviceName) {
        Intent intent = new Intent(mContext, DeviceDataActivity.class);
        intent.putExtra(DeviceDataActivity.DEVICE_NAME_KEY, deviceName);
        mContext.startActivity(intent);
    }

}
