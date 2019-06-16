package com.news.server.dao;

import com.news.server.model.HFUTReportNews;
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
@Repository("hfutReportNewsDao")
public class HFUTReportNewsDao extends MyHibernateDaoSupport{
    /**
     * 查找所有的报告讲座新闻
     * @return
     */
    public List<HFUTReportNews> selectAllReportNews() {
        List<HFUTReportNews> reportNews = new ArrayList<>();
        Session session = this.getSession(true);
        Transaction tc = session.beginTransaction();
        List list = session.createQuery("From HFUTReportNews order by id asc ").setMaxResults(20).list();
        for (Iterator iterator = list.iterator(); iterator.hasNext(); ) {
            HFUTReportNews news = (HFUTReportNews) iterator.next();
            reportNews.add(news);
        }
        try {
            tc.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        session.close();
        return reportNews;
    }
}
