package com.example.projectfare;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements Filterable{
    private Context context;
    private ArrayList<EstablishmentModal> establishmentModalArrayList,establishmentModalArrayListAll;


    public MyAdapter(Context context,ArrayList<EstablishmentModal> establishmentModalArrayList){
        this.context = context;
        this.establishmentModalArrayList = establishmentModalArrayList;
        this.establishmentModalArrayListAll = new ArrayList<>(establishmentModalArrayList);
    }


    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.estabentry,parent,false);
      //  v.setOnClickListener(monClickListener);
        return new MyViewHolder(v);
    }

    public Bitmap getImageBitmap(byte[] image){
        Bitmap bitmap = BitmapFactory.decodeByteArray(image,0,image.length);
        return bitmap;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        holder.estname.setText(String.valueOf(establishmentModalArrayList.get(position).getEstablishmentName()));
        holder.esttype.setText(String.valueOf(establishmentModalArrayList.get(position).getEstablishmentType()));
        holder.estreview.setText(String.valueOf(establishmentModalArrayList.get(position).getEstablishmentLocation()));
        holder.estImageView.setImageBitmap(getImageBitmap(establishmentModalArrayList.get(position).getImageBytes()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context, "item clicked is "+establishmentModalArrayList.get(holder.getAdapterPosition()).getEstablishmentName(), Toast.LENGTH_SHORT).show();
                
                Intent intent = new Intent(holder.itemView.getContext(),ItemViewActivity.class);
                intent.putExtra("estname",establishmentModalArrayList.get(holder.getAdapterPosition()).getEstablishmentName());
                intent.putExtra("esttype",establishmentModalArrayList.get(holder.getAdapterPosition()).getEstablishmentType());
                intent.putExtra("estreview",establishmentModalArrayList.get(holder.getAdapterPosition()).getReviewComment());
                intent.putExtra("estlocation",establishmentModalArrayList.get(holder.getAdapterPosition()).getEstablishmentLocation());
                intent.putExtra("revDate",establishmentModalArrayList.get(holder.getAdapterPosition()).getReviewDate());
                intent.putExtra("reviewer",establishmentModalArrayList.get(holder.getAdapterPosition()).getReviewer());
                intent.putExtra("love",String.valueOf(establishmentModalArrayList.get(holder.getAdapterPosition()).getLoveCount()));
                intent.putExtra("like",String.valueOf(establishmentModalArrayList.get(holder.getAdapterPosition()).getLikeCount()));
                intent.putExtra("hate",String.valueOf(establishmentModalArrayList.get(holder.getAdapterPosition()).getHateCount()));
                intent.putExtra("myReaction",establishmentModalArrayList.get(holder.getAdapterPosition()).getMyReaction());
                intent.putExtra("reviewId",String.valueOf(establishmentModalArrayList.get(holder.getAdapterPosition()).getReviewId()));
                intent.putExtra("imageBytes",establishmentModalArrayList.get(holder.getAdapterPosition()).getImageBytes());
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        //return name.size();
        return establishmentModalArrayList.size();
    }

    @Override
    public Filter getFilter() {
        return establishFilter;
    }

    private Filter establishFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<EstablishmentModal> filteredList = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0){
                filteredList.addAll(establishmentModalArrayListAll);
            }else{
                String filteredPattern = charSequence.toString().toLowerCase().trim();
                for(EstablishmentModal modal : establishmentModalArrayListAll){
                    if (modal.getEstablishmentName().toLowerCase().contains(filteredPattern)){
                        filteredList.add(modal);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return  results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            establishmentModalArrayList.clear();
            establishmentModalArrayList.addAll((List)filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView estname,esttype,estreview;
        CircleImageView estImageView;
        LinearLayout backLin,contentLin;
        CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            estname = itemView.findViewById(R.id.tvname);
            esttype = itemView.findViewById(R.id.tvtype);
            estreview = itemView.findViewById(R.id.tvreview);
            estImageView = itemView.findViewById(R.id.estaImageV);
            backLin = itemView.findViewById(R.id.liniear1);
            contentLin = itemView.findViewById(R.id.linear2);
            cardView = itemView.findViewById(R.id.contentHolder);
        }
    }

    public void filterList(List<EstablishmentModal> filteredList){
        establishmentModalArrayList = (ArrayList<EstablishmentModal>) filteredList;
        notifyDataSetChanged();

    }

    public void removeItem(int position){
        establishmentModalArrayList.remove(position);

        notifyItemRemoved(position);
    }

    public void restoreItem(EstablishmentModal modal, int position){
        establishmentModalArrayList.add(position,modal);
        notifyItemInserted(position);
    }
}
