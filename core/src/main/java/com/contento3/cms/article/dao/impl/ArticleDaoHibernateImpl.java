package com.contento3.cms.article.dao.impl;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import com.contento3.cms.article.dao.ArticleDao;
import com.contento3.cms.article.model.Article;
import com.contento3.cms.page.template.model.Template;
import com.contento3.common.spring.dao.GenericDaoSpringHibernateTemplate;

public class ArticleDaoHibernateImpl  extends GenericDaoSpringHibernateTemplate<Article, Integer> implements ArticleDao{
	ArticleDaoHibernateImpl(){
		super(Article.class);
	}

	
	@Override
	public Collection<Article> findByAccountId(String accountId) {
		// TODO Auto-generated method stub
		Criteria criteria = this.getSession()
		.createCriteria(Template.class)
		.createCriteria("AccountID")
		.add(Restrictions.eq("AccountID", accountId));
		return criteria.list();
	}


	@Override
	public Collection<Article> findLatestArticle(int count) {
		// TODO Auto-generated method stub
		Criteria criteria = this.getSession()
		.createCriteria(Template.class)
		.createCriteria("Count")
		.add(Restrictions.eq("Count", count))
		.setFirstResult(0).setMaxResults(count);
		return criteria.list();
	}
	


	

}