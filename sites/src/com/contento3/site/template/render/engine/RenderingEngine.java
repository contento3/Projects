package com.contento3.site.template.render.engine;

import java.io.Writer;

public interface RenderingEngine {

	public void process (String templatePath,Writer writer);
	
}
