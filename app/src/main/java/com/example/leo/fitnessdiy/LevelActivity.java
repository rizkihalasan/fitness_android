package com.example.leo.fitnessdiy;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;

import com.example.leo.fitnessdiy.model.UsersSharedPreferences;

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

    }
}
