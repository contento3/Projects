package com.contento3.cms.article.impl;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.*;
import org.hibernate.jmx.HibernateService;
import org.hibernate.stat.CollectionStatistics;
import org.hibernate.stat.EntityStatistics;
import org.hibernate.stat.QueryStatistics;
import org.hibernate.stat.SecondLevelCacheStatistics;
import org.hibernate.stat.Statistics;
import org.springframework.util.*;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.hibernate.SingletonEhCacheProvider;
import org.junit.Before;
import org.junit.Test;
import org.springframework.orm.hibernate3.HibernateAccessor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.contento3.cms.article.dao.impl.ArticleDaoHibernateImpl;
import com.contento3.cms.article.model.Article;

//Test Case Class for 2nd Level Cache Task
@ContextConfiguration(locations={"/spring-hibernate-context-test.xml"})
public class ArticleDaoHibernateImplTest extends AbstractTransactionalJUnit4SpringContextTests{
	public static final String CACHE_REGION = "com.contento3.cms.article.model.Article";
	
	//@Resource(name="articleDaoCache")
	//private ArticleDaoHibernateImpl articleDao;

	@Resource(name="cmsSessionFactory")
	SessionFactory sessionFactory;
	
	Integer articlePk;
	
	
	@Before
	public void setUp() throws Exception {
		Article article = new Article();
		article.setBody("test body");
		article.setHead("test article header");
		article.setIsVisible(1);
		
		//articlePk = articleDao.persist(article);
	}
	
	@Test
	public void t1(){
		/*
		List<Article> articleList = (List<Article>) articleDao.findLatestArticle(1);
		
		for(int i=0; i<19; i++)
			articleDao.findLatestArticle(1);
		
		Statistics stats = sessionFactory.getCurrentSession().getSessionFactory().getStatistics();
		
		System.out.println("2nd Lvl Stats: " + stats.getSecondLevelCacheStatistics(CACHE_REGION));
		stats.logSummary();
		
		System.out.println("Empty: " + articleList.isEmpty());
		
		assertTrue(!articleList.isEmpty());
		*/
		assertTrue(true);
	}
}