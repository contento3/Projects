package com.contento3.dam.image.model;

import java.io.File;
import java.util.Collection;
import java.util.UUID;

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
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.contento3.account.model.Account;
import com.contento3.cms.site.structure.model.Site;
import com.contento3.dam.content.storage.Storable;
import com.contento3.dam.image.library.model.ImageLibrary;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "IMAGE")
public class Image implements Storable {

	@Column(columnDefinition="TEXT", length = 100, name = "IMAGE_UUID",
			unique=true, nullable=false)
	private String imageUuid=UUID.randomUUID().toString();
	
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
	
	@Transient
	private File file;
	
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

	@Override
	public ImageLibrary getLibrary() {
		return this.imageLibrary;
	}

	@Override
	public File getFile() {
		return this.file;
	}

	@Override
	public void setFile(final File file) {
		this.file = file;
	}
}
