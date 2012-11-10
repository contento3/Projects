package com.contento3.web.common.helper;


import java.util.Collection;
import com.contento3.common.dto.Dto;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;

/**
 * Abstract GenricEntityPicker class
 */
public  class GenricEntityPicker extends CustomComponent implements Window.CloseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Dtos to be listed in table
	 */
	private final Collection<Dto> dtos;
	
	/**
	 * Vertical layout to add components
	 */
	private VerticalLayout vLayout;
	
	/**
	 * Contains list of columns to be generated
	 */
	private Collection<String> listOfColumns;
	
	/**
	 * Table builder for genric entity picker
	 */
	GenricEntityTableBuilder tableBuilder;
	
	/**
	 *  Reference to main window
	 */
	Window mainwindow; 
	
	/**
	 * The window to be opened
	 */
	Window popupWindow; 
	
	/**
	 * Button for opening the window
	 */
	Button openbutton; 
	
	/**
	 *  A button in the window
	 */
	Button closebutton; 
	
	/**
	 * Used to get service beans from spring context.
	 */
	SpringContextHelper helper;
	
	boolean isModalWindowClosable = true;
	
	/**
	 * Vertical layout to add components
	 */
	VerticalLayout popupMainLayout = new VerticalLayout();
	
	/**
	 * Contain calling object
	 */
	EntityListener entityListener;
	
	/**
	 * Constructor
	 * @param dtos
	 * @param listOfColumns
	 * @param vLayout
	 */
	
	public GenricEntityPicker(final Collection<Dto> dtos,final Collection<String> listOfColumns,final VerticalLayout vLayout,final Window mainWindow,EntityListener entityListener) {
		this.listOfColumns = listOfColumns;
		this.dtos = dtos;
		this.vLayout = vLayout;
		this.mainwindow = mainWindow;
		this.entityListener = entityListener;
	}

	public void build() { 
	
		if(vLayout.getComponentCount()>0){
	        vLayout.removeAllComponents();
		}
		tableBuilder = new GenricEntityTableBuilder(dtos, listOfColumns, vLayout);
		tableBuilder.build();
        renderPopUp();
	}
	
	public void renderPopUp() {
	        /* Create a new window. */
			popupWindow = new Window();
			popupWindow.setPositionX(200);
	    	popupWindow.setPositionY(100);
	    	popupWindow.setHeight(40,Sizeable.UNITS_PERCENTAGE);
	    	popupWindow.setWidth(37,Sizeable.UNITS_PERCENTAGE);
	       
	    	/* Add the window inside the main window. */
	        mainwindow.addWindow(popupWindow);
	        
	        /* Listen for close events for the window. */
	        popupWindow.addListener(this);
	        popupWindow.setModal(true);
	        if(entityListener.getCaption() !=  null)
	        popupWindow.setCaption(entityListener.getCaption());
	        popupMainLayout.setSpacing(true);
	        popupMainLayout.addComponent(vLayout);
	        popupWindow.addComponent(popupMainLayout);
	        popupWindow.setResizable(false);
	        
	    }

	/**
	 * Handle Close button click and close the window.
	 */
	public void closeButtonClick(Button.ClickEvent event) {
	
		if (!isModalWindowClosable) {
			/* Windows are managed by the application object. */
			mainwindow.removeWindow(popupWindow);
		}
	}

	/**
	 * Window close event
	 */
	@Override
	public void windowClose(CloseEvent e) {
		// TODO Auto-generated method stub
		entityListener.updateList();
	}

}
	
