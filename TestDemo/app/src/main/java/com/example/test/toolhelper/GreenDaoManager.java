package com.example.test.toolhelper;

import com.example.test.testdemo.SQLiteActivity;
import com.example.test.testdemo.greendao.gen.DaoMaster;
import com.example.test.testdemo.greendao.gen.DaoSession;

/**
 * Created by yuanzhaofeng
 * on 2017/7/20 15:07.
 * desc:
 * version:
 */
public class GreenDaoManager {
    private static GreenDaoManager greenDaoManager;
    private DaoMaster daoMaster;
    private DaoSession daoSession;

    private GreenDaoManager() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(SQLiteActivity.getMyContext(), "test-db", null);
        daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
        daoSession = daoMaster.newSession();
    }

    public static GreenDaoManager getInstance(){
        if (greenDaoManager==null){
            greenDaoManager=new GreenDaoManager();
        }
        return greenDaoManager;
    }

    public DaoSession getDaoSession(){
        return daoSession;
    }

    public DaoSession getNewDaoSession(){
        daoSession=daoMaster.newSession();
        return daoSession;
    }
}
