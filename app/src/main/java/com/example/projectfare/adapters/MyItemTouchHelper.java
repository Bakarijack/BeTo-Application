package com.example.projectfare.adapters;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectfare.R;
import com.example.projectfare.deletefunctionclass.CallBackItemTouch;

public class MyItemTouchHelper extends androidx.recyclerview.widget.ItemTouchHelper.Callback {
    private  CallBackItemTouch callBackItemTouch;

    public MyItemTouchHelper(CallBackItemTouch callBackItemTouch) {
        this.callBackItemTouch = callBackItemTouch;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        final int dragFlags = androidx.recyclerview.widget.ItemTouchHelper.UP | androidx.recyclerview.widget.ItemTouchHelper.DOWN;
        final int swipeFlags = androidx.recyclerview.widget.ItemTouchHelper.START | androidx.recyclerview.widget.ItemTouchHelper.END;
        return makeMovementFlags(dragFlags,swipeFlags);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        callBackItemTouch.itemTouchOnMove(viewHolder.getAdapterPosition(),target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        callBackItemTouch.onSwipe(viewHolder, viewHolder.getAdapterPosition());
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (actionState == androidx.recyclerview.widget.ItemTouchHelper.ACTION_STATE_DRAG){
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }else {
            final View forigroungView = ((MyAdapter.MyViewHolder) viewHolder).cardView;
            getDefaultUIUtil().onDrawOver(c,recyclerView,forigroungView,dX,dY,actionState,isCurrentlyActive);
        }
    }

    @Override
    public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        //super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        if (actionState != androidx.recyclerview.widget.ItemTouchHelper.ACTION_STATE_DRAG){
            final View forigroungView = ((MyAdapter.MyViewHolder)viewHolder).cardView;
            getDefaultUIUtil().onDraw(c,recyclerView,forigroungView,dX,dY,actionState,isCurrentlyActive);
        }

    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        //super.clearView(recyclerView, viewHolder);
        final View forigroungView = ((MyAdapter.MyViewHolder)viewHolder).cardView;
        forigroungView.setBackgroundColor(ContextCompat.getColor(((MyAdapter.MyViewHolder)viewHolder).cardView.getContext(), R.color.orange));
        getDefaultUIUtil().clearView(forigroungView);
    }

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        if (viewHolder != null){
            final View forigroungView = ((MyAdapter.MyViewHolder)viewHolder).cardView;
            if (actionState == androidx.recyclerview.widget.ItemTouchHelper.ACTION_STATE_DRAG){
                forigroungView.setBackgroundColor(Color.LTGRAY);
            }
            getDefaultUIUtil().onSelected(forigroungView);
        }

    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }
}
