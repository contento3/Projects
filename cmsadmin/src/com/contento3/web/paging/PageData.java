package com.contento3.web.paging;

public class PageData {

	//TODO get these values from the properties files
	private int fetchSize = 0;
	private int pageNumber = 0;
	
	public PageData(final int pageNumber,final int fetchSize){
	this.fetchSize = fetchSize;
	this.pageNumber = pageNumber;
	}
	
	public void setFecthSize(final int fetchSize){
		this.fetchSize = fetchSize;
	}
	
	public void setPageNumber(final int pageNumber){
		this.pageNumber = pageNumber;
	}
	
	public int getFecthSize(){
		return this.fetchSize;
	}
	
	public int getPageNumber(){
		return this.pageNumber;
	}
	

}
