package com.bergcomputers.rest.accounts;

import java.net.URI;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;

import com.bergcomputers.domain.Account;
import com.bergcomputers.domain.Customer;
import com.bergcomputers.ejb.IAccountController;
import com.bergcomputers.rest.exception.BaseException;
import com.bergcomputers.rest.exception.InvalidServiceArgumentException;
import com.bergcomputers.rest.exception.ResourceNotFoundException;

@Stateless
@Path("accounts")
public class AccountsResource {

	@Context
    private UriInfo uriInfo;

    @EJB
    private IAccountController accountController;

    //private DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");

    /**
     * Default constructor.
     */
    public AccountsResource() {
        // TODO Auto-generated constructor stub
    }

    @Path("/{accountid}")
    @Produces("application/json")
    @GET
    public Response getAccount(@PathParam("accountid") Long accountid) {
    	//return accountController.findAccount(accountid);
    	if (null == accountid){
    		throw new InvalidServiceArgumentException("Account Id shall be specified", BaseException.ACCOUNT_ID_REQUIRED_CODE);
    	}
    	Account result =  accountController.findAccount(accountid);
        if (null == result){
        	throw new ResourceNotFoundException(Account.class.getSimpleName()+
        			"("+accountid+") not found", BaseException.ACCOUNT_NOT_FOUND_CODE);
        }
        
        return Response.status(Response.Status.OK).entity(result)
                .build();
    }

    @GET
    @Produces("application/json")
    @Path("uris")
    public String getAccountsURIs() {
        JSONArray uriArray = new JSONArray();
             for (Account account :  accountController.getAccounts()) {
                UriBuilder ub = uriInfo.getAbsolutePathBuilder();
                URI accountUri = ub.path(((Account)account).getId().toString()).build();
                uriArray.put(accountUri.toASCIIString());
            }
        return uriArray.toString();
    }

    @GET
    @Produces("application/json")
    public List<Account> getAccounts() {
    	return accountController.getAccounts();
    }

    @DELETE
    @Path("/{accountid}")
    @Produces("application/json")
    public Response deleteAccounts(@PathParam("accountid") Long accountid){
    	accountController.delete(accountid);
    	return Response.status(Response.Status.OK).build();
    }
    
    
/*    @PUT
    @Path("/{accountid}")
    @Consumes("application/json")
    @Produces("application/json")
    public Account updateAccount(final Account jsonAccount) throws JSONException {
     Account accountEntity = accountController.update(jsonAccount);
        return accountEntity;
    }*/
    
    @PUT
    @Path("/{accountid}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response updateAccount(final Account jsonAccount) throws JSONException {
    	if (null == jsonAccount.getCurrency()){
    		throw new InvalidServiceArgumentException("Every account should have a currency", BaseException.CURRENCY_OF_ACCOUNT_NOT_FOUND);
    	}
    	if (null == jsonAccount.getCustomer()){
    		throw new InvalidServiceArgumentException("Every account should have a customer", BaseException.CUSTOMER_OF_ACCOUNT_NOT_FOUND);
    	}
    	Account accountEntity = accountController.update(jsonAccount);
        if (null == accountEntity){
        	throw new ResourceNotFoundException(Account.class.getSimpleName()+
        			"("+jsonAccount.getId()+") not found", BaseException.ACCOUNT_NOT_FOUND_CODE);
        }
        
        return Response.status(Response.Status.OK).entity(accountEntity)
                .build();
    }
    
/*    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public Account createAccount(final Account jsonAccount) throws JSONException {

    	jsonAccount.setCreationDate(null ==jsonAccount.getCreationDate() ? new Date():jsonAccount.getCreationDate());
    	Account accountEntity = accountController.create(jsonAccount);
        return accountEntity;
        
        
    }*/

    @Consumes("application/json")
    @Produces("application/json")
    public Response createAccount(final Account jsonAccount) throws JSONException {
    	if (null == jsonAccount.getCurrency()){
    		throw new InvalidServiceArgumentException("Every account should have a currency", BaseException.CURRENCY_OF_ACCOUNT_NOT_FOUND);
    	}
    	if (null == jsonAccount.getCustomer()){
    		throw new InvalidServiceArgumentException("Every account should have a customer", BaseException.CUSTOMER_OF_ACCOUNT_NOT_FOUND);
    	}
    	jsonAccount.setCreationDate(null ==jsonAccount.getCreationDate() ? new Date():jsonAccount.getCreationDate());
    	Account accountEntity = accountController.create(jsonAccount);
        if (null == accountEntity){
        	throw new ResourceNotFoundException(Account.class.getSimpleName()+
        			"("+jsonAccount.getId()+") not found", BaseException.ACCOUNT_NOT_FOUND_CODE);
        }
        
        return Response.status(Response.Status.OK).entity(accountEntity)
                .build();
}   
}
/*
     * Works but is better to use the other version
    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public String createAccountJson(String jsonAccount) throws JSONException {

    	JSONObject jsonEntity = new JSONObject(jsonAccount);
    	Account accountEntity = new Account();
        accountEntity.setIban(jsonEntity.getString("iban"));
        accountEntity.setAmount(jsonEntity.getDouble("amount"));
        Date creationDate=null;
		try {
			creationDate = sdf.parse((String)jsonEntity.optString("createDate"));
		} catch (ParseException e) {
		}
        accountEntity.setCreationDate(null ==creationDate ? new Date():creationDate);

        accountEntity = accountController.create(accountEntity);

        return   new JSONObject()
            .put("id", accountEntity.getId())
            .put("iban", accountEntity.getIban())
            .put("amount", accountEntity.getAmount())
            .put("createDate", accountEntity.getCreationDate()).toString();
    } */