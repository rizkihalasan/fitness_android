package com.example.leo.fitnessdiy;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class RegistrationActivity extends AppCompatActivity {
    private String username;
    private String password;
    private String email;
    private String phone_number;
    private String address;
    private String age;
    private String level;
    private String LOG_TAG = "REGISTRATION ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        SharedPreferences mPreferences;
        String sharedPrefFile = "com.example.leo.fitnessdiy";
        final String BACKGROUND_KEY = "background";
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

        int background = mPreferences.getInt(BACKGROUND_KEY, R.drawable.green_theme);
        getWindow().getDecorView().setBackground(getResources().getDrawable(background));
    }


    public void doRegistration(View view) {
        EditText usernameEditText = (EditText)findViewById(R.id.username);
        username = usernameEditText.getText().toString();

        EditText passwordEditText = (EditText)findViewById(R.id.password);
        password = passwordEditText.getText().toString();

        EditText emailEditText = (EditText)findViewById(R.id.email);
        email = emailEditText.getText().toString();

        EditText phoneEditText = (EditText)findViewById(R.id.phone);
        phone_number = phoneEditText.getText().toString();

        EditText addressEditText = (EditText)findViewById(R.id.address);
        address = addressEditText.getText().toString();

        EditText ageEditText = (EditText)findViewById(R.id.age);
        age = ageEditText.getText().toString();


        Log.d(LOG_TAG, username);
        Log.d(LOG_TAG, password);
        Log.d(LOG_TAG, email);
        Log.d(LOG_TAG, address);
        Log.d(LOG_TAG, phone_number);
        Log.d(LOG_TAG, age);
        Log.d(LOG_TAG, level);
        register(username, password, email, address, phone_number, age, level);
    }

    public void getLevel(View view){
        boolean checked = ((RadioButton)view).isChecked();
        level = "";
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
    }

    public void register(String username, String password, String email, String address,
                         String phone_number, String age, String level){
        if (Build.VERSION.SDK_INT > 9){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        HttpURLConnection urlConnection = null;
        try{
            String result;
            String urlstring = "http://ekiwae21.000webhostapp.com/fitness-server/register.php";
            URL url = new URL(urlstring);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("username", username)
                    .appendQueryParameter("password", password)
                    .appendQueryParameter("email", email)
                    .appendQueryParameter("address", address)
                    .appendQueryParameter("phone_number", phone_number)
                    .appendQueryParameter("age", age)
                    .appendQueryParameter("level", level);
            String query = builder.build().getQuery();

            OutputStream os = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(query);
            writer.flush();
            os.close();
            urlConnection.connect();

            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
//            scanner.useDelimiter("\\A");
//            if (scanner.hasNext()) {
//                result = scanner.next();
//            } else {
//                result = "";
//            }

//            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//            StringBuilder out = new StringBuilder();
//            if(result.equals("Username_atau_Email_sudah_ada")){
//                Toast.makeText(getApplicationContext(), "Username atau Email sudah ada", Toast.LENGTH_SHORT).show();
//            }
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }
    }
}
