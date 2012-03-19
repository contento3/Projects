package com.contento3.security.group.service;

import com.contento3.common.assembler.Assembler;
import com.contento3.security.group.dto.GroupDto;
import com.contento3.security.group.model.Group;

public interface GroupAssembler extends Assembler<GroupDto,Group> {

	 Group dtoToDomain(GroupDto dto,Group domain);
}
