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

            Account acc = new Account();
            Date date=new Date();
            acc.setAmount(2000.0);
            acc.setIban("ro03bc1234");
            acc.setCreationDate(date);
            
            //creating a new currency to associate with the account
            Currency currency=new Currency();
            currency.setId(1L);
           /* currency.setExchangerate(1.0);
            currency.setSymbol("EUR");  */ 
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

             System.out.println("Deleting test account:");

            wr.path("accounts/"+accounts.getJSONObject(0).get("id")).delete();
            //c.resource((String)accounts.get(0)).delete();
            System.out.println("-----");

    }

       // @Test
    public void updateAccount() throws JSONException{
        System.out.println("Getting list of accounts:");
        JSONArray accounts = wr.path("accounts/").accept("application/json").get(JSONArray.class);
        System.out.println(String.format("List of accounts found:\n%s", accounts.toString()));
        System.out.println("-----");
        // add a new account
        System.out.println("Creating test account:");
        JSONObject account = new JSONObject();
        account.put("iban", "ro03bc1234").put("amount", 2000.0);
        account = wr.path("accounts").type("application/json").put(JSONObject.class, account);
        System.out.println("---created --"+account);

        // make sure it was added
        System.out.println("Getting list of accounts:");
        accounts = wr.path("accounts/").accept("application/json").get(JSONArray.class);
        System.out.println(String.format("List of accounts found:\n%s", accounts.toString()));
        System.out.println("-----");

        //update the account
        System.out.println("Updating test account:");
        account.put("iban", "ro04bc1234").put("amount", 2100.0);
        account = wr.path("accounts/"+account.get("id")).type("application/json").post(JSONObject.class, account);
        System.out.println("-----Updated "+account);

        System.out.println("Getting list of accounts:");
        accounts = wr.path("accounts/").accept("application/json").get(JSONArray.class);
        System.out.println(String.format("List of accounts found:\n%s", accounts.toString()));
        System.out.println("-----");

        System.out.println("Deleting test account:");
        wr.path("accounts/"+account.get("id")).delete();
        System.out.println("-----");

    }

    @Test
    public void deleteAccount() throws JSONException{

 	//check list of accounts
     System.out.println("Getting list of accounts:");
     JSONArray accounts = wr.path("accounts/").accept("application/json").get(JSONArray.class);
     System.out.println(String.format("List of accounts found:\n%s", accounts.toString()));
     System.out.println("-----");
 	
     //deleting an account
     System.out.println("Deleting test account:");
    wr.path("accounts/"+13).delete();
    //AccountsResource testAccount=new AccountsResource();
    //testAccount.deleteAccounts(13L);
    //wr.path("accounts/").delete(13L);
    System.out.println("-----");
    
    //checking list of accounts
    System.out.println("Getting list of accounts:");
    JSONArray accounts2 = wr.path("accounts/").accept("application/json").get(JSONArray.class);
    System.out.println(String.format("List of accounts found:\n%s", accounts2.toString()));
    System.out.println("-----");
    //test if the new string and the old one are equal
    Assert.assertFalse(accounts.toString().equals(accounts2.toString()));
    

       
    }
    @Test
   public void getAccountDetails() throws JSONException{
       System.out.println("Getting list of accounts:");
       JSONArray accounts = wr.path("accounts/").accept("application/json").get(JSONArray.class);
       System.out.println(String.format("List of accounts found:\n%s", accounts.toString()));
       System.out.println("-----");
       // add a new account

       System.out.println("Creating test account:");
       Account acc = new Account();
       acc.setAmount(2000.0);
       acc.setIban("ro03bc1234");
       //creating a new currency to associate with the account
       Currency currency=new Currency();
       currency.setExchangerate(1.0);
       currency.setSymbol("EUR");   
       acc.setCurrency(currency);
       //wr.path("accounts/").type("application/json").put(Account.class, acc);
       //wr.path("accounts/").type("application/json").put(acc.toString());
       //System.out.println(account.toString());
       //JSONObject account=wr.path("accounts").type("application/json").put(JSONObject.class, acc);
       System.out.println("---created --"+acc);

       // make sure it was added
       Account accountEntity = wr.path("accounts/"+13).accept("application/json").get(Account.class);
       /*Assert.assertEquals(acc.getId(), accountEntity.getId());
       Assert.assertEquals(acc.getAmount(), accountEntity.getAmount());
       Assert.assertEquals(acc.getIban(), accountEntity.getIban());
       Assert.assertEquals(acc.getCurrency(), accountEntity.getCurrency());*/
      /*Assert.assertEquals(null, accountEntity.getId());
       boolean equal=accountEntity.getAmount()==2000.0;
       Assert.assertTrue(equal);
       Assert.assertEquals("ro03bc1234", accountEntity.getIban());
       Assert.assertEquals(currency, accountEntity.getCurrency());*/

       Assert.assertTrue(accountEntity.getId()==13);       
       Assert.assertTrue(accountEntity.getAmount()==960.0);
       Assert.assertEquals("ROTIM012345", accountEntity.getIban());
       Assert.assertEquals(currency.toString(), accountEntity.getCurrency().toString());
       
       //deleting the created account
      /* System.out.println("Deleting test account:");
       wr.path("accounts/"+acc.getId()).delete();
       System.out.println("-----");*/

   }
}
