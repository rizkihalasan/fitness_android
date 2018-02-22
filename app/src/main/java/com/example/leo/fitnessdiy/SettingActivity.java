package com.example.leo.fitnessdiy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class SettingActivity extends AppCompatActivity {
    private String LOG_TAG = "BACKGROUND";
    private String sharedPrefFile = "com.example.leo.fitnessdiy";
    private final String BACKGROUND_KEY = "background";
    private SharedPreferences mPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        SharedPreferences mPreferences;
        String sharedPrefFile = "com.example.leo.fitnessdiy";
        final String BACKGROUND_KEY = "background";
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

        int background = mPreferences.getInt(BACKGROUND_KEY, R.drawable.green_theme);
        getWindow().getDecorView().setBackground(getResources().getDrawable(background));
    }

    public void changeBackgroundColor(View view) {
        int background = 0;
        if(view.getId() == R.id.green_theme){
            background = R.drawable.green_theme;
        }
        if(view.getId() == R.id.red_theme){
            background = R.drawable.red_theme;
        }
        if(view.getId() == R.id.beach_theme){
            background = R.drawable.beach_theme;
        }
        if(view.getId() == R.id.beach_theme_2){
            background = R.drawable.beach_2_theme;
        }
        if(view.getId() == R.id.cartoon_theme){
            background = R.drawable.cartoon_theme;
        }
        if(view.getId() == R.id.cat_theme){
            background = R.drawable.cat_theme;
        }

        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putInt(BACKGROUND_KEY, background);
        preferencesEditor.apply();

        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);

    }
}
