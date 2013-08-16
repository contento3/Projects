package com.contento3.cms.article.dao.impl;


import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.contento3.cms.article.dao.RelatedArticleDao;
import com.contento3.cms.article.model.RelatedArticle;
import com.contento3.common.spring.dao.GenericDaoSpringHibernateTemplate;

public class RelatedArticleDaoHibernateImpl extends GenericDaoSpringHibernateTemplate<RelatedArticle, Integer> implements RelatedArticleDao{

	public RelatedArticleDaoHibernateImpl() {
		super(RelatedArticle.class);
		
	}
	
	public Collection<RelatedArticle> findRelatedArticles(Integer articleId){
		Criteria criteria = this.getSession()
				.createCriteria(RelatedArticle.class)
				.createCriteria("article")
				.add(Restrictions.eq("articleId", articleId));
		return criteria.list();
	}

	public void deleteRelatedArticle(Integer articleId){
	
		Criteria criteria =  this.getSession()
				.createCriteria(RelatedArticle.class)
				.createCriteria("article")
				.add(Restrictions.eq("articleId", articleId));
		this.getSession().delete((RelatedArticle)criteria.list().iterator().next());
	}

	@Override
	public void deleteRelatedArticles(Integer articleId,
			Collection<Integer> relatedArticleIds) {
		Criteria criteria =  this.getSession()
				.createCriteria(RelatedArticle.class)
				.createCriteria("article")
				.add(Restrictions.eq("articleId", articleId));
		Collection<RelatedArticle> relArticle =  criteria.list(); //Collection contain related articles
		
		for(RelatedArticle rel : relArticle){ 
			for(Integer id:relatedArticleIds){
				 //checking id whith primarykey.getRelatedArticle().getArticleId();
				if(rel.getPrimaryKey().getRelatedArticle().getArticleId() == id){
					this.getSession().delete(rel);
				}//end if
			}//end inner for
		}//end outer for
		
	}

}
