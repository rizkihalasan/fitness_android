package com.example.leo.fitnessdiy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;

import com.example.leo.fitnessdiy.model.Users;
import com.example.leo.fitnessdiy.model.UsersSharedPreferences;
import com.example.leo.fitnessdiy.routes.api;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class LevelActivity extends AppCompatActivity {
    private SharedPreferences mPreferences;
    String sharedPrefFile = "com.example.leo.fitnessdiy";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);


        final String BACKGROUND_KEY = "background";
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

        int background = mPreferences.getInt(BACKGROUND_KEY, R.drawable.green_theme);
        getWindow().getDecorView().setBackground(getResources().getDrawable(background));
    }

    public void selectLevel(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        String level = "";
        switch (view.getId()){
            case R.id.level_begineer:
                if(checked){
                    level = "begineer";
                }
                break;
            case R.id.level_intermediate:
                if(checked){
                    level = "intermediate";
                }
                break;
            case R.id.level_advanced:
                if(checked){
                    level = "advanced";
                }
                break;
        }
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putString(UsersSharedPreferences.LEVEL_USERS, level);
        preferencesEditor.apply();
        Log.d("LEVEL", mPreferences.getString(UsersSharedPreferences.LEVEL_USERS, "level salah"));
        editLevel(level, mPreferences);
        Intent i = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(i);
    }

    public void editLevel(String level, SharedPreferences mPreferences){
        int user = mPreferences.getInt(UsersSharedPreferences.ID_USERS, 0);
        String urlstring = api.EDIT_LEVEL_URL+"user="+user+"&level="+level;
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        try {
            URL url = new URL(urlstring);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                InputStream in = urlConnection.getInputStream();

                Scanner scanner = new Scanner(in);
            } finally {
                urlConnection.disconnect();
            }
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
