package com.news.server.dao;

import com.news.server.model.User;
import com.news.server.utils.MyHibernateDaoSupport;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by caojunsheng on 2017/5/19.
 */
@Repository("userDao")
public class UserDao extends MyHibernateDaoSupport{
    public void addUser(User user) {
        Session session = this.getSession(true);
        Transaction tc = session.beginTransaction();
        session.save(user);
        try {
            tc.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        session.close();
    }

    public void delUser(int id) {
        Session session = this.getSession(true);
        Transaction tc = session.beginTransaction();
        User u = new User(id);
        session.delete(u);
        try {
            tc.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        session.close();
    }

    public void updateUser(User user) {
        Session session = this.getSession(true);
        Transaction tc = session.beginTransaction();
        session.update(user);
        try {
            tc.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        session.close();

    }

    public List<String> selectAllUserName() {
        List<String> usernameList = new ArrayList<>();
        Session session = this.getSession(true);
        Transaction tc = session.beginTransaction();
        List list = session.createQuery("From User").list();
        for (Iterator iterator = list.iterator(); iterator.hasNext(); ) {
            User u = (User) iterator.next();
            usernameList.add(u.getName());
        }
        try {
            tc.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        session.close();
        return usernameList;
    }

    public List<User> selectUser() {
        List<User> users = new ArrayList<>();
        Session session = this.getSession(true);
        Transaction tc = session.beginTransaction();
        List list = session.createQuery("From User").list();
        for (Iterator iterator = list.iterator(); iterator.hasNext(); ) {
            User u = (User) iterator.next();
            users.add(u);
        }
        try {
            tc.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        session.close();
        return users;
    }

    public User getUserByUserId(int userId) {
        Session session = this.getSession(true);
        Transaction tc = session.beginTransaction();
        //load 是说明数据库中一定存在这条记录，没有则报出：ObjectNotFoundException
        //get 如果查不到记录，返回的是一个null
        User user = (User) session.load(User.class, userId);
        try {
            tc.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        session.close();
        return user;
    }

    /**
     * 注册的时候通过名字判断名字是否已经存在
     * @param name
     * @return
     */
    public boolean isExitByName(String name) {
        Session session = this.getSession(true);
        Transaction tc = session.beginTransaction();
        List user = session.createQuery("From User u where u.name=:name").setString("name", name).list();
        if (user.size() > 0) {
            try {
                tc.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            session.close();
            return true;
        }
        try {
            tc.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        session.close();
        return false;
    }

    /**
     * 当登录的时候判断名字和密码是否正确
     * @param user
     * @return
     */
    public boolean isExitByNameAndPass(User user) {
        Session session = this.getSession(true);
        Transaction tc = session.beginTransaction();
        List users = null;
        users = session.createQuery("From User u where u.name=:name and u.password=:password").setString("name", user.getName()).setString("password", user.getPassword()).list();
        if (users.size() > 0) {
            try {
                tc.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            session.close();
            return true;
        }
        try {
            tc.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        session.close();
        return false;
    }
}
