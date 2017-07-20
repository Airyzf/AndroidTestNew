package com.example.test.testdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.lang.ref.WeakReference;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ThreadHandlerActivity extends AppCompatActivity {

    private static final int DOWNLOAD_FAILED = 0x123;
    private static final int DOWNLOAD_SUCCESS = 0x124;
    private static final int DOWNLOAD_ERROR = 0x125;
    private ImageView imgDownload;
    private final ThreadHandler handler=new ThreadHandler(this);

    private static class ThreadHandler extends Handler {
        private final WeakReference<ThreadHandlerActivity> mActivity;

        public ThreadHandler(ThreadHandlerActivity activity) {
            this.mActivity = new WeakReference<ThreadHandlerActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            ThreadHandlerActivity handlerActivity = mActivity.get();
            if (handlerActivity == null) {
                super.handleMessage(msg);
                return;
            }
            switch (msg.what) {
                case DOWNLOAD_SUCCESS:
                    Toast.makeText(handlerActivity, "下载成功", Toast.LENGTH_SHORT).show();
                    Bitmap bitmap = (Bitmap) msg.obj;
                    handlerActivity.imgDownload.setVisibility(View.VISIBLE);
                    handlerActivity.imgDownload.setImageBitmap(bitmap);
                    break;
                case DOWNLOAD_FAILED:
                    Toast.makeText(handlerActivity, "下载失败", Toast.LENGTH_SHORT).show();
                    break;
                case DOWNLOAD_ERROR:
                    Toast.makeText(handlerActivity, "下载错误", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_handler);

        imgDownload = ((ImageView) findViewById(R.id.imgDownload));
        //Glide.with(ThreadHandlerActivity.this).asGif().load("http://img.zcool.cn/community/01c5125775e6e50000018c1b7366b3.gif").into(imgDownload);

        downloadImage();
    }

    private void downloadImage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = download();
                Message msg = Message.obtain();
                msg.obj = bitmap;
                msg.what = bitmap == null ? DOWNLOAD_FAILED : DOWNLOAD_SUCCESS;
                handler.sendMessage(msg);
            }
        }).start();
    }

    private Bitmap download() {
        String url = "http://www.33lc.com/article/UploadPic/2012-9/201291316472535004.jpg";
        //Resources res = ThreadHandlerActivity.this.getResources();
        //Bitmap bmp= BitmapFactory.decodeResource(res, R.mipmap.eva);

        return null;
    }

    //Http请求
    OkHttpClient client = new OkHttpClient();
    private String runOkHttp(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }
    private Bitmap Bytes2Bimap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }


}
