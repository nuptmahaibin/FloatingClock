package com.avatarmind.floatingclock.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class MyApp extends Application {
    private static final String TAG = "FloatingClock.MyApp";
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
            Log.d(TAG, "initSharedPreferences() failed");
            return;
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
            Log.d(TAG, "updatePosition() failed!");
            return;
        }

        if (mSharedPreferences.contains(this.x)) {
            mSharedPreferencesEditor.putInt(this.x, x);
            mSharedPreferencesEditor.commit();
        } else {
            Log.d(TAG, "updatePosition() failed, no x");
        }

        if (mSharedPreferences.contains(this.y)) {
            mSharedPreferencesEditor.putInt(this.y, y);
            mSharedPreferencesEditor.commit();
        } else {
            Log.d(TAG, "updatePosition() failed, no y");
        }
    }

    public int getX() {
        int x;
        if (mSharedPreferences == null) {
            x = defaultX;
            Log.d(TAG, "getX() failed, use default value(" + x + ")");
        } else {
            x = mSharedPreferences.getInt(this.x, defaultX);
        }

        return x;
    }

    public int getY() {
        int y;
        if (mSharedPreferences == null) {
            y = defaultY;
            Log.d(TAG, "getY() failed, use default value(" + y + ")");
        } else {
            y = mSharedPreferences.getInt(this.y, defaultY);
        }

        return y;
    }

    public void setTextSize(int size) {
        if (mSharedPreferences != null && mSharedPreferencesEditor != null && mSharedPreferences.contains(this.textSize)) {
            mSharedPreferencesEditor.putInt(this.textSize, size);
            mSharedPreferencesEditor.commit();
        } else {
            Log.d(TAG, "setTextSize() failed!");
            return;
        }
    }

    public int getTextSize() {
        if (mSharedPreferences == null || mSharedPreferencesEditor == null) {
            Log.d(TAG, "getTextSize() failed, use default value(" + defaultTextSize + ")");
            return defaultTextSize;
        } else {
            int size = mSharedPreferences.getInt(this.textSize, defaultTextSize);
            return size;
        }
    }
}
