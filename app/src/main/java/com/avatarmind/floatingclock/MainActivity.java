package com.avatarmind.floatingclock;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avatarmind.floatingclock.app.MyApp;
import com.avatarmind.floatingclock.service.FloatingService;
import com.avatarmind.floatingclock.utile.Constants;

public class MainActivity extends Activity {
    private static final String TAG = "FloatingClock.MainActivity";

    private Button setTextSize;
    private EditText textsizeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textsizeView = (EditText) findViewById(R.id.textsize);
        update_textsizeView();

        setTextSize = (Button) findViewById(R.id.settextsize_button);
        setTextSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ShowToast(MainActivity.this, "瓜主席威武！\r\n瓜主席666！");
                String text = textsizeView.getText().toString();
                if (TextUtils.isEmpty(text)) {
                    return;
                } else {
                    int size;
                    try {
                        size = Integer.parseInt(text);
                    } catch (Exception e) {
                        update_textsizeView();
                        return;
                    }

                    if (size <= 0) {
                        update_textsizeView();
                        return;
                    }

                    MyApp.getApplication().setTextSize(size);
                    update_textsizeView();
                    sendLocalBroadcast();
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

    private void sendLocalBroadcast() {
        final Intent intent = new Intent(Constants.ACTION_UPDATECLOCK);
        LocalBroadcastManager.getInstance(MainActivity.this).sendBroadcast(intent);
        Log.d(TAG, "sendLocalBroadcast() action = " + Constants.ACTION_UPDATECLOCK);
    }

    private void update_textsizeView() {
        textsizeView.setText("");
        textsizeView.setHint("请输入字体大小（当前为" + MyApp.getApplication().getTextSize() + "）");
    }

    private void startService() {
        Intent service = new Intent(this, FloatingService.class);
        service.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startService(service);
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

    private void ShowToast(Context context, String msg) {
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        toast.setText(msg);
        toast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout linearLayout = (LinearLayout) toast.getView();
        TextView textView = (TextView) linearLayout.getChildAt(0);
        textView.setTextSize(25);
        textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        textView.setTextColor(getColor(R.color.gold));
        toast.show();
    }
}
