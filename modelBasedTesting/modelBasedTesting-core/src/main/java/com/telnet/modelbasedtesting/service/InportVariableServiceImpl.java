/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.telnet.modelbasedtesting.service;

import com.telnet.modelbasedtesting.dao.PersistenceProvider;
import com.telnet.modelbasedtesting.entities.Inportvariable;
import com.telnet.modelbasedtesting.dao.InportVariableDao;
import com.telnet.modelbasedtesting.entities.Simulinkmodel;
import java.util.List;

/**
 *
 * @author Hanen LAFFET
 */
public class InportVariableServiceImpl implements InportVariableService{

    public static final String PERSISTENCE_UNIT_NAME = "oacaPU";
        //final static Logger LOGGER = Logger.getLogger(UserServiceImpl.class);
        private InportVariableDao inportVaribleDao = new PersistenceProvider(PERSISTENCE_UNIT_NAME).getInportVariableDao();

    public void createVariable(Inportvariable model) {
       inportVaribleDao.add(model);
    }

    public List<Inportvariable> findAll() {
       List<Inportvariable> varList = inportVaribleDao.findAll();
                //LOGGER.debug("Get Users list :" + userList.toString());
                return varList;
    }

    public Inportvariable findByid(Integer id) {
         Inportvariable var = null;
                //UserDao userDao = new PersistenceProvider(PERSISTENCE_UNIT_NAME).getUserDao();
                
         var = inportVaribleDao.findById(id);
         //LOGGER.debug("Find User : Search user with id :" + id + "  " + user.toString());
                
          return var;
    }

    public void updateVariable(Inportvariable model) {
        inportVaribleDao.update(model);
    }

    public void deleteVariable(Inportvariable model) {
        inportVaribleDao.remove(model);
    }
    
    
    public List<Inportvariable> findAllByIdSimulink(int id){
        List<Inportvariable> varList = inportVaribleDao.findAllByIdSimulink(id);
                //LOGGER.debug("Get Users list :" + userList.toString());
      //  List<Inportvariable> varList=null;
                return varList;
    }
    
}
