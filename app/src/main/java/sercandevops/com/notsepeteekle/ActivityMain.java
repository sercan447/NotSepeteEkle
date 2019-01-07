package sercandevops.com.notsepeteekle;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import sercandevops.com.notsepeteekle.adapter.AdapterNotlarListesi;
import sercandevops.com.notsepeteekle.adapter.Divider;
import sercandevops.com.notsepeteekle.adapter.SimpleTouchCallBack;
import sercandevops.com.notsepeteekle.data.Filtreler;
import sercandevops.com.notsepeteekle.data.NotProvider;
import sercandevops.com.notsepeteekle.data.Notlar;
import sercandevops.com.notsepeteekle.services.BildirimServisi;

public class ActivityMain extends AppCompatActivity {

    static final String SIRALAMA_ONEMSIZ = "SIRALAMA ONEMSIZ";
    static final String TAMAMLANMA_ONEMSIZ = "TAMAMLAMA ONEMSIZ";

    View bosliste;
    private Toolbar mToolbar;
    ImageView background;
    Button mButtonYeninot;
    NotlarRecyclerView mRecyclerviewNotlar;

    AdapterNotlarListesi mAdapterNotlarListesi;
    ArrayList<Notlar> tumNotlar = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bosliste = (View)findViewById(R.id.bos_liste);
        mToolbar = (Toolbar)findViewById(R.id.mtoolbar);
        mRecyclerviewNotlar = (NotlarRecyclerView) findViewById(R.id.rv_not_listesi);
        background = findViewById(R.id.iv_background);

        setSupportActionBar(mToolbar);

        //HER İTEM İÇİN ARALARA CIZGI CEKER
        //mRecyclerviewNotlar.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
        mRecyclerviewNotlar.addItemDecoration(new Divider(this,LinearLayoutManager.VERTICAL));
        mRecyclerviewNotlar.egerElemanYoksaSaklanacaklar(mToolbar);
        mRecyclerviewNotlar.egerElemanYoksaGosterilecekler(bosliste);


        //backgroundResminiYerlestir();

            mButtonYeninot = (Button)findViewById(R.id.btn_sepete_not_ekle);
            mButtonYeninot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    notekleDialogGoster();
                }
            });

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerviewNotlar.setLayoutManager(mLayoutManager);

        mAdapterNotlarListesi = new AdapterNotlarListesi(this,tumNotlar);

        mAdapterNotlarListesi.setHasStableIds(true);
        mRecyclerviewNotlar.setAdapter(mAdapterNotlarListesi);

        //SWİPE KAYDIRARAK SILME ISLEMI
        SimpleTouchCallBack callback = new SimpleTouchCallBack(mAdapterNotlarListesi);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(mRecyclerviewNotlar);



        dataGuncelle(SIRALAMA_ONEMSIZ,TAMAMLANMA_ONEMSIZ);


        int seciliFiltre = NotSepetiApp.sharedOku(getApplicationContext());

        switch (seciliFiltre){

            case Filtreler.COK_VAKIT_VAR:

                dataGuncelle("notTarih DESC",TAMAMLANMA_ONEMSIZ);
                break;
            case Filtreler.AZ_VAKIT_KALDI:

                dataGuncelle("notTarih ASC",TAMAMLANMA_ONEMSIZ);
                break;
            case Filtreler.TAMAMLANANLAR:

                dataGuncelle(SIRALAMA_ONEMSIZ,"1");
                break;
            case Filtreler.TAMAMLANMAYANLAR:

                dataGuncelle(SIRALAMA_ONEMSIZ,"0");
                break;
        }

        AlarmManager manager = (AlarmManager)getSystemService(ALARM_SERVICE);

        Intent intent = new Intent(this,BildirimServisi.class);
        PendingIntent pendingIntent = PendingIntent.getService(this,100,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        manager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,1000,5000,pendingIntent);

    }

    public void dataGuncelle(String siralama,String tamamlama){

        tumNotlar.clear();
        tumNotlar = tumNotlariGoster(siralama,tamamlama);
        mAdapterNotlarListesi.update(tumNotlar);
    }


    private void backgroundResminiYerlestir(){

        // background.setImageDrawable(getResources().getDrawable(R.drawable.anaresim2));
         // Glide.with(getApplicationContext()).load(R.drawable.anaresim2).apply(new RequestOptions().centerCrop()).into(background);
    }


    public void tikla(View view) {
        Toast.makeText(getApplicationContext(),"hava",Toast.LENGTH_LONG).show();
    }


    public ArrayList<Notlar> tumNotlariGoster(String siralama,String tamamlama){

        String siralamaSorgu = siralama;
        String selection = "tamamlandi=?";
        String[] tamamlamaSorgusu = {tamamlama};

        if(siralama.equals(SIRALAMA_ONEMSIZ)){

            siralamaSorgu = null;
        }
        if(tamamlama.equals(TAMAMLANMA_ONEMSIZ)){
            selection = null;
            tamamlamaSorgusu = null;

        }

        String[] projection = {NotProvider.C_ID,NotProvider.C_NOT_ICERIK,NotProvider.C_NOT_TARIH,NotProvider.C_TAMAMLANDI};

       Cursor cursor = getContentResolver().query(NotProvider.CONTENT_URI,projection,selection,tamamlamaSorgusu,siralamaSorgu);


       if(cursor != null){
           while(cursor.moveToNext()){
               Notlar geciciNot =  new Notlar();
               geciciNot.setId(cursor.getInt(cursor.getColumnIndex("id")));
               geciciNot.setNotIcerik(cursor.getString(cursor.getColumnIndex(NotProvider.C_NOT_ICERIK)));
               geciciNot.setNotTarih(cursor.getInt(cursor.getColumnIndex(NotProvider.C_NOT_TARIH)));
               geciciNot.setTamamlandi(cursor.getInt(cursor.getColumnIndex(NotProvider.C_TAMAMLANDI)));


               tumNotlar.add(geciciNot);
               Log.e("KT",cursor.getString(cursor.getColumnIndex(NotProvider.C_NOT_ICERIK)));
           }
       }
       return tumNotlar;

    }

    private void notekleDialogGoster(){
        FragmentDialogYeniNot frg = new FragmentDialogYeniNot();
        frg.show(getSupportFragmentManager(),"DialogYeniNot");
    }

    public void notTamamlaDialogGoster(int position){

        EventBus.getDefault().postSticky(new DataEvent.TamamlanacakNotPosition(position));

        FragmentDialogTamamla ftm = new FragmentDialogTamamla();
        ftm.show(getSupportFragmentManager(),"DilaogTamamlaNot");


    }

    @Subscribe(sticky = true)
    public void onNotEkleDailogGoster(DataEvent.NotEkleDialogGoster dataevent){

        if(dataevent.getTetikle() == 1){
            notekleDialogGoster();
        }
    }

    @Subscribe
    public void onNotTamamlaDialogGoster(DataEvent.DialogTamamlaNotPosition event){

       notTamamlaDialogGoster(event.getPosition());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int menu_id = item.getItemId();

            switch (menu_id){

                case R.id.menu_yeninot:
                    notekleDialogGoster();
                    break;
                case R.id.menu_cokvakit:
                   NotSepetiApp.sharedYaz(this,Filtreler.COK_VAKIT_VAR);
                    dataGuncelle("notTarih DESC",TAMAMLANMA_ONEMSIZ);
                    break;
                case R.id.menu_azvakit:
                    NotSepetiApp.sharedYaz(this,Filtreler.AZ_VAKIT_KALDI);
                    dataGuncelle("notTarih ASC",TAMAMLANMA_ONEMSIZ);
                    break;
                case R.id.menu_tamamlananlar:
                    NotSepetiApp.sharedYaz(this,Filtreler.TAMAMLANANLAR);
                    dataGuncelle(SIRALAMA_ONEMSIZ,"1");
                    break;
                case R.id.menu_tamamlanmayanlar:
                    NotSepetiApp.sharedYaz(this,Filtreler.TAMAMLANMAYANLAR);
                    dataGuncelle(SIRALAMA_ONEMSIZ,"0");
                    break;

            }//switch
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
