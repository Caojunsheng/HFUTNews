package com.news.server.dao;

import com.news.server.model.Advice;
import com.news.server.utils.MyHibernateDaoSupport;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

/**
 * Created by caojunsheng on 2017/5/22.
 */
@Repository("adviceDao")
public class AdviceDao extends MyHibernateDaoSupport{
    public void addAdvice(Advice advice) {
        Session session = this.getSession(true);
        Transaction tc = session.beginTransaction();
        session.save(advice);
        try {
            tc.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        session.close();
    }
}
