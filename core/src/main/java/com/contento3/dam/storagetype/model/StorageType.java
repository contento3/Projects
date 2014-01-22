package com.contento3.dam.storagetype.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/* Model for table platform_user/storage_type
 * Author: Syed Muhammad Ali
 */

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "STORAGE_TYPE", schema = "CMS")
public class StorageType implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue (strategy=GenerationType.AUTO)
	@Column( name = "STORAGE_TYPE_ID")
	private Integer storageTypeId;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@Column(name = "START_DATE")
	private Date startDate;

	@Column(name = "END_DATE")
	private Date endDate;
	
	public Integer getStorageTypeId() {
		return storageTypeId;
	}
	
	public void setStorageTypeId(final Integer storageTypeId) {
		this.storageTypeId = storageTypeId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(final String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(final String description) {
		this.description = description;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	
	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}
	
	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}
}

