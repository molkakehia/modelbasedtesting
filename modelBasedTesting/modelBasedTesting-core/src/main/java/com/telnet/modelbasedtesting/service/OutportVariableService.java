/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.telnet.modelbasedtesting.service;

import com.telnet.modelbasedtesting.entities.Outportvariable;
import java.util.List;

/**
 *
 * @author Hanen LAFFET
 */
public interface OutportVariableService {
    
    void createVariable(Outportvariable var);
        
    List<Outportvariable> findAll();
    
    List<Outportvariable> findAllByIdSimulink(int id);

    Outportvariable findByid(Integer id);

    void updateVariable(Outportvariable var);

    void deleteVariable(Outportvariable var);
    
    
}
