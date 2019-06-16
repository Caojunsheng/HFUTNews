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

import model.AllNewsModel;

/**
 * Created by caojunsheng on 2017/5/15.
 */

public class AllNewsUtils {
    public static final String url = "http://10.16.16.63:8080/getAllAllNews";

    public static ArrayList<AllNewsModel> arrayList = new ArrayList<>();
    private static String newsResult = "";

    public static ArrayList<AllNewsModel> getAllAllNews(Context context) {
        arrayList.clear();
        Log.i("utils", "调用了getAllAllNews()方法");
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
            AllNewsModel item = new AllNewsModel();
            item.setTitle("无网络连接");
            arrayList.add(item);
        } else {
            JSONArray jsonArray = null;
            try {
                jsonArray = new JSONArray(newsResult);
                for (int i = 0; i < jsonArray.length(); i++) {
                    final JSONObject jsonObject = jsonArray
                            .getJSONObject(i);
                    final AllNewsModel item = new AllNewsModel();
                    //int id, String date, String title, String content, String writer, String photoer, String editor, String url, String imgurl
                    List<String> imgurlList = new ArrayList<String>();
                    String imgurl = null;
                    String content = jsonObject.getString("content");
//                    Log.i("utils", "替换前=" + content);
                    content = content.replace("http://news.hfut.edu.cn", "");
                    content = content.replace("/>", "");
                    Log.i("utils", "去除http链接后新闻内容=" + content);
                    int imgnum = countStr(content, "uploadfile/image");
                    Log.i("utils", "id=" + jsonObject.getInt("id") + "图片数量=" + imgnum);
                    for (int j = 0; j < imgnum; j++) {
                        if (content.indexOf(".jpg") != -1) {
                            imgurl = content.substring(content.indexOf("uploadfile"), content.indexOf(".jpg"));
                            content = content.replace("/" + imgurl + ".jpg", "");
                            imgurl = "http://news.hfut.edu.cn/" + imgurl + ".jpg";
                        }else if(content.indexOf(".png")!=-1){
                            imgurl = content.substring(content.indexOf("uploadfile"), content.indexOf(".png"));
                            content = content.replace("/" + imgurl + ".png", "");
                            imgurl = "http://news.hfut.edu.cn/" + imgurl + ".png";
                        }
                        imgurlList.add(imgurl);
                    }
//                    for (int j = 0; j < imgnum; j++) {
//                        content += "\n<img src=\"" + imgurlList.get(j) + "\">\n图"+(j+1);
//                    }
                    Log.i("utils", "新闻内容=" + content);
                    item.setId(jsonObject
                            .getInt("id"));
                    item.setDate(jsonObject
                            .getString("date"));
                    item.setTitle(jsonObject.getString("title"));
                    item.setContent(content);
                    item.setWriter(jsonObject
                            .getString("writer"));
                    item.setPhotoer(jsonObject.getString("photoer"));
                    item.setEditor(jsonObject.getString("editor"));
                    item.setUrl(jsonObject.getString("url"));
                    item.setImgurl(imgurlList);
                    arrayList.add(item);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.i("log", "arraylist大小" + arrayList.size());
        return arrayList;
    }

    public static int countStr(String one, String two) {
        int counter = 0;
        if (one.indexOf(two) == -1) {
            return 0;
        }
        while (one.indexOf(two) != -1) {
            counter++;
            one = one.substring(one.indexOf(two) + two.length());
        }
        return counter;
    }
}
