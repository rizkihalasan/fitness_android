package com.example.leo.fitnessdiy.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.leo.fitnessdiy.R;
import com.example.leo.fitnessdiy.model.Plank;

import java.util.List;

/**
 * Created by Leo on 22/02/2018.
 */

public class PlankListAdapter extends BaseAdapter {
    Context context;
    List<Plank> plankList;

    public PlankListAdapter(List<Plank> l, Context c) {
        this.context = c;
        this.plankList = l;
    }

    @Override
    public int getCount() {
        return plankList.size();
    }

    @Override
    public Object getItem(int position) {
        return plankList.get(position);
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
            convertView = layoutInflater.inflate(R.layout.plank_card, null);

            v.Date = (TextView) convertView.findViewById(R.id.plank_date);
            v.Time = (TextView) convertView.findViewById(R.id.plank_time);
            v.Duration = (TextView) convertView.findViewById(R.id.plank_duration);

            convertView.setTag(v);
        } else {
            v = (ViewItem) convertView.getTag();
        }

        v.Date.setText(plankList.get(position).getDate());
        v.Time.setText(
                String.format("%s - %s", plankList.get(position).getStart(),
                        plankList.get(position).getEnd())
        );

        v.Duration.setText(
                String.format("You last for : %s", plankList.get(position).getDuration())
        );

        return convertView;
    }

    class ViewItem {
        TextView Date;
        TextView Time;
        TextView Duration;
    }
}
