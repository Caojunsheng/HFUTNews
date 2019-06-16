package com.news.server.action;

import com.news.server.dao.UserDao;
import com.news.server.model.User;
import com.news.server.utils.ActionSupportUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by caojunsheng on 2017/5/19.
 */
@Controller
public class UserAction extends ActionSupportUtil {
    @Resource
    private UserDao userDao;
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * 用户的登录功能。
     * 通过UserDao中的isExitByNameAndPass（user）方法可以知道数据库中用户名和密码是否正确
     * http://localhost:8080/login.action?user.name=cjs&user.password=123
     *
     * @return
     */
    @Action(value = "login")
    public void login() throws UnsupportedEncodingException {
        user.setName(new String(user.getName().getBytes("ISO-8859-1"), "UTF-8"));
        boolean flag = userDao.isExitByNameAndPass(user);
        if (flag) {
            renderText("Success");
            return;
        }
        renderText("Fail");
    }

    /**
     * 用户的注册功能。
     * 使用UserDao的addUser方法可以向数据库中插入用户信息（用户名、密码）、用户ID是自增
     * http://localhost:8080/addUser?user.name=&user.password=1234
     *
     * @return
     */
    @Action(value = "addUser")
    public void addUser() throws UnsupportedEncodingException {
        user.setName(new String(user.getName().getBytes("ISO-8859-1"), "UTF-8"));
        userDao.addUser(user);
        renderText("success");
    }

    /**
     * 获得所有的用户名及密码
     * http://localhost:8080/getAllUsers
     */
    @Action("getAllUsers")
    public void getAllUsers() {
        List<User> users = userDao.selectUser();
        if (users == null) {
            renderText("user null");
        } else {
            JSONArray json = new JSONArray();
            for (User user : users) {
                json.add(user.AllUserToJson());
            }
            renderJson(json.toString());
        }
    }

    /**
     * 获取所有的用户名
     * http://localhost:8080/getAllUserName
     */
    @Action("getAllUserName")
    public void getAllUserName() {
        List<String> usernameList = userDao.selectAllUserName();
        if (usernameList == null) {
            renderText("null");
        } else {
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            for (String name : usernameList) {
                jsonObject.put("name", name);
                jsonArray.add(jsonObject);
            }
            renderJson(jsonArray.toString());
        }
    }
}
