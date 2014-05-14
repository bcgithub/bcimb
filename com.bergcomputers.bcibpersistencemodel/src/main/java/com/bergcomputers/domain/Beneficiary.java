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
 * Entity implementation class for Entity: Beneficiary
 *
 */
public class Beneficiary extends BaseEntity implements Serializable, IBeneficiary {
	
	public final static String findAll = "com.bergcomputers.beneficiary.findAll";
	
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
}
