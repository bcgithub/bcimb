package com.bergcomputers.rest.accounts;

import java.net.URI;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.codehaus.jettison.json.JSONArray;

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
     return transactionController.get(transactionid);
	}

	/*
	 * @PUT
	 * 
	 * @Consumes("application/json")
	 * 
	 * @Produces("application/json") public Transaction createTransaction(final
	 * Transaction jsonTransaction) throws JSOnException{
	 * jsonTransaction.setCreationDate(null=jsonTransaction.getCreationDate()) }
	 */
	@DELETE
	@Path("/{transactionid}")
	public void delete(@PathParam("transactionid") Long transactionId) {	
		transactionController.delete(transactionId);
	}
}
