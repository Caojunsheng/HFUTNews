package com.example.caojunsheng.hfutnews;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import adapter.NewsCommentListAdapter;
import db.CollectDB;
import model.MediaNewsModel;

/**
 * Created by caojunsheng on 2017/5/15.
 */

public class MediaNewsDetailActivity extends Activity {
    private TextView tvBack;
    private TextView tvJump;
    private TextView tvTitle;
    private TextView tvContent;
    private TextView tvWriter;
    private TextView tvPhotoer;
    private TextView tvEditor;
    private EditText etComment;
    private ImageView ivCollect;
    private CollectDB collectDB;
    private Cursor mCursor;
    private List<String> commentList;
    private ListView lv_comment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_detail);
        Intent intent = getIntent();
        final MediaNewsModel newsModel = (MediaNewsModel) intent.getSerializableExtra("medianews");
        initData();
        tvTitle.setText(newsModel.getTitle());
        String content = newsModel.getContent();
//        tvContent.append(Html.fromHtml(content, imgGetter, null));
        tvContent.setText(content);
        tvWriter.setVisibility(View.GONE);
        tvPhotoer.setVisibility(View.GONE);
        tvEditor.setVisibility(View.GONE);
        final NewsCommentListAdapter newsCommentListAdapter = new NewsCommentListAdapter(commentList, MediaNewsDetailActivity.this);
        lv_comment.setAdapter(newsCommentListAdapter);
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvJump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(newsModel.getUrl()));
                startActivity(intent);
            }
        });
        etComment.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    SharedPreferences sharedPreferences = getSharedPreferences("test",
                            Activity.MODE_PRIVATE);
                    final String name = sharedPreferences.getString("name", "");
                    String content = etComment.getText().toString().trim();
                    if (name.equals("")) {
                        Toast.makeText(MediaNewsDetailActivity.this, "未登录，请先登录", Toast.LENGTH_SHORT).show();
                    } else if (content.equals("")) {
                        Toast.makeText(MediaNewsDetailActivity.this, "评论内容不能为空", Toast.LENGTH_SHORT).show();
                    } else {
                        String str = name + ";" + content;
                        commentList.add(str);
                        setListViewHeight(lv_comment);
                        newsCommentListAdapter.notifyDataSetChanged();
                        etComment.setText("");
                        Toast.makeText(MediaNewsDetailActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }
        });
        ivCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ivCollect.getDrawable().getCurrent().getConstantState() == getResources().getDrawable(R.mipmap.collect).getConstantState()) {
                    ivCollect.setImageResource(R.mipmap.collectpressed);
                    Toast.makeText(MediaNewsDetailActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                    String imgurl = "";
                    collectDB.insert(newsModel.getDate(), newsModel.getTitle(), newsModel.getContent(), "", "", "", newsModel.getUrl(), imgurl);
                    mCursor.requery();
                } else if (ivCollect.getDrawable().getCurrent().getConstantState() == getResources().getDrawable(R.mipmap.collectpressed).getConstantState()) {
                    ivCollect.setImageResource(R.mipmap.collect);
//                    Toast.makeText(AllNewsDetailActivity.this, "取消收藏成功", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void setListViewHeight(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    private void initData() {
        tvBack = (TextView) findViewById(R.id.tv_detailback);
        tvJump = (TextView) findViewById(R.id.tv_detailjump);
        tvTitle = (TextView) findViewById(R.id.tv_detailtitle);
        tvContent = (TextView) findViewById(R.id.tv_detailcontent);
        tvWriter = (TextView) findViewById(R.id.tv_detailwriter);
        tvEditor = (TextView) findViewById(R.id.tv_detaileditor);
        tvPhotoer = (TextView) findViewById(R.id.tv_detailphotoer);
        etComment = (EditText) findViewById(R.id.et_comment);
        ivCollect = (ImageView) findViewById(R.id.iv_collect);
        collectDB = new CollectDB(this);
        mCursor = collectDB.select();
        commentList = new ArrayList<>();
        lv_comment = (ListView) findViewById(R.id.comment_list);
    }
}
