package com.example.leo.fitnessdiy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.leo.fitnessdiy.model.UsersSharedPreferences;

/**
 * Created by Heil on 2/19/2018.
 */

public class SitupActivity extends AppCompatActivity{

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
    }

    public void setSitUpFrequency(String level){
        TextView sitUpTask = (TextView)findViewById(R.id.sitUpTask);
        if(level.equals("begineer")){
            sitUpTask.setText("Do Sit Up 30 times");
        }else if(level.equals("intermediate")){
            sitUpTask.setText("Do Sit Up 60 times");
        }else if(level.equals("advanced")){
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
    }

    public void taskBegin(View view) {
        TextView tv = (TextView)findViewById(R.id.sitUpTask);
        Button button1 = (Button) findViewById(R.id.start_situp_button);
        button1.setVisibility(View.GONE);

        Button button2 = (Button) findViewById(R.id.finish_situp_button);
        button2.setVisibility(View.VISIBLE);
    }
}
