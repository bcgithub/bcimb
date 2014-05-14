package com.bergcomputers.bcibweb.managedbeans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;



@ManagedBean(name="beneficiaries",eager=true)
@RequestScoped
public class BeneficiariesBean extends BaseBean{


	public BeneficiariesBean(){
		super();
	}
	
	public String getMessage(){
		return "mesasafasfas";
	}

}
