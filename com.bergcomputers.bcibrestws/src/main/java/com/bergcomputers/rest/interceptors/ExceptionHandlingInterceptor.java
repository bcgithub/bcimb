package com.bergcomputers.rest.interceptors;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.ws.rs.core.Response;

import com.bergcomputers.rest.exception.BaseException;
import com.bergcomputers.rest.exception.ErrorInfo;
import com.bergcomputers.rest.exception.InvalidServiceArgumentException;
import com.bergcomputers.rest.exception.ResourceNotFoundException;

public class ExceptionHandlingInterceptor {
	private static final Logger log = Logger
			.getLogger(ExceptionHandlingInterceptor.class.getName());

	public ExceptionHandlingInterceptor() {
		// TODO Auto-generated constructor stub
	}

	@AroundInvoke
	public Object interceptRestInvocation(InvocationContext ctx)
			throws Exception {
		Object[] parameters = ctx.getParameters();
		if (null !=parameters){ 
			/*if 
			String param = (String) parameters[0];
			param = param.toLowerCase();
			parameters[0] = param;*/
			ctx.setParameters(parameters);
			log.info("Parameters:"+parameters);
		}
		
		ErrorInfo errorInfo = null;
		String url="url";
		Response.Status httpStatus = Response.Status.INTERNAL_SERVER_ERROR;
		try {
			return ctx.proceed();
		} catch (InvalidServiceArgumentException e) {
			log.log(Level.SEVERE, e.getMessage(), e);
			switch(e.getErrorCode()){
			case BaseException.CUSTOMER_ID_REQUIRED_CODE:{
				url = "http://localhost:8080/bcibws/rest/customers/null";
				httpStatus = Response.Status.BAD_REQUEST;
				break;
			}case BaseException.CUSTOMER_NOT_FOUND_CODE:{
				url = "http://localhost:8080/bcibws/rest/customers/{customerid}";
				httpStatus = Response.Status.NOT_FOUND;
				break;
			}
			default:{
				
			}
			
			}
			errorInfo = new ErrorInfo(url, e.getMessage(), e.getErrorCode()
					.toString(), e.getMessage());
			 return Response.status(httpStatus).entity(errorInfo)
		                .build();
		} catch (ResourceNotFoundException e) {
			log.log(Level.SEVERE, e.getMessage(), e);
			httpStatus = Response.Status.NOT_FOUND;
			errorInfo = new ErrorInfo(url,e.getMessage(), e.getErrorCode()
					.toString(), e.getMessage());
			return Response.status(httpStatus).entity(errorInfo)
	                .build();

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage(), e);
			errorInfo =  new ErrorInfo(url,"Unexpected exception", String.valueOf(BaseException.UNEXPECTED_CODE), e.getMessage());
			return Response.status(httpStatus).entity(errorInfo)
            .build();
		}
	}
}
