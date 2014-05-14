package com.bergcomputers.bcibweb.managedbeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name = "transactions", eager = true)
@RequestScoped
public class TransactionsBean extends BaseBean {
	public TransactionsBean() {
		super();
	}

	public String getMessage() {
		return "Hello World!";
	}
}
