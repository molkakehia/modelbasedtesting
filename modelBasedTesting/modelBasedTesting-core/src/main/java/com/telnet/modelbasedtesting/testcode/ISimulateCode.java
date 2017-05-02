package com.telnet.modelbasedtesting.testcode;

import java.io.File;
import java.io.IOException;

public interface ISimulateCode {
	public File writeCodeTest(String codeCPath,String testCasesFileXlsPath) throws IOException;

}
