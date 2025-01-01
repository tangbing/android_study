package com.example.chapter08.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceUtils {

    private static SharedPreferenceUtils mUtils;

    private SharedPreferences preferences;

    public static SharedPreferenceUtils getInstance(Context ctx) {
        if (mUtils == null) {
            mUtils = new SharedPreferenceUtils();
            mUtils.preferences = ctx.getSharedPreferences("Shopping", Context.MODE_PRIVATE);
        }
        return mUtils;
    }

    public void writeBoolean(String key, boolean boolValue) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, boolValue);
        editor.commit();
    }

    public  boolean readBoolean(String key, boolean defaultValue) {
        return preferences.getBoolean(key, defaultValue);
    }


}
