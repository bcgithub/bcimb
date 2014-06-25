package com.bergcomputers.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity implementation class for Entity: Account
 *
 */
@Entity
@Table(name = "CUSTOMER")
@XmlRootElement
@NamedQuery(name=Customer.findAll,query="SELECT a from Customer a")

public class Customer extends BaseEntity implements Serializable, ICustomer{
	private static final long serialVersionUID = -7944505705705785135L;
	public final static String findAll = "com.bergcomputers.domain.customer.findAll";



	private String firstName;
	private String lastName;
	private String login;
	private String password;

	@ManyToOne
	@JoinColumn(name="ROLEID")
	private Role role;

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Customer(){
		super();
	}

	/* (non-Javadoc)
	 * @see com.bergcomputers.domain.ICustomer#getFirstName()
	 */
	@Override
	public  String getFirstName(){
		return this.firstName;
	}

	/* (non-Javadoc)
	 * @see com.bergcomputers.domain.ICustomer#setFirstName(java.lang.String)
	 */
	@Override
	public  void setFirstName(String firstName){
		this.firstName=firstName;
	}

	/* (non-Javadoc)
	 * @see com.bergcomputers.domain.ICustomer#getLastName()
	 */
	@Override
	public  String getLastName(){
		return this.lastName;
	}

	/* (non-Javadoc)
	 * @see com.bergcomputers.domain.ICustomer#setLastName(java.lang.String)
	 */
	@Override
	public  void setLastName(String lastName){
		this.lastName = lastName;

	}

	/* (non-Javadoc)
	 * @see com.bergcomputers.domain.ICustomer#getLogin()
	 */
	@Override
	public  String getLogin(){
		return this.login;
	}

	/* (non-Javadoc)
	 * @see com.bergcomputers.domain.ICustomer#setLogin(java.lang.String)
	 */
	@Override
	public  void setLogin(String login){
		this.login = login;
	}

	/* (non-Javadoc)
	 * @see com.bergcomputers.domain.ICustomer#getPassword()
	 */
	@Override
	public  String getPassword(){
		return this.password;
	}

	/* (non-Javadoc)
	 * @see com.bergcomputers.domain.ICustomer#setPassword(java.lang.String)
	 */
	@Override
	public  void setPassword(String password){
		this.password = password;
	}

	@Override
	public String toString() {
		return "Customer [firstName=" + firstName + ", lastName=" + lastName
				+ ", login=" + login + ", role=" + role + "]";
	}

}
