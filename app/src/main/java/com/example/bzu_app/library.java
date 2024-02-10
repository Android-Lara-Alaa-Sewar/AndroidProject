package com.example.bzu_app;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;



public class library extends AppCompatActivity {
    Button Home , Chat ,Notfication , Book,profile, butGet;
    TextView link;
    private RequestQueue queue;
    private ListView lstResults;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
        Home = findViewById(R.id.home_butM);
        Chat=findViewById(R.id.chat_but);
        Notfication = findViewById(R.id.notification_but);
        Book = findViewById(R.id.book_but);
        profile=findViewById(R.id.profile_but);
//        link = findViewById(R.id.link);
        queue = Volley.newRequestQueue(this);
        lstResults=findViewById(R.id._dynamic);
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
                    majors = new String[]{"Civil Engineering", "CE", "CEC"};
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
                if (selectedCollege.equals("Civil Engineering")) {
                    majors = new String[]{"ENCE231", "ENCE233", "ENCE221"};
                } else if (selectedCollege.equals("CE")) {
                    majors = new String[]{"ENSC222", "ENSC333", "ENSC2001"};
                } else if (selectedCollege.equals("Physics")) {
                    majors = new String[]{"Phys222", "Phys223", "Phys331"};
                }
                ArrayAdapter<String> majorAdapter = new ArrayAdapter<>(library.this, android.R.layout.simple_spinner_item, majors);
                majorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner3.setAdapter(majorAdapter);

                String selectedMajor = spinner3.getSelectedItem().toString();
                if (selectedMajor.equals("ENCE231")) {


                    Toast.makeText(library.this, "You selected ENCE231", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }
        });
//        Home.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(library.this, main.class);
//                startActivity(intent);
//            }
//        });
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

//flask
        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String strCat = edtCat.getText().toString().trim();
//
//                if(strCat.isEmpty()){
//                    return;
//                }

                String url = "https://10.0.2.2:5000/book_get/"+String.valueOf(2);
                JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                        null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        ArrayList<String> books = new ArrayList<>();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                books.add( obj.getString("idBook"));
                            }catch(JSONException exception){
                                Log.d("Error", exception.toString());
                            }
                        }
                        String[] arr = new String[books.size()];
                        arr = books.toArray(arr);

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(library.this,
                                android.R.layout.simple_list_item_1, arr);
                        lstResults.setAdapter(adapter);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(library.this, error.toString(),
                                Toast.LENGTH_SHORT).show();
                        Log.d("Error_json", error.toString());
                    }
                });

                queue.add(request);
            }
        });
    }


}