package com.ppsea.ds.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	public static final long ONE_DAY = 86400L;
	public static final long ONE_HOUR = 3600;
	public static final long ONE_MINUTE = 60;
	public static final long ONE_MILLISECOND = 1000L;
	public static String distanceOfTimeInWords(long fromTime, long toTime, String format) {
		return distanceOfTimeInWords(new Date(fromTime), new Date(toTime), format, 7);
	}

	public static String distanceOfTimeInWords(long fromTime, long toTime, String format, int days) {
		return distanceOfTimeInWords(new Date(fromTime), new Date(toTime), format, days);
	}

	public static String distanceOfTimeInWords(long fromTime, long toTime, int days) {
		return distanceOfTimeInWords(new Date(fromTime), new Date(toTime), "MM-dd HH:mm", days);
	}

	public static String distanceOfTimeInWords(long fromTime, long toTime) {
		return distanceOfTimeInWords(new Date(fromTime), new Date(toTime), "MM-dd HH:mm", 7);
	}

	public static String distanceOfTimeInWords(Date fromTime, Date toTime, int days) {
		return distanceOfTimeInWords(fromTime, toTime, "MM-dd HH:mm", days);
	}

	public static String distanceOfTimeInWords(Date fromTime, Date toTime, String format) {
		return distanceOfTimeInWords(fromTime, toTime, format, 7);
	}

	public static String distanceOfTimeInWords(Date fromTime, Date toTime) {
		return distanceOfTimeInWords(fromTime, toTime, "MM-dd HH:mm", 7);
	}

	/**
	 * 截止时间时间到起始时间间隔的时间描述
	 * @param fromTime 起始时间
	 * @param toTime 截止时间
	 * @param format 格式化
	 * @param days 超过此天数，将按format格式化显示实际时间
	 * @return
	 */
	public static String distanceOfTimeInWords(Date fromTime, Date toTime, String format, int days) {
		long distanceInMinutes = (toTime.getTime() - fromTime.getTime()) / 60000;
		String message = "";
		if (distanceInMinutes == 0) {
			message = "几秒钟前";
		} else if (distanceInMinutes >= 1 && distanceInMinutes < 60) {
			message = distanceInMinutes + "分钟前";
		} else if (distanceInMinutes >= 60 && distanceInMinutes < 1440) {
			message = (distanceInMinutes / 60) + "小时前";
		} else if (distanceInMinutes >= 1440 && distanceInMinutes <= (1440 * days)) {
			message = (distanceInMinutes / 1440) + "天前";
		} else {
			message = new SimpleDateFormat(format).format(fromTime);
		}
		return message;
	}

	public static void main(String[] args) {
		Calendar fc = Calendar.getInstance();
		fc.add(Calendar.DAY_OF_MONTH, -1);
		fc.set(Calendar.HOUR_OF_DAY, 16);
		fc.set(Calendar.MINUTE, 0);
		Date from = fc.getTime();
		System.out.println(from);

		Calendar tc = Calendar.getInstance();
		Date to = tc.getTime();
		System.out.println(DateUtil.distanceOfTimeInWords(from, to, 20));
		System.out.println(DateUtil.distanceOfTimeInWords(System.currentTimeMillis() - 120*60 * 60 * 1000, System.currentTimeMillis()));
	}

	/**
	 * 当前日期是否在起止范围内
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static boolean isInDateRange(String start, String end) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		try {
			return isInDateRange(sf.parse(start), sf.parse(end));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 当前日期是否在起止范围内
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static boolean isInDateRange(Date start, Date end) {
		long now = System.currentTimeMillis() / 1000L;
		long st = start.getTime() / 1000L;
		long et = end.getTime() / 1000L + ONE_DAY;
		return (now >= st && now <= et) ? true : false;
	}

	public static boolean startTime(String start){
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return isStart(sf.parse(start));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 是否在规定时间之后
	 * @param start
	 * @return
	 */
	public static boolean isStartTime(String start) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
		try {
			return isStart(sf.parse(start));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean isStart(Date start) {
		long now = System.currentTimeMillis() / 1000L;
		long st = start.getTime() / 1000L;
		return (now >= st ) ? true : false;
	}
	/**
	 * 当前时间秒
	 * 
	 * @return 当前时间秒
	 */
	public static int now() {
		return (int) (System.currentTimeMillis() / ONE_MILLISECOND);
	}

	public static Date getNow(String date){
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
		try {
			return sf.parse(date);
		} catch (ParseException e) {
		}
		return null;
	}
	
	/**
	 * 是否是同一天
	 * @param time1		ms
	 * @param time2		ms
	 * @return
	 */
	public static boolean isSameDay(long time1, long time2) {
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTimeInMillis(time1);
		
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTimeInMillis(time2);
		
		if ( calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) && calendar1.get(Calendar.DAY_OF_YEAR) == calendar2.get(Calendar.DAY_OF_YEAR) ) {
			return true;
		}
		return false;
	}
	
	public static String getTimeDesc(int timeSec) {
		if ( timeSec <= 0 ) {
			return "0秒";
		}
		int oneDay = 60 * 60 * 24;
		int days = timeSec / oneDay;
		timeSec -= days * oneDay;
		int hours = timeSec / 3600;
		timeSec -= hours * 3600;
		int min = timeSec / 60;
		timeSec -= min * 60;
		return days + "天" + hours + "小时" + min + "分钟" + timeSec + "秒";
	}
	
	/**
	 * 间隔实际天数
	 * @param millis1
	 * @param millis2
	 * @return
	 */
	public static int getIntervalDays(long millisMin, long millisMax) {
		Calendar minCalendar = Calendar.getInstance();
		Calendar maxCalendar = Calendar.getInstance();
		minCalendar.setTimeInMillis(millisMin);
		maxCalendar.setTimeInMillis(millisMax);
		if ( millisMin > millisMax ) {
			minCalendar.setTimeInMillis(millisMax);
			maxCalendar.setTimeInMillis(millisMin);
		}
		int intervalYear = maxCalendar.get(Calendar.YEAR) - minCalendar.get(Calendar.YEAR);
		int intervalDay = maxCalendar.get(Calendar.DAY_OF_YEAR) - minCalendar.get(Calendar.DAY_OF_YEAR);
		for ( int i=0; i<intervalYear; i++ ) {
			minCalendar.set(Calendar.YEAR, minCalendar.get(Calendar.YEAR) + 1);
			intervalDay += minCalendar.getMaximum(Calendar.DAY_OF_YEAR);
		}
		return intervalDay;
	}
	
	public static String getDateDesc(long millis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH)+1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
	}
	
	public static String getDateDesc(){
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		String day = sf.format(new Date());
		return day;
	}
	
	/**
	 * 是否在指定时间范围之内
	 * 每天的定时，如：每天的12：30-14：30
	 * @param begin 	1230
	 * @param end 		1430
	 * @return
	 */
	public static boolean isBetweenTime(String begin,String end){
		try {
			int bg = Integer.parseInt(begin);
			int en = Integer.parseInt(end);
			Calendar calendar = Calendar.getInstance();
			int now = Integer.parseInt(calendar.get(Calendar.HOUR_OF_DAY) + "" + calendar.get(Calendar.MINUTE));
			if (now >= bg && now <= en) {
				return true;
			}
		} catch (Exception e) {
		}
		return false;
	}
	
	 /**
	  * 倒计时  时间展示
	  * 格式00：00：00
	 * @param time
	 * @return
	 */
	public static String convertTime(int time){
	    	if (time > 86400) {
				int day = time/86400;
				int hour = (time - day*86400)/3600;
				int minute = (time - day*86400 - hour*3600)/60;
				return day+"天"+hour+"小时"+minute+"分钟";
			}
	       	if(time <= 0){
	       		return "00:00:00";
	       	}
	   		int hour = time / 3600;
	   		int minute = time % 3600 / 60;
	   		int second = time % 60;
	   		StringBuilder result = new StringBuilder();
	   		if(hour < 10){
	   			result.append("0");
	   		}
	   		result.append(hour).append(":");
	   		if(minute < 10){
	   			result.append("0");
	   		}
	   		result.append(minute).append(":");
	   		if(second < 10){
	   			result.append("0");
	   		}
	   		result.append(second);
	   		return result.toString();
	   	}
}
