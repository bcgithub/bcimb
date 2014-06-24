package com.bergcomputers.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Device.class)
public abstract class Device_ extends com.bergcomputers.domain.BaseEntity_ {

	public static volatile SingularAttribute<Device, String> name;
	public static volatile SingularAttribute<Device, Customer> customer;
	public static volatile SingularAttribute<Device, String> deviceId;

}

