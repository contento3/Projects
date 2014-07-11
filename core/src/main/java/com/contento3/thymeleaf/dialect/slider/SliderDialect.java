package com.contento3.thymeleaf.dialect.slider;

import java.util.HashSet;
import java.util.Set;

import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.processor.IProcessor;
/**
 * 
 * @author Yawar
 *
 */
public class SliderDialect extends AbstractDialect {

	/**
	 * sliderProcessor
	 */
	private SliderProcessor sliderProcessor;
	
	/*
	 * Default prefix: this is the prefix that will be used for this dialect
	 * unless a different one is specified when adding the dialect to
	 * the Template Engine.
	*/
	public String getPrefix() {
		return "slider";
	}

	 @Override
	 public Set<IProcessor> getProcessors() {
		 final Set<IProcessor> processors = new HashSet<IProcessor>();
	     processors.add(sliderProcessor);
	     return processors;
	 }

	@Override
	public boolean isLenient() {
		return false;
	}

	public void setArticleProcessor(final SliderProcessor sliderProcessor) {
		this.sliderProcessor= sliderProcessor;
	}
}
