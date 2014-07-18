package com.bergcomputers.rest.exception;

public abstract class BaseException extends RuntimeException {
	
	public static final int BASE_CODE = 1000;
	public static final int UNEXPECTED_CODE = BASE_CODE + 1;
	public static final int CUSTOMER_ID_REQUIRED_CODE = BASE_CODE + 2;
	public static final int CUSTOMER_NOT_FOUND_CODE = BASE_CODE + 3;
	public static final int CUSTOMER_CREATE_NULL_ARGUMENT_CODE = BASE_CODE + 4;
	public static final int CUSTOMER_CREATE_NULL_ROLE_CODE = BASE_CODE + 5;
	public static final int CUSTOMER_CREATE_NULL_ROLE_ID_CODE = BASE_CODE + 6;
	public static final int CUSTOMER_CREATE_ROLE_ID_NOT_FOUND_CODE = BASE_CODE + 7;
	public static final int CUSTOMER_UPDATE_NULL_ARGUMENT_CODE = BASE_CODE + 8;
	public static final int CUSTOMER_UPDATE_NULL_ROLE_CODE = BASE_CODE + 9;
	public static final int CUSTOMER_UPDATE_NULL_ROLE_ID_CODE = BASE_CODE + 10;
	public static final int CUSTOMER_UPDATE_ROLE_ID_NOT_FOUND_CODE = BASE_CODE + 11;
	public static final int ACCOUNT_ID_REQUIRED_CODE = BASE_CODE + 12;
	public static final int ACCOUNT_NOT_FOUND_CODE = BASE_CODE + 13;
	public static final int CUSTOMER_OF_ACCOUNT_NOT_FOUND = BASE_CODE + 14;
	public static final int CURRENCY_OF_ACCOUNT_NOT_FOUND = BASE_CODE + 15;
	public static final int ACCOUNT_OF_TRANSACTION_NOT_FOUND = BASE_CODE + 16;
	public static final int TRANSACTION_NOT_FOUND_CODE = BASE_CODE + 17;
	public static final int TRANSACTION_ID_REQUIRED_CODE = BASE_CODE + 18;
	public static final int TRANSACTION_CREATE_NULL_ACCOUNT_ID_CODE  = BASE_CODE + 19;
	public static final int TRANSACTION_UPDATE_NULL_ACCOUNT_ID_CODE = BASE_CODE + 20;
	public static final int TRANSACTION_UPDATE_ACCOUNT_ID_NOT_FOUND_CODE = BASE_CODE + 21;
	public static final int TRANSACTION_CREATE_ACCOUNT_ID_NOT_FOUND_CODE = BASE_CODE + 22;

	

	
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
