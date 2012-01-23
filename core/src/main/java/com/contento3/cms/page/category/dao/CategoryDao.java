package com.contento3.cms.page.category.dao;



import com.contento3.cms.page.category.model.Category;
import com.contento3.common.dao.GenericDao;

public interface CategoryDao extends GenericDao<Category,Integer>{
	
	/**
	 * Returns the category
	 * @param categoryName for a category
	 * @return 
	 */
	Category findCategoryByName(String categoryName);
		
	

}
