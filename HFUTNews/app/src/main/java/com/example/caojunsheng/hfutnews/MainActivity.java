package com.example.caojunsheng.hfutnews;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.caojunsheng.hfutnews.R.id.all;
import static com.example.caojunsheng.hfutnews.R.id.tv_tab;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ImageView iv_head;
    private TextView tv_name;
    private View head_view;
    private Bundle loginBundle;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
//        navigationView.getMenu().getItem(6).setChecked(true);
        onNavigationItemSelected(navigationView.getMenu().getItem(0));
        head_view = navigationView.getHeaderView(0);
        initData();
        head_view.setOnClickListener(new headClick());
    }

    private class headClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (tv_name.getText().toString().equals("点我登录")) {
//            Toast.makeText(MainActivity.this, "个人信息被点击", Toast.LENGTH_SHORT).show();
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(MainActivity.this, "已登录", Toast.LENGTH_SHORT).show();
                showNormalDialog();

            }
        }
    }

    private void showNormalDialog(){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(MainActivity.this);
        normalDialog.setIcon(R.mipmap.hfut1);
        normalDialog.setTitle("当前登录用户为："+userName);
        normalDialog.setMessage("是否注销当前用户？");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences sharedPreferences = getApplication()
                                .getSharedPreferences("test",
                                        Activity.MODE_PRIVATE);
                        sharedPreferences.edit().clear().commit();
                        tv_name.setText("点我登录");
                    }
                });
        normalDialog.setNegativeButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        // 显示
        normalDialog.show();
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
//        TextView textview_tab = (TextView) findViewById(R.id.tv_tab);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_mainnews) {
//            textview_tab.setText("工大要闻");
            toolbar.setTitle("工大要闻");
            MainNewsFragment mainNewsFragment = new MainNewsFragment();
            transaction.replace(R.id.frame_content, mainNewsFragment);
            Log.i("mainactivity", "工大要闻界面");
        } else if (id == R.id.nav_noticenews) {
//            textview_tab.setText("通知公告");
            toolbar.setTitle("通知公告");
            NoticeNewsFragment noticeNewsFragment = new NoticeNewsFragment();
            transaction.replace(R.id.frame_content, noticeNewsFragment);
            Log.i("mainactivity", "通知公告界面");
        } else if (id == R.id.nav_reportnews) {
//            textview_tab.setText("报告讲座");
            toolbar.setTitle("报告讲座");
            ReportNewsFragment reportNewsFragment = new ReportNewsFragment();
            transaction.replace(R.id.frame_content, reportNewsFragment);
        } else if (id == R.id.nav_medianews) {
//            textview_tab.setText("媒体工大");
            toolbar.setTitle("媒体工大");
            MediaNewsFragment mediaNewsFragment = new MediaNewsFragment();
            transaction.replace(R.id.frame_content, mediaNewsFragment);
        } else if (id == R.id.nav_allnews) {
//            textview_tab.setText("综合新闻");
            toolbar.setTitle("综合新闻");
            AllNewsFragment allNewsFragment = new AllNewsFragment();
            transaction.replace(R.id.frame_content, allNewsFragment);
        } else if (id == R.id.nav_collect) {
//            textview_tab.setText("反馈意见");
            toolbar.setTitle("我的收藏");
            NewsCollectionFragment newsCollectionFragment = new NewsCollectionFragment();
            transaction.replace(R.id.frame_content, newsCollectionFragment);
        } else if (id == R.id.nav_advice) {
//            textview_tab.setText("反馈意见");
            toolbar.setTitle("反馈意见");
            AdviceFragment adviceFragment = new AdviceFragment();
            transaction.replace(R.id.frame_content, adviceFragment);
        } else if (id == R.id.nav_aboutus) {
//            textview_tab.setText("关于我们");
            toolbar.setTitle("关于我们");
            Log.i("mainactivity", "关于界面");
            AboutusFragment aboutusFragment = new AboutusFragment();
            transaction.replace(R.id.frame_content, aboutusFragment);
        }
        transaction.commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void initData() {
        iv_head = (ImageView) head_view.findViewById(R.id.iv_head);
        tv_name = (TextView) head_view.findViewById(R.id.tv_name);
        SharedPreferences sharedPreferences = getSharedPreferences("test",
                Activity.MODE_PRIVATE);
        userName = sharedPreferences.getString("name", "");
        if (!userName.equals("")) {
            tv_name.setText(userName);
        }
    }
}
