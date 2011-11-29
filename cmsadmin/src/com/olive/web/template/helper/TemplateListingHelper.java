package com.olive.web.template.helper;

import java.util.Collection;

import com.olive.cms.page.template.dto.TemplateDirectoryDto;
import com.olive.cms.page.template.dto.TemplateDto;
import com.olive.cms.page.template.service.TemplateDirectoryService;
import com.olive.cms.page.template.service.TemplateService;
import com.vaadin.data.Item;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Tree;

public class TemplateListingHelper {

	/**
	 * Represents an id of the selected tempalte from the root
	 */
	private int selectedId =0;
	
	public Tree populateTemplateList(final Collection<TemplateDirectoryDto> directoryDtos,final TemplateService templateService,final TemplateDirectoryService templateDirectoryService){
	    final Tree root;

		final HierarchicalContainer templateContainer = new HierarchicalContainer();
        root = new Tree("",templateContainer);
        root.setImmediate(true);
    	root.setItemCaptionPropertyId("name");

        templateContainer.addContainerProperty("id", Integer.class, null);
        templateContainer.addContainerProperty("fileid", String.class, null);
        templateContainer.addContainerProperty("name", String.class, null);
        
        Item item;
        for (TemplateDirectoryDto directoryDto : directoryDtos){
        	item = templateContainer.addItem(directoryDto.getId());
        	item.getItemProperty("id").setValue(directoryDto.getId());
        	item.getItemProperty("name").setValue(directoryDto.getDirectoryName());
        	templateContainer.setChildrenAllowed(directoryDto.getId(), true);
        }

        root.expandItem(new Integer (1));
        root.addListener(new ItemClickListener() {
			private static final long serialVersionUID = -4607219466099528006L;
        	public void itemClick(ItemClickEvent event) {
        		
        		root.expandItem(event.getItemId());
        		String itemId = event.getItemId().toString();
        		//Check if the itemId is for a directory
        		if (!itemId.startsWith("file:")){
        			Item parentItem = event.getItem();
        			addChildrenToSelectedDirectory(parentItem,templateService,templateDirectoryService,templateContainer);
        		}
        		else {
        			selectedId = Integer.parseInt(itemId.split(":")[1]);
        		}
        	}	
        });
        return root;
	}

	public Integer getSelectedItemId() {
		return selectedId;
	}
	
	
	private void addChildrenToSelectedDirectory(final Item parentItem,final TemplateService templateService,
			final TemplateDirectoryService templateDirectoryService,final HierarchicalContainer templateContainer){
		Integer itemId = Integer.parseInt(parentItem.getItemProperty("id").getValue().toString());
		String name = parentItem.getItemProperty("name").getValue().toString();

		Collection <TemplateDirectoryDto> templateDirectoryDtoList = templateDirectoryService.findChildDirectories(itemId);

		for (TemplateDirectoryDto templateDirectoryDto: templateDirectoryDtoList){
				Integer itemToAdd = templateDirectoryDto.getId();
				if (null==templateContainer.getItem(itemToAdd)) {
					Item item = templateContainer.addItem(itemToAdd);
					item.getItemProperty("id").setValue(itemToAdd);
					item.getItemProperty("name").setValue(templateDirectoryDto.getDirectoryName());
					templateContainer.setParent(templateDirectoryDto.getId(), itemId);
					templateContainer.setChildrenAllowed(templateDirectoryDto.getId(), true);
				}
			}
		
		Collection <TemplateDto> templateDtoList = templateService.findTemplateByDirectoryName(name);
		
			for (TemplateDto templateDto: templateDtoList){
				String templateItemId = String.format("file:%d",templateDto.getTemplateId());
				if (null==templateContainer.getItem(templateItemId)){
					Item item = templateContainer.addItem(templateItemId);
					item.getItemProperty("fileid").setValue(templateItemId);
					item.getItemProperty("name").setValue(templateDto.getTemplateName());
					templateContainer.setParent(String.format("file:%d",templateDto.getTemplateId()), itemId);
					templateContainer.setChildrenAllowed(String.format("file:%d",templateDto.getTemplateId()), true);
				}
			}
		}

}
