package com.cloudage.membercenter.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.junit.Ignore;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Article extends DateRecord {

	String title;
	String text;
	
	User author;
	
	@ManyToOne(optional=false)
	public User getAuthor(){
		return author;
		
	}
	public void setAuthor(User author) {
		this.author = author;
	}
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Transient
	public String getAuthorName(){
		return author.name;
	}

	@Transient
	public String getAvatar(){
		return author.avatar;
	}
}
