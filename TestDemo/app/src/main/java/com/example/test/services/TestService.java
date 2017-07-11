package com.example.test.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.test.testdemo.MainActivity;
import com.example.test.testdemo.R;

public class TestService extends Service {
    private final String TAG = "tag";

    public TestService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Log.e(TAG, "onBind: ");
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Notification.Builder localBuilder = new Notification.Builder(this);
        localBuilder.setContentIntent(PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0));
        localBuilder.setAutoCancel(false);
        localBuilder.setSmallIcon(R.mipmap.iv_lc_icon);
        localBuilder.setTicker("Foreground Service Start");
        localBuilder.setContentTitle("音乐播放中");
        localBuilder.setContentText("正在运行...");
        startForeground(1, localBuilder.getNotification());
        Log.e(TAG, "onCreate: " );
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand: " );

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy: " );
        super.onDestroy();
    }
}
