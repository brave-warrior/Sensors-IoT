package com.khmelenko.lab.sensorsclient.ui.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.khmelenko.lab.sensorsclient.R;
import com.khmelenko.lab.sensorsclient.network.response.Device;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * List adapter for sensors
 *
 * @author Dmytro Khmelenko (d.khmelenko@gmail.com)
 */
public final class SensorsListAdapter extends RecyclerView.Adapter<SensorsListAdapter.SensorViewHolder> {

    private final List<Device> mSensors;
    private final OnListItemListener mListener;

    public SensorsListAdapter(List<Device> sensors, OnListItemListener listener) {
        mSensors = sensors;
        mListener = listener;
    }

    @Override
    public SensorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_device, parent, false);
        return new SensorViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SensorViewHolder holder, int position) {
        Device device = mSensors.get(position);
        holder.mName.setText(device.getName());
    }

    @Override
    public int getItemCount() {
        return mSensors.size();
    }

    /**
     * View holder
     */
    class SensorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.item_sensor_name)
        TextView mName;

        public SensorViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            CardView cardView = (CardView) itemView.findViewById(R.id.item_sensor_card_view);
            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemSelected(getLayoutPosition());
            }
        }
    }

}
