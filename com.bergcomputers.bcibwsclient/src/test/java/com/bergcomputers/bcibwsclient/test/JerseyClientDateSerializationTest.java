package com.bergcomputers.bcibwsclient.test;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/*import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;*/
import org.glassfish.jersey.client.ClientConfig;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bergcomputers.domain.Account;
import com.bergcomputers.domain.Currency;
import com.bergcomputers.domain.Customer;

public class JerseyClientDateSerializationTest {

	final static String UrlBase = "http://localhost:" + "8080"
			+ "/bcibws/rest/";
	private static  WebTarget wr = null;
	private static Client c = null;
	private DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
	//private static ObjectMapper om = null;
	@BeforeClass
	public static void setupClass() {
		ClientConfig cc = new ClientConfig();
		/*cc.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING,
				Boolean.TRUE);
		JacksonJsonProvider jacksonJsonProvider = new JacksonJaxbJsonProvider();
		om = jacksonJsonProvider.locateMapper(Account.class, MediaType.APPLICATION_JSON_TYPE);
		jacksonJsonProvider.configure(
						DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,
						false)

				.configure(
						SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS,
						true);
		cc.getSingletons().add(jacksonJsonProvider);*/
		c = ClientBuilder.newClient();

		wr = c.target(UrlBase);
	}

	@AfterClass
	public static void cleanup() {
		wr = null;
		c = null;
		//om = null;
	}

	@Test
	public void createAccount() {
		// creating an account
		Account acc = new Account();
		Date date = new Date();
		acc.setAmount(2000.0);
		acc.setIban("ro03bc1234");
		acc.setCreationDate(date);

		// creating a new currency to associate with the account
		Currency currency = new Currency();
		currency.setId(1L);
		acc.setCurrency(currency);

		Customer customer = new Customer();
		customer.setId(4L);
		acc.setCustomer(customer);

		/*String res;
		try {
			res = om.writeValueAsString(acc);
			System.out.println(res);

		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

		Response resp = wr.path("accounts").request(MediaType.APPLICATION_JSON)//.type("application/json")
				.post(Entity.json(acc));
		System.out.println("-----");
		Account accountResult =resp.readEntity(Account.class);

		// testing to see if the account is the same
		Assert.assertEquals(accountResult.getAmount(), acc.getAmount());
		Assert.assertEquals(accountResult.getIban(), acc.getIban());
		Assert.assertEquals(accountResult.getCreationDate().getTime(), acc
				.getCreationDate().getTime());
		Assert.assertEquals(accountResult.getCustomer().getId(), acc
				.getCustomer().getId());
		Assert.assertEquals(accountResult.getCurrency().getId(), acc
				.getCurrency().getId());

		// deleting the account
		System.out.println("Deleting test account:");
		System.out.println(accountResult.getId());
		wr.path("accounts/" + accountResult.getId()).request().delete();
		System.out.println("-----");

	}


}
