package com.bergcomputers.bcibwsclient.test;

import static org.junit.Assert.*;

import org.codehaus.jackson.map.DeserializationConfig;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.bergcomputers.rest.exception.BaseException;
import com.bergcomputers.rest.exception.ErrorInfo;
import com.bergcomputers.domain.Account;
import com.bergcomputers.domain.Customer;
import com.bergcomputers.domain.Transaction;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.json.JSONConfiguration;

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
	private Object transactionEntity;


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
    public void getTransactions(){
    	System.out.println("Getting list of accounts:");
        List<Transaction> transaction = wr.path("transactions/").accept("application/json").get(new GenericType<List<Transaction>>(){});
    	System.out.println(String.format("List of transactions found:\n%s", transaction.toString()));
    	System.out.println("-----");
        //checking the initial number of transactions
        int initTr=transaction.size();
            
            Transaction created= new Transaction();
            Account acc = new Account();
            acc.setId(7L);
            created.setAccount(acc);
            //created.setId(9L);
            created.setAmount(1000.0);
            created.setDetails("Plata salar");
            created.setSender("bCOMPUTERS");
            created.setStatus("NEW");
            created.setType("CREDIT");
            Date creation= new Date();
            created.setTransactionDate(creation);
            created.setDate(creation);
            created.setCreationDate(creation);
           
            
           System.out.println("Creating test transaction:");
           Transaction transactionResult = wr.path("transactions").type("application/json").put(Transaction.class, created);
           System.out.println("---created --"+created);
           
   
           //checking the number of transactions again
           transaction = wr.path("transactions/").accept("application/json").get(new GenericType<List<Transaction>>(){});
           System.out.println(transaction.toString());
           Assert.assertEquals(initTr+1, transaction.size());
           transactionResult= transaction.get(transaction.size()-1);
           Assert.assertEquals(created.getAmount(),transactionResult.getAmount());
           Assert.assertEquals(created.getDetails(),transactionResult.getDetails());
           Assert.assertEquals(created.getStatus(),transactionResult.getStatus());
           Assert.assertEquals(created.getType(),transactionResult.getType());
           Assert.assertEquals(created.getSender(),transactionResult.getSender());
           
		  
           //deleting the transaction
           wr.path("transactions/"+transactionResult.getId()).delete();
           
           System.out.println("Id of the transaction that was deleted: "+transactionResult.getId());
           
           transaction = wr.path("transactions/").accept("application/json").get(new GenericType<List<Transaction>>(){});
           Assert.assertEquals(initTr, transaction.size());
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
       acc1.setId(7L);
       created.setAccount(acc1);
       Transaction tr1 = wr.path("transactions").type("application/json").post(Transaction.class,created);
       System.out.println("-----Updated "+ created);

    
       //make sure it was added corectly 
      // assertThat(transactionEntity.getId(),equalTo(created.getId()));
       Assert.assertEquals(tr1.getId(),created.getId());
       Assert.assertEquals(tr1.getAmount(),created.getAmount());
       Assert.assertEquals(tr1.getDetails(),created.getDetails());
       Assert.assertEquals(tr1.getStatus(),created.getStatus());
       Assert.assertEquals(tr1.getDate(),created.getDate());
       Assert.assertEquals(tr1.getTransactionDate(),created.getTransactionDate());
       Assert.assertEquals(tr1.getCreationDate(),created.getCreationDate());
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
    //se creeaza o tranzactie fara cont si trebuie sa arunce exceptia de la create  cu mesajul Every transaction should have a account 
    @Test
    public void createTransactionWithNonExistingAccount() throws JSONException{
    	System.out.println("Getting list of transactions:");
        JSONArray transactions = wr.path("transactions/").accept("application/json").get(JSONArray.class);
        System.out.println(String.format("List of transactions found:\n%s", transactions.toString()));
        System.out.println("-----");
        
        Transaction created= new Transaction();
        
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
        System.out.println("-----");
        ClientResponse responseEntity = wr.path("transactions").type("application/json").put(ClientResponse.class, created);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), responseEntity.getStatus());
        ErrorInfo errorEntity=(ErrorInfo)responseEntity.getEntity(ErrorInfo.class);
        assertNotNull(errorEntity);
        assertEquals(errorEntity.getCode(), String.valueOf(BaseException.ACCOUNT_OF_TRANSACTION_NOT_FOUND));
        assertEquals(errorEntity.getUrl(), "http://localhost:8080/bcibws/rest/transactions");
        assertEquals(errorEntity.getMessage(), "Account Id not found");
        assertEquals(errorEntity.getDeveloperMessage(), "Account Id not found");
        Account acc = new Account();
        acc.setId(null);
        created.setAccount(acc);
        responseEntity = wr.path("transactions").type("application/json").post(ClientResponse.class, created);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), responseEntity.getStatus());
        errorEntity=(ErrorInfo)responseEntity.getEntity(ErrorInfo.class);
        assertNotNull(errorEntity);
        assertEquals(errorEntity.getCode(), String.valueOf(BaseException.TRANSACTION_CREATE_NULL_ACCOUNT_ID_CODE));
        assertEquals(errorEntity.getUrl(), "http://localhost:8080/bcibws/rest/transactions");
        assertEquals(errorEntity.getMessage(), "Account Id not found");
        assertEquals(errorEntity.getDeveloperMessage(), "Account Id not found");
        /*System.out.println("Creating test transaction:");
        Transaction transactionResult = wr.path("transactions").type("application/json").put(Transaction.class, created);
        System.out.println("---created --"+created);
        
        ClientResponse transaction = wr.path("transactions/"+transactionResult.getId()).accept("application/json").get(ClientResponse.class);
        ErrorInfo err=(ErrorInfo)transaction.getEntity(ErrorInfo.class);
        Assert.assertTrue(Integer.valueOf(err.getCode())== BaseException.ACCOUNT_OF_TRANSACTION_NOT_FOUND);
        //Assert.assertEquals(err.getUrl(), "http://localhost:8080/bcibws/rest/accounts/"+accountResult.getId());
    */
        }
    @Test
    public void updateTransactionWithNonExistingAccount() throws JSONException{
    }
    @Test
    public void deleteTransactionWithNonExistingAccount() throws JSONException{
    }
    
    
}

