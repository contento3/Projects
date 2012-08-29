package com.contento3.cms.page.template.service.impl;

import java.util.Collection;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.contento3.account.dao.AccountDao;
import com.contento3.account.model.Account;
import com.contento3.cms.page.section.model.PageSectionTypeEnum;
import com.contento3.cms.page.template.dao.TemplateDao;
import com.contento3.cms.page.template.dao.TemplateDirectoryDao;
import com.contento3.cms.page.template.dao.TemplateTypeDao;
import com.contento3.cms.page.template.dto.TemplateDto;
import com.contento3.cms.page.template.model.Template;
import com.contento3.cms.page.template.model.TemplateDirectory;
import com.contento3.cms.page.template.model.TemplateType;
import com.contento3.cms.page.template.service.TemplateAssembler;
import com.contento3.cms.page.template.service.TemplateService;
import com.contento3.cms.site.structure.dao.SiteDAO;
import com.contento3.cms.site.structure.model.Site;
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
	
	TemplateServiceImpl(final TemplateAssembler assembler,
						final AccountDao accountDao,
						final TemplateDirectoryDao templateDirectoryDao,
						final TemplateDao templateDao,final TemplateTypeDao templateTypeDao,final SiteDAO siteDao){
		this.templateDao = templateDao;
		this.templateAssembler = assembler;
		this.templateTypeDao = templateTypeDao;
		this.templateDirectoryDao = templateDirectoryDao;
		this.accountDao = accountDao;
		this.siteDao = siteDao;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public Integer create(final TemplateDto templateDto) {
		return templateDao.persist(buildTemplateInstance(templateDto));
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public Collection<TemplateDto> findTemplateByDirectoryName(final String directoryName) {
		Collection<Template> templateList = templateDao.findTemplateByDirectoryName(directoryName);
		return templateAssembler.domainsToDtos(templateList);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public TemplateDto findTemplateById(final Integer templateId) {
		Template template = templateDao.findById(templateId);
		return templateAssembler.domainToDto(template);
	}
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public TemplateDto findTemplateByPathAndAccount(String templatePath,Integer accountId) throws ResourceNotFoundException {
		split(templatePath);
		Collection<Template> templateList = templateDao.findTemplateByPathAndAccount(templateName, parentDirectory, "text/freemarker", accountId);
		
		if (CollectionUtils.isEmpty(templateList)){
			throw new ResourceNotFoundException();
		}
		
		Template originalTemplate = new Template();
		for(Template template : templateList){
			requestedPath.equals(buildTemplatePath(template));
			originalTemplate = template;
		}
		
		return templateAssembler.domainToDto(originalTemplate);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public TemplateDto findTemplateByPathAndSiteId(String templatePath,Integer siteId) throws ResourceNotFoundException {
		Site site = siteDao.findById(siteId);
		return findTemplateByPathAndAccount(templatePath,site.getAccount().getAccountId());
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public void updateTemplate(final TemplateDto templateDto) {
		templateDao.update(buildTemplateInstance(templateDto));
	}

	private String buildTemplatePath(final Template template){
		String templateName = template.getTemplateName();
		return 	templateNameAppender(template.getDirectory(),templateName);
	}
	
	private String templateNameAppender(final TemplateDirectory templateDirectory,String value){
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
	 */
	private Template buildTemplateInstance(final TemplateDto templateDto){
		TemplateType templateType = templateTypeDao.findByName(templateDto.getTemplateType().getTemplateTypeName());
		Account account = accountDao.findById(templateDto.getAccountDto().getAccountId());
		TemplateDirectory templateDirectory = templateDirectoryDao.findById(templateDto.getTemplateDirectoryDto().getId());
		
		templateDirectory.setAccount(account);
		Template template = templateAssembler.dtoToDomain(templateDto);
		template.setDirectory(templateDirectory);
		template.setTemplateType(templateType);
		template.setAccount(account);
		return template;
	}
}
