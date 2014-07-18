package com.bergcomputers.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.bergcomputers.domain.Currency;

@Stateless
public class CurrencyController implements ICurrencyController {
	
	@PersistenceContext
	EntityManager  em;
	public Currency findCurrency(long id)
	{
		return this.em.find(Currency.class, id);
	}
	
/*	@Override
	public void delete (long currencyid)
	{

		Currency item = findCurrency(currencyid);
		if (item != null)
		{
			em.remove(item);
		}
	}*/

}
