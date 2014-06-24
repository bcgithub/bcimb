package com.bergcomputers.bciweb.data.mappers;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.bergcomputers.bcibweb.config.Config;
import com.bergcomputers.domain.Account;
import com.bergcomputers.domain.Account_;
import com.bergcomputers.domain.IAccount;
import com.bergcomputers.domain.IBaseEntity;

public class AccountMapper implements IMapper<Account>{
	public Account fromJSON(JSONObject jsonObject) throws Exception{
		Account account = null;
		if (null != jsonObject){
			account = new Account();
			account.setId(jsonObject.optLong(IBaseEntity.id));
			account.setCreationDate(Config.DATE_FORMAT_FULL.parse(jsonObject.optString(IAccount.creationDate)));
			account.setAmount(jsonObject.optDouble(IAccount.amount));
			account.setIban(jsonObject.optString(IAccount.iban));
		}
		return account;
	}
	public List<Account> fromJSONArray(JSONArray jsonArray) throws Exception{
		List<Account> accounts = null;
		if (null != jsonArray){
			accounts = new ArrayList<Account>();
			for(int i=0; i< jsonArray.length();i++){
				accounts.add(fromJSON((JSONObject)jsonArray.get(i)));
			}
		}
		return accounts;
	}

}
