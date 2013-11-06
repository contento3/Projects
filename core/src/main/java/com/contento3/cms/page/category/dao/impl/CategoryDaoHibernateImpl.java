package com.contento3.cms.page.category.dao.impl;

import java.util.Collection;

import org.apache.commons.lang.Validate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.CollectionUtils;

import com.contento3.cms.page.category.dao.CategoryDao;
import com.contento3.cms.page.category.model.Category;
import com.contento3.common.spring.dao.GenericDaoSpringHibernateTemplate;

public class CategoryDaoHibernateImpl extends
		GenericDaoSpringHibernateTemplate<Category, Integer> implements
		CategoryDao {

	public CategoryDaoHibernateImpl() {
		super(Category.class);
	}

	@Override
	public Category findCategoryByName(final String categoryName,final Integer accountId) {
		Validate.notNull(accountId,"accountId cannot be null");
		Validate.notNull(categoryName,"categoryName cannot be null");
		Criteria criteria = this.getSession()
				.createCriteria(Category.class)
				.add(Restrictions.eq("categoryName", categoryName))
				.add(Restrictions
				.eq("account.accountId", accountId));

		Category category = null;
		if (!CollectionUtils.isEmpty(criteria.list())) {
			category = (Category) criteria.list().get(0);
		}

		return category;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Category> findNullParentIdCategory(final Integer accountId){
		Validate.notNull(accountId,"accountId cannot be null");
		Criteria criteria = this.getSession()
				.createCriteria(Category.class)
				.add(Restrictions.isNull("parent"))
				.add(Restrictions.eq("account.accountId", accountId));

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Category> findChildCategories(final Integer parentId,final Integer accountId){
		Validate.notNull(accountId,"accountId cannot be null");
		Validate.notNull(parentId,"parentId cannot be null");
		Criteria criteria = this.getSession()
				.createCriteria(Category.class)
				.add(Restrictions.eq("parent.categoryId", parentId))
				.add(Restrictions.eq("account.accountId", accountId));

		return criteria.list();
	}

}