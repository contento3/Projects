package com.contento3.cms.article.dao.impl;

import java.util.Collection;

import org.apache.commons.lang.Validate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.contento3.cms.article.dao.ArticleImageDao;
import com.contento3.cms.article.model.ArticleImage;
import com.contento3.common.spring.dao.GenericDaoSpringHibernateTemplate;

public class ArticleImageDaoHibernateImpl extends GenericDaoSpringHibernateTemplate<ArticleImage, Integer> implements ArticleImageDao {

	public ArticleImageDaoHibernateImpl() {
		super(ArticleImage.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<ArticleImage> findAsscArticleImageById(final Integer articleId,final Integer imageId) {
		Validate.notNull(articleId,"articleId cannot be null");
		Validate.notNull(imageId,"imageId cannot be null");

		final Criteria criteria = this.getSession()
				.createCriteria(ArticleImage.class)
				.add(Restrictions.eq("primaryKey.article.articleId", articleId))
				.add(Restrictions.eq("primaryKey.image.imageId", imageId));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<ArticleImage> findAsscArticleImageByArticleId(final Integer articleId) {
		Validate.notNull(articleId,"articleId cannot be null");

		final Criteria criteria = this.getSession()
				.createCriteria(ArticleImage.class)
				.add(Restrictions.eq("primaryKey.article.articleId", articleId));
		return criteria.list();
	}

	@Override
	public Collection<ArticleImage> findAsscArticleImageByArticleIdAndScopeId(
			Integer articleId, Integer scopeId) {
		Validate.notNull(articleId,"articleId cannot be null");
		Validate.notNull(scopeId,"scopeId cannot be null");

		final Criteria criteria = this.getSession()
				.createCriteria(ArticleImage.class)
				.add(Restrictions.eq("primaryKey.contentScope.id", scopeId))
				.add(Restrictions.eq("primaryKey.article.articleId", articleId));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<ArticleImage> findArticleImageByImageId(Integer imageId) {
		
		Validate.notNull(imageId,"imageId cannot be null");

		final Criteria criteria = this.getSession()
				.createCriteria(ArticleImage.class)
				.add(Restrictions.eq("primaryKey.image.imageId", imageId));
		return criteria.list();
	}
	
	

}
