package com.bergcomputers.bcibpersistence;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import com.bergcomputers.domain.Account;

public class AccountEntityTest {
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Test
	public void testEquals(){
		Account acc1 = new Account();
		acc1.setIban("ro0112345");
		Account acc2 = new Account();
		acc2.setIban("ro0112345");
		assertEquals(acc1,acc2);
	}

	@Test
	public void testSettersAndToString() throws ParseException{
		Long id = new Long(1);
		String iban = "ro0112345";
		Double amount = 200.0;
		Date creationDate = sdf.parse("2014-03-29 10:15:00");
		Account acc = new Account();
		acc.setId(id);
		acc.setIban(iban);
		acc.setAmount(amount);
		acc.setCreationDate(creationDate);

		 String textRepr = "Account [id=" + acc.getId() + ", iban=" + acc.getIban() + ", amount=" + acc.getAmount()
			+ ", version=" + null + ", creationDate=" + acc.getCreationDate()
			+ "]";
		assertEquals(textRepr,acc.toString());
	}
}
