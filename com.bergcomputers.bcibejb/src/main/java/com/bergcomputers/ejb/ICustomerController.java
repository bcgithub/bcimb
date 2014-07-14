package com.bergcomputers.ejb;

import java.util.List;

import javax.ejb.Remote;

import com.bergcomputers.domain.Customer;
@Remote
public interface ICustomerController {
	public  List <Customer> getCustomers();
	public Customer findCustomer(long id);
	void delete(long customerid);
	Customer create(Customer customer);
	Customer update(Customer customer);
}
