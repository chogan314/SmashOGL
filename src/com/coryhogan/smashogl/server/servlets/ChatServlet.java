package com.coryhogan.smashogl.server.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coryhogan.smashogl.persistence.daos.ChatDAO;
import com.coryhogan.smashogl.persistence.entities.Chat;
import com.coryhogan.smashogl.server.util.ChannelUtils;
import com.coryhogan.smashogl.server.util.SessionUtils;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;

@SuppressWarnings("serial")
public class ChatServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String username = SessionUtils.getUsername(req.getSession());
		long chatId = Long.parseLong(req.getParameter("chatId"));
		
		ChannelService channelService = ChannelServiceFactory.getChannelService();
		String token = channelService.createChannel(username + ":" + chatId);
		
		resp.setContentType("text/plain");
		resp.getWriter().write(token);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String username = SessionUtils.getUsername(req.getSession());
		long chatId = Long.parseLong(req.getParameter("chatId"));
		
		Chat chat = ChatDAO.getInstance().get(chatId);
		
		if (!chat.hasUser(username)) {
			// TODO: handle error
			return;
		}
		
		String message = req.getParameter("message");
		
		for (String uName : chat.getUsernames()) {
			ChannelUtils.sendMessage(uName, message);
		}
	}
}
















