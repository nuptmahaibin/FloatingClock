package com.avatarmind.floatingclock.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

public class SharedPreferencesUtil {
    public static final String CLOCKINFO = "clockinfo";
    public static final String isRunInBackground = "isRunInBackground";

    public static void initSharedPreferences(Context context) {
        if (context != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("com.avatarmind.floatclock_preferences", Context.MODE_PRIVATE);
            SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();

            if (sharedPreferencesEditor == null) {
                return;
            }

            if (!sharedPreferences.contains(CLOCKINFO)) {
                sharedPreferencesEditor.putString(CLOCKINFO, ClockInfo.getDefault());
                sharedPreferencesEditor.apply();
            }

            if (!sharedPreferences.contains(isRunInBackground)) {
                sharedPreferencesEditor.putBoolean(isRunInBackground, false);
                sharedPreferencesEditor.apply();
            }
        }
    }

    public static ClockInfo getClockInfo(Context context) {
        if (context != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("com.avatarmind.nav_preferences", Context.MODE_PRIVATE);
            String clockInfo = sharedPreferences.getString(CLOCKINFO, ClockInfo.getDefault());
            if (!TextUtils.isEmpty(clockInfo)) {
                return ClockInfo.getClockInfo(clockInfo);
            }
        }

        return null;
    }

    public static void setClockInfo(Context context, ClockInfo clockInfo) {
        if (context != null && clockInfo != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("com.avatarmind.nav_preferences", Context.MODE_PRIVATE);
            SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();

            sharedPreferencesEditor.putString(CLOCKINFO, clockInfo.getString());
            sharedPreferencesEditor.apply();
        }
    }

    public static boolean isExit(Context context) {
        if (context != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("com.avatarmind.nav_preferences", Context.MODE_PRIVATE);
            return sharedPreferences.getBoolean(isRunInBackground, false);
        }

        return false;
    }

    public static void setIsExit(Context context, boolean ret) {
        if (context != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("com.avatarmind.nav_preferences", Context.MODE_PRIVATE);
            SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();

            sharedPreferencesEditor.putBoolean(isRunInBackground, ret);
            sharedPreferencesEditor.apply();
        }
    }
}
