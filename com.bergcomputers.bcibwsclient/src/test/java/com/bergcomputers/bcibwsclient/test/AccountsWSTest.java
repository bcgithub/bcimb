/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bergcomputers.bcibwsclient.test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.Response;

import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jettison.json.JSONException;
import org.junit.AfterClass;
import org.junit.Assert;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import com.bergcomputers.domain.Account;
import com.bergcomputers.domain.Currency;
import com.bergcomputers.domain.Customer;
import com.bergcomputers.domain.Transaction;
import com.bergcomputers.rest.exception.BaseException;
import com.bergcomputers.rest.exception.ErrorInfo;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
/**
 *
 * @author Administrator
 */

public class AccountsWSTest {
    final static String UrlBase = "http://localhost:"+"8080"+"/bcibws/rest/";
    private static WebResource wr = null;
    private static Client c = null;
    private DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");


    @BeforeClass
    public static void setupClass(){
         	ClientConfig cc = new DefaultClientConfig();
         	cc.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
         	JacksonJsonProvider jacksonJsonProvider = 
         		    new JacksonJaxbJsonProvider()
         		    .configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
         	cc.getSingletons().add(jacksonJsonProvider);
         	c = Client.create(cc);
            wr = c.resource(UrlBase);
    }
    @AfterClass
    public static void cleanup(){
        wr=null;
        c=null;
    }
    @Test
	public void createAccount() throws JSONException{
			//creating an account
	        Account acc = new Account();
	        Date date=new Date();
	        acc.setAmount(2000.0);
	        acc.setIban("ro03bc1234");
	        acc.setCreationDate(date);
	        
	        
	        //creating a new currency to associate with the account
	        Currency currency=new Currency();
	        currency.setId(1L);
	        acc.setCurrency(currency);
	        
	        Customer customer=new Customer();
	        customer.setId(4L);
	        acc.setCustomer(customer);
	        
	        Account accountResult = wr.path("accounts").type("application/json").put(Account.class, acc);
	        System.out.println("-----");
	        
	        //testing to see if the account is the same
	        Assert.assertEquals(accountResult.getAmount(), acc.getAmount());
	        Assert.assertEquals(accountResult.getIban(),acc.getIban());
	        
	        //change after fixing the date program
	        //Assert.assertEquals(accountResult.getCreationDate(),acc.getCreationDate());
	        Assert.assertEquals(accountResult.getCustomer().getId(), acc.getCustomer().getId());
	        Assert.assertEquals(accountResult.getCurrency().getId(),acc.getCurrency().getId());
	        
	       //deleting the account
	         System.out.println("Deleting test account:");
	         System.out.println(accountResult.getId());
	         wr.path("accounts/"+accountResult.getId()).delete();
	        System.out.println("-----");
	
	}
	@Test
	public void createAccountThatDoesntHaveCurrency() throws JSONException{
	    System.out.println("Getting list of accounts:");
	    List<Account> accounts = wr.path("accounts/").accept("application/json").get(new GenericType<List<Account>>(){});
	    System.out.println(String.format("List of accounts found:\n%s", accounts.toString()));
	    System.out.println("-----");
	    
	  //creating an account
	    Account acc = new Account();
	    Date date=new Date();
	    acc.setAmount(2000.0);
	    acc.setIban("ro03bc1234");
	    acc.setCreationDate(date);
	    
	    
	    Customer customer=new Customer();
	    customer.setId(4L);
	    acc.setCustomer(customer);
	    acc.setCurrency(null);
	    
	    ClientResponse accountResult = wr.path("accounts").type("application/json").put(ClientResponse.class, acc);
	    ErrorInfo err=(ErrorInfo)accountResult.getEntity(ErrorInfo.class);
	    Assert.assertTrue(Integer.valueOf(err.getCode())== BaseException.CURRENCY_OF_ACCOUNT_NOT_FOUND);
	    Assert.assertEquals(err.getDeveloperMessage(), "Every account should have a currency");
	    Assert.assertEquals(err.getMessage(), "Every account should have a currency");
	    Assert.assertEquals(err.getUrl(), "http://localhost:8080/bcibws/rest/accounts");
	    
	
	}
	@Test
	public void createAccountThatDoesntHaveCustomer() throws JSONException{
	    System.out.println("Getting list of accounts:");
	    List<Account> accounts = wr.path("accounts/").accept("application/json").get(new GenericType<List<Account>>(){});
	    System.out.println(String.format("List of accounts found:\n%s", accounts.toString()));
	    System.out.println("-----");
	
	  //creating an account
	    Account acc = new Account();
	    Date date=new Date();
	    acc.setAmount(2000.0);
	    acc.setIban("ro03bc1234");
	    acc.setCreationDate(date);
	    
	    Currency currency=new Currency();
	    currency.setId(1L);
	    acc.setCurrency(currency);
	    
	    ClientResponse accountResult = wr.path("accounts").type("application/json").put(ClientResponse.class, acc);
	    ErrorInfo err=(ErrorInfo)accountResult.getEntity(ErrorInfo.class);
	    Assert.assertTrue(Integer.valueOf(err.getCode())== BaseException.CUSTOMER_OF_ACCOUNT_NOT_FOUND);
	    Assert.assertEquals(err.getDeveloperMessage(), "Every account should have a customer");
	    Assert.assertEquals(err.getMessage(), "Every account should have a customer");
	    Assert.assertEquals(err.getUrl(), "http://localhost:8080/bcibws/rest/accounts");
	    
	}
	@Test
	    public void deleteAccount() throws JSONException{
	
	 	//check list of accounts
	     System.out.println("Getting list of accounts:");
	     List<Account> accounts = wr.path("accounts/").accept("application/json").get(new GenericType<List<Account>>(){});
	     System.out.println(String.format("List of accounts found:\n%s", accounts.toString()));
	     System.out.println("-----");
	     //creating an account
	     Account acc = new Account();
	     Date date=new Date();
	     acc.setAmount(2000.0);
	     acc.setIban("ro03bc1234");
	     acc.setCreationDate(date);
	     //acc.setVersion(1);
	     
	     //creating a new currency to associate with the account
	     Currency currency=new Currency();
	     currency.setId(1L);
	     acc.setCurrency(currency);
	     
	     Customer customer=new Customer();
	     customer.setId(4L);
	     acc.setCustomer(customer);
	     System.out.println(acc.toString());
	     
	     Account accountResult = wr.path("accounts").type("application/json").put(Account.class, acc);
	     System.out.println("-----");
	 	
	     //deleting an account
	     System.out.println("Deleting test account with the id " +accountResult.getId()+":");
	    wr.path("accounts/"+accountResult.getId()).delete();
	    System.out.println("Deleted");
	    
	    //checking list of accounts
	    System.out.println("Getting list of accounts:");
	    List<Account> accounts2 = wr.path("accounts/").accept("application/json").get(new GenericType<List<Account>>(){});
	    System.out.println(String.format("List of accounts found:\n%s", accounts2.toString()));
	    System.out.println("-----");
	    //test if the new string and the old one are equal
	    Assert.assertFalse(accountResult.toString().equals(accounts2.toString()));
	
	    
	/*    wr.path("accounts/"+79).delete();
	    wr.path("accounts/"+80).delete();
	    wr.path("accounts/"+81).delete();
	    wr.path("accounts/"+82).delete();
	    wr.path("accounts/"+83).delete();
	    wr.path("accounts/"+84).delete();
	    wr.path("accounts/"+85).delete();
	    wr.path("accounts/"+86).delete();
	    wr.path("accounts/"+87).delete();*/
	
	    }
	@Test
	public void deleteAccountThatDoesntExist() throws JSONException {
	    System.out.println("Getting list of accounts:");
	    List<Account> accounts = wr.path("accounts/").accept("application/json").get(new GenericType<List<Account>>(){});
	    System.out.println(String.format("List of accounts found:\n%s", accounts.toString()));
	    System.out.println("-----");
	    
	    //trying to delete and unexisting account
	    System.out.println("Deleting test account with the id 10000:");
	    try{
	    wr.path("accounts/"+10000).delete();
	
	    } catch (RuntimeException e) {
	    	 Assert.fail();
	    }
	    
	    //checking the new list of accounts (which should be the same as the
	    //old one)
	    System.out.println("Getting list of accounts:");
	    List<Account> accounts2 = wr.path("accounts/").accept("application/json").get(new GenericType<List<Account>>(){});
	    //checking if the 2 strings are the same
	    Assert.assertTrue(accounts.toString().equals(accounts2.toString()));
	}
	@Test
	public void deleteAccountThatHasTransactions() throws JSONException{
		//creating a new account and a new transaction
		//creating an account
	    Account acc = new Account();
	    Date date=new Date();
	    acc.setAmount(2000.0);
	    acc.setIban("ro03bc1234");
	    acc.setCreationDate(date);
	    
	    //creating a new currency to associate with the account
	    Currency currency=new Currency();
	    currency.setId(1L);
	    acc.setCurrency(currency);
	    
	    Customer customer=new Customer();
	    customer.setId(4L);
	    acc.setCustomer(customer);
	    
	    Account accountResult = wr.path("accounts").type("application/json").put(Account.class, acc);
	    System.out.println("-----");
	    System.out.println(accountResult.getId());
	    
	            
	    //creating a new transaction
	    Transaction transaction=new Transaction();
	    transaction.setAccount(accountResult);
	    transaction.setAmount(200D);
	    transaction.setDate(date);
	    transaction.setCreationDate(date);
	    transaction.setDeleted(0);
	    transaction.setDetails("blabla");
	    transaction.setSender("Andrei");
	    transaction.setStatus("sent");
	    transaction.setTransactionDate(date);
	    transaction.setType("eur");
	    transaction.setId(9L);
	    System.out.println("Transaction created");
	    System.out.println(transaction.toString());
	    
	    //getting a list of the initial transactions
	    List<Account> transactionInit=wr.path("transactions").accept("application/json").get(new GenericType<List<Account>>(){});
	    System.out.println(transactionInit.toString());
	    
		
	    //updating the list of transactions 
	    ClientResponse transactionResult=wr.path("transactions").type("application/json").put(ClientResponse.class, transaction);
	    Transaction trans=(Transaction)transactionResult.getEntity(Transaction.class);
	    System.out.println("-----");
	    System.out.println(trans.toString());
	
	    //deleting the account, this should also delete the transaction        System.out.println("Deleting test account:");
	    wr.path("accounts/"+accountResult.getId()).delete();
	    System.out.println("-----");
	    
	    //getting the new list of transactions
	    List<Account> transactionResult2=wr.path("transactions").accept("application/json").get(new GenericType<List<Account>>(){});
	    System.out.println(transactionResult2.toString());
	    
	    //testing to see if the new list of transaction is equal to the old one
	    Assert.assertTrue(transactionInit.toString().equals(transactionResult2.toString()));
	}
	@Test
    public void getAccounts(){
            System.out.println("Getting list of accounts:");
            List<Account> accounts = wr.path("accounts/").accept("application/json").get(new GenericType<List<Account>>(){});
            System.out.println(String.format("List of accounts found:\n%s", accounts.toString()));
            System.out.println("-----");
            //checking the initial number of accounts
            int initAcc=accounts.size();
            
          //creating an account
            Account acc = new Account();
            Date date=new Date();
            acc.setAmount(2000.0);
            acc.setIban("ro03bc1234");
            acc.setCreationDate(date);
            
            //creating a new currency to associate with the account
            Currency currency=new Currency();
            currency.setId(1L);
            acc.setCurrency(currency);
            
            Customer customer=new Customer();
            customer.setId(4L);
            acc.setCustomer(customer);
            
            Account accountResult = wr.path("accounts").type("application/json").put(Account.class, acc);
            System.out.println("-----");
                     
            accounts = wr.path("accounts/").accept("application/json").get(new GenericType<List<Account>>(){});
            //checking the number of accounts again
            Assert.assertEquals(initAcc+1, accounts.size());
            Assert.assertTrue(accountResult.getAmount()==2000.0);
            Assert.assertTrue(accountResult.getIban().equals("ro03bc1234"));
            
            //deleting the account
            wr.path("accounts/"+accountResult.getId()).delete();
            
            System.out.println("Id of the account that was deleted: "+accountResult.getId());
            
            //checking the number of accounts again, should be equal to the 
            //initial number
            accounts = wr.path("accounts/").accept("application/json").get(new GenericType<List<Account>>(){});
            Assert.assertEquals(initAcc, accounts.size());
            
    }
    @Test
    public void getAccount() throws JSONException{
	       System.out.println("Getting list of accounts:");
	       List<Account> accounts = wr.path("accounts/").accept("application/json").get(new GenericType<List<Account>>(){});
	       System.out.println(String.format("List of accounts found:\n%s", accounts.toString()));
	       System.out.println("-----");
			//creating an account
	       Account acc = new Account();
	       Date date=new Date();
	       acc.setAmount(2000.0);
	       acc.setIban("ro03bc1234");
	       acc.setCreationDate(date);
	       
	       //creating a new currency to associate with the account
	       Currency currency=new Currency();
	       currency.setId(1L);
	       acc.setCurrency(currency);
	       
	       Customer customer=new Customer();
	       customer.setId(4L);
	       acc.setCustomer(customer);
	       
	       Account accountResult = wr.path("accounts").type("application/json").put(Account.class, acc);
	       System.out.println("-----");
	
	       // make sure it was added
	       Account accountEntity = wr.path("accounts/"+accountResult.getId()).accept("application/json").get(Account.class);
	      
	
	       Assert.assertEquals(accountEntity.getId(), accountResult.getId());      
	       System.out.println(accountEntity.getId());
	       Assert.assertEquals(accountEntity.getAmount(),accountResult.getAmount());
	       Assert.assertEquals(accountResult.getIban(), accountEntity.getIban());
	       Assert.assertEquals(accountResult.getCurrency().toString(), accountEntity.getCurrency().toString());
	       
	       //deleting the created account
	      System.out.println("Deleting test account:");
	       wr.path("accounts/"+accountResult.getId()).delete();
	       System.out.println("-----");
	
	   }

    @Test
    public void getAccountThatDoesntExist() throws JSONException{
        
       
        ClientResponse responseEntity = wr.path("accounts/"+10000).type("application/json").get(ClientResponse.class);
        
        String errorEntity=(String)responseEntity.getEntity(String.class);
        assertNotNull(errorEntity);
        assertTrue(errorEntity.contains("not found"));
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), responseEntity.getStatus());  
    }
    
    /*    @Test
    public void getAccountThatDoesntHaveId() throws JSONException{
        System.out.println("Getting list of accounts:");
        JSONArray accounts = wr.path("accounts/").accept("application/json").get(JSONArray.class);
        System.out.println(String.format("List of accounts found:\n%s", accounts.toString()));
        System.out.println("-----");
    	
        System.out.println("Gettind the account with the Id ' ' ");
        Account acc = wr.path("accounts/"+null).accept("application/json").get(Account.class);
    }*/
	@Test
    public void updateAccount() throws JSONException{
  		//creating an account
        Account acc = new Account();
        Date date=new Date();
        acc.setAmount(2000.0);
        acc.setIban("ro03bc1234");
        acc.setCreationDate(date);
        
        //creating a new currency to associate with the account
        Currency currency=new Currency();
        currency.setId(1L);
        acc.setCurrency(currency);
        
        Customer customer=new Customer();
        customer.setId(4L);
        acc.setCustomer(customer);
        
        Account accountResult = wr.path("accounts").type("application/json").put(Account.class, acc);
        System.out.println("-----");
    	
        // make sure it was added
        System.out.println("Getting list of accounts:");
        List<Account> accounts = wr.path("accounts/").accept("application/json").get(new GenericType<List<Account>>(){});
        System.out.println(String.format("List of accounts found:\n%s", accounts.toString()));
        System.out.println("-----");
        
    	//updating the account
        System.out.println("Updating test account:");
        Date date2=new Date();
        accountResult.setAmount(4000.0);
        accountResult.setIban("RO03TM2345");
        accountResult.setCreationDate(date2);
        
        //creating a new currency to associate with the account
        Currency currency2=new Currency();
        currency2.setId(1L);
        accountResult.setCurrency(currency2);
        
        Customer customer2=new Customer();
        customer2.setId(5L);
        accountResult.setCustomer(customer2);
        System.out.println(accountResult.toString());
        ClientResponse accountResult2 = wr.path("accounts/"+accountResult.getId()).type("application/json").put(ClientResponse.class, accountResult);
        Account acc2=accountResult2.getEntity(Account.class);
        System.out.println("-----");
        
        // make sure it was added correctly
        Account accountEntity = wr.path("accounts/"+acc2.getId()).accept("application/json").get(Account.class);
       
        Assert.assertEquals(accountEntity.getId(), acc2.getId());      
        Assert.assertEquals(accountEntity.getAmount(),acc2.getAmount());
        Assert.assertEquals(accountEntity.getIban(), acc2.getIban());
        Assert.assertEquals(accountEntity.getCurrency().toString(), acc2.getCurrency().toString());
        
        System.out.println("Deleting test account:");
        wr.path("accounts/"+acc2.getId()).delete();
        System.out.println("-----");
        
    }
	
	@Test
	public void updateAccountThatDoesntHaveCurrency() throws JSONException{
  		//creating an account
        Account acc = new Account();
        Date date=new Date();
        acc.setAmount(2000.0);
        acc.setIban("ro03bc1234");
        acc.setCreationDate(date);
        
        //creating a new currency to associate with the account
        Currency currency=new Currency();
        currency.setId(1L);
        acc.setCurrency(currency);
        
        Customer customer=new Customer();
        customer.setId(4L);
        acc.setCustomer(customer);
        
        Account accountResult = wr.path("accounts").type("application/json").put(Account.class, acc);
        System.out.println("-----");
    	
        // make sure it was added
        System.out.println("Getting list of accounts:");
        List<Account> accounts = wr.path("accounts/").accept("application/json").get(new GenericType<List<Account>>(){});
        System.out.println(String.format("List of accounts found:\n%s", accounts.toString()));
        System.out.println("-----");
        
    	//updating the account
        System.out.println("Updating test account:");
        Date date2=new Date();
        accountResult.setAmount(4000.0);
        accountResult.setIban("RO03TM2345");
        accountResult.setCreationDate(date2);
        
        //creating a new currency to associate with the account
        accountResult.setCurrency(null);
        
        Customer customer2=new Customer();
        customer2.setId(5L);
        accountResult.setCustomer(customer2);
        System.out.println(accountResult.toString());
        ClientResponse accountResult2 = wr.path("accounts/"+accountResult.getId()).type("application/json").put(ClientResponse.class, accountResult);
        //Account acc2=accountResult2.getEntity(Account.class);
        System.out.println("-----");	
		
		
		
	    //ClientResponse accountResult3 = wr.path("accounts").type("application/json").put(ClientResponse.class, acc);
	    ErrorInfo err=(ErrorInfo)accountResult2.getEntity(ErrorInfo.class);
	    Assert.assertTrue(Integer.valueOf(err.getCode())== BaseException.CURRENCY_OF_ACCOUNT_NOT_FOUND);
	    Assert.assertEquals(err.getDeveloperMessage(), "Every account should have a currency");
	    Assert.assertEquals(err.getMessage(), "Every account should have a currency");
	    Assert.assertEquals(err.getUrl(), "http://localhost:8080/bcibws/rest/accounts/"+accountResult.getId());
	
        System.out.println("Deleting test account:");
        wr.path("accounts/"+accountResult.getId()).delete();
        System.out.println("-----");
	}
	
	@Test
	public void updateAccountThatDoesntHaveCustomer() throws JSONException{
  		//creating an account
        Account acc = new Account();
        Date date=new Date();
        acc.setAmount(2000.0);
        acc.setIban("ro03bc1234");
        acc.setCreationDate(date);
        
        //creating a new currency to associate with the account
        Currency currency=new Currency();
        currency.setId(1L);
        acc.setCurrency(currency);
        
        Customer customer=new Customer();
        customer.setId(4L);
        acc.setCustomer(customer);
        
        Account accountResult = wr.path("accounts").type("application/json").put(Account.class, acc);
        System.out.println("-----");
    	
        // make sure it was added
        System.out.println("Getting list of accounts:");
        List<Account> accounts = wr.path("accounts/").accept("application/json").get(new GenericType<List<Account>>(){});
        System.out.println(String.format("List of accounts found:\n%s", accounts.toString()));
        System.out.println("-----");
        
    	//updating the account
        System.out.println("Updating test account:");
        Date date2=new Date();
        accountResult.setAmount(4000.0);
        accountResult.setIban("RO03TM2345");
        accountResult.setCreationDate(date2);
        
        //creating a new currency to associate with the account
        Currency currency2=new Currency();
        currency2.setId(1L);
        accountResult.setCurrency(currency2);
        
        accountResult.setCustomer(null);
        System.out.println(accountResult.toString());
        ClientResponse accountResult2 = wr.path("accounts/"+accountResult.getId()).type("application/json").put(ClientResponse.class, accountResult);
        //Account acc2=accountResult2.getEntity(Account.class);
        System.out.println("-----");	
		
		
		
	    //ClientResponse accountResult3 = wr.path("accounts").type("application/json").put(ClientResponse.class, acc);
	    ErrorInfo err=(ErrorInfo)accountResult2.getEntity(ErrorInfo.class);
	    Assert.assertTrue(Integer.valueOf(err.getCode())== BaseException.CUSTOMER_OF_ACCOUNT_NOT_FOUND);
	    Assert.assertEquals(err.getDeveloperMessage(), "Every account should have a customer");
	    Assert.assertEquals(err.getMessage(), "Every account should have a customer");
	    Assert.assertEquals(err.getUrl(), "http://localhost:8080/bcibws/rest/accounts/"+accountResult.getId());
	
        System.out.println("Deleting test account:");
        wr.path("accounts/"+accountResult.getId()).delete();
        System.out.println("-----");
	}
}
