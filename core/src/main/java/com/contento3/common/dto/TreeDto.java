package com.contento3.common.dto;

import java.util.Collection;

public class TreeDto extends Dto {

	public TreeDto () {
		
	}
	
	public TreeDto(Integer id, String name) {
		super(id,name);	
	}

	public Collection<TreeDto> getChildDto(){
		return null;
	} 
}
