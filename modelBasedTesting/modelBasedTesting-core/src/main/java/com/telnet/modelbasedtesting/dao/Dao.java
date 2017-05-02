package com.telnet.modelbasedtesting.dao;

import java.util.List;

public interface Dao<K, E>
{

	void add(E entity);

	void remove(E entity);

	E findById(K id);
	
	List<E> findAll();
}
