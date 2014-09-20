package com.smashogl.persistence.daos;

import com.smashogl.persistence.entities.Chat;

public final class ChatDAO extends DAO<Chat, Long> {
	private static ChatDAO instance;
	
	private ChatDAO() {
		super(Chat.class);
	}
	
	public static ChatDAO getInstance() {
		if (instance == null) {
			instance = new ChatDAO();
		}
		
		return instance;
	}
}
