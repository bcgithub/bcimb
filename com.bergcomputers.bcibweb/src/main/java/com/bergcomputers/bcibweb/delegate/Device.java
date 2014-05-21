package com.bergcomputers.bcibweb.delegate;

public class Device {
	private String name ;
	private String deviceId;
	
	public Device(){
		this.name="Device";
		this.deviceId="1";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
}