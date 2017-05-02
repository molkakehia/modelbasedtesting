/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.telnet.modelbasedtesting.dao;

import com.telnet.modelbasedtesting.entities.Inportvariable;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

/**
 *
 * @author Hanen LAFFET
 */
public class InportVariableDao extends AbstractDao<String, Inportvariable>
{

        public InportVariableDao(EntityManagerFactory entityManagerFactory)
        {
                super(entityManagerFactory);
        }

        public Inportvariable findById(Integer id) //throws GSPFException
        {
                beginTransaction();
                Query query = getEntityManager().createNamedQuery(Inportvariable.FIND_BY_ID);
                query.setParameter("id", id);
                List result = query.getResultList();

                endTransaction();
                if (result == null || result.isEmpty()) {
                        return null;
                } else if (result.size() > 1) {
                   
                    //    throw new GSPFException("More than a unique user "
                     //           + "was found with id " + id);
                }
                
                return (Inportvariable) result.get(0);
                

        }

        public List<Inportvariable> findModels() //throws GSPFException
        {
                beginTransaction();

                Query query = getEntityManager().createNamedQuery(Inportvariable.FIND_ALL);
                List result = query.getResultList();
                endTransaction();
                if (result == null || result.isEmpty()) {
                        return null;
                }

                return (List<Inportvariable>) result;

        }
    
         public List<Inportvariable> findAllByIdSimulink(int id) //throws GSPFException
        {
                beginTransaction();

                Query query = getEntityManager().createNamedQuery(Inportvariable.FIND_BY_IDSIM);
                query.setParameter("idModel", id);
                List result = query.getResultList();
                endTransaction();
                if (result == null || result.isEmpty()) {
                        return null;
                }

                return (List<Inportvariable>) result;

        }
}
