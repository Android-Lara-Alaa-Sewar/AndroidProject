package com.example.bzu_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Signup extends AppCompatActivity {
    private TextView NAme ,Pass  , CPass,email,id;
    private Button SignUp;

    private static final String SPref1 = "MyPrefs";
    private static final String USERNAME1 = "username";
    private static final String PASSWORD1 = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        NAme =findViewById(R.id.loginname);
        Pass = findViewById(R.id.loginPass);
        CPass = findViewById(R.id.confirmpass);
        SignUp = findViewById(R.id.but_login);
        email=findViewById(R.id.loginemail);
        id=findViewById(R.id.loginId);
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp ();
            }
        });
    }

    private void signUp() {
        String username = NAme.getText().toString();
        String password = Pass.getText().toString();
        String Cpassword = CPass.getText().toString();
        String Email = email.getText().toString();
        String Id =id.getText().toString();
        String url = "http://10.0.2.2:5000/signup";
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("id", Id);
            jsonBody.put("name", username);
            jsonBody.put("email", Email);
            jsonBody.put("password", password);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (password.equals(Cpassword)){

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                boolean success = response.getBoolean("success");
                                String message = response.getString("message");
                                if (success) {
                                    Toast.makeText(Signup.this, message, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Signup.this, main.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(Signup.this, message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(Signup.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Signup.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

            Volley.newRequestQueue(this).add(request);
        } else {
            Toast.makeText(this, "Mismatched passwords!!", Toast.LENGTH_SHORT).show();
        }
    }



//
//    private void saveUserDetails(String username, String password,String CPassword) {
//        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
//
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//
//        editor.putString("username", username);
//        editor.putString("password", password);
//        editor.apply();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//
//        String username = NAme.getText().toString();
//        String password = Pass.getText().toString();
//
//        SharedPreferences sharedPreferences = getSharedPreferences(SPref1, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString(USERNAME1, username);
//        editor.putString(PASSWORD1, password);
//        editor.apply();
//    }
//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        SharedPreferences sharedPreferences = getSharedPreferences(SPref1, Context.MODE_PRIVATE);
//        String savedUsername = sharedPreferences.getString(USERNAME1, "");
//        String savedPassword = sharedPreferences.getString(PASSWORD1, "");
//
//        NAme.setText(savedUsername);
//        Pass.setText(savedPassword);
//        CPass.setText(savedPassword);
//    }
//
//
//
}