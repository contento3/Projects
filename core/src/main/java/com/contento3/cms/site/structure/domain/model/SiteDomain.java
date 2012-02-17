package com.contento3.cms.site.structure.domain.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.contento3.cms.site.structure.model.Site;

@Entity
@Table(name="SITE_DOMAIN")
public class SiteDomain implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="DOMAIN_ID")
	private Integer domainId;
	
	@Column(name="DOMAIN_NAME")
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
