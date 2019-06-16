package com.news.server.utils;

/**
 * Created by SX10100563 on 2016/10/10.
 */

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import javax.annotation.Resource;

public class MyHibernateDaoSupport extends HibernateDaoSupport {
    @Resource(name="sessionFactory")    //为父类HibernateDaoSupport注入sessionFactory的值
    public void setSuperSessionFactory(SessionFactory sessionFactory){
        super.setSessionFactory(sessionFactory);
    }
}

