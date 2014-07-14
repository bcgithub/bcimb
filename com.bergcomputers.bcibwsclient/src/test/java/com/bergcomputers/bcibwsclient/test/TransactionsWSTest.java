package com.bergcomputers.bcibwsclient.test;

import static org.junit.Assert.*;

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
import com.bergcomputers.domain.Customer;
import com.bergcomputers.domain.Transaction;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import static org.hamcrest.CoreMatchers.*;
/**
 *
 * @author Administrator
 */

public class TransactionsWSTest {
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
    public void getTransactionsJSONArray(){
            System.out.println("Getting list of transactions:");
            JSONArray transactions = wr.path("transactions/").accept("application/json").get(JSONArray.class);
            System.out.println(String.format("List of transactions found:\n%s", transactions.toString()));
            System.out.println("-----");
    }
    @Test
    public void createTransactions() throws JSONException{
       
            Transaction created= new Transaction();
            Account acc = new Account();
            acc.setId(7L);
            created.setAccount(acc);
            created.setId(10L);
            created.setAmount(5000.0);
            created.setDetails("Plata salar1");
            created.setSender("bCOMPUTERS1");
            created.setStatus("NEW1");
            Date creation= new Date();
            created.setTransactionDate(creation);
            created.setType("CREDIT");
            created.setDate(creation);
            created.setCreationDate(creation);
           //created.setVersion(0);
            
           System.out.println("Creating test transaction:");
           Transaction transactionEntity = wr.path("transactions").type("application/json").put(Transaction.class, created);
           System.out.println("---created --"+created);
 
           Assert.assertEquals(created.getAmount(),transactionEntity.getAmount());
           Assert.assertEquals(created.getDetails(),transactionEntity.getDetails());
           Assert.assertEquals(created.getStatus(),transactionEntity.getStatus());
           Assert.assertEquals(created.getDate(),transactionEntity.getDate());
           Assert.assertEquals(created.getTransactionDate(),transactionEntity.getTransactionDate());
           Assert.assertEquals(created.getCreationDate(),transactionEntity.getCreationDate());
           Assert.assertEquals(acc.getId(),transactionEntity.getAccount().getId());
           
         
           //deleting the transaction
           
           System.out.println("Deleting test transaction:");
           wr.path("transactions/"+transactionEntity.getId()).delete();
           System.out.println("-----");
          
           
    }

        @Test
    public void updateTransaction() throws JSONException{
       System.out.println("Getting list of transactions:");
       JSONArray transactions = wr.path("transactions/").accept("application/json").get(JSONArray.class);
       System.out.println(String.format("List of transactions found:\n%s", transactions.toString()));
       System.out.println("-----");
        //creating a transaction
        Transaction created= new Transaction();
        Account acc = new Account();
        acc.setId(7L);
        created.setAccount(acc);
        created.setId(9L);
        created.setAmount(1000.0);
        created.setDetails("Plata salar");
        created.setSender("bCOMPUTERS");
        created.setStatus("NEW");
        Date creation= new Date();
        created.setTransactionDate(creation);
        created.setType("CREDIT");
        created.setDate(creation);
        created.setCreationDate(creation);
       //created.setVersion(0);
        
       System.out.println("Creating test transaction:");
       created = wr.path("transactions").type("application/json").put(Transaction.class, created);
       System.out.println("---created --"+created);
      
       
       //make sure it was added 
       System.out.println("Getting list of transactions:");
       JSONArray transactions1 = wr.path("transactions/").accept("application/json").get(JSONArray.class);
       System.out.println(String.format("List of transactions found:\n%s", transactions1.toString()));
       System.out.println("-----");
       assertFalse("Create transaction test-can't create transaction",transactions.equals(transactions1));
       
       //updating the transaction
       System.out.println("Updating test transaction:");
       created.setAccount(acc);
       created.setId(3L);
       created.setAmount(5000.0);
       created.setDetails("Plata salar1");
       created.setSender("bCOMPUTERS1");
       created.setStatus("NEW1");
       Date creation1= new Date();
       created.setTransactionDate(creation1);
       created.setType("CREDIT1");
       created.setDate(creation1);
       created.setCreationDate(creation1);
       Account acc1 = new Account();
       acc.setId(7L);
       created.setAccount(acc1);
       Transaction tr1 = wr.path("transactions/"+ created.getId()).type("application/json").post(Transaction.class,created);
       System.out.println("-----Updated "+ created);
       assertTrue("Update transaction test-can't update transaction",tr1.toString().equals(created.toString()));
    
       //make sure it was added corectly 
      // assertThat(transactionEntity.getId(),equalTo(created.getId()));
       Assert.assertEquals(tr1.getId(),created.getId());
       Assert.assertEquals(created.getAmount(),tr1.getAmount());
       Assert.assertEquals(created.getDetails(),tr1.getDetails());
       Assert.assertEquals(created.getStatus(),tr1.getStatus());
       Assert.assertEquals(created.getDate(),tr1.getDate());
       Assert.assertEquals(created.getTransactionDate(),tr1.getTransactionDate());
       Assert.assertEquals(created.getCreationDate(),tr1.getCreationDate());
       Assert.assertEquals(acc.getId(),tr1.getAccount().getId());
        
       //delete the transaction
       System.out.println("Deleting test transaction:");
       wr.path("transactions/"+created.getId()).delete();
       System.out.println("-----");
       JSONArray transactions2 = wr.path("transactions/").accept("application/json").get(JSONArray.class);
       assertFalse("Create transaction test:can't delete the transaction",transactions1.equals(transactions2));
       assertFalse("Create transaction test: JSONArrays not equal after delete",transactions.equals(transactions2));
       System.out.println("Create transaction test finished:");
      
        }

    @Test
    public void deleteTransaction() throws JSONException{
    	System.out.println("Getting list of transactions:");
        JSONArray transactions = wr.path("transactions/").accept("application/json").get(JSONArray.class);
        System.out.println(String.format("List of transactions found:\n%s", transactions.toString()));
        System.out.println("-----");
        
        Transaction created= new Transaction();
        Account acc = new Account();
        acc.setId(7L);
        created.setAccount(acc);
        created.setId(9L);
        created.setAmount(1000.0);
        created.setDetails("Plata salar");
        created.setSender("bCOMPUTERS");
        created.setStatus("NEW");
        Date creation= new Date();
        created.setTransactionDate(creation);
        created.setType("CREDIT");
        created.setDate(creation);
        created.setCreationDate(creation);
       //created.setVersion(0);
       System.out.println("Creating test transaction:");
       created = wr.path("transactions").type("application/json").put(Transaction.class, created);
       System.out.println("---created --"+created);
       
       JSONArray transactions1 = wr.path("transactions/").accept("application/json").get(JSONArray.class);
       assertFalse("Create transaction test-can't create transaction",transactions.equals(transactions1));
       
       System.out.println("Deleting test transaction:");
       wr.path("transactions/"+created.getId()).delete();
       System.out.println("-----");
       
       System.out.println("Getting list of transactions:");
       JSONArray transactions2 = wr.path("transactions/").accept("application/json").get(JSONArray.class);
       System.out.println(String.format("List of transactions found:\n%s", transactions1.toString()));
       System.out.println("-----");
       assertFalse("Create transaction test:can't delete the transaction",transactions1.equals(transactions2));
       assertFalse("Create transaction test: JSONArrays not equal after delete",transactions.equals(transactions2));
       
     
    }
    @Test
    public void getTransaction()throws JSONException{
    	System.out.println("Getting list of transactions:");
        JSONArray transactions = wr.path("transactions/").accept("application/json").get(JSONArray.class);
        System.out.println(String.format("List of transactions found:\n%s", transactions.toString()));
        System.out.println("-----");
       Transaction created= new Transaction();
       Account acc = new Account();
       acc.setId(7L);
       created.setAccount(acc);
       //created.setId(9L);
       created.setAmount(1000.0);
       created.setDetails("Plata salar");
       created.setSender("bCOMPUTERS");
       created.setStatus("NEW");
       Date creation= new Date();
       created.setTransactionDate(creation);
       created.setType("CREDIT");
       created.setDate(creation);
       created.setCreationDate(creation);
      //created.setVersion(0);
      System.out.println("Creating test transaction:");
      created = wr.path("transactions").type("application/json").put(Transaction.class, created);
      System.out.println("---created --"+created);
      
       
      
      Transaction transactionEntity = wr.path("transactions/"+created.getId()).accept("application/json").get(Transaction.class);
      
      Assert.assertEquals(created.getId(),transactionEntity.getId());
      Assert.assertEquals(created.getAmount(),transactionEntity.getAmount());
      Assert.assertEquals(created.getDetails(),transactionEntity.getDetails());
      Assert.assertEquals(created.getStatus(),transactionEntity.getStatus());
      Assert.assertEquals(created.getDate(),transactionEntity.getDate());
      Assert.assertEquals(created.getTransactionDate(),transactionEntity.getTransactionDate());
      Assert.assertEquals(created.getCreationDate(),transactionEntity.getCreationDate());
      Assert.assertEquals(acc.getId(),transactionEntity.getAccount().getId());
      
      System.out.println("Deleting test transaction:");
      wr.path("transactions/"+created.getId()).delete();
     
    
    }
    
}

