/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.telnet.modelbasedtesting.service;

import com.telnet.modelbasedtesting.entities.Inportvariable;
import java.util.List;

/**
 *
 * @author Hanen LAFFET
 */
public interface InportVariableService {
    
    void createVariable(Inportvariable var);
        
    List<Inportvariable> findAll();

    List<Inportvariable> findAllByIdSimulink(int id);
    
    Inportvariable findByid(Integer id);

    void updateVariable(Inportvariable var);

    void deleteVariable(Inportvariable var);
}
