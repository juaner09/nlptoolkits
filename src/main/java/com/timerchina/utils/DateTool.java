package com.timerchina.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;


public class DateTool {

	private static final SimpleDateFormat YEARMONTH_FORMART = new SimpleDateFormat ("yyyyMM");
	//private static final SimpleDateFormat TIME_FORMART_WITH_NO_SPLIT = new SimpleDateFormat ("HHmmss");
	private static final SimpleDateFormat TIMEMILLI_FORMART_WITH_NO_SPLIT = new SimpleDateFormat ("HHmmssSSS");
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat DATE_FORMAT2 = new SimpleDateFormat("yyyyMM");
	private static final SimpleDateFormat DATE_FORMAT_HOUR = new SimpleDateFormat("yyyy-MM-dd HH");
	private static final SimpleDateFormat DATE_FORMAT_MINUTE = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private static final SimpleDateFormat DATETIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final SimpleDateFormat DATETIME_ENCODE_FORMAT = new SimpleDateFormat("yyyy-MM-dd%20HH:mm:ss");
	private static final SimpleDateFormat DATETIME_FORMAT_WITH_NO_SPLIT = new SimpleDateFormat("yyyyMMddHHmmss");
	private static final SimpleDateFormat YEAR_MONTH_DAY_FORMAT = new SimpleDateFormat("yyyyMMdd", Locale.SIMPLIFIED_CHINESE);
	
	private static final int YESTERDAY_INTERVAL = 1;
	private static final int DAYBEFOREYESTERDAY_INTERVAL = 2;
	private static final String DATETIME_SPLIT = " ";
	private static final String DATE_SPLIT = "-";
	private static final String TIME_SPLIT = ":";
	
	private static final String MATCH_GROUP_LEFT = "(";
	private static final String MATCH_GROUP_RIGHT = ")";
	
	private static final String[] TODAY_PATTERN = {"今天", "今 天", "today"};
	private static final String[] YESTERDAY_PATTERN = {"昨天", "昨 天", "yesterday"};
	private static final String[] DAYBEFOREYESTERDAY_PATTERN = {"前天", "前 天", "yesterday"};
	private static final String[] YEARS_AGO_PATTERN = {"年前", "years ago"};
	private static final String[] MONTHS_AGO_PATTERN = {"月前","个月前","月 前","个 月前", "months ago"};
	private static final String[] WEEKS_AGO_PATTERN = {"周前","星期前","周 前","个星期前", "months ago"};
	private static final String[] DAYS_AGO_PATTERN = {"天前", "天 前", "days ago"};	
	private static final String[] HALFHOUR_AGO_PATTERN = {"半小时前", "半小时 前", "half hour ago", "half of hour ago"};
	private static final String[] HOURS_AGO_PATTERN = {"小时前", "小时 前", "hours ago"};	
	private static final String[] MINUTES_AGO_PATTERN = {"分钟前", "分钟 前", "分前", "分 前", "minutes ago"};	
	private static final String[] SECONDS_AGO_PATTERN = {"秒钟前", "秒钟 前", "秒前", "秒 前", "seconds ago"};	
	private static final String[] JUST_PATTERN = {"刚才", "刚 才", "just", "just now"};	
	
	private static final String[] REPLACE_YEARS_AGO_PATTERN = {"(\\d+)\\s*年前", "(\\d+)\\s*years ago"};
	private static final String[] REPLACE_MONTHS_AGO_PATTERN = {"(\\d+)\\s*月前","(\\d+)\\s*个月前","(\\d+)\\s*月 前","(\\d+)\\s*个 月前", "(\\d+)\\s*months ago"};
	private static final String[] REPLACE_WEEKS_AGO_PATTERN = {"(\\d+)\\s*周前","(\\d+)\\s*星期前","(\\d+)\\s*周 前","(\\d+)\\s*个星期前", "(\\d+)\\s*weeks ago"};
	private static final String[] REPLACE_TODAY_PATTERN = {"今天", "今 天", "today"};
	private static final String[] REPLACE_YESTERDAY_PATTERN = {"昨天", "昨 天", "yesterday"};
	private static final String[] REPLACE_DAYBEFOREYESTERDAY_PATTERN = {"前天", "前 天", "yesterday"};
	private static final String[] REPLACE_DAYS_AGO_PATTERN = {"(\\d+)\\s*天前", "(\\d+)\\s*天 前", "(\\d+)\\s*days ago"};	
	private static final String[] REPLACE_HALFHOUR_AGO_PATTERN = {"半小时前", "半小时 前", "half hour ago", "half of hour ago"};
	private static final String[] REPLACE_HOURS_AGO_PATTERN = {"(\\d+)\\s*小时前", "(\\d+)\\s*小时 前", "(\\d+)\\s*hours ago"};	
	private static final String[] REPLACE_MINUTES_AGO_PATTERN = {"(\\d+)\\s*分钟前", "(\\d+)\\s*分钟 前", "(\\d+)\\s*分前", "(\\d+)\\s*分 前", "(\\d+)\\s*minutes ago"};	
	private static final String[] REPLACE_SECONDS_AGO_PATTERN = {"(\\d+)\\s*秒钟前", "(\\d+)\\s*秒钟 前", "(\\d+)\\s*秒前", "(\\d+)\\s*秒 前", "(\\d+)\\s*seconds ago"};	
	private static final String[] REPLACE_JUST_PATTERN = {"刚才", "刚 才", "just", "just now"};	
	
	private static boolean contiansDateTimePattern (String pageDate, String[] patterns) {
		boolean isContains = false;
		for (int i = 0; i < patterns.length; i ++) {
			if (pageDate.contains(patterns[i])) {
				isContains = true;
				break;
			}
		}
		
		return isContains;
	}
	
	private static String removeGroupMark (String pattern) {
		return pattern.replace(MATCH_GROUP_LEFT, "").replace(MATCH_GROUP_RIGHT, "");
	}
	
	public static String format(Date date){
		if(CommonTools.isEmpty(date)){
			return ""	;
		}else{
			return DATETIME_FORMAT.format(date);
		}
	}

	public static Date parse(String date){
		try {
			
			return DATETIME_FORMAT.parse(parseDate(date));
		} catch (ParseException e) {
			e.printStackTrace();
			return getNow();
		}
	}
	
	public static Date getNow() {
		Calendar c = Calendar.getInstance(Locale.getDefault());
		return c.getTime();
	}
	
	private static String processPatternInPageDate (String pageDate, String[] replacePatterns, String standardDateTime) {
		String processedDateTime = pageDate;
		for (int i = 0; i < replacePatterns.length; i ++) {
			processedDateTime = pageDate.replaceAll(removeGroupMark(replacePatterns[i]), standardDateTime);
			
			if (! processedDateTime.equalsIgnoreCase(pageDate)) {
				break;
			}
		}
		
		return processedDateTime;
	}
	
	public static String getFirstGroup(String regex, String original) {
		Matcher m = getMatcher(regex, original);
		if (m.find()) {
			String result = new String(m.group(1));
			m = null;
			return result;
		}
		return "";
	}

	public static String getGroup(String regex, String original, int groupIdx) {
		Matcher m = getMatcher(regex, original);
		if (m.find()) {
			String result = new String(m.group(groupIdx));
			m = null;

			return result;
		}
		return "";
	}
	
	private static Matcher getMatcher(String regex, String content) {
		Pattern p = Pattern.compile(regex, 34);
		return p.matcher(content);
	}

	private static String processDateTimeSeparator (String pageDate) {
		String processedDateTime = "";
		
		processedDateTime = pageDate.replaceAll("  ", " ").trim();
		processedDateTime = processedDateTime.replace(" : ", TIME_SPLIT);
		processedDateTime = processedDateTime.replace("/", DATE_SPLIT);
		
		return processedDateTime;
	}
	
	private static int getDateTimeSpan (String pageDate, String[] REPLACE_PATTERN) {
		int span = 0;
		for (int i = 0; i < REPLACE_PATTERN.length; i ++) {
			span = CommonTools.parseInt(getFirstGroup(REPLACE_PATTERN[i], pageDate));
			if (span != CommonTools.PARSE_INT_ERROR) {
				break;
			}
		}
		
		return span;
	}
	
	private static String processDatePattern (String pageDate) {
		String standardDate = "";
		if (contiansDateTimePattern(pageDate, TODAY_PATTERN)) {
			standardDate = processPatternInPageDate(pageDate, REPLACE_TODAY_PATTERN, getCurrentDayByDateFormat());
		} else if (contiansDateTimePattern(pageDate, YESTERDAY_PATTERN)) {
			standardDate = processPatternInPageDate(pageDate, REPLACE_YESTERDAY_PATTERN, getYesterdayByDateFormat());
		} else if (contiansDateTimePattern(pageDate, DAYBEFOREYESTERDAY_PATTERN)) {
			standardDate = processPatternInPageDate(pageDate, REPLACE_DAYBEFOREYESTERDAY_PATTERN, getDayBeforeYesterdayByDateFormat());
		} else if (contiansDateTimePattern(pageDate, JUST_PATTERN)) {
			standardDate = processPatternInPageDate(pageDate, REPLACE_JUST_PATTERN, getJustNow());
		} else if (contiansDateTimePattern(pageDate, DAYS_AGO_PATTERN)) {
			standardDate = processPatternInPageDate(pageDate, REPLACE_DAYS_AGO_PATTERN, getDaysAgoByDateFormat(getDateTimeSpan (pageDate, REPLACE_DAYS_AGO_PATTERN)));
		} else if (contiansDateTimePattern(pageDate, HALFHOUR_AGO_PATTERN)) {
			standardDate = processPatternInPageDate(pageDate, REPLACE_HALFHOUR_AGO_PATTERN, getHalfHourAgoByDateTimeFormat());
		} else if (contiansDateTimePattern(pageDate, HOURS_AGO_PATTERN)) {
			standardDate = processPatternInPageDate(pageDate, REPLACE_HOURS_AGO_PATTERN, getHoursAgoByDateTimeFormat(getDateTimeSpan (pageDate, REPLACE_HOURS_AGO_PATTERN)));
		} else if (contiansDateTimePattern(pageDate, MINUTES_AGO_PATTERN)) {
			standardDate = processPatternInPageDate(pageDate, REPLACE_MINUTES_AGO_PATTERN, getMinutesAgoByDateTimeFormat(getDateTimeSpan (pageDate, REPLACE_MINUTES_AGO_PATTERN)));
		} else if (contiansDateTimePattern(pageDate, SECONDS_AGO_PATTERN)) {
			standardDate = processPatternInPageDate(pageDate, REPLACE_SECONDS_AGO_PATTERN, getSecondsAgoByDateTimeFormat(getDateTimeSpan (pageDate, REPLACE_SECONDS_AGO_PATTERN)));
		}else if (contiansDateTimePattern(pageDate, WEEKS_AGO_PATTERN)) {
			standardDate = processPatternInPageDate(pageDate, REPLACE_WEEKS_AGO_PATTERN, getWeeksAgoByDateTimeFormat(getDateTimeSpan (pageDate, REPLACE_WEEKS_AGO_PATTERN)));
		}else if (contiansDateTimePattern(pageDate, MONTHS_AGO_PATTERN)) {
			standardDate = processPatternInPageDate(pageDate, REPLACE_MONTHS_AGO_PATTERN, getMonthsAgoByDateTimeFormat(getDateTimeSpan (pageDate, REPLACE_MONTHS_AGO_PATTERN)));
		}else if (contiansDateTimePattern(pageDate, YEARS_AGO_PATTERN)) {
			standardDate = processPatternInPageDate(pageDate, REPLACE_YEARS_AGO_PATTERN, getYearsAgoByDateTimeFormat(getDateTimeSpan (pageDate, REPLACE_YEARS_AGO_PATTERN)));
		} else {
			standardDate = pageDate;
		}
		return standardDate;
	}
	
	private static String getYearsAgoByDateTimeFormat(int years) {
		Calendar c = Calendar.getInstance(Locale.getDefault());
		c.add(Calendar.DATE, -365 * years);

		return DATE_FORMAT.format(c.getTime());
	}

	private static String getMonthsAgoByDateTimeFormat(int months) {
		Calendar c = Calendar.getInstance(Locale.getDefault());
		c.add(Calendar.DATE, -30 * months);

		return DATE_FORMAT.format(c.getTime());
	}

	private static String getWeeksAgoByDateTimeFormat(int weeks) {
		Calendar c = Calendar.getInstance(Locale.getDefault());
		c.add(Calendar.DATE, -7 * weeks);

		return DATE_FORMAT.format(c.getTime());
	}

	private static class DateElement {
		String year;
		String month;
		String day;
		String hour;
		String minute;
		String seconds;
		
		public String getYear() {
			return year;
		}
		
		public void setYear(String year) {
			this.year = year;
		}
		
		public String getMonth() {
			return month;
		}
		
		public void setMonth(String month) {
			this.month = month;
		}
		
		public String getDay() {
			return day;
		}
		
		public void setDay(String day) {
			this.day = day;
		}
		
		public String getHour() {
			return hour;
		}
		
		public void setHour(String hour) {
			this.hour = hour;
		}
		
		public String getMinute() {
			return minute;
		}
		
		public void setMinute(String minute) {
			this.minute = minute;
		}
		
		public String getSeconds() {
			return seconds;
		}
		
		public void setSeconds(String seconds) {
			this.seconds = seconds;
		}
	}
	
	private static String processYear (String[] dateElement) {
		if (dateElement.length < 3) {
			int month = Integer.parseInt(dateElement[0]);
			int day = Integer.parseInt(dateElement[1]);
			Calendar c = Calendar.getInstance(Locale.getDefault());
			int currentYear = c.get(Calendar.YEAR);
			int currentMonth = c.get(Calendar.MONTH)+1;
			int currentDay = c.get(Calendar.DAY_OF_MONTH);
			if(month>currentMonth || (month==currentMonth && day > currentDay)){
				--currentYear;
			}
			return String.valueOf(currentYear);
		} else {
			String year = getCurrentYear();
			if (dateElement[dateElement.length - 3].length() == 4) {
				year = dateElement[dateElement.length - 3];
			} else if (dateElement[dateElement.length - 3].length() == 2) {
				year = year.substring(0, 2) + dateElement[dateElement.length - 3];
			}
			return year;
		}
	}
	
	private static String processSeconds (String[] timeElement) {
		if (timeElement.length < 3) {
			return "00";
		} else {
			return timeElement[2];
		}
	}
	
	private static String combinDateTime (DateElement dateTime) {
		return combinDateTime (dateTime.getYear(), dateTime.getMonth(), dateTime.getDay(), dateTime.getHour(), dateTime.getMinute(), dateTime.getSeconds());
	}
	
	private static String combinDateTime (String year, String month, String day, String hour, String minute, String seconds) {
		StringBuffer date = new StringBuffer ();
		date.append(year).append(DATE_SPLIT).append(month).append(DATE_SPLIT).append(day);
		
		StringBuffer time = new StringBuffer ();
		time.append(hour).append(TIME_SPLIT).append(minute).append(TIME_SPLIT).append(seconds);
		
		return date.append(DATETIME_SPLIT).append(time).toString();
		
	}
	
	private static boolean isContainsTimePart (String pageDate) {
		return pageDate.contains(TIME_SPLIT);
	}
	private static boolean isContainsDatePart (String pageDate) {
		return pageDate.contains(DATE_SPLIT);
	}
	private static String processDateFullFormat (String pageDate) {
		pageDate = pageDate.replace("年", "-");
		pageDate = pageDate.replace("月", "-");
    	pageDate = pageDate.replace("日", "");
    	pageDate = pageDate.replace("时", ":");
    	pageDate = pageDate.replace("分", ":");
    	pageDate = pageDate.replace("秒", "");
		if (! isContainsTimePart(pageDate)) {
			pageDate = pageDate.concat(" 00:00:00");
		}
		if (! isContainsDatePart(pageDate)) {
			pageDate = getCurrentDayByDateFormat()+" "+pageDate;
		}
		String[] dateTimeBlocks = pageDate.split(DATETIME_SPLIT);
		String dateBlock = dateTimeBlocks[0];
		String timeBlock = dateTimeBlocks[1];
		String[] dateElement = dateBlock.split(DATE_SPLIT);
		String[] timeElement = timeBlock.split(TIME_SPLIT);
		DateElement dateTime = new DateElement();
		dateTime.setDay(processMonthOrDay(dateElement[dateElement.length - 1]));
		dateTime.setMonth(processMonthOrDay(dateElement[dateElement.length - 2]));
		dateTime.setYear(processYear(dateElement));
		
		dateTime.setHour(timeElement[0]);
		dateTime.setMinute(timeElement[1]);
		dateTime.setSeconds(processSeconds(timeElement));
		
		return combinDateTime(dateTime);
	}
	
	private static String processMonthOrDay(String data){
		
		if(!CommonTools.isEmpty(data) && data.trim().length()<2){
			data = "0"+data;
		}
		return data;
	}

	public static String parseDate(String pageDate) {
		if(CommonTools.isEmpty(pageDate)) return "1970-01-01 00:00:00";
		
		SimpleDateFormat sdf1= new SimpleDateFormat("EEE MMM dd hh:mm:ss Z yyyy",Locale.US);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String standardDate = "";
		try {
			standardDate = sdf.format(sdf1.parse(pageDate));
			return standardDate;
		} catch (ParseException e1) {

		}
		
		try{
			standardDate = processDateTimeSeparator (pageDate);
			standardDate = processDatePattern (standardDate);
			standardDate = processDateFullFormat (standardDate);
		}catch(Exception e){
			standardDate = "1970-01-01 00:00:00";
		}
//		System.out.println(standardDate);
		return standardDate;
	}
	
	public static String getCurrentYear() {
		Calendar c = Calendar.getInstance(Locale.getDefault());

		return String.valueOf(c.get(Calendar.YEAR));
	}
	
	public static String getCurrentDay() {
		Calendar c = Calendar.getInstance(Locale.getDefault());

		return String.valueOf(c.get(Calendar.DATE));
	}
	
	public static String getCurrentYearMonth () {
		Calendar c = Calendar.getInstance(Locale.getDefault());
		return YEARMONTH_FORMART.format(c.getTime());
	}
	
	public static String getDaysAgoByYearMonthDateFormat(int days) {
		Calendar c = Calendar.getInstance(Locale.getDefault());
		c.add(Calendar.DATE, -1 * days);

		return YEARMONTH_FORMART.format(c.getTime());
	}
	
	public static String getDay(int days) {
		Calendar c = Calendar.getInstance(Locale.getDefault());
		c.add(Calendar.DATE, -1 * days);

		return String.valueOf(c.get(Calendar.DAY_OF_MONTH));
	}
	
	public static String getCurrentTime () {
		Calendar c = Calendar.getInstance(Locale.getDefault());
		return TIMEMILLI_FORMART_WITH_NO_SPLIT.format(c.getTime());
	}
	
	public static String getYearMonth (String dateTime) {
		String date = dateTime.split(" ")[0];
		String[] dateElement = date.split("-");
		String year =  dateElement[0];
		String month =  dateElement[1];
		
		return year.concat(month);
	}

	public static String getCurrentDayByDateTimeFormat() {
		Calendar c = Calendar.getInstance(Locale.getDefault());

		return DATETIME_FORMAT.format(c.getTime());
	}
	
	public static String getCurrentDayByHour() {
		Calendar c = Calendar.getInstance(Locale.getDefault());
		return DATE_FORMAT_HOUR.format(c.getTime());
	}
	
	public static String parseDateByHour(String date) {
		String dateHour = DATE_FORMAT_HOUR.format(parse(date));
		try {
			return DATETIME_FORMAT.format(DATE_FORMAT_HOUR.parse(dateHour));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "1970-01-01 00:00:00";
	}
	
	public static String getCurrentDayByMinute() {
		Calendar c = Calendar.getInstance(Locale.getDefault());
		return DATE_FORMAT_MINUTE.format(c.getTime());
	}
	
	public static String parseDateByMinute(String date) {
		String dateMinute = DATE_FORMAT_MINUTE.format(parse(date));
		try {
			return DATETIME_FORMAT.format(DATE_FORMAT_MINUTE.parse(dateMinute));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "1970-01-01 00:00:00";
	}
	
	public static String getCurrentDayByDateTimeFormatWithoutSplit() {
		Calendar c = Calendar.getInstance(Locale.getDefault());

		return DATETIME_FORMAT_WITH_NO_SPLIT.format(c.getTime());
	}
	
	
	public static String getCurrentDayByDateFormat() {
		Calendar c = Calendar.getInstance(Locale.getDefault());

		return DATE_FORMAT.format(c.getTime());
	}
	
	public static String getCurrentDayByDateFormat2() {
		Calendar c = Calendar.getInstance(Locale.getDefault());
		return DATE_FORMAT2.format(c.getTime());
	}

	public static String getYesterdayByDateFormat() {
		return getDaysAgoByDateFormat(YESTERDAY_INTERVAL);
	}

	public static String getDayBeforeYesterdayByDateFormat() {
		return getDaysAgoByDateFormat(DAYBEFOREYESTERDAY_INTERVAL);
	}

	public static String getDayByDateFormat(long second) {
		Calendar c = Calendar.getInstance(Locale.getDefault());
		c.setTimeInMillis(second);
		return DATE_FORMAT.format(c.getTime());
	}
	
	public static String getDayByDateFormat(Date date) {
		return DATE_FORMAT.format(date);
	}
	
	public static String getDaysAgoByDateFormat(int days) {
		Calendar c = Calendar.getInstance(Locale.getDefault());
		c.add(Calendar.DATE, -1 * days);

		return DATE_FORMAT.format(c.getTime());
	}
	
	public static String getDaysAgoByDateEncodeFormat(int days) {
		Calendar c = Calendar.getInstance(Locale.getDefault());
		c.add(Calendar.DATE, -1 * days);

		return DATETIME_ENCODE_FORMAT.format(c.getTime());
	}
	
	public static String parseDateByTimeMillis(String millis) {
		String date = "";
		Long millonSeconds = 0l;
		Calendar c = Calendar.getInstance(Locale.getDefault());
		try {
			millonSeconds = Long.parseLong(millis);
		} catch (Exception e) {
			millonSeconds = 0l;
		}
		c.setTimeInMillis(millonSeconds);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		date = sdf.format(c.getTime());
		return date;
	}
	
	public static String getHalfHourAgoByDateTimeFormat() {
		Calendar c = Calendar.getInstance(Locale.getDefault());
		c.add(Calendar.MINUTE, -30);

		return DATETIME_FORMAT.format(c.getTime());
	}

	public static String getDaysAgoByDateTimeFormat(int days) {
		Calendar c = Calendar.getInstance(Locale.getDefault());
		c.add(Calendar.DAY_OF_MONTH, -1*days);

		return DATETIME_FORMAT.format(c.getTime());
	}
	
	public static String getHoursAgoByDateTimeFormat(int hours) {
		Calendar c = Calendar.getInstance(Locale.getDefault());
		c.add(Calendar.HOUR, -1 * hours);

		return DATETIME_FORMAT.format(c.getTime());
	}

	public static String getMinutesAgoByDateTimeFormat(int minutes) {
		Calendar c = Calendar.getInstance(Locale.getDefault());
		c.add(Calendar.MINUTE, -1 * minutes);

		return DATETIME_FORMAT.format(c.getTime());
	}

	public static String getJustNow() {
		Calendar c = Calendar.getInstance(Locale.getDefault());
		c.add(13, -30);

		return DATETIME_FORMAT.format(c.getTime());
	}

	public static String getSecondsAgoByDateTimeFormat(int seconds) {
		Calendar c = Calendar.getInstance(Locale.getDefault());
		c.add(Calendar.SECOND, -1 * seconds);

		return DATETIME_FORMAT.format(c.getTime());
	}

	public static boolean before (String date, String compareDate, boolean isError, Logger log) {
		log.debug("before 2");
		
		log.debug("【开始 Calendar】");
		Calendar dateCalendar = Calendar.getInstance(Locale.getDefault());
		Calendar compareDateCalendar = Calendar.getInstance(Locale.getDefault());
		log.debug("【结束 Calendar】");
		
		try {
			log.debug("【开始 parse date】");
			Date parseDate = DATETIME_FORMAT.parse(date);
			dateCalendar.setTime(parseDate);
			log.debug("【结束 parse date】");
		} catch (Exception e) {
			log.debug("1 error date : " + date);
			
			if (! isError) {
				String[] dateTimeElements = date.split(" ");
				String[] dateElements = dateTimeElements[0].split("-");
				String[] timeElements = dateTimeElements[1].split(":");
				String replaceDate = dateElements[0] + "-" + dateElements[1] + "-" + dateElements[2];
				replaceDate =replaceDate+" "+ timeElements[0] + ":" + timeElements[1] + ":" + timeElements[2];
				
				log.debug("error date to replace date : " + replaceDate);
				
				return before (replaceDate, compareDate, true, log);
			}
//			System.out.println("date error");
//			e.printStackTrace();
			log.debug("2 error date : " + date);
			log.debug("date before date error : " + e.getMessage());
			return true;
		}
		
		try{
			log.debug("【开始 parse compare date】");
			Date parseCompareDate = DATETIME_FORMAT.parse(compareDate);
			compareDateCalendar.setTime(parseCompareDate);
			log.debug("【结束 parse compare date】");
		} catch (Exception e) {
//			System.out.println("date error");
//			e.printStackTrace();
			log.debug("error compare date : " + compareDate);
			log.debug("date before compare date error : " + e.getMessage());
			return true;
		}
		
		log.debug("【开始比较】");
		boolean result = dateCalendar.compareTo(compareDateCalendar) < 0;
		log.debug("【结束比较】[result : " + result + "]");
		
		return result;
	}
	
	public static boolean before (String date, String compareDate, Logger log) {
		log.debug("before 1");
		return before (date, compareDate, false, log);
	}
	
	public static boolean after (String date, String compareDate, Logger log) {
		log.debug("after");
		return ! before (date, compareDate, log) && ! equals (date, compareDate);
	}
	
	public static boolean equals (String date, String compareDate) {
		Calendar dateCalendar = Calendar.getInstance(Locale.getDefault());
		Calendar compareDateCalendar = Calendar.getInstance(Locale.getDefault());
		try {
			dateCalendar.setTime(DATETIME_FORMAT.parse(date));
			compareDateCalendar.setTime(DATETIME_FORMAT.parse(compareDate));
		} catch (ParseException e) {
		}
		
		return dateCalendar.compareTo(compareDateCalendar) == 0;
	}

	public static boolean inDateRange (String date, String compareBeginDate, String compareEndDate, Logger log) {
		log.debug("inDateRange");
//		return after(date, compareBeginDate, log) && before(date, compareEndDate, log);
		return after(date, compareBeginDate, log);
	}
	
	public static String getYearMonthByDate(String date){
		String yearAndMonth = getCurrentYearMonth();
		try {
			 Date parser = DATETIME_FORMAT.parse(date);
			 yearAndMonth = YEARMONTH_FORMART.format(parser);
		} catch (ParseException e) {
		}
		return yearAndMonth;
	}
	
	public static String getSecondsAgoByPeriodDateTimeFormat(String date, int second) throws ParseException {
			Date date2 = DATETIME_FORMAT.parse(date);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date2);
			calendar.add(Calendar.SECOND, second); 
			return DATETIME_FORMAT.format(calendar.getTime());
	}
	
	public static String getHoursAgoByPeriodDateTimeFormat(String date, int hour) {
		Date date2;
		try {
			date2 = DATE_FORMAT_HOUR.parse(date);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date2);
			calendar.add(Calendar.HOUR, hour); 
			return DATETIME_FORMAT.format(calendar.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
			
		}
		return null;
	}
	
	public static String getMinutesAgoByPeriodDateTimeFormat(String date, int minute) {
		Date date2;
		try {
			date2 = DATE_FORMAT_MINUTE.parse(date);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date2);
			calendar.add(Calendar.MINUTE, minute); 
			return DATETIME_FORMAT.format(calendar.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
			
		}
		return null;
	}
	
	public static String getDaysAgoByPeriodDateTimeFormat(String date, int day) throws ParseException {
		Date date2 = YEAR_MONTH_DAY_FORMAT.parse(date);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date2);
		calendar.add(Calendar.DAY_OF_MONTH, -1 * day); 
		return YEAR_MONTH_DAY_FORMAT.format(calendar.getTime());
	}
	
	public static String getDaysAgoByPeriodDateTimeFormat_YYYY_MM_DD_hh_mm_ss(String date, int day) throws ParseException {
		Date date2 = DATETIME_FORMAT.parse(date);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date2);
		calendar.add(Calendar.DAY_OF_MONTH, -1 * day); 
		return DATETIME_FORMAT.format(calendar.getTime());
	}
	
	public static int getTwoDaysDifference_YYYYMMDD(String date1, String date2) throws ParseException {
		long difference = 0;
		long day1 = YEAR_MONTH_DAY_FORMAT.parse(date1).getTime();
		long day2 = YEAR_MONTH_DAY_FORMAT.parse(date2).getTime();
		if (day1 > day2) {
			difference = day1 - day2;
		} else {
			difference = day2 - day1;
		}
		return (int)((difference / 86400000) + 1);
		
	}
	
	public static int getTwoDaysDifference_YYYY_MM_DD_hh_mm_ss(String date1, String date2) throws ParseException {
		long difference = 0;
		long day1 = DATETIME_FORMAT.parse(date1).getTime();
		long day2 = DATETIME_FORMAT.parse(date2).getTime();
		if (day1 > day2) {
			difference = day1 - day2;
		} else {
			difference = day2 - day1;
		}
		return (int)((difference / 86400000) + 1);
		
	}
	
	public static String getRemainingTime(long millisecond) {
		long days = millisecond / (1000 * 60 * 60 * 24);  
	    long hours = (millisecond % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);  
	    long minutes = (millisecond % (1000 * 60 * 60)) / (1000 * 60);  
	    long seconds = (millisecond % (1000 * 60)) / 1000;  
	    return days + ":" + hours + ":" + minutes + ":" + seconds + "(天：时：分：秒)";
	}
	public static void main(String[] args) throws ParseException {
//		System.out.println(inDateRange("2012-03-08 00:00:00","2012-03-07 00:00:00","2012-03-08 00:00:00",Logger.getLogger(DateTool.class)));
//		System.out.println(equals("2012-03-07 00:00:00","2012-03-07 00:00:00"));
		//		System.out.println(DateTool.parseDate("2012-03-07 00:00"));
//		System.out.println(DateTool.parseDate("Wed Aug 01 14:54:36 CST 2012"));
//		System.out.println(DateTool.parseDate("2012-08-02 12:22:32"));
//		System.out.println(DateTool.parseDate("2012-08-02"));
//		System.out.println(DateTool.parseDate("12-08-02 12:22:32"));
//		System.out.println(DateTool.parseDate("12-02 12:22:32"));
//		System.out.println(DateTool.parseDate("08-02 12:22:32"));
//		System.out.println(DateTool.parseDate("2012-08-02 12:22"));
//		System.out.println(processDateFullFormat("03-11 14:34"));
//		String date = DateTool.getCurrentDayByDateTimeFormat();
//		try {
//			System.out.println(DateTool.getSecondsAgoByPeriodDateTimeFormat(date, 3600));
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		System.out.println(DateTool.getDaysAgoByDateEncodeFormat(0));
//		System.out.println(DateTool.parseDate("1424983310000"));
//		System.out.println(DateTool.parseDate("6周前"));
//		System.out.println(DateTool.parseDate("2014年5月8日 20:09"));
//		System.out.println(DateTool.parseDate("2014年5月8日 20分09秒"));
		
//		System.out.println(DateTool.before("2012-08-02 12:22", "2012-07-02 12:22",CommonTool.getLogger()));
//		System.out.println(getTwoDaysDifference("20110101", "20110104 "));
//		System.out.println(getDaysAgoByPeriodDateTimeFormat("20150101", 1));
//		System.out.println(getSecondsAgoByPeriodDateTimeFormat(getCurrentDayByDateTimeFormat(), 3600));
//		System.out.println(getHoursAgoByPeriodDateTimeFormat(getCurrentDayByHour(), 1));
//		System.out.println(DateTool.getCurrentDayByDateTimeFormat());
//		System.out.println(DateTool.parseDateByHour(DateTool.getCurrentDayByDateTimeFormat()));
		String timeStr = "2016-10-19 10:55:47";
		System.out.println(parse(timeStr));
	}
}