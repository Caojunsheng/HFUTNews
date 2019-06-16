package com.news.server.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class HibernateUtil extends MyHibernateDaoSupport {
	public ThreadLocal session = new ThreadLocal();
	public SessionFactory sessionFactoy = this.getSessionFactory();
	

	public Session currentSession() {
		Session s = (Session) session.get();
		if (s == null) {
			s = sessionFactoy.openSession();
			session.set(s);
		}
		return s;
	}
	
}
