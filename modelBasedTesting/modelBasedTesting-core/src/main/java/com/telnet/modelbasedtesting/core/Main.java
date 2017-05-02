/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.telnet.modelbasedtesting.core;

import com.telnet.modelbasedtesting.entities.Simulinkmodel;
import com.telnet.modelbasedtesting.dao.PersistenceProvider;
import com.telnet.modelbasedtesting.dao.*;
import com.telnet.modelbasedtesting.entities.Inportvariable;
import com.telnet.modelbasedtesting.entities.Outportvariable;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import org.apache.log4j.BasicConfigurator;

/**
 *
 * @author Hanen LAFFET
 */
public class Main {
    public static void main(String[] args)
            //public static void main()
		throws  NoSuchAlgorithmException, 
		UnsupportedEncodingException
	{
		/*User user = new User();
		user.setLogin("admin");
                user.setPassword("admin");
		user.setNameUser("test1");
                user.setTypeUser("test1");*/
                        
                Simulinkmodel model = new Simulinkmodel();
		model.setNameModel("FastCore");
                model.setVersionModel("v-1.0");
                model.setDescriptionModel("C://");  //Path
                
                        
		PersistenceProvider provider = new PersistenceProvider("oacaPU");
		SimulinkModelDao mdao = provider.getSimulinkModelDao();
		mdao.add(model);
                
                
                Inportvariable inportVar= new Inportvariable();
                inportVar.setNameInport("y");
                inportVar.setDataTypeInport("y");
                inportVar.setPortDimensionInport("y");
                inportVar.setSimulinkModelidModel(model);
                
                InportVariableDao idao = provider.getInportVariableDao();
		idao.add(inportVar);
                
                
                 Outportvariable outportVar= new Outportvariable();
                outportVar.setNameOutport("y");
                outportVar.setDataTypeOutport("y");
                outportVar.setPortDimensionOutport("y");
                outportVar.setSimulinkModelidModel(model);
                
                OutportVariableDao odao = provider.getOutportVariableDao();
                try {
                odao.add(outportVar);
                } catch (Exception ex)
                {
                     ex.printStackTrace();
                }
                BasicConfigurator.configure();
                
                System.out.println("    test model+ "+ model.toString()+"  "+mdao.findAll().toString());
            //    System.out.println("    test inport+ "+idao.findAll().toString());
	}
}
