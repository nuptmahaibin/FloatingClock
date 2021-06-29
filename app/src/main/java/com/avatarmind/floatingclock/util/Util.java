package com.avatarmind.floatingclock.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;

public class Util {
    private static final int MIN_CLICK_DELAY_TIME = 3 * 1000; // 两次点击按钮之间的点击最小时间间隔
    private static long lastClickTime = 0;
    private static long lastCallTime = 0;

    public static boolean isFastClick(int delay_time) {
        boolean flag = true;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= delay_time) {
            lastClickTime = curClickTime;
            flag = false;
        }

        return flag;
    }

    public static boolean isFastClick() {
        return isFastClick(MIN_CLICK_DELAY_TIME);
    }

    public static boolean isFastCall(int delay_time) {
        boolean flag = true;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastCallTime) >= delay_time) {
            lastCallTime = curClickTime;
            flag = false;
        }

        return flag;
    }

    public static void startActivity(Context context, Class<?> cls) {
        if (context instanceof Context) {
            //do nothing
        } else {
            return;
        }

        try {
            Intent intent = new Intent(context, cls);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
        }
    }

    public static void startActivity(Context context, ComponentName componentName) {
        if (context instanceof Context) {
            //do nothing
        } else {
            return;
        }

        try {
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(componentName);
            context.startActivity(intent);
        } catch (Exception e) {
        }
    }

    public static void startService(Context context, Class<?> cls) {
        if (context instanceof Context) {
            //do nothing
        } else {
            return;
        }

        try {
            context.startService(new Intent(context, cls));
        } catch (Exception e) {
        }
    }

    public static void stopService(Context context, Class<?> cls) {
        try {
            context.stopService(new Intent(context, cls));
        } catch (Exception e) {
        }
    }

    public static void sendLocalBroadcast(Context context, String action) {
        if (context instanceof Context) {
            //do nothing
        } else {
            return;
        }

        Intent intent = new Intent(action);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public static void sendLocalBroadcast(Context context, String action, String extraName, int value) {
        if (context instanceof Context) {
            //do nothing
        } else {
            return;
        }

        Intent intent = new Intent(action);
        if (!TextUtils.isEmpty(extraName)) {
            intent.putExtra(extraName, value);
        }
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public static void sendLocalBroadcast(Context context, String action, String extraName, long value) {
        if (context instanceof Context) {
            //do nothing
        } else {
            return;
        }

        Intent intent = new Intent(action);
        if (!TextUtils.isEmpty(extraName)) {
            intent.putExtra(extraName, value);
        }
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public static void sendLocalBroadcast(Context context, String action, String extraName, String text) {
        if (context instanceof Context) {
            //do nothing
        } else {
            return;
        }

        Intent intent = new Intent(action);
        if (!TextUtils.isEmpty(extraName) && !TextUtils.isEmpty(text)) {
            intent.putExtra(extraName, text);
        }
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public static void sendLocalBroadcast(Context context, String action, String extraName, boolean flag) {
        if (context instanceof Context) {
            //do nothing
        } else {
            return;
        }

        Intent intent = new Intent(action);
        if (!TextUtils.isEmpty(extraName)) {
            intent.putExtra(extraName, flag);
        }
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public static boolean isNetworkConnected(Context context) {
        if (context instanceof Context) {
            //do nothing
        } else {
            return false;
        }

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = false;
        String type = "unknow";

        if (networkInfo != null) {
            isConnected = networkInfo.isAvailable();
            type = networkInfo.getTypeName();
        }

        LogUtil.i("isNetworkConnected() " + isConnected + ", network type = " + type);
        return isConnected;
    }
}
