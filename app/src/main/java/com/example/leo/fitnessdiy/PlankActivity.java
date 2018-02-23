package com.example.leo.fitnessdiy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.leo.fitnessdiy.Utilities.NetworkUtils;
import com.example.leo.fitnessdiy.model.Users;
import com.example.leo.fitnessdiy.model.UsersSharedPreferences;
import com.example.leo.fitnessdiy.routes.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class PlankActivity extends AppCompatActivity {
    private String LOG_TAG = "TES PLANK ACTIVITY";
    String sharedPrefFile = "com.example.leo.fitnessdiy";
    private String user;
    private String plank_date;
    private String plank_time_start;
    private String plank_time_end;
    private String plank_duration;

    private SharedPreferences mPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plank);

        final String BACKGROUND_KEY = "background";

        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        int background = mPreferences.getInt(BACKGROUND_KEY, R.drawable.green_theme);
        getWindow().getDecorView().setBackground(getResources().getDrawable(background));

        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();

        plank_date = formatDate.format(date);
        Log.d(LOG_TAG, plank_date);

        plank_time_start = formatTime.format(date);
        Log.d(LOG_TAG, plank_time_start);
    }

    public int setCountTime(String level){
        if(level.equals("begineer")){
            plank_duration = ""+60;
            return 60000;
        } else if(level.equals("intermediate")){
            plank_duration = ""+120;
            return 120000;
        } else {
            plank_duration = ""+180;
            return 180000;
        }
    }

    public String milisecondToMinutes(long l){
        long minutes = (l/1000)/60;
        long seconds = (l/1000)%60;
        if (seconds >= 10)
            return minutes+" : "+seconds;
        else
            return minutes+" : 0"+seconds;
    }

    public void countDownPlank(View view) {
        final TextView countText = findViewById(R.id.count_timer);
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        String level = mPreferences.getString(UsersSharedPreferences.LEVEL_USERS, "begineer");
        new CountDownTimer(setCountTime(level), 1000){
            @Override
            public void onTick(long l) {
                String waktu = milisecondToMinutes(l);
                countText.setText(waktu);
            }

            @Override
            public void onFinish() {
                countText.setText("BERHASIL");

                SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm:ss");
                plank_time_end = formatTime.format(new Date());
                Log.d(LOG_TAG, plank_time_end);
                String user = Integer.toString(mPreferences.getInt(UsersSharedPreferences.ID_USERS, -999));
                addThePlankHistory(user, plank_date, plank_time_start, plank_time_end, plank_duration);
            }
        }.start();
    }

    public void addThePlankHistory(String user, String plank_date, String plank_time_start,
                                   String plank_time_end, String plank_duration){
        String urlstring = api.ADD_PLANK_HISTORY_URL+"user="+user+"&plank_date="+plank_date+"&plank_time_start="+plank_time_start+
                                                    "&plank_time_end="+plank_time_end+"&plank_duration="+plank_duration;

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        try{
            URL url = new URL(urlstring);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            try{
                InputStream in = urlConnection.getInputStream();
                Scanner scanner = new Scanner(in);
            } finally {
                urlConnection.disconnect();
            }
        } catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void openVideo(View view) {
        String url = (String)view.getTag();

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);

        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}
