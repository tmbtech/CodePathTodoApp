package com.delavictoria.codepathtodoapp.database;

import com.raizlabs.android.dbflow.annotation.Database;

@Database(name = TodoDatabase.NAME, version = TodoDatabase.VERSION)
public class TodoDatabase {
    public static final String NAME = "TodoDatabase";
    public static final int VERSION = 2;
}
