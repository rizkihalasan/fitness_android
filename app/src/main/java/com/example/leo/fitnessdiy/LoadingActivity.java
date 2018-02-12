package com.example.leo.fitnessdiy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        Thread welcomeThread = new Thread() {
            @Override
            public void run() {
                try {
                    super.run();
                    sleep(10000);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    findViewById(R.id.loading_progress).setVisibility(View.GONE);
                    Intent i = new Intent(LoadingActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        };

        welcomeThread.start();
    }
}
