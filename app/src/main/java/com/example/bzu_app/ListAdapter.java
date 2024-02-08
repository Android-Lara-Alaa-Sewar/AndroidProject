package com.example.bzu_app;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;


public class ListAdapter extends BaseAdapter {

    private TaskList taskList;
    private Context context;

    public ListAdapter(TaskList taskList, Context context) {
        this.taskList = taskList;
        this.context = context;
    }

    public void updateTaskList(TaskList newTaskList) {
        this.taskList = newTaskList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return taskList.size();
    }

    @Override
    public Object getItem(int position) {
        return taskList.getTask(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_task, parent, false);
        } else {
            view = convertView;
        }

        Task task = taskList.getTask(position);

        // the task title
        TextView taskTitle = view.findViewById(R.id.task_title);
        taskTitle.setText(task.getTitle());

        // the task description
        TextView taskDescription = view.findViewById(R.id.task_description);
        taskDescription.setText(task.getDescription());

        // the task date
        TextView taskDate = view.findViewById(R.id.task_date);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        taskDate.setText(simpleDateFormat.format(task.getDate().getTime()));

       CheckBox checkCompleted = view.findViewById(R.id.check_Box);
        checkCompleted.setChecked(task.isCompleted());

        Button buttonComplete = view.findViewById(R.id.button_complete);
        buttonComplete.setText(task.isCompleted() ? "Completed" : "Incomplete");

     checkCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task.setCompleted(!task.isCompleted());
                buttonComplete.setText(task.isCompleted() ? "Completed" : "Incomplete");
                taskList.saveTasks(context);
                notifyDataSetChanged();
            }
        });
        buttonComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task.setCompleted(!task.isCompleted());
               // checkCompleted.setChecked(task.isCompleted());
                taskList.saveTasks(context);
                notifyDataSetChanged();
            }
        });
        ImageButton  buttonDelete = view.findViewById(R.id.deletetask);

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskList.removeTask(task);
                taskList.saveTasks(context);
                notifyDataSetChanged();
            }
        });

        return view;
    }


}