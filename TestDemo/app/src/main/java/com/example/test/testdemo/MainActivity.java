package com.example.test.testdemo;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.test.broadcast.test.TestBroadcast;
import com.example.test.model.Person;
import com.example.test.toolhelper.AsyncHttpClientHelper;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends BaseActivity {

    private Button btnWechat;
    private Button btnGetResult;
    private TestBroadcast testBroadcast;
    private Button btnBroadcast;
    private Button btnLoginTest;
    private Button btnNotifycation;
    private Button btnService;
    private Button btnStopService;
    private Button btnStartIntentService;
    private Button btnStopIntentService;
    private Button btnOpenCamera;
    private Button btnDeleteFile;
    private Button btnIntentTest;
    private Button btnFragmentTest;
    private Button btnTest1;
    private Button btnTest2;
    private String Tag = "tag";

    private int code;
    private Button btnBaiduSpeech;
    private Button btnFileTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initBroadcast();
        requestPermi();
        //readMsgContentProvider();
        //insertMsgContentProvider();

    }

    /**
     * 6.0请求所需权限
     */
    private void requestPermi() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.RECORD_AUDIO,

            },100);
        }
    }

    //4.4以下可用
    private void insertMsgContentProvider() {
        ContentResolver resolver = getContentResolver();
        Uri uri = Uri.parse("content://sms/");
        ContentValues values = new ContentValues();
        values.put("address", "123456");
        values.put("type", 1);
        values.put("date", System.currentTimeMillis());
        values.put("body", "this is a test message");
        Uri ret = resolver.insert(uri, values);
        Log.e("tag", "insertMsgContentProvider: " + ret.toString());
    }

    //4.4以下可用
    private void readMsgContentProvider() {
        Uri uri = Uri.parse("content://sms/");
        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(uri, new String[]{"address", "date", "type", "body"}, null, null, null);
        while (cursor.moveToNext()) {
            String address = cursor.getString(0);
            String date = cursor.getString(1);
            String type = cursor.getString(2);
            String body = cursor.getString(3);
            System.out.println("地址:" + address);
            System.out.println("时间:" + date);
            System.out.println("类型:" + type);
            System.out.println("内容:" + body);
            System.out.println("======================");
        }
        cursor.close();
    }

    /**
     * 监听系统广播
     */
    private void initBroadcast() {
        this.testBroadcast = new TestBroadcast();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(testBroadcast, intentFilter);
    }

    private void initViews() {
        btnWechat = (Button) findViewById(R.id.btnWechat);
        btnGetResult = (Button) findViewById(R.id.btnGetResult);
        btnBroadcast = ((Button) findViewById(R.id.btnBroadcast));
        btnLoginTest = ((Button) findViewById(R.id.btnLogin));
        btnNotifycation = ((Button) findViewById(R.id.btnNotification));
        btnService = ((Button) findViewById(R.id.btnService));
        btnStopService = ((Button) findViewById(R.id.btnStopService));
        btnStartIntentService = ((Button) findViewById(R.id.btnIntentService));
        btnStopIntentService = ((Button) findViewById(R.id.btnStopIntentService));
        btnOpenCamera = ((Button) findViewById(R.id.btnCamera));
        btnDeleteFile = ((Button) findViewById(R.id.btnDelete));
        btnIntentTest = ((Button) findViewById(R.id.btnIntentTest));
        btnFragmentTest = ((Button) findViewById(R.id.btnFragmentTest));
        btnTest1 = ((Button) findViewById(R.id.btnTest1));
        btnTest2 = ((Button) findViewById(R.id.btnTest2));
        btnBaiduSpeech = ((Button) findViewById(R.id.btnBaiduSpeech));
        btnFileTest = ((Button) findViewById(R.id.btnFileTest));

        btnWechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                Intent intent = new Intent();
                Person p = new Person();
                p.setName("图灵");
                p.setNum("6655");
                bundle.putSerializable("person", p);
                intent.putExtra("bundle", bundle);
                intent.setClass(MainActivity.this, WechatActivity.class);
                startActivity(intent);
                Log.e("tag", "onClick: ");
            }
        });

        btnGetResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this,"confirm click",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                intent.putExtra("testkey", "hello");
                startActivityForResult(intent, 10010);

            }
        });
        //发送自定义广播
        btnBroadcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sendBroadcast(new Intent("com.example.test.broadcast"));
                //getContacts();
                try {
                    //TestContact();
                    getContacts();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("tag", e.toString());
                }
            }
        });

        btnLoginTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("user", "wltk");
                bundle.putString("pwd", "wltk8888");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        btnNotifycation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
                startActivity(intent);
            }
        });

        final Intent intent = new Intent();
        intent.setPackage("com.example.test.testdemo");
        intent.setAction("com.example.test.services.TestService");
        btnService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(intent);
            }
        });

        btnStopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(intent);
            }
        });

        btnStartIntentService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent("com.example.test.services.TestIntentService");
                intent1.setPackage("com.example.test.testdemo");
                Bundle bundle = new Bundle();
                bundle.putString("ACTION", "s1");
                intent1.putExtras(bundle);

                Intent intent2 = new Intent("com.example.test.services.TestIntentService");
                intent2.setPackage("com.example.test.testdemo");
                Bundle bundle2 = new Bundle();
                bundle2.putString("ACTION", "s2");
                intent2.putExtras(bundle2);

                Intent intent3 = new Intent("com.example.test.services.TestIntentService");
                intent3.setPackage("com.example.test.testdemo");
                Bundle bundle3 = new Bundle();
                bundle3.putString("ACTION", "s3");
                intent3.putExtras(bundle3);

                startService(intent1);
                startService(intent2);
                startService(intent3);
            }
        });

        btnStopIntentService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnOpenCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CameraActivity.class));
//                try {
//                    insertMsgContentProvider();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    Log.e("tag", "onClick: "+e.getMessage() );
//                }
            }
        });

        btnDeleteFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //File dir=new File((Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+"PicTest"));
//                File dir=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Android/data/com.nowcasting.activity/files/");
//                DeleteFiles(dir);
//                dir=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/LSSportrelease/log/user11686488/sleepLog/");
//                DeleteFiles(dir);
                getPermission();

            }


        });

        btnIntentTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                it.setData(Contacts.People.CONTENT_URI);
                startActivity(it);

            }
        });

        btnFragmentTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FragmentTestActivity.class));
            }
        });

        btnTest1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncHttpClient client = new AsyncHttpClient();
                client.get("http://www.baidu.com", new AsyncHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        if (statusCode == 200) {
                            String res = new String(responseBody);
                            Log.e("tag", "onSuccess: " + res);
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    }
                });
            }
        });

        btnTest2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    AsyncHttpClientHelper.get("http://music.163.com/api/playlist/detail?id=2884035", null, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            // If the response is JSONObject instead of expected JSONArray
                            Log.e("tag", "onSuccess: ");

                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                            // Pull out the first event on the public timeline
                            Log.e("tag", "onSuccess: ");
                        }

                    });
                } catch (Exception e) {
                    Log.e("tag", "onClick: " + e.getMessage());
                }
            }
        });

        btnBaiduSpeech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BaiduSpeechRecognizeActivity.class));
            }
        });

        btnFileTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FileReadWriteActivity.class));
            }
        });
    }

    private void DeleteAll() {
        File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/LSSportrelease/log/");
        DeleteFiles(dir);
        dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/LSSportrelease/networkLog/");
        DeleteFiles(dir);
        Toast.makeText(MainActivity.this, "已删除 " + fileCount, Toast.LENGTH_SHORT).show();
        fileCount = 0;
    }

    int fileCount = 0;

    private void DeleteFiles(File dir) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    file.delete();
                    fileCount++;
                } else if (file.isDirectory()) {
                    DeleteFiles(file);
                }
                dir.delete();
            }
        }
    }

    private void getPermission() {
        int permissionCheck1 = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionCheck2 = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck1 != PackageManager.PERMISSION_GRANTED || permissionCheck2 != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 124);
        } else {
            DeleteAll();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 124) {
            if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                DeleteAll();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10010 && resultCode == 10020) {
            Bundle bundle = data.getExtras();
            String string = ((String) bundle.getString("contentkey"));
            Toast.makeText(MainActivity.this, string, Toast.LENGTH_LONG).show();
        } else if (resultCode == Activity.RESULT_OK) {
            Bundle bundle = data.getExtras();
            Bitmap bitmap = (Bitmap) bundle.get("data");

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(testBroadcast);
    }

    public void getContacts() {
        ContentResolver resolver = getContentResolver();
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Cursor cursor = resolver.query(uri, null, null, null, null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String num = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            Log.e("tag", name + " " + num);
        }
        cursor.close();
    }

    //查询所有联系人的姓名，电话，邮箱
    public void ReadContact() throws Exception {
        Log.i("tag", "TestContact");
        Uri uri = Uri.parse("content://com.android.contacts/contacts");
        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(uri, new String[]{"_id"}, null, null, null);
        while (cursor.moveToNext()) {
            int contractID = cursor.getInt(0);
            StringBuilder sb = new StringBuilder("contractID=");
            sb.append(contractID);
            uri = Uri.parse("content://com.android.contacts/contacts/" + contractID + "/data");
            Cursor cursor1 = resolver.query(uri, new String[]{"mimetype", "data1", "data2"}, null, null, null);
            while (cursor1.moveToNext()) {
                String data1 = cursor1.getString(cursor1.getColumnIndex("data1"));
                String mimeType = cursor1.getString(cursor1.getColumnIndex("mimetype"));
                if ("vnd.android.cursor.item/name".equals(mimeType)) { //是姓名
                    sb.append(",name=" + data1);
                } else if ("vnd.android.cursor.item/email_v2".equals(mimeType)) { //邮箱
                    sb.append(",email=" + data1);
                } else if ("vnd.android.cursor.item/phone_v2".equals(mimeType)) { //手机
                    sb.append(",phone=" + data1);
                }
            }
            cursor1.close();
            Log.i("tag", sb.toString());
        }
        cursor.close();
    }

}
