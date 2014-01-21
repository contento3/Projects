package com.contento3.dam.content.library.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.contento3.dam.image.library.model.ImageLibrary;
import com.contento3.dam.storagetype.model.StorageType;

@Entity
@Table(schema= "CMS", name = "CONTENT_LIBRARY_STORAGE_MAPPING")
public class ContentStorageLibraryMapping {

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID")
	private Integer id;
	
	@OneToOne
	@JoinColumn(name="LIBRARY_ID")
	private ImageLibrary library;
	
	@OneToOne
	@JoinColumn(name="STORAGE_TYPE_ID")
	private StorageType storageType;
	
	@Column(nullable=true, name="STORAGE_ID")
	private Integer storageId;
	
	@Column(name="CONTENT_TYPE")
	private String contentType;

	public ImageLibrary getLibrary() {
		return library;
	}

	public void setLibrary(final ImageLibrary library) {
		this.library = library;
	}

	public StorageType getStorageType() {
		return storageType;
	}

	public void setStorageType(StorageType storageType) {
		this.storageType = storageType;
	}

	public Integer getStorageId() {
		return storageId;
	}

	public void setStorageId(Integer storageId) {
		this.storageId = storageId;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	
}
