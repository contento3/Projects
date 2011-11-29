package com.olive.dam.image.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.olive.account.model.Account;

@Entity
@Table(name = "IMAGE")
public class Image {

	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy="com.olive.hibernate.UUIDGenerator")
	@Column(name="IMAGE_UUID") 
	private String imageUuid;
	
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

	public String getImageUuid() {
		return imageUuid;
	}

	public void setImageId(String imageUuid) {
		this.imageUuid = imageUuid;
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
}
