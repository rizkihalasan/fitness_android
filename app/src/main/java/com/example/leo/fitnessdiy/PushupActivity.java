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

public class PushupActivity extends AppCompatActivity{
    private String user;
    private String pushup_date;
    private String pushup_time_start;
    private String pushup_time_end;
    private String pushup_frequency;
    private String LOG_TAG = "PUSHUP ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pushup);

        SharedPreferences mPreferences;
        String sharedPrefFile = "com.example.leo.fitnessdiy";
        final String BACKGROUND_KEY = "background";
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

        String level = mPreferences.getString(UsersSharedPreferences.LEVEL_USERS, "begineer");
        setPushUpFrequency(level);

        int background = mPreferences.getInt(BACKGROUND_KEY, R.drawable.green_theme);
        getWindow().getDecorView().setBackground(getResources().getDrawable(background));

        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        pushup_date = formatDate.format(new Date());
    }

    public void setPushUpFrequency(String level){
        TextView pushUpTask = (TextView)findViewById(R.id.pushUpTask);
        if(level.equals("begineer")){
            pushUpTask.setText("Do Push Up 30 times");
        }else if(level.equals("intermediate")){
            pushUpTask.setText("Do Push Up 60 times");
        }else if(level.equals("advanced")){
            pushUpTask.setText("Do Push Up 90 times");
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
        TextView tv = (TextView)findViewById(R.id.pushUpTask);
        tv.setText("Berhasil");

        SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm:ss");
        pushup_time_end = formatTime.format(new Date());
        addPlankHistory(user, pushup_date, pushup_time_start, pushup_time_end, pushup_frequency);
        Log.d(LOG_TAG, "waktu selesai pushup : "+pushup_time_end);
    }

    public void taskBegin(View view) {
        TextView tv = (TextView)findViewById(R.id.pushUpTask);
        Button button1 = (Button) findViewById(R.id.start_pushup_button);
        button1.setVisibility(View.GONE);

        Button button2 = (Button) findViewById(R.id.finish_pushup_button);
        button2.setVisibility(View.VISIBLE);

        SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm:ss");
        pushup_time_start = formatTime.format(new Date());
        Log.d(LOG_TAG, "waktu mulai pushup : "+pushup_time_start);
    }

    public void addPlankHistory(String user, String pushup_date, String pushup_time_start,
                                String pushup_time_end, String pushup_frequency){

        String urlstring = api.ADD_PUSHUP_HISTORY_URL+"user="+user+"&pushup_date="+pushup_date+"&pushup_time_start="+pushup_time_start+
                "&pushup_time_end="+pushup_time_end+"&pushup_frequency="+pushup_frequency;

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
}
