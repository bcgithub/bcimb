package com.bergcomputers.bcibweb.delegate;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.http.impl.client.CloseableHttpClient;

import com.bergcomputers.bcibweb.core.net.HTTPHelper;
import com.bergcomputers.bcibweb.delegate.Customers;
public class CustomersDelegate {
	private final static Logger logger = Logger.getLogger(CustomersDelegate.class.getName());

	private String baseRestURL;
	private CloseableHttpClient httpclient;

	public CustomersDelegate(String baseUrl) {
		httpclient = (CloseableHttpClient)HTTPHelper.createHTTPClient();
		baseRestURL = baseUrl;
	}

	public List<Customers> getCustomers() throws Exception{
		List<Customers> customersList = new ArrayList<Customers>();
		String name;
		String lastName;
		String login;
		customersList.add(new Customers());
		/*try {
			HttpGet httpget = new HttpGet(baseRestURL
					+ Config.REST_SERVICE_ACCOUNTS_LIST);
			System.out.println("executing request " + httpget.getURI());
			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				//HttpEntity entity = response.getEntity();

				// Execute HTTP Post Request
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					String jsonString = Config.inputStreamToString(response.getEntity().getContent());
					System.out.println(jsonString);
					JSONArray array = new JSONArray(jsonString);
					System.out.println(jsonString);
					return AccountMapper.fromJSON(array);
				} else {
					 System.out.println("Error: "+response.getStatusLine().getStatusCode()
					 + " : " + response.getStatusLine().getReasonPhrase());
					String errorString = Config.inputStreamToString(response.getEntity().getContent());
					System.out.println(errorString);

				}

			} catch (JSONException e) {
				logger.severe(e.getMessage());
				throw e;
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/

		return customersList;
	}
}
