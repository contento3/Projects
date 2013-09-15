package com.contento3.dam.image.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.contento3.account.model.Account;
import com.contento3.cms.site.structure.model.Site;
import com.contento3.dam.image.library.model.ImageLibrary;

@Entity
@Table(name = "IMAGE")
public class Image {

	//@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy="com.contento3.hibernate.UUIDGenerator")
	@Column(name="IMAGE_UUID") 
	private String imageUuid ;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="IMAGE_ID")
	private Integer imageId;
	
	@Column(name="NAME") 
	private String name;
	
	@Column(name="ALT_TEXT") 
	private String altText;
	
	@OneToOne
	@JoinColumn(name = "ACCOUNT_ID")
	private Account account;
	
	@Lob
	@Column(name="IMAGE") 
	private byte[] image;
	
	/**
	 * sites that contain image
	 */
	@ManyToMany //uni directional
	@JoinTable(name="SITE_IMAGE",
	joinColumns={@JoinColumn(name="IMAGE_ID")},
	inverseJoinColumns={@JoinColumn(name="SITE_ID")})
	private Collection<Site> sites;

	/**
	 * Library associated with image
	 */
	@ManyToOne
	@JoinColumn(name = "LIBRARY_ID")
	private ImageLibrary imageLibrary;
	
	
	public final ImageLibrary getImageLibrary() {
		return imageLibrary;
	}

	public final void setImageLibrary(final ImageLibrary imageLibrary) {
		this.imageLibrary = imageLibrary;
	}

	public Collection<Site> getSites() {
		return sites;
	}

	public void setSites(Collection<Site> sites) {
		this.sites = sites;
	}

	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAltText() {
		return altText;
	}

	public void setAltText(String altText) {
		this.altText = altText;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public Integer getImageId() {
		return imageId;
	}

	public void setImageId(Integer imageId) {
		this.imageId = imageId;
	}

	public String getImageUuid() {
		return imageUuid;
	}

	public void setImageUuid(String imageUuid) {
		this.imageUuid = imageUuid;
	}
}
