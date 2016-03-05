package com.khmelenko.lab.sensorsclient.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.khmelenko.lab.sensorsclient.R;
import com.khmelenko.lab.sensorsclient.network.response.WeatherData;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Adapter for the list of history
 *
 * @author Dmytro Khmelenko (d.khmelenko@gmail.com)
 */
public class DeviceHistoryListAdapter extends RecyclerView.Adapter<DeviceHistoryListAdapter.HistoryItemViewHolder> {

    private final List<WeatherData> mHistoryList;

    public DeviceHistoryListAdapter(List<WeatherData> historyList) {
        mHistoryList = historyList;
    }

    @Override
    public HistoryItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        return new HistoryItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(HistoryItemViewHolder holder, int position) {
        WeatherData data = mHistoryList.get(position);

        // TODO Do nice formatting
        holder.mTimestamp.setText(data.getDate());
        holder.mTemperature.setText(data.getTemperature());
        holder.mHumidity.setText(data.getHumidity());
    }

    @Override
    public int getItemCount() {
        return mHistoryList.size();
    }

    class HistoryItemViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.item_history_timestamp)
        TextView mTimestamp;

        @Bind(R.id.item_history_temperature)
        TextView mTemperature;

        @Bind(R.id.item_history_humidity)
        TextView mHumidity;

        public HistoryItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
