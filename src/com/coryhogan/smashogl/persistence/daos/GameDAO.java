package com.coryhogan.smashogl.persistence.daos;

import com.coryhogan.smashogl.persistence.entities.Game;

public final class GameDAO extends DAO<Game, Long> {
	private static GameDAO instance;
	
	private GameDAO() {
		super(Game.class);
	}
	
	public static GameDAO getInstance() {
		if (instance == null) {
			instance = new GameDAO();
		}
		return instance;
	}
}
