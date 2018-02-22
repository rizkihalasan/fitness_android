package com.example.leo.fitnessdiy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.CountDownTimer;
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
import java.net.URL;
import java.util.ArrayList;

public class PlankActivity extends AppCompatActivity {
    private String LOG_TAG = "TES PLANK ACTIVITY";
    String sharedPrefFile = "com.example.leo.fitnessdiy";
    private SharedPreferences mPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plank);

        SharedPreferences mPreferences;

        final String BACKGROUND_KEY = "background";

        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        int background = mPreferences.getInt(BACKGROUND_KEY, R.drawable.green_theme);
        getWindow().getDecorView().setBackground(getResources().getDrawable(background));
    }

    public int setCountTime(String level){
        if(level.equals("begineer")){
            return 60000;
        } else if(level.equals("intermediate")){
            return 120000;
        } else {
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
            }
        }.start();
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
