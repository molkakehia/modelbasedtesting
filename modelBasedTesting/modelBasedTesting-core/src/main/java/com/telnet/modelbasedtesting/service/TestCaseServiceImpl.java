/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.telnet.modelbasedtesting.service;

import com.telnet.modelbasedtesting.dao.PersistenceProvider;
import com.telnet.modelbasedtesting.dao.TestCaseDao;
import com.telnet.modelbasedtesting.entities.Testcase;
import java.util.List;

/**
 *
 * @author Hanen LAFFET
 */
public class TestCaseServiceImpl implements TestCaseService {
    
        public static final String PERSISTENCE_UNIT_NAME = "oacaPU";
        //final static Logger LOGGER = Logger.getLogger(UserServiceImpl.class);
        private TestCaseDao testCaseDao = new PersistenceProvider(PERSISTENCE_UNIT_NAME).getTestCaseDao();

    public void createTestCase(Testcase var) {
       testCaseDao.add(var);
    }

    public List<Testcase> findAll() {
        List<Testcase> varList = testCaseDao.findAll();
                //LOGGER.debug("Get Users list :" + userList.toString());
                return varList; 
    }

    public Testcase findByid(Integer id) {
       Testcase var = null;
       var = testCaseDao.findById(id);
         //LOGGER.debug("Find User : Search user with id :" + id + "  " + user.toString());
       return var;
    }

    public void updateTestCase(Testcase var) {
        testCaseDao.update(var);
    }

    public void deleteTestCase(Testcase var) {
        testCaseDao.remove(var); 
    }
    
}
