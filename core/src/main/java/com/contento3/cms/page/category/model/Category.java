package com.contento3.cms.page.category.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;



@Entity
@Table(name = "Category")
public class Category {
	@Id @GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name = "Categoryid")
	private Integer id;
	
	@Column(name = "Category_Name")
	private String CategoryName;
	
@ManyToOne
@JoinColumn(name="Parent_Category")
private Category parent;

@OneToMany(mappedBy="Parent_Category")
private Collection<Category> child;

public Integer getid()
{
return id;
}
 public void setid(Integer id){
	 this.id=id;
 }
 public String getcategoryname()
 {
	 return CategoryName;
	  }
 
 public void setcategoryname(String CategoryName)
 {
	 this.CategoryName = CategoryName;
	 }
 public Category getparent()
 {
	 return parent;
 }
 public void setparent(Category parent)
 {
	 this.parent=parent;
 }
public Collection<Category> getchild()
{
	return child;
}
 public void setchild( Collection<Category> child)
 {
	 this.child=child;
 }
}
