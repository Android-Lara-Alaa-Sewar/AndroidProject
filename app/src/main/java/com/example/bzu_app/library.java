package com.example.bzu_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class library extends AppCompatActivity {
    Button Home , Chat ,Notfication , Book,profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
        Home = findViewById(R.id.home_butM);
        Chat=findViewById(R.id.chat_but);
        Notfication = findViewById(R.id.notification_but);
        Book = findViewById(R.id.book_but);
        profile=findViewById(R.id.profile_but);


        Spinner spinner = findViewById(R.id.colleg);
        Spinner spinner2 = findViewById(R.id.major);
        Spinner spinner3 = findViewById(R.id.cours);

        String[] colleges = new String[]{"Colleges", "Engineer", "SCI", "Medical"};
        ArrayAdapter<String> collegeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, colleges);
        collegeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(collegeAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedCollege = spinner.getSelectedItem().toString();
                String[] majors = new String[]{};
                if (selectedCollege.equals("Engineer")) {
                    majors = new String[]{"CS", "CE", "CEC"};
                } else if (selectedCollege.equals("SCI")) {
                    majors = new String[]{"Physics", "Chemistry", "Biology"};
                } else if (selectedCollege.equals("Medical")) {
                    majors = new String[]{"Doctor OF Pharmacy", "Nursing", "Nutrition Dietetics"};
                }
                ArrayAdapter<String> majorAdapter = new ArrayAdapter<>(library.this, android.R.layout.simple_spinner_item, majors);
                majorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(majorAdapter);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }
        });


        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedCollege = spinner2.getSelectedItem().toString();
                String[] majors = new String[]{};
                if (selectedCollege.equals("CS")) {
                    majors = new String[]{"Comp111", "Comp233", "comp333"};
                } else if (selectedCollege.equals("CE")) {
                    majors = new String[]{"ENSC222", "ENSC333", "ENSC2001"};
                } else if (selectedCollege.equals("Physics")) {
                    majors = new String[]{"Phys222", "Phys223", "Phys331"};
                }
                ArrayAdapter<String> majorAdapter = new ArrayAdapter<>(library.this, android.R.layout.simple_spinner_item, majors);
                majorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner3.setAdapter(majorAdapter);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }
        });
        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(library.this, main.class);
                startActivity(intent);
            }
        });
        Chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(library.this, main.class);
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
                Intent intent = new Intent(library.this, profileStudent.class);
                startActivity(intent);
            }
        });
        Book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(library.this, library.class);
                startActivity(intent);
            }
        });



    }
}