package com.example.leo.fitnessdiy.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.leo.fitnessdiy.R;
import com.example.leo.fitnessdiy.model.Jogging;

import java.util.List;

/**
 * Created by Leo on 22/02/2018.
 */

public class JogginHistoryAdapter extends RecyclerView.Adapter<JogginHistoryAdapter.JoggingHistoryViewHolder> {
    private List<Jogging> joggings;

    public JogginHistoryAdapter(List<Jogging> j) {
        this.joggings = j;
    }

    @Override
    public JoggingHistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.jogging_card, parent, false);

        return new JoggingHistoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(JogginHistoryAdapter.JoggingHistoryViewHolder holder, int position) {
        holder.Date.setText(joggings.get(position).getDate());
        holder.Time.setText(
                String.format("%s - %s", joggings.get(position).getStart(),
                        joggings.get(position).getEnd())
        );
        holder.Distance.setText(String.format("Your Jogging Distance : %s m",
                joggings.get(position).getDistance()));

        holder.Track.setText(
                String.format("You have been running from %s to %s",
                        joggings.get(position).getStartingPoint(),
                        joggings.get(position).getEndPoint())
        );
    }

    @Override
    public int getItemCount() {
        return joggings.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class JoggingHistoryViewHolder extends RecyclerView.ViewHolder{
        TextView Date;
        TextView Time;
        TextView Distance;
        TextView Track;

        public JoggingHistoryViewHolder(View itemView) {
            super(itemView);

            Date = (TextView) itemView.findViewById(R.id.jogging_date);
            Time = (TextView) itemView.findViewById(R.id.jogging_time);
            Distance = (TextView) itemView.findViewById(R.id.jogging_distance);
            Track = (TextView) itemView.findViewById(R.id.jogging_track);
        }
    }
}
