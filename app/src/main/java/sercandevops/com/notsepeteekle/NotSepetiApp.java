package sercandevops.com.notsepeteekle;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;

public class NotSepetiApp extends Application {


    public  static void sharedYaz(Context context, int secilenFiltre){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edior = preferences.edit();
        edior.putInt("filtre",secilenFiltre);
        edior.apply();
    }

    public  static  int sharedOku(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        int secilenFiltre = preferences.getInt("filtre",0);
        return secilenFiltre;
    }


}
