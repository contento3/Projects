package com.olive.cms.page.layout;

import java.util.Collection;

import com.olive.cms.page.section.dto.PageSectionDto;


public interface LayoutBuilder {

	/**
	 * Used to add create html header in the html for the layout 
	 */
    public void createHtmlHeader();
    
    /**
     * Used to create header section
     */
    public void addHeader();
    
    /**
     * Used to create footer section
     */
    public void addFooter();
    
    /**
     * Used to remove footer section
     */
    public void removeFooter();

    /**
     * Used to remove header
     */
    public void removeHeader();

    /**
     * Used to add left area (mostly used for putting navigation area)
     */
    public void addLeftArea();

    /**
     * Used to remove left area (mostly used for putting navigation area)
     * 
     */
    public void removeLeftArea();

    /**
     * Used to add right area
     */
    public void addRightArea();
    
    /**
     * Used to remove right navigational area
     */
    public void removeRightArea();
    
    /**
     * Used to add new body row
     */
    public void addNewBodyRow(String bodyColumn);
    
    /**
     * Used to remove new body row
     */
    public void removeNewBodyRow();
    
    /**
     * Used to set the bodywidth
     * @return
     */
    public void addBodyWidth(String width);
    
    /**
     * 
     * @return
     */
    public String getLayoutHTML();
    
    /**
     * Used to return the PageLayoutDto
     */
    public Collection<PageSectionDto> convertHtmlToDto();
    
	public String getBodyWidthStyle() ;

	public void setBodyWidthStyle(String bodyWidthStyle) ;
	
	public String getHeaderHeight();

	public void setHeaderHeight(String headerHeight);

	public String getFooterHeight();

	public void setFooterHeight(String footerHeight);

}
