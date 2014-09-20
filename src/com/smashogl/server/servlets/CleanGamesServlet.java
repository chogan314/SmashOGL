package com.smashogl.server.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.smashogl.persistence.daos.GameDAO;
import com.smashogl.persistence.daos.UserDAO;
import com.smashogl.persistence.entities.Game;
import com.smashogl.persistence.entities.User;
import com.smashogl.persistence.entities.Game.GameResult;
import com.smashogl.persistence.entities.Game.GameState;

@SuppressWarnings("serial")
public class CleanGamesServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		List<Game> games = GameDAO.getInstance().asList();
		
		for (Game game : games) {
			if ((game.getState().equals(GameState.STARTING) && game.age() > 30) ||
					(game.getState().equals(GameState.IN_PROGRESS) && game.age() > (60 * 60))) {
				killGame(game);
			}
		}
	}
	
	private static void killGame(Game game) {
		if (game.getState().equals(GameState.STARTING)) {
			// TODO: Send message to users
			GameDAO.getInstance().remove(game.getId());
		} else if (game.getState().equals(GameState.IN_PROGRESS)) {			
			User userA = UserDAO.getInstance().get(game.getUsernameA());
			User userB = UserDAO.getInstance().get(game.getUsernameB());
			
			switch(game.getUserAResult()) {
			case A_WIN:
				
				if (!game.getUserBResult().equals(GameResult.UNRESOLVED)) {
					// TODO: Handle error
				} else {
					// TODO: Calc rating changes
				}
				
				break;
			case B_WIN:
				
				if (!game.getUserBResult().equals(GameResult.UNRESOLVED)) {
					// TODO: Handle error
				} else {
					// TODO: Calc rating changes
				}
				
				break;
			case DRAW:
				
				if (!game.getUserBResult().equals(GameResult.UNRESOLVED)) {
					// TODO: Handle error
				} else {
					// TODO: Calc rating changes
				}
				
				break;
			case UNRESOLVED:
				
				switch(game.getUserBResult()) {
				case A_WIN:
					// TODO: Calc rating changes
					break;
				case B_WIN:
					// TODO: Calc rating changes
					break;
				case DRAW:
					// TODO: Calc rating changes
					break;
				case UNRESOLVED:
					// TODO: Calc rating changes
					break;
				default:
					break;				
				}
				
				break;
			default:
				break;			
			}				
				
			// TODO: Send message to users
			GameDAO.getInstance().remove(game.getId());
		}
	}
	
}
