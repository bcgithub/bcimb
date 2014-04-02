package com.bergcomputers.rest.config;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class Config {
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
}
