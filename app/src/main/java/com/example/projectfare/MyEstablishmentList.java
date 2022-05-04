package com.example.projectfare;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyEstablishmentList extends AppCompatActivity {
    private ReviewsDatabaseHandler reviewsDatabaseHandler;
    private EstablishmentDatabaseHandler establishmentDatabaseHandler;
    private ArrayList<EstablishmentModal> myEstablishmentModalArrayList;
    private MyEstablishmentListAdaptor myEstablishmentListAdaptor;
    private RecyclerView recyclerView;
    private TextView defaultText;
    private RVEMptyObserver observer;
    private SearchView searchViewV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myestablishment_list);


        ActionBar actionBar;
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#E17D2D"));
        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        defaultText = (TextView) findViewById(R.id.mydefaultText);
        searchViewV = (SearchView) findViewById(R.id.searchV);

        reviewsDatabaseHandler = new ReviewsDatabaseHandler(MyEstablishmentList.this);
        establishmentDatabaseHandler =  new EstablishmentDatabaseHandler(MyEstablishmentList.this);

        recyclerView = (RecyclerView)findViewById(R.id.myestabRecycleView);
        myEstablishmentModalArrayList = new ArrayList<>();
        myEstablishmentModalArrayList.clear(); //test added

        myEstablishmentListAdaptor = new MyEstablishmentListAdaptor(MyEstablishmentList.this,myEstablishmentModalArrayList);

        recyclerView.setAdapter(myEstablishmentListAdaptor);
        recyclerView.setLayoutManager(new LinearLayoutManager(MyEstablishmentList.this));

        observer = new RVEMptyObserver(recyclerView,defaultText);
        myEstablishmentListAdaptor.registerAdapterDataObserver(observer);
        displayMyEstablishments();

        searchViewV.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });
    }

    private void filter(String newText) {
        List<EstablishmentModal> filteredList = new ArrayList<>();

        for(EstablishmentModal modal : myEstablishmentModalArrayList){
            if (modal.getEstablishmentName().toLowerCase().contains(newText.toLowerCase())){
                filteredList.add(modal);
            }
        }

        myEstablishmentListAdaptor.filterList(filteredList);
    }

    public String getReviewer(){
        Cursor cursor = reviewsDatabaseHandler.getReviewer(id);
        String name = null;
        if (cursor.getCount() == 0){
            Toast.makeText(this,"no data found",Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()) {
                if (cursor.getCount() == 1) {
                    name = cursor.getString(2);
                    date = cursor.getString(1);
                    comment = cursor.getString(3);
                }
            }
        }

        return name;
    }

    private int id;
    private String date;
    private String comment;

    private void displayMyEstablishments() {
        myEstablishmentModalArrayList.clear();
        Cursor cursor = establishmentDatabaseHandler.getMyEstablishments();

        if (cursor.getCount() == 0){
            return;
        }else {
            while (cursor.moveToNext()){
                id = cursor.getInt(5);
                myEstablishmentModalArrayList.add(new EstablishmentModal(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getInt(5),
                        getReviewer(),
                        date,
                        comment,
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8),
                        cursor.getString(9),
                        cursor.getBlob(10)
                ));
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // establishmentModalArrayList.clear();
        displayMyEstablishments();
        myEstablishmentListAdaptor.notifyDataSetChanged();
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
}