package com.avatarmind.floatingclock.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtil {
    private static SharedPreferences mSharedPreferences;
    private static SharedPreferences.Editor mSharedPreferencesEditor;
    public static final int defaultX = 1;
    public static final int defaultY = 1;
    public static final int defaultTextSize = 30;

    public static final String isCloseClock = "isCloseClock";
    public static final String x = "x";
    public static final String y = "y";
    public static final String clockSize = "clockSize";

    public static void initSharedPreferences(Context context) {
        if (context instanceof Context) {
            // do nothing
        } else {
            return;
        }

        mSharedPreferences = context.getSharedPreferences("com.avatarmind.floatclock_preferences", Context.MODE_PRIVATE);
        mSharedPreferencesEditor = mSharedPreferences.edit();

        if (mSharedPreferences == null || mSharedPreferencesEditor == null) {
            return;
        }

        if (!mSharedPreferences.contains(clockSize)) {
            mSharedPreferencesEditor.putBoolean(clockSize, false);
            mSharedPreferencesEditor.commit();
        }

        if (!mSharedPreferences.contains(x)) {
            mSharedPreferencesEditor.putInt(x, defaultX);
            mSharedPreferencesEditor.commit();
        }

        if (!mSharedPreferences.contains(y)) {
            mSharedPreferencesEditor.putInt(y, defaultY);
            mSharedPreferencesEditor.commit();
        }

        if (!mSharedPreferences.contains(clockSize)) {
            mSharedPreferencesEditor.putInt(clockSize, defaultTextSize);
            mSharedPreferencesEditor.commit();
        }
    }

    public static boolean getSharedPreferencesValue(Context context, String name, boolean defaultValue) {
        if (context instanceof Context) {
            // do nothing
        } else {
            return defaultValue;
        }

        SharedPreferences sharedPreferences = context.getSharedPreferences("com.avatarmind.nav_preferences", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(name, defaultValue);
    }

    public static void setSharedPreferencesValue(Context context, String name, boolean value) {
        if (context instanceof Context) {
            // do nothing
        } else {
            return;
        }

        SharedPreferences sharedPreferences = context.getSharedPreferences("com.avatarmind.nav_preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();

        sharedPreferencesEditor.putBoolean(name, value);
        sharedPreferencesEditor.commit();
    }

    public static int getSharedPreferencesValue(Context context, String name, int defaultValue) {
        if (context instanceof Context) {
            // do nothing
        } else {
            return defaultValue;
        }

        SharedPreferences sharedPreferences = context.getSharedPreferences("com.avatarmind.nav_preferences", Context.MODE_PRIVATE);
        return sharedPreferences.getInt(name, defaultValue);
    }

    public static void setSharedPreferencesValue(Context context, String name, int value) {
        if (context instanceof Context) {
            // do nothing
        } else {
            return;
        }

        SharedPreferences sharedPreferences = context.getSharedPreferences("com.avatarmind.nav_preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();

        sharedPreferencesEditor.putInt(name, value);
        sharedPreferencesEditor.commit();
    }

    public static String getSharedPreferencesValue(Context context, String name, String defaultValue) {
        if (context instanceof Context) {
            // do nothing
        } else {
            return defaultValue;
        }

        SharedPreferences sharedPreferences = context.getSharedPreferences("com.avatarmind.nav_preferences", Context.MODE_PRIVATE);
        return sharedPreferences.getString(name, defaultValue);
    }

    public static void setSharedPreferencesValue(Context context, String name, String value) {
        if (context instanceof Context) {
            // do nothing
        } else {
            return;
        }

        SharedPreferences sharedPreferences = context.getSharedPreferences("com.avatarmind.nav_preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();

        sharedPreferencesEditor.putString(name, value);
        sharedPreferencesEditor.commit();
    }
}
