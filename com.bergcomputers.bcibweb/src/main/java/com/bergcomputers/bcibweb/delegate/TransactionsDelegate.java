package com.bergcomputers.bcibweb.delegate;

import java.util.List;
import java.util.logging.Logger;

import com.bergcomputers.bcibweb.config.Config;
import com.bergcomputers.bciweb.data.mappers.IMapper;
import com.bergcomputers.bciweb.data.mappers.TransactionMapper;
import com.bergcomputers.domain.Transaction;

public class TransactionsDelegate extends BaseDelegate<Transaction>{
	private final static Logger logger = Logger.getLogger(TransactionsDelegate.class.getName());

	private IMapper<Transaction> mapper;
	public TransactionsDelegate(String baseUrl) {
		super(baseUrl);
	}

	public List<Transaction> getTransactions() throws Exception{
		return getList(baseUrl + Config.REST_SERVICE_TRANSACTIONS_LIST);
	}

	@Override
	public IMapper<Transaction> getMapper() {
		if (null == mapper){
			mapper = new TransactionMapper();
		}
		return mapper;
	}
}
