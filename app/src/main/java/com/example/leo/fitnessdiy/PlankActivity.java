package com.example.leo.fitnessdiy;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class PlankActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plank);
    }

    public void countDownPlank(View view) {
        final TextView countText = findViewById(R.id.count_timer);
        new CountDownTimer(60000, 1000){
            @Override
            public void onTick(long l) {
                String waktu = Long.toString(l/1000);
                countText.setText(waktu);
            }

            @Override
            public void onFinish() {
                countText.setText("BERHASIL");
            }
        }.start();
    }
}
