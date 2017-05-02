package com.telnet.modelbasedtesting.testmodel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import matlabcontrol.MatlabConnectionException;
import matlabcontrol.MatlabInvocationException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class SimulateModel implements ISimulateModel {
	
	public String getModelName(String modelPath){
		String modelName="";
		String[] m= modelPath.split("\\.")[0].replace("\\", "/").split("/");
		modelName= m[m.length-1];
		
		return modelName;
	}
	
	public void writeInFile(String script, File scriptFile){
		//BufferWriter
        BufferedWriter output = null;
        try {
            output = new BufferedWriter(new FileWriter(scriptFile));
            output.write(script);
        } catch ( IOException e ) {
            e.printStackTrace();
        } finally {
            if ( output != null )
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        }
		
	}
	
	/**Generate coverage report from model and test suites**/
	public static void replaceAll(StringBuffer builder, String from, String to)
	{
	    int index = builder.indexOf(from);
	    while (index != -1)
	    {
	        builder.replace(index, index + from.length(), to);
	        index += to.length(); // Move to the end of the replacement
	        index = builder.indexOf(from, index);
	    }
	}
	
	public void xlsTocsv(File inputFile, File outputFile) {
		
		// For storing data into CSV files
        StringBuffer data = new StringBuffer();
        try 
        {
        FileOutputStream fos = new FileOutputStream(outputFile);

        // Get the workbook object for XLS file
        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(inputFile));
        // Get first sheet from the workbook
        HSSFSheet sheet = workbook.getSheetAt(0);
        Cell cell;
        Row row;

        // Iterate through each rows from first sheet
        Iterator<Row> rowIterator = sheet.iterator();
        int i=0;rowIterator.next();
        while (rowIterator.hasNext()) 
        {
                row = rowIterator.next();
                // For each row, iterate through each columns
                Iterator<Cell> cellIterator = row.cellIterator();
                data.append(i+","); 
                while (cellIterator.hasNext()) 
                {
                        cell = cellIterator.next();
                        
                        switch (cell.getCellType()) 
                        {
                        case Cell.CELL_TYPE_BOOLEAN:
                                data.append(cell.getBooleanCellValue() + ",");
                                break;
                                
                        case Cell.CELL_TYPE_NUMERIC:
                                data.append(cell.getNumericCellValue() + ",");
                                break;
                                
                        case Cell.CELL_TYPE_STRING:
                                data.append(cell.getStringCellValue() + ",");
                                break;

                        case Cell.CELL_TYPE_BLANK:
                                data.append("" + ",");
                                break;
                        
                        default:
                        	
                                data.append(cell + ",");
                        }
                        
                        
                }data.deleteCharAt(data.length()-1);data.append('\n'); i++;
        }
        //replace [
        /*for (int index = 0; index < data.length(); index++) {
            if (data.charAt(index) == '[') {
                data.setCharAt(index, ' ');
            }
        }*/
        
        replaceAll(data, "[ ", "");
        replaceAll(data, " ]", "");
        replaceAll(data, " ", ",");
        
        fos.write(data.toString().getBytes());
        fos.close();
        }
        catch (FileNotFoundException e) 
        {
                e.printStackTrace();
        }
        catch (IOException e) 
        {
                e.printStackTrace();
        }
        }
	
	@Override
	public File coverageReportScript(File scriptFile, String testCasesFileXlsPath, String modelPath){
		// In this step, we import manually test suites to model harness
		String modelName= getModelName(modelPath);
		String script= "cd "+modelPath.split(modelName)[0]+";\n"
				+ "load_system('"+modelPath+"');\n"
				+ "sldvmakeharness('"+modelName+"');";
		
		//method write in script file 
		writeInFile(script, scriptFile);
	
		return scriptFile;
	}
	
	
	/** Generate Expected Output result from Model**/
	
	//File scriptFile; 
	@Override
	public File createScriptFile(String pathFile) throws IOException{
		//System.getProperty("user.dir")+ "/tmp/slices/principal.m"
		File scriptFile; 
		scriptFile= new File(pathFile);
		if (!(scriptFile.exists())) {
			scriptFile.createNewFile();
		}
		return scriptFile;
	}

	//Analyze and extract informations from model: Nom variables inport; outport
	@Override
        public ArrayList<ArrayList<String>> modelVariablesInportOutport(String modelPath){
		ArrayList<ArrayList<String>> modelVariable = new ArrayList<ArrayList<String>>();
		ArrayList<String> inportVariablesNames = new ArrayList<String>();
		ArrayList<String> outportVariablesNames = new ArrayList<String>();
		
		
		String modelVariablesScript ="load_system('"+modelPath+"');\n"
				+ "blockPaths = find_system('"+getModelName(modelPath)+"','Type','Block');\n"
						+ "blockTypes = get_param(blockPaths,'BlockType');\n"
						+ "BB=[blockPaths,blockTypes];\n"
						+ "fileID = fopen('"+System.getProperty("user.dir")+"\\tmp\\VariableNameType2.txt','w');\n"
						+ "formatSpec = '%s %s \\n';\n"
						+ "[nrows,ncols] = size(BB);\n"
						+ "for row = 1:nrows "
						+ "fprintf(fileID,formatSpec,BB{row,:}); "
						+ "end \n"
						+ "fclose(fileID); \n";
		File modelVariablesScriptFile = new File(System.getProperty("user.dir")+"\\tmp\\modelVariables.m");
		writeInFile(modelVariablesScript, modelVariablesScriptFile);
		System.out.println("End write in file");
		try {
			System.out.println("Begin run matlab");
			runScriptMatlab(modelVariablesScriptFile); System.out.println("End running matlab");
		} catch (MatlabInvocationException e) {
			e.printStackTrace();
		} catch (MatlabConnectionException e) {
			e.printStackTrace();
		}
		
		// Read txt VariableName file
		
		BufferedReader bufferRed = null;
		String currentLine = "";
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(System.getProperty("user.dir")+ "\\tmp\\VariableNameType2.txt");

			bufferRed = new BufferedReader(fileReader);
			while ((currentLine = bufferRed.readLine()) != null) {
				if(currentLine.contains("Inport")&& currentLine.split("/").length==2)
					inportVariablesNames.add(currentLine.split("\\s+")[0].split("/")[1].trim()); // split espace donne M1/In1 , ensuite split / donne In1
				else if(currentLine.contains("Outport")&& currentLine.split("/").length==2)
					outportVariablesNames.add(currentLine.split("\\s+")[0].split("/")[1].trim());
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferRed != null)
					bufferRed.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
	System.out.println("Inport"+inportVariablesNames.toString());
	System.out.println("Outport"+outportVariablesNames.toString());

	modelVariable.add(inportVariablesNames);
	modelVariable.add(outportVariablesNames);
	//modelVariable.add
		return modelVariable;
	}

	// Analyse and extract information from model : Data Type and Port Dimensions
        // we just analyse inport variable
        // output vector0 : inport Type 
        //        vector1 : inport dimensions
        @Override
	public ArrayList<ArrayList<String>> modelVariablesSpecifications(String modelPath){
		ArrayList<ArrayList<String>> modelVariable = new ArrayList<ArrayList<String>>();
		
                ArrayList<String> inportVariablesTypes = new ArrayList<String>();
		ArrayList<String> inportVariablesDimensions = new ArrayList<String>();
                
                ArrayList<String> outportVariablesTypes = new ArrayList<String>();
		ArrayList<String> outportVariablesDimensions = new ArrayList<String>();
                
		
		System.out.println("begin f2");
		
                String script="";
		int indexIterator=0;
                String EE="EE={"; // matrice contenant le type et la dimension
		ArrayList<String> nomVariableInport= modelVariablesInportOutport(modelPath).get(0);
		script="load_system('"+modelPath+"');\n";
		for (Iterator<String> it = nomVariableInport.iterator(); it.hasNext();) {
			String elementIterator= it.next();
			//lecture de chaque varible d'entré et définition de data type et dimension
			script=script+"t"+indexIterator+"=get_param('"+getModelName(modelPath)+"/"+elementIterator+"','DataType');\n"
					+ "d"+indexIterator+"=get_param('"+getModelName(modelPath)+"/"+elementIterator+"','PortDimensions');\n";
			
			EE=EE+"t"+indexIterator+","+"d"+indexIterator+";";	
			indexIterator++;
		}
		EE= EE.substring(0, EE.length()-1)+"}";
		script=script+EE+"\n"
				+ "fileID = fopen('"+System.getProperty("user.dir")+"\\tmp\\VariableTypeDimension.txt','w');\n"
				+ "formatSpec = '%s %s \\n';\n"
				+ "[nrows,ncols] = size(EE);\n"
				+ "for row = 1:nrows fprintf(fileID,formatSpec,EE{row,:}); end \n"
				+ "fclose(fileID); ";
		
		
		System.out.println(script);
		File modelVariablesSpecificationsFile= new File(System.getProperty("user.dir")+"\\tmp\\modelVariablesSpecifications.m");
		writeInFile(script, modelVariablesSpecificationsFile );
		try {
			runScriptMatlab(modelVariablesSpecificationsFile);
		} catch (MatlabInvocationException e) {
			e.printStackTrace();
		} catch (MatlabConnectionException e) {
			e.printStackTrace();
		}
		// jusqu'ici obtient un file txt contenant le type de chaque variable inport et le port dimensions 
		
		// now Read txt VariableTypeDimensions file
		
               
                BufferedReader bufferRed = null;
		String currentLine = "";
		FileReader fileReader = null;
                
                
		try {
			fileReader = new FileReader(System.getProperty("user.dir")+ "\\tmp\\VariableTypeDimension.txt");

			bufferRed = new BufferedReader(fileReader);
			while ((currentLine = bufferRed.readLine()) != null) {
				System.out.println(" current line 0"+currentLine.split("\\s+")[0].trim());
				System.out.println(" current line 1"+currentLine.split("\\s+")[1].trim());
				if(currentLine.split("\\s+")[0].trim().equals("auto"))
					inportVariablesTypes.add("double");
				else
					inportVariablesTypes.add(currentLine.split("\\s+")[0].trim());
				
				if(currentLine.split("\\s+")[1].trim().equals("-1"))
					inportVariablesDimensions.add("1");
				else
					inportVariablesDimensions.add(currentLine.split("\\s+")[1]);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferRed != null)
					bufferRed.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		System.out.println("Type ="+inportVariablesTypes.toString());
		System.out.println("Dimensions ="+inportVariablesDimensions .toString());

		//OutportVariable Type & Dimension 
                
                String script2="";
		int indexIterator2=0;
		String EE2="EE={"; // matrice contenant le type et la dimension
		ArrayList<String> nomVariableOutport= modelVariablesInportOutport(modelPath).get(1);
		script2="load_system('"+modelPath+"');\n";
		for (Iterator<String> it = nomVariableOutport.iterator(); it.hasNext();) {
			String elementIterator= it.next();
			//lecture de chaque varible d'entré et définition de data type et dimension
			script2=script2+"t"+indexIterator2+"=get_param('"+getModelName(modelPath)+"/"+elementIterator+"','DataType');\n"
					+ "d"+indexIterator2+"=get_param('"+getModelName(modelPath)+"/"+elementIterator+"','PortDimensions');\n";
			
			EE2=EE2+"t"+indexIterator2+","+"d"+indexIterator2+";";	
			indexIterator2++;
		}
		EE2= EE2.substring(0, EE2.length()-1)+"}";
		script2=script2+EE2+"\n"
				+ "fileID = fopen('"+System.getProperty("user.dir")+"\\tmp\\VariableTypeDimensionOutport.txt','w');\n"
				+ "formatSpec = '%s %s \\n';\n"
				+ "[nrows,ncols] = size(EE);\n"
				+ "for row = 1:nrows fprintf(fileID,formatSpec,EE{row,:}); end \n"
				+ "fclose(fileID); ";
		
		
		System.out.println(script2);
		File modelVariablesSpecificationsOutportFile= new File(System.getProperty("user.dir")+"\\tmp\\modelVariablesSpecificationsOutport.m");
		writeInFile(script2, modelVariablesSpecificationsOutportFile );
		try {
			runScriptMatlab(modelVariablesSpecificationsOutportFile);
		} catch (MatlabInvocationException e) {
			e.printStackTrace();
		} catch (MatlabConnectionException e) {
			e.printStackTrace();
		}
		// jusqu'ici obtient un file txt contenant le type de chaque variable inport et le port dimensions 
		
		// now Read txt VariableTypeDimensions file
		BufferedReader bufferRed2 = null;
		String currentLine2 = "";
		FileReader fileReader2 = null;
		try {
			fileReader2 = new FileReader(System.getProperty("user.dir")+ "\\tmp\\VariableTypeDimensionOutport.txt");

			bufferRed2 = new BufferedReader(fileReader2);
			while ((currentLine2 = bufferRed2.readLine()) != null) {
				System.out.println(" current line 0"+currentLine2.split("\\s+")[0].trim());
				System.out.println(" current line 1"+currentLine2.split("\\s+")[1].trim());
				if(currentLine2.split("\\s+")[0].trim().equals("auto"))
					outportVariablesTypes.add("double");
				else
					outportVariablesTypes.add(currentLine2.split("\\s+")[0].trim());
				
				if(currentLine2.split("\\s+")[1].trim().equals("-1"))
					outportVariablesDimensions.add("1");
				else
					outportVariablesDimensions.add(currentLine2.split("\\s+")[1]);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferRed2 != null)
					bufferRed2.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		System.out.println("Type outport="+outportVariablesTypes.toString());
		System.out.println("Dimensions outport="+outportVariablesDimensions .toString());
                
                
                
                // here we need to add outport
		modelVariable.add(inportVariablesTypes);
		modelVariable.add(inportVariablesDimensions);
		//outport
                modelVariable.add(outportVariablesTypes);
		modelVariable.add(outportVariablesDimensions);
			
            
            return modelVariable;
	}
	
	@Override
	public File expectedOutputScript(File scriptFile, String testCasesFileXlsPath, String modelPath) throws IOException{
		
		// Find last row number of file testCasesFileXls
            
		InputStream myxls = new FileInputStream(testCasesFileXlsPath );
		HSSFWorkbook book = new HSSFWorkbook(myxls);
		HSSFSheet sheet = book.getSheetAt(0);
		int noOfRows =sheet.getLastRowNum();
		
		
		//Find number of columns 
		int noOfColumns = sheet.getRow(0).getPhysicalNumberOfCells(); 
		
		// Find name of variable in xls file   remplacer par:  read variables from Model
		ArrayList<String> nomVariable = modelVariablesInportOutport(modelPath).get(0);
		
//		ArrayList<String> nomVariable = new ArrayList<String>();                 //maybe for test 
//		for (int colIndex = 0; colIndex < noOfColumns; colIndex++) {
//			nomVariable.add(sheet.getRow(0).getCell(colIndex).getStringCellValue()); 
//			System.out.println(" name ::"+sheet.getRow(0).getCell(colIndex).getStringCellValue());
//		}
		
		// read test cases file
		
		//Model name like M1
		String modelName= getModelName(modelPath);

		// find type of variable in mdl  create script read the model and write the type of blocks in file .txt
//		String scriptType = "load_system('"+modelPath+"');\n";
//		int indexIterator=0;
//		String outputTypeFilePath= modelPath.split("\\.")[0]+"_Type.txt";
//		scriptType= scriptType+"fid = fopen('"+outputTypeFilePath+"','wt');";
//		for (Iterator<String> it = nomVariable.iterator(); it.hasNext();) {
//			//System.out.println("Item inputs is: " + it.next());
//			scriptType= scriptType+"block_params = get_param('"+modelName+"/"+it.next()+"','DialogParameters'); \n "
//					+ "t"+indexIterator+"= block_params.OutputFunctionCall.Type ; \n"
//							+ "fprintf(fid, '%s', t"+indexIterator+",' ');\n" ;
//			indexIterator++;	
//		}
//		scriptType= scriptType+"fclose(fid);";    // C FAUX :(
		

//		fid = fopen('C:\Hanen\MesTests\M1_ExpectedOutputType2.txt','wt');
//		fprintf(fid, '%s\n%s\n%s', t0, t1, t2);
//		fclose(fid);
//		
		
//		System.out.println("scriptType :"+scriptType);
		
		
		//Create file writer and write the script .m
		String script = "load_system('"+modelPath+"');\n"+
				"input.time=(1:1:"+noOfRows+")';\n"; //First line precise the variable "input"
		
		
		// lire les types de variables from txt file 
//		BufferedReader bufferRed=null;
//		String type="";
//		FileReader fileReader=new FileReader("C:\\Hanen\\MesTests\\Model_varType.txt");  // model test a modify, shoud be dynamically
//		bufferRed = new BufferedReader(fileReader);
//		ArrayList<String> arraytype= new ArrayList<String>();
//		while ((type = bufferRed.readLine()) != null) {
//			arraytype.add(type);
//		}
//		System.out.println(" arraty"+arraytype.get(0)+arraytype.get(1)+arraytype.get(2));
//		
		
		// Find types of variables from model
		ArrayList<String> typeVariables= modelVariablesSpecifications(modelPath).get(0);
		ArrayList<String> dimensionVariables= modelVariablesSpecifications(modelPath).get(1);
		
		//write test cases 
		for (int colIndex = 0; colIndex < noOfColumns; colIndex++) {
			String testData=""; int verif=0; // pour ne pas repeter les cas de tests tant le nbre de col
			for (int rowIndex = 1; rowIndex <= noOfRows; rowIndex++) {
				
				HSSFRow row = sheet.getRow(rowIndex);
				
				if (row != null) {
					String key="";
					HSSFCell cell = row.getCell(colIndex);
					if (cell != null) {
						try{
							key = cell.getStringCellValue();
						}catch(java.lang.IllegalStateException e){
							key = String.valueOf(cell.getNumericCellValue());}
						testData= testData+key+";";
					}
				}
			}
			
			if (verif== 0){
				System.out.println("testData =" + testData);
					int j=colIndex+1;
					
					script=script+"input.signals("+j+").dimensions="+dimensionVariables.get(colIndex)+";"
							+ "input.signals("+j+").values="+typeVariables.get(colIndex)+"(["+testData+"]);\n";  // boolean a modifier
					verif= 1;
			}
		}
		// Expected Output file
		String outputFilePath= System.getProperty("user.dir")+ "\\tmp\\"+ modelName+"_ExpectedOutput.txt";
		
		//String modelName=modelPath.split("\\.")[0]; 
		script= script+"options=simset('MaxStep',1,'InitialStep',1,'FixedStep',1); \n"
						+ "[t,x,y]= sim('"+modelName+"',[1 "+noOfRows+"], options, input);\n"
						+ "whos;\n"
						+ " A = [t, x, y];\n"
						+ "fileID = fopen('"+outputFilePath+"','w'); \n"
						+ "[nrows,ncols] = size(y); \n"
						+ "formatSpec ='';\n"
						+ "for row = 1:ncols formatSpec =strcat(formatSpec,' %f'); end \n"
						+ "formatSpec =strcat(formatSpec,' \\n'); \n"
						+ "for row = 1:nrows fprintf(fileID,formatSpec,y(row,:)); end \n"
						+ "fclose(fileID); \n";
						//+ "save "+outputFilePath+" A -ASCII";
		 
        writeInFile(script, scriptFile);
        
        return scriptFile;
	}
	
	@Override
	public void runScriptMatlab(File scriptFile) throws MatlabInvocationException, MatlabConnectionException{
            System.out.println("    Begin runScriptMatlab !");
            StartMatlabProxy matlab = new StartMatlabProxy();
		if (!matlab.proxy.isConnected()) {
			matlab = new StartMatlabProxy();
		}
		//Buffer Reader for reading the scriptFile
		BufferedReader br = null;
		try {
			String sCurrentLine;
			FileReader fileReader=new FileReader(scriptFile);
			br = new BufferedReader(fileReader);
			while ((sCurrentLine = br.readLine()) != null) {
				matlab.proxy.eval(sCurrentLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
		System.out.println("Fin runScript");
	}
	
	
	
	public static void main(String []arg){
		SimulateModel m = new SimulateModel();
		try {
                    System.out.println("    user.dir "+System.getProperty("user.dir"));
                    String pathFile = System.getProperty("user.dir")+ "\\tmp\\F_ExpectedOutput.m"; //"C:\\Hanen\\MesTests\\scriptFile_F_coverageReport.m";
                    File scriptFile= m.createScriptFile(pathFile);
                    
                    File f =m.expectedOutputScript(scriptFile, "C:\\Users\\Molka\\Desktop\\Projet PFE\\Code Hanen\\modelBasedTesting_19_08\\modelBasedTesting\\integration_testCase.xls", "C:\\Modèles_Valeo_à_tester\\Detect.mdl");
                    m.runScriptMatlab(f);
                        //File f =m.ExpectedOutputScript(scriptFile, "C:\\Hanen\\MesTests\\M1.xls", "C:\\Hanen\\MesTests\\M1.mdl");
		 	//File f =m.ExpectedOutputScript(scriptFile, "C:\\Hanen\\MesTests\\M1.xls", "C:\\Hanen\\MesTests\\TestSubsystem\\M1_subsystem.mdl");
                        //File f =m.ExpectedOutputScript(scriptFile, "C:\\Hanen\\PFE_Telnet\\MesTests\\F.xls", "C:\\Hanen\\PFE_Telnet\\MesTests\\F.mdl");
		 	//m.runScriptMatlab(f);
		 	//File f =m.coverageReportScript(scriptFile, "C:\\Hanen\\MesTests\\F.xls", "C:\\Hanen\\MesTests\\F.mdl");
		 	
		 	//m.xlsTocsv(new File("C:\\Hanen\\MesTests\\F_final.xls"),new File("C:\\Hanen\\MesTests\\F_final.csv"));
			
		 //	m.runScriptMatlab(f);
		// 	ArrayList<ArrayList<String>> mm= m.modelVariablesSpecifications("C:\\hanen\\PFE_Telnet\\MesTests\\M1.mdl");
		// m.runScriptMatlab(new File("C:\\Users\\TELNET_INN\\workspace\\DriverTest\\tmp\\modelVariables.m"));		
		 	
			System.out.println("fin Main");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MatlabInvocationException e) {
			e.printStackTrace();
		} catch (MatlabConnectionException e) {
			e.printStackTrace();
		
		}
		
		
	}

	}
	

	


