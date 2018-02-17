package com.example.leo.fitnessdiy;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class HomeActivity extends AppCompatActivity
        implements ExerciseFragment.OnFragmentInteractionListener,
                   HistoryFragment.OnFragmentInteractionListener{

    private FloatingActionButton chatButton;
    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        chatButton = findViewById(R.id.chat_button);
        chatButton.setImageResource(R.drawable.chat_icon);
    }

    public void openHistory(View view) {
        Intent i = new Intent(getApplicationContext(), HIstoryActivity.class);
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
        else if(view == findViewById(R.id.exercise_button)){
            fragment = new ExerciseFragment();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.home_fragment, fragment);
            ft.commit();
        }
    }

    public void goToChat(View view) {

    }
}
