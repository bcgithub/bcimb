package com.bergcomputers.rest.accounts;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import com.bergcomputers.domain.Currency;
import com.bergcomputers.ejb.ICurrencyController;

@Stateless
@Path("currency")
public class CurrencyResource {

	@Context
    private UriInfo uriInfo;
	@EJB
	private ICurrencyController currencyController;
	
    @GET
    @Produces("application/json")
    @Path("/{currencyid}")
    public Currency findCurrency(@PathParam("currencyid") Long currencyid){
    	return currencyController.findCurrency(currencyid);
    }
    
/*    @DELETE
    @Path("/{currencyid}")
    @Produces("application/json")
    public Response deleteAccounts(@PathParam("currencyid") Long currencyid){
    	currencyController.delete(currencyid);
    	return Response.status(Response.Status.OK).build();
    }*/
}
