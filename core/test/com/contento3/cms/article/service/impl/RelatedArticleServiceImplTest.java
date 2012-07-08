package com.contento3.cms.article.service.impl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.contento3.cms.AbstractDataAccessTest;
import com.contento3.cms.article.dto.RelatedArticleDto;
import com.contento3.cms.article.model.Article;
import com.contento3.cms.article.service.RelatedArticleService;
@RunWith(SpringJUnit4ClassRunner.class)
//ApplicationContext will be loaded from "/applicationContext.xml"
@ContextConfiguration("/applicationContext.xml")
public class RelatedArticleServiceImplTest {

	   @Autowired
	   private ApplicationContext applicationContext;
	   
	   RelatedArticleService service;
	   private Integer articleId;
	   private Integer expectedId;
	   private String expectedHead;
	   
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		articleId =1;
		expectedId=2;
		expectedHead ="article 2";
		 service = (RelatedArticleService) applicationContext
					.getBean("relatedArticleService");
	}

	@After
	public void tearDown() throws Exception {
	}
	
	
	
	@Test
	public void testFindRelatedArticles() {
		
	
		Collection<RelatedArticleDto> dtos =service.findRelatedArticles(articleId);
		RelatedArticleDto dto = dtos.iterator().next();
		Article article = dto.getPrimaryKey().getRelatedArticle();
		assertEquals(expectedId, article.getArticleId());
		assertEquals(expectedHead, article.getHead());
		
	}

	@Test
	public void testDeleteRelatedArticle() {
		service.deleteRelatedArticle(articleId);
		testFindRelatedArticles();
	}

	@Test
	public void testDeleteRelatedArticles() {
		Collection<Integer> relatedArticleIds = new ArrayList<Integer>();
		//relatedArticleIds.add(2);
		relatedArticleIds.add(3);
		//relatedArticleIds.add(4);
		service.deleteRelatedArticles(articleId, relatedArticleIds);
		expectedId=4;
		expectedHead ="article 4";
		testFindRelatedArticles();
	}

}
