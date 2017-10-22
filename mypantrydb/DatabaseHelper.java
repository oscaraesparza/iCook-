package com.example.henry.mypantrydb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Henry on 10/21/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String PANTRY_DB = "Pantry.db";
    public static final String TABLE_PANTRY = "pantry_table";
    public static final String COL_1 = "ITEM";
    public static final String COL_2 = "QUANTITY";
    public DatabaseHelper(Context context) {
        super(context, PANTRY_DB, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_PANTRY +" (ITEM TEXT, QUANTITY INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_PANTRY);
        onCreate(db);

    }

    public boolean insertData(String item, int quantity){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, item);
        contentValues.put(COL_2, quantity);
        long result = db.insert(TABLE_PANTRY, null, contentValues);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+ TABLE_PANTRY,null);
        return res;
    }

}
