package com.bergcomputers.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.bergcomputers.domain.Role;

@Stateless
public class RoleController implements IRoleController{
	@PersistenceContext
	EntityManager  em;
	
public RoleController(){
		
	}

	public Role getRole(long id){
		return em.find(Role.class, id);
	}
}
