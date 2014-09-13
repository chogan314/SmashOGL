package com.coryhogan.smashogl.persistence.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class QueueEntry {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String username;
	private long joinTime;
	
	public QueueEntry(String username) {
		this.username = username;
		joinTime = System.currentTimeMillis();
	}

	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}
	
	public long getJoinTime() {
		return joinTime;
	}
}
