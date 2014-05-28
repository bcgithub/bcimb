package com.bergcomputers.ejb;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.bergcomputers.domain.Beneficiary;

/**
 * Session Bean implementation class AccountController
 */
@Stateless
public class BeneficiaryController implements IBeneficiaryController {
	@PersistenceContext
	EntityManager  em;
    /**
     * Default constructor.
     */
    public BeneficiaryController() {
        // TODO Auto-generated constructor stub
    }

	@PostConstruct
	public void init(){
	}

	@Override
	public Beneficiary create(Beneficiary beneficiary) {
		if (null != beneficiary && null == beneficiary.getCreationDate()){
			beneficiary.setCreationDate(new Date());
		}
		this.em.persist(beneficiary);
		return beneficiary;
	}

	/* (non-Javadoc)
	 * @see com.bergcomputers.ejb.IAccountController#save(com.bergcomputers.domain.Account)
	 */
	@Override
	public void save(Beneficiary beneficiary)
	{
			this.em.merge(beneficiary);
	}

	/* (non-Javadoc)
	 * @see com.bergcomputers.ejb.IAccountController#findAccount(long)
	 */
	@Override
	public Beneficiary findBeneficiary(long id)
	{
		return this.em.find(Beneficiary.class, id);
	}

	/* (non-Javadoc)
	 * @see com.bergcomputers.ejb.IAccountController#getAccounts()
	 */
	@Override
	public List<Beneficiary> getBeneficiary()
	{
		return this.em.createNamedQuery(Beneficiary.findAll).getResultList();
	}


	/* (non-Javadoc)
	 * @see com.bergcomputers.ejb.IAccountController#delete(long)
	 */
	@Override
	public void delete(long beneficiaryid)
	{
		Beneficiary item = findBeneficiary(beneficiaryid);
		if (item != null)
		{
			em.remove(item);
		}
	}
}
