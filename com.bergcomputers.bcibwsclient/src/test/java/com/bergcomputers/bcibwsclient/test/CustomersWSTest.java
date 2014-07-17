package com.bergcomputers.bcibwsclient.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.Response;

import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jettison.json.JSONException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bergcomputers.domain.Customer;
import com.bergcomputers.domain.Role;
import com.bergcomputers.rest.exception.BaseException;
import com.bergcomputers.rest.exception.ErrorInfo;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

public class CustomersWSTest {
	final static String UrlBase = "http://localhost:"+"8080"+"/bcibws/rest/";
    private static WebResource wr = null;
    private static Client c = null;
    private DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");


    @BeforeClass
    public static void setupClass(){
         	ClientConfig cc = new DefaultClientConfig();
         	cc.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
         	JacksonJsonProvider jacksonJsonProvider = 
         		    new JacksonJaxbJsonProvider()
         		    .configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false)
         		    .configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, true);
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
            
            List<Customer> customers = wr.path("customers/").accept("application/json").get(new GenericType<List<Customer>>(){});
            ClientResponse result = wr.path("customers/").type("application/json").post(ClientResponse.class, cust);           
            Customer customerResult = (Customer)result.getEntity(Customer.class);
            List<Customer> customers2 = wr.path("customers/").accept("application/json").get(new GenericType<List<Customer>>(){});
            assertFalse("Delete Customer test: can't create customer", customers.toString().equals(customers2.toString()));

            System.out.println("Deleting test customer:");
            wr.path("customers/"+customerResult.getId()).delete();
            
            List<Customer> customers3 = wr.path("customers/").accept("application/json").get(new GenericType<List<Customer>>(){});
            assertFalse("Delete Customer test: can't delete customer", customers3.equals(customers2));
            //assertFalse("Delete Customer test: JSONArrays not equal after delete", customers.equals(customers3));
            assertEquals(customers, customers3);
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
        System.out.println(cust.getCreationDate());
        
        System.out.println("Adding test customer:");
        List<Customer> customers = wr.path("customers/").accept("application/json").get(new GenericType<List<Customer>>(){});
        ClientResponse result = wr.path("customers").type("application/json").post(ClientResponse.class, cust);
        Customer customerResult = (Customer)result.getEntity(Customer.class);
        List<Customer> customers2 = wr.path("customers/").accept("application/json").get(new GenericType<List<Customer>>(){});
        
        assertFalse("Create customer test: can't create customer", customers.equals(customers2));
        assertEquals(customerResult.getFirstName(),cust.getFirstName());
        assertEquals(customerResult.getLastName(),cust.getLastName());
        assertEquals(customerResult.getLogin(),cust.getLogin());
        assertEquals(customerResult.getPassword(),cust.getPassword());
        assertEquals(customerResult.getRole().getId(),cust.getRole().getId());
        
        System.out.println("deleting test customer:");
        wr.path("customers/"+customerResult.getId()).delete();
        
        List<Customer> customers3 = wr.path("customers/").accept("application/json").get(new GenericType<List<Customer>>(){});
        assertFalse("Create customer test: can't delete customer", customers2.equals(customers3));
        //assertFalse("Create customer test: JSONArrays not equal after delete", customers.equals(customers3));
        assertEquals(customers, customers3);
        System.out.println("Create customer test: finished");
    }
    
    @Test
    public void updateCustomer() throws JSONException{
    	System.out.println("Update customer test: started");
    	System.out.println("-----");
        
    	List<Customer> customers = wr.path("customers/").accept("application/json").get(new GenericType<List<Customer>>(){});
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
        ClientResponse result = wr.path("customers/").type("application/json").post(ClientResponse.class, customer);
        customer = (Customer)result.getEntity(Customer.class);
        
        List<Customer> customers2 = wr.path("customers/").accept("application/json").get(new GenericType<List<Customer>>(){});
        assertFalse("Update customer test: can't create customer", customers.equals(customers2));

        System.out.println("Updating test customer:");
        customer.setFirstName("newFirstName");
        customer.setLastName("newLastName");
        customer.setLogin("newLogin");
        customer.setPassword("newPassword");
        customer.setCreationDate(new Date());
        role = new Role();
        role.setName("role");
        role.setId(3L);
        customer.setRole(role);
        ClientResponse result2 = wr.path("customers/").type("application/json").put(ClientResponse.class, customer);
        Customer customerResult = (Customer)result2.getEntity(Customer.class);
        
        assertThat(customerResult.getFirstName(), equalTo(customer.getFirstName()));
        assertThat(customerResult.getLastName(), equalTo(customer.getLastName()));
        assertThat(customerResult.getLogin(), equalTo(customer.getLogin()));
        assertThat(customerResult.getPassword(), equalTo(customer.getPassword()));
        System.out.println(customerResult.getCreationDate()+"********"+customer.getCreationDate());
        //assertThat(customerResult.getCreationDate(), equalTo(customer.getCreationDate()));
        assertThat(customerResult.getRole().getId(), equalTo(customer.getRole().getId()));
        System.out.println(customerResult.getRole().getId());

        customers2 = wr.path("customers/").accept("application/json").get(new GenericType<List<Customer>>(){});

        System.out.println("Deleting test customer:");
        wr.path("customers/"+customer.getId()).delete();
        List<Customer> customers3 = wr.path("customers/").accept("application/json").get(new GenericType<List<Customer>>(){});
        assertFalse("Update customer test: can't delete customer", customers2.equals(customers3));
        //assertFalse("Update customer test: JSONArrays not equal after delete", customers.equals(customers3));
        assertEquals(customers, customers3);
        System.out.println("Update customer test: finished");

    }
    
    @Test
    public void getCustomer() throws JSONException{
    	System.out.println("Get customer test: started");
    	System.out.println("-----");
        
    	List<Customer> customers = wr.path("customers/").accept("application/json").get(new GenericType<List<Customer>>(){});
        System.out.println("Creating test customer:");
        Customer customer = new Customer();
        customer.setFirstName("firstName2");
        customer.setLastName("lastName");
        customer.setLogin("login");
        customer.setPassword("password");
        Role role = new Role();
        role.setName("role");
        role.setId(2L);
        customer.setRole(role);
        customer.setCreationDate(new Date());
        ClientResponse result = wr.path("customers").type("application/json").post(ClientResponse.class, customer);
        customer = (Customer)result.getEntity(Customer.class);
        List<Customer> customers2 = wr.path("customers/").accept("application/json").get(new GenericType<List<Customer>>(){});
        assertFalse("Get customer test: can't create customer", customers.equals(customers2));
        
        System.out.println("Getting test customer:");

        ClientResponse result2 = wr.path("customers/"+customer.getId()).accept("application/json").get(ClientResponse.class);
        Customer customerEntity = (Customer)result2.getEntity(Customer.class);
        
        assertEquals(customer.getFirstName() , customerEntity.getFirstName());
        assertEquals(customer.getLastName(), customerEntity.getLastName());
        assertEquals(customer.getLogin(), customerEntity.getLogin());
        assertEquals(customer.getPassword(), customerEntity.getPassword());
        assertEquals(customer.getCreationDate(), customerEntity.getCreationDate());
        assertEquals(customer.getRole().getId(), customerEntity.getRole().getId());
        
        System.out.println("Deleting test customer:");
        wr.path("customers/"+customer.getId()).delete();
        List<Customer> customers3 = wr.path("customers/").accept("application/json").get(new GenericType<List<Customer>>(){});
        assertFalse("Can't delete customer", customers2.equals(customers3));
        //assertFalse("JSONArrays not equal after delete", customers.equals(customers3));
        assertEquals(customers, customers3);
        System.out.println("Get customer test: finished");
    }
    
    /*@Test
    public void curatare() throws JSONException{
    	for(int i = 12; i<174;i++)
    		wr.path("customers/"+i).delete();
    }*/
    
    @Test
    public void getNonExistingCustomer() throws JSONException{
    	System.out.println("get non existing customer test: started");
    	System.out.println("-----");
    	
    	System.out.println("Geting test customer:");
    	ClientResponse responseEntity = wr.path("customers/"+Integer.MAX_VALUE).accept("application/json").get(ClientResponse.class);
    	assertEquals(Response.Status.NOT_FOUND.getStatusCode(), responseEntity.getStatus());
    	ErrorInfo errorEntity=(ErrorInfo)responseEntity.getEntity(ErrorInfo.class);
    	assertNotNull(errorEntity);
    	assertEquals(errorEntity.getCode(), String.valueOf(BaseException.CUSTOMER_NOT_FOUND_CODE));
    	assertEquals(errorEntity.getUrl(), "http://localhost:8080/bcibws/rest/customers/2147483647");
    	assertEquals(errorEntity.getMessage(), "Customer(2147483647) not found");
    	assertEquals(errorEntity.getDeveloperMessage(), "Customer(2147483647) not found");
        System.out.println("Get non existing customer test: finished");
    }
    
    @Test
    public void createNonExistingCustomer() throws JSONException{
    	System.out.println("Create non existing customer test: started");
    	System.out.println("-----");
    	Customer cust = null;
    	ClientResponse responseEntity = wr.path("customers/").type("application/json").post(ClientResponse.class, cust);
    	assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), responseEntity.getStatus());
    	String errorEntity=(String)responseEntity.getEntity(String.class);
    	assertNotNull(errorEntity);
    	assertTrue(errorEntity.contains("Bad Request"));
    	//assertEquals(errorEntity.getCode(), String.valueOf(BaseException.CUSTOMER_CREATE_NULL_ARGUMENT_CODE));
    	//assertEquals(errorEntity.getUrl(), "http://localhost:8080/bcibws/rest/customers/2147483647");
    	//assertEquals(errorEntity.getMessage(), "Customer(2147483647) not found");
    	//assertEquals(errorEntity.getDeveloperMessage(), "Customer(2147483647) not found");
        System.out.println("Create non existing customer test: finished");
    	
    }
    
    @Test
    public void createCustomerWithNonExistingRole() throws JSONException{
    	System.out.println("create Customer With Non Existing Role: started");
    	System.out.println("-----");
    	Customer cust = new Customer();
        cust.setFirstName("firstName");
        cust.setLastName("lastName");
        cust.setLogin("login");
        cust.setPassword("password");
        cust.setCreationDate(new Date());
        ClientResponse responseEntity = wr.path("customers").type("application/json").post(ClientResponse.class, cust);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), responseEntity.getStatus());
    	ErrorInfo errorEntity=(ErrorInfo)responseEntity.getEntity(ErrorInfo.class);
    	assertNotNull(errorEntity);
    	assertEquals(errorEntity.getCode(), String.valueOf(BaseException.CUSTOMER_CREATE_NULL_ROLE_CODE));
    	assertEquals(errorEntity.getUrl(), "http://localhost:8080/bcibws/rest/customers");
    	assertEquals(errorEntity.getMessage(), "Customer(Customer [firstName=firstName, lastName=lastName, login=login, role=null]) null Role");
    	assertEquals(errorEntity.getDeveloperMessage(), "Customer(Customer [firstName=firstName, lastName=lastName, login=login, role=null]) null Role");
    	Role role = new Role();
    	role.setName("role");
        role.setId(null);
        cust.setRole(role);
        responseEntity = wr.path("customers").type("application/json").post(ClientResponse.class, cust);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), responseEntity.getStatus());
    	errorEntity=(ErrorInfo)responseEntity.getEntity(ErrorInfo.class);
    	assertNotNull(errorEntity);
    	assertEquals(errorEntity.getCode(), String.valueOf(BaseException.CUSTOMER_CREATE_NULL_ROLE_ID_CODE));
    	assertEquals(errorEntity.getUrl(), "http://localhost:8080/bcibws/rest/customers");
    	assertEquals(errorEntity.getMessage(), "Customer(Customer [firstName=firstName, lastName=lastName, login=login, role=BaseEntity [id=null, deleted=0, version=null, creationDate=null]]) null Role Id");
    	assertEquals(errorEntity.getDeveloperMessage(), "Customer(Customer [firstName=firstName, lastName=lastName, login=login, role=BaseEntity [id=null, deleted=0, version=null, creationDate=null]]) null Role Id");
    	role = new Role();
    	role.setName("role");
        role.setId(Long.MAX_VALUE);
        cust.setRole(role);
        responseEntity = wr.path("customers/").type("application/json").post(ClientResponse.class, cust);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), responseEntity.getStatus());
    	errorEntity=(ErrorInfo)responseEntity.getEntity(ErrorInfo.class);
    	assertNotNull(errorEntity);
    	assertEquals(errorEntity.getCode(), String.valueOf(BaseException.CUSTOMER_CREATE_ROLE_ID_NOT_FOUND_CODE));
    	assertEquals(errorEntity.getUrl(), "http://localhost:8080/bcibws/rest/customers/");
    	assertEquals(errorEntity.getMessage(), "Customer(Customer [firstName=firstName, lastName=lastName, login=login, role=BaseEntity [id=9223372036854775807, deleted=0, version=null, creationDate=null]]) Role Id not found");
    	assertEquals(errorEntity.getDeveloperMessage(), "Customer(Customer [firstName=firstName, lastName=lastName, login=login, role=BaseEntity [id=9223372036854775807, deleted=0, version=null, creationDate=null]]) Role Id not found");
    	System.out.println("create Customer With Non Existing Role test: finished");
    }
    
    @Test
    public void updateNonExistingCustomer() throws JSONException{
    	System.out.println("Update non existing customer test: started");
    	System.out.println("-----");
    	ClientResponse responseEntity = wr.path("customers").type("application/json").put(ClientResponse.class, null);
    	assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), responseEntity.getStatus());
    	String errorEntity=(String)responseEntity.getEntity(String.class);
    	assertNotNull(errorEntity);
    	assertTrue(errorEntity.contains("Bad Request"));
    	//assertEquals(errorEntity.getUrl(), "http://localhost:8080/bcibws/rest/customers/2147483647");
    	//assertEquals(errorEntity.getMessage(), "Customer(2147483647) not found");
    	//assertEquals(errorEntity.getDeveloperMessage(), "Customer(2147483647) not found");
        System.out.println("Update non existing customer test: finished");
    	
    }
    
    @Test
    public void updateCustomerWithNonExistingRole() throws JSONException{
    	System.out.println("Update Customer With Non Existing Role: started");
    	System.out.println("-----");
    	Customer cust = new Customer();
        cust.setFirstName("firstName");
        cust.setLastName("lastName");
        cust.setLogin("login");
        cust.setPassword("password");
        cust.setCreationDate(new Date());
        //ClientResponse responseEntity = wr.path("customers").type("application/json").put(ClientResponse.class, cust);
        //assertEquals(Response.Status.NOT_FOUND.getStatusCode(), responseEntity.getStatus());
    	//ErrorInfo errorEntity=(ErrorInfo)responseEntity.getEntity(ErrorInfo.class);
    	//assertNotNull(errorEntity);
    	//assertEquals(errorEntity.getCode(), String.valueOf(BaseException.CUSTOMER_UPDATE_NULL_ROLE_CODE));
    	//assertEquals(errorEntity.getUrl(), "http://localhost:8080/bcibws/rest/customers");
    	//assertEquals(errorEntity.getMessage(), "Customer(Customer [firstName=firstName, lastName=lastName, login=login, role=null]) Role not found");
    	//assertEquals(errorEntity.getDeveloperMessage(), "Customer(Customer [firstName=firstName, lastName=lastName, login=login, role=null]) Role not found");
    	Role role = new Role();
    	role.setName("role");
        role.setId(null);
        cust.setRole(role);
        //responseEntity = wr.path("customers").type("application/json").put(ClientResponse.class, cust);
        //assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), responseEntity.getStatus());
    	//errorEntity=(ErrorInfo)responseEntity.getEntity(ErrorInfo.class);
    	//assertNotNull(errorEntity);
    	//assertEquals(errorEntity.getCode(), String.valueOf(BaseException.CUSTOMER_UPDATE_NULL_ROLE_ID_CODE));
    	//assertEquals(errorEntity.getUrl(), "http://localhost:8080/bcibws/rest/customers");
    	//assertEquals(errorEntity.getMessage(), "Customer(Customer [firstName=firstName, lastName=lastName, login=login, role=BaseEntity [id=null, deleted=0, version=null, creationDate=null]]) null Role Id");
    	//assertEquals(errorEntity.getDeveloperMessage(), "Customer(Customer [firstName=firstName, lastName=lastName, login=login, role=BaseEntity [id=null, deleted=0, version=null, creationDate=null]]) null Role Id");
    	role = new Role();
    	role.setName("role");
        role.setId(Long.MAX_VALUE);
        cust.setRole(role);
        ClientResponse responseEntity = wr.path("customers/").type("application/json").put(ClientResponse.class, cust);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), responseEntity.getStatus());
    	ErrorInfo errorEntity=(ErrorInfo)responseEntity.getEntity(ErrorInfo.class);
    	assertNotNull(errorEntity);
    	assertEquals(errorEntity.getCode(), String.valueOf(BaseException.CUSTOMER_UPDATE_ROLE_ID_NOT_FOUND_CODE));
    	assertEquals(errorEntity.getUrl(), "http://localhost:8080/bcibws/rest/customers/");
    	assertEquals(errorEntity.getMessage(), "Customer(Customer [firstName=firstName, lastName=lastName, login=login, role=BaseEntity [id=9223372036854775807, deleted=0, version=null, creationDate=null]]) Role Id not found");
    	assertEquals(errorEntity.getDeveloperMessage(), "Customer(Customer [firstName=firstName, lastName=lastName, login=login, role=BaseEntity [id=9223372036854775807, deleted=0, version=null, creationDate=null]]) Role Id not found");
    	System.out.println("create Customer With Non Existing Role test: finished");
    }
    
    @Test
    public void getCustomers(){
            System.out.println("Getting list of customers:");
            List<Customer> customers = wr.path("customers/").accept("application/json").get(new GenericType<List<Customer>>(){});
            
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
            
            ClientResponse result = wr.path("customers").type("application/json").post(ClientResponse.class, cust);
            Customer customerResult = (Customer)result.getEntity(Customer.class);
            List<Customer> customers2 = wr.path("customers/").accept("application/json").get(new GenericType<List<Customer>>(){});
            cust = customers2.get(customers2.size()-1);
            System.out.println(cust);
            assertEquals(customers.size()+1, customers2.size());
            assertEquals(customerResult.getFirstName(),cust.getFirstName());
            assertEquals(customerResult.getLastName(),cust.getLastName());
            assertEquals(customerResult.getLogin(),cust.getLogin());
            assertEquals(customerResult.getPassword(),cust.getPassword());
            assertEquals(customerResult.getRole().getId(),cust.getRole().getId());
            wr.path("customers/"+customerResult.getId()).delete();
    }
}
