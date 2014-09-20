package com.smashogl.server.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
import com.smashogl.persistence.daos.ChatDAO;
import com.smashogl.persistence.daos.GameDAO;
import com.smashogl.persistence.daos.QueueEntryDAO;
import com.smashogl.persistence.daos.UserDAO;
import com.smashogl.persistence.entities.Chat;
import com.smashogl.persistence.entities.Game;
import com.smashogl.persistence.entities.QueueEntry;
import com.smashogl.persistence.entities.User;

@SuppressWarnings("serial")
public class MatchmakerServlet extends HttpServlet {
	
	private static final class Pair {
		private User userA;
		private User userB;
		
		public Pair(User userA, User userB) {
			this.userA = userA;
			this.userB = userB;
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		List<QueueEntry> entries = QueueEntryDAO.getInstance().asList();
		List<User> users = new ArrayList<User>();
		Map<String, QueueEntry> usernameToEntry = new HashMap<String, QueueEntry>();
		
		for (QueueEntry entry : entries) {
			users.add(UserDAO.getInstance().get(entry.getUsername()));
			usernameToEntry.put(entry.getUsername(), entry);
		}
		
		List<Pair> pairs = makePairs(entries, users, usernameToEntry);
		
		for (Pair pair : pairs) {
			User userA = pair.userA;
			User userB = pair.userB;
			QueueEntry entryA = usernameToEntry.get(pair.userA.getUsername());
			QueueEntry entryB = usernameToEntry.get(pair.userB.getUsername());
			
			QueueEntryDAO.getInstance().remove(entryA.getId());
			QueueEntryDAO.getInstance().remove(entryB.getId());
			
			Game game = new Game(userA, userB);
			GameDAO.getInstance().add(game);
			
			Chat chat = new Chat();
			chat.getUsernames().add(userA.getUsername());
			chat.getUsernames().add(userB.getUsername());
			ChatDAO.getInstance().add(chat);
			
			String channelAKey = userA.getUsername() + ":" + entryA.getId();
			String channelBKey = userB.getUsername() + ":" + entryB.getId();
			
			String message = "game=" + game.getId().toString() + 
					":chat=" + chat.getId().toString();
			
			sendUpdateToUser(channelAKey, message);
			sendUpdateToUser(channelBKey, message);
		}
	}
	
	private static List<Pair> makePairs(List<QueueEntry> entries, List<User> users, 
			Map<String, QueueEntry> usernameToEntry) {
		
		List<Pair> pairs = new ArrayList<Pair>();		
		Iterator<User> userIter = users.iterator();	
		
		while (userIter.hasNext()) {
			User userA = userIter.next();			
			if (!userIter.hasNext()) {
				break;
			}			
			User userB = userIter.next();			
			pairs.add(new Pair(userA, userB));
		}		
		return pairs;
	}
	
	private void sendUpdateToUser(String key, String message) {
		ChannelService channelService = ChannelServiceFactory.getChannelService();
		channelService.sendMessage(new ChannelMessage(key, message));
	}
	
}
