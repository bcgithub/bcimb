package com.bergcomputers.bcibweb.managedbeans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.bergcomputers.bcibweb.config.Config;
import com.bergcomputers.bcibweb.delegate.TransactionsDelegate;
import com.bergcomputers.domain.Transaction;

@ManagedBean
@RequestScoped
public class TransactionsBean extends BaseBean {
	public TransactionsBean() {
		super();
	}

	public List<Transaction> getTransactions() throws Exception{
		return new TransactionsDelegate(Config.REST_SERVICE_BASE_URL).getTransactions();
	}
}
