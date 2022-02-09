package com.kanifanath.inshorts.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NewsBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "news.db";
    public NewsBaseHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String str = "create table " + NewsDbSchema.NewsTable.tableName +
                "( _id integer primary key autoincrement, " +
                NewsDbSchema.NewsTable.Cols.heading + " text, " +
                NewsDbSchema.NewsTable.Cols.detail + " text, " +
                NewsDbSchema.NewsTable.Cols.links + " text, " +
                NewsDbSchema.NewsTable.Cols.linkWords + " text , " +
                NewsDbSchema.NewsTable.Cols.bitImage + " BLOB )";
        db.execSQL(str);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
