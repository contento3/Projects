package com.contento3.web.site.listener;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.AuthorizationException;

import com.contento3.cms.page.category.dto.CategoryDto;
import com.contento3.cms.page.category.service.CategoryService;
import com.contento3.cms.page.dto.PageDto;
import com.contento3.cms.page.exception.PageCannotCreateException;
import com.contento3.cms.page.service.PageService;
import com.contento3.common.dto.Dto;
import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.common.exception.EntityNotFoundException;
import com.contento3.web.common.helper.EntityListener;
import com.contento3.web.common.helper.GenricEntityPicker;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class PageAssignCategoryListener extends EntityListener implements ClickListener {

	private static final Logger LOGGER = Logger.getLogger(PageAssignCategoryListener.class);
	
	private static final long serialVersionUID = 1L;

	private final VerticalLayout mainLayout;
	
	private PageDto pageDto;
	
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
	public PageAssignCategoryListener(final SpringContextHelper helper, final PageDto pageDto, final Integer accountId){
		this.accountId = accountId;
		this.pageDto = pageDto;
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
		if(pageDto != null){
			Collection<String> listOfColumns = new ArrayList<String>();
			listOfColumns.add("Categories");
			GenricEntityPicker categoryPicker;
			Collection<Dto> dtos = null;
			Collection<Dto> assignedDtos = null;
			try {
				dtos = (Collection) categoryService.findNullParentIdCategory(accountId);
				assignedDtos = populateGenericDtoFromCategoryDto(pageDto.getCategories());
			} catch (final EntityNotFoundException e) {
				
			}
			 catch (final AuthorizationException ae) {
				 LOGGER.debug("Current user not allowed to assign category to page");
			}
			setCaption("Add Category");
			categoryPicker = new GenricEntityPicker(dtos, assignedDtos, listOfColumns, mainLayout, this, true);
			categoryPicker.build(null);
		}else{
			//warning message
			Notification.show("Opening failed", "create page first", Notification.Type.WARNING_MESSAGE);
		}
	}
	
	private Collection<Dto> populateGenericDtoFromCategoryDto(final Collection <CategoryDto> categoryDtos){
		Collection <Dto> dtos = new ArrayList<Dto>();
		for (CategoryDto caegoryDto : categoryDtos){
			Dto dto = new Dto(caegoryDto.getId(),caegoryDto.getName());
			dtos.add(dto);
		}
		return dtos;
	}
	
	/**
	 * Assign selected category to page
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void updateList() {

		Collection<String> selectedItems =(Collection<String>) this.mainLayout.getData();
		
		if(selectedItems != null) {
			PageService pageService = (PageService) helper.getBean("pageService");
			pageDto.getCategories().clear();
			
			try {
				
				for(String name : selectedItems ){
					
					CategoryDto category = categoryService.findById(Integer.parseInt(name));
					 pageDto.getCategories().add(category);
				}
				pageService.update(pageDto);
				Notification.show("Category assigned successfully.", Notification.Type.TRAY_NOTIFICATION);
			
			} catch (EntityAlreadyFoundException e) {
				e.printStackTrace();
			} catch (PageCannotCreateException e) {
				Notification.show(e.getMessage(), Notification.Type.ERROR_MESSAGE);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (EntityNotFoundException e) {
				LOGGER.debug(e.getMessage());
			}
		}
		
	}

}
