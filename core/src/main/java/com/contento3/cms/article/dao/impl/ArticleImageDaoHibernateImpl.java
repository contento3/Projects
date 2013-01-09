package com.contento3.cms.article.dao.impl;

import com.contento3.cms.article.dao.ArticleImageDao;
import com.contento3.cms.article.model.ArticleImage;
import com.contento3.common.spring.dao.GenericDaoSpringHibernateTemplate;

public class ArticleImageDaoHibernateImpl extends GenericDaoSpringHibernateTemplate<ArticleImage, Integer> implements ArticleImageDao {

	public ArticleImageDaoHibernateImpl() {
		super(ArticleImage.class);
	}

}
