/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.telnet.modelbasedtesting.service;

import com.telnet.modelbasedtesting.entities.User;
import java.util.List;

/**
 *
 * @author Hanen LAFFET
 */
public interface UserService {
    void createUser(User user);
        
    List<User> findAll();

    User findByid(Integer id);

    void updateUser(User user);

    void deleteUser(User user);
}
