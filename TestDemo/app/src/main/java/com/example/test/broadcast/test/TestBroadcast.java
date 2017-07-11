package com.example.test.broadcast.test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by yuanzhaofeng
 * on 2017/6/29 15:11.
 * desc:
 * version:
 */

public class TestBroadcast extends BroadcastReceiver{
    private final String ACTION_TEST="com.example.test.broadcast";
    @Override
    public void onReceive(Context context, Intent intent) {
        if (ACTION_TEST.equals(intent.getAction())) {
            Toast.makeText(context, "收到通知了", Toast.LENGTH_SHORT).show();
        }
//        else {
//            Toast.makeText(context, "网络发生变化", Toast.LENGTH_SHORT).show();
//        }
    }
}
