package com.example.leo.fitnessdiy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.leo.fitnessdiy.model.Users;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class LoginActivity extends AppCompatActivity {
    private String LOG_TAG = "LOGIN ACTIVITY";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences mPreferences;
        String sharedPrefFile = "com.example.leo.fitnessdiy";
        final String BACKGROUND_KEY = "background";
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        int background = R.drawable.green_theme;
        try{
            background = mPreferences.getInt(BACKGROUND_KEY, R.drawable.green_theme);
        } catch(ClassCastException e){
            SharedPreferences.Editor preferencesEditor = mPreferences.edit();
            preferencesEditor.putInt(BACKGROUND_KEY, background);
            preferencesEditor.apply();
        } finally {
            getWindow().getDecorView().setBackground(getResources().getDrawable(background));
        }
    }

    public void doLogin(View view) {
        EditText edit_text_email = (EditText)findViewById(R.id.login_email);
        EditText edit_text_password = (EditText)findViewById(R.id.login_password);
        String email = edit_text_email.getText().toString();
        String password = edit_text_password.getText().toString();
        String response = "";
        Users user = null;
        try {
            response = getResponseFromHttpUrlPost(email, password);
            Log.d("RESPONSE", response);
        } catch (IOException e){
            e.printStackTrace();
        }
        if(!response.equals("not_found")) {
            user = Users.initializeData(response);
        }
        if(user == null){
            Log.d(LOG_TAG, "Email atau password salah");
        } else {
            Log.d(LOG_TAG, "" + user.getId());
            Log.d(LOG_TAG, user.getUsername());
            Log.d(LOG_TAG, user.getEmail());
            Log.d(LOG_TAG, user.getLevel());
            Intent i = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(i);
        }
    }
    public static String getResponseFromHttpUrlPost(String email, String password) throws IOException {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        try {
            String urlstring = "http://ekiwae21.000webhostapp.com/fitness-server/login.php";
            URL url = new URL(urlstring);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("email", email)
                    .appendQueryParameter("password", password);
            String query = builder.build().getQuery();

            OutputStream os = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();
            urlConnection.connect();

            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
