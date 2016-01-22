package com.crossover.article.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Article {
	private String id;
	private String file;
	private String description;
	  
	public Article(){
	    
	}
	
	public Article (String id, String file){
	  this.id = id;
	  this.file = file;
	}
	
	public String getId() {
	  return id;
	}
	
	public void setId(String id) {
	  this.id = id;
	}
	
	public String getFile() {
	  return file;
	}
	
	public void setFile(String file) {
	  this.file = file;
	}
	
	public String getDescription() {
	  return description;
	}
	
	public void setDescription(String description) {
	  this.description = description;
	}
  
} 
