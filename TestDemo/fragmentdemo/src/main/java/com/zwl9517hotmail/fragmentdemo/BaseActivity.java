package com.zwl9517hotmail.fragmentdemo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by ZouWeiLin
 * on 2017/7/11  17:48
 * version:
 * desc:
 */
public class BaseActivity extends AppCompatActivity {

    private Fragment currentFragment;

    /**
     * 传进来的Fragment是否已经add,如果add,显示出来,如果没有add,就add
     * 如果要显示其他fragment,隐藏当前显示的fragment
     *
     * @param fragment
     * @param tag
     * @param containerId
     */
    protected void switchFragment(int containerId, Fragment fragment, String tag) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();

        Fragment fragmentByTag = manager.findFragmentByTag(tag);

        if (currentFragment != null) {
            fragmentTransaction.hide(currentFragment);
        }

        if (fragmentByTag != null) {
            fragmentTransaction.show(fragmentByTag);
        } else {
            fragmentTransaction.add(containerId, fragment, tag);
            fragmentByTag = fragment;
        }
        currentFragment = fragmentByTag;
        fragmentTransaction.commit();
    }
}
