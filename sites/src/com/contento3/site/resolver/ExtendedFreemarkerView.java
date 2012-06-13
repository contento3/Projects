package com.contento3.site.resolver;

import java.io.PrintWriter;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.support.RequestContext;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.AbstractTemplateView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import com.contento3.cms.site.structure.dto.SiteDto;
import com.contento3.cms.site.structure.service.SiteService;
import com.contento3.site.template.model.TemplateModelMapImpl;
import com.contento3.site.template.render.engine.RenderingEngine;
import com.contento3.util.DomainUtil;

import freemarker.ext.jsp.TaglibFactory;
import freemarker.ext.servlet.FreemarkerServlet;
import freemarker.ext.servlet.HttpRequestHashModel;
import freemarker.ext.servlet.HttpRequestParametersHashModel;
import freemarker.ext.servlet.HttpSessionHashModel;
import freemarker.ext.servlet.IncludePage;
import freemarker.ext.servlet.ServletContextHashModel;

public class ExtendedFreemarkerView extends FreeMarkerView {
	private static final Logger LOGGER = Logger
			.getLogger(ExtendedFreemarkerView.class);

	private RenderingEngine freemarkerRenderingEngine;

	private SiteService siteService;

	private TaglibFactory taglibFactory;

	private ServletContextHashModel servletContextHashModel;

	/**
	 *	Set whether all request attributes should be added to the
	 * model prior to merging with the template. Default is "false".
	 */
	@Override
	protected void renderMergedTemplateModel(Map<String,Object> model,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String requestURI = request.getRequestURI();
		String[] pageUri = requestURI.split("/page/");
		String pagePath = "";

		if (requestURI.contains("page/")) {
			pagePath = pageUri[1];
		} else {
			pagePath = pageUri[0];
		}
		
		LOGGER.info(String.format("Requested page [%s]",pagePath));
		SiteDto siteDto = siteService.findSiteByDomain(DomainUtil.fetchDomain(request));
		PrintWriter writer = response.getWriter();

		try {		

			if (logger.isDebugEnabled()) {
				logger.debug("Rendering FreeMarker template [" + getUrl() + "] in FreeMarkerView '" + getBeanName() + "'");
			}

			freemarkerRenderingEngine.process(buildModelMap(model,request,response),siteDto, pagePath, writer);
		} catch (Exception e) {
			LOGGER.error(String
					.format("Something went wrong while accessing the page [%s:] [%s] ",
							pagePath, e.getLocalizedMessage()));
			// TODO Redirect to the error page is required.
		} finally {
			writer.close();
		}
	}

	/**
	 * Build the TemplateModelMap so that it can be added to the freemarker modelCOntext.
	 * This includes adding jsp tag lib,request,response,session and include page support.
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	private TemplateModelMapImpl buildModelMap(final Map<String,Object> model,HttpServletRequest request, HttpServletResponse response) throws Exception {
		// Read the data file and process the template using FreeMarker
			model.put(AbstractTemplateView.SPRING_MACRO_REQUEST_CONTEXT_ATTRIBUTE, new RequestContext(
                    request,
                    response,
                    this.getServletContext(),
                    model));
			
			setExposeRequestAttributes(true);
			setExposeSessionAttributes(true);
			setExposeSpringMacroHelpers(true);

			exposeModelAsRequestAttributes(model, request);
			
			model.put(FreeMarkerView.SPRING_MACRO_REQUEST_CONTEXT_ATTRIBUTE, 
					new RequestContext(request,response,this.getServletContext(),model));
				
	        for (Map.Entry<String, Object> entry : model.entrySet()) {
	        	String modelName = entry.getKey();
	            Object modelValue = entry.getValue();
	            if (modelValue != null) {
	            	request.setAttribute(modelName, modelValue);
	            } 
	            else {
	                request.removeAttribute(modelName);
	            }
	        }    
			
	        // Expose all standard FreeMarker hash models.
		    model.put( FreemarkerServlet.KEY_JSP_TAGLIBS, this.taglibFactory );
		    model.put( FreemarkerServlet.KEY_APPLICATION, this.servletContextHashModel );
		    model.put( FreemarkerServlet.KEY_SESSION, buildSessionModel( request,response ) );
		    model.put( FreemarkerServlet.KEY_REQUEST, new HttpRequestHashModel(request, response, getObjectWrapper() ) );
		    model.put( FreemarkerServlet.KEY_REQUEST_PARAMETERS,new HttpRequestParametersHashModel( request ) );
		    model.put( FreemarkerServlet.KEY_INCLUDE, new IncludePage( request,response ) );
				
		    TemplateModelMapImpl modelMap = new TemplateModelMapImpl();
		    modelMap.setMap(model);
		    return modelMap;
	}
	
	private HttpSessionHashModel buildSessionModel(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			return new HttpSessionHashModel(session, getObjectWrapper());
		}
		else {
			return new HttpSessionHashModel(null, request, response, getObjectWrapper());
		}
	}

//	@Override
//	protected void doRender(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
//		// Expose model to JSP tags (as request attributes).
//		exposeModelAsRequestAttributes(model, request);
//		// Expose all standard FreeMarker hash models.
//		TemplateHashModel fmModel = buildTemplateModel(model, request, response);
//
//		if (logger.isDebugEnabled()) {
//			logger.debug("Rendering FreeMarker template [" + getUrl() + "] in FreeMarkerView '" + getBeanName() + "'");
//		}
//		// Grab the locale-specific version of the template.
//		Locale locale = RequestContextUtils.getLocale(request);
//		processTemplate(getTemplate(locale), fmModel, response);
//	}

	public void setFreemarkerRenderingEngine(
			final RenderingEngine freemarkerRenderingEngine) {
		this.freemarkerRenderingEngine = freemarkerRenderingEngine;
	}

	public void setSiteService(final SiteService siteService) {
		this.siteService = siteService;
	}

}
