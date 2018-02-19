package com.example.leo.fitnessdiy;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class HomeActivity extends AppCompatActivity
        implements ExerciseFragment.OnFragmentInteractionListener,
                   HistoryFragment.OnFragmentInteractionListener{

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void openHistory(View view) {
        Intent i = new Intent(getApplicationContext(), HIstoryActivity.class);
        startActivity(i);
    }

    public void startExcercise(View view) {
        Intent i = new Intent(getApplicationContext(), ExerciseActivity.class);
        startActivity(i);
    }

    public void changeFragment(View view){
        Fragment fragment;

        if(view == findViewById(R.id.history_button)){
            fragment = new HistoryFragment();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.home_fragment, fragment);
            ft.commit();
        }
        if(view == findViewById(R.id.exercise_button)){
            fragment = new ExerciseFragment();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.home_fragment, fragment);
            ft.commit();
        }
    }

    public void openJogging(View view) {
        Intent i = new Intent(getApplicationContext(), JoggingActivity.class);
        startActivity(i);
    }
}
