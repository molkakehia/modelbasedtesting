/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.telnet.modelbasedtesting.dao;

import com.telnet.modelbasedtesting.entities.User;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

/**
 *
 * @author Hanen LAFFET
 */
public class UserDao extends AbstractDao<String, User>
{

        public UserDao(EntityManagerFactory entityManagerFactory)
        {
                super(entityManagerFactory);
        }

        public User findById(Integer id) //throws GSPFException
        {
                beginTransaction();
                Query query = getEntityManager().createNamedQuery(User.FIND_BY_ID);
                query.setParameter("id", id);
                List result = query.getResultList();

                endTransaction();
                if (result == null || result.isEmpty()) {
                        return null;
                } else if (result.size() > 1) {
                    //    throw new GSPFException("More than a unique user "
                     //           + "was found with id " + id);
                }
                return (User) result.get(0);

        }

        public List<User> findUsers() //throws GSPFException
        {
                beginTransaction();

                Query query = getEntityManager().createNamedQuery(User.FIND_ALL);
                List result = query.getResultList();
                endTransaction();
                if (result == null || result.isEmpty()) {
                        return null;
                }

                return (List<User>) result;

        }
    
}
