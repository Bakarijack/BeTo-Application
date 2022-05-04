package com.example.projectfare;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Menu extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{


    private boolean establishmentClicked = false;
    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment = new HomeFragment();
    EstablishmentFragment establishmentFragment = new EstablishmentFragment();
    LocationFragment locationFragment = new LocationFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //manipulate the action bar
        // Define ActionBar object
        ActionBar actionBar;
        actionBar = getSupportActionBar();

        // Define ColorDrawable object and parse color
        // using parseColor method
        // with color hash code as its parameter
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#E17D2D"));

        // Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);

        // methods to display the icon in the ActionBar
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home);
        getSupportFragmentManager().beginTransaction().replace( R.id.container, homeFragment).commit();


    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.navigation_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected( @NonNull MenuItem item ) {

        switch (item.getItemId()){
            case R.id.setting:
                Intent intent2 = new Intent(Menu.this,Setting.class);
                startActivity(intent2);
                break;
            case R.id.profile:
                Intent intent3 = new Intent(Menu.this,Profile.class);
                startActivity(intent3);
                break;

        }
            return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.home:
                establishmentClicked = false;
                getSupportFragmentManager().beginTransaction().replace( R.id.container, homeFragment).commit();
                return true;
            case R.id.establishment:
                establishmentClicked = true;
                getSupportFragmentManager().beginTransaction().replace( R.id.container,establishmentFragment).commit();
                return true;
            case R.id.mylocation:
                establishmentClicked = false;
                getSupportFragmentManager().beginTransaction().replace( R.id.container,locationFragment).commit();
                return true;
        }

        return  false;
    }

}