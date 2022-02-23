package com.avatarmind.floatingclock.util;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class ClockInfo {
    private int x;
    private int y;
    private int textSize;

    public ClockInfo(int x, int y, int textSize) {
        this.x = x;
        this.y = y;
        this.textSize = textSize;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public String getString() {
        return new Gson().toJson(this);
    }

    public static String getDefault() {
        return new ClockInfo(1, 1, 30).getString();
    }

    public static ClockInfo getClockInfo(String json) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }

        Type type = new TypeToken<ClockInfo>() {
        }.getType();
        ClockInfo clockInfo = new Gson().fromJson(json, type);
        return clockInfo;
    }
}
