package com.bergcomputers.rest.activator;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/rest")
public class JaxRsActivator extends Application{
	public JaxRsActivator(){
		super();
	}
}
