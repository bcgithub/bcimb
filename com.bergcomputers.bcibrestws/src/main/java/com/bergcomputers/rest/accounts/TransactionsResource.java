package com.bergcomputers.rest.accounts;

import java.net.URI;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
import com.bergcomputers.domain.Transaction;
import com.bergcomputers.ejb.IAccountController;
import com.bergcomputers.ejb.ITransactionController;
import com.bergcomputers.rest.exception.BaseException;
import com.bergcomputers.rest.exception.InvalidServiceArgumentException;
import com.bergcomputers.rest.exception.ResourceNotFoundException;

@Stateless
@Path("transactions")
public class TransactionsResource {

	@Context
	private UriInfo uriInfo;
	@EJB
	private ITransactionController transactionController;


	@EJB
	private IAccountController accountController;
	
	public TransactionsResource() {
		// TODO Auto-generated constructor stub
	}
	/*@POST
	@Consumes("application/json")
    @Produces("application/json")
	public Transaction updateTransaction(final Transaction jsonTransaction) {
		Transaction transactionEntity = transactionController.save(jsonTransaction);
        return transactionEntity;
	}
	*/
	@POST
	@Consumes("application/json")
    @Produces("application/json")
	public Response updateTransaction(final Transaction jsonTransaction) {
		if (null == jsonTransaction.getAccount()){
    		throw new InvalidServiceArgumentException("Every transaction should have a account", BaseException.ACCOUNT_OF_TRANSACTION_NOT_FOUND);
    	}
		else if(null ==jsonTransaction.getAccount().getId()){
        	throw new ResourceNotFoundException(Transaction.class.getSimpleName()+
        			"("+jsonTransaction+") null Account Id", BaseException.TRANSACTION_UPDATE_NULL_ACCOUNT_ID_CODE);
        }else if(null == accountController.findAccount(jsonTransaction.getAccount().getId())){
        	throw new ResourceNotFoundException(Transaction.class.getSimpleName()+
        			"("+jsonTransaction+") Account Id not found", BaseException.TRANSACTION_UPDATE_ACCOUNT_ID_NOT_FOUND_CODE);
        }
    	Transaction transactionEntity = transactionController.save(jsonTransaction);
    	if (null == transactionEntity){
        	throw new ResourceNotFoundException(Transaction.class.getSimpleName()+
        			"("+jsonTransaction.getId()+") not found", BaseException.TRANSACTION_NOT_FOUND_CODE);
        }
        
        return Response.status(Response.Status.OK).entity(transactionEntity).build();
    }
	
	/*@GET
	@Path("/{transactionid}")
	public Transaction getTransaction(@PathParam("transactionid") Long transactionid) {
		 return transactionController.findTransaction(transactionid);
		
	}
	*/
	
	    @GET
	    @Path("/{transactionid}")
	    public Response getTransaction(@PathParam("transactionid") Long transactionid) {
	    	if (null == transactionid){
	    		throw new InvalidServiceArgumentException("Transaction Id shall be specified", BaseException.TRANSACTION_ID_REQUIRED_CODE);
	    	}
	    	Transaction result =  transactionController.findTransaction(transactionid);
	        if (null == result){
	        	throw new ResourceNotFoundException(Transaction.class.getSimpleName()+
	        			"("+transactionid+") not found", BaseException.TRANSACTION_NOT_FOUND_CODE);
	        }
	        
	        return Response.status(Response.Status.OK).entity(result)
	                .build();
	    }

	@GET
	@Produces("application/json")
	public List<Transaction> getTransactions() {
		return transactionController.getTransactions();
	}
	
	@GET
    @Path("/detail/{transactionid}")
    @Produces("application/json")
    public Transaction getTransactionDetails(@PathParam("transactionid") Long transactionid) {
     return transactionController.findTransaction(transactionid);
	}
    @GET
    @Produces("application/json")
    @Path("uris")
    public String getTransactionsURIs() {
        JSONArray uriArray = new JSONArray();
             for (Transaction transaction :  transactionController.getTransactions()) {
                UriBuilder ub = uriInfo.getAbsolutePathBuilder();
                URI transactionUri = ub.path(((Transaction)transaction).getId().toString()).build();
                uriArray.put(transactionUri.toASCIIString());
            }
        return uriArray.toString();
    }
   /* @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public Transaction createTransaction(final Transaction jsonTransaction) throws JSONException {

    	jsonTransaction.setCreationDate(null ==jsonTransaction.getCreationDate() ? new Date():jsonTransaction.getCreationDate());
    	Transaction transactionEntity = transactionController.create(jsonTransaction);
        return transactionEntity;
    } */
    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public Response createTransaction(final Transaction jsonTransaction) {
    	if (null == jsonTransaction.getAccount()){
    		throw new InvalidServiceArgumentException("Every transaction should have a account", BaseException.ACCOUNT_OF_TRANSACTION_NOT_FOUND);
    	}
       
    	jsonTransaction.setCreationDate(null ==jsonTransaction.getCreationDate() ? new Date():jsonTransaction.getCreationDate());
    	Transaction transactionEntity = transactionController.create(jsonTransaction);
        if (null == transactionEntity){
        	throw new ResourceNotFoundException(Transaction.class.getSimpleName()+
        			"("+jsonTransaction.getId()+") not found", BaseException.TRANSACTION_NOT_FOUND_CODE);
        }
        
        return Response.status(Response.Status.OK).entity(transactionEntity).build();
    }


/*	@DELETE
	@Path("/{transactionid}")
	public void delete(@PathParam("transactionid") Long transactionId) {	
		transactionController.delete(transactionId);
	}
*/
   @DELETE
   @Path("/{transactionid}")
    public Response delete(@PathParam("transactionid") Long transactionId){
	transactionController.delete(transactionId);
	return Response.status(Response.Status.OK).build();
   }
}