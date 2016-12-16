package com.cloudage.membercenter.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Comment extends DateRecord{

	String text;
	
	Article article;
	User author;

	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	@ManyToOne(optional = false)
	public Article getArticle() {
		return article;
	}
	public void setArticle(Article acticle) {
		this.article = acticle;
	}
	
	@ManyToOne(optional = false)
	public User getAuthor() {
		return author;
	}
	public void setAuthor(User author) {
		this.author = author;
	}
}
