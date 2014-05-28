package com.bergcomputers.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Customer.class)
public abstract class Customer_ extends com.bergcomputers.domain.BaseEntity_ {

	public static volatile SingularAttribute<Customer, String> lastName;
	public static volatile SingularAttribute<Customer, Role> role;
	public static volatile SingularAttribute<Customer, String> login;
	public static volatile SingularAttribute<Customer, String> firstName;
	public static volatile SingularAttribute<Customer, String> password;

}

