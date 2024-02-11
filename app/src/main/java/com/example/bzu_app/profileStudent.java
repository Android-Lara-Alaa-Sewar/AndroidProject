package com.example.bzu_app;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
public class profileStudent extends AppCompatActivity {
    Button Home , Chat ,Notfication , Book,profile;
    EditText editTextId, EditTextName, EditTextEmail, EditTextNumber, EditTextNumber2, EditTextNumber3, EditTextNumber6; // أضفت الحقول هنا

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_student);

        editTextId = findViewById(R.id.editTextId); // Initialize EditText
        EditTextName = findViewById(R.id.EditTextName); // Initialize EditText
        EditTextEmail = findViewById(R.id.editTextTextEmailAddress); // Initialize EditText
        EditTextNumber = findViewById(R.id.editTextNumber); // Initialize EditText
        EditTextNumber2 = findViewById(R.id.editTextNumber2); // Initialize EditText
        EditTextNumber3 = findViewById(R.id.editTextNumber3); // Initialize EditText
        EditTextNumber6 = findViewById(R.id.editTextNumber6); // Initialize EditText

        // Retrieve the user's ID from where it's stored, such as SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("id", "");
        String username = sharedPreferences.getString("username", "");
        String email = sharedPreferences.getString("email", "");

        // Set the retrieved ID to the EditText
        editTextId.setText(userId);
        EditTextName.setText(username);
        EditTextEmail.setText(email);

        Home = findViewById(R.id.home_butM);
        Chat=findViewById(R.id.chat_but);
        Notfication = findViewById(R.id.notification_but);
        Book = findViewById(R.id.book_but);
        profile=findViewById(R.id.profile_but);

        Intent intent = getIntent();


        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(profileStudent.this, main.class);
                startActivity(intent);
            }
        });
//        Chat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(profileStudent.this, main.class);
//                startActivity(intent);
//            }
//        });


        Notfication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(profileStudent.this, profileStudent.class);
                startActivity(intent);
            }
        });
        Book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(profileStudent.this, library.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();

        // Get the values entered by the user
        String number = EditTextNumber.getText().toString();
        String number2 = EditTextNumber2.getText().toString();
        String number3 = EditTextNumber3.getText().toString();
        String number6 = EditTextNumber6.getText().toString();

        // Save the values in SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("number", number);
        editor.putString("number2", number2);
        editor.putString("number3", number3);
        editor.putString("number6", number6);
        editor.apply();
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Retrieve the values from SharedPreferences and set them to EditTexts
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String savedNumber = sharedPreferences.getString("number", "");
        String savedNumber2 = sharedPreferences.getString("number2", "");
        String savedNumber3 = sharedPreferences.getString("number3", "");
        String savedNumber6 = sharedPreferences.getString("number6", "");

        EditTextNumber.setText(savedNumber);
        EditTextNumber2.setText(savedNumber2);
        EditTextNumber3.setText(savedNumber3);
        EditTextNumber6.setText(savedNumber6);
    }
}
