package com.olive.cms.page.template.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.olive.cms.page.template.dao.TemplateTypeDao;
import com.olive.cms.page.template.model.TemplateType;
import com.olive.common.spring.dao.GenericDaoSpringHibernateTemplate;

/**
 * @author : Hammad Afridi
 * Created : 10/16/2011
 */

public class TemplateTypeDaoHibernateImpl extends GenericDaoSpringHibernateTemplate<TemplateType, Integer> implements TemplateTypeDao {

	public TemplateTypeDaoHibernateImpl (){
		super(TemplateType.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public TemplateType findByName(String name) {
		TemplateType templateType = null;
		Criteria criteria = this.getSession()
			.createCriteria(TemplateType.class)
			.add(Restrictions
			.eq("templateTypeName", name));

		List<TemplateType> templateTypeList =  (ArrayList<TemplateType>) criteria.list();
		templateType = templateTypeList.get(0);
		
		return templateType;
	}
}
