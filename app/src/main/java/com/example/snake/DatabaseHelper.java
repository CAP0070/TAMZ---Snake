package com.example.snake;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Snake.db";
    public static final String TABLE_NAME = "coordinates_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "COORDINATES";
    public static final String COL_3 = "TIME";
    public static final String COL_4 = "SCORE";
    public static final String COL_5 = "DIRECTION";
    public static final String COL_6 = "FOOD";
    public static final String COL_7 = "SOUND";
    public static final String COL_8 = "FPS";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT, COORDINATES TEXT, TIME TEXT, " +
                                                    "SCORE TEXT, DIRECTION TEXT, FOOD TEXT, SOUND TEXT, FPS TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, name);

        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean insertData(String coordinates, String time, String score,
                              String direction, String food, String sound, String fps) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, coordinates);
        contentValues.put(COL_3, time);
        contentValues.put(COL_4, score);
        contentValues.put(COL_5, direction);
        contentValues.put(COL_6, food);
        contentValues.put(COL_7, sound);
        contentValues.put(COL_8, fps);

        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

    public boolean updateData(String id, String coordinates, String time, String score,
                              String direction, String food, String sound, String fps) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, id);
        contentValues.put(COL_2, coordinates);
        contentValues.put(COL_3, time);
        contentValues.put(COL_4, score);
        contentValues.put(COL_5, direction);
        contentValues.put(COL_6, food);
        contentValues.put(COL_7, sound);
        contentValues.put(COL_8, fps);
        db.update(TABLE_NAME, contentValues, "ID = ?",new String[] { id });
        return true;
    }

    public boolean updateData(String id, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, id);
        contentValues.put(COL_2, name);
        db.update(TABLE_NAME, contentValues, "ID = ?",new String[] { id });
        return true;
    }

    public Integer deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?",new String[] {id});
    }
}
