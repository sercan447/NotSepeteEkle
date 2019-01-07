package sercandevops.com.notsepeteekle;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Calendar;
import java.util.TimeZone;

import sercandevops.com.notsepeteekle.data.NotProvider;

public class FragmentDialogYeniNot extends DialogFragment {


    private ImageButton mBtnKapat;
    private EditText mNotIcerik;
    //private DatePicker mNotTarih;
    private TarihSecDatePicker mNotTarih;
    private Button mBtnNotEkle;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL,R.style.dialogTemasi);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dialog_yeni_not,container,false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mBtnKapat = (ImageButton)view.findViewById(R.id.btn_dialog_kapat);
        mNotIcerik = (EditText) view.findViewById(R.id.et_not);
        mNotTarih = (TarihSecDatePicker) view.findViewById(R.id.dp_tarih);
        mBtnNotEkle = (Button)view.findViewById(R.id.btn_not_ekle);

        mBtnKapat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    mBtnNotEkle.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

          /*  Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
            calendar.set(Calendar.DAY_OF_MONTH,mNotTarih.getDayOfMonth());
            calendar.set(Calendar.MONTH,mNotTarih.getMonth());
            calendar.set(Calendar.YEAR,mNotTarih.getYear());
            calendar.set(Calendar.HOUR,0);
            calendar.set(Calendar.MINUTE,0);
            calendar.set(Calendar.SECOND,0);
            */

            ContentValues values = new ContentValues();
            values.put(NotProvider.C_NOT_ICERIK,mNotIcerik.getText().toString());
            values.put(NotProvider.C_NOT_TARIH,mNotTarih.getDrawingTime());
            values.put(NotProvider.C_NOT_EKLEME_TARIH,System.currentTimeMillis());
            values.put(NotProvider.C_TAMAMLANDI,0);

          Uri uri = getActivity().getContentResolver().insert(NotProvider.CONTENT_URI,values);


            Toast.makeText(getContext(),"NOT eklendi."+uri.toString(),Toast.LENGTH_LONG).show();

            ((ActivityMain)getActivity()).dataGuncelle("SIRALAMA ONEMSIZ","TAMAMLAMA ONEMSIZ");


        }
    });


    }



}
