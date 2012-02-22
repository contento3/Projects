package com.contento3.cms.article.dao.impl;

import java.util.Collection;

import com.contento3.cms.article.dao.ArticleDao;
import com.contento3.cms.article.model.Article;
import com.contento3.common.spring.dao.GenericDaoSpringHibernateTemplate;

public class ArticleDaoHibernateImpl  extends GenericDaoSpringHibernateTemplate<Article, Integer> implements ArticleDao{
	ArticleDaoHibernateImpl(){
		super(Article.class);
	}

	
	@Override
	public Collection<Article> findByAccountId(String accountId) {
		// TODO Auto-generated method stub
		return null;
	}

	


	

}