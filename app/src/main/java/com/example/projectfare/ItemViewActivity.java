package com.example.projectfare;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ItemViewActivity extends AppCompatActivity {
    private ImageButton moreMenu;
    private TextView estName,estType,estReview,revDate,estReviewer,estlocation;
    private TextView loveCount,likeCount,hateCOunt;
    private ImageButton heart,like,hate;
    private String reaction;
    private EstablishmentDatabaseHandler establishmentDatabaseHandler;
    private int reviewId;
    private int lovec,likec,hatec;
    private int myCount = 0;
    private ImageView estabImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //get the action bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_item_view);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.orange));
        }

        heart = (ImageButton) findViewById(R.id.heartButton);
        like = (ImageButton) findViewById(R.id.likeButton);
        hate = (ImageButton) findViewById(R.id.hateButton);
        estabImage = (ImageView) findViewById(R.id.estaImage1);
        Intent intent = getIntent();

        estName = (TextView) findViewById(R.id.estaName);
        estName.setText(intent.getStringExtra("estname"));

        estType = (TextView) findViewById(R.id.estaType);
        estType.setText(intent.getStringExtra("esttype"));

        estReview = (TextView) findViewById(R.id.estaReview);
        estReview.setText(intent.getStringExtra("estreview"));

        revDate = (TextView) findViewById(R.id.revDate);
        revDate.setText(intent.getStringExtra("revDate"));

        estReviewer = (TextView) findViewById(R.id.estaReviewer);
        estReviewer.setText(intent.getStringExtra("reviewer"));

        estlocation = (TextView) findViewById(R.id.estaLocation);
        estlocation.setText(intent.getStringExtra("estlocation"));

        loveCount = (TextView) findViewById(R.id.loveTv);
        //loveCount.setText(intent.getStringExtra("love"));
        lovec = Integer.parseInt(intent.getStringExtra("love"));
        loveCount.setText(String.valueOf(lovec));

        likeCount = (TextView) findViewById(R.id.likeTv);
        //likeCount.setText(intent.getStringExtra("like"));
        likec = Integer.parseInt(intent.getStringExtra("like"));
        likeCount.setText(String.valueOf(likec));

        hateCOunt = (TextView) findViewById(R.id.hateTv);
        //hateCOunt.setText(intent.getStringExtra("hate"));
        hatec = Integer.parseInt(intent.getStringExtra("hate"));
        hateCOunt.setText(String.valueOf(hatec));

        estabImage.setImageBitmap(getImageBitmap(intent.getByteArrayExtra("imageBytes")));

        reviewId = Integer.parseInt(intent.getStringExtra("reviewId"));

        establishmentDatabaseHandler = new EstablishmentDatabaseHandler(this);

        reaction = intent.getStringExtra("myReaction");
        setReaction();


        moreMenu = (ImageButton) findViewById(R.id.moreMenu);

        moreMenu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(ItemViewActivity.this,moreMenu);

                popupMenu.getMenuInflater().inflate(R.menu.pop_out_menu,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.addFavourite:
                                Toast.makeText(ItemViewActivity.this, "favourite clicked", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.hide:
                                Toast.makeText(ItemViewActivity.this, "Hide clicked", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.mute:
                                Toast.makeText(ItemViewActivity.this, "Mute clicked", Toast.LENGTH_SHORT).show();
                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
    }

    public Bitmap getImageBitmap(byte[] image){
        Bitmap bitmap = BitmapFactory.decodeByteArray(image,0,image.length);
        return bitmap;
    }

    public void heartButtonOnAction(View view){

        switch (reaction){
            case "love":
                reaction = "none";
                heart.setImageResource(R.drawable.love_action);
                lovec -= 1;
                loveCount.setText(String.valueOf(lovec));
                //establishmentDatabaseHandler.updateReaction(reaction,reviewId);
                updateIcon();
                break;
            case "like":
                likec -= 1;
                likeCount.setText(String.valueOf(likec));
                like.setImageResource(R.drawable.ic_like_svgrepo_com);

                reaction = "love";
                lovec += 1;
                loveCount.setText(String.valueOf(lovec));
                heart.setImageResource(R.drawable.ic_heartfill);
                //establishmentDatabaseHandler.updateReaction(reaction,reviewId);
                updateIcon();
                break;
            case "hate":
                hatec -= 1;
                hateCOunt.setText(String.valueOf(hatec));
                hate.setImageResource(R.drawable.ic_emoji_sad2);

                reaction = "love";
                lovec += 1;
                loveCount.setText(String.valueOf(lovec));
                heart.setImageResource(R.drawable.ic_heartfill);
                //establishmentDatabaseHandler.updateReaction(reaction,reviewId);
                updateIcon();
                break;
            case "none":
                reaction = "love";
                lovec += 1;
                loveCount.setText(String.valueOf(lovec));
                heart.setImageResource(R.drawable.ic_heartfill);
                //establishmentDatabaseHandler.updateReaction(reaction,reviewId);
                updateIcon();
                break;

        }
//        if (reaction.equals("love")){
//
//
//
//            //if (establishmentDatabaseHandler.updateReaction("none",reviewId) == false){
//                //Toast.makeText(this,"failed to update reaction",Toast.LENGTH_SHORT).show();
//           // }else{
//            //    Toast.makeText(this,"reaction updated successfully",Toast.LENGTH_SHORT).show();
//           // }
//        }else{
//            reaction = "love";
//            lovec += 1;
//            loveCount.setText(String.valueOf(lovec));
//            heart.setImageResource(R.drawable.ic_heartfill);
//
//            //if(establishmentDatabaseHandler.updateReaction("love",reviewId) == false){
//           //     Toast.makeText(this,"failed to update reaction",Toast.LENGTH_SHORT).show();
//           // }else{
//           //     Toast.makeText(this,"reaction updated",Toast.LENGTH_SHORT).show();
//
//            //}
    }


    public void likeButtonOnAction(View view){
        switch (reaction){
            case "like":
                reaction = "none";
                like.setImageResource(R.drawable.ic_like_svgrepo_com);
                likec -= 1;
                likeCount.setText(String.valueOf(likec));
               // establishmentDatabaseHandler.updateReaction(reaction,reviewId);
                updateIcon();
                break;
            case "love":
                lovec -= 1;
                loveCount.setText(String.valueOf(lovec));
                heart.setImageResource(R.drawable.love_action);

                reaction = "like";
                likec += 1;
                likeCount.setText(String.valueOf(likec));
                like.setImageResource(R.drawable.like_fill);
                //establishmentDatabaseHandler.updateReaction(reaction,reviewId);
                updateIcon();
                break;
            case "hate":
                hatec -= 1;
                hateCOunt.setText(String.valueOf(hatec));
                hate.setImageResource(R.drawable.ic_emoji_sad2);

                reaction = "like";
                likec += 1;
                likeCount.setText(String.valueOf(likec));
                like.setImageResource(R.drawable.like_fill);
                //establishmentDatabaseHandler.updateReaction(reaction,reviewId);
                updateIcon();
                break;
            case "none":
                reaction = "like";
                likec += 1;
                likeCount.setText(String.valueOf(likec));
                like.setImageResource(R.drawable.like_fill);
                //establishmentDatabaseHandler.updateReaction(reaction,reviewId);
                updateIcon();
                break;

        }
//        if (reaction.equals("like")){
//            reaction = "none";
//            like.setImageResource(R.drawable.ic_like_svgrepo_com);
//            likec -= 1;
//            likeCount.setText(String.valueOf(likec));
//        }else {
//            reaction = "like";
//            likec += 1;
//            likeCount.setText(String.valueOf(likec));
//            like.setImageResource(R.drawable.like_fill);
//        }
    }

    public void hateButtonOnAction(View view){
        switch (reaction){
            case "hate":
                reaction = "none";
                hate.setImageResource(R.drawable.ic_emoji_sad2);
                hatec -= 1;
                hateCOunt.setText(String.valueOf(hatec));
                //establishmentDatabaseHandler.updateReaction(reaction,reviewId);
                updateIcon();
                break;
            case "love":
                lovec -= 1;
                loveCount.setText(String.valueOf(lovec));
                heart.setImageResource(R.drawable.love_action);

                reaction = "hate";
                hatec += 1;
                hateCOunt.setText(String.valueOf(hatec));
                hate.setImageResource(R.drawable.sad_fill);
                //establishmentDatabaseHandler.updateReaction(reaction,reviewId);
                updateIcon();
                break;
            case "like":
                likec -= 1;
                likeCount.setText(String.valueOf(likec));
                like.setImageResource(R.drawable.ic_like_svgrepo_com);

                reaction = "hate";
                hatec += 1;
                hateCOunt.setText(String.valueOf(hatec));
                hate.setImageResource(R.drawable.sad_fill);
                //establishmentDatabaseHandler.updateReaction(reaction,reviewId);
                updateIcon();
                break;
            case "none":
                reaction = "hate";
                hatec += 1;
                hateCOunt.setText(String.valueOf(hatec));
                hate.setImageResource(R.drawable.sad_fill);
                updateIcon();
                break;
        }
//        if (reaction.equals("hate")){
//            reaction = "none";
//            hate.setImageResource(R.drawable.ic_emoji_sad2);
//            hatec -= 1;
//            hateCOunt.setText(String.valueOf(hatec));
//        }else{
//            reaction = "hate";
//            hatec += 1;
//            hateCOunt.setText(String.valueOf(hatec));
//            hate.setImageResource(R.drawable.sad_fill);
//        }
    }

    public void updateIcon(){
        if(establishmentDatabaseHandler.updateReaction(reaction,reviewId,lovec,likec,hatec)==false){
            Toast.makeText(this,"failed",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this,"successfully",Toast.LENGTH_SHORT).show();
        }
    }
    public void setReaction(){
        switch (reaction){
            case "love":
                heart.setImageResource(R.drawable.ic_heartfill);
                break;
            case "like":
                like.setImageResource(R.drawable.like_fill);
                break;
            case "hate":
                hate.setImageResource(R.drawable.sad_fill);
                break;
            case "none":
                heart.setImageResource(R.drawable.love_action);
                like.setImageResource(R.drawable.ic_like_svgrepo_com);
                hate.setImageResource(R.drawable.ic_emoji_sad2);
                break;
        }
    }

    public void updateReaction(){
    }

    public String getCount(String count,TextView tv){
        if (Integer.parseInt(count) == 0){
            tv.setVisibility(View.INVISIBLE);
            return "0";
        }else {
            return count;
        }
    }


}