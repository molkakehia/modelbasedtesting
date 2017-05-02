/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.telnet.modelbasedtesting.service;
import com.telnet.modelbasedtesting.entities.Simulinkmodel;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Hanen LAFFET
 */
public class Main {
   public static void main(String[] args)
        {
        Simulinkmodel model= new Simulinkmodel();
        model.setNameModel("testAM");
        model.setDescriptionModel("testAD");
        model.setVersionModel("testAV");
        SimulinkModelServiceImpl simulinkModelService= new SimulinkModelServiceImpl();
        //  simulinkModelService.createModel(model);
        simulinkModelService.generateExpectedOutput("C:\\Users\\Molka\\Desktop\\Projet PFE\\Code Hanen\\modelBasedTesting_19_08\\integration_testCase.xls", "C:\\Users\\Molka\\Desktop\\Modèles_Valeo_à_tester\\Detect.mdl");
       
       /*try {
          // simulinkModelService.readTestCaseFile("C:\\hanen\\PFE_Telnet\\MesTests\\F.xls", 147);
           
          
       } catch (IOException ex) {
           Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
       }*/
          
        System.out.println("  End  ");
    
    }
    
}
