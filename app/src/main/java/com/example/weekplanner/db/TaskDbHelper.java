package com.example.weekplanner.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TaskDbHelper extends SQLiteOpenHelper {

    private static final String TAG = "TaskDbHelper";

    public TaskDbHelper(Context context) {

        super(context, TaskContract.DB_NAME, null, TaskContract.DB_VERSION);
        Log.i(TAG, "hey, TaskDbHelper");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "hey, onCreateDB");
        String createTable = "CREATE TABLE " + TaskContract.TaskEntry.TABLE + " ( "
                + TaskContract.TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TaskContract.TaskEntry.COL_TASK_TITLE + " TEXT NOT NULL, "
                + TaskContract.TaskEntry.DOW + " TEXT NOT NULL, "
                + TaskContract.TaskEntry.TIME + " INTEGER);";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TaskContract.TaskEntry.TABLE);
        onCreate(db);
    }
}
