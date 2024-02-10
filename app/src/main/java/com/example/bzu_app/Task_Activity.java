package com.example.bzu_app;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.os.Bundle;


public class Task_Activity extends AppCompatActivity {


    private TaskList taskList;
    private ListAdapter listAdapter;
    private Button addButton;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        addButton = findViewById(R.id.button);
        taskList = new TaskList();
        taskList.getTasks(this);
        listAdapter = new ListAdapter(taskList, this);

        listView = findViewById(R.id.listView);
        listView.setAdapter(listAdapter);

        Button buttonBack = findViewById(R.id.buttonBack);


        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Task_Activity.this, main.class);
                startActivity(intent);
            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Task_Activity.this, AddActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Load tasks
        taskList.getTasks(this);
        // Update the TextViews
        TextView textView2 = findViewById(R.id.textView2);
        TextView textView3 = findViewById(R.id.textView3);
        if (taskList.size() > 0) {
            textView2.setVisibility(View.GONE);
            textView3.setVisibility(View.GONE);
        } else {
            textView2.setVisibility(View.VISIBLE);
            textView3.setVisibility(View.VISIBLE);
        }
        listAdapter.updateTaskList(taskList);
        listAdapter.notifyDataSetChanged();


    }


}
