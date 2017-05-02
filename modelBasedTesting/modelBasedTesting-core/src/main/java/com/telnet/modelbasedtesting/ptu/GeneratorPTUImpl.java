/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.telnet.modelbasedtesting.ptu;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;

/**
 *
 * @author Hanen LAFFET
 */
public class GeneratorPTUImpl implements GeneratorPTU {
    
    File file = new File(System.getProperty("user.dir")+"\\tmp\\TestModel_PTU.ptu");


    public void generatePTUScript(String sourceCodePath) {
        int nbrOfTest=1;
        // PTU Script 
         String ptuScript="ENTETE TestModel_PTU \n"
                + "\n"
                + "comment* Author:  HL \n"
                + "comment* Code C generated from Embedded Coder Matlab \n"
                + "\n";
                
        
        
        // search file in folder sourceCodePath and find header files 
        File fSourceCodePath = new File(sourceCodePath);
        File[] matchingFiles = fSourceCodePath.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return  name.endsWith(".h");  //   return name.startsWith("temp") && name.endsWith("txt");
            }
        });
 
        // Get header files names 
        for (File child: matchingFiles){
            ptuScript =ptuScript +"## include "+ child.getName()+ "; \n"; 
            System.out.println("   Header FileName: "+ child.getName());
        }
        
        // Debut de Ptu
        ptuScript =ptuScript+ "\n"
                + "DEBUT \n" ;
               // + "SERVICE M12_step";
        
        
        // Debut SERViCE
        // Search _step.c file
        File[] matchingFiles2 = fSourceCodePath.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return  name.endsWith(".c");  //   return name.startsWith("temp") && name.endsWith("txt");
            }
        });
        
        // Get the RIGHT C file name 
        String cFile="";
        for (File child: matchingFiles2){
            String name= child.getName();
            if (!name.equals("ert_main.c") && !name.equals("rtGetInf.c") && !name.equals("rtGetNaN.c")){
                if ( !name.contains("_")){
                    cFile=name;
                    System.out.println("   C FileName: "+ name);
                }
            }
        }
        
        ptuScript =ptuScript+ "SERVICE "+ cFile.split("\\.")[0]+"_step \n"
                + "TYPE_SERVICE externe \n"
                + "\n"
                + " TEST "+nbrOfTest+ "\n"
                + "     FAMILLE Black Box \n"
                + "     ELEMENT \n"
                + "     --* Input parameters (ev = init)\n"   // a partir de cette partie
                + "       VAR M12_U_In1,         init = 0,        ev = init \n"
                + "       VAR M12_U_In2,         init = 0,        ev = init \n"
                + "       VAR M12_U_In3,         init = 0,        ev = init \n"
                + "      --* Output parameters (ev != init) \n"
                + "       VAR M12_Y_Out1,         init = 1,        ev = 0 \n"
                + "     --* Tested operation call \n"
                + "     #M12_step(M12_U_In1, M12_U_In2, M12_U_In3, &M12_Y_Out1); \n"
                + ""
                + ""
                + "     FIN ELEMENT \n"
                + " FIN TEST \n"
                + "FIN SERVICE \n";
        /*
        // Read model.c file 
        BufferedReader br = null;
        try {

            String sCurrentLine;
            br = new BufferedReader(new FileReader(sourceCodePath+"\\"+cFile));

            while ((sCurrentLine = br.readLine()) != null) {
              if(sCurrentLine.contains("_step"))
                System.out.println("    sCurrentLine: "+sCurrentLine);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
      */
       
        
       
        try{
            // if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(ptuScript);
			bw.close();

			System.out.println("Done");

		} catch (IOException e) {
			e.printStackTrace();
		}
    
      //  return  file;
    }
    public static void main(String[] args){
        
        GeneratorPTU genPtu = new GeneratorPTUImpl();
        genPtu.generatePTUScript("C:\\Hanen\\MatlabWorkingFolder\\M12_ert_rtw");
        
        
    }
    
}
