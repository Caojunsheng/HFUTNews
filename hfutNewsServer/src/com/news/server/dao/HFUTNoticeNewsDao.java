package com.news.server.dao;

import com.news.server.model.HFUTNoticeNews;
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
@Repository("hfutNoticeNewsDao")
public class HFUTNoticeNewsDao extends MyHibernateDaoSupport{

    /**
     * 查找所有的通知公告新闻
     * @return
     */
    public List<HFUTNoticeNews> selectAllNoticeNews() {
        List<HFUTNoticeNews> noticeNews = new ArrayList<>();
        Session session = this.getSession(true);
        Transaction tc = session.beginTransaction();
        List list = session.createQuery("From HFUTNoticeNews order by id asc ").setMaxResults(20).list();
        for (Iterator iterator = list.iterator(); iterator.hasNext(); ) {
            HFUTNoticeNews news = (HFUTNoticeNews) iterator.next();
            noticeNews.add(news);
        }
        try {
            tc.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        session.close();
        return noticeNews;
    }
}
