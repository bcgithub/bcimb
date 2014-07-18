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
		BaseException e  = null;
		if (exc instanceof BaseException) {
			e = ((BaseException) exc);
			if (e instanceof InvalidServiceArgumentException) {
				switch (e.getErrorCode()) {
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
				default: {
	
				}
					errorInfo = new ErrorInfo(url, e.getMessage(), e.getErrorCode()
							.toString(), e.getMessage());
				}
			}
		} else if (e instanceof ResourceNotFoundException) {
			switch (e.getErrorCode()) {
			case BaseException.CUSTOMER_NOT_FOUND_CODE: {
				httpStatus = Response.Status.NOT_FOUND;
				errorInfo = new ErrorInfo(url, e.getMessage(), e.getErrorCode()
						.toString(), e.getMessage());
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
			case BaseException.TRANSACTION_UPDATE_NULL_ACCOUNT_ID_CODE: {
				httpStatus = Response.Status.BAD_REQUEST;
				break;
			}
			case BaseException.TRANSACTION_UPDATE_ACCOUNT_ID_NOT_FOUND_CODE: {
				httpStatus = Response.Status.NOT_FOUND;
				break;
			}
			case BaseException.TRANSACTION_NOT_FOUND_CODE: {
				httpStatus = Response.Status.NOT_FOUND;
				break;
			}
			case BaseException.CUSTOMER_UPDATE_ROLE_ID_NOT_FOUND_CODE: {
				httpStatus = Response.Status.NOT_FOUND;
				break;
			}
			case BaseException.TRANSACTION_ID_REQUIRED_CODE: {
				httpStatus = Response.Status.BAD_REQUEST;
				break;
			}			
			case BaseException.ACCOUNT_OF_TRANSACTION_NOT_FOUND: {
				httpStatus = Response.Status.BAD_REQUEST;
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
				break;
			}
			}
		}else{
		errorInfo = new ErrorInfo(url, "Unexpected exception",
				String.valueOf(BaseException.UNEXPECTED_CODE), e.getMessage());
		
		return Response.status(httpStatus).entity(errorInfo).
				type(MediaType.APPLICATION_JSON).build();
		}
		
		errorInfo = new ErrorInfo(url, e.getMessage(), e.getErrorCode()
				.toString(), e.getMessage());				
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
