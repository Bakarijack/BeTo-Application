package com.example.projectfare;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyEstablishmentListAdaptor extends RecyclerView.Adapter<MyEstablishmentListAdaptor.MyListViewHolder> {
    private Context context;
    private ArrayList<EstablishmentModal> myEstablishmentArrayList;

    public MyEstablishmentListAdaptor(Context context, ArrayList<EstablishmentModal> myEstablishmentArrayList){
        this.context = context;
        this.myEstablishmentArrayList = myEstablishmentArrayList;
    }
    @NonNull
    @Override
    public MyEstablishmentListAdaptor.MyListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.myestab_entity,parent,false);
        return new MyListViewHolder(v);
    }


    public Bitmap getImageBitmap(byte[] image){
        Bitmap bitmap = BitmapFactory.decodeByteArray(image,0,image.length);
        return bitmap;
    }

    @Override
    public void onBindViewHolder(@NonNull MyListViewHolder holder, int position) {
        holder.myestabImage.setImageBitmap(getImageBitmap(myEstablishmentArrayList.get(position).getImageBytes()));
        holder.myestabName.setText(myEstablishmentArrayList.get(position).getEstablishmentName());
        holder.myestabReview.setText(myEstablishmentArrayList.get(position).getReviewComment());
        holder.heartCV.setText(myEstablishmentArrayList.get(position).getLoveCount());
        holder.likeCV.setText(myEstablishmentArrayList.get(position).getLikeCount());
        holder.sadCV.setText(myEstablishmentArrayList.get(position).getHateCount());
        holder.mymenuNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context,holder.mymenuNav);

                popupMenu.getMenuInflater().inflate(R.menu.my_menu_pop,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.edit:
                                Toast.makeText(context, "edit clicked", Toast.LENGTH_SHORT).show();
                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return myEstablishmentArrayList.size();
    }

    public void filterList(List<EstablishmentModal> filteredList) {
        myEstablishmentArrayList = (ArrayList<EstablishmentModal>) filteredList;
        notifyDataSetChanged();
    }

    public class MyListViewHolder extends RecyclerView.ViewHolder {
        TextView myestabName,myestabReview,heartCV,likeCV,sadCV;
        ImageButton myheart,mylike,mysad,mymenuNav;
        CircleImageView myestabImage;
        public MyListViewHolder(@NonNull View itemView) {
            super(itemView);
            myestabName = itemView.findViewById(R.id.myestabname);
            myestabReview = itemView.findViewById(R.id.myestbReview);
            myheart = itemView.findViewById(R.id.heartButtonView);
            heartCV = itemView.findViewById(R.id.loveTvView);
            likeCV = itemView.findViewById(R.id.likeTvView);
            sadCV = itemView.findViewById(R.id.hateTvView);
            mymenuNav = itemView.findViewById(R.id.moreMenuView);
            myestabImage = itemView.findViewById(R.id.myestabImage);
        }
    }
}
