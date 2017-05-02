/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.telnet.modelbasedtesting.dao;

import com.telnet.modelbasedtesting.entities.Testcase;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

/**
 *
 * @author Hanen LAFFET
 */
public class TestCaseDao extends AbstractDao<String, Testcase>
{

        public TestCaseDao(EntityManagerFactory entityManagerFactory)
        {
                super(entityManagerFactory);
        }

        public Testcase findById(Integer id) //throws GSPFException
        {
                beginTransaction();
                Query query = getEntityManager().createNamedQuery(Testcase.FIND_BY_ID);
                query.setParameter("id", id);
                List result = query.getResultList();

                endTransaction();
                if (result == null || result.isEmpty()) {
                        return null;
                } else if (result.size() > 1) {
                    //    throw new GSPFException("More than a unique user "
                     //           + "was found with id " + id);
                }
                return (Testcase) result.get(0);

        }

        public List<Testcase> findModels() //throws GSPFException
        {
                beginTransaction();

                Query query = getEntityManager().createNamedQuery(Testcase.FIND_ALL);
                List result = query.getResultList();
                endTransaction();
                if (result == null || result.isEmpty()) {
                        return null;
                }

                return (List<Testcase>) result;

        }
    
}
