package sercandevops.com.notsepeteekle.adapter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import sercandevops.com.notsepeteekle.R;

public class Divider extends RecyclerView.ItemDecoration {

    private Drawable mDivider;
    private int mOrientation;


    public Divider(Context context,int orentation){

       // mDivider = context.getDrawable(R.drawable.divider);
      mDivider = ContextCompat.getDrawable(context,R.drawable.divider);

        if(orentation != LinearLayoutManager.VERTICAL){
            throw new IllegalArgumentException("Bu dekorasyon burda kullanılamaz.");
        }

        mOrientation =orentation;

    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);

        drawHorizontalDivider(c,parent,state);

    }


    private void drawHorizontalDivider(Canvas c,RecyclerView parent, RecyclerView.State state){

        int sol,yukari,sag,asagi;
        sol = parent.getPaddingLeft();
        sag = parent.getWidth()-parent.getPaddingRight();
    int elemanSayisi = parent.getChildCount();

    for(int i=0;i<elemanSayisi;i++){

        View suankiView = parent.getChildAt(i);
        yukari = suankiView.getTop();
        asagi = yukari+mDivider.getIntrinsicHeight()+20;

        mDivider.setBounds(sol,yukari,sag,asagi);
        mDivider.draw(c);
    }

    }


}
