package com.smashogl.persistence.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class RememberMeUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Date creationDate;	
	private String username;
	
	public RememberMeUser(String username) {
		this.username = username;
		creationDate = new Date();
	}
	
	public Long getId() {
		return id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}
}
