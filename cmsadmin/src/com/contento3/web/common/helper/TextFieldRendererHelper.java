package com.contento3.web.common.helper;

import com.vaadin.ui.Button;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TextField;

/**
 * This class is used in rendering input elements.This can be used 
 * so that we don't have to write the same boiler plate ui input code
 * again and again.  
 * @author HAMMAD
 *
 */
public class TextFieldRendererHelper {
	/**
	 * The method creates number of text fields with 
	 * name passed in the method and adds them into the 
	 * parent component passed 
	 * @param parent
	 * @param name
	 */
	public void addTextInputs(Layout layout,String ... textFieldNames){
		for (String textFieldName : textFieldNames){
			TextField textField = new TextField();
			textField.setCaption(textFieldName);
			layout.addComponent(textField);
		}
	}
	
	/**
	 * USed to add a single text field and add in the component
	 * @param layout
	 * @param textFieldName
	 */
	public void addTextInput(Layout layout,String textFieldName){
		TextField textField = new TextField();
		textField.setCaption(textFieldName);
		layout.addComponent(textField);
	}

	
	/**
	 * Create a submit button and adds it in the layout 
	 * @param layout
	 * @param textFieldName
	 */
	public void addSubmitButton(Layout layout,String buttonText){
		Button newButton = new Button();
		newButton.setCaption(buttonText);
		layout.addComponent(newButton);
	}

}
