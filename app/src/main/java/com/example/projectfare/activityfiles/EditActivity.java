package com.example.projectfare.activityfiles;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectfare.R;
import com.example.projectfare.databasefiles.EstablishmentDatabaseHandler;
import com.example.projectfare.databasefiles.ReviewsDatabaseHandler;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditActivity extends AppCompatActivity {
    private FloatingActionButton photoEditor;
    private CircleImageView imageE;
    private EditText name,locationE,review;
    private String reviewId;
    private Spinner type;
    private String[] estTypes = {"Cafe","Bar","Restaurant","Others"};
    private String spinItem;
    private EstablishmentDatabaseHandler establishmentDatabaseHandler;
    private ReviewsDatabaseHandler reviewsDatabaseHandler;
    private String estype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#E17D2D"));
        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        establishmentDatabaseHandler = new EstablishmentDatabaseHandler(this);
        reviewsDatabaseHandler = new ReviewsDatabaseHandler(this);

        photoEditor = (FloatingActionButton) findViewById(R.id.photoE);
        imageE = (CircleImageView) findViewById(R.id.imageE);
        name = (EditText) findViewById(R.id.nameE);
        type = (Spinner) findViewById(R.id.typeE);
        locationE = (EditText) findViewById(R.id.locationE);
        review = (EditText) findViewById(R.id.reviewE);

        Intent intent = getIntent();
        imageE.setImageBitmap(getImageBitmap(intent.getByteArrayExtra("imageBytes")));
        name.setText(intent.getStringExtra("estname"));
        estype = intent.getStringExtra("esttype");
        locationE.setText(intent.getStringExtra("estlocation"));
        review.setText(intent.getStringExtra("estreview"));
        reviewId = intent.getStringExtra("reviewId");


        ArrayAdapter<String> ad = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,getArray());
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(ad);
        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinItem = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public String[] getArray(){
        String[] s = null;
        if (estype.equals("Cafe")){
             s = new String[]{"Cafe", "Bar", "Restaurant", "Others"};
            return s;
        }else if (estype.equals("Bar")){
             s = new String[]{"Bar", "Cafe", "Restaurant", "Others"};
            return s;
        }else if (estype.equals("Restaurant")){
             s = new String[]{"Restaurant", "Bar", "Cafe", "Others"};
            return s;
        }else if (estype.equals("Others")){
             s = new String[]{"Others", "Restaurant", "Bar", "Cafe"};
            return s;
        }
        return s;
    }


    public void imagePickFloatBtnOnAction(View view){
        ImagePicker.with(this)
                .crop()	    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start();
    }


    public void saveButtonOnAction(View view){
        String estname = name.getText().toString();
        String estlocation = locationE.getText().toString();
        if (!name.getText().toString().isEmpty()){
            if (!review.getText().toString().isEmpty()){
                if(establishmentDatabaseHandler.updateEstablishmentData(estname,spinItem,estlocation,reviewId,imageE) ==  true){
                    Toast.makeText(this, "Establishment data updated successfully", Toast.LENGTH_SHORT).show();
                    if (reviewsDatabaseHandler.updateReviewComment(review.getText().toString(),reviewId) == true){
                        Toast.makeText(this, "Review updated successfully", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(this, "Review field to update", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(this, "failed to update establishment", Toast.LENGTH_SHORT).show();

                }
            }else {
                Toast.makeText(this, "Review field cannot be empty", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "Name field cannot be empty", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = data.getData();
        imageE.setImageURI(uri);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public Bitmap getImageBitmap(byte[] image){
        Bitmap bitmap = BitmapFactory.decodeByteArray(image,0,image.length);
        return bitmap;
    }
}