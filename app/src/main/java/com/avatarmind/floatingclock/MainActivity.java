package com.avatarmind.floatingclock;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
    private Button setTextSize;
    private EditText textsizeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textsizeView = (EditText) findViewById(R.id.textsize);

        setTextSize = (Button) findViewById(R.id.settextsize_button);
        setTextSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = textsizeView.getText().toString();

                if (TextUtils.isEmpty(text)) {
                    return;
                } else {
                    int size;
                    try {
                        size = Integer.parseInt(text);
                    } catch (Exception e) {
                        textsizeView.setText("");
                        return;
                    }

                    if (size <= 0) {
                        textsizeView.setText("");
                        return;
                    }

                    MyApp.getApplication().setTextSize(size);
                }
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
    }

    private void startService() {
        if (!FloatingService.isFloatingWindowShow) {
            Intent service = new Intent(this, FloatingService.class);
            service.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startService(service);
        }
    }

    private void stopService() {
        Intent service = new Intent(this, FloatingService.class);
        stopService(service);
    }

    private void checkOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//判断系统版本
            if (!Settings.canDrawOverlays(this)) {
                Toast.makeText(this, "当前无显示悬浮窗权限，请授权", Toast.LENGTH_SHORT);
                startActivityForResult(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName())), 0);
            } else {
                Toast.makeText(this, "当前有权限，启动服务", Toast.LENGTH_SHORT);
                startService();
            }
        } else {
            startService();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (!Settings.canDrawOverlays(this)) {
                Toast.makeText(this, "授权失败", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "授权成功", Toast.LENGTH_SHORT).show();
                startService();
            }
        }
    }
}
