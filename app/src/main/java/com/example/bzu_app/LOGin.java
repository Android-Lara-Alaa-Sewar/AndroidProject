package com.example.bzu_app;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LOGin extends AppCompatActivity {
    private TextView id, name;
    private CheckBox RemamberMe;
    private Button LogIN, signup;

    private static final String SPref = "MyPrefs";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String REMEMBER = "remember";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        id = findViewById(R.id.loginId);
        name = findViewById(R.id.loginPass);
        RemamberMe = findViewById(R.id.checkBox);
        LogIN = findViewById(R.id.but_login);
        signup = findViewById(R.id.but_sign);

        SharedPreferences sharedPreferences = getSharedPreferences(SPref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
//
        if (sharedPreferences.getBoolean(REMEMBER, false)) {
            id.setText(sharedPreferences.getString(USERNAME, ""));
            name.setText(sharedPreferences.getString(PASSWORD, ""));
            RemamberMe.setChecked(true);
        }

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LOGin.this, Signup.class);
                startActivity(intent);
            }
        });


        LogIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = id.getText().toString();
                String password = name.getText().toString();
                loginUser(username, password);
            }
        });

    }


    private void loginUser(String id, String name) {
        SharedPreferences sharedPreferences = getSharedPreferences(SPref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String url = "http://10.0.2.2:5000/login";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    boolean found = false;
                    for (int i = 0; i < response.length(); i++) {
                        JSONArray user = response.getJSONArray(i);
                        String userId = user.getString(0);
                        String userName = user.getString(1);
                        if (userId.equals(id) && userName.equals(name)) {
                            found = true;
                            break;
                        }
                    }
                    if (found) {
                        if (RemamberMe.isChecked()) {
                            saveCredentials(id, name);

                        }
                        Intent intent = new Intent(LOGin.this, main.class);
                        startActivity(intent);
                        Toast.makeText(LOGin.this, "Login successful!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LOGin.this, "Invalid Login, Try Again!", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException exception) {
                    Log.d("Error", exception.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LOGin.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("Error_json", error.toString());
            }
        });

        Volley.newRequestQueue(this).add(request);
    }

    private void saveCredentials(String id, String name) {
        SharedPreferences sharedPreferences = getSharedPreferences(SPref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USERNAME, id);
        editor.putString(PASSWORD, name);
        editor.putBoolean(REMEMBER, true);
        editor.apply();
    }}