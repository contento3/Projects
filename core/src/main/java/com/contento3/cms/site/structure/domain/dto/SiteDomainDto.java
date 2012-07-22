package com.contento3.cms.site.structure.domain.dto;

import com.contento3.common.dto.Dto;


public class SiteDomainDto extends Dto {
	
	private Integer domainId;
	
	private String domainName;
	
	public Integer getDomainId() {
		return domainId;
	}

	public void setDomainId(final Integer domainId) {
		this.domainId = domainId;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(final String domainName) {
		this.domainName = domainName;
	}


}
