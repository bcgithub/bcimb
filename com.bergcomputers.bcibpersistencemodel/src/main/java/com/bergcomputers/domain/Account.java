package com.bergcomputers.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity implementation class for Entity: Account
 *
 */
@Entity
@Table(name = "ACCOUNT")
@XmlRootElement
@NamedQuery(name=Account.findAll,query="SELECT a from Account a")
public class Account extends BaseEntity implements Serializable, IAccount {

	private static final long serialVersionUID = -7944505705705785135L;
	public final static String findAll = "com.bergcomputers.account.findAll";


	@NotNull
	@Size(min = 10, max = 10)
	private String iban;
	private Double amount;

	@ManyToOne
	@JoinColumn(name="CUSTOMERID")
	private Customer customer;

	@ManyToOne
	@JoinColumn(name="CURRENCYID")
	private Currency currency;

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Account() {
		super();
	}
	/* (non-Javadoc)
	 * @see com.bergcomputers.domain.IAccount#getId()
	 */

	@Override
	public String getIban() {
		return this.iban;
	}

	/* (non-Javadoc)
	 * @see com.bergcomputers.domain.IAccount#setIban(java.lang.String)
	 */
	@Override
	public void setIban(String iban) {
		this.iban = iban;
	}
	/* (non-Javadoc)
	 * @see com.bergcomputers.domain.IAccount#getAmount()
	 */
	@Override
	public Double getAmount() {
		return this.amount;
	}

	/* (non-Javadoc)
	 * @see com.bergcomputers.domain.IAccount#setAmount(java.lang.Double)
	 */
	@Override
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((iban == null) ? 0 : iban.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (iban == null) {
			if (other.iban != null)
				return false;
		} else if (!iban.equals(other.iban))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Account [id="+getId()+", iban=" + iban + ", amount=" + amount + ", version=" + getVersion() + ", creationDate=" + getCreationDate() + "]";
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}



}
