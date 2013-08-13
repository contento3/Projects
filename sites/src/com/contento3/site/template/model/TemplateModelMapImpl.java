package com.contento3.site.template.model;

import java.util.HashMap;
import java.util.Map;

import freemarker.template.ObjectWrapper;
import freemarker.template.SimpleCollection;
import freemarker.template.TemplateCollectionModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

public class TemplateModelMapImpl implements TemplateModelMap {

	/** The map. */
	private Map<String, Object> map;

	/** The object wrapper. */
	private ObjectWrapper objectWrapper;

//	private RenderingContext renderingContext;
//
//	private List<ContextInterceptor> contextInterceptors;


	/**
	 * Gets the map.
	 *
	 * @return the map
	 */
	public Map<String, Object> getMap() {
		if (map == null) {
			map = new HashMap<String, Object>();
		}
		return map;
	}

	/**
	 * Sets the map.
	 *
	 * @param map
	 *            the map
	 */
	public void setMap(final Map<String, Object> map) {
		this.map = map;
	}

	public TemplateModelMapImpl() {
		this(ObjectWrapper.BEANS_WRAPPER);
	}

	public TemplateModelMapImpl(ObjectWrapper beansWrapper) {
		this.objectWrapper = beansWrapper;
	}

	@Override
	public TemplateCollectionModel keys() throws TemplateModelException {
		// TODO Auto-generated method stub
		return new SimpleCollection(getMap().keySet());
	}

	@Override
	public int size() throws TemplateModelException {
		// TODO Auto-generated method stub
		return getMap().size();
	}

	@Override
	public TemplateCollectionModel values() throws TemplateModelException {
		// 
		// it wraps into {@link SimpleCollection} template model, because there wasn't 
		// reason to some specially implementation 
		return new SimpleCollection(getMap().values());
	}

	@Override
	public TemplateModel get(String key) throws TemplateModelException {
		//notifyGet(key);
		Object value, wrapped;
		
		//
		// fetch from map
		value = getMap().get(key); 
		if ( value != null
				&& !(value instanceof TemplateModel)) {
			
			if ( value instanceof Map ) {
				TemplateModelMapImpl templateModelMapImpl = new TemplateModelMapImpl(getObjectWrapper());
				
				// 
				// cast set map 
				templateModelMapImpl.setMap((Map<String, Object>) value);
				
				// wrap into template model 
				wrapped = templateModelMapImpl; 	
			} else {
				wrapped = getObjectWrapper().wrap(value);
			}
			
			// lazy wrapping
			// if object is in map, wrap it by wrapper and substitute
			getMap().put(key, wrapped);

		}	
		return (TemplateModel) getMap().get(key);
	}

	@Override
	public boolean isEmpty() throws TemplateModelException {
		return getMap().isEmpty();
	}

	@Override
	public void put(String name, TemplateModel model) {
		getMap().put(name, model);
	}

	@Override
	public void put(String name, Object value) {
		getMap().put(name, value);
	}

	@Override
	public void putAll(Map<String, ?> params) {
		getMap().putAll(map);
	}

	@Override
	public void wrapAndPut(String name, Object object)
			throws TemplateModelException {
		getMap().put(name, getObjectWrapper().wrap(object));
	}

	@Override
	public boolean contains(String name) {
		return getMap().containsKey(name);
	}

	@Override
	public ObjectWrapper getObjectWrapper() {
		return objectWrapper;
	}

	@Override
	public void clear() {
		getMap().clear();
	}

	
}
