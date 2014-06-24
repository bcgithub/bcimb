package com.bergcomputers.bcibweb.managedbeans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import com.bergcomputers.bcibweb.config.Config;
import com.bergcomputers.bcibweb.delegate.CustomersDelegate;
import com.bergcomputers.domain.Customer;

@ManagedBean
@RequestScoped
public class CustomersBean extends BaseBean{


	@Inject FacesContext fc;

	public CustomersBean(){
		super();
	}

	public List<Customer> getCustomers() throws Exception{
		return new CustomersDelegate(Config.REST_SERVICE_BASE_URL).getCustomers();
	}

}
