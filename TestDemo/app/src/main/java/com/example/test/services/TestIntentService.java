package com.example.test.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;

import com.example.test.testdemo.MainActivity;
import com.example.test.testdemo.R;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class TestIntentService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "ACTION";
    private static final String ACTION_BAZ = "BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "PARAM1";
    private static final String EXTRA_PARAM2 = "PARAM2";
    private MediaPlayer mp;
    private final String TAG="tag";
    public TestIntentService() {

        super("TestIntentService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, TestIntentService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, TestIntentService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    public void onCreate() {
        mp=MediaPlayer.create(this,R.raw.background_music);
        Log.e(TAG, "onCreate: " );
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getExtras().getString(ACTION_FOO);
            if (action.equals("s1")) {
                Log.e(TAG, "handleActionFoo: s1");
                Notification.Builder localBuilder = new Notification.Builder(this);
                localBuilder.setContentIntent(PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0));
                localBuilder.setAutoCancel(false);
                localBuilder.setSmallIcon(R.mipmap.iv_lc_icon);
                localBuilder.setTicker("Foreground Service Start");
                localBuilder.setContentTitle("音乐播放中");
                localBuilder.setContentText("正在运行...");
                startForeground(1, localBuilder.getNotification());
                mp.start();
                Log.e(TAG, "onHandleIntent: end" );

            }
            else if (action.equals("s2")) {
                Log.e(TAG, "handleActionFoo: s2");
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else if (action.equals("s3")) {
                if (mp!=null) {
                    mp.stop();
                }
                Log.e(TAG, "handleActionFoo: s3");
            }
        }
    }


}
