package com.contento3.module.email.model;

import java.util.Collection;


public class EmailInfo {

	private String to;
	
	private String from;

	private String subject;
	
	private String emailText;
	
	private String mimeType;
	
	private Collection<String> replyToAddresses;
	
	public String getTo() {
		return to;
	}

	public void setTo(final String to) {
		this.to = to;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(final String from) {
		this.from = from;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(final String subject) {
		this.subject = subject;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(final String mimeType) {
		this.mimeType = mimeType;
	}

	public String getEmailText() {
		return emailText;
	}

	public void setEmailText(final String emailText) {
		this.emailText = emailText;
	}

	public Collection<String> getReplyToAddresses() {
		return replyToAddresses;
	}

	public void setReplyToAddresses(final Collection<String> replyToAddresses) {
		this.replyToAddresses = replyToAddresses;
	}

}
