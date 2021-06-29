package com.avatarmind.floatingclock.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextClock;

import com.avatarmind.floatingclock.util.Constants;
import com.avatarmind.floatingclock.util.SharedPreferencesUtil;

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

        initView();
        showFloatingWindow();
        registerBroadcast();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        removeFloatingWindow();
        unregisterReceiver();
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
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.x = SharedPreferencesUtil.getSharedPreferencesValue(FloatingService.this, SharedPreferencesUtil.x, SharedPreferencesUtil.defaultX);
        layoutParams.y = SharedPreferencesUtil.getSharedPreferencesValue(FloatingService.this, SharedPreferencesUtil.y, SharedPreferencesUtil.defaultY);
    }

    public void showFloatingWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//判断系统版本
            if (Settings.canDrawOverlays(this)) {
                updateTextClock();
            }
        } else {
            updateTextClock();
        }
    }

    public void removeFloatingWindow() {
        windowManager.removeView(mTextClock);
    }

    private void updateTextClock() {
        mTextClock = new TextClock(getApplicationContext());
        mTextClock.setOnTouchListener(new FloatingOnTouchListener());
        mTextClock.setFormat24Hour("HH:mm:ss");
        int clockSize = SharedPreferencesUtil.getSharedPreferencesValue(FloatingService.this, SharedPreferencesUtil.clockSize, SharedPreferencesUtil.defaultTextSize);
        mTextClock.setTextSize(clockSize);
        mTextClock.setGravity(Gravity.CENTER);
        mTextClock.setTextColor(Color.BLACK);
        mTextClock.setBackgroundColor(Color.WHITE);
        windowManager.addView(mTextClock, layoutParams);
        windowManager.updateViewLayout(mTextClock.getRootView(), layoutParams);
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
                    SharedPreferencesUtil.setSharedPreferencesValue(FloatingService.this, SharedPreferencesUtil.x, layoutParams.x);
                    SharedPreferencesUtil.setSharedPreferencesValue(FloatingService.this, SharedPreferencesUtil.y, layoutParams.y);
                    break;
                default:
                    break;
            }
            return false;
        }
    }

    private void registerBroadcast() {
        IntentFilter intentFilter = new IntentFilter(Constants.ACTION_UPDATECLOCK);
        LocalBroadcastManager.getInstance(this).registerReceiver(clockReceiver, intentFilter);
    }

    private void unregisterReceiver() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(clockReceiver);
    }

    private final BroadcastReceiver clockReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (TextUtils.equals(action, Constants.ACTION_UPDATECLOCK)) {
                int clockSize = SharedPreferencesUtil.getSharedPreferencesValue(FloatingService.this, SharedPreferencesUtil.clockSize, SharedPreferencesUtil.defaultTextSize);
                mTextClock.setTextSize(clockSize);
            } else {
            }
        }
    };
}
