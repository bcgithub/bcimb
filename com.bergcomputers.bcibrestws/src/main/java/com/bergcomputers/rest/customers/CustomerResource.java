package com.bergcomputers.rest.customers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.UriInfo;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.bergcomputers.domain.Customer;
import com.bergcomputers.ejb.ICustomerController;
import com.bergcomputers.rest.config.Config;
import com.sun.jersey.api.NotFoundException;

public class CustomerResource {

	private final static Logger logger = Logger.getLogger(CustomerResource.class.getName());

    private Long customerid; // customerid from url
    private Customer customerEntity; // appropriate jpa customer entity

    private UriInfo uriInfo; // actual uri info provided by parent resource
    private ICustomerController customerController; //controller provided by parent resource
    //private DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
    
    /** Creates a new instance of CustomerResource */
    public CustomerResource(UriInfo uriInfo,  ICustomerController customerController, Long customerid) {
        this.uriInfo = uriInfo;
        this.customerid = customerid;
        this.customerController = customerController;
        customerEntity = customerController.findCustomer(customerid);
    }

    @GET
    @Produces("application/json")
    public String getCustomer() throws JSONException {
        if (null == customerEntity) {
            throw new NotFoundException("customerid " + customerid + "does not exist!");
        }
        return new JSONObject()
            .put("id", customerEntity.getId())
            .put("firstname", customerEntity.getFirstName())
            .put("lastname", customerEntity.getLastName())
        	.put("login", customerEntity.getLogin())
        	.put("password",customerEntity.getPassword())
        	.put("createDate", customerEntity.getCreationDate()).toString();
    }
/*
    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public String putCustomer(String jsonCustomer) throws JSONException {

    	JSONObject jsonEntity = new JSONObject(jsonCustomer);
        Long jsonCustomerid = jsonEntity.getLong("id");

        if ((null != jsonCustomerid) && !jsonCustomerid.equals(customerid)) {
            //return Response.status(409).entity("userids differ!\n").build();
            throw new NotFoundException("customerids differ!\n");
        }

        final boolean newRecord = (null == customerEntity); // insert or update ?

        if (newRecord) { // new user record to be inserted
            customerEntity = new Customer();
        }
        customerEntity.setFirstName(jsonEntity.getString("firstName"));
        customerEntity.setLastName(jsonEntity.getString("lastName"));
        customerEntity.setLogin(jsonEntity.getString("login"));
        customerEntity.setPassword(jsonEntity.getString("password"));
        
        try {
			customerEntity.setCreationDate(Config.DATE_FORMAT_FULL.parse(((String)jsonEntity.optString("creationDate"))));
		} catch (ParseException e) {
			logger.warning(e.getMessage());
		}

        if (newRecord) {
        	customerEntity = customerController.create(customerEntity);
            //return Response.created(uriInfo.getAbsolutePath()).build();
        } else {
            customerController.save(customerEntity);
        	//return Response.noContent().build();
        }
        return   new JSONObject()
            .put("id", customerEntity.getId())
            .put("login", customerEntity.getLogin())
            .put("password", customerEntity.getPassword())
            .put("firstname",customerEntity.getFirstName())
            .put("lastname",customerEntity.getLastName())
            .put("createDate", customerEntity.getCreationDate()).toString();
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public String updateCustomer(String jsonCustomer) throws JSONException {
    	JSONObject jsonEntity = new JSONObject(jsonCustomer);
    	Customer customerEntity = customerController.findCustomer(jsonEntity.getLong("id"));
        customerEntity.setIban(jsonEntity.getString("iban"));
        customerEntity.setAmount(jsonEntity.getDouble("amount"));
        Date creationDate=null;
		try {
			creationDate = Config.DATE_FORMAT_FULL.parse((String)jsonEntity.optString("createDate"));
		} catch (ParseException e) {
			logger.warning(e.getMessage());
		}
        customerEntity.setCreationDate(null ==creationDate ? new Date():creationDate);

        customerController.save(customerEntity);

        return   new JSONObject()
            .put("id", customerEntity.getId())
            .put("iban", customerEntity.getIban())
            .put("amount", customerEntity.getAmount())
            .put("createDate", customerEntity.getCreationDate()).toString();
    }
*/
    @DELETE
    public void deleteCustomer() {
        if (null == customerEntity) {
            throw new NotFoundException("customerid " + customerid + "does not exist!");
        }
        customerController.delete(customerid);
    }

    public String asString() {
        return toString();
    }

    public String toString() {
        return customerEntity.toString();
    }

    public CustomerResource(Customer customerEntity) {
        this.customerEntity = customerEntity;
    }

    public Customer getCustomerEntity() {
        return customerEntity;
    }
   
}
