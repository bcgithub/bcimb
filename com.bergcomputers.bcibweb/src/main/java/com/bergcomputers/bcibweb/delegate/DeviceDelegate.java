package com.bergcomputers.bcibweb.delegate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;

import com.bergcomputers.bcibweb.config.Config;
import com.bergcomputers.bcibweb.core.net.HTTPHelper;
import com.bergcomputers.bciweb.data.mappers.AccountMapper;
import com.bergcomputers.bciweb.data.mappers.DeviceMapper;
public class DeviceDelegate {
	private final static Logger logger = Logger.getLogger( DeviceDelegate.class.getName());

	private String baseRestURL;
	private CloseableHttpClient httpclient;

	public DeviceDelegate(String baseUrl) {
		
		httpclient = (CloseableHttpClient)HTTPHelper.createHTTPClient();
		baseRestURL = baseUrl;
	}

	public List<com.bergcomputers.domain.Device> getDevice() throws Exception{
		List< com.bergcomputers.domain.Device> DeviceList = new ArrayList< com.bergcomputers.domain.Device>();
		DeviceList.add(new com.bergcomputers.domain.Device());
		try {
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
					return DeviceMapper.fromJSON(array);
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
		}

		return DeviceList;
	}
}
