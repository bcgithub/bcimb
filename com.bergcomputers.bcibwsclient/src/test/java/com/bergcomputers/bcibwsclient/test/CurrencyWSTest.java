package com.bergcomputers.bcibwsclient.test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;

import com.bergcomputers.domain.Currency;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

	public class CurrencyWSTest {
		final static String UrlBase = "http://localhost:"+"8080"+"/bcibws/rest/";
		private static WebResource wr = null;
		private static Client c = null;
		private DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");

		public static void setupClass(){
			ClientConfig cc = new DefaultClientConfig();
//			cc.getSingletons().add(jacksonJsonProvider);
			c = Client.create(cc);
			wr = c.resource(UrlBase);
		}
		
	    public static void cleanup(){
	        wr=null;
	        c=null;
	    }
	    @Test
	    public void findCurrency(){
	    	//creating a new currency
	    	Currency currency = new Currency();
	    	currency.setCreationDate(new Date());
	    	currency.setExchangerate(2D);
	    	currency.setId(2L);
	    	currency.setSymbol("EUR");
	    	currency.setVersion(1);
	    	
	    	//adding the new currency.
	    	wr.path("currency").type("application/json").put(Currency.class, currency);
	    	
	    	//getting the currency
	    	Currency currencyResult = wr.path("currency"+currency.getId()).type("application/json").get(Currency.class);
	    	
	    	//checking to see if the 2 currencies are the same
	    	Assert.assertEquals(currency.getId(), currencyResult.getId());
	    	Assert.assertEquals(currency.getVersion(), currencyResult.getVersion());
	    	Assert.assertEquals(currency.getExchangerate(), currencyResult.getExchangerate());
	    	Assert.assertTrue(currency.getCreationDate().equals(currencyResult.getCreationDate()));
	    	Assert.assertTrue(currency.getSymbol().equals(currencyResult.getSymbol()));
	    	
/*	    	//deleting the test currency
	    	
	    	wr.path("currency"+currency.getId()).type("application/json").delete();
*/
	    	
	    	
	    }
}
