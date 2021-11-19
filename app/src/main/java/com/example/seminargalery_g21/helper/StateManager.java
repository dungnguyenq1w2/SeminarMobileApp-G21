package com.example.seminargalery_g21.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class StateManager {

    private static final String APP_SETTINGS = "com.group21.gallery";

    private StateManager() {}

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(APP_SETTINGS, Context.MODE_PRIVATE);
    }

    public static int getThemeMode(Context context) {
        return getSharedPreferences(context).getInt("THEME_MODE", 1);
    }

    public static void setThemeMode(Context context, int themeMode) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putInt("THEME_MODE", themeMode);
        editor.apply();
    }
}
