package com.contento3.web.site.listener;

import java.util.ArrayList;
import java.util.Collection;

import com.contento3.cms.page.category.dto.CategoryDto;
import com.contento3.cms.page.category.service.CategoryService;
import com.contento3.cms.page.dto.PageDto;
import com.contento3.cms.page.exception.PageCannotCreateException;
import com.contento3.cms.page.exception.PageNotFoundException;
import com.contento3.cms.page.service.PageService;
import com.contento3.common.dto.Dto;
import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.web.common.helper.EntityListener;
import com.contento3.web.common.helper.GenricEntityPicker;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class PageAssignCategoryListener extends EntityListener implements ClickListener {

	private static final long serialVersionUID = 1L;

	private final VerticalLayout mainLayout;
	
	private Integer pageId;
	
	private Integer accountId;
	
	private SpringContextHelper helper;
	
	private CategoryService categoryService;
	
	private Window mainWindow;
	
	/**
	 * Constructor
	 * @param mainWindow
	 * @param helper
	 * @param articleId
	 * @param accountId
	 */
	public PageAssignCategoryListener(final SpringContextHelper helper,final Integer pageId,final Integer accountId){
		this.accountId = accountId;
		this.pageId = pageId;
		this.helper = helper;
		categoryService = (CategoryService)helper.getBean("categoryService");
		mainLayout = new VerticalLayout();
	}
	
	/**
	 * Button Click Event
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void click(ClickEvent event) {
		//validation article exist
		if(pageId != null){
			Collection<String> listOfColumns = new ArrayList<String>();
			listOfColumns.add("Categories");
			GenricEntityPicker categoryPicker;
			Collection<Dto> dtos = null;
			dtos = (Collection) categoryService.findNullParentIdCategory(accountId);
			setCaption("Add Category");
			categoryPicker = new GenricEntityPicker(dtos,null,listOfColumns,mainLayout,this,true);
			categoryPicker.build();
		}else{
			//warning message
			Notification.show("Opening failed", "create page first", Notification.Type.WARNING_MESSAGE);
		}
	}
	
	/**
	 * Assign selected category to page
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void updateList() {
		/* update page */
		Collection<String> selectedItems =(Collection<String>) this.mainLayout.getData();
		if(selectedItems != null){
			PageService pageService = (PageService) helper.getBean("pageService");
			PageDto page=null;
			try {
				page = pageService.findById(pageId);
			} catch (PageNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			for(String name : selectedItems ){
				CategoryDto category = categoryService.findById(Integer.parseInt(name));
				// validation
				 boolean isAddable = true;
				 for(CategoryDto dto:page.getCategories()){
					 if(dto.getName().equals(category.getName()))
		     			 isAddable = false;
				 }//end inner for
				 if(isAddable){
					 page.getCategories().add(category);
		     	 }//end if
			}//end outer for
			
			try {
				pageService.update(page);
			} catch (EntityAlreadyFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (PageCannotCreateException e) {
				Notification.show(e.getMessage(), Notification.Type.ERROR_MESSAGE);
			}
		}
		
	}

}
