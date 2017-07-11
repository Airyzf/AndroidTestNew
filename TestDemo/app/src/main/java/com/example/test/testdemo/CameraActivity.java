package com.example.test.testdemo;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CameraActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnOpen;
    private ImageView imgShow;
    private File imgFile;
    private static final int REQUEST_PERMISSION = 655;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        initView();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{android.Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            }, REQUEST_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==REQUEST_PERMISSION){
            if (grantResults!=null&&grantResults.length!=0){
                if (grantResults[0]== PackageManager.PERMISSION_GRANTED) {
                    startCamera();

                }

            }
        }
    }

    private void initView() {
        btnOpen = ((Button) findViewById(R.id.btnOpen));
        imgShow = ((ImageView) findViewById(R.id.imgShow));
        btnOpen.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        startCamera();

    }

    private void startCamera() {
        File dir=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+"PicTest");
        if (!dir.exists()){
            dir.mkdirs();
        }
        long timeMillis = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss", Locale.CHINA);
        String newDate = format.format(new Date(timeMillis));
        imgFile=new File(dir, newDate +".jpg");
        if(!imgFile.exists()){
            try {
                imgFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imgFile));
        startActivityForResult(intent, Activity.DEFAULT_KEYS_DIALER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == Activity.RESULT_OK) {
//            Bundle bundle = data.getExtras();
//            Bitmap bitmap = (Bitmap) bundle.get("data");
//            try {
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(createFile()));
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//            imgShow.setImageBitmap(bitmap);
//        }
        if (requestCode==Activity.DEFAULT_KEYS_DIALER){
            imgShow.setImageURI(Uri.fromFile(imgFile));
        }
    }

    private File createFile() {
        String path = Environment.getExternalStorageDirectory() + File.separator + "PicTest";
        Log.e("tag", "createFile: path=" + path);
        File dir = new File(path);
        if (!dir.exists()) {
            boolean mkdirs = dir.mkdirs();
            Log.e("tag", "createFile: 创建成功：" + mkdirs);
        }
        imgFile = new File(dir, System.currentTimeMillis() + ".jpg");
        if (!imgFile.exists()) {
            try {
                boolean ret = imgFile.createNewFile();
                Log.e("tag", "createFile: " + (ret==true?"成功":"失败"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            Log.e("tag", "createFile: 文件已存在" );
        }
        return imgFile;
    }
}
