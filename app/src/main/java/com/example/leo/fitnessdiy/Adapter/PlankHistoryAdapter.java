package com.example.leo.fitnessdiy.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.leo.fitnessdiy.R;
import com.example.leo.fitnessdiy.model.Jogging;
import com.example.leo.fitnessdiy.model.Plank;

import java.util.List;

/**
 * Created by Leo on 22/02/2018.
 */

public class PlankHistoryAdapter extends RecyclerView.Adapter<PlankHistoryAdapter.PlankHistoryViewHolder>{
    private List<Plank> planks;

    public PlankHistoryAdapter(List<Plank> p) {
        this.planks = p;
    }

    @Override
    public PlankHistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.plank_card, parent, false);

        return new PlankHistoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PlankHistoryViewHolder holder, int position) {
        holder.Date.setText(planks.get(position).getDate());
        holder.Time.setText(
                String.format("%s - %s", planks.get(position).getStart(),
                        planks.get(position).getEnd())
        );
        holder.Duration.setText(String.format("You last for : %s detik",
                planks.get(position).getDuration()));
    }

    @Override
    public int getItemCount() {
        return planks.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class PlankHistoryViewHolder extends RecyclerView.ViewHolder{
        TextView Date;
        TextView Time;
        TextView Duration;

        public PlankHistoryViewHolder(View itemView) {
            super(itemView);

            Date = (TextView) itemView.findViewById(R.id.plank_date);
            Time = (TextView) itemView.findViewById(R.id.plank_time);
            Duration = (TextView) itemView.findViewById(R.id.plank_duration);
        }
    }
}
