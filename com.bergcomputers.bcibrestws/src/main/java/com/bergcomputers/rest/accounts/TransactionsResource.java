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
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;

import com.bergcomputers.domain.Account;
import com.bergcomputers.domain.Transaction;
import com.bergcomputers.ejb.ITransactionController;

@Stateless
@Path("transactions")
public class TransactionsResource {

	@Context
	private UriInfo uriInfo;
	@EJB
	private ITransactionController transactionController;

	public TransactionsResource() {
		// TODO Auto-generated constructor stub
	}
	@POST
	@Consumes("application/json")
    @Produces("application/json")
	public Transaction updateTransaction(final Transaction jsonTransaction) {
		Transaction transactionEntity = transactionController.save(jsonTransaction);
        return transactionEntity;
	}
	
	@GET
	@Path("/{transactionid}")
	public Transaction getTransaction(@PathParam("transactionid") Long transactionid) {
		 return transactionController.findTransaction(transactionid);
		
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
    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public Transaction createTransaction(final Transaction jsonTransaction) {

    	jsonTransaction.setCreationDate(null ==jsonTransaction.getCreationDate() ? new Date():jsonTransaction.getCreationDate());
    	Transaction transactionEntity = transactionController.create(jsonTransaction);
        return transactionEntity;
    }


	@DELETE
	@Path("/{transactionid}")
	public void delete(@PathParam("transactionid") Long transactionId) {	
		transactionController.delete(transactionId);
	}
}
