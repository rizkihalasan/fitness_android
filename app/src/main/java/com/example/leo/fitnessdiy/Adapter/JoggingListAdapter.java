package com.example.leo.fitnessdiy.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.leo.fitnessdiy.R;
import com.example.leo.fitnessdiy.model.Jogging;

import java.util.List;

/**
 * Created by Leo on 22/02/2018.
 */

public class JoggingListAdapter extends BaseAdapter {
    Context context;
    List<Jogging> joggingList;

    public JoggingListAdapter(List<Jogging> l, Context c) {
        this.context = c;
        this.joggingList = l;
    }

    @Override
    public int getCount() {
        return joggingList.size();
    }

    @Override
    public Object getItem(int position) {
        return joggingList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewItem v = null;
        if (convertView == null) {
            v = new ViewItem();
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.jogging_card, null);

            v.Date = (TextView) convertView.findViewById(R.id.jogging_date);
            v.Time = (TextView) convertView.findViewById(R.id.jogging_time);
            v.Distance = (TextView) convertView.findViewById(R.id.jogging_distance);
            v.Track = (TextView) convertView.findViewById(R.id.jogging_track);

            convertView.setTag(v);
        } else {
            v = (ViewItem) convertView.getTag();
        }

        v.Date.setText(joggingList.get(position).getDate());
        v.Time.setText(
                String.format("%s - %s", joggingList.get(position).getStart(),
                        joggingList.get(position).getEnd())
        );
        v.Distance.setText(String.format("Your Jogging Distance : %s m",
                joggingList.get(position).getDistance()));

        v.Track.setText(
                String.format("You have been running from %s to %s",
                        joggingList.get(position).getStartingPoint(),
                        joggingList.get(position).getEndPoint())
        );

        return convertView;
    }

    class ViewItem {
        TextView Date;
        TextView Time;
        TextView Distance;
        TextView Track;
    }
}
