package com.example.bowang.simpletodo;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by bowang on 1/12/17.
 */

@Database(name = MyDatabase.NAME, version = MyDatabase.VERSION)
public class MyDatabase {
    public static final String NAME = "MyDataBase";

    public static final int VERSION = 2;
}
