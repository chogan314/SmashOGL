package com.smashogl.persistence.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.smashogl.persistence.utils.EMFService;

public abstract class DAO <T, K> {
	protected Class<T> clazz;
	
	protected DAO(Class<T> clazz) {
		this.clazz = clazz;
	}
	
	public void add(T obj) {
		EntityManager em = EMFService.get().createEntityManager();
		em.persist(obj);
		em.close();
	}
	
	public T get(K key) {
		EntityManager em = EMFService.get().createEntityManager();
		T obj = em.find(clazz, key);
		em.close();
		return obj;
	}
	
	public void remove(K key) {
		EntityManager em = EMFService.get().createEntityManager();
		T obj = em.find(clazz, key);
		em.remove(obj);
		em.close();
	}
	
	public List<T> asList() {
		EntityManager em = EMFService.get().createEntityManager();
		TypedQuery<T> tq = em.createQuery("select m from " + clazz.getSimpleName() + " m", clazz);
		List<T> lst = tq.getResultList();
		em.close();
		return lst;
	}
}
