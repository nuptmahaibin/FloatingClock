package com.avatarmind.floatingclock.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ToastUtil {
    private static Toast mToast = null;

    public static void error(final Context context, final String text) {
        showToast(context, text, 0, Toast.LENGTH_LONG, Gravity.CENTER);
    }

    public static void remind(final Context context, final String text) {
        showToast(context, text, 0, Toast.LENGTH_LONG, Gravity.CENTER);
    }

    public static void debug(final Context context, final String text) {
        if (Constants.DEBUG) showToast(context, text, 0, Toast.LENGTH_LONG, Gravity.CENTER);
    }

    public static void error(final Context context, final String text, int duration) {
        showToast(context, text, 0, duration, Gravity.CENTER);
    }

    public static void remind(final Context context, final String text, int duration) {
        showToast(context, text, 0, duration, Gravity.CENTER);
    }

    public static void debug(final Context context, final String text, int duration) {
        if (Constants.DEBUG) showToast(context, text, 0, duration, Gravity.CENTER);
    }

    /**
     * 弹出Toast
     *
     * @param context  上下文对象
     * @param text     提示的文本
     * @param duration 持续时间（Toast.LENGTH_SHORT:短； Toast.LENGTH_LONG:长）
     * @param gravity  位置（Gravity.CENTER;Gravity.TOP;...）
     */
    public static void showToast(final Context context, final String text, final int textSize, final int duration, final int gravity) {
        Handler handlerThree = new Handler(Looper.getMainLooper());
        handlerThree.post(new Runnable() {
            public void run() {
                if (mToast == null) {
                    mToast = Toast.makeText(context.getApplicationContext(), null, Toast.LENGTH_SHORT);
                }

                int size = textSize;
                if (size <= 0) {
                    size = 20;
                }

                mToast.setText(text);
                LinearLayout linearLayout = (LinearLayout) mToast.getView();
                TextView textView = (TextView) linearLayout.getChildAt(0);
                textView.setTextSize(size);

                mToast.setDuration(duration);
                mToast.setGravity(gravity, 0, 0);
                mToast.show();
            }
        });
    }

    /**
     * 关闭Toast
     */
    public static void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }
    }
}