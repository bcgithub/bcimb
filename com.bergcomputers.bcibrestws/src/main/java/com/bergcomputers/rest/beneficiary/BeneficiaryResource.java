package com.bergcomputers.rest.beneficiary;

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
import com.bergcomputers.domain.Beneficiary;
import com.bergcomputers.ejb.IAccountController;
import com.bergcomputers.ejb.IBeneficiaryController;
import com.bergcomputers.rest.accounts.AccountResource;
import com.bergcomputers.rest.config.Config;
import com.sun.jersey.api.NotFoundException;

public class BeneficiaryResource {
	private final static Logger logger = Logger.getLogger(BeneficiaryResource.class.getName());

    private Long beneficiaryid; // accountid from url
    private Beneficiary beneficiaryEntity; // appropriate jpa account entity

    private UriInfo uriInfo; // actual uri info provided by parent resource
    private IBeneficiaryController beneficiaryController; //controller provided by parent resource
    //private DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");

    /** Creates a new instance of BeneficiaryResource */
    public BeneficiaryResource(UriInfo uriInfo,  IBeneficiaryController beneficiaryController, Long beneficiaryid) {
        this.uriInfo = uriInfo;
        this.beneficiaryid = beneficiaryid;
        this.beneficiaryController = beneficiaryController;
        beneficiaryEntity = beneficiaryController.findBeneficiary(beneficiaryid);
    }

    @GET
    @Produces("application/json")
    public String getBeneficiary() throws JSONException {
        if (null == beneficiaryEntity) {
            throw new NotFoundException("beneficiaryid " + beneficiaryid + "does not exist!");
        }
        return new JSONObject()
            .put("id", beneficiaryEntity.getId())
            .put("iban", beneficiaryEntity.getIban())
            .put("name", beneficiaryEntity.getName())
        	.put("details", beneficiaryEntity.getDetails())
        	.put("accountholder", beneficiaryEntity.getAccountHolder())
        	.put("createDate", beneficiaryEntity.getCreationDate()).toString();
//            .put("bookmarks", uriInfo.getAbsolutePathBuilder().path("bookmarks").build());
    }

    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public String putBeneficiary(String jsonBeneficiary) throws JSONException {

    	JSONObject jsonEntity = new JSONObject(jsonBeneficiary);
        Long jsonBeneficiaryid = jsonEntity.getLong("id");

        if ((null != jsonBeneficiaryid) && !jsonBeneficiaryid.equals(beneficiaryid)) {
            //return Response.status(409).entity("userids differ!\n").build();
            throw new NotFoundException("beneficiaryids differ!\n");
        }

        final boolean newRecord = (null == beneficiaryEntity); // insert or update ?

        if (newRecord) { // new user record to be inserted
            beneficiaryEntity = new Beneficiary();
        }
        beneficiaryEntity.setIban(jsonEntity.getString("iban"));
        beneficiaryEntity.setName(jsonEntity.getString("name"));
        beneficiaryEntity.setDetails(jsonEntity.getString("details"));
        beneficiaryEntity.setAccountHolder(jsonEntity.getString("accountholder"));
        try {
			beneficiaryEntity.setCreationDate(Config.DATE_FORMAT_FULL.parse(((String)jsonEntity.optString("creationDate"))));
		} catch (ParseException e) {
			logger.warning(e.getMessage());
		}

        if (newRecord) {
        	beneficiaryEntity = beneficiaryController.create(beneficiaryEntity);
            //return Response.created(uriInfo.getAbsolutePath()).build();
        } else {
            beneficiaryController.save(beneficiaryEntity);
        	//return Response.noContent().build();
        }
        return   new JSONObject()
            .put("id", beneficiaryEntity.getId())
            .put("iban", beneficiaryEntity.getIban())
            .put("name", beneficiaryEntity.getName())
        	.put("details", beneficiaryEntity.getDetails())
        	.put("accountholder", beneficiaryEntity.getAccountHolder())
            .put("createDate", beneficiaryEntity.getCreationDate()).toString();
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public String updateBeneficiary(String jsonBeneficiary) throws JSONException {
    	JSONObject jsonEntity = new JSONObject(jsonBeneficiary);
    	Beneficiary beneficiaryEntity = beneficiaryController.findBeneficiary(jsonEntity.getLong("id"));
        beneficiaryEntity.setIban(jsonEntity.getString("iban"));
        beneficiaryEntity.setName(jsonEntity.getString("name"));
        beneficiaryEntity.setDetails(jsonEntity.getString("details"));
        beneficiaryEntity.setAccountHolder(jsonEntity.getString("accountholder"));
        Date creationDate=null;
		try {
			creationDate = Config.DATE_FORMAT_FULL.parse((String)jsonEntity.optString("createDate"));
		} catch (ParseException e) {
			logger.warning(e.getMessage());
		}
        beneficiaryEntity.setCreationDate(null ==creationDate ? new Date():creationDate);

        beneficiaryController.save(beneficiaryEntity);

        return   new JSONObject()
            .put("id", beneficiaryEntity.getId())
            .put("iban", beneficiaryEntity.getIban())
            .put("name", beneficiaryEntity.getName())
        	.put("details", beneficiaryEntity.getDetails())
        	.put("accountholder", beneficiaryEntity.getAccountHolder())
            .put("createDate", beneficiaryEntity.getCreationDate()).toString();
    }

    @DELETE
    public void deleteBeneficiary() {
        if (null == beneficiaryEntity) {
            throw new NotFoundException("beneficiaryid " + beneficiaryid + "does not exist!");
        }
        beneficiaryController.delete(beneficiaryid);
    }


    public String asString() {
        return toString();
    }

    public String toString() {
        return beneficiaryEntity.toString();
    }

    public BeneficiaryResource(Beneficiary beneficiaryEntity) {
        this.beneficiaryEntity = beneficiaryEntity;
    }

    public Beneficiary getBeneficiaryEntity() {
        return beneficiaryEntity;
    }
}
