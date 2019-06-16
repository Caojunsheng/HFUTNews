package utils;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
//用来判断注册的用户名是否已经存在。
public class IsUsernameExit {

	private String serverUrl = "http://10.16.16.63:8080/";
	private String httpResult;

	public Boolean isExit(String username) {
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(serverUrl+"getAllUserName");
		try {
			HttpResponse response = client.execute(httpGet);
			// 判断网络是否连接成功
			if (response.getStatusLine().getStatusCode() == 200) {
				httpResult = EntityUtils
						.toString(response.getEntity(), "utf-8");
//				Log.i("TAG", httpResult);
			} else {
				httpResult = "网络连接失败";
			}
			JSONArray jsonArray = new JSONArray(httpResult);
			Log.i("TAG", jsonArray.length() + "");
			for (int i = 0; i < jsonArray.length(); i++) {
				Log.i("TAG", jsonArray.getJSONObject(i).getString("name"));
				if (username.equals(jsonArray.getJSONObject(i).getString(
						"name"))) {
					return true;
				}
			}
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
