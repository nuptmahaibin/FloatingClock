package com.avatarmind.floatingclock.service;

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

import com.avatarmind.floatingclock.util.ClockInfo;
import com.avatarmind.floatingclock.util.SharedPreferencesUtil;
import com.avatarmind.floatingclock.util.event.UpdateClockViewEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class FloatingService extends Service {
    private WindowManager windowManager;
    private WindowManager.LayoutParams layoutParams;
    private TextClock mTextClock;

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
        init();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uninit();
    }

    private void init() {
        EventBus.getDefault().register(this);
        ClockInfo clockInfo = SharedPreferencesUtil.getClockInfo(FloatingService.this);

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        layoutParams = new WindowManager.LayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        layoutParams.format = PixelFormat.RGBA_8888;
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.x = clockInfo.getX();
        layoutParams.y = clockInfo.getY();

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M || Settings.canDrawOverlays(this)) {
            mTextClock = new TextClock(getApplicationContext());
            mTextClock.setFormat24Hour("HH:mm:ss");
            mTextClock.setTextSize(clockInfo.getTextSize());
            mTextClock.setGravity(Gravity.CENTER);
            mTextClock.setTextColor(Color.BLACK);
            mTextClock.setBackgroundColor(Color.WHITE);
            mTextClock.setOnTouchListener(new FloatingOnTouchListener());

            windowManager.addView(mTextClock, layoutParams);
            windowManager.updateViewLayout(mTextClock.getRootView(), layoutParams);
        }
    }

    private void uninit() {
        EventBus.getDefault().unregister(this);
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

                    ClockInfo clockInfo = SharedPreferencesUtil.getClockInfo(FloatingService.this);
                    clockInfo.setX(layoutParams.x);
                    clockInfo.setY(layoutParams.y);
                    SharedPreferencesUtil.setClockInfo(FloatingService.this, clockInfo);
                    break;
                default:
                    break;
            }
            return false;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(UpdateClockViewEvent event) {
        if (event != null && mTextClock != null) {
            ClockInfo clockInfo = event.getClockInfo();
            mTextClock.setTextSize(clockInfo.getTextSize());
            SharedPreferencesUtil.setClockInfo(FloatingService.this, clockInfo);
        }
    }
}
