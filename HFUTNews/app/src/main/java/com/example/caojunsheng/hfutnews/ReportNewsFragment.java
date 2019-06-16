package com.example.caojunsheng.hfutnews;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import adapter.ReportNewsAdapter;
import model.ReportNewsModel;
import utils.ReportNewsUtils;

/**
 * Created by caojunsheng on 2017/4/10.
 */

public class ReportNewsFragment extends Fragment {
    private Context mContext;
    private ArrayList<ReportNewsModel> allNews = null;
    private ArrayList<ReportNewsModel> data = null;
    private ListView lv_news;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View ReportNewsLayout=inflater.inflate(R.layout.reportnews_layout,container,false);
        mContext = getActivity();
        lv_news = (ListView) ReportNewsLayout.findViewById(R.id.lv_reportnews);
        //养成先写思路，再写程序的习惯
        //  1：用模拟新闻假数据，用list封装
        //  2：布局ListView
        //  3：获取ListView控件
        //  4：创建一个adapter设置给listview
        //  5：设置ListView条目单击事件
        //1.获取新闻数据用list封装
        new Thread(new Runnable() {
            @Override
            public void run() {
                allNews = ReportNewsUtils.getAllReportNews(mContext);
//                try {
//                    Thread.sleep(5000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                Message msg = new Message();
                msg.obj = allNews;
                handler.sendMessage(msg);
            }
        }).start();


//        Log.i("log","获得的news大小"+allNews.size());
        lv_news.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //需要获取条目上bean对象中url做跳转
                ReportNewsModel bean = (ReportNewsModel) parent.getItemAtPosition(position);

                String url = bean.getUrl();

                Log.i("url", url);
                //跳转浏览器
                Intent intent = new Intent();
                intent.putExtra("reportnews", bean);
                intent.setClass(getActivity(), ReportNewsDetailActivity.class);
                startActivity(intent);
            }
        });
//        Log.i("log","主布局log打印")
        return ReportNewsLayout;
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            data = (ArrayList<ReportNewsModel>) msg.obj;
            Log.i("TAG", data.size() + "数据大小");
            ReportNewsAdapter newsAdapter = new ReportNewsAdapter(data, mContext);
            lv_news.setAdapter(newsAdapter);
            newsAdapter.notifyDataSetChanged();
        }
    };

    @Override
    public void onResume() {
        super.onResume();
    }
}
