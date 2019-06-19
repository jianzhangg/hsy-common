package com.hsy.common.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 *
 *  @author zzf
 *
 *  Description: 日期处理工具类
 *  java8新提供的时间api，优化了性能，线程安全性.
 *
 *  Date:2018年1月12日
 */
public final class DateUtils {
	
	public static final String PATTERN_MONITOR_TIME = "yy-MM-dd HH";

	public static final String PATTERN_FULL_HOUR = "yyyy-MM-dd HH";

	public static final String HOUR = "HH";
	
	public static final String HOUR_MINUTES = "HH:mm";

	public static final String PATTERN_SIMPLE = "yyyy-MM-dd";

	public static final String PATTERN_MINUTES = "yyyy-MM-dd HH:mm";

	public static final String PATTERN_NORMAL = "yyyy-MM-dd HH:mm:ss";

	public static final String PATTERN_FULL = "yyyy-MM-dd HH:mm:ss S";

	public static final String PATTERN_FULL_SIMPLE = "yyyyMMddHHmmss";

	public static final String PATTERN_FULL_DAY = "yyyyMMdd";

	public static final String ORACLE_FORMAT = "YYYY-MM-DD HH24:MI:SS";

	public static final String SSSS = "yyyyMMddHHmmssSSSS";
	
	public static final String PATTERN_FULL_MINUTES = "yyyyMMddHHmm";
	
	private static final String NUMBER_REGEX = "[0-9]{4}-[0-9]{2}-[0-9]{2}";

	/**
	 * 生成当前日期
	 * @return
	 */
	public static Date nowDate(){
		LocalDateTime localDateTime = LocalDateTime.now();
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}
	
	/**
	 * 生成当前日期字符串
	 * @return
	 */
	public static String now(){
		return now(PATTERN_NORMAL);
	}
	
	/**
	 * 根据传入格式，获取当前日期字符串
	 * @param pattern
	 * @return
	 */
	public static String now(String pattern){
		LocalDateTime localDateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return localDateTime.format(formatter);
	}
	
	/**
	 * 将日期字符串格式化为日期，日期格式为yyyy-MM-dd HH:mm:ss
	 * @param src
	 * @return
	 */
	public static Date parse(String src){
		if(isValidDate(src)){
			return parseDate(src, PATTERN_SIMPLE);
		}
		return parseDateTime(src,PATTERN_NORMAL);
	}
	
	/**
	 * 根据传入格式将日期字符串格式化为日期
	 * @param src
	 * @return
	 */
	public static Date parse(String src,String pattern){
		if(isValidDate(src)){
			return parseDate(src, pattern);
		}
		return parseDateTime(src, pattern);
	}
	
	private static Date parseDate(String src,String pattern) {
		LocalDate localDate = LocalDate.parse(src, DateTimeFormatter.ofPattern(pattern));
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return date;
	}
	
	private static Date parseDateTime(String src,String pattern) {
		LocalDateTime localDateTime = LocalDateTime.parse(src, DateTimeFormatter.ofPattern(pattern));
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()); 
	}
	
	/**
	 * 将时间戳长度格式化为日期
	 * @param timeMills
	 * @return
	 */
	public static Date parse(Long timeMills){
		Instant instant = Instant.ofEpochMilli(timeMills);
		LocalDateTime localDateTime = LocalDateTime.ofInstant(instant,ZoneId.systemDefault());
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}
	
	/**
	 * 获取当前日期的开始时间
	 * @return
	 */
	public static Date startOfToday() {
		ZoneId zone = ZoneId.systemDefault();
		Instant instant = LocalDate.now().atStartOfDay(zone).toInstant();
		return Date.from(instant);
	}

	/**
	 * 获取指定日期的开始时间
	 * @param date
	 * @return
	 */
	public static Date startOfToday(Date date){
	    ZoneId zone = ZoneId.systemDefault();
		LocalDate localDate = convertDateLocal(date);
	    return Date.from(localDate.atStartOfDay(zone).toInstant());
	}

	/**
	 * 获取当前日期的结束时间
	 * @return
	 */
	public static Date endOfToday() {
		return endOfToday(startOfToday());
	}
	
	/**
	 * 获取指定日期的结束时间
	 * @param date
	 * @return
	 */
	public static Date endOfToday(Date date) {
		long time = startOfToday(date).getTime();
		Instant instant = Instant.ofEpochMilli(time).plusMillis(TimeUnit.DAYS.toMillis(1) - 1);
		return Date.from(instant);
	}

	/**
	 * 日期加一个数，根据field不同加不同值,field为ChronoUnit.*
	 * @param date
	 * @param number
	 * @param field，为ChronoUnit枚举
	 * @return
	 */
	public static Date add(Date date, long number, TemporalUnit field) {
		LocalDateTime localDateTime = convertLocalDateTime(date);
		return convertDate(localDateTime.plus(number, field));
	}
	
	/**
	 * 日期减一个数，根据field不同加不同值,field为ChronoUnit.*
	 * @param date
	 * @param number
	 * @param field，为ChronoUnit枚举
	 * @return
	 */
	public static Date minu(Date date, long number, TemporalUnit field){
		LocalDateTime localDateTime = convertLocalDateTime(date);
		return convertDate(localDateTime.minus(number, field));
	}


	/**
	 * @author 张梓枫
	 * @param date
	 * @return
	 * @return Date
	 * @throws Exception
	 * @description 活动日期所在月的最后一天
	 */
	public static Date lastDayOfMonth(Date date){
		LocalDateTime localDateTime = convertLocalDateTime(date);
		LocalDateTime dateTime = localDateTime.with(TemporalAdjusters.lastDayOfMonth());
		return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
	}
	
	/**
	 * @author 张梓枫
	 * @return
	 * @return Date
	 * @throws Exception
	 * @description 获取当前日期所在月的最后一天
	 */
	public static Date lastDayOfMonth() {
		return lastDayOfMonth(nowDate());
	}

	/**
	 * @author 张梓枫
	 * @param date
	 * @return
	 * @return Date
	 * @throws Exception
	 * @description 获取日期所在月的第一天
	 */
	public static Date firstDayOfMonth(Date date) {
		LocalDateTime localDateTime = convertLocalDateTime(date);
		LocalDateTime dateTime = localDateTime.with(TemporalAdjusters.firstDayOfMonth());
		return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	/**
	 * @author 张梓枫
	 * @return
	 * @return Date
	 * @throws Exception
	 * @description 获取当前时间所在月的第一天
	 */
	public static Date firstDayOfMonth() {
		return firstDayOfMonth(nowDate());
	}

	private static LocalDateTime convertLocalDateTime(Date date){
		Instant instant = date.toInstant();
	    ZoneId zone = ZoneId.systemDefault();
	    LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
	    return localDateTime;
	}
	
	private static Date convertDate(LocalDateTime localDateTime){
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()); 
	}
	
	private static LocalDate convertDateLocal(Date date) {
	    Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDate();
	}
	
	/**
	 * @author 张梓枫
	 * @param  @param a
	 * @param  @param b
	 * @param  @return 
	 * @return boolean
	 * @throws Exception 
	 * @desc  判断日期是否相等
	 */
	public static boolean equals(Date a, Date b) {
	    return convertDateLocal(a).equals(convertDateLocal(b));
	}
	

	/**
	 * @author 张梓枫
	 * @param date
	 * @return
	 * @return String
	 * @throws Exception
	 * @description 日期格式化，默认格式为：yyyy-MM-dd HH:mm:ss
	 */
	public static String format(Date date){
		return formatDateTime(date, PATTERN_NORMAL);
	}

	/**
	 * @author 张梓枫
	 * @param date
	 * @param pattern
	 * @return
	 * @return String
	 * @throws Exception
	 * @description 将日期格式化为日期字符串，格式化后得到传入的格式化类型
	 */
	public static String format(Date date,String pattern){
		if(isValidDate(pattern)){
			return formatDate(date, pattern);
		}
		return formatDateTime(date, pattern);
	}
	
	private static String formatDate(Date date,String pattern){
		LocalDateTime localDateTime = convertLocalDateTime(date);
		LocalDate localDate = localDateTime.toLocalDate();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return localDate.format(formatter);
	}
	
	private static String formatDateTime(Date date,String pattern){
		LocalDateTime localDateTime = convertLocalDateTime(date);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return localDateTime.format(formatter);
	}
	

	/**
	 * @author 张梓枫
	 * @param field ChronoField枚举
	 * @return
	 * @return int
	 * @throws Exception
	 * @description 根据field获取当前日期所在当前日/月/年
	 */
	public static int getDate(TemporalField field){
		return getDate(nowDate(), field);
	}
	
	/**
	 * @author 张梓枫
	 * @param date
	 * @param field ChronoField枚举
	 * @return
	 * @return int
	 * @throws Exception
	 * @description 根据field获取传入日期所在当前日/月/年
	 */
	public static int getDate(Date date,TemporalField field){
		LocalDateTime localDateTime = convertLocalDateTime(date);
		return localDateTime.get(field);
	}

	/**
	 * @author 张梓枫
	 * @param beginTime
	 * @param endTime
	 * @param unit      根据计算是相差天/月/年,ChronoUnit的枚举
	 * @return
	 * @return Long
	 * @throws Exception
	 * @description 计算两个日期之间相差
	 */
	public static Long subtract(Date beginTime, Date endTime,TemporalUnit unit) {
		LocalDateTime startDate = convertLocalDateTime(beginTime);
		LocalDateTime endDate = convertLocalDateTime(endTime);
		return unit.between(startDate, endDate);
	}

	/**
	 * @author 张梓枫
	 * @param src
	 * @return
	 * @return boolean
	 * @throws Exception
	 * @description 判断日期字符串是否有时分秒，如果有，返回false，没有，返回true
	 */
	public static boolean isValidDate(String src){
		Pattern pattern = Pattern.compile(NUMBER_REGEX);
        Matcher m = pattern.matcher(src);
        return m.matches();
	}
	
	public static boolean compareDate(Date sDate, Date tDate) {
	    return sDate.compareTo(tDate) > 0;
	}
}
