package com.contento3.dam.image.library.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "IMAGE_LIBRARY")
public class ImageLibrary {
	/**
	 * Primary key
	 */
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="LIBRARY_ID")
	private Integer id;
	
	/**
	 * Name for image 
	 */
	@Column(name="NAME")
	private String name;
	
	/**
	 * Description of image
	 */
	@Column(name="DESCRIPTION")
	private String description;
	
	public Integer getId() {
		return id;
	}
	public void setId(final Integer id) {
		this.id = id;
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

}
