package com.bergcomputers.ejb;

import java.util.List;

import javax.ejb.Remote;

import com.bergcomputers.domain.Account;

@Remote
public interface IAccountController {

	public abstract Account create(Account account);
	public abstract void save(Account account);

	public abstract Account findAccount(long id);

	public abstract List<Account> getAccounts();

	public abstract void delete(long accountid);
	
	public abstract Account update(Account account);

}