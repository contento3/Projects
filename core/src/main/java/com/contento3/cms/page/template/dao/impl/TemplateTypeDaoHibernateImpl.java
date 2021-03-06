package com.contento3.cms.page.template.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.contento3.cms.page.template.dao.TemplateTypeDao;
import com.contento3.cms.page.template.model.TemplateType;
import com.contento3.common.spring.dao.GenericDaoSpringHibernateTemplate;

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
		Validate.notNull(name,"name cannot be null");
		
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
