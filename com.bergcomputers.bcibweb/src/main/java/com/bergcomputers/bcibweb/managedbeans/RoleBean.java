package com.bergcomputers.bcibweb.managedbeans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import com.bergcomputers.bcibweb.config.Config;
import com.bergcomputers.bcibweb.delegate.Customers;
import com.bergcomputers.bcibweb.delegate.CustomersDelegate;
import com.bergcomputers.bcibweb.delegate.Role;
import com.bergcomputers.bcibweb.delegate.RoleDelegate;

@ManagedBean
@RequestScoped
public class RoleBean extends BaseBean{


	@Inject FacesContext fc;

	public RoleBean(){
		super();
	}
	public List<Role> getRole() throws Exception{
		return new RoleDelegate(Config.REST_SERVICE_BASE_URL).getRole();
	}

}
