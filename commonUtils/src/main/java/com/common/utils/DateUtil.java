package com.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.common.dao.SysDateDao;

public class DateUtil {
	// private static Logger log = Logger.getLogger(DateUtil.class);

	public final static String DATE_FORMAT = "yyyy-MM-dd";
	public final static String DATETIME_FORMAT = "yyyy-MM-dd HH:mm";
	public final static String DATETIME_AM_FORMAT = "yyyy-MM-dd hh:mm aa";
	public final static String DATETIME_FORMAT_HH_MM_SS_AA = "yyyy-MM-dd hh:mm ss aa";
	public final static String DATETIME_FORMAT_HH_MM_SS = "yyyy-MM-dd hh:mm:ss ";
	public final static String EN_DATE_FORMAT_DD_MMM = "dd MMM";
	public final static String DATE_FORMAT_YYMMDDHHMM = "yyMMddHHmm";
	public final static String DATE_FORMAT_YYYYMMDDHHMMSS = "yyyyMMddhhmmss";
	public final static String DATE_FORMAT_DDMMYYYYHHMM = "dd/MM/yyyy HH:mm";
	public final static String DATE_FORMAT_DDMMYYYY = "dd/MM/yyyy";
	public final static String DATE_FORMAT_MMDD = "MMdd";
	public final static String DATE_FORMAT_YYYY_MM = "yyyy-MM";
	public final static String DATE_FORMAT_HH_MM = "HH:mm";
	protected static final Map<String, String> DAY_OF_WEEK;
	static {
		DAY_OF_WEEK = new HashMap<String, String>();
		DAY_OF_WEEK.put("1", "Sun");
		DAY_OF_WEEK.put("2", "Mon");
		DAY_OF_WEEK.put("3", "Tue");
		DAY_OF_WEEK.put("4", "Wed");
		DAY_OF_WEEK.put("5", "Thu");
		DAY_OF_WEEK.put("6", "Fri");
		DAY_OF_WEEK.put("7", "Sat");

	}

	public static String getCurrentYear() {
		String currentDate = formatCurrentDate();
		String year = currentDate.substring(0, 4);
		return year;

	}

	public static String getCurrentMonth() {
		String currentDate = formatCurrentDate();
		String month = currentDate.substring(5, 7);
		return month;

	}

	public static String formatEnDate(Date date) {
		SimpleDateFormat format = new SimpleDateFormat(EN_DATE_FORMAT_DD_MMM);
		return format.format(date);
	}

	public static Date convertStringToDate(String dateStr) {
		try {
			SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
			Date date = format.parse(dateStr);
			return date;
		} catch (ParseException pe) {
			return null;
		}
	}

	public static Date convertStringToDate(String dateStr, String dateFormat) {
		try {
			SimpleDateFormat format = new SimpleDateFormat(dateFormat);
			Date date = format.parse(dateStr);
			return date;
		} catch (ParseException pe) {
			return null;
		}
	}

	public static Date convertToDate(Date date) {
		try {
			if (date != null) {
				SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
				String dateStr = format.format(date);
				return format.parse(dateStr);
			} else {
				return null;
			}
		} catch (ParseException pe) {
			return null;
		}
	}

	public static Date getDateAfter(Date d, int day) {
		SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
		return convertStringToDate(format.format(now.getTime()));
	}

	public static Date getDateMonthNumber(int monthNumber) {
		SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, monthNumber);
		return convertStringToDate(format.format(c.getTime()));
	}

	public static String formatCurrentDate() {
		SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
		// return format.format(new Date());
		return format.format(new SysDateDao().getSysDate());
	}

	public static String formatDate(Date date) {
		if (date != null) {
			SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
			return format.format(date);
		}
		return null;
	}

	public static String formatDate(Date date, String dateFormat) {
		if (date != null) {
			SimpleDateFormat format = new SimpleDateFormat(dateFormat);
			return format.format(date);
		}
		return null;
	}

	public static String formatDateTime(Date date) {
		if (date != null) {
			SimpleDateFormat format = new SimpleDateFormat(DATETIME_FORMAT_HH_MM_SS_AA);
			return format.format(date);
		}
		return null;
	}

	public static java.sql.Date convertToDBDate(Date date) {
		if (date != null) {
			java.sql.Date sqlDate = new java.sql.Date(date.getTime());
			return sqlDate;
		}
		return null;
	}

	public static Date convertStringToDateAM(String dateStr) {
		try {
			SimpleDateFormat format = new SimpleDateFormat(DATETIME_AM_FORMAT);
			Date date = format.parse(dateStr);
			return date;
		} catch (ParseException pe) {
			return null;
		}
	}

	public static java.sql.Timestamp convertToDBDate(Date date, String strAM) {
		if (date != null) {
			String dateStr;
			SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
			dateStr = format.format(date);

			if (strAM == null || (strAM != null && strAM.trim().toUpperCase().equals("AM"))) {
				dateStr = dateStr + " 1:00 AM";
			}
			if (strAM != null && strAM.trim().toUpperCase().equals("PM")) {
				dateStr = dateStr + " 1:00 PM";
			}

			date = convertStringToDateAM(dateStr);

			java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(date.getTime());

			return sqlTimestamp;
		}
		return null;
	}

	// public static void main(String[] strArry){
	// convertToDBDate(new Date(),ConstantsUtil.STR_PM);
	// }
	public static java.sql.Timestamp convertToDBTimestamp(Date date) {
		if (date != null) {
			java.sql.Timestamp sqlDate = new java.sql.Timestamp(date.getTime());
			return sqlDate;
		}
		return null;
	}

	public static List<String> getYearList() {
		List<String> list = new ArrayList<String>();
		Calendar c = Calendar.getInstance();
		int year1 = c.get(Calendar.YEAR);
		c.add(Calendar.YEAR, -1);
		int year0 = c.get(Calendar.YEAR);
		c.add(Calendar.YEAR, 2);
		int year2 = c.get(Calendar.YEAR);
		list.add(String.valueOf(year0));
		list.add(String.valueOf(year1));
		list.add(String.valueOf(year2));
		return list;
	}

	public static List<Integer> getPICYearList() {
		List<Integer> list = new ArrayList<Integer>();
		Calendar c = Calendar.getInstance();
		for (int i = 0; i < 5; i++) {
			int year = c.get(Calendar.YEAR);
			c.add(Calendar.YEAR, +1);
			list.add(year);
		}
		return list;
	}

	public static List<String> getYearList(int totalYears) {
		List<String> yearList = new ArrayList<String>();
		Calendar c = Calendar.getInstance();
		for (int i = 0; i < totalYears; i++) {
			int year = c.get(Calendar.YEAR);
			yearList.add(String.valueOf(year));
			c.add(Calendar.YEAR, -1);
		}
		return yearList;
	}

	public static List<String> getMonthList() {
		List<String> list = new ArrayList<String>();
		list.add("01");
		list.add("02");
		list.add("03");
		list.add("04");
		list.add("05");
		list.add("06");
		list.add("07");
		list.add("08");
		list.add("09");
		list.add("10");
		list.add("11");
		list.add("12");
		return list;
	}

	public static Map<String, String> getMonthMap() {
		Map<String, String> map = new Hashtable<String, String>();
		map.put("01", "Jan");
		map.put("02", "Feb");
		map.put("03", "Mar");
		map.put("04", "Apr");
		map.put("05", "May");
		map.put("06", "Jun");
		map.put("07", "Jul");
		map.put("08", "Aug");
		map.put("09", "Sep");
		map.put("10", "Oct");
		map.put("11", "Nov");
		map.put("12", "Dec");
		return map;
	}

	public static long getLongDateNumber() {
		// Date date = new Date();
		Date date = new SysDateDao().getSysDate();
		return date.getTime();
	}

	/** convert string "Fri Apr 11 14:01:32 CST 2008" to 2008-07-11 **/
	@Deprecated
	public static String convertCSTtoDate(String CSTFormat) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		String formatedDate = dateFormat.format(new Date(CSTFormat));
		return formatedDate;
	}

	public static String getDaysNameOfWeek(String daysOfWeek) {
		String[] days = daysOfWeek.split(",");
		String daysNameOfWeek = "";
		if (days != null) {
			for (String key : days) {
				daysNameOfWeek = daysNameOfWeek + " " + DAY_OF_WEEK.get(key);
			}
		}
		return daysNameOfWeek;
	}

	public static Date convertOracleTimpstamp(Object object) {
		Date convertDate = null;
		if (object instanceof oracle.sql.TIMESTAMP) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				convertDate = sdf.parse(object.toString().substring(0, 19));
			} catch (Exception e) {
				return null;
			}
		}
		return convertDate;
	}
}
