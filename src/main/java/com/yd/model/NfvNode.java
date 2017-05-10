package com.yd.model;

public class NfvNode {
	private String name;
	private int category;
	private String value;
	private String url;
	public NfvNode(String name,int category,String value,String url){
		this.name=name;
		this.category=category;
		this.value=value;
		this.url=url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String toString(){
		return "name:"+name+"category:"+category+"value:"+value+"url:"+url;
	}
}
