package com.bergcomputers.ejb;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.bergcomputers.domain.Customer;

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

	@Override
	public Customer create(Customer customer) {
		if (null != customer && null == customer.getCreationDate()){
			customer.setCreationDate(new Date());
		}
		this.em.persist(customer);
		return customer;
	}

	/* (non-Javadoc)
	 * @see com.bergcomputers.ejb.ICustomerController#save(com.bergcomputers.domain.Customer)
	 */
	/*
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
		return this.em.find(Customer.class, id);
	}

	


	/* (non-Javadoc)
	 * @see com.bergcomputers.ejb.ICustomerController#delete(long)
	 */
	/*
	@Override
	public void delete(long customerid)
	{
		Customer item = findCustomer(customerid);
		if (item != null)
		{
			em.remove(item);
		}
	}
	*/

}

