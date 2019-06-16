package com.example.caojunsheng.hfutnews;

import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by caojunsheng on 2017/4/10.
 */

public class AdviceFragment extends Fragment {
    private EditText et_advice;
    private Button btn_send;
    private String content;
    private final String url = "http://10.16.16.63:8080/addAdvice";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View AdviceLayout = inflater.inflate(R.layout.advice_layout, container, false);
        et_advice = (EditText) AdviceLayout.findViewById(R.id.et_advice);
        btn_send = (Button) AdviceLayout.findViewById(R.id.btn_send);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("test",
                Activity.MODE_PRIVATE);
        final String name = sharedPreferences.getString("name", "");
        //http://localhost:8080/addAdvice?advice.content=不错哦&advice.date=2017/5/22&advice.adviceuser=test
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content = et_advice.getText().toString().trim();
                Log.i("advice", content);
                final SimpleDateFormat df = new SimpleDateFormat(
                        "yyyy-MM-dd HH:mm");
                if (name.equals("")) {
                    Toast.makeText(getActivity(), "未登录，请先登录", Toast.LENGTH_SHORT).show();
                } else if (content.equals("")) {
                    Toast.makeText(getActivity(), "反馈意见不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    new Thread(new Runnable() {
                        String result;

                        @Override
                        public void run() {
                            List<NameValuePair> params = new ArrayList<NameValuePair>();
                            params.add(new BasicNameValuePair(
                                    "advice.date", df.format(new Date())));
                            params.add(new BasicNameValuePair(
                                    "advice.content", content));
                            params.add(new BasicNameValuePair(
                                    "advice.adviceuser", name));
                            HttpEntity entity;
                            try {
                                entity = new UrlEncodedFormEntity(params,
                                        HTTP.UTF_8);
                                // HttpPost httpPost = connNet.getHttpPost(action);
                                HttpPost httpPost = new HttpPost(url);
                                httpPost.setEntity(entity);
                                HttpClient client = new DefaultHttpClient();
                                HttpResponse httpResponse = client
                                        .execute(httpPost);
                                if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                                    // result = "网络连接成功";
                                    result = EntityUtils.toString(
                                            httpResponse.getEntity(), "utf-8");
                                    // if (result.equals("success")) {
                                    // Toast.makeText(TuCaoActivity.this,
                                    // "感谢您的吐槽", Toast.LENGTH_SHORT)
                                    // .show();
                                    // }
                                } else {
                                    result = "fail";
                                }
                            } catch (UnsupportedEncodingException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            } catch (ClientProtocolException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            Message msg = new Message();
                            msg.obj = result;
                            handler.sendMessage(msg);
                        }
                    }).start();
                }

            }
        });

        return AdviceLayout;
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.obj.toString().equals("success")) {
                Toast.makeText(getActivity(), "感谢您的反馈", Toast.LENGTH_SHORT)
                        .show();
                et_advice.setText("");
            }
            super.handleMessage(msg);
        }
    };
}
