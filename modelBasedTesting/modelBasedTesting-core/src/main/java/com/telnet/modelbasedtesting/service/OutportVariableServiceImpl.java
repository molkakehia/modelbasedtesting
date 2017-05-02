/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.telnet.modelbasedtesting.service;

import com.telnet.modelbasedtesting.dao.OutportVariableDao;
import com.telnet.modelbasedtesting.dao.PersistenceProvider;
import com.telnet.modelbasedtesting.entities.Inportvariable;
import com.telnet.modelbasedtesting.entities.Outportvariable;
import java.util.List;

/**
 *
 * @author Hanen LAFFET
 */
public class OutportVariableServiceImpl implements OutportVariableService{

    public static final String PERSISTENCE_UNIT_NAME = "oacaPU";
        //final static Logger LOGGER = Logger.getLogger(UserServiceImpl.class);
        private OutportVariableDao outportVaribleDao = new PersistenceProvider(PERSISTENCE_UNIT_NAME).getOutportVariableDao();

    public void createVariable(Outportvariable model) {
       outportVaribleDao.add(model);
    }

    public List<Outportvariable> findAll() {
          List<Outportvariable> varList = outportVaribleDao.findAll();
                //LOGGER.debug("Get Users list :" + userList.toString());
                return varList; 
    }

    public Outportvariable findByid(Integer id) {
         Outportvariable var = null;
                //UserDao userDao = new PersistenceProvider(PERSISTENCE_UNIT_NAME).getUserDao();
                
         var = outportVaribleDao.findById(id);
         //LOGGER.debug("Find User : Search user with id :" + id + "  " + user.toString());
                
          return var;
    }

    public void updateVariable(Outportvariable var) {
        outportVaribleDao.update(var);
    }

    public void deleteVariable(Outportvariable var) {
        outportVaribleDao.remove(var);
    }
    
    public List<Outportvariable> findAllByIdSimulink(int id){
        List<Outportvariable> varList = outportVaribleDao.findAllByIdSimulink(id);
                //LOGGER.debug("Get Users list :" + userList.toString());
      //  List<Inportvariable> varList=null;
                return varList;
    }
    
}
