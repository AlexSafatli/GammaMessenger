// The representation of a snapshot in time
// and the relevant values for that time (year, ..., second).
// When an object is constructed, all relevant values for
// the time when it was constructed are stored as integers.

package lib;

import java.util.Calendar;
import java.text.SimpleDateFormat;

public class Timestamp {
	
	// Attributes
	
	private final Calendar calendar = Calendar.getInstance();
	private int year;
	private int month;
	private int day;
	private int hour;
	private int minute;
	private int second;
	
	// Constructor
	
	public Timestamp() {
		// Set all date variables.
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH);
		day = calendar.get(Calendar.DATE);
		hour = calendar.get(Calendar.HOUR);
		minute = calendar.get(Calendar.MINUTE);
		second = calendar.get(Calendar.SECOND);
	}
	
	// Get methods.

	public int getYear() {
		return year;
	}

	public int getMonth() {
		return month;
	}

	public int getDay() {
		return day;
	}

	public int getHour() {
		return hour;
	}

	public int getMinute() {
		return minute;
	}

	public int getSecond() {
		return second;
	}
	
	public Calendar getCalendarInstance() {
		return calendar;
	}
	
	// Returns the string.

	public String toString() {
		SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		return date.format(calendar.getTime());
	}
	
}
