package com.contento3.site.template.model;

import java.util.Map;

import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateHashModelEx;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 * TemplateModelMap is used as core engine interface passing to freemarker engine. It
 * suggest map model is dynamic map, so new Object/Template models are add
 * during runtime. It extends functionality of TemplateHashModel to put objects
 * into map.
 * 
 * @author martin.krajc
 */
public interface TemplateModelMap extends TemplateHashModelEx {

	/**
	 * Put new template model.
	 * 
	 * @param name
	 *            the name of template model
	 * @param model
	 *            the template model
	 */
	void put(String name, TemplateModel model);

	/**
	 * Put object into map, it will be wrapped when asked for.
	 * 
	 * @param name
	 *            the name of object
	 * @param value
	 *            the object
	 */
	void put(String name, Object value);

	/**
	 * Put another map into map model.
	 * 
	 * @param params
	 *            the params
	 */
	void putAll(Map<String, ?> params);

	/**
	 * Wrap given object to template model and put into map. If you want to use
	 * lazy wrapping use {@link #put(String, Object)}
	 * 
	 * @param name
	 *            the name
	 * @param object
	 *            the object to wrap and put
	 * @throws TemplateModelException
	 *             if wrapping failed
	 */
	void wrapAndPut(String name, Object object) throws TemplateModelException;

	/**
	 * If map model contains Object under given name.
	 * 
	 * @param name
	 *            the checked name
	 * @return true, if successful
	 */
	boolean contains(String name);

	/**
	 * Gets the object wrapper used for wrapping.
	 * 
	 * @return the object wrapper
	 */
	ObjectWrapper getObjectWrapper();

	/**
	 * Clear.
	 */
	void clear();

}
