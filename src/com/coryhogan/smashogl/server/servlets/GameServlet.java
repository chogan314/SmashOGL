package com.coryhogan.smashogl.server.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coryhogan.smashogl.persistence.daos.GameDAO;
import com.coryhogan.smashogl.persistence.entities.Game;
import com.coryhogan.smashogl.persistence.entities.Game.GameState;
import com.coryhogan.smashogl.server.util.ChannelUtils;
import com.coryhogan.smashogl.server.util.SessionUtils;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;

@SuppressWarnings("serial")
public class GameServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String username = SessionUtils.getUsername(req.getSession());
		long gameId = Long.parseLong(req.getParameter("gameId"));
		
		ChannelService channelService = ChannelServiceFactory.getChannelService();
		String token = channelService.createChannel(username + ":" + gameId);
		
		resp.setContentType("text/plain");
		resp.getWriter().write(token);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String username = SessionUtils.getUsername(req.getSession());
		long gameId = Long.parseLong(req.getParameter("gameId"));
		String gameState = req.getParameter("gameState");
		String action = req.getParameter("action");
		
		Game game = GameDAO.getInstance().get(gameId);
		
		if (game == null) {
			// TODO: Handle error
		}
		
		if (!game.getState().equals(GameState.valueOf(gameState))) {
			// TODO: Handle error
		}
		
		switch (GameState.valueOf(gameState)) {
		case STARTING:
			
			if (action.equalsIgnoreCase("start")) {
				boolean bothReady = game.ready(username);
				
				if (bothReady) {
					game.setState(GameState.IN_PROGRESS);
					String message = "gameState=" + game.getState();
					ChannelUtils.sendMessage(game.getUsernameA() + ":" + gameId, message);
					ChannelUtils.sendMessage(game.getUsernameB() + ":" + gameId, message);
				}
				
			}
			
			break;
		case IN_PROGRESS:
			
			if (action.equalsIgnoreCase("win")) {
				game.win(username);
			} else if (action.equalsIgnoreCase("loss")) {
				game.lose(username);
			} else if (action.equalsIgnoreCase("draw")) {
				game.draw(username);
			}
			
			if (game.resultsIn()) {
				game.setState(GameState.RESULTS);
				
				// TODO: Handle rating changes
				
				String message = "gameState=" + game.getState();
				ChannelUtils.sendMessage(game.getUsernameA() + ":" + gameId, message);
				ChannelUtils.sendMessage(game.getUsernameB() + ":" + gameId, message);
				
				GameDAO.getInstance().remove(game.getId());
			}
			
			break;
		case FINISHED:
			break;
		case RESULTS:			
			break;
		default:
			break;			
		}		
	}
	
}
