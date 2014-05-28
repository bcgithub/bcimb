package com.bergcomputers.domain;


public interface IAccount {
	public static final String id = new String("id");
	public static final String amount = new String("amount");
	public static final String creationDate = new String("creationDate");
	public static final String iban = new String("iban");


	public abstract String getIban();

	public abstract void setIban(String iban);

	public abstract Double getAmount();

	public abstract void setAmount(Double amount);
}