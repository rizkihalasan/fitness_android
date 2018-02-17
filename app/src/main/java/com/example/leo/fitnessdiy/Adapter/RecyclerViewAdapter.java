package com.example.leo.fitnessdiy.Adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.leo.fitnessdiy.R;
import com.example.leo.fitnessdiy.model.History;

import java.util.List;

/**
 * Created by Leo on 16/02/2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.HistoryViewHolder> {
    private List<History> histories;

    public RecyclerViewAdapter(List<History> h) {
        this.histories = h;
    }
    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card, parent, false);

        HistoryViewHolder hvh = new HistoryViewHolder(view);

        return hvh;
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {
        holder.sportName.setText(histories.get(position).getSport_name());
        holder.sportDate.setText(histories.get(position).getSport_date());
        holder.sportStart.setText(histories.get(position).getSport_start());
        holder.sportEnd.setText(histories.get(position).getSport_end());
    }

    @Override
    public int getItemCount() {
        return histories.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView sportName;
        TextView sportDate;
        TextView sportStart;
        TextView sportEnd;

        public HistoryViewHolder(View itemView) {
            super(itemView);

            sportName = (TextView) itemView.findViewById(R.id.sport_name);
            sportDate = (TextView) itemView.findViewById(R.id.sport_date);
            sportStart = (TextView) itemView.findViewById(R.id.sport_start);
            sportEnd = (TextView) itemView.findViewById(R.id.sport_end);
        }
    }

}
