package com.avatarmind.floatingclock;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class MyApp extends Application {
    private static final String TAG = "FloatingClock";
    private static final String x = "x";
    private static final String y = "y";
    private static final String textSize = "textSize";
    private static final int defaultX = 1;
    private static final int defaultY = 1;
    private static final int defaultTextSize = 30;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mSharedPreferencesEditor;

    private static MyApp app;

    public void onCreate() {
        super.onCreate();

        mSharedPreferences = getSharedPreferences("com.avatarmind.floatclock_preferences", Context.MODE_PRIVATE);
        mSharedPreferencesEditor = mSharedPreferences.edit();

        app = this;
    }

    public static MyApp getApplication() {
        return app;
    }

    public void initSharedPreferences() {
        if (mSharedPreferences == null || mSharedPreferencesEditor == null) {
            Log.d(TAG, "MyApp.initSharedPreferences() mSharedPreferences is null");
            return;
        } else {
            Log.d(TAG, "MyApp.initSharedPreferences() start");
        }

        if (!mSharedPreferences.contains(x)) {
            mSharedPreferencesEditor.putInt(x, defaultX);
            mSharedPreferencesEditor.commit();
        }

        if (!mSharedPreferences.contains(y)) {
            mSharedPreferencesEditor.putInt(y, defaultY);
            mSharedPreferencesEditor.commit();
        }

        if (!mSharedPreferences.contains(textSize)) {
            mSharedPreferencesEditor.putInt(textSize, defaultTextSize);
            mSharedPreferencesEditor.commit();
        }
    }

    public void updatePosition(int x, int y) {
        if (mSharedPreferences == null || mSharedPreferencesEditor == null) {
            return;
        }

        Log.d(TAG, "MyApp.updatePosition() " + x + " " + y);

        if (mSharedPreferences.contains(this.x)) {
            mSharedPreferencesEditor.putInt(this.x, x);
            mSharedPreferencesEditor.commit();
        } else {
            Log.d(TAG, "MyApp.updatePosition() no x");
        }

        if (mSharedPreferences.contains(this.y)) {
            mSharedPreferencesEditor.putInt(this.y, y);
            mSharedPreferencesEditor.commit();
        } else {
            Log.d(TAG, "MyApp.updatePosition() no y");
        }
    }

    public int getX() {
        int x;
        if (mSharedPreferences == null) {
            x = defaultX;
            Log.d(TAG, "MyApp.getX() mSharedPreferences is null, X = " + x);
        } else {
            x = mSharedPreferences.getInt(this.x, defaultX);
            Log.d(TAG, "MyApp.getX() X = " + x);
        }

        return x;
    }

    public int getY() {
        int y;
        if (mSharedPreferences == null) {
            y = defaultY;
            Log.d(TAG, "MyApp.getY() mSharedPreferences is null, Y = " + y);
        } else {
            y = mSharedPreferences.getInt(this.y, defaultY);

            Log.d(TAG, "MyApp.getY() Y = " + y);
        }

        return y;
    }

    public void setTextSize(int size) {
        if (mSharedPreferences == null || mSharedPreferencesEditor == null) {
            return;
        }

        Log.d(TAG, "MyApp.setTextSize()  size(" + size + ")");

        if (mSharedPreferences.contains(this.textSize)) {
            mSharedPreferencesEditor.putInt(this.textSize, size);
            mSharedPreferencesEditor.commit();
        } else {
            Log.d(TAG, "MyApp.setTextSize() no textSize");
        }
    }

    public int getTextSize() {
        if (mSharedPreferences == null || mSharedPreferencesEditor == null) {
            Log.d(TAG, "MyApp.getTextSize() use default size (" + defaultTextSize + ")");
            return defaultTextSize;
        } else {
            int size = mSharedPreferences.getInt(this.textSize, defaultTextSize);
            Log.d(TAG, "MyApp.getTextSize() size (" + size + ")");
            return size;
        }
    }
}
