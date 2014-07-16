package com.bergcomputers.ejb;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.bergcomputers.domain.Account;
import com.bergcomputers.domain.Currency;
import com.bergcomputers.domain.Customer;
import com.bergcomputers.domain.Role;

/**
 * Session Bean implementation class AccountController
 */
@Stateless
public class CustomerController implements ICustomerController{
	
	@PersistenceContext
	EntityManager  em;
	
	public CustomerController(){
		
	}
	
	public List<Customer> getCustomers()
	{
		return this.em.createNamedQuery(Customer.findAll).getResultList();
	}
	/*
	@PostConstruct
	public void init(){
	}
	*/

	@Override
	public Customer create(Customer customer) {
		if (customer != null){
			customer = em.merge(customer);
			em.persist(customer);
			em.flush();
			return customer;
		}else
			return null;
	}

	/* (non-Javadoc)
	 * @see com.bergcomputers.ejb.ICustomerController#save(com.bergcomputers.domain.Customer)
	 

	@Override
	
	public void save(Customer customer)
	{
			this.em.merge(customer);
	}

	/* (non-Javadoc)
	 * @see com.bergcomputers.ejb.ICustomerController#findCustomer(long)
	 */
	
	@Override
	public Customer findCustomer(long id)
	{
		Customer item = em.find(Customer.class, id);
		return item;
	}

	


	/* (non-Javadoc)
	 * @see com.bergcomputers.ejb.ICustomerController#delete(long)
	 */
	@Override
	public void delete(long customerid)
	{
		Customer item = findCustomer(customerid);
		if (item != null)
		{
			em.remove(item);
		}
	}
	
	@Override
	public Customer update(Customer customer){
		Customer  cust = (Customer)em.find(Customer.class ,customer.getId());
		if(cust != null){
			if(customer.getRole() != null){
				Role role = new Role();
				role = em.find(Role.class, customer.getRole().getId());
				cust.setRole(role);
			}
		   cust.setFirstName(customer.getFirstName());
		   cust.setLastName(customer.getLastName());
		   cust.setLogin(customer.getLogin());
		   cust.setPassword(customer.getPassword());
		   cust.setCreationDate(customer.getCreationDate());
		   return cust;}
		else
			return null;
	}
	

}


