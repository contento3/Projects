package com.olive.common.assembler;

import java.util.Collection;

public interface Assembler<DTO,DOMAIN> {

	DOMAIN dtoToDomain(DTO dto);
	
	DTO domainToDto(DOMAIN domain);
	
	Collection<DTO> domainsToDtos(Collection<DOMAIN> domains);

	Collection<DOMAIN> dtosToDomains(Collection<DTO> dtos);
	
}
