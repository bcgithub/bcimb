package com.bergcomputers.bcibwsclient.test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bergcomputers.domain.Account;
import com.bergcomputers.domain.Customer;
import com.bergcomputers.domain.Role;
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
            System.out.println("-----");

            System.out.println("Getting list of customers:");
            JSONArray customers = wr.path("customers/").accept("application/json").get(JSONArray.class);
            System.out.println(String.format("List of customers found:\n%s", customers.toString()));
            System.out.println("-----");

            System.out.println("Deleting test account:");
            wr.path("customers/"+14).delete();
            System.out.println("-----");

            System.out.println("Getting list of customers:");
            customers = wr.path("customers/").accept("application/json").get(JSONArray.class);
            System.out.println(String.format("List of customers found:\n%s", customers.toString()));
            System.out.println("-----");

    }
    
    @Test
    public void createCustomers() throws JSONException{
    	System.out.println("Test: create");
    	System.out.println("-----");
    	System.out.println("Getting list of customers:");
        JSONArray customers = wr.path("customers/").accept("application/json").get(JSONArray.class);
        System.out.println(String.format("List of customers found:\n%s", customers.toString()));
        System.out.println("-----");
        System.out.println("Creating test customer:");
        Customer cust = new Customer();
        cust.setFirstName("firstName");
        cust.setLastName("lastName");
        cust.setLogin("login");
        cust.setPassword("password");
        Role role = new Role();
        role.setName("role");
        role.setId(2L);
        cust.setRole(role);
        Customer customerResult = wr.path("customers").type("application/json").post(Customer.class, cust);
        System.out.println("deleting test customer:");
        wr.path("accounts/"+customerResult.getId()).delete();
        System.out.println("Getting list of customers:");
        customers = wr.path("customers/").accept("application/json").get(JSONArray.class);
        System.out.println(String.format("List of customers found:\n%s", customers.toString()));
        System.out.println("-----");
        System.out.println("finished Test: create");
    }
    
    @Test
    public void updateCustomer() throws JSONException{
        System.out.println("Getting list of Customers:");
        JSONArray customers = wr.path("customers/").accept("application/json").get(JSONArray.class);
        System.out.println(String.format("List of customers found:\n%s", customers.toString()));
        System.out.println("-----");
        // add a new account
        System.out.println("Creating test customer:");
        Customer customer = new Customer();
        customer.setFirstName("firstName");
        customer.setLastName("lastName");
        customer.setLogin("login");
        customer.setPassword("password");
        Role role = new Role();
        role.setName("role");
        role.setId(2L);
        customer.setRole(role);
        customer = wr.path("customers").type("application/json").post(Customer.class, customer);
        System.out.println("---created --"+customer);

        // make sure it was added
        System.out.println("Getting list of customers:");
        customers = wr.path("customers/").accept("application/json").get(JSONArray.class);
        System.out.println(String.format("List of customers found:\n%s", customers.toString()));
        System.out.println("-----");

        //update the account
        System.out.println("Updating test customer:");
        customer.setFirstName("newFirstName");
        customer.setLastName("newLastName");
        customer = wr.path("customers/"+customer.getId()).type("application/json").put(Customer.class, customer);
        System.out.println("-----Updated "+customer);

        System.out.println("Getting list of accounts:");
        customers = wr.path("customers/").accept("application/json").get(JSONArray.class);
        System.out.println(String.format("List of customers found:\n%s", customers.toString()));
        System.out.println("-----");

        System.out.println("Deleting test customer:");
        wr.path("customers/"+customer.getId()).delete();
        System.out.println("-----");

    }
    
    @Test
    public void getCustomersJSONArray(){
            System.out.println("Getting list of customers:");
            JSONArray customers = wr.path("customers/").accept("application/json").get(JSONArray.class);
            System.out.println(String.format("List of customers found:\n%s", customers.toString()));
            System.out.println("-----");
    }
}
