
package com.telnet.modelbasedtesting.dao;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * 
 * @author Hanen LAFFET
 */
public final class EMSingleFactory
{
	private static Map<String, EntityManagerFactory> emFactories 
		= new HashMap<String, EntityManagerFactory>();
	
	private EMSingleFactory()
	{
		/**
		 * 
		 */
	}

	public static EntityManagerFactory getEMF(String persistenceUnit)
	{
		if (!emFactories.containsKey(persistenceUnit)) {
			synchronized (EMSingleFactory.class) {
				if (!emFactories.containsKey(persistenceUnit)) {
					EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnit);
					emFactories.put(persistenceUnit, emf);
				}
			}
		}
		return emFactories.get(persistenceUnit);
	}
}
