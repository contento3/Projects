package com.contento3.site.template.render.engine.impl;

import java.io.IOException;
import java.io.Writer;
import java.util.Locale;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;

import com.contento3.cms.site.structure.dto.SiteDto;
import com.contento3.site.template.model.TemplateModelContext;
import com.contento3.site.template.render.engine.RenderingEngine;

import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * This class is used to render requested freemarker template.
 * This is THE CORE part of page rendering. 
 * @author HAMMAD
 *
 */

public class FreemarkerRenderingEngine implements RenderingEngine {

	private static final Logger LOGGER = Logger.getLogger(FreemarkerRenderingEngine.class);

	private TemplateLoader templateLoader;
	
	private Configuration configuration;
	
	private TemplateModelContext modelContext;
	
	@PostConstruct
	private void afterPropertiesSet(){
		Configuration cfg = new Configuration();
		cfg = new Configuration();
		cfg.setObjectWrapper(ObjectWrapper.BEANS_WRAPPER);
		cfg.setLocalizedLookup(false);
		cfg.setNumberFormat("0.######");
	}
	
	public void setTemplateLoader(final TemplateLoader templateLoader){
		this.templateLoader = templateLoader;
	}
	
	public void setConfiguration(final Configuration configuration){
		this.configuration = configuration;
	}

	public void setModelContext(final TemplateModelContext modelContext){
		this.modelContext = modelContext;
	}

	@Override
	public void process(SiteDto siteDto,String pagePath, Writer writer) {
		try {
			
			pagePath = String.format("%s:%d",pagePath,siteDto.getSiteId());
			// our loader is already cached and also do a validation
			// cfg.setCacheStorage(new DisableCache());
			configuration.setTemplateLoader(templateLoader);

			Template tpl = configuration.getTemplate(pagePath,Locale.ENGLISH,"en");

			tpl.process(modelContext, writer);
 
		} catch (IOException e) {
			LOGGER.error("error ioexception",e);
		} catch (TemplateException e) {
			LOGGER.error("error templateexception",e);
		}
		catch (Exception e) {
			LOGGER.error("error exception",e);
		}
	}
}
