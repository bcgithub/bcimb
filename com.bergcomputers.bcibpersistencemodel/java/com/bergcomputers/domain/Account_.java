package com.bergcomputers.domain;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Account.class)
public abstract class Account_ {

	public static volatile SingularAttribute<Account, Double> amount;
	public static volatile SingularAttribute<Account, Long> id;
	public static volatile SingularAttribute<Account, Date> creationDate;
	public static volatile SingularAttribute<Account, Integer> version;
	public static volatile SingularAttribute<Account, String> iban;

}

