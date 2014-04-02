package com.bergcomputers.bcibweb.managedbeans;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

public class BaseBean {

	//@Inject FacesContext fc;

	protected String getRequestURL()
	{
	    Object request = FacesContext.getCurrentInstance().getExternalContext().getRequest();
		//Object request = fc.getExternalContext().getRequest();
		if(request instanceof HttpServletRequest)
	    {
	            return ((HttpServletRequest) request).getRequestURL().toString();
	    }else
	    {
	        return "";
	    }
	}
}
