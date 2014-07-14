package com.bergcomputers.ejb;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.bergcomputers.domain.Account;
import com.bergcomputers.domain.Currency;
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
		transaction.setCreationDate(new Date());
		}
		transaction=em.merge(transaction);
		this.em.persist(transaction);
		em.flush();
		return transaction;
		}
	/*public Transacation create(int id,..){
	 Transaction tr = new Transaction();
	 tr.setId(id);
	 ...
	 em.persist(tr);
	 return tr;
	 }*/

	@Override
	public Transaction save(Transaction transaction) {
		Account acc = null;
		Transaction tr =(Transaction)em.find(Transaction.class, transaction.getId());
		 if(transaction.getAccount()!=null){
			 acc = (Account)em.find(Account.class, transaction.getAccount().getId());
			  }
		tr.setAccount(acc);
		tr.setAmount(transaction.getAmount());
        tr.setId(transaction.getId());
        tr.setDetails(transaction.getDetails());
        tr.setSender(transaction.getSender());
        tr.setStatus(transaction.getStatus());
        tr.setType(transaction.getType());
        tr.setTransactionDate(transaction.getTransactionDate());       
        tr.setDate(transaction.getDate());
        tr.setCreationDate(transaction.getCreationDate());
		tr=this.em.merge(tr);
		return tr;
		
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
		Transaction item = em.find(Transaction.class,transactionid);
		//Transaction item = findTransaction(transactionid);
		if (item !=null)
		{
			em.remove(item);
		
		}
		
	}

}
