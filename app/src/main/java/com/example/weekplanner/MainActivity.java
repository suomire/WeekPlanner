package com.example.weekplanner;

import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.weekplanner.db.Task;
import com.example.weekplanner.db.TaskContract;
import com.example.weekplanner.db.TaskDbHelper;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TaskDbHelper mHelper;

    private ListView mTaskListView;
    private TaskListAdapter mAdapter;

    private TabLayout tabLayout;
    private String[] weekDays = new String[]{"MON", "TUE", "WEN", "THU", "FRI", "SAT", "SUN"};
    private int position;
    private EditText txtTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout = findViewById(R.id.tablelayout);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                updateUI();
                position = tab.getPosition();
                Log.i(TAG, "hey = " + position);
                updateUI();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        mHelper = new TaskDbHelper(this);
        mTaskListView = findViewById(R.id.list_todo);


        updateUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_add_task:
                Log.i(TAG, "AddNewTask");
                addTask();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addTask() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        final View mView = getLayoutInflater().inflate(R.layout.dialog_item, null);
        final EditText addNewTask = mView.findViewById(R.id.task_name);
        EditText settedTime = mView.findViewById(R.id.textTime);
        Button setTime = mView.findViewById(R.id.set_time_btn);
        setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                txtTime = mView.findViewById(R.id.textTime);
                                String str = hourOfDay + ":" + minute;
                                txtTime.setText(str);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });
        mBuilder.setView(mView)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String task = String.valueOf(addNewTask.getText());
                        SQLiteDatabase db = mHelper.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put(TaskContract.TaskEntry.COL_TASK_TITLE, task);
                        values.put(TaskContract.TaskEntry.DOW, weekDays[position]);
                        values.put(TaskContract.TaskEntry.TIME, String.valueOf(txtTime.getText()));

                        db.insertWithOnConflict(TaskContract.TaskEntry.TABLE,
                                null,
                                values,
                                SQLiteDatabase.CONFLICT_REPLACE);
                        db.close();
                        updateUI();
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        mBuilder.show();


    }

    private void updateUI() {
        ArrayList<Task> taskList = new ArrayList<>();
        ArrayList<String> timeList = new ArrayList<>();
        String[] selectionArgs = new String[]{weekDays[position]};
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(TaskContract.TaskEntry.TABLE,
                new String[]{TaskContract.TaskEntry._ID, TaskContract.TaskEntry.COL_TASK_TITLE, TaskContract.TaskEntry.DOW, TaskContract.TaskEntry.TIME},
                "dow = ? ", selectionArgs, null, null, TaskContract.TaskEntry.TIME);
        while (cursor.moveToNext()) {
            Task task = new Task(cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.TIME)),
                    cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TITLE)));
            taskList.add(task);

        }

        if (mAdapter == null) {
            mAdapter = new TaskListAdapter(this,
                    R.layout.item_todo,
                    taskList);
            mTaskListView.setAdapter(mAdapter);
        } else {
            mAdapter.clear();
            mAdapter.addAll(taskList);
            mAdapter.notifyDataSetChanged();
        }

        cursor.close();
        db.close();
    }

    public void deleteTask(View view) {
        View parent = (View) view.getParent();
        TextView taskTextView = (TextView) parent.findViewById(R.id.task_title);
        String task = String.valueOf(taskTextView.getText());
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.delete(TaskContract.TaskEntry.TABLE,
                TaskContract.TaskEntry.COL_TASK_TITLE + " = ?",
                new String[]{task});
        db.close();
        updateUI();
    }

    //TODO make async tasks!!
}
