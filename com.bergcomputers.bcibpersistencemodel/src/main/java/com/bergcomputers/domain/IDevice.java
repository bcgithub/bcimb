package com.bergcomputers.domain;

public interface IDevice {

	public abstract String getName();

	public abstract void setName(String name);

	public abstract String getDeviceId();

	public abstract void setDeviceId(String deviceId);

	public abstract Customer getCustomer();

	public abstract void setCustomer(Customer customer);

}