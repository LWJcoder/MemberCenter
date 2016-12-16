package com.cloudage.membercenter.entity;

import java.io.Serializable;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.annotations.Table;

import com.cloudage.membercenter.util.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class User extends BaseEntity implements Serializable{
	String account;
	String passwordHash;
	public String name;
	public String avatar;
	String email;

	@Column(unique=true)
	public String getAccount() {
		return account;
	}
	@Column(nullable = false)
	@JsonIgnore
	public String getPasswordHash() {
		return passwordHash;
	}
	public String getName() {
		return name;
	}
	public String getAvatar() {
		return avatar;
	}
	@Column(nullable = false)
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmail() {
		return email;
	}
	public void setAccount(String account2) {
		this.account = account2;
	}
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getCurrentUser(HttpServletRequest request){
		return getAccount();

	}

}
