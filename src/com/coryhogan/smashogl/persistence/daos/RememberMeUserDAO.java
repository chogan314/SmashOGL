package com.coryhogan.smashogl.persistence.daos;

import javax.persistence.EntityManager;

import com.coryhogan.smashogl.persistence.entities.RememberMeUser;
import com.coryhogan.smashogl.persistence.entities.User;
import com.coryhogan.smashogl.persistence.utils.EMFService;
import com.google.appengine.api.datastore.Key;

public final class RememberMeUserDAO {
	private RememberMeUserDAO() { }
	
	public static void add(String username) {
		EntityManager em = EMFService.get().createEntityManager();
		RememberMeUser rmUser = new RememberMeUser(username);
		em.persist(rmUser);
		em.close();
	}
	
	public static User getUserFromCookieData(String data) {
		return null;
	}
	
	public static void remove(Key key) {
		EntityManager em = EMFService.get().createEntityManager();
		RememberMeUser rmUser = em.find(RememberMeUser.class, key);
		em.remove(rmUser);
		em.close();
	}
}
