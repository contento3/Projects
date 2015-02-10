package com.contento3.cms.page.template.service.impl;

import java.util.Collection;

import org.apache.commons.lang.Validate;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.cache.StandardCacheManager;

import com.contento3.account.dao.AccountDao;
import com.contento3.account.model.Account;
import com.contento3.cms.page.template.dao.TemplateDao;
import com.contento3.cms.page.template.dao.TemplateDirectoryDao;
import com.contento3.cms.page.template.dao.TemplateTypeDao;
import com.contento3.cms.page.template.dto.TemplateDto;
import com.contento3.cms.page.template.model.SystemTemplateNameEnum;
import com.contento3.cms.page.template.model.Template;
import com.contento3.cms.page.template.model.TemplateDirectory;
import com.contento3.cms.page.template.model.TemplateType;
import com.contento3.cms.page.template.service.TemplateAssembler;
import com.contento3.cms.page.template.service.TemplateService;
import com.contento3.cms.site.structure.dao.SiteDAO;
import com.contento3.cms.site.structure.model.Site;
import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.common.exception.EntityNotFoundException;
import com.contento3.common.exception.ResourceNotFoundException;

@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
public class TemplateServiceImpl implements TemplateService {

        private String contentType;

        private String templateName;
        
        private String parentDirectory;
        
        String uriElement[];
        
        private String requestedPath = "";
                          
        private TemplateDao templateDao;
        
        private TemplateAssembler templateAssembler;
        
        private TemplateTypeDao templateTypeDao;
        
        private TemplateDirectoryDao templateDirectoryDao;

        private AccountDao accountDao;
        
        private SiteDAO siteDao;
        
        public TemplateServiceImpl(final TemplateAssembler assembler,
                                                final AccountDao accountDao,
                                                final TemplateDirectoryDao templateDirectoryDao,
                                                final TemplateDao templateDao,
                                                final TemplateTypeDao templateTypeDao,
                                                final SiteDAO siteDao){
                Validate.notNull(assembler,"assembler cannot be null");
                Validate.notNull(accountDao,"accountDao cannot be null");
                Validate.notNull(templateDirectoryDao,"templateDirectoryDao cannot be null");
                Validate.notNull(templateDao,"templateDao cannot be null");
                Validate.notNull(templateTypeDao,"templateTypeDao cannot be null");
                Validate.notNull(siteDao,"siteDao cannot be null");
                
                this.templateDao = templateDao;
                this.templateAssembler = assembler;
                this.templateTypeDao = templateTypeDao;
                this.templateDirectoryDao = templateDirectoryDao;
                this.accountDao = accountDao;
                this.siteDao = siteDao;
        }
        
    	@RequiresPermissions(value = { "TEMPLATE:ADD", "TEMPLATE_LIBRARY:ADD" }, logical = Logical.OR) 
        @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
        @Override
        public Integer create(final TemplateDto templateDto) throws EntityAlreadyFoundException {
                Validate.notNull(templateDto,"templateDto cannot be null");
                return templateDao.persist(buildTemplateInstance(templateDto));
        }

    	@RequiresPermissions(value = { "TEMPLATE:VIEW_LISTING", "TEMPLATE_LIBRARY:VIEW_LISTING" }, logical = Logical.OR) 
        @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
        @Override
        public Collection<TemplateDto> findTemplateByDirectoryId(final Integer directoryId) {
                Validate.notNull(directoryId,"directoryId cannot be null");
                final Collection<Template> templateList = templateDao.findTemplateByDirectoryId(directoryId);
                return templateAssembler.domainsToDtos(templateList);
        }
        
    	@RequiresPermissions(value = { "TEMPLATE:VIEW", "TEMPLATE_LIBRARY:VIEW" }, logical = Logical.OR) 
        @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
        @Override
        public TemplateDto findTemplateById(final Integer templateId) {
                Validate.notNull(templateId,"templateId cannot be null");
                Template template = templateDao.findById(templateId);
                return templateAssembler.domainToDto(template,new TemplateDto());
        }
        
    	@RequiresPermissions(value = { "TEMPLATE:VIEW", "TEMPLATE_LIBRARY:VIEW" }, logical = Logical.OR) 
        @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
        @Override
        public TemplateDto findTemplateByNameAndAccount(String templatePath,Integer accountId) throws ResourceNotFoundException {
                Validate.notNull(templatePath,"templatePath cannot be null");
                Validate.notNull(accountId,"accountId cannot be null");
                split(templatePath);
                Collection<Template> templateList = templateDao.findTemplateByNameAndAccount(templateName, parentDirectory, "text/freemarker", accountId);
                
                if (CollectionUtils.isEmpty(templateList)){
                        throw new ResourceNotFoundException();
                }
                
                Template originalTemplate = new Template();
                for(Template template : templateList){
                        requestedPath.equals(buildTemplatePath(template));
                        originalTemplate = template;
                }
                
                return templateAssembler.domainToDto(originalTemplate,new TemplateDto());
        }

    	@RequiresPermissions(value = { "TEMPLATE:VIEW", "TEMPLATE_LIBRARY:VIEW" }, logical = Logical.OR) 
        @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
        @Override
        public TemplateDto findTemplateByKeyAndAccount(String templateKey,Integer accountId) throws ResourceNotFoundException {
                Validate.notNull(templateKey,"templateKey cannot be null");
                Validate.notNull(accountId,"accountId cannot be null");
                //split(templateKey);
                Template template = templateDao.findTemplateByKeyAndAccount(templateKey, "text/freemarker", accountId);
                
                if (template == null){
                        throw new ResourceNotFoundException();
                }
                return templateAssembler.domainToDto(template,new TemplateDto());
        }

    	@RequiresPermissions(value = { "TEMPLATE:VIEW", "TEMPLATE_LIBRARY:VIEW" }, logical = Logical.OR) 
        @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
        @Override
        public TemplateDto findTemplateByNameAndSiteId(String templatePath,Integer siteId) throws ResourceNotFoundException {
                Validate.notNull(siteId,"siteId cannot be null");
                Validate.notNull(siteDao,"siteDao cannot be null");
                Site site = siteDao.findById(siteId);
                return findTemplateByNameAndAccount(templatePath,site.getAccount().getAccountId());
        }

    	@RequiresPermissions(value = { "TEMPLATE:EDIT", "TEMPLATE_LIBRARY:EDIT" }, logical = Logical.OR) 
        @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
        @Override
        public void updateTemplate(final TemplateDto templateDto) throws EntityAlreadyFoundException {
                Validate.notNull(templateDto,"templateDto cannot be null");
                templateDao.update(buildTemplateInstance(templateDto));
        }

    	@RequiresPermissions(value = { "TEMPLATE:VIEW", "TEMPLATE_LIBRARY:VIEW" }, logical = Logical.OR) 
        @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
        @Override
        public TemplateDto findSystemTemplateForAccount(final SystemTemplateNameEnum templateCategory,final Integer accountId) throws EntityNotFoundException {
                Validate.notNull(templateCategory,"template category cannot be null");
                Validate.notNull(accountId,"accountId cannot be null");
                
                String category = templateCategory.toString();
                Template template = templateDao.findSystemTemplateForAccount(category,accountId);
                                
                //If there is no template defined for this account and category, the template object will be 
                //null. In that case we should find the global system template for the given category.
                if (null==template){
                        template = templateDao.findGlobalSystemTemplate(category);
                }
                
                if (null==template)
                        throw new EntityNotFoundException("No system template either for account or global found for category: "+category);
                
                return templateAssembler.domainToDto(template,new TemplateDto());
        }

        private String buildTemplatePath(final Template template){
                Validate.notNull(template,"template cannot be null");
                String templateName = template.getTemplateName();
                return templateNameAppender(template.getDirectory(),templateName);
        }
    	
    	@Override
        public String buildTemplatePath(final TemplateDto templateDto){
    		final Template template = templateAssembler.dtoToDomain(templateDto, new Template());
    		return buildTemplatePath(template);
       }
        
        private String templateNameAppender(final TemplateDirectory templateDirectory,String value){
                Validate.notNull(templateDirectory,"templateDirectory cannot be null");
                Validate.notNull(value,"value cannot be null");
                value = String.format("%s/%s", templateDirectory.getDirectoryName(),value);
                if (null!=templateDirectory.getParent()){
                        value = templateNameAppender(templateDirectory.getParent(),value);
                }
                
                return value;
        }
        
        /**
         * Splits a path into requestedPath,parentDirectory.
         * @param templatePath
         */
        private void split(final String templatePath){
                Validate.notNull(templatePath,"templatePath cannot be null");
                uriElement = templatePath.split("/");
                
                if (uriElement[1].equals("css")){
                        contentType = "text/css";
                }
                else if  (uriElement[1].equals("js")){
                        contentType = "text/javascript";
                }
                templateName = uriElement[uriElement.length-1]; 
                
                int count = 1;
                while (count<uriElement.length){
                        requestedPath = String.format("%s/%s",requestedPath, uriElement[count]);
                        count++;
                }
                
                parentDirectory = uriElement[uriElement.length-2];
        }
        
        /**
         * Fills in the all the associated objects so 
         * that Template can be added or updated.
         * @param templateDto
         * @return
         * @throws EntityAlreadyFoundException 
         */
        private Template buildTemplateInstance(final TemplateDto templateDto) throws EntityAlreadyFoundException{
                Validate.notNull(templateDto,"templateDto cannot be null");
                TemplateType templateType = templateTypeDao.findByName(templateDto.getTemplateType().getTemplateTypeName());
                Account account = accountDao.findById(templateDto.getAccountDto().getAccountId());
                TemplateDirectory templateDirectory = templateDirectoryDao.findById(templateDto.getTemplateDirectoryDto().getId());
                
                Collection<Template> siblingTemplates = templateDao.findTemplateByDirectoryId(templateDirectory.getId());
                for (Template siblingTemplate : siblingTemplates)
                if (siblingTemplate.getTemplateName().equals(templateDto.getTemplateName()) && !siblingTemplate.getTemplateId().equals(templateDto.getTemplateId()))
                {
                	throw new EntityAlreadyFoundException();
                }
                
                templateDirectory.setAccount(account);
                
                Template template = null;
                if (null!=templateDto.getTemplateId())
                        template = templateDao.findById(templateDto.getTemplateId());
                else {
                        template = new Template();
                }
                
                if (templateDirectory.isGlobal()){
                	templateDto.setGlobal(true);
                }
                	
                template = templateAssembler.dtoToDomain(templateDto,template);
                template.setDirectory(templateDirectory);
                template.setTemplateType(templateType);
                template.setAccount(account);
                return template;
        }

        @Override
        public void delete(final TemplateDto dtoToDelete) {
                Validate.notNull(dtoToDelete,"dtoToDelete cannot be null");
                templateDao.delete(templateAssembler.dtoToDomain(dtoToDelete, new Template()));
        }

        @Override
        public TemplateDto findTemplateByPathAndAccount(final String path,
                        final Integer accountId) throws Exception {
                Validate.notNull(path,"template path cannot be null");
                Validate.notNull(accountId,"template path cannot be null");

                String [] templateParts = path.split("/");
                String dirPath = "";
                for (int i=1;i<templateParts.length-1;i++)
                {
                        dirPath = dirPath + "/" + templateParts[i];
                }        
                return templateAssembler.domainToDto(templateDao.findTemplateByPathAndAccount(dirPath, templateParts[templateParts.length-1], accountId),new TemplateDto());

        }

        @Override
        public void clearCache(final Integer templateId) {
                StandardCacheManager cachemanager = new StandardCacheManager();
                String path= templateDao.findById(templateId).getTemplatePath();
                String templateName = templateDao.findById(templateId).getTemplateName();
                cachemanager.getTemplateCache().clearKey("aboutus");
        }

		@Override
		public TemplateDto findGlobalTemplateByKey(String templateKey) throws ResourceNotFoundException {
			return this.findTemplateByKeyAndAccount(templateKey, 1);
		}
}