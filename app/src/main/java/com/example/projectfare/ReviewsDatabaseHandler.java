package com.example.projectfare;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ReviewsDatabaseHandler extends DatabaseHandler{
    public ReviewsDatabaseHandler(Context context) {
        super(context);
    }

    public boolean insertReviewData(String reviewDate, String reviewer,String review){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("reviewDate",reviewDate);
        values.put("reviewer",reviewer);
        values.put("review",review);

        long results = db.insert("reviews",null,values);

        if (results == -1){
            return false;
        }else {
            return true;
        }
    }

    public void deleteReviewData(int reviewId){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete("reviews","reviewId = ?",new String[]{String.valueOf(reviewId)});
        db.close();
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from reviews",null);
        return cursor;
    }

    public Cursor getReviewDataByTime(String cDate){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from reviews where reviewDate = '"+cDate+"'",null);
        return cursor;
    }

    public Cursor getReviewer(int reviewId){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from reviews where reviewId = '"+reviewId+"'",null);
        return cursor;
    }

}
