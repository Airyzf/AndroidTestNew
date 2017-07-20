package com.example.test.testdemo;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class HandlerActivity extends AppCompatActivity {

    private ImageView imgChange;
    int imgids[] = new int[]{
            R.mipmap.one, R.mipmap.two,R.mipmap.three,
            R.mipmap.four,R.mipmap.five,R.mipmap.six,
            R.mipmap.seven,R.mipmap.eight
    };
    int imgstart = 0;

    final Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==0x123){
                imgChange.setImageResource(imgids[imgstart++ % 8]);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);

        imgChange = ((ImageView) findViewById(R.id.imgChange));

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(0x123);
            }
        },0,200);
    }
}
