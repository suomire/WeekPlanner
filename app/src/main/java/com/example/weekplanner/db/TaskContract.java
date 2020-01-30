package com.example.weekplanner.db;

import android.provider.BaseColumns;

public class TaskContract {
    public static final String DB_NAME = "com.example.weekplanner.db";
    public static final int DB_VERSION = 1;

    public class TaskEntry implements BaseColumns {
        public static final String TABLE = "tasks";

        public static final String COL_TASK_TITLE = "title";
        public static final String DOW = "dow";
        /*public static final String MON = "MON";
        public static final String TUE = "TUE";
        public static final String WEN = "WEN";
        public static final String THU = "THU";
        public static final String FRI = "FRI";
        public static final String SAT = "SAT";
        public static final String SUN = "SUN";*/
    }
}

