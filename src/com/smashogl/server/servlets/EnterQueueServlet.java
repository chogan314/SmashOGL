package com.smashogl.server.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
import com.smashogl.persistence.daos.QueueEntryDAO;
import com.smashogl.persistence.entities.QueueEntry;
import com.smashogl.server.util.SessionUtils;

@SuppressWarnings("serial")
public class EnterQueueServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String username = SessionUtils.getUsername(req.getSession());
		QueueEntry entry = new QueueEntry(username);
		QueueEntryDAO.getInstance().add(entry);
		String entryId = Long.toString(entry.getId());
		
		ChannelService channelService = ChannelServiceFactory.getChannelService();
		String token = channelService.createChannel(username + ':' + entryId);
		
		resp.setContentType("text/plain");
		resp.getWriter().write(token);
	}

}
