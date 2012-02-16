package com.contento3.cms.page.service;

import com.contento3.cms.page.dto.PageDto;
import com.contento3.cms.page.model.Page;
import com.contento3.common.assembler.Assembler;

public interface PageAssembler extends Assembler<PageDto,Page> {

	 Page dtoToDomain(PageDto dto,Page domain);
}
