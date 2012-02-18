package com.olive.article.dao;

import java.util.Collection;

import com.contento3.cms.content.model.Article;
import com.contento3.common.dao.GenericDao;

public interface ArticleDao extends GenericDao<Article,Integer> {

	Collection<Article> findByAccount(Integer accountId);

	Article findByAccountId(String accountId);
	Article findBySiteId(String siteId);
}
