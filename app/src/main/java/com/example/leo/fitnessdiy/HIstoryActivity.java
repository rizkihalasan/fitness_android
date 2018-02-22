package com.example.leo.fitnessdiy;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.leo.fitnessdiy.Adapter.JogginHistoryAdapter;
import com.example.leo.fitnessdiy.Adapter.PlankHistoryAdapter;
import com.example.leo.fitnessdiy.Utilities.NetworkUtils;
import com.example.leo.fitnessdiy.model.History;
import com.example.leo.fitnessdiy.model.Jogging;
import com.example.leo.fitnessdiy.model.Plank;
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
    private RecyclerView mRecyclerView;
    private RecyclerView plankRecyclerView;
    private List<Jogging> joggingHistory = new ArrayList<>();
    private CardView jogging;
    private CardView plank;
    private List<PushUp> pushupHistory;
    private List<Plank> plankHistory = new ArrayList<>();
    private List<SitUp> situpHistory;
    public static final String TAG = HIstoryActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        mRecyclerView = (RecyclerView) findViewById(R.id.jogging_history);
        mRecyclerView.setVisibility(View.GONE);

        plankRecyclerView = (RecyclerView) findViewById(R.id.plank_history);
        plankRecyclerView.setVisibility(View.GONE);

        getJoggingData();
        getPlankData();

        jogging = (CardView) findViewById(R.id.jogging);
        jogging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Jogging Title Clicked");
                if (mRecyclerView.getVisibility() == View.GONE) {
                    Log.d(TAG, "Jogging View Gone");
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

//        Set Jogging Adapter
                    mRecyclerView.setLayoutManager(layoutManager);
                    mRecyclerView.setHasFixedSize(true);
                    JogginHistoryAdapter adapter = new JogginHistoryAdapter(joggingHistory);
                    mRecyclerView.setAdapter(adapter);
                    mRecyclerView.setVisibility(View.VISIBLE);
                } else {
                    Log.d(TAG, "Jogging View Visible");
                    mRecyclerView.setVisibility(View.GONE);
                    mRecyclerView.removeAllViewsInLayout();
                }
            }
        });

        plank = (CardView) findViewById(R.id.plank);
        plank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Plank Title Clicked");
                if (plankRecyclerView.getVisibility() == View.GONE) {
                    LinearLayoutManager layoutManager2 = new LinearLayoutManager(getApplicationContext());
                    //Set Plank Adapter
                    plankRecyclerView.setLayoutManager(layoutManager2);
                    plankRecyclerView.setHasFixedSize(true);
                    PlankHistoryAdapter pa = new PlankHistoryAdapter(plankHistory);
                    plankRecyclerView.setAdapter(pa);
                    plankRecyclerView.setVisibility(View.VISIBLE);
                    Log.d(TAG, "Plank View Now Visible");
                } else {
                    plankRecyclerView.setVisibility(View.GONE);
                    plankRecyclerView.removeAllViewsInLayout();
                    Log.d(TAG, "Plank View Now Gone");

                }
            }
        });

    }

    public void getJoggingData() {
        try {
            String response = NetworkUtils.getResponseFromHttpUrl(
                    new URL(api.JOGGING_HISTORY_URL + "1")
            );
            Log.d(TAG, response);
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
                Log.d(TAG, i + jsonObject.getString("jogging_date"));
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getPlankData() {
        try {
            String response = NetworkUtils.getResponseFromHttpUrl(
                    new URL(api.PLANK_HISTORY_URL + "1")
            );
            Log.d(TAG, response);
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                plankHistory.add(new Plank(
                        jsonObject.getString("plank_date"),
                        jsonObject.getString("plank_time_start"),
                        jsonObject.getString("plank_time_end"),
                        jsonObject.getInt("plank_duration")
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
