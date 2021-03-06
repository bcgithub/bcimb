package com.bergcomputers.rest.customers;

import java.net.URI;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;

import com.bergcomputers.domain.Customer;
import com.bergcomputers.ejb.ICustomerController;
import com.bergcomputers.ejb.IRoleController;
import com.bergcomputers.rest.exception.BaseException;
import com.bergcomputers.rest.exception.InvalidServiceArgumentException;
import com.bergcomputers.rest.exception.ResourceNotFoundException;
import com.bergcomputers.rest.interceptors.PerfLoggingInterceptor;

@Interceptors(PerfLoggingInterceptor.class)
@Stateless
@Path("customers")
public class CustomersResources {

	@Context
    private UriInfo uriInfo;

    @EJB
    private ICustomerController customerController;
    
    @EJB
    private IRoleController roleController;

    //private DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");

    /**
     * Default constructor.
     */
    public CustomersResources() {
        // TODO Auto-generated constructor stub
    }

    @GET
    @Path("/{customerid}")
    @Produces("application/json")
    public Response getCustomer(@PathParam("customerid") Long customerId) {
    	Customer result =  customerController.findCustomer(customerId);
        if (null == result){
        	throw new ResourceNotFoundException(Customer.class.getSimpleName()+
        			"("+customerId+") not found", BaseException.CUSTOMER_NOT_FOUND_CODE);
        }
        
        return Response.status(Response.Status.OK).entity(result).
        		build();
        
    }

   /* @GET
    @Path("/detail/{customerid}")
    @Produces("application/json")
    public Customer getCustomerDetails(@PathParam("customerid") Long customerid) {
        return customerController.findCustomer(customerid);
    }*/

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
    
    @DELETE
    @Path("/{accountid}")
    @Produces("application/json")
    public void deleteCustomer(@PathParam("accountid") Long accountid){
    	customerController.delete(accountid);
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response createCustomer(final Customer jsonCustomer) throws JSONException {
    	if(null == jsonCustomer){
    		throw new InvalidServiceArgumentException("Customer create argument must be not null", BaseException.CUSTOMER_CREATE_NULL_ARGUMENT_CODE);
    	}
    	if (null == jsonCustomer.getRole()){
        	throw new ResourceNotFoundException(Customer.class.getSimpleName()+
        			"("+jsonCustomer+") null Role", BaseException.CUSTOMER_CREATE_NULL_ROLE_CODE);
        }else if(null ==jsonCustomer.getRole().getId()){
        	throw new ResourceNotFoundException(Customer.class.getSimpleName()+
        			"("+jsonCustomer+") null Role Id", BaseException.CUSTOMER_CREATE_NULL_ROLE_ID_CODE);
        }else if(null == roleController.getRole(jsonCustomer.getRole().getId())){
        	throw new ResourceNotFoundException(Customer.class.getSimpleName()+
        			"("+jsonCustomer+") Role Id not found", BaseException.CUSTOMER_CREATE_ROLE_ID_NOT_FOUND_CODE);
        }
    	jsonCustomer.setCreationDate(null ==jsonCustomer.getCreationDate() ? new Date():jsonCustomer.getCreationDate());
    	Customer customerEntity = customerController.create(jsonCustomer);
    	return Response.status(Response.Status.OK).entity(customerEntity).
    			build();
    }
    
    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public Response updateCustomer(final Customer jsonCustomer) throws JSONException {
    	if(null == jsonCustomer){
    		throw new InvalidServiceArgumentException("Customer update argument must be not null", BaseException.CUSTOMER_UPDATE_NULL_ARGUMENT_CODE);
    	}
    	if (null == jsonCustomer.getRole()){
        	throw new ResourceNotFoundException(Customer.class.getSimpleName()+
        			"("+jsonCustomer+") Role not found", BaseException.CUSTOMER_UPDATE_NULL_ROLE_CODE);
        }else if(null ==jsonCustomer.getRole().getId()){
        	throw new ResourceNotFoundException(Customer.class.getSimpleName()+
        			"("+jsonCustomer+") null Role Id", BaseException.CUSTOMER_UPDATE_NULL_ROLE_ID_CODE);
        }else if(null == roleController.getRole(jsonCustomer.getRole().getId())){
        	throw new ResourceNotFoundException(Customer.class.getSimpleName()+
        			"("+jsonCustomer+") Role Id not found", BaseException.CUSTOMER_UPDATE_ROLE_ID_NOT_FOUND_CODE);
        }
    	jsonCustomer.setCreationDate(null == jsonCustomer.getCreationDate() ? new Date():jsonCustomer.getCreationDate());
    	Customer customerEntity = customerController.update(jsonCustomer);
        return Response.status(Response.Status.OK).entity(customerEntity).
    			build();
    }
}