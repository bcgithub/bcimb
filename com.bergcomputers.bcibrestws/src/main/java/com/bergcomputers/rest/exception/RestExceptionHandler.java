package com.bergcomputers.rest.exception;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.bergcomputers.rest.interceptors.PerfLoggingInterceptor;

@Provider
public class RestExceptionHandler implements ExceptionMapper<BaseException> {
	private static final Logger log = Logger
			.getLogger(PerfLoggingInterceptor.class.getName());

	@Context
	private UriInfo uriInfo;

	public RestExceptionHandler() {
		// TODO Auto-generated constructor stub
	}

	public Response toResponse(BaseException e) {
		Response.Status httpStatus = Response.Status.INTERNAL_SERVER_ERROR;
		ErrorInfo errorInfo = null;
		String url = uriInfo.getAbsolutePath().toString();
		log.log(Level.SEVERE, e.getMessage(), e);

		if (e instanceof InvalidServiceArgumentException) {
			switch (e.getErrorCode()) {
			case BaseException.CUSTOMER_ID_REQUIRED_CODE: {
				httpStatus = Response.Status.BAD_REQUEST;
				break;
			}
			case BaseException.CUSTOMER_NOT_FOUND_CODE: {
				httpStatus = Response.Status.NOT_FOUND;
				break;
			}
			case BaseException.CUSTOMER_CREATE_NULL_ARGUMENT_CODE: {
				httpStatus = Response.Status.BAD_REQUEST;
				break;
			}
			default: {

			}
				errorInfo = new ErrorInfo(url, e.getMessage(), e.getErrorCode()
						.toString(), e.getMessage());
			}
		} else if (e instanceof ResourceNotFoundException) {
			log.log(Level.SEVERE, e.getMessage(), e);
			httpStatus = Response.Status.NOT_FOUND;
			errorInfo = new ErrorInfo(url, e.getMessage(), e.getErrorCode()
					.toString(), e.getMessage());
		}else
		errorInfo = new ErrorInfo(url, "Unexpected exception",
				String.valueOf(BaseException.UNEXPECTED_CODE), e.getMessage());
		return Response.status(httpStatus).entity(errorInfo).
				type(MediaType.APPLICATION_JSON).build();

	}

}
