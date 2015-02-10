package com.contento3.web.modules.listener;

import com.contento3.cms.constant.NavigationConstant;
import com.contento3.web.Manager;
import com.contento3.web.UIManager;
import com.contento3.web.UIManagerCreator;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Tree;

public class SocialMediaItemClickListener implements ItemClickListener {

	private static final long serialVersionUID = 1L;

	final HorizontalSplitPanel treeHorizontalSplitPanel;
	
	final SpringContextHelper helper;
	
	final Tree root;
	
	public SocialMediaItemClickListener (final Tree root,final HorizontalSplitPanel treeHorizontalSplitPanel,final SpringContextHelper helper){
		this.treeHorizontalSplitPanel = treeHorizontalSplitPanel;
		this.helper = helper;
		this.root = root;
	}
	
	@Override
	public void itemClick(final ItemClickEvent event) {
		root.expandItem(event.getItemId());
        final String itemSelected = event.getItem().getItemProperty("name").getValue().toString();

        if (null!=itemSelected && itemSelected.equals(NavigationConstant.PUBLISH)){
        	final UIManager socialPublishUI = UIManagerCreator.createUIManager(Manager.SocialPublish,helper);
        	treeHorizontalSplitPanel.setSecondComponent(socialPublishUI.render(null));
        }
	}


}
