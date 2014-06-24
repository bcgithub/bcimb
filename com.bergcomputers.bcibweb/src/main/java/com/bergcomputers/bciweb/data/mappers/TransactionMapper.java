package com.bergcomputers.bciweb.data.mappers;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.bergcomputers.bcibweb.config.Config;
import com.bergcomputers.domain.Beneficiary;
import com.bergcomputers.domain.IBaseEntity;
import com.bergcomputers.domain.IBeneficiary;
import com.bergcomputers.domain.ITransaction;
import com.bergcomputers.domain.Transaction;

public class TransactionMapper implements IMapper<Transaction>{
	private AccountMapper accountMapper = new AccountMapper();

	public Transaction fromJSON(JSONObject jsonObject) throws Exception{
		Transaction trans = null;
		if (null != jsonObject){
			trans = new Transaction();
			trans.setId(jsonObject.optLong(IBaseEntity.id));
			trans.setCreationDate(Config.DATE_FORMAT_FULL.parse(jsonObject.optString(IBaseEntity.creationDate)));
			trans.setDate(Config.DATE_FORMAT_FULL.parse(jsonObject.getString(ITransaction.date)));
			trans.setAmount(jsonObject.getDouble(ITransaction.amount));
			trans.setDetails(jsonObject.optString(ITransaction.details));
			trans.setSender(jsonObject.getString(ITransaction.sender));
			trans.setStatus(jsonObject.getString(ITransaction.status));
			trans.setType(jsonObject.getString(ITransaction.type));
			trans.setAccount(accountMapper.fromJSON(jsonObject.getJSONObject(ITransaction.account)));
		}
		return trans;
	}
	public List<Transaction> fromJSONArray(JSONArray jsonArray) throws Exception{
		List<Transaction> list = null;
		if (null != jsonArray){
			list = new ArrayList<Transaction>();
			for(int i=0; i< jsonArray.length();i++){
				list.add(fromJSON((JSONObject)jsonArray.get(i)));
			}
		}
		return list;
	}

}
