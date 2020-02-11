package com.example.weekplanner.db;

public class Task {
    private String timeForTask;
    private String taskName;

    public Task(String  mTime, String mName) {
        timeForTask = mTime;
        taskName = mName;
    }

    public String getTimeForTask() {
        return timeForTask;
    }

    public String getTaskName() {
        return taskName;
    }
}
