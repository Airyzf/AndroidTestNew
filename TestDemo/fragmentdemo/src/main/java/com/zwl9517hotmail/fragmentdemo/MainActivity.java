package com.zwl9517hotmail.fragmentdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Fragment和Activity类似,具有显示控件的功能
 * Fragment:碎片
 * 生命周期伴随产生它的Activity
 * Fragment ViewPager使用
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1: {
                Fragment fragment1 = Fragment1.newInstance();
                /*FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(R.id.container, fragment1, "f1");
                transaction.commit();*/
                switchFragment(R.id.container, fragment1, "f1");
            }
            break;
            case R.id.btn2: {
                Fragment fragment2 = Fragment2.newInstance();
                /*FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(R.id.container, fragment2, "f2");
                transaction.commit();*/
                switchFragment(R.id.container, fragment2, "f2");

            }
            break;
        }
    }
}
