package sercandevops.com.notsepeteekle.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

public class SimpleTouchCallBack extends ItemTouchHelper.Callback {

    SwipeListener mSwipeListener;

    public SimpleTouchCallBack(SwipeListener mSwipeListener) {
        this.mSwipeListener = mSwipeListener;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {

        if(viewHolder.getItemViewType() == 0){
            return makeMovementFlags(0,ItemTouchHelper.END);
        }else{
            return makeMovementFlags(0,0);
        }

    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
        return false;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        mSwipeListener.onSwipe(viewHolder.getAdapterPosition());
    }


}
