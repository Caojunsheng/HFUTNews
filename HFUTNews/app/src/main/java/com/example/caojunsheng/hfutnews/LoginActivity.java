package com.example.caojunsheng.hfutnews;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import utils.LoginJudge;

/**
 * Created by caojunsheng on 2017/5/16.
 */

public class LoginActivity extends Activity {
    private EditText et_login_name;
    private EditText et_login_password;
    private Button btn_login;
    private TextView tv_regist;
    private String username;
    private String password;
    ProgressDialog p;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        initData();
        tv_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, RegistActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_login.setOnClickListener(new loginListener());
    }

    private void initData() {
        et_login_name = (EditText) findViewById(R.id.et_login_name);
        et_login_password = (EditText) findViewById(R.id.et_login_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        tv_regist = (TextView) findViewById(R.id.tv_regist);
        SharedPreferences sharedPreferences = getSharedPreferences("test",
                Activity.MODE_PRIVATE);
        String name = sharedPreferences.getString("name", "");
        String password = sharedPreferences.getString("password", "");
        et_login_name.setText(name);
        et_login_password.setText(password);
        p = new ProgressDialog(LoginActivity.this);
        p.setTitle("登录中");
        p.setMessage("登录中，马上就好");
    }

    // 点击登录的监听事件
    private class loginListener implements View.OnClickListener {

        @Override
        public void onClick(View arg0) {
            // Intent intent =new Intent();
            // intent.setClass(LoginActivity.this, MainActivity.class);
            // startActivity(intent);
            // LoginActivity.this.finish();
            username = et_login_name.getText().toString().trim();
            if (username == null || username.length() <= 0) {
                et_login_name.requestFocus();
                et_login_name.setError("对不起，用户名不能为空");
                return;
            } else {
                username = et_login_name.getText().toString().trim();
            }
            password = et_login_password.getText().toString().trim();
            if (password == null || password.length() <= 0) {
                et_login_password.requestFocus();
                et_login_password.setError("对不起，密码不能为空");
                return;
            } else {
                password = et_login_password.getText().toString().trim();
            }
            p.show();
            new Thread(new Runnable() {

                public void run() {
                    LoginJudge judge = new LoginJudge();
                    String result = judge.login("login", username, password);
                    Log.i("TAG", "result=" + result);
                    Message msg = new Message();
                    msg.obj = result;
                    try {
                        // 延时一秒操作
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.sendMessage(msg);
                }
            }).start();
        }

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String result = (String) msg.obj;
            Log.i("TAG", "string=" + result);
            p.dismiss();
            super.handleMessage(msg);
            if ("Success".equals(result)) {
                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_LONG)
                        .show();
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, MainActivity.class);
                intent.putExtra("userName", username);
                startActivity(intent);
                LoginActivity.this.finish();
                SharedPreferences mySharedPreferences = getSharedPreferences(
                        "test", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = mySharedPreferences.edit();
                editor.putString("name", username);
                editor.putString("password", password);
                editor.commit();
            } else if ("InternalFail".equals(result)) {
                Toast.makeText(LoginActivity.this, "网络似乎出了点问题哦 (*>﹏<*)",
                        Toast.LENGTH_SHORT).show();
            } else if (result == null) {
                Toast.makeText(LoginActivity.this, "服务器崩了，老夫也没办法，等等把 ( T___T )",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(LoginActivity.this, "用户名或密码不正确",
                        Toast.LENGTH_SHORT).show();
            }
        }
    };
}
