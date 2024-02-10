package com.example.bzu_app;

import java.util.Calendar;
import java.util.Date;

public class Task {

    private String title;
    private String description;

    private Calendar date;
    private boolean completed;
    private int iconTask;

    public Task(String title, String description, Calendar date) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.completed = false;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Calendar getDate() {
        return date;
    }


    public boolean isCompleted() {
        return completed;
    }
    // Task.java
    public Date getJavaDate() {
        if (date != null) {
            return date.getTime(); // تحويل Calendar إلى Date
        } else {
            return null;
        }
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
