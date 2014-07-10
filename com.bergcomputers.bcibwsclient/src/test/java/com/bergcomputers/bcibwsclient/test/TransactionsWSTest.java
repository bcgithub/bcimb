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
        System.out.println("Getting list of transactions:");
            JSONArray transactions = wr.path("transactions/").accept("application/json").get(JSONArray.class);
            System.out.println(String.format("List of transactions found:\n%s", transactions.toString()));
            System.out.println("-----");
       
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
           created = wr.path("transactions").type("application/json").put(Transaction.class, created);
           System.out.println("---created --"+created);

           System.out.println("Getting list of transactions:");
           transactions = wr.path("transactions/").accept("application/json").get(JSONArray.class);
           System.out.println(String.format("List of transactions found:\n%s",transactions.toString()));
           System.out.println("-----");
           /* 
            System.out.println("Creating test transactions:");
            JSONObject transaction = new JSONObject();
            transaction.put("type", "type?").put("amount", 1000.0);
            wr.path("transactions").type("application/json").put(transaction.toString());
            System.out.println("-----");

            System.out.println("Deleting test transaction:");

            wr.path("transactions/"+transactions.getJSONObject(0).get("id")).delete();
            //c.resource((String)accounts.get(0)).delete();
            System.out.println("-----");
            */

    }

        @Test
    public void updateTransaction() throws JSONException{
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
       
       System.out.println("Getting list of transactions:");
       transactions = wr.path("transactions/").accept("application/json").get(JSONArray.class);
       System.out.println(String.format("List of transactions found:\n%s", transactions.toString()));
       System.out.println("-----");

       System.out.println("Updating test transaction:");
       created.setId(11L);
       created.setAmount(9000.0);
       created = wr.path("transactions/"+ created.getId()).type("application/json").post(Transaction.class,  created);
       System.out.println("-----Updated "+ created);
       
       System.out.println("Getting list of transactions:");
       transactions = wr.path("transactions/").accept("application/json").get(JSONArray.class);
       System.out.println(String.format("List of transactions found:\n%s", transactions.toString()));
       System.out.println("-----");

       System.out.println("Deleting test transaction:");
       wr.path("transactions/"+created.getId()).delete();
       System.out.println("-----");
        /*System.out.println("Creating test transaction:");
        JSONObject transaction = new JSONObject();
        transaction.put("type", "CREDIT").put("amount", 1500.0);
        transaction = wr.path("transactions").type("application/json").put(JSONObject.class, transaction);
        System.out.println("---created --"+transaction);

        // make sure it was added

        System.out.println("Getting list of transactions:");
        transactions = wr.path("transactions/").accept("application/json").get(JSONArray.class);
        System.out.println(String.format("List of transactions found:\n%s", transactions.toString()));
        System.out.println("-----");


        //update the account
        System.out.println("Updating test transaction:");
        transaction.put("type", "CREDIT").put("amount", 2000.0);
        transaction = wr.path("transactions/"+transaction.get("id")).type("application/json").post(JSONObject.class, transaction);
        System.out.println("-----Updated "+transaction);

        System.out.println("Getting list of transactions:");
        transactions = wr.path("transactions/").accept("application/json").get(JSONArray.class);
        System.out.println(String.format("List of transactions found:\n%s", transactions.toString()));
        System.out.println("-----");


        System.out.println("Deleting test transaction:");
        wr.path("transactions/"+transaction.get("id")).delete();
        System.out.println("-----");
        */
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
       
       Transaction transactionResult = wr.path("transactions/"+created.getId()).accept("application/json").get(Transaction.class);
       
       System.out.println("Deleting test transaction:");
       wr.path("transactions/"+created.getId()).delete();
       System.out.println("-----");
        
       System.out.println("Getting list of transactions:");
       JSONArray transactions1 = wr.path("transactions/").accept("application/json").get(JSONArray.class);
       System.out.println(String.format("List of transactions found:\n%s", transactions1.toString()));
       System.out.println("-----");
       //Assert.assertFalse(transactionResult.toString(),equals(transactions1.toString()));
       Assert.assertFalse(transactionResult.toString().equals(transactions1.toString()));
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

