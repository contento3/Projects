package com.contento3.site.template.render.engine.impl;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;

import com.contento3.cms.site.structure.dto.SiteDto;
import com.contento3.site.template.model.TemplateModelContext;
import com.contento3.site.template.model.TemplateModelMapImpl;
import com.contento3.site.template.render.engine.RenderingEngine;

import freemarker.cache.TemplateLoader;
import freemarker.core.Environment;
import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.utility.HtmlEscape;

/**
 * This class is used to render requested freemarker template.
 * This is THE CORE part of page rendering. 
 * @author HAMMAD
 *
 */

public class FreemarkerRenderingEngine implements RenderingEngine {

	private static final Logger LOGGER = Logger.getLogger(FreemarkerRenderingEngine.class);

	private TemplateLoader customTemplateLoader;
	
	private Configuration configuration;
	
	private TemplateModelContext modelContext;

	private Configuration cfg;
	
	@PostConstruct
	private void afterPropertiesSet(){
		Configuration cfg = new Configuration();
		cfg = new Configuration();
		cfg.setObjectWrapper(ObjectWrapper.BEANS_WRAPPER);
		cfg.setLocalizedLookup(false);
		cfg.setNumberFormat("0.######");
		
		Map map = new HashMap();
		map.put("spring", "org/springframework/web/servlet/view/freemarker/spring.ftl");
		cfg.setAutoImports(map);
	}
	
	public void setCustomTemplateLoader(final TemplateLoader customTemplateLoader){
		this.customTemplateLoader = customTemplateLoader;
	}
	
	public void setConfiguration(final Configuration configuration){
		this.configuration = configuration;
	}

	public void setModelContext(final TemplateModelContext modelContext){
		this.modelContext = modelContext;
	}

	@Override
	public void process(TemplateModelMapImpl fmModel,SiteDto siteDto,String pagePath, Writer writer) {
		try {
			
			configuration.setLocalizedLookup(false);
			configuration.setObjectWrapper(ObjectWrapper.BEANS_WRAPPER);
			configuration.setSharedVariable("html_escape", new HtmlEscape());
			pagePath = String.format("%s:%d",pagePath,siteDto.getSiteId());
			// our loader is already cached and also do a validation
			// cfg.setCacheStorage(new DisableCache());

//			ClassTemplateLoader ctl = new ClassTemplateLoader(getClass(), "");
//			TemplateLoader[] loaders = new TemplateLoader[] { customTemplateLoader, ctl };
//			MultiTemplateLoader mtl = new MultiTemplateLoader(loaders);
//			
			
			modelContext.getModels().add(fmModel);
			//configuration.setTemplateLoader(mtl);
			configuration.setClassForTemplateLoading(getClass(), "/");
			Template  tpl = configuration.getTemplate("org/springframework/web/servlet/view/freemarker/spring.ftl");
//			
//
//			tpl.process(modelContext, writer);
			configuration.setTemplateLoader(customTemplateLoader);
			Template tpl1 = configuration.getTemplate(pagePath);
			Environment env = tpl1.createProcessingEnvironment(modelContext,writer);
			env.importLib(tpl,"spring");
			env.process();
			//tpl1.process(modelContext, writer);
			
//			 tpl = configuration.getTemplate("org/springframework/web/servlet/view/freemarker/spring.ftl");
//			tpl.process(modelContext, writer);
//			configuration.addAutoImport("spring", "org/springframework/web/servlet/view/freemarker/spring.ftl");

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
