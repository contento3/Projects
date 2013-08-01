package com.contento3.web.site;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;

import com.contento3.cms.article.dto.ArticleDto;
import com.contento3.cms.article.service.ArticleService;
import com.contento3.cms.page.service.impl.PageServiceImpl;
import com.contento3.cms.site.structure.dto.SiteDto;
import com.contento3.cms.site.structure.service.SiteService;
import com.contento3.common.dto.Dto;
import com.contento3.web.common.helper.EntityListener;
import com.contento3.web.common.helper.GenricEntityPicker;
import com.contento3.web.common.helper.HorizontalRuler;
import com.contento3.web.common.helper.ScreenHeader;
import com.contento3.web.common.helper.SessionHelper;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.Notification;

public class SiteContentAssignmentUIManager extends EntityListener  implements ClickListener{

	private static final Logger LOGGER = Logger.getLogger(SiteContentAssignmentUIManager.class);

	private final TabSheet tabSheet;

	/**
	 * Helper use to load spring beans
	 */
	private final SpringContextHelper contextHelper;

	/**
	 * Parent window that contains this ui
	 */
	private final Window parentWindow;

	/**
	 * main layout for content assignment screen
	 */
	private VerticalLayout verticalLayout = new VerticalLayout();

	private SiteService siteService;
	
	private ArticleService articleService;

	private SiteDto siteDto = null;
	
	private Collection<Dto> assignedDtos;
	
	public SiteContentAssignmentUIManager(TabSheet uiTabSheet,
			SpringContextHelper contextHelper, Window parentWindow) {
		tabSheet = uiTabSheet;
		this.contextHelper = contextHelper;
		this.parentWindow = parentWindow;
		this.siteService = (SiteService)contextHelper.getBean("siteService");
		this.articleService = (ArticleService)contextHelper.getBean("articleService");
	}

	public Component render(final Integer siteId){
		siteDto = siteService.findSiteById(siteId);
		final ScreenHeader screenHeader = new ScreenHeader(verticalLayout,"Assign Content to Site : "+siteDto.getSiteName());
		verticalLayout.setSpacing(true);
		verticalLayout.setMargin(true);

		Tab tab = tabSheet.addTab(verticalLayout);
		tab.setCaption("Site Content Assigner");
		tab.setClosable(true);
		tabSheet.setSelectedTab(verticalLayout);

		//Pop-up that adds a new domain
		final FormLayout formLayout = new FormLayout();
		final Collection<String> contentTypeValue = new ArrayList<String>();
		contentTypeValue.add("Article");
		contentTypeValue.add("Image");
		contentTypeValue.add("Video");
		contentTypeValue.add("Document");
		
		final ComboBox contentTypeComboBox = new ComboBox("Content Type",contentTypeValue);
		contentTypeComboBox.setImmediate(true);
		formLayout.addComponent(contentTypeComboBox);
		
		final Button button = new Button("Assign Content");
		formLayout.addComponent(button);
		button.addListener(this);
		verticalLayout.addComponent(formLayout);
		verticalLayout.addComponent(new HorizontalRuler());
		return tabSheet;
	}

	@Override
	public void buttonClick(ClickEvent event) {
		Collection<String> listOfColumns = new ArrayList<String>();
		listOfColumns.add("Articles");
		GenricEntityPicker contentPicker;
		Collection<Dto> dtos = populateGenericDtoFromArticleDto(articleService.findByAccountId((Integer)SessionHelper.loadAttribute(parentWindow, "accountId")));
		assignedDtos = populateGenericDtoFromArticleDto(articleService.findLatestArticleBySiteId(siteDto.getSiteId(),null));
		contentPicker = new GenricEntityPicker(dtos,assignedDtos,listOfColumns,verticalLayout,parentWindow,this,false);
		contentPicker.setCaption("Assign Content to Site");
		contentPicker.build();
	}
	
	public void updateList(){
		Collection<String> selectedItems =(Collection<String>) this.verticalLayout.getData();
		if(selectedItems != null){
			ArticleDto articleDto = null;
			try {
				for(String name : selectedItems ){
					Integer articleId = Integer.parseInt(name);
					articleDto = articleService.findArticleByIdAndSiteId(articleId,siteDto.getSiteId());
					if (articleDto==null){
						articleDto = articleService.findById(articleId);
						articleDto.getSite().add(siteDto);
						articleService.update(articleDto);
					}
					else if (assignedDtos.contains(((Dto)articleDto))){
						assignedDtos.remove(((Dto)articleDto));
					}
				}//end outer for
				removeAssignedContentDtos(assignedDtos);
				parentWindow.showNotification("Articles saved successfully for site" + siteDto.getSiteName(), Notification.TYPE_TRAY_NOTIFICATION);
			}
			catch (Exception e) {
				LOGGER.error("Unable to assign article to site" + siteDto.getSiteName());
				parentWindow.showNotification("Unable to associate article with head" + articleDto .getHead()+ " for site" + siteDto.getSiteName(), Notification.TYPE_ERROR_MESSAGE);
			}
		}//end if
	}

	private void removeAssignedContentDtos(Collection<Dto> dtos){
		for(Dto dto:dtos ){
			ArticleDto articleDto = articleService.findById(dto.getId());
			articleDto.getSite().clear();
			articleService.update(articleDto);
		}
	}
	
	private Collection<Dto> populateGenericDtoFromArticleDto(final Collection <ArticleDto> articleDtos){
		Collection <Dto> dtos = new ArrayList<Dto>();
		for (ArticleDto articleDto : articleDtos){
			Dto dto = new Dto(articleDto.getId(),articleDto.getHead());
			dtos.add(dto);
		}
		return dtos;
	}
	
}
