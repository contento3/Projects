package com.contento3.cms.page.template.dao.impl;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.CollectionUtils;

import com.contento3.cms.page.template.dao.TemplateDao;
import com.contento3.cms.page.template.model.Template;
import com.contento3.common.spring.dao.GenericDaoSpringHibernateTemplate;

public class TemplateDaoHibernateImpl extends GenericDaoSpringHibernateTemplate<Template,Integer> 
 implements  TemplateDao {

        private static final String CACHE_REGION = "com.contento3.cms.page.template.model.Template";

        public TemplateDaoHibernateImpl() {
                super(Template.class);
        }

        @Override
        public Collection<Template> findTemplateByDirectoryName(final String name){
                Validate.notNull(name,"name cannot be null");
                
                final Criteria criteria = this.getSession()
                .createCriteria(Template.class)
                .setCacheable(true)
                .setCacheRegion(CACHE_REGION)
                .createCriteria("directory")
                .add(Restrictions
                .eq("directoryName", name));

                return criteria.list();
        }
        
        @Override
        public Collection<Template> findTemplateByDirectoryId(final Integer id){
                Validate.notNull(id,"name cannot be null");
                
                Criteria criteria = this.getSession()
                .createCriteria(Template.class)
                .setCacheable(true)
                .setCacheRegion(CACHE_REGION)
                .createCriteria("directory")
                .add(Restrictions
                .eq("id", id));

                return criteria.list();
        }


        @Override
        public Collection<Template> findTemplateByNameAndAccount(final String templateName,final String parentDirectory,
                        final String templateType,final Integer accountId){
                Validate.notNull(templateName,"templateName cannot be null");
                Validate.notNull(parentDirectory,"parentDirectory cannot be null");
                Validate.notNull(templateType,"templateType cannot be null");
                Validate.notNull(accountId,"accountId cannot be null");
                
                final Criteria criteria = this.getSession()
                .createCriteria(Template.class,"template")
                .setCacheable(true)
                .setCacheRegion(CACHE_REGION)
                .add(Restrictions
                .eq("template.templateName", templateName));
                
                Criteria directoryCriteria = criteria.createCriteria("directory").add(Restrictions
                                .eq("directoryName", parentDirectory));
                
                Criteria templateTypeCriteria = criteria
                .createCriteria("templateType").add(Restrictions
                .eq("contentType", templateType));
                
                Criteria accountCriteria = criteria
                .createCriteria("account").add(Restrictions
                                .eq("accountId", accountId));
                        
                return criteria.list();
        }

        @Override
        public Template findSystemTemplateForAccount(final String templateCategory,
                        final Integer accountId) {
                Validate.notNull(templateCategory,"templateCategory cannot be null");
                Validate.notNull(accountId,"accountId cannot be null");

                final Criteria criteria = this.getSession()
                .createCriteria(Template.class)
                .setCacheable(true)
                .setCacheRegion(CACHE_REGION);
                
                criteria.createAlias("templateCategory","templateCategory")
                .add(Restrictions
                .eq("templateCategory.templateCategoryName", templateCategory));

                criteria.createAlias("account","account")
               .add(Restrictions
               .eq("account.accountId", accountId));
                
                Template template = null;
                if (!CollectionUtils.isEmpty(criteria.list())){
                        template = (Template)criteria.list().get(0);
                }        
                
                return template;
        }

        @Override
        public Template findGlobalSystemTemplate(final String templateCategory)         {
                Validate.notNull(templateCategory,"templateCategory cannot be null");

                final Criteria criteria = this.getSession()
                .createCriteria(Template.class)
                .setCacheable(true)
                .setCacheRegion(CACHE_REGION)
                .add(Restrictions
                .eq("isGlobal", true));
                
                criteria.createAlias("templateCategory","templateCategory")
                .add(Restrictions
                .eq("templateCategory.templateCategoryName", templateCategory));

                Template template = null;
                if (!CollectionUtils.isEmpty(criteria.list())){
                        template = (Template)criteria.list().get(0);
                }        
                
                return template;
        }

        @Override
        public Template findTemplateByPathAndAccount(
                        String path, String templateName, Integer accountId) {
                final Criteria criteria = this.getSession()
                .createCriteria(Template.class)
                .setCacheable(true)
                .setCacheRegion(CACHE_REGION)
                .add(Restrictions
                .eq("templateName", templateName))
                .add(Restrictions
                .eq("templatePath", path));
                
                criteria.createAlias("account","account")
                .add(Restrictions
                .eq("account.accountId", accountId));

                Template template = null;
                if (!CollectionUtils.isEmpty(criteria.list())){
                        template = (Template)criteria.list().get(0);
                }        
                
                return template;
        }

		@Override
		public Template findTemplateByKeyAndAccount(String templateKey,
				String contentType, Integer accountId) {

			Validate.notNull(templateKey,"templateKey cannot be null");
            Validate.notNull(contentType,"contentType cannot be null");
            Validate.notNull(accountId,"accountId cannot be null");
            
            final Criteria criteria = this.getSession()
            .createCriteria(Template.class,"template")
            .setCacheable(true)
            .setCacheRegion(CACHE_REGION)
            .add(Restrictions
            .eq("template.templateKey", templateKey));
            
            Criteria templateTypeCriteria = criteria
            .createCriteria("templateType").add(Restrictions
            .eq("contentType", contentType));
            
            Criteria accountCriteria = criteria
            .createCriteria("account").add(Restrictions
                            .eq("accountId", accountId));
                    
            Template template = null;
            if (!CollectionUtils.isEmpty(criteria.list())){
                    template = (Template)criteria.list().get(0);
            }        
            
            return template;
		}

}