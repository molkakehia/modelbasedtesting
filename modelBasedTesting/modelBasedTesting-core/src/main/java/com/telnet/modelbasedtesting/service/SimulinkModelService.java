/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.telnet.modelbasedtesting.service;

import com.telnet.modelbasedtesting.entities.Simulinkmodel;

import java.util.List;

/**
 *
 * @author Hanen LAFFET
 */
public interface SimulinkModelService {
    void createModel(Simulinkmodel model);
        
        List<Simulinkmodel> findAll();

        Simulinkmodel findByid(Integer id);

        void updateModel(Simulinkmodel model);

        void deleteModel(Simulinkmodel model);
        
        void analyseModel(String modelPath);
        
        void generateExpectedOutput (String testCasesFileXlsPath, String modelPath);
        
        //
        void loadModel(String modelPath);
        
        void verifyModel(String modelPath);
        
        void generateCodeC(String modelPath);
        
        void generateTestSuite(String modelPath);
        
}
