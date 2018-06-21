package com.example.avi.snakeandroid3.classes;

import android.app.ActionBar;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Avi on 21/06/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "highscores.db";
    public static final String TABLE_NAME = "highscores";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "score";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " ("+COL_1+" INTEGER PRIMARY KEY AUTOINCREMENT,"+ COL_2+" INTEGER) ");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(int score){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,score);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if(result == -1)
            return false;
        else return true;
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select "+ COL_2 + " from "+ TABLE_NAME+ " Order by " + COL_2 + " DESC;", null);
        return result;
    }
}
