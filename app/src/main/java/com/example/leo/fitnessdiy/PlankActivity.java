package com.example.leo.fitnessdiy;

import android.content.Intent;
import android.net.Uri;
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
        new CountDownTimer(120000, 1000){
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
