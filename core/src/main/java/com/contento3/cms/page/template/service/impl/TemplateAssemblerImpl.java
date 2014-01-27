package com.contento3.cms.page.template.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.contento3.account.service.AccountAssembler;
import com.contento3.cms.page.template.dto.TemplateDto;
import com.contento3.cms.page.template.model.Template;
import com.contento3.cms.page.template.service.TemplateAssembler;
import com.contento3.cms.page.template.service.TemplateDirectoryAssembler;
import com.contento3.cms.page.template.service.TemplateTypeAssembler;

public class TemplateAssemblerImpl implements TemplateAssembler {

        private TemplateTypeAssembler templateTypeAssembler;
        private TemplateDirectoryAssembler templateDirectoryAssembler;
        private AccountAssembler accountAssembler;
        
        TemplateAssemblerImpl(final TemplateTypeAssembler templateTypeAssembler,
                        final TemplateDirectoryAssembler templateDirectoryAssembler,
                        final AccountAssembler accountAssembler){
                this.templateTypeAssembler = templateTypeAssembler;
                this.templateDirectoryAssembler = templateDirectoryAssembler;
                this.accountAssembler = accountAssembler;
        }
        
        @Override
        public Template dtoToDomain(TemplateDto dto,Template domain) {
                
                if (null!=dto.getTemplateId()){
                        domain.setTemplateId(dto.getTemplateId());
                }
                
                domain.setGlobal(dto.isGlobal());
                domain.setTemplateName(dto.getTemplateName());
                domain.setTemplateText(dto.getTemplateText());
                domain.setTemplateType(templateTypeAssembler.dtoToDomain(dto.getTemplateType()));
                domain.setTemplatePath(dto.getTemplatePath());
                //domain.setDirectory(templateDirectoryAssembler.dtoToDomain(dto.getTemplateDirectoryDto()));
                return domain;
        }

        @Override
        public TemplateDto domainToDto(Template domain,TemplateDto dto) {
                dto.setTemplateId(domain.getTemplateId());
                dto.setGlobal(domain.isGlobal());
                dto.setTemplateName(domain.getTemplateName());
                dto.setTemplateText(domain.getTemplateText());
                dto.setAccountDto(accountAssembler.domainToDto(domain.getAccount()));
                dto.setTemplateType(templateTypeAssembler.domainToDto(domain.getTemplateType()));
                dto.setTemplateDirectoryDto(templateDirectoryAssembler.domainToDto(domain.getDirectory()));
                dto.setTemplatePath(domain.getTemplatePath());
                return dto;
        }

        @Override
        public Collection<TemplateDto> domainsToDtos(Collection<Template> domains) {
                Collection<TemplateDto> dtos = new ArrayList<TemplateDto>();
                Iterator<Template> iterator = domains.iterator();
                while (iterator.hasNext()){
                        dtos.add(domainToDto(iterator.next(),new TemplateDto()));
                }
                return dtos;
        }

        @Override
        public Collection<Template> dtosToDomains(Collection<TemplateDto> dtos) {
                Collection <Template> domains = new ArrayList<Template>();
                
                Iterator<Template> iterator = domains.iterator();
                while (iterator.hasNext()){
                        dtos.add(domainToDto(iterator.next(),new TemplateDto()));
                }
                
                return domains;
        }

}