package com.example.bzu_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class profileStudent extends AppCompatActivity {
    Button Home , Chat ,Notfication , Book,profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_student);
        Home = findViewById(R.id.home_butM);
        Chat=findViewById(R.id.chat_but);
        Notfication = findViewById(R.id.notification_but);
        Book = findViewById(R.id.book_but);
        profile=findViewById(R.id.profile_but);



        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(profileStudent.this, main.class);
                startActivity(intent);
            }
        });
        Chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(profileStudent.this, main.class);
                startActivity(intent);
            }
        });


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
}