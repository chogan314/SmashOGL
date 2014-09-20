package com.smashogl.server.servlets;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.smashogl.persistence.daos.LimboUserDAO;
import com.smashogl.persistence.daos.UserDAO;
import com.smashogl.persistence.entities.LimboUser;
import com.smashogl.persistence.utils.PasswordHash;
import com.smashogl.server.util.SessionUtils;

public class CreateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String username = req.getParameter("username").trim();
		String password = req.getParameter("password");
		String emailAddress = req.getParameter("emailAddress").trim();

		String errorReport = "";

		List<LimboUser> limboUsers = LimboUserDAO.getInstance().asList();
		for (LimboUser lUser : limboUsers) {
			if (lUser.getUsername().equals(username)) {
				errorReport += ":usernameLimbo";
				break;
			}
		}

		if (UserDAO.getInstance().get(username) != null) {
			errorReport += ":usernameTaken";
		}

		if (password.length() < 8) {
			errorReport += ":passwordShort";
		}

		Pattern regex = Pattern.compile("^\\S+@\\S+\\.\\S+$");

		if (!regex.matcher(emailAddress).matches()) {
			errorReport += ":emailInvalid";
		}

		if (!errorReport.equalsIgnoreCase("")) {
			resp.setContentType("text/plain");
			resp.getWriter().write(errorReport);
			return;
		}

		try {
			String passwordHash = PasswordHash.createHash(password);
			LimboUser limboUser = new LimboUser(username, passwordHash,
					emailAddress);
			LimboUserDAO.getInstance().add(limboUser);
			
			Properties props = new Properties();
	        Session session = Session.getDefaultInstance(props, null);
	        
	        String msgBody = "Welcome to SmashOGL, " + username + "! Click the link below to activate "
	        		+ "your account:\n" + req.getServerName() + "/createUser?id=" + limboUser.getId();

	        String sender = "donotreply@smash-ogl.appspotmail.com";
	        
			try {
				Message msg = new MimeMessage(session);
				msg.setFrom(new InternetAddress(sender, "SmashOGL"));
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(emailAddress, username));
				msg.setSubject("SmashOGL Account Email Confirmation");
				msg.setText(msgBody);
				Transport.send(msg);
				
				resp.setContentType("text/plain");
				resp.getWriter().write(":success");

			} catch (AddressException e) {
				// TODO: Handle error
			} catch (MessagingException e) {
				// TODO: Handle error
			}

		} catch (NoSuchAlgorithmException e) {
			// TODO: Handle error
		} catch (InvalidKeySpecException e) {
			// TODO: Handle error
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		long id = Long.parseLong(req.getParameter("id"));
		LimboUser limboUser = LimboUserDAO.getInstance().get(id);

		if (limboUser == null) {
			// TODO: Handle error
			return;
		}

		String username = limboUser.getUsername();
		UserDAO.getInstance().add(username, limboUser.getPasswordHash(),
				limboUser.getEmailAddress());
		HttpSession session = req.getSession(true);
		SessionUtils.hardLogin(session, username);

		LimboUserDAO.getInstance().remove(limboUser.getId());

		resp.sendRedirect("/index.jsp");
	}
}
