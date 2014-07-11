package com.contento3.web.template.helper;

import java.util.Collection;

import com.contento3.cms.page.template.dto.TemplateDirectoryDto;
import com.contento3.cms.page.template.dto.TemplateDto;
import com.contento3.cms.page.template.service.TemplateDirectoryService;
import com.contento3.cms.page.template.service.TemplateService;
import com.contento3.web.common.helper.SessionHelper;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.data.Item;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Resource;
import com.vaadin.ui.Tree;

public class TemplateListingHelper {

	/**
	 * Represents an id of the selected tempalte from the root
	 */
	private int selectedId =0;
	
	private Boolean isDirectorySelected;
	
	private SpringContextHelper helper;
	
	private TemplateService templateService;
	
	private TemplateDirectoryService templateDirectoryService;
	
	public TemplateListingHelper(final SpringContextHelper helper){
		this.helper = helper;
		templateService = (TemplateService) helper.getBean("templateService");
		templateDirectoryService = (TemplateDirectoryService) helper.getBean("templateDirectoryService");		
	}
	
	public Tree populateTemplateList(final Collection<TemplateDirectoryDto> directoryDtos,final TemplateService templateService,final TemplateDirectoryService templateDirectoryService){
	    final Tree root;

		final HierarchicalContainer templateContainer = new HierarchicalContainer();
        root = new Tree("",templateContainer);
        root.setImmediate(true);
    	root.setItemCaptionPropertyId("name");

        templateContainer.addContainerProperty("id", Integer.class, null);
        templateContainer.addContainerProperty("fileid", String.class, null);
        templateContainer.addContainerProperty("name", String.class, null);
        templateContainer.addContainerProperty("icon", Resource.class, null);
        
        Item item;
        for (TemplateDirectoryDto directoryDto : directoryDtos){
        	item = templateContainer.addItem(directoryDto.getId());
        	item.getItemProperty("id").setValue(directoryDto.getId());
        	item.getItemProperty("name").setValue(directoryDto.getDirectoryName());
        	templateContainer.setChildrenAllowed(directoryDto.getId(), true);
			item.getItemProperty("icon").setValue(
					new ExternalResource("images/add-template-16.png"));
        }

        root.expandItem(new Integer (1));
        root.addItemClickListener(new ItemClickListener() {
			private static final long serialVersionUID = -4607219466099528006L;
        	public void itemClick(ItemClickEvent event) {
        		
        		root.expandItem(event.getItemId());
        		String itemId = event.getItemId().toString();
        		//Check if the itemId is for a directory
        		if (!itemId.startsWith("file:")){
        			Item parentItem = event.getItem();
        			addChildrenToSelectedDirectory(parentItem,templateContainer,true);
        		}
        		else {
        			selectedId = Integer.parseInt(itemId.split(":")[1]);
        		}
        	}	
        });
        return root;
	}

	public Tree populateTemplateDirectoryList(final Collection<TemplateDirectoryDto> directoryDtos,final TemplateDirectoryService templateDirectoryService,final Boolean templatesAllowed){
	    final Tree root;

		final HierarchicalContainer templateContainer = new HierarchicalContainer();
        root = new Tree("",templateContainer);
        root.setImmediate(true);
    	root.setItemCaptionPropertyId("name");

        templateContainer.addContainerProperty("id", Integer.class, null);
        templateContainer.addContainerProperty("fileid", String.class, null);
        templateContainer.addContainerProperty("name", String.class, null);
        templateContainer.addContainerProperty("icon", Resource.class, null);

		Item item;
		root.setItemIconPropertyId("icon");

        for (TemplateDirectoryDto directoryDto : directoryDtos){
        	item = templateContainer.addItem(directoryDto.getId());
        	item.getItemProperty("id").setValue(directoryDto.getId());
        	item.getItemProperty("name").setValue(directoryDto.getDirectoryName());
        	templateContainer.setChildrenAllowed(directoryDto.getId(), true);
			item.getItemProperty("icon").setValue(
					new ExternalResource("images/directory.png"));
        }

        root.expandItem(new Integer (1));
        root.addItemClickListener(new ItemClickListener() {
			private static final long serialVersionUID = -4607219466099528006L;
        	public void itemClick(ItemClickEvent event) {
        		String itemId = event.getItemId().toString();
        		if (!itemId.startsWith("file:")){
	        		root.expandItem(event.getItemId());
	    			Item parentItem = event.getItem();
	    			addChildrenToSelectedDirectory(parentItem,templateContainer,templatesAllowed);
	    			selectedId = (Integer)event.getItemId();
	    			isDirectorySelected = true;
        		}
        		else {
        			selectedId = Integer.parseInt(itemId.split(":")[1]);
        			isDirectorySelected = false;
        		}
        		
        	}	
        });
        return root;
	}

	public Integer getSelectedItemId() {
		return selectedId;
	}
	
	public Boolean isDirectorySelected() {
		return isDirectorySelected;
	}
	
	
	private void addChildrenToSelectedDirectory(final Item parentItem,final HierarchicalContainer templateContainer,final Boolean templatesAllowed){
		final Integer itemId = Integer.parseInt(parentItem.getItemProperty("id").getValue().toString());
		final String name = parentItem.getItemProperty("name").getValue().toString();

		final Collection <TemplateDirectoryDto> templateDirectoryDtoList = templateDirectoryService.findChildDirectories(itemId,(Integer)SessionHelper.loadAttribute("accountId"));

		for (TemplateDirectoryDto templateDirectoryDto: templateDirectoryDtoList){
				Integer itemToAdd = templateDirectoryDto.getId();
				if (null==templateContainer.getItem(itemToAdd)) {
					Item item = templateContainer.addItem(itemToAdd);
					item.getItemProperty("id").setValue(itemToAdd);
					item.getItemProperty("name").setValue(templateDirectoryDto.getDirectoryName());
					templateContainer.setParent(templateDirectoryDto.getId(), itemId);
					templateContainer.setChildrenAllowed(templateDirectoryDto.getId(), true);
					item.getItemProperty("icon").setValue(
							new ExternalResource("images/directory.png"));
				}
			}
		
		if (templatesAllowed){
			final Collection <TemplateDto> templateDtoList = templateService.findTemplateByDirectoryId(itemId);
		
			for (TemplateDto templateDto: templateDtoList){
				String templateItemId = String.format("file:%d",templateDto.getTemplateId());
				if (null==templateContainer.getItem(templateItemId)){
					Item item = templateContainer.addItem(templateItemId);
					item.getItemProperty("fileid").setValue(templateItemId);
					item.getItemProperty("name").setValue(templateDto.getTemplateName());
					templateContainer.setParent(String.format("file:%d",templateDto.getTemplateId()), itemId);
					templateContainer.setChildrenAllowed(String.format("file:%d",templateDto.getTemplateId()), true);
					item.getItemProperty("icon").setValue(
							new ExternalResource("images/add-template-16.png"));
				}
			}
		}
	}	

}
