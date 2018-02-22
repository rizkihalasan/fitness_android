package com.example.leo.fitnessdiy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity
        implements ExerciseFragment.OnFragmentInteractionListener,
                   HistoryFragment.OnFragmentInteractionListener,
                   BlankFragment.OnFragmentInteractionListener{

    private FloatingActionButton chatButton;

    // Sensor variable declaration
    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private SensorEventListener proximitySensorListener;


    private final String LOG_TAG = "BACKGROUND";
    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_setting_background) {
            Intent i = new Intent(this, SettingActivity.class);
            startActivity(i);
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        chatButton = findViewById(R.id.chat_button);
        chatButton.setImageResource(R.drawable.chat_icon);

        SharedPreferences mPreferences;
        String sharedPrefFile = "com.example.leo.fitnessdiy";
        final String BACKGROUND_KEY = "background";

        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        int background = mPreferences.getInt(BACKGROUND_KEY, R.drawable.green_theme);
        getWindow().getDecorView().setBackground(getResources().getDrawable(background));

        // Proximity Sensor
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        if (proximitySensor == null) {
            Toast.makeText(this, "Proximity sensor is not available !", Toast.LENGTH_LONG).show();
            finish();
        }

        proximitySensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if (sensorEvent.values[0] < proximitySensor.getMaximumRange()) {
                    Intent i = new Intent(getApplicationContext(), ChatActivity.class);
                    startActivity(i);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
            }
        };

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
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        }
        else if(view == findViewById(R.id.exercise_button)){
            fragment = new ExerciseFragment();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.home_fragment, fragment);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

            ft.commit();
        }
    }

    public void openJogging(View view) {
        Intent i = new Intent(getApplicationContext(), JoggingActivity.class);
        startActivity(i);
    }
    public void goToChat(View view) {
        Intent i = new Intent(getApplicationContext(), ChatActivity.class);
        startActivity(i);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(proximitySensorListener, proximitySensor,
                2 * 1000 * 1000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(proximitySensorListener);
    }


}
