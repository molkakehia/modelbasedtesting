package com.telnet.modelbasedtesting.testcode;

public class Main {

	public static void main(String[] args) {
		String modelVariableNameScript="load_system('');\n"
				+ "blockPath = find_system('','Type','Block');\n"
						+ "blockTypes = get_param(blockPath,'BlockType');\n"
						+ "fileID = fopen('"+System.getProperty("user.dir")+"\\VariableNameType2.txt','w');\n"
						+ "formatSpec = '%s %s \\n';\n"
						+ "[nrows,ncols] = size(BB);\n"
						+ "for row = 1:nrows \n"
						+ "fprintf(fileID,formatSpec,BB{row,:});\n"
						+ "end \n"
						+ "fclose(fileID); \n";

		System.out.println("Working Directory = " +
	              System.getProperty("user.dir")+"\n"+modelVariableNameScript);

	}

} 
