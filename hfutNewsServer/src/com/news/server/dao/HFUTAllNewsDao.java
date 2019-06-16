package com.news.server.dao;

import com.news.server.model.HFUTAllNews;
import com.news.server.utils.MyHibernateDaoSupport;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by caojunsheng on 2017/4/23.
 */
@Repository("hfutAllNewsDao")
public class HFUTAllNewsDao extends MyHibernateDaoSupport {
    /**
     * 查找所有综合新闻
     * @return
     */
    public List<HFUTAllNews> selectAllAllNews(){
        List<HFUTAllNews> allNews = new ArrayList<>();
        Session session = this.getSession(true);
        Transaction tc = session.beginTransaction();
        List list = session.createQuery("From HFUTAllNews order by id asc ").setMaxResults(20).list();
        for (Iterator iterator = list.iterator(); iterator.hasNext(); ) {
            HFUTAllNews news = (HFUTAllNews) iterator.next();
            allNews.add(news);
        }
        try {
            tc.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        session.close();
        return allNews;
    }
}
