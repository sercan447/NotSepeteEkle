package sercandevops.com.notsepeteekle.adapter;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import sercandevops.com.notsepeteekle.DataEvent;
import sercandevops.com.notsepeteekle.NotSepetiApp;
import sercandevops.com.notsepeteekle.R;
import sercandevops.com.notsepeteekle.data.Filtreler;
import sercandevops.com.notsepeteekle.data.NotProvider;
import sercandevops.com.notsepeteekle.data.Notlar;

public class AdapterNotlarListesi extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements SwipeListener{

    private static final int ITEM = 0;
    private static final int FOOTER = 1;
    private static final int BOS_LISTE = 2;
    LayoutInflater mInflater;
    ArrayList<Notlar> tumNotlar;
    private ContentResolver resolver;
    Context mContext;

    public static final int FOOTER_EKLE = 1;
    public static final int BOS_FILTRE_EKLE = 1;

    private int mFiltre;

    public AdapterNotlarListesi(Context context, ArrayList<Notlar> notlar){

        resolver = context.getContentResolver();
        mInflater = LayoutInflater.from(context);
        this.tumNotlar = notlar;
        mContext = context;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        if(i == ITEM){
            View view = mInflater.inflate(R.layout.tek_satir_not,viewGroup,false);
            NotHolder holder = new NotHolder(view);
            return holder;
        }else if(i == BOS_LISTE){
            View view = mInflater.inflate(R.layout.bos_filtre,viewGroup,false);
            BosFiltreHolder holder = new BosFiltreHolder(view);
            return holder;
        }else {
            View view = mInflater.inflate(R.layout.footer,viewGroup,false);
            FooterHolder fholder = new FooterHolder(view);
            return fholder;
        }

        //return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int i) {

        if(holder instanceof NotHolder) {
            NotHolder notholder = (NotHolder)holder;
            //notholder.mTextNotIcerik.setText(tumNotlar.get(i).getNotIcerik());
//            notholder.mTextNotTarih.setText(""+tumNotlar.get(i).getNotTarih());
            //notholder.setBackroundRenk(tumNotlar.get(i).getTamamlandi());
         ///   notholder.setTarih(tumNotlar.get(i).getNotTarih());
        }
    }

    @Override
    public int getItemCount() {

        if(!tumNotlar.isEmpty()){
            return tumNotlar.size() + FOOTER_EKLE;
        }else{
            if(mFiltre == Filtreler.AZ_VAKIT_KALDI || mFiltre == Filtreler.COK_VAKIT_VAR){
                return 0;
            }else{
                return FOOTER_EKLE + BOS_FILTRE_EKLE;
            }
        }

    }


    @Override
    public int getItemViewType(int position) {

        if(!tumNotlar.isEmpty()){

            if(position < tumNotlar.size()){
                return ITEM;
            }else{
                return FOOTER;
            }
        }else{
            if(mFiltre == Filtreler.TAMAMLANANLAR || mFiltre == Filtreler.TAMAMLANMAYANLAR){

                if(position == 0){
                    return BOS_LISTE;
                }else{
                    return FOOTER;
                }
            }else{
                return ITEM;
            }
        }
    }

    @Override
    public long getItemId(int position) {

        if(position < tumNotlar.size()){
            return tumNotlar.get(position).getId();
        }

        return RecyclerView.NO_ID;
    }

    @Override
    public void onSwipe(int position) {


        if(position < tumNotlar.size()){

            Notlar silinecekNot = tumNotlar.get(position);
            String silinecekNotID = String.valueOf(silinecekNot.getId());

            int etkilenenSatirSayisi = resolver.delete(NotProvider.CONTENT_URI,"id=?",new String[]{silinecekNotID});
            if(etkilenenSatirSayisi != 0){
                tumNotlar.remove(silinecekNot);
                update(tumNotlar);
                notifyDataSetChanged();
            }
        }
    }

    public void update(ArrayList<Notlar> tumNotlar){
        this.tumNotlar = tumNotlar;
        mFiltre = NotSepetiApp.sharedOku(mContext);
        notifyDataSetChanged();
    }

    @Subscribe
    public void NotTamamlaPosition(DataEvent.NotTamamlaPosition event){
            int position = event.getPosition();

            if(position < tumNotlar.size()){
                Notlar tamamlanacakNot = tumNotlar.get(position);
                String notID = String.valueOf(tamamlanacakNot.getId());

                ContentValues values = new ContentValues();
                values.put("tamamlandi",1);
                int etkilenenSatirSayisi = resolver.update(NotProvider.CONTENT_URI,values,"id=?",new String[]{notID});

                if(etkilenenSatirSayisi != 0){
                    tamamlanacakNot.setTamamlandi(1);
                    tumNotlar.set(position,tamamlanacakNot);
                    Log.e("Guncellendi Data :",notID);
                        notifyDataSetChanged();
                }
            }
    }

    public class NotHolder extends RecyclerView.ViewHolder {

        TextView mTextNotIcerik;
        TextView mTextNotTarih;
        View mView;

        public NotHolder(@NonNull final View itemView) {
            super(itemView);

            mView = itemView;
            mTextNotIcerik = (TextView)itemView.findViewById(R.id.tv_not_icerik);
            mTextNotTarih = (TextView)itemView.findViewById(R.id.tv_not_tarih);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new DataEvent.DialogTamamlaNotPosition(getAdapterPosition()));


                }
            });
        }

        public void setBackroundRenk(int tamamlandi){

            Drawable backgroundDrawable = null;

            if(tamamlandi == 0){
                backgroundDrawable = ContextCompat.getDrawable(mContext,R.color.tamamlanmamis_not);
            }else{
                backgroundDrawable = ContextCompat.getDrawable(mContext,R.color.tamamlanmis_not);
            }
            mView.setBackground(backgroundDrawable);
        }//FUNC

        public void setTarih(long notTarih){
            mTextNotTarih.setText(DateUtils.getRelativeTimeSpanString(notTarih,System.currentTimeMillis(),DateUtils.DAY_IN_MILLIS,0));
        }
    }// INNER CLASS Notholder

    public class FooterHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        Button mBtnFooterEkle;

        public FooterHolder(@NonNull View itemView) {
            super(itemView);

            mBtnFooterEkle = (Button)itemView.findViewById(R.id.btn_footer);
            mBtnFooterEkle.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {


            EventBus.getDefault().post(new DataEvent.NotEkleDialogGoster(1));

        }
    }//INNER CLASS FooterHolder

    public class BosFiltreHolder extends RecyclerView.ViewHolder{

        public BosFiltreHolder(View itemView){
            super(itemView);
        }

    }//BosFilter INNER CLASS


    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        EventBus.getDefault().register(this);

    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);

        EventBus.getDefault().unregister(this);
    }
}//class
