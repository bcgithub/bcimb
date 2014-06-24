package com.bergcomputers.domain;

public interface ICustomer {

	String firstName = "firstName";
	String lastName = "lastName";
	String login = "login";
	public abstract String getFirstName();

	public abstract void setFirstName(String firstName);

	public abstract String getLastName();

	public abstract void setLastName(String lastName);

	public abstract String getLogin();

	public abstract void setLogin(String login);

	public abstract String getPassword();

	public abstract void setPassword(String password);
	Role getRole();
	void setRole(Role r);

}