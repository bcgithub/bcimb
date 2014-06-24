package com.bergcomputers.bciweb.data.mappers;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.bergcomputers.domain.IRole;
import com.bergcomputers.domain.Role;

public class RoleMapper {
	public static Role fromJSON(JSONObject jsonObject) throws Exception{
		Role role = null;
		if (null != jsonObject){
			role = new Role();
			role.setName(IRole.Name);

		}
		return role;
	}
	public static List<Role> fromJSON(JSONArray jsonArray) throws Exception{
		List<Role> roles = null;
		if (null != jsonArray){
			roles = new ArrayList<Role>();
			for(int i=0; i< jsonArray.length();i++){
				roles.add(fromJSON((JSONObject)jsonArray.get(i)));
			}
		}
		return roles;
	}

}

