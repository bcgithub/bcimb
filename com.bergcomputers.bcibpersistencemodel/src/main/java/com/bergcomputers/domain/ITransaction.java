package com.bergcomputers.domain;

import java.util.Date;

public interface ITransaction {
	public static final String date = new String("Date");
	public static final String type = new String("Type");
	public static final String amount = new String("Amount");
	public static final String sender = new String("Sender");
	public static final String details = new String("Details");
	public static final String status = new String("Status");
	public static final String accountid = new String("AccountID");
	public static final String transactionid = new String("TransactionID");
	
	public abstract long getTransactionId();
	
	public abstract void setTransactionId(Long transactionid);
	
	public abstract long getAccountId();
	
	public abstract void setAccountId(Long accountid);
	
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