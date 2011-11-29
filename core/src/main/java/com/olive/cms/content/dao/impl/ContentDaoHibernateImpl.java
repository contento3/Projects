package com.olive.cms.content.dao.impl;
import com.olive.cms.content.dao.ContentDao;
import com.olive.cms.content.model.Content;
import com.olive.common.spring.dao.GenericDaoSpringHibernateTemplate;

public class ContentDaoHibernateImpl extends GenericDaoSpringHibernateTemplate<Content, Integer> implements ContentDao {

	public ContentDaoHibernateImpl (){
		super(Content.class);
	}
}
