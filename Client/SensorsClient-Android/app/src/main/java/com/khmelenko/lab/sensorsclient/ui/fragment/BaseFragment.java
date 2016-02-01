package com.khmelenko.lab.sensorsclient.ui.fragment;

import android.support.v4.app.Fragment;

import com.khmelenko.lab.sensorsclient.di.HasComponent;

/**
 * @author Dmytro Khmelenko (d.khmelenko@gmail.com)
 */
public abstract class BaseFragment extends Fragment {
    @SuppressWarnings("unchecked")
    protected <T> T getComponent(Class<T> componentType) {
        return componentType.cast(((HasComponent<T>)getActivity()).getComponent());
    }
}