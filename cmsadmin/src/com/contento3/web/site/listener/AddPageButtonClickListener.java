package com.contento3.web.site.listener;

import java.util.ArrayList;

import com.contento3.cms.page.category.dto.CategoryDto;
import com.contento3.cms.page.dto.PageDto;
import com.contento3.cms.page.layout.service.PageLayoutService;
import com.contento3.cms.page.service.PageService;
import com.contento3.cms.site.structure.dto.SiteDto;
import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.web.helper.SpringContextHelper;
import com.contento3.web.site.PageUIManager;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class AddPageButtonClickListener implements ClickListener {

	private static final long serialVersionUID = 1L;

	private PageUIManager pageUIManager;
	//private TabSheet uiTabSheet;
	private Integer siteId;
	final SpringContextHelper contextHelper;
	private TextField titleTxt;
	private TextField uriTxt;

	private SiteDto siteDto;

	private ComboBox pageLayoutCombo;

	private PageLayoutService pageLayoutService;

	private Integer pageId;

	private PageDto newPageDtoWithLayout;

	private TabSheet pagesTab;

	private VerticalLayout newPageParentlayout;
	
	public AddPageButtonClickListener(SpringContextHelper contextHelper_ , TextField titleTxt_, TextField uriTxt_, SiteDto siteDto_, ComboBox pageLayoutCombo_, PageLayoutService pageLayoutService_, Integer pageId_, PageDto newPageDtoWithLayout_, TabSheet pagesTab_, VerticalLayout newPageParentlayout_, PageUIManager pageUIManager_) {
		this.contextHelper = contextHelper_;
		this.titleTxt = titleTxt_;
		this.uriTxt = uriTxt_;
		this.siteDto = siteDto_;	
		this.pageLayoutCombo = pageLayoutCombo_;
		this.pageLayoutService = pageLayoutService_;
		this.pageId = pageId_;
		this.newPageDtoWithLayout = newPageDtoWithLayout_;
		this.pagesTab = pagesTab_;
		this.newPageParentlayout = newPageParentlayout_;
		this.pageUIManager = pageUIManager_;
	}

	@Override
	public void click(ClickEvent event) {
		PageService pageService = (PageService) contextHelper
		.getBean("pageService");
		PageDto pageDto = new PageDto();
		pageDto.setTitle(titleTxt.getValue().toString());
		pageDto.setUri(uriTxt.getValue().toString());
		pageDto.setSite(siteDto);
		pageDto.setCategories(new ArrayList<CategoryDto>());
		////if(categories!=null){
		////	pageDto.setCategories(categories);
		////}else{
		////	pageDto.setCategories(null);
		//}
		if (null != pageLayoutCombo.getValue()) {
			pageDto.setPageLayoutDto(pageLayoutService
					.findPageLayoutById(Integer
							.parseInt(pageLayoutCombo.getValue()
									.toString())));
		}
		
		try{
		String notificationMsg = "Page %s %s successfullly";
		if (null!=pageId){
			pageDto.setPageId(pageId);
			pageService.update(pageDto);	
			notificationMsg = String.format(notificationMsg,pageDto.getTitle(),"updated");
		}
		else {
			// Create a new page,get page dto with its layout.
			newPageDtoWithLayout = pageService.createAndReturn(pageDto);
			pageUIManager.addPageToPageListTable(newPageDtoWithLayout, siteId, pagesTab,
					new Button());
		
			// Render the page layout by splitting them with page sections
			// and add them to the parent layout i.e. VerticalLayout
			newPageParentlayout.addComponent(pageUIManager.renderPageLayouts(newPageDtoWithLayout));
			notificationMsg = String.format(
					"Page %s added successfully",
					newPageDtoWithLayout.getTitle());
		}
		
		Notification.show(notificationMsg);
		}
		catch(EntityAlreadyFoundException e){
			Notification.show("Page already exists with this title or uri",Notification.Type.ERROR_MESSAGE);
		}

	}

}
