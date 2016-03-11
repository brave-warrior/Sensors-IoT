package com.khmelenko.lab.sensorsclient.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.khmelenko.lab.sensorsclient.R;
import com.khmelenko.lab.sensorsclient.network.response.WeatherData;
import com.khmelenko.lab.sensorsclient.ui.adapter.DeviceHistoryListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Device history fragment
 *
 * @author Dmytro Khmelenko (d.khmelenko@gmail.com)
 */
public final class DeviceHistoryDataFragment extends Fragment {

    @Bind(R.id.empty_text)
    TextView mEmptyView;

    @Bind(R.id.device_history_recycler_view)
    RecyclerView mHistoryRecyclerView;

    @Bind(R.id.device_history_swipe_view)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private DeviceHistoryListAdapter mDeviceHistoryListAdapter;
    private List<WeatherData> mHistoryList = new ArrayList<>();

    private DeviceHistoryDataListener mListener;

    public DeviceHistoryDataFragment() {
        // Required empty public constructor
    }

    /**
     * Creates new instance of the fragment
     *
     * @return Fragment instance
     */
    public static DeviceHistoryDataFragment newInstance() {
        DeviceHistoryDataFragment fragment = new DeviceHistoryDataFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_device_history_data, container, false);
        ButterKnife.bind(this, view);

        mHistoryRecyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mHistoryRecyclerView.setLayoutManager(layoutManager);

        mDeviceHistoryListAdapter = new DeviceHistoryListAdapter(getContext(), mHistoryList);
        mHistoryRecyclerView.setAdapter(mDeviceHistoryListAdapter);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.swipe_refresh_progress);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mListener.loadHistory();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DeviceHistoryDataListener) {
            mListener = (DeviceHistoryDataListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement DeviceHistoryDataListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * Sets the history
     *
     * @param history History list
     */
    public void setHistory(List<WeatherData> history) {
        mHistoryList.clear();
        mHistoryList.addAll(history);
        mDeviceHistoryListAdapter.notifyDataSetChanged();

        checkIfEmpty();
        cancelRefreshingProgress();
    }

    /**
     * Handles the case when loading data failed
     */
    public void handleLoadingFailed() {
        checkIfEmpty();
        cancelRefreshingProgress();
    }

    /**
     * Cancels the progress of the loading
     */
    public void cancelRefreshingProgress() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    /**
     * Checks whether data existing or not
     */
    public void checkIfEmpty() {
        mEmptyView.setText(R.string.device_history_empty_text);
        if (mHistoryList.isEmpty()) {
            mEmptyView.setVisibility(View.VISIBLE);
        } else {
            mEmptyView.setVisibility(View.GONE);
        }
    }

    /**
     * Interface for communication with the fragment
     */
    public interface DeviceHistoryDataListener {

        void loadHistory();

    }
}
