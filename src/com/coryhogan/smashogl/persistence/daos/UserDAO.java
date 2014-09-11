package com.coryhogan.smashogl.persistence.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.coryhogan.smashogl.persistence.entities.User;
import com.coryhogan.smashogl.persistence.utils.EMFService;

public final class UserDAO {
	private UserDAO() { }
	
	public static List<User> asList() {
		EntityManager em = EMFService.get().createEntityManager();
		TypedQuery<User> tq = em.createQuery("select m from Todo m", User.class);
		List<User> users = tq.getResultList();
		em.close();
		return users;
	}
	
	public static void add(String username, String passwordHash, String emailAddress) {
		EntityManager em = EMFService.get().createEntityManager();
		User user = new User(username, passwordHash, emailAddress);
		em.persist(user);
		em.close();
	}
	
	public static User get(String username) {
		EntityManager em = EMFService.get().createEntityManager();
		User user = em.find(User.class, username);
		em.close();
		return user;
	}
	
	public static void remove(String username) {
		EntityManager em = EMFService.get().createEntityManager();
		User user = em.find(User.class, username);
		em.remove(user);
		em.close();
	}
}
