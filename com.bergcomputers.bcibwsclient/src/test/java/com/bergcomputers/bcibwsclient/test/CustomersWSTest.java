package com.bergcomputers.bcibwsclient.test;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bergcomputers.domain.Customer;
import com.bergcomputers.domain.Role;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import static org.hamcrest.CoreMatchers.*;
import java.util.Date;

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
    	
    		System.out.println("Delete customer test: started");
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
            cust.setCreationDate(new Date());
            
            JSONArray customers = wr.path("customers/").accept("application/json").get(JSONArray.class);
            Customer customerResult = wr.path("customers").type("application/json").post(Customer.class, cust);
            JSONArray customers2 = wr.path("customers/").accept("application/json").get(JSONArray.class);
            assertFalse("Delete Customer test: can't create customer", customers.toString().equals(customers2.toString()));

            System.out.println("Deleting test customer:");
            wr.path("customers/"+customerResult.getId()).delete();
            
            JSONArray customers3 = wr.path("customers/").accept("application/json").get(JSONArray.class);
            assertFalse("Delete Customer test: can't delete customer", customers3.equals(customers2));
            assertFalse("Delete Customer test: JSONArrays not equal after delete", customers.equals(customers3));
            System.out.println("-----");
            System.out.println("Delete customer test: finished");
    }
    
    @Test
    public void createCustomers() throws JSONException{
    	System.out.println("Create customer test: started");
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
        cust.setCreationDate(new Date());
        
        System.out.println("Adding test customer:");
        JSONArray customers = wr.path("customers/").accept("application/json").get(JSONArray.class);
        Customer customerResult = wr.path("customers").type("application/json").post(Customer.class, cust);
        JSONArray customers2 = wr.path("customers/").accept("application/json").get(JSONArray.class);
        
        assertFalse("Create customer test: can't create customer", customers.equals(customers2));
        assertEquals(customerResult.getFirstName(),cust.getFirstName());
        assertEquals(customerResult.getLastName(),cust.getLastName());
        assertEquals(customerResult.getLogin(),cust.getLogin());
        assertEquals(customerResult.getPassword(),cust.getPassword());
        assertEquals(customerResult.getRole().getId(),cust.getRole().getId());
        
        System.out.println("deleting test customer:");
        wr.path("accounts/"+customerResult.getId()).delete();
        
        JSONArray customers3 = wr.path("customers/").accept("application/json").get(JSONArray.class);
        assertFalse("Create customer test: can't delete customer", customers2.equals(customers3));
        assertFalse("Create customer test: JSONArrays not equal after delete", customers.equals(customers3));
        System.out.println("Create customer test: finished");
    }
    
    @Test
    public void updateCustomer() throws JSONException{
    	System.out.println("Update customer test: started");
    	System.out.println("-----");
        System.out.println("Creating test customer:");
        
        JSONArray customers = wr.path("customers/").accept("application/json").get(JSONArray.class);
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
        customer.setCreationDate(new Date());
        customer = wr.path("customers").type("application/json").post(Customer.class, customer);

        JSONArray customers2 = wr.path("customers/").accept("application/json").get(JSONArray.class);
        assertFalse("Update customer test: can't create customer", customers.equals(customers2));

        System.out.println("Updating test customer:");
        customer.setFirstName("newFirstName");
        customer.setLastName("newLastName");
        //customer.setLogin("newLogin");
        //customer.setPassword("newPassword");
        customer.setCreationDate(new Date());
        role = new Role();
        role.setName("role");
        role.setId(3L);
        customer.setRole(role);
        Customer customerResult = wr.path("customers").type("application/json").put(Customer.class, customer);
        //assertTrue("Update customer test: can't update customer", customerResult.toString().equals(customer.toString()));
        assertThat(customerResult.getFirstName(), (equalTo(customer.getFirstName())));
        assertThat(customerResult.getLastName(), (equalTo(customer.getLastName())));
        assertThat(customerResult.getLogin(), (equalTo(customer.getLogin())));
        assertThat(customerResult.getPassword(), (equalTo(customer.getPassword())));
        assertThat(customerResult.getCreationDate(), (equalTo(customer.getCreationDate())));

        customers2 = wr.path("customers/").accept("application/json").get(JSONArray.class);

        System.out.println("Deleting test customer:");
        wr.path("customers/"+customer.getId()).delete();
        JSONArray customers3 = wr.path("customers/").accept("application/json").get(JSONArray.class);
        assertFalse("Update customer test: can't delete customer", customers2.equals(customers3));
        assertFalse("Update customer test: JSONArrays not equal after delete", customers.equals(customers3));
        System.out.println("Create customer test: finished");

    }
    
    @Test
    public void getCustomersJSONArray(){
            System.out.println("Getting list of customers:");
            JSONArray customers = wr.path("customers/").accept("application/json").get(JSONArray.class);
            System.out.println(String.format("List of customers found:\n%s", customers.toString()));
            System.out.println("-----");
    }
}
