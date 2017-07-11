package com.example.test.testdemo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends BaseActivity {

    private TextView tvResult;
    private TextView tvTest;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        intiView();
        threadInit();
    }

    private void intiView() {
        String test = ((String) getIntent().getStringExtra("testkey"));
        btnBack = ((Button) findViewById(R.id.btnBack));
        tvTest = ((TextView) findViewById(R.id.tvTest));
        tvResult = ((TextView) findViewById(R.id.tvResult));
        String content = (String) tvResult.getText();
        tvTest.setText(test);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                intent.putExtra("contentkey","I am back");

                setResult(10020,intent);
                finish();
            }
        });
    }

    private void threadInit() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int i=0;
                while (i<1000)
                {
                    Log.e("tag", "run: "+i);
                    i++;
                }
            }
        }).start();
    }
}
