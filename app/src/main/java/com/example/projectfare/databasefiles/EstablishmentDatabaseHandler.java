package com.example.projectfare.databasefiles;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class EstablishmentDatabaseHandler extends DatabaseHandler {
    public EstablishmentDatabaseHandler(Context context) {
        super(context);
    }

    public boolean insertEstablishmentData(String name, String estabType, String footType, String estabLocation, int reviewid, CircleImageView circleImageView){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("name",name);
        values.put("estType",estabType);
        values.put("foodType",footType);
        values.put("estLocation",estabLocation);
        values.put("reviewId",reviewid);
        values.put("loveCount","0");
        values.put("likeCount","0");
        values.put("hateCount","0");
        values.put("reaction","none");
        values.put("estImage",circleImageViewToByte(circleImageView));
        values.put("myEstab",1);

        long results = db.insert("establishmentData",null,values);

        if (results == -1){
            return false;
        }else {
            return true;
        }
    }

    private byte[] circleImageViewToByte(CircleImageView circleImageView) {
        Bitmap bitmap = ((BitmapDrawable)circleImageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,80,stream);
        byte[] bytes = stream.toByteArray();

        return bytes;
    }

    public void deleteEstablishment(int reviewId){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete("establishmentData","reviewId = ?",new String[]{String.valueOf(reviewId)});

        db.close();
    }


    public boolean isEstablishmentExist(String ename,String elocation){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from establishmentData where name = ? and estLocation = ?", new String[] {ename,elocation});

        if(cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public boolean updateReaction(String reaction, int reviewId,int lovec,int likec,int hatec){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        long result;
        switch (reaction){
            case "love":
                values.put("reaction",reaction);
                values.put("loveCount",lovec);
                values.put("likeCount",likec);
                values.put("hateCount",hatec);
                result = db.update("establishmentData",values,"reviewId = ?",new String[]{String.valueOf(reviewId)});
                break;
            case "like":
                values.put("reaction",reaction);
                values.put("loveCount",lovec);
                values.put("likeCount",likec);
                values.put("hateCount",hatec);
                result = db.update("establishmentData",values,"reviewId = ?", new String[]{String.valueOf(reviewId)});
                break;
            case "hate":
                values.put("reaction",reaction);
                values.put("loveCount",lovec);
                values.put("likeCount",likec);
                values.put("hateCount",hatec);
                result = db.update("establishmentData",values,"reviewId = ?",new String[]{String.valueOf(reviewId)});
                break;
            default:
                values.put("reaction","none");
                values.put("loveCount",lovec);
                values.put("likeCount",likec);
                values.put("hateCount",hatec);
                result = db.update("establishmentData",values,"reviewId = ?",new String[]{String.valueOf(reviewId)});
        }
        
        if (result == -1){
            return false;
        }else {
            return true;
        }

    }

    public boolean updateEstablishmentData(String name,String type,String locatione,String reviewId,CircleImageView circleImageView){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        if (locatione.isEmpty()){
            locatione = "Unspecified";
        }

        values.put("name",String.valueOf(name));
        values.put("estType",String.valueOf(type));
        values.put("estLocation",String.valueOf(locatione));
        values.put("estImage",String.valueOf(circleImageViewToByte(circleImageView)));

        long result = db.update("establishmentData",values,"reviewId=?",new String[]{String.valueOf(reviewId)});

        if (result == -1){
            return false;
        }else {
            return true;
        }
    }


    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from establishmentData",null);
        return cursor;
    }

    public Cursor getMyEstablishments(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from establishmentData where myEstab = '1'",null);
        return cursor;
    }

}
