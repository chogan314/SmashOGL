package com.smashogl.persistence.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Chat {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private List<String> usernames;

	public Chat() {
		usernames = new ArrayList<String>();
	}
	
	public Long getId() {
		return id;
	}

	public List<String> getUsernames() {
		return usernames;
	}
	
	public boolean hasUser(String username) {
		for (String uName : usernames) {
			if (uName.equals(username)) {
				return true;
			}
		}
		
		return false;
	}
}
