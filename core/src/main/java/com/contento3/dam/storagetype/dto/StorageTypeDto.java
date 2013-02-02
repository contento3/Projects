package com.contento3.dam.storagetype.dto;

import java.util.Date;

import com.contento3.common.dto.Dto;

public class StorageTypeDto extends Dto {
	
	/**
	 * Storage id of the StorageType
	 */
	private long storage_id;

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
	private Date start_date;

	/**
	 * End date of the StorageType
	 */
	private Date end_date;

	/**
	 * Gets the storage id of the StorageType
	 */
	public long getStorage_id() {
		return storage_id;
	}

	/**
	 * Sets the storage id of the StorageType
	 */
	public void setStorage_id(long storage_id) {
		this.storage_id = storage_id;
	}

	/**
	 * Gets the Name of the StorageType
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the Name of the StorageType
	 */
	public void setName(String name) {
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
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the start date of the StorageType
	 */
	public Date getStart_date() {
		return start_date;
	}

	/**
	 * Sets the start date of the StorageType
	 */
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	/**
	 * Get the end date of the StorageType
	 */
	public Date getEnd_date() {
		return end_date;
	}

	/**
	 * Sets the end date of the StorageType
	 */
	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}
}
