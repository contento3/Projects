package com.olive.cms.page.template.service.impl;

import java.util.Collection;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.olive.cms.page.template.dao.TemplateDao;
import com.olive.cms.page.template.dao.TemplateDirectoryDao;
import com.olive.cms.page.template.dao.TemplateTypeDao;
import com.olive.cms.page.template.dto.TemplateDto;
import com.olive.cms.page.template.model.Template;
import com.olive.cms.page.template.model.TemplateDirectory;
import com.olive.cms.page.template.model.TemplateType;
import com.olive.cms.page.template.service.TemplateAssembler;
import com.olive.cms.page.template.service.TemplateService;

@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
public class TemplateServiceImpl implements TemplateService {

	private TemplateDao templateDao;
	private TemplateAssembler templateAssembler;
	private TemplateTypeDao templateTypeDao;
	private TemplateDirectoryDao templateDirectoryDao;
	
	TemplateServiceImpl(final TemplateAssembler assembler,final TemplateDirectoryDao templateDirectoryDao,
			final TemplateDao templateDao,final TemplateTypeDao templateTypeDao){
		this.templateDao = templateDao;
		this.templateAssembler = assembler;
		this.templateTypeDao = templateTypeDao;
		this.templateDirectoryDao = templateDirectoryDao;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public void create(final TemplateDto templateDto) {
		TemplateType templateType = templateTypeDao.findByName(templateDto.getTemplateType().getTemplateTypeName());
		Template template = templateAssembler.dtoToDomain(templateDto);

		template.setTemplateType(templateType);
		templateDao.persist(template);
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

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public void updateTemplate(final TemplateDto templateDto) {
		Template template = templateAssembler.dtoToDomain(templateDto);
		templateDao.update(template);
	}

}
