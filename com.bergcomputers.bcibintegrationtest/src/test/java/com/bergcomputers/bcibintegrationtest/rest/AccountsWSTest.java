/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bergcomputers.bcibintegrationtest.rest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.bergcomputers.domain.Account;
import com.bergcomputers.ejb.AccountController;
import com.bergcomputers.rest.accounts.AccountResource;
import com.bergcomputers.rest.accounts.AccountsResource;
import com.bergcomputers.rest.activator.JaxRsActivator;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

/**
 *
 * @author Administrator
 */
@RunWith(Arquillian.class)
public class AccountsWSTest {
    final static String UrlBase = "http://localhost:"+"8181"+"/test/rest/";
    private static WebResource wr = null;
    private static Client c = null;
    private DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");

    @Deployment
    public static Archive<?> createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
            .addPackage(Account.class.getPackage())
            .addPackage(AccountController.class.getPackage())
            .addPackage(JaxRsActivator.class.getPackage())
            .addPackage(AccountsResource.class.getPackage())
            .addClass(JaxRsActivator.class)
            .addClass(AccountsResource.class)
            .addClass(AccountResource.class)
            .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

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
        System.out.println("Getting list of accounts:");
            JSONArray accounts = wr.path("accounts/").accept("application/json").get(JSONArray.class);
            System.out.println(String.format("List of accounts found:\n%s", accounts.toString()));
            System.out.println("-----");
        // add a new user

            System.out.println("Creating test account:");
            JSONObject account = new JSONObject();
            account.put("iban", "RO01BC1234")
             	   .put("amount", 1000.0);
            wr.path("accounts").type("application/json").put(account.toString());
            System.out.println("-----");

            // make sure it was added

            System.out.println("Getting list of accounts:");
            accounts = wr.path("accounts/").accept("application/json").get(JSONArray.class);
            System.out.println(String.format("List of accounts found:\n%s", accounts.toString()));
            System.out.println("-----");

             System.out.println("Deleting test account:");

            wr.path("accounts/"+accounts.getJSONObject(0).get("id")).delete();
            //c.resource((String)accounts.get(0)).delete();
            System.out.println("-----");

    }

        @Test
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

        // add a new account

            System.out.println("Creating test account:");
            /*JSONObject account = new JSONObject();
            account.put("iban", "RO02BC1678").put("amount", 500.0).put("creationDate", sdf.format(new Date()));
            account = wr.path("accounts").type("application/json").put(JSONObject.class, account);*/

            Account acc = new Account();
            acc.setIban("RO02BC1678");
            acc.setAmount(500.0);
            acc.setCreationDate(new Date());
            Account account = wr.path("accounts").type("application/json").put(Account.class, acc);
            System.out.println("-----");

            // make sure it was added

            System.out.println("Getting list of accounts:");
            JSONArray accounts = wr.path("accounts/").accept("application/json").get(JSONArray.class);
            System.out.println(String.format("List of accounts found:\n%s", accounts.toString()));
            System.out.println("-----");

             System.out.println("Deleting test account:");
            wr.path("accounts/"+account.getId()).delete();
            System.out.println("-----");

            System.out.println("Getting list of accounts:");
            accounts = wr.path("accounts/").accept("application/json").get(JSONArray.class);
            System.out.println(String.format("List of accounts found:\n%s", accounts.toString()));
            System.out.println("-----");

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
       /*JSONObject account = new JSONObject();
       account.put("iban", "ro03bc1234").put("amount", 2000.0);*/

       Account account = wr.path("accounts").type("application/json").put(Account.class, acc);
       //wr.path("accounts").type("application/json").put(JSONObject.class, account);
       System.out.println("---created --"+account);

       // make sure it was added
       Account accountEntity = wr.path("accounts/detail/"+account.getId()).accept("application/json").get(Account.class);
       Assert.assertEquals(account.getId(), accountEntity.getId());
       System.out.println("Getting list of accounts:");
       accounts = wr.path("accounts/").accept("application/json").get(JSONArray.class);
       System.out.println(String.format("List of accounts found:\n%s", accounts.toString()));
       System.out.println("-----");




       System.out.println("Deleting test account:");
       wr.path("accounts/"+account.getId()).delete();
       System.out.println("-----");

   }
}
