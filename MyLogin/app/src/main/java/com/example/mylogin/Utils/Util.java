package com.example.mylogin.Utils;

import android.content.SharedPreferences;

public class Util {

    public static String getUserMailPrefs(SharedPreferences preferences){
        return preferences.getString("email", "");
    }

    public static String getUserpassPrefs(SharedPreferences preferences){
        return preferences.getString("pass", "");
    }

    public static void removeSharedPref(SharedPreferences preferences){
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.remove("email");
        editor.remove("pass");
        editor.apply();
    }
}

