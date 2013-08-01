package com.contento3.cms.article.dao.impl;

import java.util.Collection;

import org.apache.commons.lang.Validate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.CollectionUtils;

import com.contento3.cms.article.dao.ArticleDao;
import com.contento3.cms.article.dto.ArticleDto;
import com.contento3.cms.article.model.Article;
import com.contento3.common.spring.dao.GenericDaoSpringHibernateTemplate;

public class ArticleDaoHibernateImpl  extends GenericDaoSpringHibernateTemplate<Article, Integer> implements ArticleDao{
		
	public ArticleDaoHibernateImpl(){
		super(Article.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Article> findByAccountId(final Integer accountId) {
		Validate.notNull(accountId,"accountId cannot be null");

		final Criteria criteria = this.getSession()
		.createCriteria(Article.class)
		.add(Restrictions.eq("account.accountId", accountId))
		.add(Restrictions.eq("isVisible", 1));
		return criteria.list();
	}


	@SuppressWarnings("unchecked")
	@Override
	public Collection<Article> findLatestArticle(final Integer count) {
		Validate.notNull(count,"count cannot be null");

		final Criteria criteria = this.getSession()
		.createCriteria(Article.class)
		.add(Restrictions.eq("isVisible", 1))
		.createCriteria("Count")
		.add(Restrictions.eq("Count", count))
		.setFirstResult(0).setMaxResults(count);
		
		return criteria.list();
	}
	

	@Override
	public Article findByUuid(final String uuid) {
		Validate.notNull(uuid,"uuid cannot be null");

		final Criteria criteria = this.getSession()
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
	public Article findById(final Integer id) {
		return super.findById(id);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Article> findLatestArticleBySiteId(final Integer siteId,final Integer count) {
		Validate.notNull(siteId,"siteId cannot be null");

		final Criteria criteria = this.getSession()
				.createCriteria(Article.class)
				.addOrder(Order.desc("dateCreated"))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				.setFirstResult(0)
				.add(Restrictions.eq("isVisible", 1))
				.createCriteria("site")
				.add(Restrictions.eq("siteId", siteId));
		
		if(count!=null){
			criteria.setMaxResults(count);
		}
		return criteria.list();
	}


	@SuppressWarnings("unchecked")
	@Override
	public Collection<Article> findLatestArticleByCategory(final Integer categoryId,
			final Integer numberOfArticles,final Integer siteId) {
		Validate.notNull(categoryId,"siteId cannot be null");
		Validate.notNull(siteId,"siteId cannot be null");
		Validate.notNull(numberOfArticles,"numberOfArticles cannot be null");

		final Criteria criteria = this.getSession()
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

	@Override
	public Article findArticleByIdAndSiteId(final Integer articleId,final Integer siteId) {
		Validate.notNull(articleId,"articleId cannot be null");
		Validate.notNull(siteId,"siteId cannot be null");

		final Criteria criteria = this.getSession()
		.createCriteria(Article.class)
		.add(Restrictions.eq("isVisible", 1))
		.add(Restrictions.eq("articleId", articleId))
		.createAlias("site", "s")
		.add(Restrictions.eq("s.siteId", siteId));
		
		Article article = null;
		if (!CollectionUtils.isEmpty(criteria.list())) {
			article = (Article) criteria.list().get(0);
		}

		return article;
	}

	@Override
	public Collection<Article> findByHeaderName(String header) {
		
		Validate.notNull(header,"header cannot be null");

		final Criteria criteria = this.getSession()
	    .createCriteria(Article.class)
		.add(Restrictions.eq("head", header))
		.add(Restrictions.eq("isVisible", 1));
		return criteria.list();

	}

	@Override
	public Collection<Article> findBySearch(String header, String catagory) {
	
		
	//Validate.notNull(header, "header cannot be null");
	//Validate.notNull(catagory, "catagory cannot");
	
		final Criteria criteria = this.getSession()
				.createCriteria(Article.class);
				
				if(!header.isEmpty()){
				
					criteria.add(Restrictions.eq("head",header));
				}
				criteria.add(Restrictions.eq("isVisible",1));
	
				
				if(!catagory.isEmpty()){
				
					criteria.createAlias("categories", "category")
					.add(Restrictions.eq("category.categoryName", catagory));
				}
				criteria.add(Restrictions.eq("isVisible", 1));
				
		return criteria.list();
	}

	

	}

