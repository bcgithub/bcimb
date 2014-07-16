package com.bergcomputers.ejb;

import com.bergcomputers.domain.Currency;

public interface ICurrencyController {

	public abstract Currency findCurrency(long id);
	
/*	public abstract void delete(long id);*/
}
