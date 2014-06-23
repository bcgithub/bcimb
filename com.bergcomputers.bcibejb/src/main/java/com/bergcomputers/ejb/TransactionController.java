package com.bergcomputers.ejb;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.bergcomputers.domain.Transaction;

@Stateless
public class TransactionController implements ITransactionController {

	@PersistenceContext
	EntityManager em;
	
	public TransactionController(){
		
	}
	
	@PostConstruct
	public void init(){
		
	}
	
	@Override
	public Transaction create(Transaction transaction) {
		if (null != transaction && null == transaction.getTransactionDate()){
		return null;}
		this.em.persist(transaction);
		return transaction;
	}

	@Override
	public void save(Transaction transaction) {

			this.em.merge(transaction);
		
	}

	@Override
	public Transaction findTransaction(long id) {

		return this.em.find(Transaction.class, id);
	}

	@Override
	public List<Transaction> getTransactions() {
		
		return this.em.createNamedQuery(Transaction.findAll).getResultList();
	}

	@Override
	public void delete(long transactionid) {
		
		Transaction item = findTransaction(transactionid);
		if (item !=null)
		{
			em.remove(item);
		}
		
	}

}