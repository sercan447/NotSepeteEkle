package sercandevops.com.notsepeteekle;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TarihSecDatePicker extends LinearLayout implements  View.OnTouchListener{

    TextView mTextGun;
    TextView mTextAy;
    TextView mTextYil;
    Calendar mCalendar;
    SimpleDateFormat mFormat;

    public TarihSecDatePicker(Context context) {
        super(context);
        init(context);
    }

    public TarihSecDatePicker(Context context,  @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TarihSecDatePicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public TarihSecDatePicker(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    public void init(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.tarih_view,this);
        mCalendar = Calendar.getInstance();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mTextGun = (TextView)this.findViewById(R.id.tv_tarih_gun);
        mTextAy = (TextView)this.findViewById(R.id.tv_tarih_ay);
        mTextYil = (TextView)this.findViewById(R.id.tv_tarih_yil);
        mFormat = new SimpleDateFormat("MMM");

        mTextGun.setOnTouchListener(this);
        mTextAy.setOnTouchListener(this);
        mTextYil.setOnTouchListener(this);

        int gun = mCalendar.get(Calendar.DATE);
        int ay = mCalendar.get(Calendar.MONTH);
        int yil = mCalendar.get(Calendar.YEAR);

        guncelle(gun,ay,yil,0,0,0);

    }

    private void guncelle(int gun,int ay,int yil,int saat,int dakika,int saniye){
        mTextGun.setText(""+gun);
        mTextAy.setText(mFormat.format(mCalendar.getTime()));
        mTextYil.setText(""+yil);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (v.getId()){
            case R.id.tv_tarih_gun:
                proccesEventFor(mTextGun,event);
                break;
            case R.id.tv_tarih_ay:
                proccesEventFor(mTextAy,event);
                break;
            case R.id.tv_tarih_yil:
                proccesEventFor(mTextYil,event);
                break;
        }
        return true;
    }

    private void proccesEventFor(TextView textView, MotionEvent event) {

        Drawable[] drawables = textView.getCompoundDrawables();

        if(yukariDrawableVarmi(drawables) && asagiDrawableVarmi(drawables)){
            Rect yukariSinir = drawables[1].getBounds();
            Rect asagiSinir = drawables[3].getBounds();

            float x = event.getX();
            float y = event.getY();

            if(yukariDrawableTiklandi(textView,yukariSinir,x,y)){
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    //Toast.makeText(getContext(),"YUKARI TIK",Toast.LENGTH_LONG).show();
                    arkaPlaniDegistir(textView,1);
                    arttir(textView.getId());
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                        arkaPlanEskiHaliGetir(textView);
                }



            }else if(asagiDrawableTiklandi(textView,asagiSinir,x,y)){

                if(event.getAction() == MotionEvent.ACTION_DOWN){
                 //   Toast.makeText(getContext(),"ASAGI TIKLANDI",Toast.LENGTH_LONG).show();
                    arkaPlaniDegistir(textView,0);
                    azalt(textView.getId());
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    arkaPlanEskiHaliGetir(textView);
                }

            }else{

            }
        }
    }//FUNC

    private void arkaPlanEskiHaliGetir(TextView textView) {

        textView.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.up_normal,0,R.drawable.down_normal);
    }

    private void arkaPlaniDegistir(TextView textView,int i){
        if(i == 1){
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(0,R.drawable.up_normal_pressed,0,R.drawable.down_normal);
        }else{
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(0,R.drawable.up_normal,0,R.drawable.up_normal_pressed);
        }
    }

    private void azalt(int id) {
        switch (id){
            case R.id.tv_tarih_gun:
                mCalendar.add(Calendar.DATE,-1);
                break;
            case R.id.tv_tarih_ay:
                mCalendar.add(Calendar.MONTH,-1);
                break;
            case R.id.tv_tarih_yil:
                mCalendar.add(Calendar.YEAR,-1);
                break;
        }

        textviewAlanTarihiGuncelle(mCalendar);
    }

    private void textviewAlanTarihiGuncelle(Calendar mCalendar) {

        int gun = mCalendar.get(Calendar.DATE);
        int ay = mCalendar.get(Calendar.MONTH);
        int yil = mCalendar.get(Calendar.YEAR);

        guncelle(gun,ay,yil,0,0,0);
    }

    private void arttir(int id) {

        switch (id){
            case R.id.tv_tarih_gun:
                mCalendar.add(Calendar.DATE,1);
                break;
            case R.id.tv_tarih_ay:
                mCalendar.add(Calendar.MONTH,1);
                break;
            case R.id.tv_tarih_yil:
                mCalendar.add(Calendar.YEAR,1);
                break;
        }
        textviewAlanTarihiGuncelle(mCalendar);

    }


    private boolean asagiDrawableVarmi(Drawable[] drawables) {
        return drawables[3] != null;
    }

    private boolean yukariDrawableVarmi(Drawable[] drawables) {
        return drawables[1] != null;
    }

    private boolean yukariDrawableTiklandi(TextView textView, Rect yukariSinir, float x, float y) {
            int xmin = textView.getPaddingLeft();
            int xmax = textView.getWidth() - textView.getPaddingRight();

            int ymin = textView.getPaddingTop();
            int ymax = textView.getPaddingTop()+yukariSinir.height();

            return x > xmin && x < xmax && y > ymin && y<ymax;
    }

    private boolean asagiDrawableTiklandi(TextView textView, Rect asagiSinir, float x, float y) {

        int xmin = textView.getPaddingLeft();
        int xmax = textView.getWidth() - textView.getPaddingRight();

        int ymax = textView.getHeight() - textView.getPaddingBottom();
        int ymin = ymax - asagiSinir.height();

        return x > xmin && x < xmax && y > ymin && y<ymax;

    }
}
