package com.avatarmind.floatingclock;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.avatarmind.floatingclock.service.FloatingService;
import com.avatarmind.floatingclock.util.ClockInfo;
import com.avatarmind.floatingclock.util.LogUtil;
import com.avatarmind.floatingclock.util.SharedPreferencesUtil;
import com.avatarmind.floatingclock.util.ToastUtil;
import com.avatarmind.floatingclock.util.Util;
import com.avatarmind.floatingclock.util.event.UpdateClockViewEvent;

import org.greenrobot.eventbus.EventBus;

public class MainActivity extends Activity {
    private TextView mTVClockSize;
    private EditText mEtClockSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferencesUtil.initSharedPreferences(MainActivity.this);
        ClockInfo clockInfo = SharedPreferencesUtil.getClockInfo(MainActivity.this);

        mTVClockSize = (TextView) findViewById(R.id.tv_clocksize);
        mTVClockSize.setText(getString(R.string.currentclocksize) + clockInfo.getTextSize());

        mEtClockSize = (EditText) findViewById(R.id.et_clocksize);
        mEtClockSize.setText(String.valueOf(clockInfo.getTextSize()));
        mEtClockSize.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                LogUtil.i("afterTextChanged()");

                String text = mEtClockSize.getText().toString();
                if (TextUtils.isEmpty(text)) {
                    return;
                }

                ClockInfo clockInfo = SharedPreferencesUtil.getClockInfo(MainActivity.this);
                int size = Integer.parseInt(text);

                try {
                    if (size <= 0 || size > 100) {
                        ToastUtil.showToast(MainActivity.this, getString(R.string.clocksizeremind));
                        mEtClockSize.setText("");
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                clockInfo.setTextSize(size);
                EventBus.getDefault().post(new UpdateClockViewEvent(clockInfo));
                mTVClockSize.setText(getString(R.string.currentclocksize) + clockInfo.getTextSize());
            }
        });

        Switch switchCloseClock = (Switch) findViewById(R.id.st_close_clock);
        switchCloseClock.setChecked(SharedPreferencesUtil.isExit(MainActivity.this));
        switchCloseClock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferencesUtil.setIsExit(MainActivity.this, isChecked);
            }
        });

        checkOverlayPermission();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            super.onSaveInstanceState(outState);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (!SharedPreferencesUtil.isExit(MainActivity.this))
            Util.stopService(MainActivity.this, FloatingService.class);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (!Settings.canDrawOverlays(this)) {
                ToastUtil.showToast(MainActivity.this, "授权失败");
            } else {
                ToastUtil.showToast(MainActivity.this, "授权成功");
                Util.startService(MainActivity.this, FloatingService.class);
            }
        }
    }

    private void checkOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//判断系统版本
            if (!Settings.canDrawOverlays(this)) {
                ToastUtil.showToast(MainActivity.this, "应用没有显示悬浮窗权限，请授权");
                startActivityForResult(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName())), 0);
            } else {
                Util.startService(MainActivity.this, FloatingService.class);
            }
        } else {
            Util.startService(MainActivity.this, FloatingService.class);
        }
    }
}
