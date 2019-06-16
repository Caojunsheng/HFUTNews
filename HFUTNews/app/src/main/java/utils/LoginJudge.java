package utils;

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
import java.util.ArrayList;
import java.util.List;

/**
 * 这个类用来进行登录判断的工具类
 * 
 * @author ubuntu
 * 
 */
public class LoginJudge {

	private final String serverUrl = "http://10.16.16.63:8080/";

	/**
	 * 使用post方式向服务器发送请求，相当于http://localhost:8080/login.action?user.name=cjs&
	 * user.password=123这种get方式 向服务器发出http请求，附带用户名和密码
	 * 
	 * @param action
	 * @param userName
	 * @param userPassword
	 * @return
	 */
	public String login(String action, String userName, String userPassword) {
		String result = null;
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("user.name", userName));
		params.add(new BasicNameValuePair("user.password", userPassword));
		HttpEntity entity;
		HttpClient client = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(serverUrl + "login");
		HttpResponse response;
		try {
			entity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
			httpPost.setEntity(entity);
			response = client.execute(httpPost);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				// result = "网络连接成功";
				result = EntityUtils.toString(response.getEntity(), "utf-8");
			} else {
				result = "InternalFail";
			}
			// if (response.getStatusLine().getStatusCode() == 200) {
			// HttpEntity entity = response.getEntity();
			// if (entity != null) {
			// result = EntityUtils.toString(entity);
			// }
			// }
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

}
