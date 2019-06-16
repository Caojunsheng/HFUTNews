package com.example.caojunsheng.hfutnews;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import adapter.AllNewsAdapter;
import db.CollectDB;
import model.AllNewsModel;

/**
 * Created by caojunsheng on 2017/4/26.
 */

public class NewsCollectionFragment extends Fragment {
    private Context mContext;
    private List<AllNewsModel> data = null;
    private ListView lv_news;
    private CollectDB collectDB;
    private AllNewsAdapter newsAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View NewsCollectionLayout = inflater.inflate(R.layout.news_collect, container, false);
        mContext = getActivity();
        lv_news = (ListView) NewsCollectionLayout.findViewById(R.id.lv_collectnews);
        collectDB = new CollectDB(getActivity());
        //养成先写思路，再写程序的习惯
        //  1：用模拟新闻假数据，用list封装
        //  2：布局ListView
        //  3：获取ListView控件
        //  4：创建一个adapter设置给listview
        //  5：设置ListView条目单击事件
        //1.获取新闻数据用list封装
        data = collectDB.query();
        newsAdapter = new AllNewsAdapter((ArrayList<AllNewsModel>) data, mContext);
        lv_news.setAdapter(newsAdapter);
        newsAdapter.notifyDataSetChanged();

//        Log.i("log","获得的news大小"+allNews.size());
        lv_news.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //需要获取条目上bean对象中url做跳转
                AllNewsModel bean = (AllNewsModel) parent.getItemAtPosition(position);

                String url = bean.getUrl();

                Log.i("url", url);
                //跳转浏览器
                Intent intent = new Intent();
                intent.putExtra("allnews", bean);
                intent.setClass(getActivity(), AllNewsDetailActivity.class);
                startActivity(intent);
            }
        });
        lv_news.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {
                final AlertDialog.Builder normalDialog =
                        new AlertDialog.Builder(getActivity());
                normalDialog.setTitle("删除这项收藏的新闻");
                normalDialog.setMessage("是否删除该新闻？");
                normalDialog.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                collectDB.delete(((AllNewsModel) parent.getItemAtPosition(position)).getId());
                                List<AllNewsModel> allNews = collectDB.query();
                                newsAdapter = new AllNewsAdapter((ArrayList<AllNewsModel>) allNews, mContext);
                                lv_news.setAdapter(newsAdapter);
                                newsAdapter.notifyDataSetChanged();
                            }
                        });
                normalDialog.setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //...To-do
                            }
                        });
                // 显示
                normalDialog.show();
                return true;
            }
        });
//        Log.i("log","主布局log打印")
        return NewsCollectionLayout;
    }


    @Override
    public void onResume() {
        super.onResume();
    }
}
