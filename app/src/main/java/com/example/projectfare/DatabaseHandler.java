package com.example.projectfare;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {


    public DatabaseHandler(Context context) {
        super(context, "javaProject.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table establishmentData(id INTEGER primary key AUTOINCREMENT,name TEXT,estType TEXT,foodType TEXT,estLocation TEXT,reviewId INTEGER,loveCount INTEGER,likeCount INTEGER,hateCount INTEGER,reaction TEXT,estImage blob,myEstab INTEGER DEFAULT 0)");
        sqLiteDatabase.execSQL("create table reviews(reviewId INTEGER primary key AUTOINCREMENT,reviewDate TEXT,reviewer TEXT,review TEXT)");
        sqLiteDatabase.execSQL("create table comments(commentId INTEGER primary key AUTOINCREMENT,commenter TEXT,comment TEXT,commentDate TEXT,emojiStatus TEXT)");
        sqLiteDatabase.execSQL("create table user(userId INTEGER primary key AUTOINCREMENT, name TEXT default null,phoneNumber TEXT NOT NULL)");

    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists establishmentData");
        sqLiteDatabase.execSQL("drop table if exists user");
        sqLiteDatabase.execSQL("drop table if exists reviews");
        sqLiteDatabase.execSQL("drop table if exists comments");
    }
}
