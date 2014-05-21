package com.bergcomputers.domain;

import javax.persistence.Entity;

@Entity
public class Role extends BaseEntity implements IRole {
	private String name;

	/* (non-Javadoc)
	 * @see com.bergcomputers.domain.IRole#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see com.bergcomputers.domain.IRole#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}
	
}
