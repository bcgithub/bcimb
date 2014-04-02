package com.bergcomputers.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
public class Account implements Serializable, IAccount {

	private static final long serialVersionUID = -7944505705705785135L;
	public final static String findAll = "com.bergcomputers.account.findAll";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
	private Long id;

	@NotNull
	@Size(min = 10, max = 10)
	private String iban;
	private Double amount;

	@Version
    @Column(name = "VERSION")
    private Integer version;

    @NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;

	public Account() {
		super();
	}
	/* (non-Javadoc)
	 * @see com.bergcomputers.domain.IAccount#getId()
	 */
	@Override
	public Long getId() {
		return this.id;
	}

	/* (non-Javadoc)
	 * @see com.bergcomputers.domain.IAccount#setId(java.lang.Long)
	 */
	@Override
	public void setId(Long id) {
		this.id = id;
	}
	/* (non-Javadoc)
	 * @see com.bergcomputers.domain.IAccount#getIban()
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
	/* (non-Javadoc)
	 * @see com.bergcomputers.domain.IAccount#getCreationDate()
	 */
	@Override
	public Date getCreationDate() {
		return this.creationDate;
	}

	/* (non-Javadoc)
	 * @see com.bergcomputers.domain.IAccount#setCreationDate(java.util.Date)
	 */
	@Override
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		if (iban == null) {
			if (other.iban != null)
				return false;
		} else if (!iban.equals(other.iban))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Account [id=" + id + ", iban=" + iban + ", amount=" + amount
				+ ", version=" + version + ", creationDate=" + creationDate
				+ "]";
	}


}
