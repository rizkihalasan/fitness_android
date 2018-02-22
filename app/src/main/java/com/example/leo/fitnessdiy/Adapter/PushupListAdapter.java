package com.example.leo.fitnessdiy.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.leo.fitnessdiy.R;
import com.example.leo.fitnessdiy.model.PushUp;

import java.util.List;

/**
 * Created by Leo on 22/02/2018.
 */

public class PushupListAdapter extends BaseAdapter {
    Context context;
    List<PushUp> pushUpList;

    public PushupListAdapter(List<PushUp> p, Context c) {
        this.context = c;
        this.pushUpList = p;
    }
    @Override
    public int getCount() {
        return pushUpList.size();
    }

    @Override
    public Object getItem(int position) {
        return pushUpList.get(position);
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
            convertView = layoutInflater.inflate(R.layout.pushup_card, null);

            v.Date = (TextView) convertView.findViewById(R.id.pushup_date);
            v.Time = (TextView) convertView.findViewById(R.id.pushup_time);
            v.Frequency = (TextView) convertView.findViewById(R.id.push_frequency);

            convertView.setTag(v);
        } else {
            v = (ViewItem) convertView.getTag();
        }

        v.Date.setText(pushUpList.get(position).getDate());
        v.Time.setText(
                String.format("%s - %s", pushUpList.get(position).getStart(),
                        pushUpList.get(position).getEnd())
        );

        v.Frequency.setText(
                String.format("You manage to beat %s push ups", pushUpList.get(position)
                        .getFrequency())
        );

        return convertView;
    }

    class ViewItem {
        TextView Date;
        TextView Time;
        TextView Frequency;
    }
}
