package com.bergcomputers.ejb;

import javax.ejb.Remote;

import com.bergcomputers.domain.Role;

@Remote
public interface IRoleController {
	public Role getRole(long id);
}
