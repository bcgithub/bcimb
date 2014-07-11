/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bergcomputers.bcibwsclient.test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bergcomputers.domain.Account;
import com.bergcomputers.domain.Currency;
import com.bergcomputers.domain.Customer;
import com.bergcomputers.domain.Transaction;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
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
            c = Client.create(cc);
            wr = c.resource(UrlBase);
    }

    @AfterClass
    public static void cleanup(){
        wr=null;
        c=null;
    }
    @Test
    public void getAccountsJSONArray(){
            System.out.println("Getting list of accounts:");
            JSONArray accounts = wr.path("accounts/").accept("application/json").get(JSONArray.class);
            System.out.println(String.format("List of accounts found:\n%s", accounts.toString()));
            System.out.println("-----");
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
            Assert.assertEquals(accountResult.getCreationDate(),acc.getCreationDate());
            Assert.assertEquals(accountResult.getCustomer().getId(), acc.getCustomer().getId());
            Assert.assertEquals(accountResult.getCurrency().getId(),acc.getCurrency().getId());
            
           //deleting the account
             System.out.println("Deleting test account:");
             System.out.println(accountResult.getId());
             wr.path("accounts/"+accountResult.getId()).delete();
            System.out.println("-----");

    }

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
        JSONArray accounts = wr.path("accounts/").accept("application/json").get(JSONArray.class);
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
        Account accountResult2 = wr.path("accounts/"+accountResult.getId()).type("application/json").put(Account.class, accountResult);
        System.out.println("-----");
        
        // make sure it was added correctly
        Account accountEntity = wr.path("accounts/"+accountResult2.getId()).accept("application/json").get(Account.class);
       
        Assert.assertTrue(accountEntity.getId()==accountResult2.getId());      
        Assert.assertEquals(accountEntity.getAmount(),accountResult2.getAmount());
        Assert.assertEquals(accountResult2.getIban(), accountEntity.getIban());
        Assert.assertEquals(accountResult2.getCurrency().toString(), accountEntity.getCurrency().toString());
        
        System.out.println("Deleting test account:");
        wr.path("accounts/"+accountResult2.getId()).delete();
        System.out.println("-----");
        
    }

    @Test
    public void deleteAccount() throws JSONException{

 	//check list of accounts
     System.out.println("Getting list of accounts:");
     JSONArray accounts = wr.path("accounts/").accept("application/json").get(JSONArray.class);
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
 	
     //deleting an account
     System.out.println("Deleting test account with the id " +accountResult.getId()+":");
    wr.path("accounts/"+accountResult.getId()).delete();
    System.out.println("Deleted");
    
    //checking list of accounts
    System.out.println("Getting list of accounts:");
    JSONArray accounts2 = wr.path("accounts/").accept("application/json").get(JSONArray.class);
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
   public void getAccountDetails() throws JSONException{
       System.out.println("Getting list of accounts:");
       JSONArray accounts = wr.path("accounts/").accept("application/json").get(JSONArray.class);
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
      

       Assert.assertTrue(accountEntity.getId()==accountResult.getId());      
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
    public void deleteAccountThatDoesntExist() throws JSONException {
        System.out.println("Getting list of accounts:");
        JSONArray accounts = wr.path("accounts/").accept("application/json").get(JSONArray.class);
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
        JSONArray accounts2 = wr.path("accounts/").accept("application/json").get(JSONArray.class);
        //checking if the 2 strings are the same
        Assert.assertTrue(accounts.toString().equals(accounts2.toString()));
    }
    
    //@Test
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
        
        //creating a new transaction
        Transaction transaction=new Transaction();
        transaction.setAccount(acc);
        transaction.setAmount(200D);
        transaction.setDate(date);
        transaction.setCreationDate(date);
        transaction.setDeleted(0);
        transaction.setDetails("blabla");
        transaction.setSender("Andrei");
        transaction.setStatus("sent");
        transaction.setTransactionDate(date);
        transaction.setType("eur");
        
        Transaction transactionResult=wr.path("transactions").type("application/json").put(Transaction.class, transaction);
        System.out.println("-----");
        
        //test the transaction on the server
        Transaction transactionResult2=wr.path("transactions").accept("application/json").get(Transaction.class);
        System.out.println(transactionResult2.toString());
/*        try{
        	wr.path("accounts/"+accountResult.getId()).delete();
        	Assert.fail();
        } catch (TooManyTransactions e){
        	Assert.assertTrue(true);
        }*/
 	
    
    }
}
