package com.example.projectfare;

import androidx.recyclerview.widget.RecyclerView;

public interface CallBackItemTouch {
    void itemTouchOnMove(int oldPosition,int newPosition);
    void onSwipe(RecyclerView.ViewHolder viewHolder, int position);

}
