package com.contento3.cms.article.dao.impl;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.CollectionUtils;

import com.contento3.cms.article.dao.ArticleDao;
import com.contento3.cms.article.model.Article;
import com.contento3.cms.page.category.model.Category;
import com.contento3.common.spring.dao.GenericDaoSpringHibernateTemplate;

public class ArticleDaoHibernateImpl  extends GenericDaoSpringHibernateTemplate<Article, Integer> implements ArticleDao{
	

	
	ArticleDaoHibernateImpl(){
		super(Article.class);
	}

	
	@Override
	public Collection<Article> findByAccountId(Integer accountId) {
		Criteria criteria = this.getSession()
		.createCriteria(Article.class)
		.add(Restrictions.eq("account.accountId", accountId))
		.add(Restrictions.eq("isVisible", 1));
		return criteria.list();
	}


	@Override
	public Collection<Article> findLatestArticle(int count) {
		Criteria criteria = this.getSession()
		.createCriteria(Article.class)
		.add(Restrictions.eq("isVisible", 1))
		.createCriteria("Count")
		.add(Restrictions.eq("Count", count))
		.setFirstResult(0).setMaxResults(count);
		
		return criteria.list();
	}
	

	@Override
	public Article findByUuid(String uuid) {
		
		Criteria criteria = this.getSession()
				.createCriteria(Article.class)
				.add(Restrictions.eq("uuid", uuid))
				.add(Restrictions.eq("isVisible", 1));
		Article article = null;
		if (!CollectionUtils.isEmpty(criteria.list())) {
			article = (Article) criteria.list().get(0);
		}

		return article;
		
	}

	@Override
	public Article findById(Integer id) {
		// TODO Auto-generated method stub
		return super.findById(id);
	}
	
	@Override
	public Collection<Article> findLatestArticleBySiteId(Integer siteId,Integer count) {
		Criteria criteria = this.getSession()
				.createCriteria(Article.class)
				.addOrder(Order.desc("dateCreated"))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				.setFirstResult(0).setMaxResults(count)
				.add(Restrictions.eq("isVisible", 1))
				.createCriteria("site")
				.add(Restrictions.eq("siteId", siteId));
				
		return criteria.list();
	}


	@Override
	public Collection<Article> findLatestArticleByCategory(Integer categoryId,
			Integer numberOfArticles, Integer siteId) {
		Criteria criteria = this.getSession()
		.createCriteria(Article.class)
		.addOrder(Order.desc("dateCreated"))
		.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
		.setFirstResult(0).setMaxResults(numberOfArticles)
		.add(Restrictions.eq("isVisible", 1))
		.createAlias("categories", "category")
		.add(Restrictions.eq("category.categoryId", categoryId))
		.createAlias("site", "s")
		.add(Restrictions.eq("s.siteId", siteId));
		
		return criteria.list();
	}

}