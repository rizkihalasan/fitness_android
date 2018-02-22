package com.example.leo.fitnessdiy;


import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.leo.fitnessdiy.Utilities.NetworkUtils;
import com.example.leo.fitnessdiy.model.History;
import com.example.leo.fitnessdiy.model.Jogging;
import com.example.leo.fitnessdiy.model.PushUp;
import com.example.leo.fitnessdiy.model.SitUp;
import com.example.leo.fitnessdiy.routes.api;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class HIstoryActivity extends AppCompatActivity {
    public static  final String TAG = HIstoryActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private List<Jogging> joggingHistory;
    private List<PushUp> pushupHistory;
    private List<SitUp> situpHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

//        mRecyclerView = (RecyclerView) findViewById(R.id.rv_history);
//
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//
//        mRecyclerView.setLayoutManager(layoutManager);
//        mRecyclerView.setHasFixedSize(true);
//        RecyclerViewAdapter rv = new RecyclerViewAdapter(histories);
//        mRecyclerView.setAdapter(rv);
    }

    public void getJoggingData() {
        try {
            String response = NetworkUtils.getResponseFromHttpUrl(
                    new URL(api.JOGGING_HISTORY_URL + "1")
            );
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                joggingHistory.add(new Jogging(
                        jsonObject.getString("jogging_date"),
                        jsonObject.getString("jogging_time_start"),
                        jsonObject.getString("jogging_time_end"),
                        (float) jsonObject.getDouble("jogging_distance"),
                        jsonObject.getString("starting_point"),
                        jsonObject.getString("end_point")
                ));
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
