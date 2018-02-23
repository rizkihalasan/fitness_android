package com.example.leo.fitnessdiy;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.leo.fitnessdiy.Adapter.JoggingListAdapter;
import com.example.leo.fitnessdiy.Adapter.PlankListAdapter;
import com.example.leo.fitnessdiy.Adapter.PushupListAdapter;
import com.example.leo.fitnessdiy.Adapter.SitupListAdapter;
import com.example.leo.fitnessdiy.Utilities.NetworkUtils;
import com.example.leo.fitnessdiy.model.Jogging;
import com.example.leo.fitnessdiy.model.Plank;
import com.example.leo.fitnessdiy.model.PushUp;
import com.example.leo.fitnessdiy.model.SitUp;
import com.example.leo.fitnessdiy.model.UsersSharedPreferences;
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
    private ListView mHistoryView;
    private List<Jogging> joggingHistory = new ArrayList<>();
    private CardView jogging;
    private CardView plank;
    private CardView pushup;
    private CardView situp;
    private List<PushUp> pushupHistory = new ArrayList<>();
    private List<Plank> plankHistory = new ArrayList<>();
    private List<SitUp> situpHistory = new ArrayList<>();
    public static final String TAG = HIstoryActivity.class.getSimpleName();
    private int iduser;
    private SharedPreferences mPreferences;
    private String sharedPrefFile = "com.example.leo.fitnessdiy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        mHistoryView = (ListView) findViewById(R.id.history);

        final PlankListAdapter plankListAdapter = new PlankListAdapter(plankHistory, this);

        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

        iduser = mPreferences.getInt(UsersSharedPreferences.ID_USERS, 0);

        jogging = (CardView) findViewById(R.id.jogging);
        jogging.setVisibility(View.GONE);
        jogging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JoggingListAdapter joggingListAdapter = new JoggingListAdapter(joggingHistory,
                        getApplicationContext());
                mHistoryView.setAdapter(joggingListAdapter);
            }
        });

        plank = (CardView) findViewById(R.id.plank);
        plank.setVisibility(View.GONE);
        plank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlankListAdapter plankListAdapter1 = new PlankListAdapter(plankHistory,
                        getApplicationContext());
                mHistoryView.setAdapter(plankListAdapter);
            }
        });

        pushup = (CardView) findViewById(R.id.pushup);
        pushup.setVisibility(View.GONE);
        pushup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PushupListAdapter pushupListAdapter = new PushupListAdapter(pushupHistory,
                        getApplicationContext());
                mHistoryView.setAdapter(pushupListAdapter);
            }
        });

        situp = (CardView) findViewById(R.id.situp);
        situp.setVisibility(View.GONE);
        situp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SitupListAdapter situpListAdapter = new SitupListAdapter(situpHistory,
                        getApplicationContext());
                mHistoryView.setAdapter(situpListAdapter);
            }
        });

        new GetData(this).execute();
    }

    public void getJoggingData() {
        try {
            String response = NetworkUtils.getResponseFromHttpUrl(
                    new URL(api.JOGGING_HISTORY_URL + iduser)
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
                    new URL(api.PLANK_HISTORY_URL + iduser)
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

    public void getPushupData() {
        try {
            String response = NetworkUtils.getResponseFromHttpUrl(
                    new URL(api.PUSHUP_HISTORY_URL + iduser)
            );
            Log.d(TAG, response);
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                pushupHistory.add(new PushUp(
                        jsonObject.getString("pushup_date"),
                        jsonObject.getString("pushup_time_start"),
                        jsonObject.getString("pushup_time_end"),
                        jsonObject.getInt("pushup_frequency")
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

    public void getSitupData() {
        try {
            String response = NetworkUtils.getResponseFromHttpUrl(
                    new URL(api.SITUP_HISTORY_URL + iduser)
            );
            Log.d(TAG, response);
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                situpHistory.add(new SitUp(
                        jsonObject.getString("situp_date"),
                        jsonObject.getString("situp_time_start"),
                        jsonObject.getString("situp_time_end"),
                        jsonObject.getInt("situp_frequency")
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

    private class GetData extends AsyncTask<Void, Void, Void>{
        private Context context;

        public GetData(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            getJoggingData();
            getPlankData();
            getPushupData();
            getSitupData();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            ProgressBar pg = (ProgressBar) findViewById(R.id.proCollage);
            pg.setVisibility(View.GONE);
            jogging.setVisibility(View.VISIBLE);
            plank.setVisibility(View.VISIBLE);
            pushup.setVisibility(View.VISIBLE);
            situp.setVisibility(View.VISIBLE);
        }
    }
}
