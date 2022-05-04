package com.example.projectfare;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;


public class EstablishmentFragment extends Fragment implements CallBackItemTouch{
    private FloatingActionButton addButton,favouriteButton,establishmentButton;
    private TextView favouriteTv,establishmentTv,defaultText;
    private Boolean isAllFabsVisible;
    private ReviewsDatabaseHandler reviewsDatabaseHandler;
    private  ReviewModalClass reviewModalClass;
    private RelativeLayout relativeLayout;
    private RVEMptyObserver observer;
    private androidx.appcompat.widget.SearchView searchView;


    private RecyclerView recyclerView;
   // private DatabaseHandler databaseHandler;
    private EstablishmentDatabaseHandler establishmentDatabaseHandler;
    private ArrayList<String> name,type,review;
    private ArrayList<EstablishmentModal> establishmentModalArrayList;

    private MyAdapter adapter;

    public EstablishmentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_establishment, container, false);

        //databaseHandler = new DatabaseHandler(getContext());
        establishmentDatabaseHandler = new EstablishmentDatabaseHandler(getContext());
        reviewsDatabaseHandler = new ReviewsDatabaseHandler(getContext());

        recyclerView = view.findViewById(R.id.recyV);
        defaultText = (TextView) view.findViewById(R.id.defaultText);

        ItemTouchHelper.Callback callback = new MyItemTouchHelper(this);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);
        establishmentModalArrayList = new ArrayList<>();

       // adapter = new MyAdapter(getContext(),name,type,review);
        adapter = new MyAdapter(getContext(),establishmentModalArrayList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        observer = new RVEMptyObserver(recyclerView,defaultText);
        adapter.registerAdapterDataObserver(observer);
        displayData();

        //ids for the floating buttons
        addButton = (FloatingActionButton) view.findViewById(R.id.addButton);
        favouriteButton = (FloatingActionButton) view.findViewById(R.id.favorite);
        establishmentButton = (FloatingActionButton) view.findViewById(R.id.addEstablishment);
        favouriteTv = (TextView) view.findViewById(R.id.favoriText);
        establishmentTv = (TextView) view.findViewById(R.id.establishmentText);
        relativeLayout = (RelativeLayout) view.findViewById(R.id.mainLayout);
        searchView = (androidx.appcompat.widget.SearchView) view.findViewById(R.id.searchEngine);

        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
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

        addButton.setAlpha(0f);
        addButton.animate().alpha(1f).setDuration(1500);


        favouriteButton.setVisibility(View.GONE);
        establishmentButton.setVisibility(View.GONE);
        favouriteTv.setVisibility(View.GONE);
        establishmentTv.setVisibility(View.GONE);


        isAllFabsVisible = false;

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isAllFabsVisible){
                    addButton.animate().setDuration(1000);
                    addButton.setImageResource(R.drawable.ic_multiply);
                    favouriteButton.show();
                    establishmentButton.show();
                    favouriteTv.setVisibility(View.VISIBLE);
                    establishmentTv.setVisibility(View.VISIBLE);

                    isAllFabsVisible = true;
                }else{
                    addButton.animate().setDuration(1000);
                    addButton.setImageResource(R.drawable.ic_plus4);
                    favouriteButton.hide();
                    establishmentButton.hide();
                    favouriteTv.setVisibility(View.GONE);
                    establishmentTv.setVisibility(View.GONE);

                    isAllFabsVisible = false;
                }
            }
        });

        establishmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),AddEstablishment.class);
                startActivity(intent);
            }
        });

        favouriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //displayData();
                adapter.notifyDataSetChanged();
                Intent intent = new Intent(getContext(), MyEstablishmentList.class);
                startActivity(intent);

            }
        });

        return view;
    }

    public void filter(String newText) {
        List<EstablishmentModal> filteredList = new ArrayList<>();

        for(EstablishmentModal modal : establishmentModalArrayList){
            if (modal.getEstablishmentName().toLowerCase().contains(newText.toLowerCase())){
                filteredList.add(modal);
            }
        }

        adapter.filterList(filteredList);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.navigation_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected( @NonNull MenuItem item ) {

        switch (item.getItemId()){

        }
        return super.onOptionsItemSelected(item);
    }

    public String getReviewer(){
        Cursor cursor = reviewsDatabaseHandler.getReviewer(id);
        String name = null;
        if (cursor.getCount() == 0){
            Toast.makeText(getContext(),"no data found",Toast.LENGTH_SHORT).show();
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
    private void displayData() {
        establishmentModalArrayList.clear();
        Cursor cursor = establishmentDatabaseHandler.getData();

        if (cursor.getCount() == 0){
            return;
        }else {
            while (cursor.moveToNext()){
                id = cursor.getInt(5);
                establishmentModalArrayList.add(new EstablishmentModal(
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
        displayData();
        deleteData();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void itemTouchOnMove(int oldPosition, int newPosition) {
        establishmentModalArrayList.add(newPosition,establishmentModalArrayList.remove(oldPosition));
        adapter.notifyItemMoved(oldPosition,newPosition);
    }

    public void deleteData(){
        if (!establishmentModalArrayList.contains(modal) && modal != null ){
            establishmentDatabaseHandler.deleteEstablishment(modal.getReviewId());
            reviewsDatabaseHandler.deleteReviewData(modal.getReviewId());
            establishmentModalArrayList.clear();
            displayData();
            modal = null;
        }
    }

    private EstablishmentModal modal = null;
    @Override
    public void onSwipe(RecyclerView.ViewHolder viewHolder, int position) {
        String name = establishmentModalArrayList.get(viewHolder.getAdapterPosition()).getEstablishmentName();
        final  EstablishmentModal deletedModal = establishmentModalArrayList.get(viewHolder.getAdapterPosition());
        final int deletedModalIndex = viewHolder.getAdapterPosition();
        modal = deletedModal;
        //remove the item from the recyclerview
        adapter.removeItem(viewHolder.getAdapterPosition());

        //showing the undo message
        Snackbar snackbar = Snackbar.make(relativeLayout,name+" Removed..!",Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.WHITE);
        snackbar.show();
        snackbar.setAction("Undo", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.restoreItem(deletedModal,deletedModalIndex);
                modal = null;
            }
        });

    }
}