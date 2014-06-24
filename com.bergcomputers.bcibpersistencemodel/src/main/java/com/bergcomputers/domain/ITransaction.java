package com.bergcomputers.domain;

import java.util.Date;

public interface ITransaction {
	public static final String date = new String("date");
	public static final String type = new String("type");
	public static final String amount = new String("amount");
	public static final String sender = new String("sender");
	public static final String details = new String("details");
	public static final String status = new String("status");
	public static final String account = new String("account");

	public abstract Account getAccount();

	public abstract void setAccount(Account account);

	public abstract Date getTransactionDate();

	public abstract void setTransactionDate(Date Date);

	public abstract String getType();

	public abstract void setType(String type);

	public abstract Double getAmount();

	public abstract void setAmount(Double amount);

	public abstract String getSender();

	public abstract void setSender(String sender);

	public abstract String getDetails();

	public abstract void setDetails(String details);

	public abstract String getStatus();

	public abstract void setStatus(String status);



}