package com.bergcomputers.domain;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Transaction.class)
public abstract class Transaction_ extends com.bergcomputers.domain.BaseEntity_ {

	public static volatile SingularAttribute<Transaction, String> sender;
	public static volatile SingularAttribute<Transaction, Double> amount;
	public static volatile SingularAttribute<Transaction, String> details;
	public static volatile SingularAttribute<Transaction, String> status;
	public static volatile SingularAttribute<Transaction, Account> account;
	public static volatile SingularAttribute<Transaction, String> type;
	public static volatile SingularAttribute<Transaction, Date> date;

}

