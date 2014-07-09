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
        // add a new user

            System.out.println("Creating test transactions:");
            JSONObject transaction = new JSONObject();
            transaction.put("type", "type?")
             	   .put("amount", 1000.0);
            wr.path("transactions").type("application/json").put(transaction.toString());
            System.out.println("-----");

            // make sure it was added

            System.out.println("Getting list of transactions:");
            transactions = wr.path("transactions/").accept("application/json").get(JSONArray.class);
            System.out.println(String.format("List of transactions found:\n%s",transactions.toString()));
            System.out.println("-----");

             System.out.println("Deleting test transaction:");

            wr.path("transactions/"+transactions.getJSONObject(0).get("id")).delete();
            //c.resource((String)accounts.get(0)).delete();
            System.out.println("-----");

    }

        @Test
    public void updateTransaction() throws JSONException{
        System.out.println("Getting list of transactions:");
        JSONArray transactions = wr.path("transactions/").accept("application/json").get(JSONArray.class);
        System.out.println(String.format("List of transactions found:\n%s", transactions.toString()));
        System.out.println("-----");
        // add a new account

        System.out.println("Creating test transaction:");
        JSONObject transaction = new JSONObject();
        transaction.put("type", "ro03bc1234").put("amount", 2000.0);
        transaction = wr.path("transactions").type("application/json").put(JSONObject.class, transaction);
        System.out.println("---created --"+transaction);

        // make sure it was added

        System.out.println("Getting list of transactions:");
        transactions = wr.path("transactions/").accept("application/json").get(JSONArray.class);
        System.out.println(String.format("List of transactions found:\n%s", transactions.toString()));
        System.out.println("-----");


        //update the account
        System.out.println("Updating test transaction:");
        transaction.put("type", "ro04bc1234").put("amount", 2100.0);
        transaction = wr.path("transactions/"+transaction.get("id")).type("application/json").post(JSONObject.class, transaction);
        System.out.println("-----Updated "+transaction);

        System.out.println("Getting list of transactions:");
        transactions = wr.path("transactions/").accept("application/json").get(JSONArray.class);
        System.out.println(String.format("List of transactions found:\n%s", transactions.toString()));
        System.out.println("-----");


        System.out.println("Deleting test transaction:");
        wr.path("transactions/"+transaction.get("id")).delete();
        System.out.println("-----");

    }

    @Test
    public void deleteTransaction() throws JSONException{
    	System.out.println("Getting list of transactions:");
        JSONArray transactions = wr.path("transactions/").accept("application/json").get(JSONArray.class);
        System.out.println(String.format("List of transactions found:\n%s", transactions.toString()));
        System.out.println("-----");
        
        System.out.println("Deleting test transaction:");
        wr.path("transactions/"+8).delete();
        System.out.println("-----");
        
        System.out.println("Getting list of transactions:");
        transactions = wr.path("transactions/").accept("application/json").get(JSONArray.class);
        System.out.println(String.format("List of transactions found:\n%s", transactions.toString()));
        System.out.println("-----");
        
    }
    @Test
   public void getTransactionDetails() throws JSONException{
       System.out.println("Getting list of transactions:");
       JSONArray transactions = wr.path("transactions/").accept("application/json").get(JSONArray.class);
       System.out.println(String.format("List of transactions found:\n%s", transactions.toString()));
       System.out.println("-----");
       // add a new account

       System.out.println("Creating test transaction:");
       Transaction tr = new Transaction();
       tr.setAmount(2000.0);
       tr.setType("ro03bc1234");
       /*JSONObject account = new JSONObject();
       account.put("iban", "ro03bc1234").put("amount", 2000.0);*/

       Transaction transaction = wr.path("transactions").type("application/json").put(Transaction.class, tr);
       //wr.path("accounts").type("application/json").put(JSONObject.class, account);
       System.out.println("---created --"+transaction);

       // make sure it was added
       Transaction transactionEntity = wr.path("transactions/detail/"+transaction.getId()).accept("application/json").get(Transaction.class);
       Assert.assertEquals(transaction.getId(),transactionEntity.getId());
       System.out.println("Getting list of transactions:");
       transactions = wr.path("transactions/").accept("application/json").get(JSONArray.class);
       System.out.println(String.format("List of transactions found:\n%s", transactions.toString()));
       System.out.println("-----");




       System.out.println("Deleting test transaction:");
       wr.path("transactions/"+transaction.getId()).delete();
       System.out.println("-----");

   }
}

