package com.contento3.site.template.loader;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import com.contento3.cms.page.exception.PageNotFoundException;
import com.contento3.site.template.assembler.Assembler;
import com.contento3.site.template.dto.TemplateContentDto;

import freemarker.cache.TemplateLoader;

/**
 * Used to load freemarker templates from database.
 * @author hammad.afridi
 *
 */
public class FreemarkerTemplateLoader implements TemplateLoader {

	private Assembler pageAssembler;
	
	@Override
	public void closeTemplateSource(Object arg0) throws IOException {
	}

	@Override
	public Object findTemplateSource(String path) throws IOException {
		String[] pathSplitter = path.split(":");
		Integer siteId = Integer.parseInt(pathSplitter[1].split("_")[0]);
		TemplateContentDto dto;
		try {
			dto = pageAssembler.assemble(siteId,String.format("/%s",pathSplitter[0]));
		} catch (PageNotFoundException e) {
			throw new IOException("Request page not found",e);
		}
		return dto;
	}

	@Override
	public long getLastModified(Object arg0) {
		return 0;
	}

	@Override
	public Reader getReader(Object source, String encoding) throws IOException {
		return new StringReader(((TemplateContentDto)source).getContent());
	}

	public void setPageAssembler(final Assembler pageAssembler){
		this.pageAssembler = pageAssembler;
	}
}
