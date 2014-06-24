package com.bergcomputers.domain;


public interface IBeneficiary {

	public static final String iban = new String("iban");
	public static final String name = new String("name");
	public static final String details = new String("details");
	public static final String accountholder = new String("accountHolder");

	public abstract String getIban();

	public abstract void setIban(String iban);

	public abstract String getName();

	public abstract void setName(String name);

	public abstract String getDetails();

	public abstract void setDetails(String details);

	public abstract String getAccountHolder();

	public abstract void setAccountHolder(String accountholder);
}
