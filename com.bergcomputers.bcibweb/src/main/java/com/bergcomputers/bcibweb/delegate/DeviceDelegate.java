package com.bergcomputers.bcibweb.delegate;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.http.impl.client.CloseableHttpClient;

import com.bergcomputers.bcibweb.core.net.HTTPHelper;
import com.bergcomputers.bcibweb.delegate.Role;;
public class DeviceDelegate {
	private final static Logger logger = Logger.getLogger( DeviceDelegate.class.getName());

	private String baseRestURL;
	private CloseableHttpClient httpclient;

	public DeviceDelegate(String baseUrl) {
		
		httpclient = (CloseableHttpClient)HTTPHelper.createHTTPClient();
		baseRestURL = baseUrl;
	}

	public List< Device> getDevice() throws Exception{
		List< Device> DeviceList = new ArrayList< Device>();
		DeviceList.add(new Device());
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

		return DeviceList;
	}
}
