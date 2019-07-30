package inesc_id.gsd.bibtrek.app.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {
	
	private TimeUtils() {
		
	}
	
	public static String getCurrentTimeString() {
		String dateString;
		Date date;
		SimpleDateFormat format;
		
		date = new Date();
		format = new SimpleDateFormat("ddMMyyyy_HHmmssSSSSSS");	
		dateString = format.format(date);
		
		return dateString;
	}
}
