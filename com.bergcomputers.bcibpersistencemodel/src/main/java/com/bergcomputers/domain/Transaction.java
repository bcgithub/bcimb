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
 * Entity implementation class for Entity: Transaction
 *
 */

@Entity
@Table(name = "TRANSACTION")
@XmlRootElement
@NamedQuery(name=Transaction.findAll,query="SELECT t from Transaction t")
public class Transaction implements Serializable, ITransaction {
	
	private static final long serialVersionUID = -7944505705705785135L;
	public final static String findAll = "com.bergcomputers.transaction.findAll";
	
	private long accountid;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private long transactionid;
	
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	
	
	private String type;
	private Double amount;
	private String sender;
	private String details;
	private String status;
	
	public Transaction(){
		super();
	}
	
	@Override
	public long getTransactionId(){
		return this.transactionid;
	}
	
	@Override
	public void setTransactionId(Long transactionid){
		this.transactionid=transactionid;
	}
	
	@Override
	public long getAccountId(){
		// TODO Auto-generated method stub
				return this.accountid;
	}
	
	public void setAccountId(Long accountid){
		this.accountid=accountid;
	}
	
	@Override
	public Date getTransactionDate() {
		// TODO Auto-generated method stub
		return this.date;
	}
	@Override
	public void setTransactionDate(Date Date) {
		// TODO Auto-generated method stub
		this.date=date;
	}
	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return this.type;
	}
	@Override
	public void setType(String type) {
		// TODO Auto-generated method stub
		this.type=type;
	}
	@Override
	public Double getAmount() {
		// TODO Auto-generated method stub
		return this.amount;
	}
	@Override
	public void setAmount(Double amount) {
		// TODO Auto-generated method stub
		this.amount=amount;
	}
	@Override
	public String getSender() {
		// TODO Auto-generated method stub
		return this.sender;
	}
	@Override
	public void setSender(String sender) {
		// TODO Auto-generated method stub
		this.sender=sender;
	}
	@Override
	public String getDetails() {
		// TODO Auto-generated method stub
		return this.details;
	}
	@Override
	public void setDetails(String details) {
		// TODO Auto-generated method stub
		this.details=details;
	}
	@Override
	public String getStatus() {
		// TODO Auto-generated method stub
		return this.status;
	}
	@Override
	public void setStatus(String status) {
		// TODO Auto-generated method stub
		this.status=status;
	}
	
	
}
