package com.olive.article.dao.impl;

import java.util.Collection;

import com.contento3.cms.content.model.Article;
import com.olive.article.dao.ArticleDao;

public class ArticleDaoHibernateImpl  implements ArticleDao{

	@Override
	public Integer persist(Article newInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Article transientObject) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Article persistentObject) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Article findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Article findById(Integer id, boolean lock) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Article findByIdUnique(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Article> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Collection<Article> findByExample(Article exampleInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<Article> findByAccount(Integer accountId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Article findByAccountId(String accountId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Article findBySiteId(String siteId) {
		// TODO Auto-generated method stub
		return null;
	}
	public void findLatestArticles(Integer count){
		
	}

}