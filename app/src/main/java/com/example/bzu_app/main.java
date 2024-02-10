package com.example.bzu_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class main extends AppCompatActivity {
private CardView avg;
private Button home,profile ,book , Notfication , Chat
         ;

    private CardView task, profilcard ,Bookcard ,news ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afterlogin);
        avg =findViewById(R.id.cardavg);
        home=findViewById(R.id.home_butM);
        task=findViewById(R.id.cardtask);
        profile=findViewById(R.id.profile_but);
        profilcard = findViewById(R.id.cardstudent);
        Bookcard = findViewById(R.id.cardcollege);
        book =findViewById(R.id.book_but);
        news =findViewById(R.id.cardtnews);
        avg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(main.this,l_AVA.class);
                startActivity(intent);
            }
        });

        task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(main.this, Task_Activity.class);
                startActivity(intent);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(main.this,main.class);
                startActivity(intent);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(main.this, profileStudent.class);
                startActivity(intent);
            }
        });
        profilcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(main.this, profileStudent.class);
                startActivity(intent);
            }
        });

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(main.this, library.class);
                startActivity(intent);
            }
        });

        Bookcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(main.this, library.class);
                startActivity(intent);
            }
        });
        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(main.this, UploadNews.class);
                startActivity(intent);
            }
        });
    }
}