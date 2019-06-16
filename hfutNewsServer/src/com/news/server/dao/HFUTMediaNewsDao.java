package com.news.server.dao;

import com.news.server.model.HFUTMediaNews;
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
@Repository("hfutMediaNewsDao")
public class HFUTMediaNewsDao extends MyHibernateDaoSupport {

    /**
     * 获取所有的媒体工大新闻
     * @return
     */
    public List<HFUTMediaNews> selectAllMediaNews() {
        List<HFUTMediaNews> mediaNews = new ArrayList<>();
        Session session = this.getSession(true);
        Transaction tc = session.beginTransaction();
        List list = session.createQuery("From HFUTMediaNews order by id asc ").setMaxResults(20).list();
        for (Iterator iterator = list.iterator(); iterator.hasNext(); ) {
            HFUTMediaNews news = (HFUTMediaNews) iterator.next();
            mediaNews.add(news);
        }
        try {
            tc.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        session.close();
        return mediaNews;
    }
}
