package com.example.leo.fitnessdiy;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.TextView;

import com.example.leo.fitnessdiy.Utilities.NetworkUtils;
import com.example.leo.fitnessdiy.routes.api;


import java.io.IOException;
import java.net.URL;


public class HIstoryActivity extends AppCompatActivity {
    public static  final String TAG = HIstoryActivity.class.getSimpleName();
    private TextView mHasil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        mHasil = (TextView) findViewById(R.id.hasil);

        String githubSearchResults = null;
        try {
            githubSearchResults = NetworkUtils.getResponseFromHttpUrl(new URL(api.HISTORY_URL + "1"));
            mHasil.setText(githubSearchResults);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
