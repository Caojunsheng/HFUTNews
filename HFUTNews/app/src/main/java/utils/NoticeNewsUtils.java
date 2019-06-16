package utils;

import android.content.Context;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.NoticeNewsModel;

/**
 * Created by caojunsheng on 2017/4/26.
 */

public class NoticeNewsUtils {
    public static final String url = "http://10.16.16.63:8080/getAllNoticeNews";

    public static ArrayList<NoticeNewsModel> arrayList = new ArrayList<>();
    private static String newsResult = "";

    public static ArrayList<NoticeNewsModel> getAllNoticeNews(Context context) {
        arrayList.clear();
        Log.i("utils", "调用了getAllNoticeNews()方法");
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        try {
            HttpResponse response = client.execute(httpPost);
            // 判断网络是否连接成功
            if (response.getStatusLine().getStatusCode() == 200) {
                newsResult = EntityUtils.toString(
                        response.getEntity(), "utf-8");
                Log.i("response", "网络连接成功" + newsResult);
            } else {
                newsResult = "网络连接失败";
                Log.i("response", newsResult);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (newsResult.equals("网络连接失败")) {
            NoticeNewsModel item = new NoticeNewsModel();
            item.setTitle("无网络连接");
            arrayList.add(item);
        } else {
            JSONArray jsonArray = null;
            try {
                jsonArray = new JSONArray(newsResult);
                for (int i = 0; i < jsonArray.length(); i++) {
                    final JSONObject jsonObject = jsonArray
                            .getJSONObject(i);
                    final NoticeNewsModel item = new NoticeNewsModel();
                    List<String> imgurlList = new ArrayList<String>();
                    String imgurl = null;
                    String content = jsonObject.getString("content");
                    content = content.replace("http://news.hfut.edu.cn", "");
                    item.setId(jsonObject
                            .getInt("id"));
                    item.setDate(jsonObject
                            .getString("date"));
                    item.setTitle(jsonObject.getString("title"));
                    item.setContent(content);
                    item.setEditor(jsonObject.getString("editor"));
                    item.setUrl(jsonObject.getString("url"));
                    arrayList.add(item);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.i("log", "arraylist大小" + arrayList.size());
        return arrayList;
    }
}
