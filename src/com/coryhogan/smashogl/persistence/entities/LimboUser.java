package com.coryhogan.smashogl.persistence.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class LimboUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	String username;
	String passwordHash;
	String emailAddress;
	Date creationDate;
	
	public LimboUser(String username, String passwordHash, String emailAddress) {
		this.username = username;
		this.passwordHash = passwordHash;
		this.emailAddress = emailAddress;
		creationDate = new Date();
	}
	
	public float age() {
		Date now = new Date();
		long time = now.getTime() - creationDate.getTime();
		float days = time / 1000 / 60 / 60 / 24;
		return days;
	}

	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public String getEmailAddress() {
		return emailAddress;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}
}
