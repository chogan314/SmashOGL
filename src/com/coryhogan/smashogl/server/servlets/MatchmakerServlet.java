package com.coryhogan.smashogl.server.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coryhogan.smashogl.persistence.daos.QueueEntryDAO;
import com.coryhogan.smashogl.persistence.daos.UserDAO;
import com.coryhogan.smashogl.persistence.entities.QueueEntry;
import com.coryhogan.smashogl.persistence.entities.User;

@SuppressWarnings("serial")
public class MatchmakerServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		List<QueueEntry> entries = QueueEntryDAO.getInstance().asList();
		List<User> users = new ArrayList<User>();
		Map<String, Long> timeInQueue = new HashMap<String, Long>();
		
		for (QueueEntry entry : entries) {
			users.add(UserDAO.getInstance().get(entry.getUsername()));
			timeInQueue.put(entry.getUsername(), entry.getJoinTime());
		}
		
		
	}
	
}
