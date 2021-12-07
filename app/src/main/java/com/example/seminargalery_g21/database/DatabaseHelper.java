package com.example.seminargalery_g21.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String CREATE_TABLE_PHOTO =
            "CREATE TABLE PHOTO (PATH TEXT, IS_REACT INTEGER , IS_RECYCLE_BIN INTEGER)";

    private static final String DROP_TABLE_PHOTO = "DROP TABLE IF EXISTS PHOTO";

    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_PHOTO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DROP_TABLE_PHOTO);
        onCreate(sqLiteDatabase);
    }
}