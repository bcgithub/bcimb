package com.bergcomputers.ejb;

import java.util.List;

import javax.ejb.Remote;

import com.bergcomputers.domain.Beneficiary;

@Remote
public interface IBeneficiaryController {
	public abstract Beneficiary create(Beneficiary beneficiary);
	public abstract void save(Beneficiary beneficiary);

	public abstract Beneficiary findBeneficiary(long id);

	public abstract List<Beneficiary> getBeneficiary();

	public abstract void delete(long accountid);

}
