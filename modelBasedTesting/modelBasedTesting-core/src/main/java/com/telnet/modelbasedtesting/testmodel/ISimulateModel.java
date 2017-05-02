package com.telnet.modelbasedtesting.testmodel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import matlabcontrol.MatlabConnectionException;
import matlabcontrol.MatlabInvocationException;

public interface ISimulateModel {

	public String getModelName(String modelPath);
        
	public File createScriptFile(String pathFile) throws IOException;
        
	public void writeInFile(String script, File scriptFile);
        
	public ArrayList<ArrayList<String>> modelVariablesInportOutport(String modelPath);
        
        public ArrayList<ArrayList<String>> modelVariablesSpecifications(String modelPath);
        
	public File coverageReportScript(File scriptFile, String testCasesFileXlsPath, String modelPath);
	
        public File expectedOutputScript(File scriptFile, String testCasesFile, String modelName) throws IOException;
	
	public void runScriptMatlab(File scriptFile) throws MatlabInvocationException, MatlabConnectionException;
		
}
