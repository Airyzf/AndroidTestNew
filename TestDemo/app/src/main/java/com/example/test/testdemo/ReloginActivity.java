package com.example.test.testdemo;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.Button;

import com.example.test.broadcast.test.ReloginBroadcast;

public class ReloginActivity extends BaseActivity {

    private Button btnBroadcast;
    private LocalBroadcastManager localBroadcastManager;
    private ReloginBroadcast reloginBroadcast;
    private IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relogin);
        initLocalBroadcast();
        btnBroadcast = ((Button) findViewById(R.id.btnBroadcast));
        btnBroadcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("loginOther");
                localBroadcastManager.sendBroadcast(intent);
                sendBroadcast(intent);
            }
        });
    }

    private void initLocalBroadcast(){
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        reloginBroadcast = new ReloginBroadcast();
        intentFilter = new IntentFilter();
        intentFilter.addAction("loginOther");
        localBroadcastManager.registerReceiver(reloginBroadcast,intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(reloginBroadcast);
        //unregisterReceiver(reloginBroadcast);
    }
}
