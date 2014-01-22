package com.contento3.dam.storagetype.dto;

import java.util.Date;

import com.contento3.common.dto.Dto;

public class StorageTypeDto extends Dto {
	
	/**
	 * Storage id of the StorageType
	 */
	private Integer storageTypeId;

	/**
	 * Name of StorageType
	 */
	private String name;

	/**
	 * Description of StorageType
	 */
	private String description;

	/**
	 * Start date of the StorageType
	 */
	private Date startDate;

	/**
	 * End date of the StorageType
	 */
	private Date endDate;

	/**
	 * Gets the storage id of the StorageType
	 */
	public Integer getStorageTypeId() {
		return storageTypeId;
	}

	/**
	 * Gets the storage id of the StorageType
	 */
	@Override
	public Integer getId() {
		return storageTypeId;
	}

	/**
	 * Sets the storage id of the StorageType
	 */
	public void setStorageTypeId(final Integer storageTypeId) {
		this.storageTypeId = storageTypeId;
	}

	/**
	 * Gets the Name of the StorageType
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * Sets the Name of the StorageType
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Gets the description of the Storage Type
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description of the StorageType
	 */
	public void setDescription(final String description) {
		this.description = description;
	}

	/**
	 * Gets the start date of the StorageType
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * Sets the start date of the StorageType
	 */
	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * Get the end date of the StorageType
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * Sets the end date of the StorageType
	 */
	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}
}
