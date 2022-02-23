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

    private ToastUtil() {
    }

    public static void showToast(Context context, String text) {
        showToast(context, text, 25, Toast.LENGTH_LONG, Gravity.CENTER);
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
        Runnable toastRunnable = new Runnable() {
            @Override
            public void run() {
                if (mToast == null) {
                    mToast = Toast.makeText(context, "", duration);
                }

                try {
                    LinearLayout linearLayout = (LinearLayout) mToast.getView();
                    TextView textView = (TextView) linearLayout.getChildAt(0);
                    textView.setTextSize(textSize);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                mToast.setDuration(duration);
                mToast.setGravity(gravity, 0, 0);
                mToast.setText(text);
                mToast.show();
            }
        };

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(toastRunnable);
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