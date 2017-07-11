package com.example.test.testdemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends BaseActivity {

    private EditText edtName;
    private EditText edtPassword;
    private Button btnLogin;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        pref= PreferenceManager.getDefaultSharedPreferences(this);
        intiView();
    }



    private void intiView() {
        edtName = ((EditText) findViewById(R.id.edtName));
        edtPassword = ((EditText) findViewById(R.id.edtPassword));
        btnLogin = ((Button) findViewById(R.id.btnLogin));
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = edtName.getText().toString();
                String password = edtPassword.getText().toString();
                if(user.equals("wltk") && password.equals("wltk8888"))
                {
                    editor=pref.edit();
                    editor.putString("user",user);
                    editor.putString("pwd",password);
                    editor.commit();
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this,ReloginActivity.class));
                    finish();
                }else {
                    Toast.makeText(LoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!pref.getString("user","").equals("")){
            edtName.setText(pref.getString("user",""));
            edtPassword.setText(pref.getString("pwd",""));
        }
    }


}
