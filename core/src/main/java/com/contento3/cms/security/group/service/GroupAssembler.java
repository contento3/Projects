package com.contento3.cms.security.group.service;

import com.contento3.cms.security.group.dto.GroupDto;
import com.contento3.cms.security.group.model.Group;
import com.contento3.common.assembler.Assembler;

public interface GroupAssembler extends Assembler<GroupDto,Group> {

	 Group dtoToDomain(GroupDto dto,Group domain);
}
