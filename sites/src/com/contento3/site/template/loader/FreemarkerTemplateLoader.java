package com.contento3.site.template.loader;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import com.contento3.site.template.dto.TemplateDto;

import freemarker.cache.TemplateLoader;

/**
 * Used to load freemarker templates from database.
 * @author hammad.afridi
 *
 */
public class FreemarkerTemplateLoader implements TemplateLoader {

	@Override
	public void closeTemplateSource(Object arg0) throws IOException {
	}

	@Override
	public Object findTemplateSource(String arg0) throws IOException {
		//TODO get it from the database here
		TemplateDto dto = new TemplateDto();
		return dto;
	}

	@Override
	public long getLastModified(Object arg0) {
		return 0;
	}

	@Override
	public Reader getReader(Object source, String encoding) throws IOException {
		return new StringReader(((TemplateDto)source).getContent());
	}

}
