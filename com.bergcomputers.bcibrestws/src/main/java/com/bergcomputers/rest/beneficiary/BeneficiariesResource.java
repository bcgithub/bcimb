package com.bergcomputers.rest.beneficiary;

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

import com.bergcomputers.domain.Account;
import com.bergcomputers.domain.Beneficiary;
import com.bergcomputers.ejb.IAccountController;
import com.bergcomputers.ejb.IBeneficiaryController;
import com.bergcomputers.rest.beneficiary.BeneficiaryResource;

@Stateless
@Path("beneficiaries")
public class BeneficiariesResource {
	@Context
    private UriInfo uriInfo;

    @EJB
    private IBeneficiaryController beneficiaryController;

    //private DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");

    /**
     * Default constructor.
     */
    public BeneficiariesResource() {
        // TODO Auto-generated constructor stub
    }

   /* @Path("{accountid}")
    public BeneficiaryResource getBeneficiary(@PathParam("beneficiaryid") Long accountid) {
        return new BeneficiaryResource(uriInfo, accountController, accountid);
    }

    @GET
    @Path("/detail/{accountid}")
    @Produces("application/json")
    public Beneficiary getBeneficiaryDetails(@PathParam("accountid") Long accountid) {
        return accountController.findBeneficiary(accountid);
    }

    @GET
    @Produces("application/json")
    @Path("uris")
    public String getBeneficiaryURIs() {
        JSONArray uriArray = new JSONArray();
             for (Beneficiary account :  beneficiaryController.getBeneficiary()) {
                UriBuilder ub = uriInfo.getAbsolutePathBuilder();
                URI beneficiaryUri = ub.path(((Beneficiary)beneficiary).getId().toString()).build();
                uriArray.put(beneficiaryUri.toASCIIString());
            }
        return uriArray.toString();
    }*/

    @GET
    @Produces("application/json")
    public List<Beneficiary> getBeneficiary() {
    	return beneficiaryController.getBeneficiary();
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
    } 

    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public Account createAccount(final Account jsonAccount) throws JSONException {

    	jsonAccount.setCreationDate(null ==jsonAccount.getCreationDate() ? new Date():jsonAccount.getCreationDate());
    	Account accountEntity = accountController.create(jsonAccount);
        return accountEntity;
    }*/

}
