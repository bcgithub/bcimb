package com.bergcomputers.bcibweb.managedbeans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.bergcomputers.bcibweb.config.Config;
import com.bergcomputers.bcibweb.delegate.BeneficiaryDelegate;
import com.bergcomputers.domain.Beneficiary;



@ManagedBean
@RequestScoped
public class BeneficiariesBean extends BaseBean{


	public BeneficiariesBean(){
		super();
	}

	public List<Beneficiary> getBeneficiaries() throws Exception{
		return new BeneficiaryDelegate(Config.REST_SERVICE_BASE_URL).getBeneficiaries();
	}
}
