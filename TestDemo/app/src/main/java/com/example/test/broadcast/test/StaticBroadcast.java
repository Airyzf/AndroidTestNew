package com.example.test.broadcast.test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class StaticBroadcast extends BroadcastReceiver {
    private final String FLY_MODE="Intent.ACTION_AIRPLANE_MODE_CHANGED";
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        if (Intent.ACTION_AIRPLANE_MODE_CHANGED.equals(intent.getAction())) {
            Toast.makeText(context, "飞行模式改变", Toast.LENGTH_SHORT).show();

        }
        Log.e("tag", "飞行模式改变: " );
    }
}
