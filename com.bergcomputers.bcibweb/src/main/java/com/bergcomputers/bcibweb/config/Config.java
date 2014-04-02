package com.bergcomputers.bcibweb.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class Config {
	public final static String REST_SERVICE_ACCOUNTS_LIST="accounts";
	public final static String REST_SERVICE_BASE_URL="http://localhost:8080/bcibws/rest/";

	public static final DateFormat DATE_FORMAT;
	public static final DateFormat DATE_FORMAT_FULL;
	public static final DateFormat DATE_FORMAT_USER;
	public static final DateFormat TIME_FORMAT_USER;
	public static final int MILLISECONDS_IN_DAY = 1000 * 60 * 60 * 24;
	static {
		DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
		DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("UTC"));
		DATE_FORMAT_FULL = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		DATE_FORMAT_FULL.setTimeZone(TimeZone.getTimeZone("UTC"));
		DATE_FORMAT_USER = new SimpleDateFormat("MMM d, yyyy");
		DATE_FORMAT_USER.setTimeZone(TimeZone.getTimeZone("UTC"));
		TIME_FORMAT_USER = new SimpleDateFormat("hh:mm a");
		TIME_FORMAT_USER.setTimeZone(TimeZone.getTimeZone("UTC"));
	}

	public static String inputStreamToString(InputStream is) throws IOException {
		String line = "";
		StringBuilder total = new StringBuilder();
		// Wrap a BufferedReader around the InputStream
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		// Read response until the end
		while ((line = rd.readLine()) != null) {
			total.append(line);
		}
		// Return full string
		return total.toString();
	}
}
