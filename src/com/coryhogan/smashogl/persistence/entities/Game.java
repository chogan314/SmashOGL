package com.coryhogan.smashogl.persistence.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Game {
	
	public enum GameState {
		STARTING, IN_PROGRESS, FINISHED, RESULTS
	}
	
	public enum GameResult {
		UNRESOLVED, A_WIN, B_WIN, DRAW
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String usernameA;
	private String usernameB;
	private long startTime;
	private GameState state;
	
	private boolean userAReady;
	private boolean userBReady;
	
	private GameResult userAResult;
	private GameResult userBResult;
	
	public Game(User userA, User userB) {
		usernameA = userA.getUsername();
		usernameB = userB.getUsername();
		startTime = System.currentTimeMillis();
		state = GameState.STARTING;
		userAReady = false;
		userBReady = false;
		userAResult = GameResult.UNRESOLVED;
		userBResult = GameResult.UNRESOLVED;
	}
	
	public boolean ready(String username) {
		if (username.equals(usernameA)) {
			userAReady = true;
		} else if (username.equals(usernameB)) {
			userBReady = true;
		} else {
			// TODO: Handle error
		}
		
		return userAReady && userBReady;
	}
	
	public boolean win(String username) {
		if (username.equals(usernameA)) {
			userAResult = GameResult.A_WIN;
		} else if (username.equals(usernameB)) {
			userBResult = GameResult.B_WIN;
		} else {
			// TODO: Handle error
		}
		
		return resultsIn();
	}
	
	public boolean lose(String username) {
		if (username.equals(usernameA)) {
			userAResult = GameResult.B_WIN;
		} else if (username.equals(usernameB)) {
			userBResult = GameResult.A_WIN;
		} else {
			// TODO: Handle error
		}
		
		return resultsIn();
	}
	
	public boolean draw(String username) {
		if (username.equals(usernameA)) {
			userAResult = GameResult.DRAW;
		} else if (username.equals(usernameB)) {
			userBResult = GameResult.DRAW;
		} else {
			// TODO: Handle error
		}
		
		return resultsIn();
	}
	
	public boolean resultsIn() {
		return !userAResult.equals(GameResult.UNRESOLVED) && !userBResult.equals(GameResult.UNRESOLVED);
	}
	
	public long age() {
		return (System.currentTimeMillis() - startTime) / 1000;
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
	
	public void setState(GameState state) {
		this.state = state;
	}

	public boolean isUserAReady() {
		return userAReady;
	}

	public boolean isUserBReady() {
		return userBReady;
	}
	
	public GameResult getUserAResult() {
		return userAResult;
	}
	
	public GameResult getUserBResult() {
		return userBResult;
	}
}
