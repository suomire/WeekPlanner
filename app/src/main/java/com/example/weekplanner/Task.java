package com.example.weekplanner;

class Task {
    private String timeForTask;
    private String taskName;

    Task(String mTime, String mName) {
        timeForTask = mTime;
        taskName = mName;
    }

    String getTimeForTask() {
        return timeForTask;
    }

    String getTaskName() {
        return taskName;
    }
}
