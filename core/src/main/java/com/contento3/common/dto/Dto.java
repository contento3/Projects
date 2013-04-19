package com.contento3.common.dto;


public class Dto {

	private Integer id;
	
	private String name;
	
	public Dto(){
		
	}
	
	public Dto (Integer id,String name){
		this.id = id;
		this.name = name;
	}
	
	public Integer getId(){
		return id;
	}
	
	public String getName(){
		return name;
	}
	
	
	
}
