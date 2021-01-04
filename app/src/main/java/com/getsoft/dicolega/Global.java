package com.getsoft.dicolega;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class Global {

    // Fonction qui nous aide a memoriser la traduction choisie
    public static void saveState(Activity activity, String key, String value){
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    // Fonction qui nous aide a recuperer l'activite avec son identifiant
    public static String getState(Activity activity, String key){
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getString(key, null);
    }

}

