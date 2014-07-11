package com.contento3.web.email.marketing;

import com.contento3.web.common.Form;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

public class SubscriptionForm implements Form {

	private TextField subscriptionName;
	
	private TextArea subscriptionDescription;

	private TextField subscriberFirstName;

	private TextField subscriberLastName;
	
	private TextField subscriberEmail;
	
	public TextField getSubscriberFirstName() {
		return subscriberFirstName;
	}

	public void setSubscriberFirstName(final TextField subscriberFirstName) {
		this.subscriberFirstName = subscriberFirstName;
	}

	public TextField getSubscriberLastName() {
		return subscriberLastName;
	}

	public void setSubscriberLastName(final TextField subscriberLastName) {
		this.subscriberLastName = subscriberLastName;
	}

	public TextField getSubscriberEmail() {
		return subscriberEmail;
	}

	public void setSubscriberEmail(final TextField subscriberEmail) {
		this.subscriberEmail = subscriberEmail;
	}

	public TextArea getSubscriptionDescription() {
		return subscriptionDescription;
	}

	public void setSubscriptionDescription(final TextArea subscriptionDescription) {
		this.subscriptionDescription = subscriptionDescription;
	}

	public TextField getSubscriptionName() {
		return subscriptionName;
	}

	public void setSubscriptionName(final TextField subscriptionName) {
		this.subscriptionName = subscriptionName;
	}
}
