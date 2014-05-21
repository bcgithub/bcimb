package com.bergcomputers.domain;

public interface IDevice {

	public abstract String getName();

	public abstract void setName(String name);

	public abstract String getDeviceId();

	public abstract void setDeviceId(String deviceId);

	public abstract ICustomer getCustomer();

	public abstract void setCustomer(ICustomer customer);

}