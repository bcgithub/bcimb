package com.bergcomputers.rest.accounts;

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

import com.bergcomputers.domain.Account;
import com.bergcomputers.ejb.IAccountController;
import com.bergcomputers.rest.config.Config;
import com.sun.jersey.api.NotFoundException;

public class AccountResource {

	private final static Logger logger = Logger.getLogger(AccountResource.class.getName());

    private Long accountid; // accountid from url
    private Account accountEntity; // appropriate jpa account entity

    private UriInfo uriInfo; // actual uri info provided by parent resource
    private IAccountController accountController; //controller provided by parent resource
    //private DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");

    /** Creates a new instance of AccountResource */
    public AccountResource(UriInfo uriInfo,  IAccountController accountController, Long accountid) {
        this.uriInfo = uriInfo;
        this.accountid = accountid;
        this.accountController = accountController;
        accountEntity = accountController.findAccount(accountid);
    }

    @GET
    @Produces("application/json")
    public String getAccount() throws JSONException {
        if (null == accountEntity) {
            throw new NotFoundException("accountid " + accountid + "does not exist!");
        }
        return new JSONObject()
            .put("id", accountEntity.getId())
            .put("iban", accountEntity.getIban())
        	.put("ammount", accountEntity.getAmount())
        	.put("createDate", accountEntity.getCreationDate()).toString();
//            .put("bookmarks", uriInfo.getAbsolutePathBuilder().path("bookmarks").build());
    }

    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public String putAccount(String jsonAccount) throws JSONException {

    	JSONObject jsonEntity = new JSONObject(jsonAccount);
        Long jsonAccountid = jsonEntity.getLong("id");

        if ((null != jsonAccountid) && !jsonAccountid.equals(accountid)) {
            //return Response.status(409).entity("userids differ!\n").build();
            throw new NotFoundException("accountids differ!\n");
        }

        final boolean newRecord = (null == accountEntity); // insert or update ?

        if (newRecord) { // new user record to be inserted
            accountEntity = new Account();
        }
        accountEntity.setIban(jsonEntity.getString("iban"));
        accountEntity.setAmount(jsonEntity.getDouble("amount"));
        try {
			accountEntity.setCreationDate(Config.DATE_FORMAT_FULL.parse(((String)jsonEntity.optString("creationDate"))));
		} catch (ParseException e) {
			logger.warning(e.getMessage());
		}

        if (newRecord) {
        	accountEntity = accountController.create(accountEntity);
            //return Response.created(uriInfo.getAbsolutePath()).build();
        } else {
            accountController.save(accountEntity);
        	//return Response.noContent().build();
        }
        return   new JSONObject()
            .put("id", accountEntity.getId())
            .put("iban", accountEntity.getIban())
            .put("amount", accountEntity.getAmount())
            .put("createDate", accountEntity.getCreationDate()).toString();
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public String updateAccount(String jsonAccount) throws JSONException {
    	JSONObject jsonEntity = new JSONObject(jsonAccount);
    	Account accountEntity = accountController.findAccount(jsonEntity.getLong("id"));
        accountEntity.setIban(jsonEntity.getString("iban"));
        accountEntity.setAmount(jsonEntity.getDouble("amount"));
        Date creationDate=null;
		try {
			creationDate = Config.DATE_FORMAT_FULL.parse((String)jsonEntity.optString("createDate"));
		} catch (ParseException e) {
			logger.warning(e.getMessage());
		}
        accountEntity.setCreationDate(null ==creationDate ? new Date():creationDate);

        accountController.save(accountEntity);

        return   new JSONObject()
            .put("id", accountEntity.getId())
            .put("iban", accountEntity.getIban())
            .put("amount", accountEntity.getAmount())
            .put("createDate", accountEntity.getCreationDate()).toString();
    }

    @DELETE
    public void deleteAccount() {
        if (null == accountEntity) {
            throw new NotFoundException("accountid " + accountid + "does not exist!");
        }
        accountController.delete(accountid);
    }


    public String asString() {
        return toString();
    }

    public String toString() {
        return accountEntity.toString();
    }

    public AccountResource(Account accountEntity) {
        this.accountEntity = accountEntity;
    }

    public Account getAccountEntity() {
        return accountEntity;
    }
}
