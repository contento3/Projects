package com.contento3.cms.page.category.dao.impl;

//mport java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.CollectionUtils;

import com.contento3.cms.page.category.dao.CategoryDao;
import com.contento3.cms.page.category.model.Category;

import com.contento3.common.spring.dao.GenericDaoSpringHibernateTemplate;

public class CategoryDAOHibernateImpl extends GenericDaoSpringHibernateTemplate<Category,Integer>
										implements CategoryDao{
	

	public CategoryDAOHibernateImpl(){
		super(Category.class);
	}
	
	
	@Override
	public Category findCategoryByName(String categoryName){
		
		Criteria criteria =	this.getSession()
				.createCriteria(Category.class)
				.add(Restrictions
				.eq("categoryName", categoryName));

		Category cat=null;
		if (!CollectionUtils.isEmpty(criteria.list())){
			cat = (Category)criteria.list().get(0);
		}
		
		return cat;
	}

}
