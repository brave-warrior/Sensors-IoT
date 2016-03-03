package com.khmelenko.lab.sensorsclient.ui.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.khmelenko.lab.sensorsclient.R;
import com.khmelenko.lab.sensorsclient.network.response.Device;
import com.khmelenko.lab.sensorsclient.ui.adapter.OnListItemListener;
import com.khmelenko.lab.sensorsclient.ui.adapter.SensorsListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Fragment for showing available sensors
 *
 * @author Dmytro Khmelenko
 */
public final class SensorsFragment extends Fragment {

    private SensorsFragmentListener mListener;

    @Bind(R.id.empty_text)
    TextView mEmptyView;

    @Bind(R.id.main_sensors_swipe_view)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Bind(R.id.main_sensors_recycler_view)
    RecyclerView mSensorsRecyclerView;

    private SensorsListAdapter mSensorsListAdapter;
    private List<Device> mDevices = new ArrayList<>();

    private ProgressDialog mProgressDialog;

    /**
     * Creates new instance of the fragment
     *
     * @return Fragment instance
     */
    public static SensorsFragment newInstance() {
        return new SensorsFragment();
    }

    public SensorsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sensors, container, false);
        ButterKnife.bind(this, view);

        mSensorsRecyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mSensorsRecyclerView.setLayoutManager(layoutManager);

        mSensorsListAdapter = new SensorsListAdapter(mDevices, new OnListItemListener() {
            @Override
            public void onItemSelected(int position) {
                if (mListener != null) {
                    mListener.onDeviceSelected(mDevices.get(position));
                }
            }
        });
        mSensorsRecyclerView.setAdapter(mSensorsListAdapter);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.swipe_refresh_progress);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mListener != null) {
                    mListener.onRefreshData();
                }
            }
        });

        return view;
    }

    /**
     * Checks whether data existing or not
     */
    private void checkIfEmpty() {
        mEmptyView.setText(R.string.sensors_empty_text);
        if (mDevices.isEmpty()) {
            mEmptyView.setVisibility(View.VISIBLE);
        } else {
            mEmptyView.setVisibility(View.GONE);
        }
    }

    /**
     * Clears the fragment data
     */
    public void clearData() {
        mDevices.clear();
        mSensorsListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (SensorsFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement SensorsFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * Sets the list of devices
     *
     * @param devices Devices
     */
    public void setDevices(List<Device> devices) {
        mDevices.clear();
        mDevices.addAll(devices);
        mSensorsListAdapter.notifyDataSetChanged();

        checkIfEmpty();
    }

    /**
     * Sets the progress of the loading
     *
     * @param isLoading True, if loading is in progress. False otherwise
     */
    public void setLoadingProgress(boolean isLoading) {
        if (isLoading) {
            mProgressDialog = ProgressDialog.show(getActivity(), "", getString(R.string.loading_msg));
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }
        }
    }

    /**
     * Handles the case when loading data failed
     */
    public void handleLoadingFailed(String message) {
        checkIfEmpty();

        String msg = getString(R.string.error_failed_loading_sensors, message);
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * Fragment listener
     */
    public interface SensorsFragmentListener {

        /**
         * Handles device selection
         *
         * @param device Selected device
         */
        void onDeviceSelected(Device device);

        /**
         * Handles request for refreshing data
         */
        void onRefreshData();
    }

}
