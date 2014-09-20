package com.smashogl.persistence.daos;

import com.smashogl.persistence.entities.LimboUser;

public final class LimboUserDAO extends DAO<LimboUser, Long> {
	private static LimboUserDAO instance;
	
	private LimboUserDAO() {
		super(LimboUser.class);
	}
	
	public static LimboUserDAO getInstance() {
		if (instance == null) {
			instance = new LimboUserDAO();
		}
		return instance;
	}
}
