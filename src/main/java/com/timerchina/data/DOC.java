package com.timerchina.data;

public class DOC {
	String id;
	String title;
	String summary;
	String content;
	String feature;
	
	public DOC() {
		super();
	}
	public DOC(String id, String content, String title, String summary) {
		this.id = id;
		this.content = content;
		this.title = title;
		this.summary = summary;
	}
	public DOC(String id, String content, String feature) {
		this.id = id;
		this.content = content;
		this.feature = feature;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getFeature() {
		return feature;
	}
	public void setFeature(String feature) {
		this.feature = feature;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	@Override
	public String toString() {
		return "DOC [id=" + id + "]";
	}
	
}
