package sercandevops.com.notsepeteekle.services;

import android.app.IntentService;

import android.app.Notification;
import android.content.Intent;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import java.util.ArrayList;

import sercandevops.com.notsepeteekle.data.NotProvider;
import sercandevops.com.notsepeteekle.data.Notlar;


public class BildirimServisi extends IntentService {

    public static final String TAG = Thread.currentThread().getName();
    ArrayList<Notlar> tamamlanmayanNotlar = null;

    public BildirimServisi() {
        super("BildirimServisi");

       tamamlanmayanNotlar = new ArrayList<Notlar>();
        bildirimYolla("constreuctr");

    }//FUNC

    private void bildirimYolla(String durum){

        Log.d(TAG,"BILDIRIM CALISIYO : "+durum);


    }
    public ArrayList<Notlar> tamamlanmayanNotlariGetir(){

        String selection = "tamamlandi=?";
        String[] tamamlamaSorgusu = {"0"};
        ArrayList<Notlar> tumNotlar = new ArrayList<>();
        String[] projection = {NotProvider.C_ID,NotProvider.C_NOT_ICERIK,NotProvider.C_NOT_TARIH,NotProvider.C_NOT_EKLEME_TARIH,NotProvider.C_TAMAMLANDI};

        Cursor cursor = getContentResolver().query(NotProvider.CONTENT_URI,projection,selection,tamamlamaSorgusu,null);

        if(cursor != null){
            while(cursor.moveToNext()){
                Notlar geciciNot =  new Notlar();
                geciciNot.setId(cursor.getInt(cursor.getColumnIndex("id")));
                geciciNot.setNotIcerik(cursor.getString(cursor.getColumnIndex(NotProvider.C_NOT_ICERIK)));
                geciciNot.setNotTarih(cursor.getInt(cursor.getColumnIndex(NotProvider.C_NOT_TARIH)));
                geciciNot.setNotEklenmeTarihi(cursor.getInt(cursor.getColumnIndex(NotProvider.C_NOT_EKLEME_TARIH)));
                geciciNot.setTamamlandi(cursor.getInt(cursor.getColumnIndex(NotProvider.C_TAMAMLANDI)));


                tumNotlar.add(geciciNot);
                Log.e("KT",cursor.getString(cursor.getColumnIndex(NotProvider.C_NOT_ICERIK)));
            }
        }
        return tumNotlar;
    }


    @Override
    protected void onHandleIntent(Intent intent) {

        Log.d(TAG,"BildirimServisi: onhanldend");
        tamamlanmayanNotlar = tamamlanmayanNotlariGetir();

        for(Notlar geciciNot : tamamlanmayanNotlar)
        {
            if(bildirimGerekli(geciciNot.getNotEklenmeTarihi(),geciciNot.getNotTarih())){

                bildirimYolla("var");
            }else{
                bildirimYolla("yok");
            }
        }//for
    }

    private boolean bildirimGerekli(long noteklenmeTarihi,long notTarih){
        long now = System.currentTimeMillis();

        if(now > notTarih){
            return false;
        }else{
            long yuzde90 = (long) 0.9 * (notTarih - noteklenmeTarihi);
            return (now > (noteklenmeTarihi + yuzde90)) ?true : false;
        }
    }


}
