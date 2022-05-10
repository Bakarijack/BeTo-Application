package com.example.projectfare.databasefiles;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UserDatabaseHandler extends DatabaseHandler {
    public UserDatabaseHandler(Context context) {
        super(context);
    }

    public boolean isUserExist(String phoneNumber){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from user where phoneNumber = ?", new String[] {phoneNumber});

        if(cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public boolean isAnyUserExist(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from user",null);

        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public boolean insertUserData(String name,String phoneNumber){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("name",name);
        values.put("phoneNumber",phoneNumber);


        long results = db.insert("user",null,values);

        if (results == -1){
            return false;
        }else {
            return true;
        }
    }

    public boolean updateUserData(String name,String newPhone,String oldPhone){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("name",name);
        values.put("phoneNumber",newPhone);

        long result = db.update("user",values," phoneNumber = ?",new String[]{oldPhone});

        if (result == -1){
            return false;
        }else {
            return true;
        }
    }


    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from user",null);
        return cursor;
    }
}
