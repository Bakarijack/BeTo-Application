package com.example.projectfare.activityfiles;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectfare.R;
import com.example.projectfare.modalclasses.ReviewModalClass;
import com.example.projectfare.modalclasses.UserModalClass;
import com.example.projectfare.databasefiles.EstablishmentDatabaseHandler;
import com.example.projectfare.databasefiles.ReviewsDatabaseHandler;
import com.example.projectfare.databasefiles.UserDatabaseHandler;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddEstablishment extends AppCompatActivity {
    private Button addButton,cancelButton;
    private EditText establishmentName,establishmentLocation,foodType,review;
    private Spinner establishmentType;
    private RelativeLayout relayData,relayPhoto;
    private ImageButton nextButton,backButton;
    private FloatingActionButton imgPick;
    private ImageButton imgView;
    private CircleImageView circleImageView;
    private RelativeLayout relativeLayout1,relativeLayoutLoader;

    Handler handler1 = new Handler();
    Runnable runnable1 =  new Runnable() {
        @Override
        public void run() {
            relativeLayout1.setVisibility(View.INVISIBLE);
            relativeLayoutLoader.setVisibility(View.VISIBLE);
        }
    };

    Handler handler2 = new Handler();
    Runnable runnable2 = new Runnable() {
        @Override
        public void run() {
            relativeLayoutLoader.setVisibility(View.INVISIBLE);
            relativeLayout1.setVisibility(View.VISIBLE);
        }
    };


    private String[] estTypes = {"Cafe","Bar","Restaurant","Others"};
    private String spinItem;

    //private DatabaseHandler databaseHandler;
    private EstablishmentDatabaseHandler establishmentDatabaseHandler;
    private UserDatabaseHandler userDatabaseHandler;
    private ReviewsDatabaseHandler reviewsDatabaseHandler;

    private UserModalClass userModalClass;
    private ReviewModalClass reviewModalClass;

    private String food;
    private String elocation;
    private String ename;
    private String rv;

    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_establishment);

        ActionBar actionBar;
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#E17D2D"));
        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        establishmentName = (EditText) findViewById(R.id.establishmentName);
        establishmentType = (Spinner) findViewById(R.id.establishmentType);
        establishmentLocation = (EditText) findViewById(R.id.establishmentLocation);
        foodType = (EditText) findViewById(R.id.foodType);
        review = (EditText) findViewById(R.id.establishmentReview);
        addButton = (Button) findViewById(R.id.addButton);
        nextButton = (ImageButton) findViewById(R.id.nextButton);
        backButton = (ImageButton) findViewById(R.id.backButton);
        relayData = (RelativeLayout) findViewById(R.id.estdata);
        relayPhoto = (RelativeLayout) findViewById(R.id.estaPhoto);
        imgPick = (FloatingActionButton) findViewById(R.id.imagePicker);
        circleImageView = (CircleImageView) findViewById(R.id.photoV);

        //databaseHandler = new DatabaseHandler(this);
        establishmentDatabaseHandler = new EstablishmentDatabaseHandler(this);
        userDatabaseHandler = new UserDatabaseHandler(this);
        reviewsDatabaseHandler = new ReviewsDatabaseHandler(this);


        ArrayAdapter<String> ad = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,estTypes);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        establishmentType.setAdapter(ad);
        establishmentType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinItem = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        //ratingBar.setRating(5);
        //float rating = ratingBar.getRating();

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                Toast.makeText(AddEstablishment.this, String.valueOf(ratingBar.getRating()),Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void imagePickFloatBtnOnAction(View view){
        ImagePicker.with(this)
                .crop()	    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start();
    }

    public void viewAnimation1(){
        relativeLayout1 = (RelativeLayout) findViewById(R.id.relay1);
        relativeLayoutLoader = (RelativeLayout) findViewById(R.id.gifLoader);

        handler1.postDelayed(runnable1,0);
        handler2.postDelayed(runnable2,1150);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = data.getData();
        circleImageView.setImageURI(uri);
    }

    public void nextButtonOnAction(View view){
        relayPhoto.setVisibility(View.INVISIBLE);
        relayData.setVisibility(View.VISIBLE);
    }

    public void backButtonOnAction(View view){
        relayData.setVisibility(View.INVISIBLE);
        relayPhoto.setVisibility(View.VISIBLE);
    }

    public void addButtonOnAction(View view){
        ename = establishmentName.getText().toString();
        rv = review.getText().toString();
        if (!ename.isEmpty()){
            if (!spinItem.isEmpty()){
                if (!rv.isEmpty()){
                    //insertReviewData();
                    insertEstData();
                }else {
                    Toast.makeText(AddEstablishment.this,"Review is required",Toast.LENGTH_SHORT).show();
                    return;
                }
            }else{
                Toast.makeText(AddEstablishment.this,"Establishment type required",Toast.LENGTH_SHORT).show();
                return;
            }
        }else {
            Toast.makeText(AddEstablishment.this,"Establishment name required",Toast.LENGTH_SHORT).show();
            return;
        }
    }


    public void insertEstData(){
        food = foodType.getText().toString();
        elocation = establishmentLocation.getText().toString();
        ename = establishmentName.getText().toString();
        rv = review.getText().toString();

        if (elocation.isEmpty()){
            elocation = "Unspecified";
        }
        if (food.isEmpty()){
            food = "Unspecified";
        }

        insertReviewData();
        getReviewData();

        if (establishmentDatabaseHandler.insertEstablishmentData(ename, spinItem, food, elocation, reviewModalClass.getId(),circleImageView) == true) {
            viewAnimation1();
            Toast.makeText(AddEstablishment.this, "Establishment added successfully", Toast.LENGTH_SHORT).show();
            establishmentName.setText("");
            establishmentLocation.setText("");
            foodType.setText("");
            review.setText("");
            circleImageView.setImageResource(R.drawable.photod);
            return;
        } else {
            Toast.makeText(AddEstablishment.this, "Failed add establishment", Toast.LENGTH_SHORT).show();
            return;
        }

    }

    private String cDate;
    public String getCurrentDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        String currentDate = dateFormat.format(date);
        cDate = currentDate;

        return currentDate;
    }


    public void getReviewData(){
        Cursor cursor = reviewsDatabaseHandler.getReviewDataByTime(cDate);

        if (cursor.getCount() == 0){
            Toast.makeText(AddEstablishment.this,"no data found",Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()) {
                if (cursor.getCount() == 1) {
                    reviewModalClass = new ReviewModalClass(
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3)
                    );
                }
            }
        }
    }

    public void getUserData(){
        Cursor cursor = userDatabaseHandler.getData();

        if (cursor.getCount() == 0){
            Toast.makeText(AddEstablishment.this,"No user exist",Toast.LENGTH_SHORT).show();
            return;
        }else {
            if (cursor.moveToFirst())
                userModalClass = new UserModalClass(cursor.getString(1),cursor.getString(2));
        }
    }

    public void insertReviewData(){
        getUserData();

        if (reviewsDatabaseHandler.insertReviewData(getCurrentDate(),userModalClass.getName(),review.getText().toString()) == false){
            Toast.makeText(AddEstablishment.this,"failed to add review data",Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
//                Intent intent = new Intent(AddEstablishment.this,Menu.class);
//                startActivity(intent);
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}