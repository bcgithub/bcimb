package com.bergcomputers.ejb;

import java.util.List;

import javax.ejb.Remote;

import com.bergcomputers.domain.Transaction;

@Remote
public interface ITransactionController {

	public abstract Transaction create(Transaction transaction);
	public abstract Transaction save (Transaction transaction);
	
	public abstract Transaction findTransaction(long id);
	
	public abstract List<Transaction> getTransactions();
	
	public abstract void delete (long transactionid);
}
