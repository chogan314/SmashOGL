package com.smashogl.persistence.daos;

import com.smashogl.persistence.entities.User;

public final class UserDAO extends DAO<User, String> {
	private static UserDAO instance;
	
	private UserDAO() {
		super(User.class);
	}
	
	public static UserDAO getInstance() {
		if (instance == null) {
			instance = new UserDAO();
		}
		return instance;
	}
	
	public void add(String username, String passwordHash, String emailAddress) {
		User user = new User(username, passwordHash, emailAddress);
		add(user);
	}
}
