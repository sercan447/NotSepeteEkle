package sercandevops.com.notsepeteekle;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class FragmentDialogTamamla extends DialogFragment {

    private ImageButton mBtnKapat;
    private Button mBtnTamamlandi;
    static int notAdapterPosition=0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dialog_tamamlandi,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBtnKapat = (ImageButton)view.findViewById(R.id.btn_dialog_kapat2);
        mBtnKapat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        mBtnTamamlandi = (Button)view.findViewById(R.id.btn_tamamlandi);
        mBtnTamamlandi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              // Toast.makeText(getContext(),"DURUM"+notAdapterPosition,Toast.LENGTH_LONG).show();
                EventBus.getDefault().post(new DataEvent.NotTamamlaPosition(notAdapterPosition));
                dismiss();
            }
        });

    }

    @Subscribe(sticky = true)
    public void onDialogTamamlaNotPosition(DataEvent.TamamlanacakNotPosition event){

        notAdapterPosition = event.getPosition();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        EventBus.getDefault().register(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getDefault().unregister(this);
    }


}
