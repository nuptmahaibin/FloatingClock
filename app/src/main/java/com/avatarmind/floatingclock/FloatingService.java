package com.avatarmind.floatingclock;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextClock;

public class FloatingService extends Service {
    private static final String TAG = "FloatingClock";
    private WindowManager windowManager;
    private WindowManager.LayoutParams layoutParams;
    private TextClock mTextClock;
    public static boolean isFloatingWindowShow = false;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        MyApp.getApplication().initSharedPreferences();
        initView();
        showFloatingWindow();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //removeFloatingWindow();
    }

    private void initView() {
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        layoutParams = new WindowManager.LayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        layoutParams.format = PixelFormat.RGBA_8888;
        //layoutParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        layoutParams.width = MyApp.getApplication().getWidth();
        layoutParams.height = MyApp.getApplication().getHeight();
        layoutParams.x = MyApp.getApplication().getX();
        layoutParams.y = MyApp.getApplication().getY();
    }

    public void showFloatingWindow() {
        isFloatingWindowShow = true;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//判断系统版本
            if (Settings.canDrawOverlays(this)) {
                setTextClock();
            }
        } else {
            setTextClock();
        }
    }

    private void setTextClock() {
        mTextClock = new TextClock(getApplicationContext());
        mTextClock.setOnTouchListener(new FloatingOnTouchListener());
        mTextClock.setFormat24Hour("HH:mm:ss");
        mTextClock.setTextSize(MyApp.getApplication().getTextSize());
        mTextClock.setGravity(Gravity.CENTER);
        mTextClock.setTextColor(Color.BLACK);
        mTextClock.setBackgroundColor(Color.WHITE);
        windowManager.addView(mTextClock, layoutParams);
        windowManager.updateViewLayout(mTextClock.getRootView(), layoutParams);
    }

    public void removeFloatingWindow() {
        isFloatingWindowShow = false;
        windowManager.removeView(mTextClock);
    }

    private class FloatingOnTouchListener implements View.OnTouchListener {
        private int x;
        private int y;

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    x = (int) event.getRawX();
                    y = (int) event.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    int nowX = (int) event.getRawX();
                    int nowY = (int) event.getRawY();
                    int movedX = nowX - x;
                    int movedY = nowY - y;
                    x = nowX;
                    y = nowY;
                    layoutParams.x = layoutParams.x + movedX;
                    layoutParams.y = layoutParams.y + movedY;
                    windowManager.updateViewLayout(view, layoutParams);
                    MyApp.getApplication().updatePosition(layoutParams.x, layoutParams.y);
                    break;
                default:
                    break;
            }
            return false;
        }
    }
}
