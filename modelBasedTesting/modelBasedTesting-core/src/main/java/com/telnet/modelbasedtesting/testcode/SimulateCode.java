package com.telnet.modelbasedtesting.testcode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class SimulateCode implements ISimulateCode {

    //@Override
    public File writeCodeTest(String codeCPath, String testCasesFileXlsPath) throws IOException {
        System.out.println("  Path:  "+codeCPath + "\\ert_main.c");
        File ert_main = new File(codeCPath + "\\ert_main.c");

        ArrayList<String> inputsVariablesNames = new ArrayList<String>();
        ArrayList<String> outputsVariablesNames = new ArrayList<String>();

        List<ArrayList<String>> inputsVariablesValues = new ArrayList<ArrayList<String>>();
        //ArrayList inputsVariablesValues = new ArrayList();
        ArrayList outputsVariablesValues = new ArrayList();

        //Buffer Reader for reading the main file ert_main
        BufferedReader br = null;
        String modelStep = "";

        try {
            String sCurrentLine;
					//String modelStep="";

            FileReader fileReader = new FileReader(ert_main);
            br = new BufferedReader(fileReader);
            while ((sCurrentLine = br.readLine()) != null) {
                if (sCurrentLine.contains("_step(")) {
                    if (sCurrentLine.contains(")")) {
                        modelStep = sCurrentLine;
                    } else {
                        modelStep = sCurrentLine;
                        while ((sCurrentLine = br.readLine()) != null) {    //model_step in multi lines
                            modelStep = modelStep + sCurrentLine;
                            if (sCurrentLine.contains(")")) {
                                //modelStep= modelStep+ sCurrentLine;
                                break;
                            }
                        }
                    }
                }
            }
            System.out.println("modelStep =" + modelStep);
            //StringTokenizer for splitting the string modelStep
            StringTokenizer st1 = new StringTokenizer(modelStep, ",");
            while (st1.hasMoreElements()) {
                String var = st1.nextElement().toString();

                if (var.contains("_U_")) {
                    if (var.contains("(")) {
                        inputsVariablesNames.add(var.split("\\(")[1]); // Create  inputs var
                    } else {
                        inputsVariablesNames.add(var);
                    }
                }
                if (var.contains("_Y_")) // Create  outputs var
                {
                    if (var.contains(")")) {
                        outputsVariablesNames.add(var.split("\\)")[0]);
                    } else {
                        outputsVariablesNames.add(var);
                    }
                }
            }
            for (Iterator<String> it = inputsVariablesNames.iterator(); it.hasNext();) {
                System.out.println("Item inputs is: " + it.next());
            }
            for (Iterator<String> it = outputsVariablesNames.iterator(); it.hasNext();) {
                System.out.println("Item output is: " + it.next());
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

				// Read test cases file: testCasesFileXlsPath
        // Find last row number of file testCasesFileXls
        InputStream myxls = new FileInputStream(testCasesFileXlsPath);
        HSSFWorkbook book = new HSSFWorkbook(myxls);
        HSSFSheet sheet = book.getSheetAt(0);
        int noOfRows = sheet.getLastRowNum();

        //Find number of columns 
        int noOfColumns = sheet.getRow(0).getPhysicalNumberOfCells();

        //write test cases 
        for (int colIndex = 0; colIndex < noOfColumns; colIndex++) {
            ArrayList<String> testData = new ArrayList<String>();;
            String t = "";
            int verif = 0; // pour ne pas repeter les cas de tests tant le nbre de col
            for (int rowIndex = 1; rowIndex <= noOfRows; rowIndex++) {

                HSSFRow row = sheet.getRow(rowIndex);
                if (row != null) {
                    String key = "";
                    HSSFCell cell = row.getCell(colIndex);
                    if (cell != null) {
                        try {
                            key = cell.getStringCellValue();
                        } catch (java.lang.IllegalStateException e) {
                            key = String.valueOf(cell.getNumericCellValue());
                        }
                        testData.add(key);
                    }
                }
            }
            /*System.out.println("Size final ="+testData.size());
             for (Iterator<Integer> it = testData.iterator(); it.hasNext();) {
             System.out.println("Item inputs values is: " + it.next());
             }*/

            if (verif == 0) {
                inputsVariablesValues.add(testData);
						//int j=colIndex+1;
                //script=script+"input.signals("+j+").dimensions=1;"
                //		+ "input.signals("+j+").values=boolean(["+testData+"]);\n";
                verif = 1;
            }
        }
        int i = 0;
        while (i < inputsVariablesValues.size()) {
            //for (Iterator<String> it = inputsVariablesValues.iterator(); it.hasNext();) {
            ArrayList var = inputsVariablesValues.get(i);
            System.out.println("inputsVariablesValues is: " + i);
            for (Iterator<Integer> it = var.iterator(); it.hasNext();) {
                System.out.println("inputsVariablesValues is: " + it.next());
            }
            i++;
        }
        System.out.println("Fin while inputsVariablesValues");

//				//Fin partie extraction des variables names & variables values
//				
//				// Delete block lines  doesn't work
//				File inputFile = new File("C:\\Hanen\\MesTests\\myFile.txt");
//				File tempFile = new File("C:\\Hanen\\MesTests\\myTempFile.txt");
//
//				BufferedReader reader = new BufferedReader(new FileReader(inputFile));
//				BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
//
//				String lineToRemove = "fflush((NULL));"
//						+ "while (rtmGetErrorStatus(M1_M) == (NULL)) {"
//						+ "/*  Perform other application tasks here */"
//						+ "}";
//				String currentLine;
//
//				while((currentLine = reader.readLine()) != null) {
//				    // trim newline when comparing with lineToRemove
//				    String trimmedLine = currentLine.trim();
//				    if(trimmedLine.equals(lineToRemove)) continue;
//				    writer.write(currentLine + System.getProperty("line.separator"));
//				}
//				
//				writer.close(); 
//				reader.close(); 
//				boolean successful = tempFile.renameTo(inputFile);
//				//Fin delete lines
        //Write Script test
        String testCode = "";
        String[] arrayInputVarVal;
        for (int indexRow = 0; indexRow < noOfRows; indexRow++) {

            for (int indexcol = 0; indexcol < noOfColumns; indexcol++) {
                String inputVarVal = inputsVariablesValues.get(indexcol).get(indexRow); // test for  array input variable like [ 1 1 1 1 ]
                System.out.println("inputVarVal" + inputVarVal + "\n");
                if (inputVarVal.contains("[")) {
                    // tableau contenant les valeurs 1 1 1 1
                    arrayInputVarVal = inputVarVal.split("\\s+");
                    System.out.println("length" + arrayInputVarVal.length);
                    for (int nbreOfVal = 0; nbreOfVal < arrayInputVarVal.length; nbreOfVal++) {
                        if (!(arrayInputVarVal[nbreOfVal].contains("[") || arrayInputVarVal[nbreOfVal].contains("]"))) {
                            testCode = testCode + inputsVariablesNames.get(indexcol) + "[" + (nbreOfVal - 1) + "]" + "=" + arrayInputVarVal[nbreOfVal] + ";\n";
                        }
                    }
                } else {
                    testCode = testCode + inputsVariablesNames.get(indexcol) + "=" + inputVarVal + ";\n";
                }
                //	System.out.println("test code c "+testCode);
            }

            testCode = testCode + modelStep + "\n printf(\"Output " + indexRow + " =";

            for (int it = 0; it < outputsVariablesNames.size(); it++) {
  // Modified 05/04
                testCode = testCode + " %f ";
                testCode = testCode + " %f ";
                testCode = testCode + " %f ";
                testCode = testCode + " %f ";
            }
            //System.out.println("test code c 2"+testCode);
            testCode = testCode + " \",";
            for (Iterator<String> it = outputsVariablesNames.iterator(); it.hasNext();) {
                String s = it.next();
                if (s.contains("&")) {
                    s = s.split("&")[1];
                }
// modified 05/04  testCode = testCode + s + ",";
                testCode =testCode +"(double)" + s+ "[0]" + ",";
                testCode =testCode +"(double)" + s+ "[1]" + ",";
                testCode =testCode +"(double)" + s+ "[2]" + ",";
                testCode =testCode +"(double)" + s+ "[3]" + ",";
            }
            testCode = testCode.substring(0, testCode.length() - 1) + " );\n" + "fflush((NULL));\n";

        }
				//System.out.println("Test Code "+testCode);

//					String sCurrentLine;
//					
//					FileReader fileReader=new FileReader(ert_main);
//					br = new BufferedReader(fileReader);
//					while ((sCurrentLine = br.readLine()) != null) {
//						if (sCurrentLine.contains("_initialize"))
//							modelStep= sCurrentLine;	
//					
//					}
				// write the test code in ert_main
				//	PrintWriter writer2 = new PrintWriter(new BufferedWriter(new FileWriter(codeCPath+"\\ert_main.c")));
        // Open a temporary file to write to.
        PrintWriter writer2 = new PrintWriter(codeCPath + "\\ert_main2.c");

        // ... then inside your loop ...
        String sCurrentLine;
        int verifInit = 0;

        FileReader fileReader = new FileReader(ert_main);
        br = new BufferedReader(fileReader);
        while ((sCurrentLine = br.readLine()) != null) {
            if (sCurrentLine.contains("_initialize") && sCurrentLine.contains(")")) {
                writer2.write(sCurrentLine);
                sCurrentLine = br.readLine();
                sCurrentLine = sCurrentLine.replaceFirst(sCurrentLine, testCode);
                System.out.println("sCurrentLine" + sCurrentLine);
            } else if (sCurrentLine.contains("_initialize")) {
                writer2.write(sCurrentLine);
                if (verifInit == 0) {
                    while ((sCurrentLine = br.readLine()) != null) {
                        writer2.write(sCurrentLine);
                        if (sCurrentLine.contains(")")) {
                            verifInit = 1;
                            break;
                        }
                    }
                }

                //modelStep= sCurrentLine;
                sCurrentLine = br.readLine();
                sCurrentLine = sCurrentLine.replaceFirst(sCurrentLine, testCode);
                System.out.println("sCurrentLine" + sCurrentLine);
            }
            // Always write the line, whether you changed it or not.
            writer2.println(sCurrentLine);
            System.out.println("sCurrentLine" + sCurrentLine);

        }
        writer2.close();
				// ... and finally ...
        //	ert_main.delete(); // remove the old file
        //	new File(codeCPath+"\\ert_main.temp").renameTo(ert_main); // Rename temp file

        return null;
    }

    public static void main(String[] args) {
        ISimulateCode s = new SimulateCode();
        try {
            //s.writeCodeTest("C:\\Hanen\\MatlabWorkingFolder\\M1_ert_rtw", "C:\\Hanen\\MesTests\\M1.xls");
            //s.writeCodeTest("C:\\Hanen\\MatlabWorkingFolder\\F_ert_rtw", "C:\\Hanen\\MesTests\\F_3.xls");
            s.writeCodeTest("C:\\hanen\\PFE_Telnet\\TestCodeCoverage\\F_ert_rtw", "C:\\Users\\Molka\\Desktop\\Projet PFE\\Code Hanen\\modelBasedTesting_19_08\\integration_testCase.xls");

            
            System.out.println("Fin Main");
       } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
