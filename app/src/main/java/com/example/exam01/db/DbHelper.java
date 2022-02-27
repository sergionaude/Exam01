package com.example.exam01.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Exam01.db";
    private static final String TABLE_NAME= "Users";
    private static final String CMDDROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
    public static final String CMDTRUNCATE_TABLE = "DELETE FROM " + TABLE_NAME + ";";
    private static final String CMDCREATE_TABLE="CREATE TABLE " + TABLE_NAME + "(" +
            "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "Email TEXT NOT NULL," +
            "Pwd TEXT NOT NULL);";


    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CMDDROP_TABLE);
        sqLiteDatabase.execSQL(CMDCREATE_TABLE);
        sqLiteDatabase.execSQL(CMDTRUNCATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
