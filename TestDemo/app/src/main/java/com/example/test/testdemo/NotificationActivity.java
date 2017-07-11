package com.example.test.testdemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class NotificationActivity extends AppCompatActivity implements View.OnClickListener{

    private Context mcontext;
    private NotificationManager notificationmanager;
    private Notification notify;
    Bitmap LargeBitmap;
    private static final int NOTIFYID_1 =1;
    private Button btnShow;
    private Button btnHide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        mcontext=NotificationActivity.this;
        btnShow = ((Button) findViewById(R.id.btnShow));
        btnHide = ((Button) findViewById(R.id.btnHide));
        LargeBitmap= BitmapFactory.decodeResource(getResources(),R.mipmap.iv_lc_icon);
        notificationmanager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        initView();
    }

    private void initView() {
        btnShow.setOnClickListener(this);
        btnHide.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnShow:
                //定义一个PendingIntent点击Notification后启动一个Activity
                Intent it = new Intent(mcontext, OtherActivity.class);
                PendingIntent pit = PendingIntent.getActivity(mcontext, 0, it, 0);

                //设置图片,通知标题,发送时间,提示方式等属性
                Notification.Builder mBuilder = new Notification.Builder(this);
                mBuilder.setContentTitle("空灵")                        //标题
                        .setContentText("收红包啦")      //内容
                        .setSubText("--记住我叫空灵")                    //内容下面的一小段文字
                        .setTicker("收到空灵发送过来的信息~")             //收到信息后状态栏显示的文字信息
                        .setWhen(System.currentTimeMillis())           //设置通知时间
                        .setSmallIcon(R.mipmap.ic_lol_icon)            //设置小图标
                        .setLargeIcon(LargeBitmap)                     //设置大图标
                        .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE)    //设置默认的三色灯与振动器
                        .setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.biaobiao))  //设置自定义的提示音
                        .setAutoCancel(true)                           //设置点击后取消Notification
                        .setContentIntent(pit);                        //设置PendingIntent
                notify = mBuilder.build();
                notificationmanager.notify(NOTIFYID_1, notify);
                break;

            case R.id.btnHide:
                //除了可以根据ID来取消Notification外,还可以调用cancelAll();关闭该应用产生的所有通知
                notificationmanager.cancel(NOTIFYID_1);                          //取消Notification
                break;
        }
    }
}



