package com.bergcomputers.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Currency.class)
public abstract class Currency_ extends com.bergcomputers.domain.BaseEntity_ {

	public static volatile SingularAttribute<Currency, String> symbol;
	public static volatile SingularAttribute<Currency, Double> exchangerate;

}

