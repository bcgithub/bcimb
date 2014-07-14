package com.bergcomputers.rest.exception;

public class InvalidServiceArgumentException extends BaseException {

	public InvalidServiceArgumentException() {
		// TODO Auto-generated constructor stub
	}

	public InvalidServiceArgumentException(String arg0, Integer errorCode) {
		super(arg0, errorCode);
		// TODO Auto-generated constructor stub
	}

	public InvalidServiceArgumentException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public InvalidServiceArgumentException(String arg0, Integer errorCode,
			Throwable arg1) {
		super(arg0, errorCode, arg1);
		// TODO Auto-generated constructor stub
	}

}
