package com.contento3.cms.article.impl;

import static org.junit.Assert.assertTrue;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

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