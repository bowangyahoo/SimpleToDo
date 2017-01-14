package com.example.bowang.simpletodo;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by bowang on 1/12/17.
 */
@Table(database = MyDatabase.class)
public class ToDoItem extends BaseModel {

    @Column
    @PrimaryKey
    int id;

    @Column
    String text;

    @Column
    int priority;

    @Column
    long date;
}
