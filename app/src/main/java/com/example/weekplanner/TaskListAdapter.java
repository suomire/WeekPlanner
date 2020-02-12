package com.example.weekplanner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class TaskListAdapter extends ArrayAdapter<Task> {

    private LayoutInflater layoutInflater;
    private ArrayList<Task> tasks;
    private int mViewResId;

    public TaskListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Task> objects) {
        super(context, resource, objects);
        this.tasks = objects;
        this.mViewResId = resource;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(mViewResId, null);

        Task user = tasks.get(position);

        if (user != null) {
            TextView taskTitle = convertView.findViewById(R.id.task_title);
            EditText taskTime = convertView.findViewById(R.id.editText);
            if (taskTitle != null) {
                taskTitle.setText(user.getTaskName());
            }
            if (taskTime != null) {
                taskTime.setText((user.getTimeForTask()));
            }
        }

        return convertView;
    }

}
