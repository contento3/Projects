package com.contento3.site.resolver;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

import com.contento3.site.template.render.engine.RenderingEngine;

public class FreemarkerViewResolver extends AbstractView {

	private RenderingEngine freemarkerRenderingEngine;
	
	@Override
	protected void renderMergedOutputModel(Map arg0, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// Read the data file and process the template using FreeMarker
		try {
			PrintWriter writer = response.getWriter();
			freemarkerRenderingEngine.process("example2.txt",writer);
			writer.close();
		}
		catch(Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
	}
	
	public void setFreemarkerRenderingEngine(final RenderingEngine freemarkerRenderingEngine ){
		this.freemarkerRenderingEngine = freemarkerRenderingEngine;
	}
	
}
