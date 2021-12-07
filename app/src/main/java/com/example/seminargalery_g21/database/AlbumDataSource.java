package com.example.seminargalery_g21.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.seminargalery_g21.helper.Photo;

import java.util.ArrayList;
import java.util.List;

public class AlbumDataSource {

    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    private static final String UPDATE_REACT_PHOTO =
            "UPDATE PHOTO SET IS_REACT = ? where PATH = ?";
    private static final String UPDATE_BIN_PHOTO =
            "UPDATE PHOTO SET IS_RECYCLE_BIN = ? where PATH = ?";
    private static final String DELETE_PHOTO =
            "DELETE FROM PHOTO where PATH = ?";

    public AlbumDataSource(Context context) {
        dbHelper = new DatabaseHelper(context, "DB_PHOTO", null, 1);
        db = dbHelper.getWritableDatabase();
    }

    public List<Photo> getPhotos() {
        List<Photo> photos = new ArrayList<>();

        String query = "SELECT * FROM PHOTO";

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Photo photo = new Photo(
                        cursor.getString(0),
                        cursor.getInt(1),
                        cursor.getInt(2)
                );
                photos.add(photo);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return photos;
    }
    public long insert(String path){
        ContentValues row = new ContentValues();
        row.put("PATH", path);
        row.put("IS_REACT", 0);
        row.put("IS_RECYCLE_BIN", 0);
        long r = db.insert("PHOTO", null, row);
        return r;
    }

    public void updateReact(String path, int react){
        db.execSQL(UPDATE_REACT_PHOTO, new String[]{react+"", path});
    }

    public void updateBin(String path, int state){
        db.execSQL(UPDATE_BIN_PHOTO, new String[]{state+"", path});
    }

    public void deleteProductByPath(String path) {
        db.execSQL(DELETE_PHOTO, new String[]{path});
    }
    public void close(){
        dbHelper.close();
    }
}