package com.bergcomputers.bciweb.data.mappers;

import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

public interface IMapper<T> {
	T fromJSON(JSONObject jsonObject) throws Exception;
	List<T> fromJSONArray(JSONArray jsonArray) throws Exception;
}
