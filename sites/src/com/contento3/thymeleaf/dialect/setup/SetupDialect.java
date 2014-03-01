package com.contento3.thymeleaf.dialect.setup;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.thymeleaf.context.IProcessingContext;
import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.dialect.IExpressionEnhancingDialect;
import org.thymeleaf.processor.IProcessor;

import com.contento3.thymeleaf.dialect.article.ArticleTemplateHelper;
import com.contento3.thymeleaf.dialect.category.CategoryTemplateHelper;
import com.contento3.thymeleaf.dialect.navigation.NavigationTemplateHelper;

public class SetupDialect extends AbstractDialect implements IExpressionEnhancingDialect {

//	private ArticleProcessor articleProcessor;
	
	/*
	 * Default prefix: this is the prefix that will be used for this dialect
	 * unless a different one is specified when adding the dialect to
	 * the Template Engine.
	*/
	public String getPrefix() {
		return "setup";
	}

	 @Override
	 public Set<IProcessor> getProcessors() {
//		 final Set<IProcessor> processors = new HashSet<IProcessor>();
//	     processors.add(articleProcessor);
	     return null;
	 }

	@Override
	public boolean isLenient() {
		return false;
	}

	@Override
	public Map<String, Object> getAdditionalExpressionObjects(
			IProcessingContext arg0) {
		HashMap<String,Object> expressionobjects = new HashMap<String,Object>();
		expressionobjects.put("CategoryUtility", new CategoryTemplateHelper());
		expressionobjects.put("ArticleUtility", new ArticleTemplateHelper());
		expressionobjects.put("NavigationUtility", new NavigationTemplateHelper());
        return expressionobjects;
	}
}
