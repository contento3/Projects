package com.contento3.security.group.service;

import com.contento3.common.assembler.AssemblerNew;
import com.contento3.security.group.dto.GroupDto;
import com.contento3.security.group.model.Group;

public interface GroupAssembler extends AssemblerNew<GroupDto,Group> {

	 Group dtoToDomain(GroupDto dto,Group domain);
}
