package com.bergcomputers.bcibweb.delegate;

import java.util.List;
import java.util.logging.Logger;

import com.bergcomputers.bcibweb.config.Config;
import com.bergcomputers.bciweb.data.mappers.BeneficiaryMapper;
import com.bergcomputers.bciweb.data.mappers.IMapper;
import com.bergcomputers.domain.Beneficiary;

public class BeneficiaryDelegate extends BaseDelegate<Beneficiary>{
	private final static Logger logger = Logger.getLogger(BeneficiaryDelegate.class.getName());

	private IMapper<Beneficiary> mapper;
	public BeneficiaryDelegate(String baseUrl) {
		super(baseUrl);
	}

	public List<Beneficiary> getBeneficiaries() throws Exception{
		return getList(baseUrl + Config.REST_SERVICE_BENEFICIARIES_LIST);
	}

	@Override
	public IMapper<Beneficiary> getMapper() {
		if (null == mapper){
			mapper = new BeneficiaryMapper();
		}
		return mapper;
	}
}
