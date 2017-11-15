package com.equation.utils.time.period;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Wellington
 */
public class TimePeriod {
	public long getTimeTaken(String initalPeriod, String finalPeriod) {
		final long MILLI_TO_HOUR = 1000 * 60 * 60;
		long period = 0;
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		Date date1;
		try {
			date1 = format.parse(initalPeriod);
			Date date2 = format.parse(finalPeriod);
			long difference = (date2.getTime() - date1.getTime());
			period = (difference / MILLI_TO_HOUR);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return period;
	}
}