package com.contento3.thymeleaf.dialect.navigation;

import java.util.HashSet;
import java.util.Set;

import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.processor.IProcessor;

public class NavigationDialect extends AbstractDialect {

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
}
