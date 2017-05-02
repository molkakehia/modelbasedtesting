
package com.telnet.modelbasedtesting.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaQuery;

abstract class AbstractDao<K, E> implements Dao<K, E>
{
	protected Class entityClass;
	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;

	public AbstractDao(EntityManagerFactory entityManagerFactory)
	{
		ParameterizedType genericSuperclass = (ParameterizedType) getClass().
			getGenericSuperclass();
		this.entityClass = (Class) genericSuperclass.getActualTypeArguments()[1];
		this.entityManagerFactory = entityManagerFactory;
	}
	
	@Override
	public void add(E entity)
	{
		beginTransaction();
		entityManager.persist(entity);
		endTransaction();
	}

	@Override
	public void remove(E entity)
	{
		beginTransaction();
		// the passed entity is detached so merge it
		entity = entityManager.merge(entity);
		entityManager.remove(entity);
		endTransaction();
	}

	@Override
	public E findById(K id)
	{
		beginTransaction();
		E result = (E) entityManager.find(entityClass, id);
		endTransaction();
		return result;
	}
	
        @Override
	public List<E> findAll()
	{
                
                beginTransaction();
		CriteriaQuery cq = entityManager.getCriteriaBuilder().createQuery(); //System.out.println("   cq  = "+ cq);
		cq.select(cq.from(entityClass)); 
                List<E> result = entityManager.createQuery(cq).getResultList();
		endTransaction();
                
		return result;
	}
	
	public E update(E entity)
	{
		beginTransaction();
		E newManagedENtity = entityManager.merge(entity);
		endTransaction();
		return newManagedENtity;
	}
	
	void beginTransaction()
	{
		entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
	}

	void rollback()
	{
		entityManager.getTransaction().rollback();
	}

	void endTransaction()
	{
		entityManager.getTransaction().commit();
		entityManager.close();
	}
	
	void flush()
	{
		entityManager.flush();
	}

	void joinTransaction()
	{
		entityManager = entityManagerFactory.createEntityManager();
		entityManager.joinTransaction();
	}
	
	/**
	 * Gets the entity manager if not null.
	 * 
	 * @return the entity manager. 
	 * 	It can be {@code null} (closed entity manager). 
	 * 	Call {@link #beginTransaction()} before. 
	 */
	EntityManager getEntityManager()
	{
		return entityManager;
	}
        
}
