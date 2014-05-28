package com.bergcomputers.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Beneficiary.class)
public abstract class Beneficiary_ extends com.bergcomputers.domain.BaseEntity_ {

	public static volatile SingularAttribute<Beneficiary, String> details;
	public static volatile SingularAttribute<Beneficiary, String> name;
	public static volatile SingularAttribute<Beneficiary, String> accountholder;
	public static volatile SingularAttribute<Beneficiary, String> iban;

}

