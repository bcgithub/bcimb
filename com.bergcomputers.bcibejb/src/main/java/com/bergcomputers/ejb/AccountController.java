package com.bergcomputers.ejb;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.bergcomputers.domain.Account;
import com.bergcomputers.domain.Currency;
import com.bergcomputers.domain.Customer;
import com.bergcomputers.domain.Transaction;
//import com.sun.jersey.api.client.WebResource;

/**
 * Session Bean implementation class AccountController
 */
@Stateless
public class AccountController implements IAccountController {

	@PersistenceContext
	EntityManager  em;
    /**
     * Default constructor.
     */
    public AccountController() {
        // TODO Auto-generated constructor stub
    }

	@PostConstruct
	public void init(){
	}

	@Override
	public Account create(Account account) {
		if (null != account && null == account.getCreationDate()){
			account.setCreationDate(new Date());
		}
		account= em.merge(account);
		this.em.persist(account);
		em.flush();
		return account;
	}

	/* (non-Javadoc)
	 * @see com.bergcomputers.ejb.IAccountController#save(com.bergcomputers.domain.Account)
	 */
	@Override
	public void save(Account account)
	{
			this.em.merge(account);
	}
	
	@Override
	 public Account update (Account account){
	  Account acc = (Account)em.find(Account.class ,account.getId());
	  Customer customer=new Customer();
	  Currency currency=new Currency();
	  if(account.getCustomer()!=null)
	  customer = (Customer)em.find(Customer.class, account.getCustomer().getId());
	  
	  if(account.getCurrency()!=null){
	  currency = (Currency)em.find(Currency.class, account.getCurrency().getId());
	   System.out.println("marco:"+currency.getId());
	  }
	  acc.setAmount(account.getAmount());
	     acc.setCreationDate(account.getCreationDate() == null ? new Date() : account.getCreationDate() );
	     acc.setCurrency(currency);
	     acc.setCustomer(customer);
	     acc.setDeleted(account.getDeleted());
	     acc.setIban(account.getIban());
	     return acc;
	 }

	/* (non-Javadoc)
	 * @see com.bergcomputers.ejb.IAccountController#findAccount(long)
	 */
	@Override
	public Account findAccount(long id)
	{
		return this.em.find(Account.class, id);
	}

	/* (non-Javadoc)
	 * @see com.bergcomputers.ejb.IAccountController#getAccounts()
	 */
	@Override
	public List<Account> getAccounts()
	{
		return this.em.createNamedQuery(Account.findAll).getResultList();
	}
	
	


	/* (non-Javadoc)
	 * @see com.bergcomputers.ejb.IAccountController#delete(long)
	 */
	@Override
	public void delete(long accountid)
	{

		Account item = findAccount(accountid);
		if (item != null)
		{
			em.remove(item);
		}
	}


}
