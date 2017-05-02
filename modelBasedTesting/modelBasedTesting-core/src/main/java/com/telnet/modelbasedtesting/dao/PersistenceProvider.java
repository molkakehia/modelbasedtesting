package com.telnet.modelbasedtesting.dao;

import javax.persistence.EntityManagerFactory;

public class PersistenceProvider {

    private SimulinkModelDao simulinkModelDao;
    private InportVariableDao inportVariableDao;
    private OutportVariableDao outportVariableDao;
    private TestCaseDao testCaseDao;
    private UserDao userDao;
    private ResultDao resultDao;

    public PersistenceProvider(String persistenceUnit) {
        EntityManagerFactory emf = EMSingleFactory.
                getEMF(persistenceUnit);
        this.simulinkModelDao = new SimulinkModelDao(emf);
        this.inportVariableDao= new InportVariableDao(emf);
        this.outportVariableDao= new OutportVariableDao(emf);
        this.testCaseDao= new TestCaseDao(emf);
        this.userDao= new UserDao(emf);
        this.resultDao= new ResultDao(emf);
    }

    public SimulinkModelDao getSimulinkModelDao() {
        return simulinkModelDao;
    }

    public InportVariableDao getInportVariableDao() {
        return inportVariableDao;
    }

    public OutportVariableDao getOutportVariableDao() {
        return outportVariableDao;
    }

    public TestCaseDao getTestCaseDao() {
        return testCaseDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public ResultDao getResultDao() {
        return resultDao;
    }

    
    
  
}
