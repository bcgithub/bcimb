package com.bergcomputers.bcibweb.managedbeans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import com.bergcomputers.bcibweb.config.Config;
import com.bergcomputers.bcibweb.delegate.Customers;
import com.bergcomputers.bcibweb.delegate.CustomersDelegate;
import com.bergcomputers.bcibweb.delegate.Device;
import com.bergcomputers.bcibweb.delegate.DeviceDelegate;
import com.bergcomputers.bcibweb.delegate.Role;
import com.bergcomputers.bcibweb.delegate.RoleDelegate;

@ManagedBean
@RequestScoped
public class DeviceBean extends BaseBean{


	@Inject FacesContext fc;

	public DeviceBean(){
		super();
	}
	public List<Device> getDevice() throws Exception{
		return new DeviceDelegate(Config.REST_SERVICE_BASE_URL).getDevice();
	}

}
