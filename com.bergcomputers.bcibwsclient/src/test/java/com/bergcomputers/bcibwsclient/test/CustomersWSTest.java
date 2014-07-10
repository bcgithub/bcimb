package com.bergcomputers.bcibwsclient.test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bergcomputers.domain.Account;
import com.bergcomputers.domain.Customer;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class CustomersWSTest {
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
    public void deleteCustomers() throws JSONException{

        // add a new account
    		System.out.println("Test delete");
            System.out.println("Creating test customers:");
            //Customer acc = new Customer();
            //acc.setIban("RO02BC1678");
            //acc.setAmount(500.0);
            //acc.setCreationDate(new Date());
            //Customer customer = wr.path("customers").type("application/json").put(Customer.class, acc);
            System.out.println("-----");

            // make sure it was added

            System.out.println("Getting list of customers:");
            JSONArray customers = wr.path("customers/").accept("application/json").get(JSONArray.class);
            System.out.println(String.format("List of customers found:\n%s", customers.toString()));
            System.out.println("-----");

            System.out.println("Deleting test account:");
            wr.path("customers/"+4).delete();
            System.out.println("-----");

            System.out.println("Getting list of customers:");
            customers = wr.path("customers/").accept("application/json").get(JSONArray.class);
            System.out.println(String.format("List of customers found:\n%s", customers.toString()));
            System.out.println("-----");

    }
    
    @Test
    public void createCustomers() throws JSONException{
    	System.out.println("Test create");
    	System.out.println("Getting list of customers:");
        JSONArray customers = wr.path("customers/").accept("application/json").get(JSONArray.class);
        System.out.println(String.format("List of customers found:\n%s", customers.toString()));
        System.out.println("-----");
        
        System.out.println("Creating test customers:");
        Customer cust = new Customer();
        wr.path("customers").type("application/json").post(Customer.class, cust);
        
        System.out.println("Getting list of customers:");
        customers = wr.path("customers/").accept("application/json").get(JSONArray.class);
        System.out.println(String.format("List of customers found:\n%s", customers.toString()));
        System.out.println("-----");
    }
    
    /*@Test
    public void getCustomersJSONArray(){
            System.out.println("Getting list of customers:");
            JSONArray customers = wr.path("customers/").accept("application/json").get(JSONArray.class);
            System.out.println(String.format("List of customers found:\n%s", customers.toString()));
            System.out.println("-----");
    }*/
}
