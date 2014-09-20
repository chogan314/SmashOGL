package com.smashogl.persistence.daos;

import com.smashogl.persistence.entities.Game;

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
