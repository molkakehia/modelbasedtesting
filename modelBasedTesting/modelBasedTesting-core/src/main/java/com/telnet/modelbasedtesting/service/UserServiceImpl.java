/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.telnet.modelbasedtesting.service;

import com.telnet.modelbasedtesting.dao.PersistenceProvider;
import com.telnet.modelbasedtesting.dao.UserDao;
import com.telnet.modelbasedtesting.entities.Testcase;
import com.telnet.modelbasedtesting.entities.User;
import java.util.List;

/**
 *
 * @author Hanen LAFFET
 */
//@Service("trainService")
public class UserServiceImpl implements UserService {
    public static final String PERSISTENCE_UNIT_NAME = "oacaPU";
    private UserDao userDao = new PersistenceProvider(PERSISTENCE_UNIT_NAME).getUserDao();

    public void createUser(User user) {
        userDao.add(user);
    System.out.println("    into  addUser UserServiceImpl");
    }

    public List<User> findAll() {
        List<User> varList = userDao.findAll();
       // System.out.println("    test dans UserServiceImpl"+ varList.toString());
        //LOGGER.debug("Get Users list :" + userList.toString());
        return varList; 
    }

    public User findByid(Integer id) {
       User var = null;
       var = userDao.findById(id);
         //LOGGER.debug("Find User : Search user with id :" + id + "  " + user.toString());
       return var;
    }

    public void updateUser(User user) {
        userDao.update(user);
    }

    public void deleteUser(User user) {
        userDao.remove(user);
    }
    
    public static void main(String[] args){
        UserService u= new UserServiceImpl();
        List <User> listUser= u.findAll();
        System.out.println("    List User "+ listUser.toString());
    }

    
}
