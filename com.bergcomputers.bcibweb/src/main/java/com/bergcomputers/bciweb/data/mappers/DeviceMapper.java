package com.bergcomputers.bciweb.data.mappers;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.bergcomputers.bcibweb.config.Config;
import com.bergcomputers.domain.Account;
import com.bergcomputers.domain.Customer;
import com.bergcomputers.domain.Device;
import com.bergcomputers.domain.IAccount;
import com.bergcomputers.domain.ICustomer;
import com.bergcomputers.domain.IDevice;

public class DeviceMapper {
	public static Device fromJSON(JSONObject jsonObject) throws Exception{
		Device device = null;
		if (null != jsonObject){
			device = new Device();
			device.setDeviceId(IDevice.id);
			device.setName(IDevice.Name);
		}
		return device;
	}
	public static List<Device> fromJSON(JSONArray jsonArray) throws Exception{
		List<Device> devices = null;
		if (null != jsonArray){
			devices = new ArrayList<Device>();
			for(int i=0; i< jsonArray.length();i++){
				devices.add(fromJSON((JSONObject)jsonArray.get(i)));
			}
		}
		return devices;
	}

}

