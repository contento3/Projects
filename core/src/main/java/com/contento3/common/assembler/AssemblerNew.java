package com.contento3.common.assembler;

import java.util.Collection;

public interface AssemblerNew<DTO, DOMAIN> {

	DOMAIN dtoToDomain(DTO dto,DOMAIN domain);
		
	DTO domainToDto(DOMAIN domain,DTO dto);
		
	Collection<DTO> domainsToDtos(Collection<DOMAIN> domains);

	Collection<DOMAIN> dtosToDomains(Collection<DTO> dtos);
		
}
