package com.bergcomputers.bcibweb.delegate;

import java.util.List;
import java.util.logging.Logger;

import com.bergcomputers.bcibweb.config.Config;
import com.bergcomputers.bciweb.data.mappers.CustomerMapper;
import com.bergcomputers.bciweb.data.mappers.IMapper;
import com.bergcomputers.domain.Customer;
public class CustomersDelegate extends BaseDelegate<Customer>{
	private final static Logger logger = Logger.getLogger(CustomersDelegate.class.getName());

	private IMapper<Customer> mapper;
	public CustomersDelegate(String baseUrl) {
		super(baseUrl);
	}

	public List<Customer> getCustomers() throws Exception{
		return getList(baseUrl + Config.REST_SERVICE_CUSTOMERS_LIST);
	}

	@Override
	public IMapper<Customer> getMapper() {
		if (null == mapper){
			mapper = new CustomerMapper();
		}
		return mapper;
	}
}
