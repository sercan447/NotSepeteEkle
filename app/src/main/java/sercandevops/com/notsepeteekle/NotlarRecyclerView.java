package sercandevops.com.notsepeteekle;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class NotlarRecyclerView extends RecyclerView {

    List<View> elemanYoksaSaklanacaklar = Collections.emptyList();
    List<View> elemanYoksaGosterilecekler = Collections.emptyList();


    AdapterDataObserver observer = new AdapterDataObserver() {
        @Override
        public void onChanged() {
                viewGosterGizle();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            viewGosterGizle();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, @Nullable Object payload) {
            viewGosterGizle();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            viewGosterGizle();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            viewGosterGizle();
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            viewGosterGizle();
        }
    };

    public NotlarRecyclerView(@NonNull Context context) {
        super(context);
    }

    public NotlarRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NotlarRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        if(adapter != null){
            adapter.registerAdapterDataObserver(observer);

        }

        observer.onChanged();
    }

    public void egerElemanYoksaSaklanacaklar(View... saklanacakView){
        elemanYoksaSaklanacaklar = Arrays.asList(saklanacakView);
    }
    public void egerElemanYoksaGosterilecekler(View... gosterilecekView){
        elemanYoksaGosterilecekler = Arrays.asList(gosterilecekView);
    }

    private void viewGosterGizle(){


        if(getAdapter() != null){

            if(getAdapter().getItemCount() == 0){

               for(View view : elemanYoksaSaklanacaklar){
                    view.setVisibility(View.GONE);
                }

                setVisibility(View.GONE);

                for (View view : elemanYoksaGosterilecekler){
                    view.setVisibility(View.VISIBLE);
                }


            }else /*if(getAdapter().getItemCount()  > 1)*/{


                for(View view : elemanYoksaSaklanacaklar){
                   view.setVisibility(View.VISIBLE);

                }

                setVisibility(View.VISIBLE);

                for (View view : elemanYoksaGosterilecekler){
                    view.setVisibility(View.GONE);

                }
            }
        }else{
            Log.e("FATMA","EN DISTAKI EELSE GIRDI");
        }

    }

}
