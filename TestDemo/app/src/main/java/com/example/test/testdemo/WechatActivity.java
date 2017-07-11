package com.example.test.testdemo;

import android.os.Bundle;
import android.widget.TextView;

import com.example.test.model.Person;

public class WechatActivity extends BaseActivity {

    private TextView tvName;
    private TextView tvNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wechat);

        initView();
    }

    private void initView() {
        tvName = ((TextView) findViewById(R.id.tvName));
        tvNum = ((TextView) findViewById(R.id.tvNum));
        Bundle bundle = getIntent().getBundleExtra("bundle");
        Person p = ((Person) bundle.getSerializable("person"));
        //String b = getIntent().getStringExtra("bytekey");
        tvName.setText(String.valueOf(p.getName()));
        tvNum.setText(String.valueOf(p.getNum()));
    }
}
