/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.telnet.modelbasedtesting.service;

import com.telnet.modelbasedtesting.dao.ResultDao;
import com.telnet.modelbasedtesting.dao.PersistenceProvider;
import com.telnet.modelbasedtesting.entities.Result;
import java.util.List;

/**
 *
 * @author Hanen LAFFET
 */
public class ResultServiceImpl implements ResultService{

    public static final String PERSISTENCE_UNIT_NAME = "oacaPU";
    //final static Logger LOGGER = Logger.getLogger(UserServiceImpl.class);
    private ResultDao resultDao = new PersistenceProvider(PERSISTENCE_UNIT_NAME).getResultDao();

    public void createVariable(Result model) {
       resultDao.add(model);
    }

    public List<Result> findAll() {
       List<Result> varList = resultDao.findAll();
                //LOGGER.debug("Get Users list :" + userList.toString());
                return varList;
    }

    public Result findByid(Integer id) {
         Result var = null;
         //UserDao userDao = new PersistenceProvider(PERSISTENCE_UNIT_NAME).getUserDao();
         var = resultDao.findById(id);
         //LOGGER.debug("Find User : Search user with id :" + id + "  " + user.toString());
         return var;
    }

    
    public void updateVariable(Result res) {
        resultDao.update(res);
    }

    public void deleteVariable(Result res) {
        resultDao.remove(res);
    }
    
    
}
