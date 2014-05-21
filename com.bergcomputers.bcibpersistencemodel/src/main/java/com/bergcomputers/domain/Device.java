package com.bergcomputers.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Device extends BaseEntity implements IDevice {
	
	private String name;
	private String deviceId;
	@ManyToOne
	private ICustomer customer;
	/* (non-Javadoc)
	 * @see com.bergcomputers.domain.IDevice#getName()
	 */
	@Override
	public String getName() {
		return name;
	}
	/* (non-Javadoc)
	 * @see com.bergcomputers.domain.IDevice#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}
	/* (non-Javadoc)
	 * @see com.bergcomputers.domain.IDevice#getDeviceId()
	 */
	@Override
	public String getDeviceId() {
		return deviceId;
	}
	/* (non-Javadoc)
	 * @see com.bergcomputers.domain.IDevice#setDeviceId(java.lang.String)
	 */
	@Override
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	/* (non-Javadoc)
	 * @see com.bergcomputers.domain.IDevice#getCustomer()
	 */
	@Override
	public ICustomer getCustomer() {
		return customer;
	}
	/* (non-Javadoc)
	 * @see com.bergcomputers.domain.IDevice#setCustomer(com.bergcomputers.domain.Customer)
	 */
	@Override
	public void setCustomer(ICustomer customer) {
		this.customer = customer;
	}
}