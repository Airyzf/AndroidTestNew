package com.example.test.testdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FragmentTestActivity extends BaseActivity implements View.OnClickListener{

    private Button btnFrgm1;
    private Button btnFrgm2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_test);

        btnFrgm1 = ((Button) findViewById(R.id.btnFgmt1));
        btnFrgm2 = ((Button) findViewById(R.id.btnFgmt2));

        btnFrgm1.setOnClickListener(this);
        btnFrgm2.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnFgmt1:
                FragmentUI1 ui1=FragmentUI1.newInstance();
                showFragment(R.id.container,ui1,"ui1");
                break;
            case R.id.btnFgmt2:
                FragmentUI2 ui2=FragmentUI2.newInstance();
                showFragment(R.id.container,ui2,"ui2");
                break;
        }
    }
}
