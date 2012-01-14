package com.contento3.cms.content.dao.impl;
import com.contento3.cms.content.dao.ContentDao;
import com.contento3.cms.content.model.Content;
import com.contento3.common.spring.dao.GenericDaoSpringHibernateTemplate;

public class ContentDaoHibernateImpl extends GenericDaoSpringHibernateTemplate<Content, Integer> implements ContentDao {

	public ContentDaoHibernateImpl (){
		super(Content.class);
	}
}
