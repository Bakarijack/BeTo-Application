package com.example.projectfare.activityfiles;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.projectfare.R;
import com.example.projectfare.databasefiles.UserDatabaseHandler;
import com.example.projectfare.modalclasses.UserModalClass;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity {
    private CircleImageView userImage;
    private FloatingActionButton addPic;
    private UserDatabaseHandler userDatabaseHandler;
    private UserModalClass userModalClass;
    private EditText username,phone;
    private AppCompatButton saveButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ActionBar actionBar;
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#E17D2D"));
        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.orange));
        }

        userDatabaseHandler = new UserDatabaseHandler(this);

        userImage = (CircleImageView) findViewById(R.id.userImage);
        username = (EditText) findViewById(R.id.userName);
        phone = (EditText) findViewById(R.id.phone);
        saveButton = (AppCompatButton) findViewById(R.id.saveButton);
        getUserData();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userDatabaseHandler.updateUserData(username.getText().toString(),phone.getText().toString(),userModalClass.getPhoneNumber()) == true){
                    Toast.makeText(Profile.this,"Successfully updated",Toast.LENGTH_SHORT);
                }else {
                    Toast.makeText(Profile.this,"Failed to updated",Toast.LENGTH_SHORT);
                }
            }
        });
    }

    public void pickImage(View view){
        ImagePicker.with(this)
                .crop()	    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = data.getData();
        userImage.setImageURI(uri);
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


    public void getUserData(){
        Cursor cursor = userDatabaseHandler.getData();

        if (cursor.getCount() == 0){
            Toast.makeText(Profile.this,"No user exist",Toast.LENGTH_SHORT).show();
            return;
        }else {
            if (cursor.moveToFirst())
                userModalClass = new UserModalClass(cursor.getString(1),cursor.getString(2));
        }

        username.setText(userModalClass.getName());
        phone.setText(userModalClass.getPhoneNumber());
    }

}