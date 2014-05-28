package com.bergcomputers.rest.customers;

import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;

import com.bergcomputers.domain.Customer;
import com.bergcomputers.ejb.ICustomerController;

@Stateless
@Path("customers")
public class CustomersResources {

	@Context
    private UriInfo uriInfo;

    @EJB
    private ICustomerController customerController;

    //private DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");

    /**
     * Default constructor.
     */
    public CustomersResources() {
        // TODO Auto-generated constructor stub
    }

    @Path("{customerid}")
    public CustomerResource getCustomer(@PathParam("customerid") Long customerid) {
        return new CustomerResource(uriInfo, customerController, customerid);
    }

    @GET
    @Path("/detail/{customerid}")
    @Produces("application/json")
    public Customer getCustomerDetails(@PathParam("customerid") Long customerid) {
        return customerController.findCustomer(customerid);
    }

    @GET
    @Produces("application/json")
    @Path("uris")
    public String getCustomersURIs() {
        JSONArray uriArray = new JSONArray();
             for (Customer customer :  customerController.getCustomers()) {
                UriBuilder ub = uriInfo.getAbsolutePathBuilder();
                URI customerUri = ub.path(((Customer)customer).getId().toString()).build();
                uriArray.put(customerUri.toASCIIString());
            }
        return uriArray.toString();
    }

    @GET
    @Produces("application/json")
    public List<Customer> getCustomers() {
    	return customerController.getCustomers();
    }

    /*
     * Works but is better to use the other version
    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public String createCustomerJson(String jsonCustomer) throws JSONException {

    	JSONObject jsonEntity = new JSONObject(jsonCustomer);
    	Customer customerEntity = new Customer();
        customerEntity.setIban(jsonEntity.getString("iban"));
        customerEntity.setAmount(jsonEntity.getDouble("amount"));
        Date creationDate=null;
		try {
			creationDate = sdf.parse((String)jsonEntity.optString("createDate"));
		} catch (ParseException e) {
		}
        customerEntity.setCreationDate(null ==creationDate ? new Date():creationDate);

        customerEntity = customerController.create(customerEntity);

        return   new JSONObject()
            .put("id", customerEntity.getId())
            .put("iban", customerEntity.getIban())
            .put("amount", customerEntity.getAmount())
            .put("createDate", customerEntity.getCreationDate()).toString();
    } */
/*
    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public Customer createCustomer(final Customer jsonCustomer) throws JSONException {

    	jsonCustomer.setCreationDate(null ==jsonCustomer.getCreationDate() ? new Date():jsonCustomer.getCreationDate());
    	Customer customerEntity = customerController.create(jsonCustomer);
        return customerEntity;
    }
*/
}