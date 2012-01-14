/**
 * This class //TODO
 * @author : Hammad Afridi
 * Created : 10/16/2011
 */

package com.contento3.cms.page.template.dao;

import com.contento3.cms.page.template.model.TemplateType;
import com.contento3.common.dao.GenericDao;

public interface TemplateTypeDao extends GenericDao<TemplateType,Integer>{

	TemplateType findByName(String name);
	
}
