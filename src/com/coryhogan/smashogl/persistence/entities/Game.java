package com.coryhogan.smashogl.persistence.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Game {
	
	public enum GameState {
		STARTING, IN_PROGRESS, FINISHED
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String usernameA;
	private String usernameB;
	private long startTime;
	private GameState state;
	
	public Game(User userA, User userB) {
		usernameA = userA.getUsername();
		usernameB = userB.getUsername();
		startTime = System.currentTimeMillis();
		state = GameState.STARTING;
	}
	
	public Long getId() {
		return id;
	}

	public String getUsernameA() {
		return usernameA;
	}

	public String getUsernameB() {
		return usernameB;
	}

	public long getStartTime() {
		return startTime;
	}

	public GameState getState() {
		return state;
	}	
}
