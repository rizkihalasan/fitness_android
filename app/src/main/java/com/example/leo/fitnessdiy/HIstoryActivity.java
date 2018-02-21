package com.example.leo.fitnessdiy;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.leo.fitnessdiy.Adapter.RecyclerViewAdapter;
import com.example.leo.fitnessdiy.Utilities.NetworkUtils;
import com.example.leo.fitnessdiy.model.History;
import com.example.leo.fitnessdiy.routes.api;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class HIstoryActivity extends AppCompatActivity {
    public static  final String TAG = HIstoryActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private List<History> histories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);


        String fetchResults = null;
        try {
            fetchResults = NetworkUtils.getResponseFromHttpUrl(new URL(api.HISTORY_URL + "1"));
//            fetchResults = "[{\"id\":\"1\",\"id_user\":\"1\",\"sport_name\":\"jogging\",\"sport_date\":\"2018-02-14\",\"sport_time_start\":\"09:00:00\",\"sport_time_end\":\"11:00:00\",\"created_at\":null,\"updated_at\":null},{\"id\":\"2\",\"id_user\":\"1\",\"sport_name\":\"jogging\",\"sport_date\":\"2018-02-15\",\"sport_time_start\":\"09:00:00\",\"sport_time_end\":\"11:00:00\",\"created_at\":null,\"updated_at\":null},{\"id\":\"3\",\"id_user\":\"1\",\"sport_name\":\"plank\",\"sport_date\":\"2018-02-16\",\"sport_time_start\":\"09:00:00\",\"sport_time_end\":\"09:45:00\",\"created_at\":null,\"updated_at\":null},{\"id\":\"4\",\"id_user\":\"1\",\"sport_name\":\"push-up\",\"sport_date\":\"2018-02-16\",\"sport_time_start\":\"10:00:00\",\"sport_time_end\":\"10:30:00\",\"created_at\":null,\"updated_at\":null}]";
            initializeData(fetchResults);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_history);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        RecyclerViewAdapter rv = new RecyclerViewAdapter(histories);
        mRecyclerView.setAdapter(rv);
    }

    public void initializeData(String data) {
        this.histories = new ArrayList<>();
        try {
            JSONArray parser = new JSONArray(data);
            for (int i = 0; i < parser.length(); i++) {
                JSONObject json = parser.getJSONObject(i);
                histories.add(new History(json.getString("sport_name"),
                        json.getString("sport_date"),
                        json.getString("sport_time_start"),
                        json.getString("sport_time_end")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
