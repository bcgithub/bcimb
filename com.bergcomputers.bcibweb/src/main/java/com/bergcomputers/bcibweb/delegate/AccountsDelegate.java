package com.bergcomputers.bcibweb.delegate;

import java.util.List;
import java.util.logging.Logger;

import com.bergcomputers.bcibweb.config.Config;
import com.bergcomputers.bciweb.data.mappers.AccountMapper;
import com.bergcomputers.bciweb.data.mappers.IMapper;
import com.bergcomputers.domain.Account;

public class AccountsDelegate extends BaseDelegate<Account>{
	private final static Logger logger = Logger.getLogger(AccountsDelegate.class.getName());

	private IMapper<Account> mapper;
	public AccountsDelegate(String baseUrl) {
		super(baseUrl);
	}

	public List<Account> getAccounts() throws Exception{
		return getList(baseUrl + Config.REST_SERVICE_ACCOUNTS_LIST);
	}

	@Override
	public IMapper<Account> getMapper() {
		if (null == mapper){
			mapper = new AccountMapper();
		}
		return mapper;
	}
}
