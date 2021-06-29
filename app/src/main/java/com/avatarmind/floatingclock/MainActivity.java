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
import android.widget.Toast;

import com.avatarmind.floatingclock.service.FloatingService;
import com.avatarmind.floatingclock.util.Constants;
import com.avatarmind.floatingclock.util.SharedPreferencesUtil;
import com.avatarmind.floatingclock.util.ToastUtil;
import com.avatarmind.floatingclock.util.Util;

public class MainActivity extends Activity {
    private TextView mTVClockSize;
    private EditText mEtClockSize;
    private Switch mSwitchCloseClock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferencesUtil.initSharedPreferences(MainActivity.this);

        mTVClockSize = (TextView) findViewById(R.id.tv_clocksize);
        int clockSize = SharedPreferencesUtil.getSharedPreferencesValue(MainActivity.this, SharedPreferencesUtil.clockSize, SharedPreferencesUtil.defaultTextSize);
        mTVClockSize.setText(getString(R.string.currentclocksize) + clockSize);

        mEtClockSize = (EditText) findViewById(R.id.et_clocksize);
        mEtClockSize.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = mEtClockSize.getText().toString();
                if (TextUtils.isEmpty(text)) {
                    return;
                }

                try {
                    int size = Integer.parseInt(text);
                    if (size <= 0 || size > 100) {
                        ToastUtil.remind(MainActivity.this, getString(R.string.clocksizeremind), Toast.LENGTH_SHORT);
                        mEtClockSize.setText("");
                        return;
                    }

                    SharedPreferencesUtil.setSharedPreferencesValue(MainActivity.this, SharedPreferencesUtil.clockSize, size);
                    Util.sendLocalBroadcast(MainActivity.this, Constants.ACTION_UPDATECLOCK);
                    mTVClockSize.setText(getString(R.string.currentclocksize) + size);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        mSwitchCloseClock = (Switch) findViewById(R.id.st_close_clock);
        mSwitchCloseClock.setChecked(SharedPreferencesUtil.getSharedPreferencesValue(MainActivity.this, SharedPreferencesUtil.isCloseClock, false));
        mSwitchCloseClock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferencesUtil.setSharedPreferencesValue(MainActivity.this, SharedPreferencesUtil.isCloseClock, isChecked);
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
        if (SharedPreferencesUtil.getSharedPreferencesValue(MainActivity.this, SharedPreferencesUtil.isCloseClock, false))
            Util.stopService(MainActivity.this, FloatingService.class);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (!Settings.canDrawOverlays(this)) {
                ToastUtil.remind(MainActivity.this, "授权失败", Toast.LENGTH_SHORT);
            } else {
                ToastUtil.error(MainActivity.this, "授权成功", Toast.LENGTH_SHORT);
                Util.startService(MainActivity.this, FloatingService.class);
            }
        }
    }

    private void checkOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//判断系统版本
            if (!Settings.canDrawOverlays(this)) {
                ToastUtil.remind(MainActivity.this, "应用没有显示悬浮窗权限，请授权", Toast.LENGTH_SHORT);
                startActivityForResult(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName())), 0);
            } else {
                Util.startService(MainActivity.this, FloatingService.class);
            }
        } else {
            Util.startService(MainActivity.this, FloatingService.class);
        }
    }


}
