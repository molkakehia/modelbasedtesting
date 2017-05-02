/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.telnet.modelbasedtesting.dao;

import com.telnet.modelbasedtesting.entities.Result;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

/**
 *
 * @author Hanen LAFFET
 */
public class ResultDao extends AbstractDao<String, Result>
{

        public ResultDao(EntityManagerFactory entityManagerFactory)
        {
                super(entityManagerFactory);
        }

        public Result findById(Integer id) //throws GSPFException
        {
                beginTransaction();
                Query query = getEntityManager().createNamedQuery(Result.FIND_BY_ID);
                query.setParameter("id", id);
                List result = query.getResultList();

                endTransaction();
                if (result == null || result.isEmpty()) {
                        return null;
                } else if (result.size() > 1) {
                    //    throw new GSPFException("More than a unique user "
                     //           + "was found with id " + id);
                }
                return (Result) result.get(0);

        }

        public List<Result> findModels() //throws GSPFException
        {
                beginTransaction();

                Query query = getEntityManager().createNamedQuery(Result.FIND_ALL);
                List result = query.getResultList();
                endTransaction();
                if (result == null || result.isEmpty()) {
                        return null;
                }

                return (List<Result>) result;

        }
    
}
