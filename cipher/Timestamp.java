// The representation of a snapshot in time
// and the relevant values for that time (year, ..., second).
// When an object is constructed, all relevant values for
// the time when it was constructed are stored as integers.

package cipher;

import java.util.Calendar;
import java.text.SimpleDateFormat;

public class Timestamp {
	
	// Attributes
	
	private Calendar CALENDAR = Calendar.getInstance();
	private int year;
	private int month;
	private int day;
	private int hour;
	private int minute;
	private int second;
	
	// Constructor
	
	public Timestamp() {
		// Set all date variables.
		setTimeVars();
	}
	
	public Timestamp(String datestr) {
		// Uses an unformatted string output from
		// this class (see below) in order to
		// make a timestamp from a previously made
		// Timestamp object.
		CALENDAR.set(Integer.parseInt(datestr.substring(0,4)), 
				Integer.parseInt(datestr.substring(4,6)), 
				Integer.parseInt(datestr.substring(6,8)), 
				Integer.parseInt(datestr.substring(8,10)), 
				Integer.parseInt(datestr.substring(10,12)), 
				Integer.parseInt(datestr.substring(12,14)));
		setTimeVars();
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
		return CALENDAR;
	}
	
	// Variable assignment.
	
	private void setTimeVars() {
		year = CALENDAR.get(Calendar.YEAR);
		month = CALENDAR.get(Calendar.MONTH);
		day = CALENDAR.get(Calendar.DATE);
		hour = CALENDAR.get(Calendar.HOUR_OF_DAY); // 24-hour format.
		minute = CALENDAR.get(Calendar.MINUTE);
		second = CALENDAR.get(Calendar.SECOND);
	}
	
	// Returns the string.

	public String toUnformatted() {
		// No spaces, very unformatted String.
		// Used in the creation of a raw transmission String
		// for networking purposes.
		SimpleDateFormat date = new SimpleDateFormat("yyyyMMddHHmmss");
		return date.format(CALENDAR.getTime());
	}
	
	public String toString() {
		SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		return date.format(CALENDAR.getTime());
	}
	
}
