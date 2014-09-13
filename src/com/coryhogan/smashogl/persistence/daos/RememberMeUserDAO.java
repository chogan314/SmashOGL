package com.coryhogan.smashogl.persistence.daos;

import javax.persistence.EntityManager;

import com.coryhogan.smashogl.persistence.entities.RememberMeUser;
import com.coryhogan.smashogl.persistence.entities.User;
import com.coryhogan.smashogl.persistence.utils.EMFService;

public final class RememberMeUserDAO extends DAO<RememberMeUser, Long> {
	private static RememberMeUserDAO instance;
	
	private RememberMeUserDAO() {
		super(RememberMeUser.class);
	}
	
	public static RememberMeUserDAO getInstance() {
		if (instance == null) {
			instance = new RememberMeUserDAO();
		}
		return instance;
	}
	
	public void add(String username) {
		RememberMeUser rmUser = new RememberMeUser(username);
		add(rmUser);
	}
	
	public User getUserFromCookieData(String data) {
		long id = Long.parseLong(data);
		EntityManager em = EMFService.get().createEntityManager();
		RememberMeUser rmUser = em.find(RememberMeUser.class, id);
		
		if (rmUser == null) {
			return null;
		}
		
		User user = UserDAO.getInstance().get(rmUser.getUsername());
		em.close();
		return user;
	}
}
