package com.bergcomputers.bcibweb.delegate;

import com.sun.xml.rpc.processor.modeler.j2ee.xml.string;

public class Customers {
	private String name ;
	private String lastName;
	private String login;
	
	public Customers(){
		this.name="Ban";
		this.lastName="Dan";
		this.login="BanDan";
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

}
