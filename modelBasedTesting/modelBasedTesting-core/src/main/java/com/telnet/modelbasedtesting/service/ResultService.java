/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.telnet.modelbasedtesting.service;

import com.telnet.modelbasedtesting.entities.Result;
import java.util.List;

/**
 *
 * @author Hanen LAFFET
 */
public interface ResultService {
    void createVariable(Result var);
        
    List<Result> findAll();

    Result findByid(Integer id);

    void updateVariable(Result var);

    void deleteVariable(Result var);
    
}
