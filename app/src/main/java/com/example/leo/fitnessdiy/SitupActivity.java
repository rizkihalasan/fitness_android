package com.example.leo.fitnessdiy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.leo.fitnessdiy.model.UsersSharedPreferences;
import com.example.leo.fitnessdiy.routes.api;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * Created by Heil on 2/19/2018.
 */

public class SitupActivity extends AppCompatActivity{
    private String user;
    private String situp_date;
    private String situp_time_start;
    private String situp_time_end;
    private String situp_frequency;
    private String LOG_TAG = "SITUP ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_situp);

        SharedPreferences mPreferences;
        String sharedPrefFile = "com.example.leo.fitnessdiy";
        final String BACKGROUND_KEY = "background";
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

        String level = mPreferences.getString(UsersSharedPreferences.LEVEL_USERS, "begineer");
        setSitUpFrequency(level);

        int background = mPreferences.getInt(BACKGROUND_KEY, R.drawable.green_theme);
        getWindow().getDecorView().setBackground(getResources().getDrawable(background));

        user = Integer.toString(mPreferences.getInt(UsersSharedPreferences.ID_USERS, -999));
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        situp_date = formatDate.format(new Date());
    }

    public void setSitUpFrequency(String level){
        TextView sitUpTask = (TextView)findViewById(R.id.sitUpTask);
        if(level.equals("begineer")){
            situp_frequency = ""+30;
            sitUpTask.setText("Do Sit Up 30 times");
        }else if(level.equals("intermediate")){
            situp_frequency = ""+60;
            sitUpTask.setText("Do Sit Up 60 times");
        }else if(level.equals("advanced")){
            situp_frequency = ""+90;
            sitUpTask.setText("Do Sit Up 90 times");
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

    public void taskComplete(View view) {
        TextView tv = (TextView)findViewById(R.id.sitUpTask);
        tv.setText("Berhasil");

        SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm:ss");
        situp_time_end = formatTime.format(new Date());
        Log.d(LOG_TAG, "waktu selesai situp : "+situp_time_end);
        addSitupHistory(user, situp_date, situp_time_start, situp_time_end, situp_frequency);
    }

    public void taskBegin(View view) {
        TextView tv = (TextView)findViewById(R.id.sitUpTask);
        Button button1 = (Button) findViewById(R.id.start_situp_button);
        button1.setVisibility(View.GONE);

        Button button2 = (Button) findViewById(R.id.finish_situp_button);
        button2.setVisibility(View.VISIBLE);

        SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm:ss");
        situp_time_start = formatTime.format(new Date());
        Log.d(LOG_TAG, "waktu mulai situp : "+situp_time_start);
    }

    public void addSitupHistory(String user, String situp_date, String situp_time_start,
                                String situp_time_end, String situp_frequency){
        String urlstring = api.ADD_SITUP_HISTORY_URL+"user="+user+"&situp_date"+situp_date+"&situp_time_start"+situp_time_start+
                "&situp_time_end="+situp_time_end+"&situp_frequency="+situp_frequency;

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
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
