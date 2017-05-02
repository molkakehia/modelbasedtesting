package com.telnet.modelbasedtesting.testmodel;

import matlabcontrol.MatlabConnectionException;
import matlabcontrol.MatlabInvocationException;
import matlabcontrol.MatlabProxy;
import matlabcontrol.MatlabProxyFactory;
import matlabcontrol.MatlabProxyFactoryOptions;

public class StartMatlabProxy {
	public static  MatlabProxy proxy;
	public StartMatlabProxy() throws MatlabConnectionException, MatlabInvocationException {
		MatlabProxyFactoryOptions options = new MatlabProxyFactoryOptions.Builder()
	    .setUsePreviouslyControlledSession(true)
	    .setHidden(true)
	    .setMatlabLocation(null).build(); 
	
	    MatlabProxyFactory factory = new MatlabProxyFactory(options);
	   proxy = factory.getProxy();
	   proxy.eval("");
	}
	
}