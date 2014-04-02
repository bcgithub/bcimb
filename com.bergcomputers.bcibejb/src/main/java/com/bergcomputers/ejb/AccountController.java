package com.bergcomputers.ejb;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.bergcomputers.domain.Account;

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
		this.em.persist(account);
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
