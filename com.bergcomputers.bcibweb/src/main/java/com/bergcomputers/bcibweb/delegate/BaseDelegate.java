package com.bergcomputers.bcibweb.delegate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;

import com.bergcomputers.bcibweb.config.Config;
import com.bergcomputers.bcibweb.core.net.HTTPHelper;
import com.bergcomputers.bciweb.data.mappers.IMapper;

public abstract class BaseDelegate<T> {
	private final static Logger logger = Logger.getLogger(BaseDelegate.class.getName());
	protected  CloseableHttpClient httpclient;
	protected String baseUrl;

	public BaseDelegate(String baseUrl){
		httpclient = (CloseableHttpClient)HTTPHelper.createHTTPClient();
		this.baseUrl = baseUrl;
	}
	public abstract IMapper<T> getMapper();

	public List<T> getList(String url) throws Exception{
			List<T> listOfEntities = new ArrayList<T>();

			try {
				HttpGet httpget = new HttpGet(url);
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
						listOfEntities = getMapper().fromJSONArray(array);
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
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					httpclient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			return listOfEntities;
		}
	
	public void delete(String url) throws Exception{
		HttpDelete httpget = new HttpDelete(url);
		System.out.println("executing request " + httpget.getURI());
		CloseableHttpResponse response = httpclient.execute(httpget);
		response.close();
	}
}
