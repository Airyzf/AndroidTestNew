package com.example.test.testdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.test.entity.User;
import com.example.test.entity.UserAdapter;
import com.example.test.testdemo.greendao.gen.UserDao;
import com.example.test.toolhelper.GreenDaoManager;

import java.util.ArrayList;
import java.util.List;

public class SQLiteActivity extends AppCompatActivity implements View.OnClickListener {

    private static Context MyContext;
    private EditText edtName;
    private EditText edtOldName;
    private Button btnAdd;
    private Button btnDelete;
    private Button btnUpdate;
    private ListView lvUsers;

    private List<User> userList = new ArrayList<>();
    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);
        MyContext=getApplicationContext();
        intiViews();
        intiDatabase();

    }

    private void intiViews() {
        edtName = ((EditText) findViewById(R.id.edtName));
        edtOldName = ((EditText) findViewById(R.id.edtOldName));
        btnAdd = ((Button) findViewById(R.id.btnAdd));
        btnDelete = ((Button) findViewById(R.id.btnDelete));
        btnUpdate = ((Button) findViewById(R.id.btnUpdate));
        lvUsers = ((ListView) findViewById(R.id.lvUsers));
        btnAdd.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);

    }

    private void updateUser(String oldName, String newName) {
        UserDao userDao = GreenDaoManager.getInstance().getDaoSession().getUserDao();
        User user = userDao.queryBuilder().where(UserDao.Properties.Name.eq(oldName)).build().unique();
        if (user != null) {
            user.setName(newName);
            userDao.update(user);
            updateUserList(userDao);
            Toast.makeText(this, "更新成功", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "更新失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteUser(String name) {
        UserDao userDao = GreenDaoManager.getInstance().getDaoSession().getUserDao();
        User user = userDao.queryBuilder().where(UserDao.Properties.Name.eq(name)).build().unique();
        if (user != null) {
            userDao.deleteByKey(user.getId());
            updateUserList(userDao);
            Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
        }
    }

    private void insertUser(Long id, String name) {
        UserDao userDao = GreenDaoManager.getInstance().getDaoSession().getUserDao();
        User user = new User(id, name);
        userDao.insert(user);
        updateUserList(userDao);
        Toast.makeText(this, "insert成功", Toast.LENGTH_SHORT).show();
    }

    private void updateUserList(UserDao userDao){
        edtName.setText("");
        userList.clear();
        userList.addAll(userDao.queryBuilder().build().list());
        userAdapter.notifyDataSetChanged();
    }

    private void intiDatabase() {
        userList = GreenDaoManager.getInstance().getDaoSession().getUserDao().queryBuilder().build().list();
        userAdapter = new UserAdapter(this, userList);
        lvUsers.setAdapter(userAdapter);
        Toast.makeText(this, "intiDatabase成功", Toast.LENGTH_SHORT).show();
    }


    public static Context getMyContext() {
        return MyContext;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAdd:
                insertUser(null, edtName.getText().toString());
                break;
            case R.id.btnDelete:
                deleteUser(edtName.getText().toString());
                break;
            case R.id.btnUpdate:
                updateUser(edtName.getText().toString(), edtOldName.getText().toString());
                break;
        }
    }
}
