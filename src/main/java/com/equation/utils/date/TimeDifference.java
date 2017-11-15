package com.equation.utils.date;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.joda.time.Minutes;

/**
 *
 * @author Wellington
 */
public class TimeDifference {

	public String getPeriod(String startDate, String endDate) {
		String period = null;
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

		Date d1 = null;
		Date d2 = null;

		try {
			d1 = format.parse(startDate);
			d2 = format.parse(endDate);

			DateTime dt1 = new DateTime(d1);
			DateTime dt2 = new DateTime(d2);
			period = "" + Hours.hoursBetween(dt1, dt2).getHours() % 24 + ","
					+ Minutes.minutesBetween(dt1, dt2).getMinutes() % 60;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return period;
	}

	public static void main(String[] args) {
		TimeDifference difference = new TimeDifference();
		System.out.println("Perion = " + difference.getPeriod("09:00:00", "09:29:58"));

	}
}