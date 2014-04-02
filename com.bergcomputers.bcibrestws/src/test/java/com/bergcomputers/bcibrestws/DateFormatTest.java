package com.bergcomputers.bcibrestws;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.junit.Test;

import com.bergcomputers.rest.config.Config;

public class DateFormatTest {

	@Test
	public void testFullFormat(){
		try {
			Date parsedDate = Config.DATE_FORMAT_FULL.parse("2014-01-25T08:01:02");
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
			calendar.setTime(parsedDate);
			assertEquals(2014, calendar.get(Calendar.YEAR));
			assertEquals(1, calendar.get(Calendar.MONTH)+1);
			assertEquals(25, calendar.get(Calendar.DAY_OF_MONTH));
			assertEquals(8, calendar.get(Calendar.HOUR_OF_DAY));
			assertEquals(1, calendar.get(Calendar.MINUTE));
			assertEquals(2, calendar.get(Calendar.SECOND));
		} catch (ParseException e) {
			fail("No ParseException shall be thrown");
		}
	}
	@Test(expected=ParseException.class)
	public void testFullDateFormatWrong() throws Exception{
			Date parsedDate = Config.DATE_FORMAT_FULL.parse("2014-01-25 08:01:02");
			fail("ParseException shall be thrown");
	}

}
