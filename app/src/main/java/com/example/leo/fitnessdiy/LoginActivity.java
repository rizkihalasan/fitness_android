package com.example.leo.fitnessdiy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences mPreferences;
        String sharedPrefFile = "com.example.leo.fitnessdiy";
        final String BACKGROUND_KEY = "background";
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        int background = R.drawable.green_theme;
        try{
            background = mPreferences.getInt(BACKGROUND_KEY, R.drawable.green_theme);
        } catch(ClassCastException e){
            SharedPreferences.Editor preferencesEditor = mPreferences.edit();
            preferencesEditor.putInt(BACKGROUND_KEY, background);
            preferencesEditor.apply();
        } finally {
            getWindow().getDecorView().setBackground(getResources().getDrawable(background));
        }
    }

    public void doLogin(View view) {
        Intent i = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(i);
    }
}
