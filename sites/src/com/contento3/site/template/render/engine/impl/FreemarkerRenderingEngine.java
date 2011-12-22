package com.contento3.site.template.render.engine.impl;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;

import javax.annotation.PostConstruct;

import com.contento3.site.template.render.engine.RenderingEngine;

import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreemarkerRenderingEngine implements RenderingEngine{

	private TemplateLoader templateLoader;
	
	private Configuration configuration;
	
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
	
	@Override
	public void process(String templatePath, Writer writer) {
		HashMap datamodel = new HashMap();
		datamodel.put("pet", "Bunnies");
		datamodel.put("number", new Integer(6));

		try {

			// our loader is already cached and also do a validation
			//	cfg.setCacheStorage(new DisableCache());
			configuration.setTemplateLoader(templateLoader);
			Template tpl;
		
			tpl = configuration.getTemplate(templatePath);
			//OutputStreamWriter output = new OutputStreamWriter(System.out);
			tpl.process(datamodel, writer);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
