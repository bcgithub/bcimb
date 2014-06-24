package com.bergcomputers.bciweb.data.mappers;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.bergcomputers.bcibweb.config.Config;
import com.bergcomputers.domain.Account;
import com.bergcomputers.domain.Beneficiary;
import com.bergcomputers.domain.IAccount;
import com.bergcomputers.domain.IBaseEntity;
import com.bergcomputers.domain.IBeneficiary;

public class BeneficiaryMapper implements IMapper<Beneficiary>{
	public Beneficiary fromJSON(JSONObject jsonObject) throws Exception{
		Beneficiary benef = null;
		if (null != jsonObject){
			benef = new Beneficiary();
			benef.setId(jsonObject.optLong(IBaseEntity.id));
			benef.setCreationDate(Config.DATE_FORMAT_FULL.parse(jsonObject.optString(IBaseEntity.creationDate)));
			benef.setName(jsonObject.getString(IBeneficiary.name));
			benef.setDetails(jsonObject.optString(IBeneficiary.details));
			benef.setIban(jsonObject.getString(IBeneficiary.iban));
			benef.setAccountHolder(jsonObject.getString(IBeneficiary.accountholder));
		}
		return benef;
	}
	public List<Beneficiary> fromJSONArray(JSONArray jsonArray) throws Exception{
		List<Beneficiary> list = null;
		if (null != jsonArray){
			list = new ArrayList<Beneficiary>();
			for(int i=0; i< jsonArray.length();i++){
				list.add(fromJSON((JSONObject)jsonArray.get(i)));
			}
		}
		return list;
	}

}
