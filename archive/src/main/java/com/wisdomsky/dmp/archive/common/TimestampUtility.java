package com.wisdomsky.dmp.archive.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wisdomsky.dmp.archive.exception.InvalidDateTimeStringException;

public class TimestampUtility {

   private static final Logger logger = LoggerFactory.getLogger(TimestampUtility.class);

   private static String zone="GMT+8";//"UTC";

   public static long getCurrentTimestamp() {
      TimeZone timeZone = TimeZone.getTimeZone(zone);
      TimeZone.setDefault(timeZone);
      return Calendar.getInstance(timeZone).getTimeInMillis();
   }
   
   public static java.sql.Timestamp getCurrentSqlTimestamp() {
      TimeZone timeZone = TimeZone.getTimeZone(zone);
      TimeZone.setDefault(timeZone);
      return new java.sql.Timestamp(Calendar.getInstance(timeZone).getTimeInMillis());
   }

   public static long getTimestampPreMonth(long timestamp, int offset) {
      int year = getYear(timestamp);
      int month = getMonth(timestamp) - 1;
      // int date = getDay(timestamp);
      // TimeZone timeZone = TimeZone.getTimeZone(zone);
      // TimeZone.setDefault(timeZone);  
      Calendar calendar = Calendar.getInstance();
      calendar.setTimeInMillis(0);
      calendar.set(year, month, 1, 0, 0, 0);
      calendar.add(Calendar.MONTH, offset);
      
      return calendar.getTimeInMillis();
   }

   public static long getTimestampPreDay(long timestamp, int offset) {
      int year = getYear(timestamp);
      int month = getMonth(timestamp) - 1;
      int date = getDay(timestamp);
      // TimeZone timeZone = TimeZone.getTimeZone(zone);
      // TimeZone.setDefault(timeZone);  
      Calendar calendar = Calendar.getInstance();
      calendar.setTimeInMillis(0);
      calendar.set(year, month, date, 0, 0, 0);
      calendar.add(Calendar.DATE, offset);
      
      return calendar.getTimeInMillis();
   }

   public static long getTimestampPreHour(long timestamp, int offset) {
      //int timeZoneOffset = timeZone.getRawOffset();
      int year = getYear(timestamp);
      int month = getMonth(timestamp) - 1;
      int date = getDay(timestamp);
      int hour = getHour(timestamp);
      //TimeZone timeZone = TimeZone.getTimeZone(zone);
      //TimeZone.setDefault(timeZone);  
      Calendar calendar = Calendar.getInstance();
      calendar.setTimeInMillis(0);
      calendar.set(year, month, date, hour, 0, 0);
      calendar.add(Calendar.HOUR, offset);
      
      return calendar.getTimeInMillis();
   }

   public static long getTimestampPreMinute(long timestamp, int offset) {
      //int timeZoneOffset = timeZone.getRawOffset();
      int year = getYear(timestamp);
      int month = getMonth(timestamp) - 1;
      int date = getDay(timestamp);
      int hour = getHour(timestamp);
      int minute = getMinute(timestamp);
      //TimeZone timeZone = TimeZone.getTimeZone(zone);
      //TimeZone.setDefault(timeZone);  
      Calendar calendar = Calendar.getInstance();
      calendar.setTimeInMillis(0);
      calendar.set(year, month, date, hour, minute, 0);
      calendar.add(Calendar.MINUTE, offset);
      
      return calendar.getTimeInMillis();
   }

   public static long getTimestampYearOffset(long timestamp, int offset) {
      int year = getYear(timestamp);
      int month = getMonth(timestamp) - 1;
      int date = getDay(timestamp);
      int hour = getHour(timestamp);
      int minute = getMinute(timestamp);
      int second = getSecond(timestamp);
      // TimeZone timeZone = TimeZone.getTimeZone(zone);
      // TimeZone.setDefault(timeZone);  
      Calendar calendar = Calendar.getInstance();
      calendar.setTimeInMillis(0);
      calendar.set(year, month, date, hour, minute, second);
      calendar.add(Calendar.YEAR, offset);
      
      return calendar.getTimeInMillis();
   }

   public static long getTimestampMonthOffset(long timestamp, int offset) {
      int year = getYear(timestamp);
      int month = getMonth(timestamp) - 1;
      int date = getDay(timestamp);
      int hour = getHour(timestamp);
      int minute = getMinute(timestamp);
      int second = getSecond(timestamp);
      // TimeZone timeZone = TimeZone.getTimeZone(zone);
      // TimeZone.setDefault(timeZone);  
      Calendar calendar = Calendar.getInstance();
      calendar.setTimeInMillis(0);
      calendar.set(year, month, date, hour, minute, second);
      calendar.add(Calendar.MONTH, offset);
      
      return calendar.getTimeInMillis();
   }

   public static long getTimestampDayOffset(long timestamp, int offset) {
      int year = getYear(timestamp);
      int month = getMonth(timestamp) - 1;
      int date = getDay(timestamp);
      int hour = getHour(timestamp);
      int minute = getMinute(timestamp);
      int second = getSecond(timestamp);
      // TimeZone timeZone = TimeZone.getTimeZone(zone);
      // TimeZone.setDefault(timeZone);  
      Calendar calendar = Calendar.getInstance();
      calendar.setTimeInMillis(0);
      calendar.set(year, month, date, hour, minute, second);
      calendar.add(Calendar.DATE, offset);
      
      return calendar.getTimeInMillis();
   }

   public static long getTimestampHourOffset(long timestamp, int offset) {
      int year = getYear(timestamp);
      int month = getMonth(timestamp) - 1;
      int date = getDay(timestamp);
      int hour = getHour(timestamp);
      int minute = getMinute(timestamp);
      int second = getSecond(timestamp);
      // TimeZone timeZone = TimeZone.getTimeZone(zone);
      // TimeZone.setDefault(timeZone);  
      Calendar calendar = Calendar.getInstance();
      calendar.setTimeInMillis(0);
      calendar.set(year, month, date, hour, minute, second);
      calendar.add(Calendar.HOUR, offset);
      
      return calendar.getTimeInMillis();
   }

   public static long getTimestampMinuteOffset(long timestamp, int offset) {
      int year = getYear(timestamp);
      int month = getMonth(timestamp) - 1;
      int date = getDay(timestamp);
      int hour = getHour(timestamp);
      int minute = getMinute(timestamp);
      int second = getSecond(timestamp);
      // TimeZone timeZone = TimeZone.getTimeZone(zone);
      // TimeZone.setDefault(timeZone);  
      Calendar calendar = Calendar.getInstance();
      calendar.setTimeInMillis(0);
      calendar.set(year, month, date, hour, minute, second);
      calendar.add(Calendar.MINUTE, offset);
      
      return calendar.getTimeInMillis();
   }

   public static long getTimestampRoundMinute(long timestamp, int unit) {
      //int timeZoneOffset = timeZone.getRawOffset();
      int year = getYear(timestamp);
      int month = getMonth(timestamp) - 1;
      int date = getDay(timestamp);
      int hour = getHour(timestamp);
      int minute = getMinute(timestamp);
      minute = (minute / unit) * unit;
      //TimeZone timeZone = TimeZone.getTimeZone(zone);
      //TimeZone.setDefault(timeZone);  
      Calendar calendar = Calendar.getInstance();
      calendar.setTimeInMillis(0);
      calendar.set(year, month, date, hour, minute, 0);
      
      return calendar.getTimeInMillis();
   }

   /*************************
    * 0: yyyyMMddHHmmss;
    * 1: yyyy-MM-dd HH:mm:ss;
    * 2: yyyy/MM/dd HH:mm:ss;
    * 3: yyyyMMdd;
    * 4: yyyy-MM-dd;
    * 5: yyyy/MM/dd;
    * 6: HH:mm:ss;
    * 7: HHmmss;
    * 8: yyyy-MM-dd HH:mm:ss+08:00;
    * 9: yyyyMMddHHmm;
    ***************************/
   public static String timestamp2String(long timestamp, int mode) {
      String formatString = getFormatString(mode);
      //TimeZone timeZone = TimeZone.getTimeZone(zone);
      //TimeZone.setDefault(timeZone);  
      Calendar calendar = Calendar.getInstance();
      calendar.setTimeInMillis(timestamp);
      SimpleDateFormat sdf = new SimpleDateFormat(formatString);
      String dateString = sdf.format(calendar.getTime());
      return dateString;
   }

   public static Calendar string2Calendar(String dateTimeString, int mode) throws InvalidDateTimeStringException {
      try {
         String formatString = getFormatString(mode);
         Calendar calendar = Calendar.getInstance();
         SimpleDateFormat sdf = new SimpleDateFormat(formatString);
         calendar.setTime(sdf.parse(dateTimeString));
         return calendar;
      }
      catch (Exception ex) {
         String message = String.format("Invalid DateTime String : %s. The format mode is %d.", dateTimeString, mode);
         logger.error(message, ex);
         throw new InvalidDateTimeStringException(message);
      }
   }

   public static java.sql.Timestamp string2SqlTimestampWithRoundMinute(String dateTimeString, int mode, int unit)
         throws InvalidDateTimeStringException {
      Calendar calendar = string2Calendar(dateTimeString, mode);
      int minute = calendar.get(Calendar.MINUTE);
      minute = (minute / unit) * unit;
      calendar.set(Calendar.MINUTE, minute);
      calendar.set(Calendar.SECOND, 0);
      return calendar2Timestamp(calendar);
   }

   public static java.sql.Timestamp string2SqlTimestampWithRoundHour(String dateTimeString, int mode, int unit)
         throws InvalidDateTimeStringException {
      Calendar calendar = string2Calendar(dateTimeString, mode);
      int hour = calendar.get(Calendar.HOUR_OF_DAY);
      hour = (hour / unit) * unit;
      calendar.set(Calendar.HOUR_OF_DAY, hour);
      calendar.set(Calendar.MINUTE, 0);
      calendar.set(Calendar.SECOND, 0);
      return calendar2Timestamp(calendar);
   }

   public static java.sql.Timestamp string2SqlTimestampWithRoundDay(String dateTimeString, int mode)
         throws InvalidDateTimeStringException {
      Calendar calendar = string2Calendar(dateTimeString, mode);
      calendar.set(Calendar.HOUR_OF_DAY, 0);
      calendar.set(Calendar.MINUTE, 0);
      calendar.set(Calendar.SECOND, 0);
      return calendar2Timestamp(calendar);
   }

   public static java.sql.Timestamp calendar2Timestamp(Calendar calendar) {
      return new java.sql.Timestamp(calendar.getTimeInMillis());
   }

   public static String string2StringWithRoundMinute(String dateTimeString, int inMode, int outMode, int unit) throws InvalidDateTimeStringException {
      Calendar calendar = string2Calendar(dateTimeString, inMode);
      int minute = calendar.get(Calendar.MINUTE);
      minute = (minute / unit) * unit;
      calendar.set(Calendar.MINUTE, minute);
      calendar.set(Calendar.SECOND, 0);
      return calendar2String(calendar, outMode);
   }

   public static String string2StringWithRoundHour(String dateTimeString, int inMode, int outMode, int unit) throws InvalidDateTimeStringException {
      Calendar calendar = string2Calendar(dateTimeString, inMode);
      int hour = calendar.get(Calendar.HOUR_OF_DAY);
      hour = (hour / unit) * unit;
      calendar.set(Calendar.HOUR_OF_DAY, hour);
      calendar.set(Calendar.MINUTE, 0);
      calendar.set(Calendar.SECOND, 0);
      return calendar2String(calendar, outMode);
   }

   public static String string2StringWithRoundDay(String dateTimeString, int inMode, int outMode) throws InvalidDateTimeStringException {
      Calendar calendar = string2Calendar(dateTimeString, inMode);
      calendar.set(Calendar.HOUR_OF_DAY, 0);
      calendar.set(Calendar.MINUTE, 0);
      calendar.set(Calendar.SECOND, 0);
      return calendar2String(calendar, outMode);
   }

   public static String calendar2String(Calendar calendar, int mode) {
      String formatString = getFormatString(mode);
      SimpleDateFormat sdf = new SimpleDateFormat(formatString);
      String dateString = sdf.format(calendar.getTime());
      return dateString;
   }

   public static long timestamp2TimeSign(long timestamp, int mode) {
      int year = getYear(timestamp);
      int month = getMonth(timestamp);
      int day = getDay(timestamp);
      int hour = getHour(timestamp);
      int minute = getMinute(timestamp);
      int second = getSecond(timestamp);
      long output = 0;
      switch (mode) {
         case 0:
         output = year * 10000000000L + month * 100000000L + day * 1000000L + hour * 10000L + minute * 100L + second * 1L;
         break;
         case 1:
         output = year * 10000L + month * 100L + day * 1L;
         break;
         case 2:
         output = hour * 10000L + minute * 100L + second * 1L;
         break;
         case 3:
         output = year * 1000000L + month * 10000L + day * 100L + hour * 1L;
         break;
         default:
         output = year * 10000000000L + month * 100000000L + day * 1000000L + hour * 10000L + minute * 100L + second * 1L;
         break;
      }
      return output;
   }

   public static int getYear(long timestamp) {
      //TimeZone timeZone = TimeZone.getTimeZone(zone);
      //TimeZone.setDefault(timeZone);  
      Calendar calendar = Calendar.getInstance();
      calendar.setTimeInMillis(timestamp);
      return calendar.get(Calendar.YEAR);
   }

   // Use
   public static int getMonth(long timestamp) {
      //TimeZone timeZone = TimeZone.getTimeZone(zone);
      //TimeZone.setDefault(timeZone);  
      Calendar calendar = Calendar.getInstance();
      calendar.setTimeInMillis(timestamp);
      return calendar.get(Calendar.MONTH) + 1;
   }

   // Use
   public static int getDay(long timestamp) {
      //TimeZone timeZone = TimeZone.getTimeZone(zone);
      //TimeZone.setDefault(timeZone);  
      Calendar calendar = Calendar.getInstance();
      calendar.setTimeInMillis(timestamp);
      return calendar.get(Calendar.DAY_OF_MONTH);
   }

   public static int getHour(long timestamp) {
      //TimeZone timeZone = TimeZone.getTimeZone(zone);
      //TimeZone.setDefault(timeZone);  
      Calendar calendar = Calendar.getInstance();
      calendar.setTimeInMillis(timestamp);
      return calendar.get(Calendar.HOUR_OF_DAY);
   }

   public static int getMinute(long timestamp) {
      //TimeZone timeZone = TimeZone.getTimeZone(zone);
      //TimeZone.setDefault(timeZone);  
      Calendar calendar = Calendar.getInstance();
      calendar.setTimeInMillis(timestamp);
      return calendar.get(Calendar.MINUTE);
   }

   public static int getSecond(long timestamp) {
      //TimeZone timeZone = TimeZone.getTimeZone(zone);
      //TimeZone.setDefault(timeZone);  
      Calendar calendar = Calendar.getInstance();
      calendar.setTimeInMillis(timestamp);
      return calendar.get(Calendar.SECOND);
   }

   public static int getTimestampIndex(long timestamp, int mode) {
      int hour = getHour(timestamp);
      int minute = getMinute(timestamp);
      int second = getSecond(timestamp);
      int index = 0;
      switch (mode) {
         case 0:
         index = hour;
         break;
         case 1:
         index = hour * 60 + minute;
         break;
         case 2:
         index = hour * 3600 + minute * 60 + second;
         break;
      }
      return index;
   }

   public static long string2Timestamp(String dateTimeString, int mode) throws InvalidDateTimeStringException {
      try {
         String formatString = getFormatString(mode);
         SimpleDateFormat format = new SimpleDateFormat(formatString);
         Date date = format.parse(dateTimeString);
         //TimeZone timeZone = TimeZone.getTimeZone(zone);
         //TimeZone.setDefault(timeZone);  
         Calendar calendar = Calendar.getInstance();
         calendar.setTime(date);
         long result = calendar.getTimeInMillis();
         return result;
      }
      catch (Exception ex) {
         String message = String.format("Invalid DateTime String : %s. The format mode is %d.", dateTimeString, mode);
         logger.error(message, ex);
         throw new InvalidDateTimeStringException(message);
      }
   }

   public static java.util.Date string2UtilDate(String dateTimeString, int mode) throws InvalidDateTimeStringException {
      try {
         String formatString = getFormatString(mode);
         SimpleDateFormat format = new SimpleDateFormat(formatString);
         java.util.Date date = format.parse(dateTimeString);
         return date;
      }
      catch (Exception ex) {
         String message = String.format("Invalid DateTime String : %s. The format mode is %d.", dateTimeString, mode);
         logger.error(message, ex);
         throw new InvalidDateTimeStringException(message);
      }
   }

   public static long utilDate2Timestamp(Date date) {
      //TimeZone timeZone = TimeZone.getTimeZone(zone);
      //TimeZone.setDefault(timeZone);  
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(date);
      return calendar.getTimeInMillis();
   }

   public static Date timestamp2UtilDate(long timestamp) {
      //TimeZone timeZone = TimeZone.getTimeZone(zone);
      //TimeZone.setDefault(timeZone);  
      Calendar calendar = Calendar.getInstance();
      calendar.setTimeInMillis(timestamp);
      return calendar.getTime();
   }

   public static java.util.Date sqlDate2UtilDate(java.sql.Date sqlDate) {
      return new java.util.Date(sqlDate.getTime());
   }

   public static java.sql.Date utilDate2SqlDate(java.util.Date utilDate) {
      return new java.sql.Date(utilDate.getTime());
   }

   public static java.util.Date sqlTimestamp2UtilDate(java.sql.Timestamp timestamp) {
      return new java.util.Date(timestamp.getTime());
   }

   public static java.sql.Timestamp utilDate2SqlTimestamp(java.util.Date utilDate) {
      return new java.sql.Timestamp(utilDate.getTime());
   }

   public static long sqlTimestamp2Timestamp(java.sql.Timestamp sqlTimestamp) {
      return sqlTimestamp.getTime();
   }

   public static java.sql.Timestamp timestamp2SqlTimestamp(long timestamp) {
      return new java.sql.Timestamp(timestamp);
   }

   public static java.sql.Timestamp string2SqlTimestamp(String dateTimeString, int mode) throws InvalidDateTimeStringException {
      try {
         
         Date dateTime = string2UtilDate(dateTimeString, mode);
         return utilDate2SqlTimestamp(dateTime);
      }
      catch (Exception ex) {
         String message = String.format("Invalid DateTime String : %s. The format mode is %d.", dateTimeString, mode);
         logger.error(message, ex);
         throw new InvalidDateTimeStringException(message);
      }
   }

   public String minuteTrunc(String dateTimeString, int mode, int unit) throws InvalidDateTimeStringException {
      long timestamp = string2Timestamp(dateTimeString, mode);
      timestamp = getTimestampRoundMinute(timestamp, unit);
      return timestamp2String(timestamp, mode);
      
   }

   public static String sqlTimestamp2String(java.sql.Timestamp timestamp, int mode) {
      String formatString = getFormatString(mode);
      SimpleDateFormat sdf = new SimpleDateFormat(formatString);
      return sdf.format(timestamp);
   }

   public static long timestamp2Long(java.sql.Timestamp timestamp) {
      return timestamp.getTime();
   }

   public static int getDaysOfMonth(int year, int month) {
      Calendar calendar = Calendar.getInstance();
      calendar.set(year, (month - 1), 1);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
   }
   
   public static int getDaysOfWeek(long timestamp) {
      Calendar calendar = Calendar.getInstance();
      calendar.setTimeInMillis(timestamp);
      boolean isFirstSunday = (calendar.getFirstDayOfWeek() == Calendar.SUNDAY);
      int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
      if(isFirstSunday){
         weekDay = weekDay - 1;
         if (weekDay == 0) {
            weekDay = 7;
         }
      }
		return weekDay;
	}

   private static String getFormatString(int mode) {
      String formatString = "yyyy-MM-dd HH:mm:ss";
      switch (mode) {
         case 0:
         formatString = "yyyyMMddHHmmss";
         break;
         case 1:
         formatString = "yyyy-MM-dd HH:mm:ss";
         break;
         case 2:
         formatString = "yyyy/MM/dd HH:mm:ss";
         break;
         case 3:
         formatString = "yyyyMMdd";
         break;
         case 4:
         formatString = "yyyy-MM-dd";
         break;
         case 5:
         formatString = "yyyy/MM/dd";
         break;
         case 6:
         formatString = "HH:mm:ss";
         break;
         case 7:
         formatString = "HHmmss";
         break;
         case 8:
         formatString = "yyyy-MM-dd HH:mm:ss+08:00";
         break;
         case 9:
         formatString = "yyyyMMddHHmm";
         break;
      }
      return formatString;
   }

   public static long getWeekMondayTimestamp(long timestamp) {
		Calendar calendar = Calendar.getInstance();
      calendar.setTimeInMillis(timestamp);
		// 获得当前日期是一个星期的第几天
		int dayWeek = calendar.get(Calendar.DAY_OF_WEEK);
		if (1 == dayWeek) {
			calendar.add(Calendar.DAY_OF_MONTH, -1);
		}
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		int day = calendar.get(Calendar.DAY_OF_WEEK);
		calendar.add(Calendar.DATE, calendar.getFirstDayOfWeek() - day);
		return calendar.getTimeInMillis();
	}

}
