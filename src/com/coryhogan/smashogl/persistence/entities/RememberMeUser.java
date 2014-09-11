package com.coryhogan.smashogl.persistence.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;

@Entity
public class RememberMeUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;
	private Date creationDate;
	
	private String username;
	
	public RememberMeUser(String username) {
		this.username = username;
		creationDate = new Date();
	}
	
	public Key getKey() {
		return key;
	}
	
	public String getUsername() {
		return username;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}
}
