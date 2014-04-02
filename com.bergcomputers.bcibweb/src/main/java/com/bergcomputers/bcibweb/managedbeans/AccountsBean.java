package com.bergcomputers.bcibweb.managedbeans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import com.bergcomputers.bcibweb.config.Config;
import com.bergcomputers.bcibweb.delegate.AccountsDelegate;
import com.bergcomputers.domain.Account;

@ManagedBean
@RequestScoped
public class AccountsBean extends BaseBean{


	@Inject FacesContext fc;

	public AccountsBean(){
		super();
	}
	public List<Account> getAccounts() throws Exception{
		return new AccountsDelegate(Config.REST_SERVICE_BASE_URL).getAccounts();
	}

}
