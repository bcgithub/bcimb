package com.bergcomputers.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity implementation class for Entity: Beneficiary
 *
 */
@Entity
@Table(name = "BENEFICIARY")
@XmlRootElement
@NamedQuery(name=Beneficiary.findAll,query="SELECT a from Beneficiary a")
public class Beneficiary extends BaseEntity implements Serializable, IBeneficiary {

	public final static String findAll = "com.bergcomputers.domain.beneficiary.findAll";

	@NotNull
	@Size(min = 10, max = 10)
	private String iban;

	@NotNull
	private String name;

	@NotNull
	private String details;

	@NotNull
	private String accountholder;

	public Beneficiary()
	{
		super();
	}

	public String getIban()
	{
		return this.iban;
	}

	public void setIban(String iban)
	{
		this.iban=iban;
	}

	public String getName()
	{
		return this.name;
	}

	public void setName(String name)
	{
		this.name=name;
	}

	public String getDetails()
	{
		return this.details;
	}

	public void setDetails(String details)
	{
		this.details=details;
	}

	public String getAccountHolder()
	{
		return this.accountholder;
	}

	public void setAccountHolder(String accountholder)
	{
		this.accountholder=accountholder;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((accountholder == null) ? 0 : accountholder.hashCode());
		result = prime * result + ((details == null) ? 0 : details.hashCode());
		result = prime * result + ((iban == null) ? 0 : iban.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Beneficiary other = (Beneficiary) obj;
		if (accountholder == null) {
			if (other.accountholder != null)
				return false;
		} else if (!accountholder.equals(other.accountholder))
			return false;
		if (details == null) {
			if (other.details != null)
				return false;
		} else if (!details.equals(other.details))
			return false;
		if (iban == null) {
			if (other.iban != null)
				return false;
		} else if (!iban.equals(other.iban))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
