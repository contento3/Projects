/**
 * This class //TODO
 * @author : Hammad Afridi
 * Created : 10/16/2011
 */

package com.olive.cms.page.template.dao;

import com.olive.cms.page.template.model.TemplateType;
import com.olive.common.dao.GenericDao;

public interface TemplateTypeDao extends GenericDao<TemplateType,Integer>{

	TemplateType findByName(String name);
	
}
