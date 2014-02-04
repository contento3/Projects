package com.contento3.thymeleaf.dialect.navigation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.thymeleaf.context.IProcessingContext;
import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.dialect.IExpressionEnhancingDialect;
import org.thymeleaf.processor.IProcessor;

import com.contento3.cms.page.category.dao.impl.CategoryDaoHibernateImpl;
import com.contento3.cms.page.dao.impl.PageDaoHibernateImplTest;
import com.contento3.thymeleaf.dialect.helper.ArticleUtility;
import com.contento3.thymeleaf.dialect.helper.CategoryUtility;

public class NavigationDialect extends AbstractDialect implements IExpressionEnhancingDialect  {

	private NavigationProcessor navigationProcessor;
	
	/*
	 * Default prefix: this is the prefix that will be used for this dialect
	 * unless a different one is specified when adding the dialect to
	 * the Template Engine.
	*/
	public String getPrefix() {
		return "nav";
	}

	 @Override
	 public Set<IProcessor> getProcessors() {
		 final Set<IProcessor> processors = new HashSet<IProcessor>();
	     processors.add(navigationProcessor);
	     return processors;
	 }

	@Override
	public boolean isLenient() {
		return false;
	}

	public void setNavigationProcessor(final NavigationProcessor navigationProcessor ) {
		this.navigationProcessor = navigationProcessor ;
	}

	@Override
	public Map<String, Object> getAdditionalExpressionObjects(
			IProcessingContext arg0) {
		HashMap<String,Object> expressionobjects = new HashMap<String,Object>();
		expressionobjects.put("CategoryUtility", new CategoryUtility());
		expressionobjects.put("ArticleUtility", new ArticleUtility());
        return expressionobjects;
	}
}
