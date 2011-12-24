package com.contento3.site.template.model;

import java.util.Collection;

import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

public class TemplateModelContext implements TemplateHashModel {

	private Collection<TemplateModelMap> models;
	
	public Collection<TemplateModelMap> getModels() {
		return models;
	}

	public void setModels(Collection<TemplateModelMap> models) {
		this.models = models;
	}

	@Override
	public freemarker.template.TemplateModel get(String key)
			throws TemplateModelException {
		TemplateModel model = null;

		for (TemplateHashModel hashModel : models) {
			if (hashModel == null) {
				// if mapModel is not set go for next
				continue;
			}
			// find model in map by key
			model = hashModel.get(key);

			// when exists break from cycle
			if (model != null) {
				break;
			}
		}

		// finally check if some model exist
		if (model == null) {
			// this method is declared from Freemarker API so cannot throw
			// anything else than TemplateModelException
			// so I build cause first and then pass it to
			// TemplateModelException
			throw new TemplateModelException();
		}
		return model;
	}

	@Override
	public boolean isEmpty() throws TemplateModelException {
		return (models == null) ? true : models.isEmpty();
	}
	
}
