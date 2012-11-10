package com.contento3.cms.page.category.dao;



import java.util.Collection;

import com.contento3.cms.page.category.model.Category;
import com.contento3.common.dao.GenericDao;

public interface CategoryDao extends GenericDao<Category,Integer>{
	
	/**
	 * Returns the category
	 * @param categoryName for a category
	 * @return 
	 */
	Category findCategoryByName(String categoryName);
		
	/**
	 * Returns the categories whose parentId = null
	 * @return 
	 */
	Collection<Category> findNullParentIdCategory();
	
	/**
	 * Returns the child categories by using parentId
	 * @param parentId
	 * @return 
	 */
	Collection<Category> findChildCategories(final Integer parentId);
}
