package com.contento3.common.dto;

import java.util.HashMap;

import org.apache.commons.lang.builder.HashCodeBuilder;


public class Dto {

	private Integer id;
	
	private String name;
	
	private HashMap<String, Object> hashMap;
	
	public Dto(){
		
	}
	
	public Dto (Integer id,String name){
		this.id = id;
		this.name = name;
		this.hashMap = new HashMap<String, Object>();
	}
	
	public Integer getId(){
		return id;
	}
	
	public String getName(){
		return name;
	}
	

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
      return new HashCodeBuilder(23,31)
                  .append(this.getId())
                  .append(this.getName())
                  .toHashCode();
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
      boolean isEqual;
      
      if(obj == null) {
        isEqual = false;
      } else if(this == obj) {
    	  isEqual = true;
      } else if(obj instanceof Dto) {
    	  final Dto dto = (Dto)obj;
    	  if (dto.getId() == this.getId())
    	  isEqual = true;
    	  else 
    	  isEqual = false;	  
      } else {
        isEqual = false;
      }
      return isEqual;
    }

	public HashMap<String, Object> getHashMap() {
		return hashMap;
	}


	
}
