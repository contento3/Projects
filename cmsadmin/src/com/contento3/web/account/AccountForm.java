package com.contento3.web.account;

import com.contento3.web.common.UIContext;
import com.vaadin.ui.Button;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;

public class AccountForm extends UIContext{
	
	/**
	 * Textfield for firstnaem
	 */
	private TextField firstName = new TextField();
	
	/**
	 * Textfield for lastname
	 */
	private TextField lastName = new TextField();

	/**
	 * Textfield for email address
	 */
	private TextField email = new TextField();

	/**
	 * Textfield for old password
	 */
	private TextField oldPassword = new TextField();

	/**
	 * Textfield for new password
	 */
	private PasswordField newPassword = new PasswordField();

	/**
	 * Textfield for confirming new password
	 */
	private PasswordField confirmNewPassword = new PasswordField();

	/**
	 * Submit button form the form
	 */
	private Button submitButton = new Button("Submit");
	
	/**
	 * Get the first name textfield
	 * @param None
	 * @return TextField
	 */
	public TextField getFirstName() {
		return firstName;
	}

	/**
	 * Set the first name textfield
	 * @param TextField
	 * @return void
	 */
	public void setFirstName(TextField firstName) {
		this.firstName = firstName;
	}

	/**
	 * Get the last name textfield
	 * @param None
	 * @return TextField
	 */
	public TextField getLastName() {
		return lastName;
	}

	/**
	 * Set the last name textfield
	 * @param TextField
	 * @return void
	 */
	public void setLastName(TextField lastName) {
		this.lastName = lastName;
	}

	/**
	 * Get the email textfield
	 * @param None
	 * @return TextField
	 */
	public TextField getEmail() {
		return email;
	}

	/**
	 * Set the email textfield
	 * @param TextField
	 * @return void
	 */
	public void setEmail(TextField email) {
		this.email = email;
	}

	/**
	 * Get the old password textfield
	 * @param None
	 * @return TextField
	 */
	public TextField getOldPassword() {
		return oldPassword;
	}

	/**
	 * Set the old password textfield
	 * @param TextField
	 * @return void
	 */
	public void setOldPassword(TextField oldPassword) {
		this.oldPassword = oldPassword;
	}

	/**
	 * Get the new password textfield
	 * @param None
	 * @return TextField
	 */
	public PasswordField getNewPassword() {
		return newPassword;
	}

	/**
	 * Set the new password textfield
	 * @param TextField
	 * @return void
	 */
	public void setNewPassword(PasswordField newPassword) {
		this.newPassword = newPassword;
	}

	/**
	 * Get the confirm new password textfield
	 * @param None
	 * @return TextField
	 */
	public PasswordField getConfirmNewPassword() {
		return confirmNewPassword;
	}

	/**
	 * Set the confirm new password textfield
	 * @param TextField
	 * @return void
	 */
	public void setConfirmNewPassword(PasswordField confirmNewPassword) {
		this.confirmNewPassword = confirmNewPassword;
	}

	/**
	 * Get the submit button
	 * @param None
	 * @return Button
	 */
	public Button getSubmitButton() {
		return submitButton;
	}

	/**
	 * Set the submit button
	 * @param Button
	 * @return void
	 */
	public void setSubmitButton(Button submitButton) {
		this.submitButton = submitButton;
	}
}
