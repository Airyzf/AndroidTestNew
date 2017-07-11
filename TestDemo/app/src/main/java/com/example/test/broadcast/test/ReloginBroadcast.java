package com.example.test.broadcast.test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.test.toolhelper.AppManager;

/**
 * Created by yuanzhaofeng
 * on 2017/6/29 16:58.
 * desc:
 * version:
 */
public class ReloginBroadcast extends BroadcastReceiver{
    @Override
    public void onReceive(final Context context, Intent intent) {
//        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
//        dialogBuilder.setTitle("警告：");
//        dialogBuilder.setMessage("您的账号在别处登录，请重新登陆~");
//        dialogBuilder.setCancelable(false);
//        dialogBuilder.setPositiveButton("确定",
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        AppManager.getAppManager().finishAllActivity();
//                        Intent intent = new Intent(context, LoginActivity.class);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        context.startActivity(intent);
//                    }
//                });
//        AlertDialog alertDialog = dialogBuilder.create();
//        alertDialog.getWindow().setType(
//                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
//        alertDialog.show();

        Toast.makeText(context, "您的账号在别处登录，请重新登陆~", Toast.LENGTH_SHORT).show();
        AppManager.getAppManager().finishAllActivity();

    }
}
