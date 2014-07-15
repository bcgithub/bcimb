package com.bergcomputers.rest.exception;

public abstract class BaseException extends RuntimeException {
	
	public static final int BASE_CODE = 1000;
	public static final int UNEXPECTED_CODE = BASE_CODE + 1;
	public static final int CUSTOMER_ID_REQUIRED_CODE = BASE_CODE + 2;
	public static final int CUSTOMER_NOT_FOUND_CODE = BASE_CODE + 3;
	public static final int CUSTOMER_CREATE_NULL_ARGUMENT_CODE = BASE_CODE + 4;
	public static final int CUSTOMER_CREATE_NULL_ROLE_CODE = BASE_CODE + 5;
	public static final int ACCOUNT_ID_REQUIRED_CODE = BASE_CODE + 6;
	public static final int ACCOUNT_NOT_FOUND_CODE = BASE_CODE + 7;
	

	
	protected Integer errorCode;

	public BaseException() {
		// TODO Auto-generated constructor stub
	}

	public BaseException(String arg0,Integer errorCode) {
		super(arg0);
		this.errorCode = errorCode;
	}

	public BaseException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public BaseException(String arg0, Integer errorCode, Throwable arg1) {
		super(arg0, arg1);
		this.errorCode = errorCode;
	}

	

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

}
