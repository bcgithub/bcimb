package com.bergcomputers.rest.interceptors;

import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.ws.rs.core.Response;

import com.bergcomputers.rest.exception.BaseException;
import com.bergcomputers.rest.exception.ErrorInfo;
import com.bergcomputers.rest.exception.InvalidServiceArgumentException;
import com.bergcomputers.rest.exception.ResourceNotFoundException;

public class PerfLoggingInterceptor {
	private static final Logger log = Logger
			.getLogger(PerfLoggingInterceptor.class.getName());

	public PerfLoggingInterceptor() {
		// TODO Auto-generated constructor stub
	}

	@AroundInvoke
	public Object interceptRestInvocation(InvocationContext ctx)
			throws Exception {
		Object target = ctx.getTarget();
		Method method = ctx.getMethod();
		log.info("Method:" + method.getName());
		Object[] parameters = ctx.getParameters();
		if (null != parameters) {
			ctx.setParameters(parameters);
			log.info("Parameters:" + parameters);
		}
		long startTime = System.currentTimeMillis();
		try {
			return ctx.proceed();
		} finally {
			long duration = System.currentTimeMillis() - startTime;
			log.info(target.getClass().getSimpleName()+
					"."+method+"("+(parameters == null ? "":parameters)+"):"+(duration/1000)+"sec");
		}
	}
}
