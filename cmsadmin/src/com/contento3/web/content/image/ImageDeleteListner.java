package com.contento3.web.content.image;

import java.util.Collection;
import org.apache.shiro.authz.AuthorizationException;
import org.vaadin.dialogs.ConfirmDialog;
import com.contento3.cms.article.dto.ArticleImageDto;
import com.contento3.cms.article.service.ArticleImageService;
import com.contento3.common.exception.EntityCannotBeDeletedException;
import com.contento3.dam.image.dto.ImageDto;
import com.contento3.dam.image.service.ImageService;
import com.contento3.web.content.image.listener.DeleteListener;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

public class ImageDeleteListner implements ClickListener {

	
	private static final long serialVersionUID = 1L;

	/**
	 * ImageService for image related operations
	 */
	private ImageService imageService;
	  
	/**
	 * ArticleService for article related operations
	 */
    private ArticleImageService articleImageService;
    
	/**
	 * image to be edit
	 */
	private ImageDto imageDto;
	
	/**
	 * Delete Listener
	 */
	private DeleteListener deleteListener;
	
	
	public ImageDeleteListner(final SpringContextHelper helper,final ImageDto imageDto, final DeleteListener listener) {
		
		this.imageService = (ImageService)helper.getBean("imageService");
		this.articleImageService = (ArticleImageService) helper.getBean("articleImageService");
		this.imageDto = imageDto;
		this.deleteListener = listener;
	}

	
	@Override
	public void buttonClick(ClickEvent event) {
		
		ConfirmDialog.show(UI.getCurrent(), "Please Confirm"," Are you really sure to delete?",
		        "Yes", "Cancel", new ConfirmDialog.Listener() {

			private static final long serialVersionUID = 1L;
		

			public void onClose(ConfirmDialog dialog) {
                if (dialog.isConfirmed()) {
                    // Confirmed to continue
                	try
                	{
                		Collection<ArticleImageDto> dtos = articleImageService.findArticleImageByImageId(imageDto.getId());
                		
                		if(dtos != null && dtos.size() > 0) {
                			Notification.show("Image", imageDto.getName() +" cannot be deleted.", Notification.Type.TRAY_NOTIFICATION);
                		} else {
                			imageService.delete(imageDto);
                			Notification.show("Image", imageDto.getName() +" deleted.", Notification.Type.TRAY_NOTIFICATION);
                			deleteListener.onDelete();
                		}	
                	}catch(AuthorizationException ex){
                		Notification.show("You are not permitted to delete articles");
                	} catch (EntityCannotBeDeletedException e) {
                		Notification.show("Image", imageDto.getName() +" cannot be deleted.", Notification.Type.TRAY_NOTIFICATION);
					}
                } else {
                    // User did not confirm
                    
                }
            }
        });
    	
		
	}

}
