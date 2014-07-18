package com.bergcomputers.rest.exception;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.bergcomputers.rest.interceptors.PerfLoggingInterceptor;

@Provider
public class RestExceptionHandler implements ExceptionMapper<Exception> {
	private static final Logger log = Logger
			.getLogger(PerfLoggingInterceptor.class.getName());

	@Context
	private UriInfo uriInfo;

	public RestExceptionHandler() {
		// TODO Auto-generated constructor stub
	}

	public Response toResponse(Exception exc) {
		
		Response.Status httpStatus = Response.Status.INTERNAL_SERVER_ERROR;
		ErrorInfo errorInfo = null;
		String url = uriInfo.getAbsolutePath().toString();
		log.log(Level.SEVERE, exc.getMessage(), exc);
		BaseException be  = null;
		if (exc instanceof BaseException) {
			be = ((BaseException) exc);
			if (be instanceof InvalidServiceArgumentException) {
				switch (be.getErrorCode()) {
				case BaseException.CUSTOMER_ID_REQUIRED_CODE: {
					httpStatus = Response.Status.BAD_REQUEST;
					break;
				}
				case BaseException.CUSTOMER_NOT_FOUND_CODE: {
					httpStatus = Response.Status.NOT_FOUND;
					break;
				}case BaseException.ACCOUNT_ID_REQUIRED_CODE:{
					url = "http://localhost:8080/bcibws/rest/accounts/null";
					httpStatus = Response.Status.BAD_REQUEST;
					break;
				}case BaseException.ACCOUNT_NOT_FOUND_CODE:{
					url = "http://localhost:8080/bcibws/rest/accounts/{customerid}";
					httpStatus = Response.Status.NOT_FOUND;
					break;
				}
				case BaseException.CUSTOMER_CREATE_NULL_ARGUMENT_CODE: {
					httpStatus = Response.Status.BAD_REQUEST;
					break;
				}
				case BaseException.CUSTOMER_UPDATE_NULL_ARGUMENT_CODE: {
					httpStatus = Response.Status.BAD_REQUEST;
					break;
				}
				default: {
	
				}
					errorInfo = new ErrorInfo(url, be.getMessage(), be.getErrorCode()
							.toString(), be.getMessage());
				}
			} else if (be instanceof ResourceNotFoundException) {
				log.log(Level.SEVERE, be.getMessage(), be);
				httpStatus = Response.Status.NOT_FOUND;
				
				switch (be.getErrorCode()) {
				case BaseException.CUSTOMER_NOT_FOUND_CODE: {
					httpStatus = Response.Status.NOT_FOUND;
					break;
				}
				case BaseException.CUSTOMER_CREATE_NULL_ROLE_CODE: {
					httpStatus = Response.Status.NOT_FOUND;
					break;
				}
				case BaseException.CUSTOMER_CREATE_NULL_ROLE_ID_CODE: {
					httpStatus = Response.Status.BAD_REQUEST;
					break;
				}
				case BaseException.CUSTOMER_UPDATE_NULL_ROLE_CODE: {
					httpStatus = Response.Status.NOT_FOUND;
					break;
				}
				case BaseException.CUSTOMER_UPDATE_NULL_ROLE_ID_CODE: {
					httpStatus = Response.Status.BAD_REQUEST;
					break;
				}
				case BaseException.CUSTOMER_CREATE_ROLE_ID_NOT_FOUND_CODE: {
					httpStatus = Response.Status.NOT_FOUND;
					break;
				}
				case BaseException.CUSTOMER_UPDATE_ROLE_ID_NOT_FOUND_CODE: {
					httpStatus = Response.Status.NOT_FOUND;
					break;
				}
				default: {
					break;
				}
				}
				errorInfo = new ErrorInfo(url, be.getMessage(), be.getErrorCode()
						.toString(), be.getMessage());
			}else{
				errorInfo = new ErrorInfo(url, "Unexpected exception",
						String.valueOf(BaseException.UNEXPECTED_CODE), be.getMessage());
			}
			return Response.status(httpStatus).entity(errorInfo).
					type(MediaType.APPLICATION_JSON).build();
		}else{ //if not base exception
			if (getCause(exc, ConstraintViolationException.class) instanceof ConstraintViolationException){
				StringBuffer sb = new StringBuffer(); 
				ConstraintViolationException cex = (ConstraintViolationException)getCause(exc, ConstraintViolationException.class);
				Set<ConstraintViolation<?>> constraintViolations = cex.getConstraintViolations();
				for(ConstraintViolation cv:constraintViolations){
					sb.append(cv.getMessage()).append("\n");
				}
				errorInfo = new ErrorInfo(url, sb.toString(),
						String.valueOf(BaseException.UNEXPECTED_CODE), getCause(exc, ConstraintViolationException.class) .getMessage());
			}else{
			
			errorInfo = new ErrorInfo(url, "Unexpected exception",
					String.valueOf(BaseException.UNEXPECTED_CODE), exc.getMessage());
			}
			
		}
		return Response.status(httpStatus).entity(errorInfo).
				type(MediaType.APPLICATION_JSON).build();
		
		
	}
	
	private Throwable getCause(Throwable ex){
		Throwable res = ex;
		while (null != res.getCause()){
			res = res.getCause();
		}
		return res;
		
	}

	private Throwable getCause(Throwable ex, Class toFind){
		Throwable res = ex;
		if (res.getClass().getName().equals(toFind.getName())){
			return res;
		}
		while (null != res.getCause()){
			res = res.getCause();
			if (res.getClass().getName().equals(toFind.getName())){
				return res;
			}

		}
		return res;
		
	}
}
