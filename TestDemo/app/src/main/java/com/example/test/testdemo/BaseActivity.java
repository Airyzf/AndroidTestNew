package com.example.test.testdemo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.test.toolhelper.AppManager;

public class BaseActivity extends AppCompatActivity {

    private Fragment currFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
    }

    protected void showFragment(int containerId, Fragment fragment, String tag) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment fragmentTag = manager.findFragmentByTag(tag);
        if (currFragment != null) {
            transaction.hide(currFragment);
        }
        if (fragment != fragmentTag) {
            transaction.add(containerId, fragment, tag);
        }
        transaction.show(fragment);
        currFragment = fragment;
        transaction.commit();
    }
}
