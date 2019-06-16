package com.example.caojunsheng.hfutnews;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utils.IsUsernameExit;

/**
 * Created by caojunsheng on 2017/5/17.
 */

public class RegistActivity extends Activity {
    private final String serverUrl = "http://10.16.16.63:8080/";
    private EditText et_regist_name;
    private EditText et_regist_password;
    private Button btn_regist;
    String str;
    private IsUsernameExit getalluser;
    private String nameResult = "";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regist_layout);
        initData();
        btn_regist.setOnClickListener(new registListener());
        et_regist_name.setOnFocusChangeListener(new registnameListener());
    }

    // 初始化一些变量
    private void initData() {
        getalluser = new IsUsernameExit();
        et_regist_name = (EditText) findViewById(R.id.et_regist_name);
        et_regist_password = (EditText) findViewById(R.id.et_regist_password);
        btn_regist = (Button) findViewById(R.id.btn_regist);
    }
    // 检测当注册时候该用户名的动态变化时候，该用户名是否已经存在。
    private class registnameListener implements View.OnFocusChangeListener {
        public void onFocusChange(View v, boolean hasFocus) {
            if (!et_regist_name.hasFocus()) {
                str = et_regist_name.getText().toString().trim();
                if (str == null || str.length() <= 0) {
                    et_regist_name.setError("用户名不能为空");
                } else {
                    new Thread(new Runnable() {
                        // 如果用户名不为空，那么将用户名提交到服务器上进行验证，看用户名是否存在，就像JavaEE中利用
                        // ajax一样，虽然你看不到但是它却偷偷摸摸做了很多
                        public void run() {
                            if (getalluser.isExit(str)) {
                                nameResult = "namefail";
                            } else {
                                nameResult = "namesuccess";
                            }
                            // System.out.println("result:"+result);
                            Message message = new Message();
                            message.obj = nameResult;
                            handler.sendMessage(message);
                        }
                    }).start();

                }
            }
        }

    }

    // TODO
    // 点击注册按钮的监听事件
    private class registListener implements View.OnClickListener {

        @Override
        public void onClick(View arg0) {
            final String name = et_regist_name.getText().toString().trim();
            final String password = et_regist_password.getText().toString();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    String httpResult = null;
                    String result;
                    if (name == null || nameResult.equals("namefail")
                            || password == null || password.length() <= 0
                            || name.length() <= 0) {
                        result = "registfail";
                        // Toast.makeText(RegistActivity.this, "注册失败",
                        // Toast.LENGTH_SHORT).show();
                    }
                    // 用来判断用户名或密码中是否有非法字符
                    else if (name.indexOf(" ") > 0 || password.indexOf(" ") > 0) {
                        result = "containblank";
                    } else {
                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                        params.add(new BasicNameValuePair("user.name", name));
                        params.add(new BasicNameValuePair("user.password",
                                password));
                        HttpEntity entity;
                        HttpClient client = new DefaultHttpClient();
                        HttpPost httpPost = new HttpPost(serverUrl + "addUser");
                        HttpResponse response;
                        try {
                            entity = new UrlEncodedFormEntity(params,
                                    HTTP.UTF_8);
                            httpPost.setEntity(entity);
                            response = client.execute(httpPost);
                            // 判断网络是否连接成功
                            if (response.getStatusLine().getStatusCode() == 200) {
                                httpResult = EntityUtils.toString(
                                        response.getEntity(), "utf-8");
                                // Log.i("TAG", httpResult);
                            } else {
                                httpResult = "InternalFail";
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Log.i("TAG", "httpResult=" + httpResult);
                        if (httpResult.equals("success")) {
                            result = "registsuccess";
                            // Toast.makeText(RegistActivity.this, "注册成功，请返回登录",
                            // Toast.LENGTH_SHORT).show();
                        } else if (httpResult.equals("InternalFail")) {
                            result = "InternalFail";
                            // Toast.makeText(RegistActivity.this,
                            // "网络似乎出了点问题哦 ‘(*>﹏<*)’", Toast.LENGTH_SHORT)
                            // .show();
                        } else {
                            result = "unknownfail";
                            // Toast.makeText(RegistActivity.this,
                            // "程序猿童鞋也不知道怎么回事了", Toast.LENGTH_SHORT)
                            // .show();
                        }
                    }
                    Message message = new Message();
                    message.obj = result;
                    handler.sendMessage(message);
                }
            }).start();

        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String msgobj = msg.obj.toString();
            switch (msgobj) {
                case "containblank":
                    Toast.makeText(RegistActivity.this, "用户名或密码中有特殊字符",
                            Toast.LENGTH_LONG).show();
                    break;
                case "namefail":
                    et_regist_name.requestFocus();
                    et_regist_name.setError("该用户名已存在");
                    break;
                case "namesuccess":

                    break;
                case "registfail":
                    Toast.makeText(RegistActivity.this, "注册失败", Toast.LENGTH_SHORT)
                            .show();
                    break;
                case "InternalFail":
                    Toast.makeText(RegistActivity.this, "网络似乎出了点问题哦 ‘(*>﹏<*)’",
                            Toast.LENGTH_SHORT).show();
                    break;
                case "registsuccess":
                    Toast.makeText(RegistActivity.this, "注册成功，请返回登录",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setClass(RegistActivity.this, LoginActivity.class);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    startActivity(intent);
                    RegistActivity.this.finish();
                    break;
                case "unknownfail":
                    Toast.makeText(RegistActivity.this, "程序猿童鞋也不知道怎么回事了",
                            Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };
}
