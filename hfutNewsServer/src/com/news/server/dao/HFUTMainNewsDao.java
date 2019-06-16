package com.news.server.dao;

import com.news.server.model.HFUTMainNews;
import com.news.server.utils.MyHibernateDaoSupport;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by caojunsheng on 2017/3/15.
 */
@Repository("hfutMainNewsDao")
public class HFUTMainNewsDao extends MyHibernateDaoSupport{
    /**
     * 查找所有的工大要闻
     * @return
     */
    public List<HFUTMainNews> selectAllMainNews() {
        List<HFUTMainNews> mainNews = new ArrayList<>();
        Session session = this.getSession(true);
        Transaction tc = session.beginTransaction();
        List list = session.createQuery("From HFUTMainNews order by id asc ").setMaxResults(20).list();
        for (Iterator iterator = list.iterator(); iterator.hasNext(); ) {
            HFUTMainNews news = (HFUTMainNews) iterator.next();
            mainNews.add(news);
        }
        try {
            tc.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        session.close();
        return mainNews;
    }
}
