package com.example.bzu_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;



public class library extends AppCompatActivity {
    Button Home, Chat, Notfication, Book, profile, GEt;
    TextView links, bookname;
    private RequestQueue queue;
    private ListView lstResults;
    private ImageView img;
    private CardView items, items2;
    private CheckBox fav;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
        Home = findViewById(R.id.home_butM);
        Chat = findViewById(R.id.chat_but);
        Notfication = findViewById(R.id.notification_but);
        Book = findViewById(R.id.book_but);
        profile = findViewById(R.id.profile_but);
        links = findViewById(R.id.link);
        bookname = findViewById(R.id.namethebook);
        queue = Volley.newRequestQueue(this);
        Spinner spinner = findViewById(R.id.colleg);
        Spinner spinner2 = findViewById(R.id.major);
        Spinner spinner3 = findViewById(R.id.cours);
        img = findViewById(R.id.book_image);
        items = findViewById(R.id.item);
        items2 = findViewById(R.id.item2);
        GEt =findViewById(R.id.get);
        String[] colleges = new String[]{"Colleges", "Engineering", "SCI", "Medical"};
        ArrayAdapter<String> collegeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, colleges);
        collegeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(collegeAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedCollege = spinner.getSelectedItem().toString();
                String[] majors = new String[]{};
                if (selectedCollege.equals("Engineering")) {
                    majors = new String[]{"Civil Engineering", "Computer Science", "CEC"};
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
            }
        });


        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedCollege = spinner2.getSelectedItem().toString();
                String[] majors = new String[]{};
                if (selectedCollege.equals("Civil Engineering")) {
                    majors = new String[]{"ENCE231", "ENCE232"};
                } else if (selectedCollege.equals("Computer Science")) {
                    majors = new String[]{"Comp333", "Comp242", "COMP438"};
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
//        Chat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(library.this, main.class);
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


//        butGet = findViewById(R.id.butGet);
        GEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedCollege = spinner.getSelectedItem().toString();
                String selectedMajor = spinner2.getSelectedItem().toString();
                String selectedCourse = spinner3.getSelectedItem().toString();

                String url = "http://10.0.2.2:5000/library";

                JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                        null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            boolean found = false;
                            for (int i = 0; i < response.length(); i++) {
                                JSONArray book = response.getJSONArray(i);
                                String coll = book.getString(0);
                                String major = book.getString(1);
                                String course = book.getString(2);
                                String nameBook = book.getString(4);
                                String bookLink = book.getString(5);

                                if (coll.equals(selectedCollege) && major.equals(selectedMajor) && course.equals(selectedCourse)) {
                                    found = true;
                                    items.setVisibility(View.VISIBLE);
                                    items2.setVisibility(View.VISIBLE);
                                    createBookCard(i, nameBook, bookLink);

                                    fav.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            // Call a method to add the card to favorites activity
                                            addToFavorites(nameBook, bookLink);
                                        }
                                    });
                                }
                            }
                            if (!found) {
                                // Show a toast indicating book unavailability
                                Toast.makeText(library.this, "Book not available for the selected options", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException exception) {
                            Log.e("Error", "Error parsing JSON", exception);
                        }
                    }


                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Show a toast indicating error in fetching data
                        Toast.makeText(library.this, "Error fetching data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("Error", "Error fetching data", error);
                    }
                });

                queue.add(request);
            }
        });


    }

    private void createBookCard(int index, String nameBook, String bookLink) {
        CardView cardView = new CardView(library.this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(16, 16, 16, 16);
        cardView.setLayoutParams(layoutParams);
        cardView.setRadius(8);
        cardView.setContentPadding(16, 16, 16, 16);
        cardView.setCardBackgroundColor(Color.WHITE);
        cardView.setMaxCardElevation(30);
        cardView.setCardElevation(20);

        LinearLayout innerLayout = new LinearLayout(library.this);
        innerLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        innerLayout.setOrientation(LinearLayout.HORIZONTAL);

        ImageView bookImageView = new ImageView(library.this);
        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(
                200,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        bookImageView.setLayoutParams(imageParams);
        bookImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        if (nameBook.equals("Data Base")) {
            bookImageView.setImageResource(R.drawable.database);
        } else if (nameBook.equals("Data Structures")) {
            // Set image for Data Structures book
            bookImageView.setImageResource(R.drawable.datast);
        }else if (nameBook.equals("STATICS")) {
            // Set image for Data Structures book
            bookImageView.setImageResource(R.drawable.datast);
        }else if (nameBook.equals("ENGINEERING GEOLOGY")) {
            // Set image for Data Structures book
            bookImageView.setImageResource(R.drawable.datast);
        }else if (nameBook.equals("DATA STRUCTURES")) {
            // Set image for Data Structures book
            bookImageView.setImageResource(R.drawable.datast);
        }else if (nameBook.equals("Android")) {
            // Set image for Data Structures book
            bookImageView.setImageResource(R.drawable.datast);
        }
        innerLayout.addView(bookImageView);

        LinearLayout textLayout = new LinearLayout(library.this);
        textLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        textLayout.setOrientation(LinearLayout.VERTICAL);

        TextView nameTextView = new TextView(library.this);
        nameTextView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        nameTextView.setText(nameBook);
        nameTextView.setTextColor(Color.parseColor("#FF188267"));
        nameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 23);
        nameTextView.setTypeface(null, Typeface.BOLD);

        TextView linkTextView = new TextView(library.this);
        linkTextView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                120 // Height
        ));
        linkTextView.setText(bookLink);
        linkTextView.setTextColor(Color.parseColor("#FF188267"));
        linkTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        linkTextView.setTypeface(null, Typeface.BOLD);
        linkTextView.setClickable(true);
        linkTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(bookLink));
                startActivity(intent);
            }
        });

        textLayout.addView(nameTextView);
        textLayout.addView(linkTextView);

        innerLayout.addView(textLayout);

        cardView.addView(innerLayout);

        if (index == 0) {
            items.addView(cardView);
        } else if (index == 1) {
            items2.addView(cardView);
        }
    }
    private void addToFavorites(String nameBook, String bookLink) {
        Intent intent = new Intent(library.this, FavoriteActivity.class);
        intent.putExtra("nameBook", nameBook);
        intent.putExtra("bookLink", bookLink);
        startActivity(intent);
    }
}