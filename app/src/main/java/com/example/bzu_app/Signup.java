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

public class Signup extends AppCompatActivity {
    private TextView NAme ,Pass  , CPass, ID,Email;
    private Button SignUp;

    private static final String SPref1 = "MyPrefs";
    private static final String USERNAME1 = "username";
    private static final String PASSWORD1 = "password";
    private static final String ID1 = "id";
    private static final String EMAIL = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ID = findViewById(R.id.loginId);
        NAme =findViewById(R.id.loginname);
        Email =findViewById(R.id.loginemail);

        Pass = findViewById(R.id.loginPass);
        CPass = findViewById(R.id.confirmpass);
        SignUp = findViewById(R.id.but_login);


        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp ();
            }
        });
    }

    private void signUp() {
        String username = NAme.getText().toString();
        String email = Email.getText().toString();

        String password = Pass.getText().toString();
        String Cpassword = CPass.getText().toString();
        String id = ID.getText().toString();

        if (password.equals(Cpassword)){
            saveUserDetails(username, password, Cpassword, id,email); // قم بإرسال الـ ID إلى الدالة
            Toast.makeText(this, "Sign Up successful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Signup.this, main.class);
            startActivity(intent);
        }else
            Toast.makeText(this, "Mismatched passwords!!", Toast.LENGTH_SHORT).show();
    }


    private void saveUserDetails(String username, String password, String CPassword, String id ,String email) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("username", username);
        editor.putString("email", email);

        editor.putString("password", password);
        editor.putString("id", id);
        editor.apply();
    }

    @Override
    protected void onStop() {
        super.onStop();

        String username = NAme.getText().toString();
        String password = Pass.getText().toString();

        SharedPreferences sharedPreferences = getSharedPreferences(SPref1, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USERNAME1, username);
        editor.putString(PASSWORD1, password);
        editor.apply();
    }
    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences sharedPreferences = getSharedPreferences(SPref1, Context.MODE_PRIVATE);
        String savedUsername = sharedPreferences.getString(USERNAME1, "");
        String savedEmail = sharedPreferences.getString(EMAIL, "");

        String savedPassword = sharedPreferences.getString(PASSWORD1, "");
        String savedID = sharedPreferences.getString(ID1, ""); // استرداد الـ ID هنا

        NAme.setText(savedUsername);
        Pass.setText(savedPassword);
        CPass.setText(savedPassword);
        ID.setText(savedID);
        Email.setText(savedEmail);
    }


}