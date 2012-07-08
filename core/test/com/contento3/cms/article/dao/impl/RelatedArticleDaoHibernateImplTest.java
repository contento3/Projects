package com.contento3.cms.article.dao.impl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.contento3.cms.AbstractDataAccessTest;
import com.contento3.cms.article.dao.RelatedArticleDao;
import com.contento3.cms.article.model.Article;
import com.contento3.cms.article.model.RelatedArticle;
@RunWith(SpringJUnit4ClassRunner.class)
//specifies the Spring configuration to load for this test fixture
@ContextConfiguration("/spring-hibernate-context.xml")

public class RelatedArticleDaoHibernateImplTest{ //extends AbstractDataAccessTest{

	@Autowired
	private RelatedArticleDao relatedArticleDao;
	
	private Integer expectedArticleId=null;
	private Integer id;

	
	// Spring will automatically inject the RelatedArticleDaoDAO
    public void setUserService(RelatedArticleDao dao)
    {
        this.relatedArticleDao= dao;
    }



	@Test
	public void testDeleteRelatedArticle(){
		id=2;
		relatedArticleDao.deleteRelatedArticle(id);

	
	}
	
	@Test
	public void testDeleteRelatedArticles(){
		id=1;
		Collection<Integer> relatedArticleIds = new ArrayList<Integer>();
		relatedArticleIds.add(2);
		relatedArticleIds.add(4);
		relatedArticleDao.deleteRelatedArticles(id, relatedArticleIds);
	
	}
	
	@Test
	public void testFindRelatedArticles() {
		//fail("Not yet implemented");
		//relatedArticleDao = new RelatedArticleDaoHibernateImpl();
		id=1;
		Collection<RelatedArticle> relatedArticle = relatedArticleDao.findRelatedArticles(id);
		
		Article article = relatedArticle.iterator()
					.next()
					.getPrimaryKey().getRelatedArticle();
					
		expectedArticleId =2;
		assertNotNull("relatedArticle is not null ", relatedArticle);
		assertEquals(expectedArticleId,article.getArticleId());
		//assertEquals("article 2",article.getHead());
		

	}

	
}
