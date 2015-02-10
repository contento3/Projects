package com.contento3.thymeleaf.dialect.seo;

import java.util.HashSet;
import java.util.Set;

import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.processor.IProcessor;

public class SEODialect extends AbstractDialect {

	private SEOProcessor seoProcessor;
	
	@Override
	public String getPrefix() {
		return "seo";
	}

	 @Override
	 public Set<IProcessor> getProcessors() {
		 final Set<IProcessor> processors = new HashSet<IProcessor>();
	     processors.add(seoProcessor);
	     return processors;
	 }

	@Override
	public boolean isLenient() {
		return false;
	}

	public void setSeoProcessor(final SEOProcessor seoProcessor ) {
		this.seoProcessor = seoProcessor ;
	}

}
