package com.bergcomputers.bciweb.data.mappers;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.bergcomputers.bcibweb.config.Config;
import com.bergcomputers.domain.Account;
import com.bergcomputers.domain.Customer;
import com.bergcomputers.domain.IAccount;
import com.bergcomputers.domain.ICustomer;

public class CustomerMapper {
	public static Customer fromJSON(JSONObject jsonObject) throws Exception{
		Customer customer = null;
		if (null != jsonObject){
			customer = new Customer();
			customer.setFirstName(ICustomer.firstName);
			customer. setLastName(ICustomer.lastName);
			customer.setLogin(ICustomer.login);
		}
		return customer;
	}
	public static List<Customer> fromJSON(JSONArray jsonArray) throws Exception{
		List<Customer> customers = null;
		if (null != jsonArray){
			customers = new ArrayList<Customer>();
			for(int i=0; i< jsonArray.length();i++){
				customers.add(fromJSON((JSONObject)jsonArray.get(i)));
			}
		}
		return customers;
	}

}
