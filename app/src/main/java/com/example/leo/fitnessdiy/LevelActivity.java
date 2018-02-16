package com.example.leo.fitnessdiy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

public class LevelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
    }

    public void selectLevel(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()){
            case R.id.level_begineer:
                if(checked){

                }
                break;
            case R.id.level_intermediate:
                if(checked){

                }
                break;
            case R.id.level_advanced:
                if(checked){

                }
                break;
        }
    }
}
