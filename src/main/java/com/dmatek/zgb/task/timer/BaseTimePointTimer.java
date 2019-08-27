package com.dmatek.zgb.task.timer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
/**
 * 时间点执行的定时器抽象类
 * @author Administrator
 * @data 2019年1月22日 下午3:47:39
 * @Description
 */
public abstract class BaseTimePointTimer extends Timer implements ITaskTimer {
	private static final String DEFAULT_PATTERN = "HH:mm:ss";
	private static final long ONE_DAY_MS = 1 * 24 * 60 * 60 * 1000;
	private static final long SINGLE_HOUR = 1 * 60 * 60 * 1000;
	private static final String DEFAULT_SCHEDULE_TIME = "19:00:00";
	private String strScheduleTime; // 调度的时间点
	private SimpleDateFormat simpleFormat;
	public BaseTimePointTimer() {
		this.strScheduleTime = DEFAULT_SCHEDULE_TIME;
		simpleFormat = new SimpleDateFormat(DEFAULT_PATTERN);
	}
	public BaseTimePointTimer(String sScheduleTime) {
		this.strScheduleTime = sScheduleTime;
		simpleFormat = new SimpleDateFormat(DEFAULT_PATTERN);
	}
	@SuppressWarnings({ "deprecation"})
	protected Date parseScheduleTime() throws ParseException {
		Date current = new Date();
		Date scheduleTime = simpleFormat.parse(strScheduleTime);
		scheduleTime.setYear(current.getYear());
		scheduleTime.setMonth(current.getMonth());
		scheduleTime.setDate(current.getDate());
		return scheduleTime;
	}
	public String getStrScheduleTime() {
		return strScheduleTime;
	}
	public void setStrScheduleTime(String strScheduleTime) {
		this.strScheduleTime = strScheduleTime;
	}
	public SimpleDateFormat getSimpleFormat() {
		return simpleFormat;
	}
	public void setSimpleFormat(SimpleDateFormat simpleFormat) {
		this.simpleFormat = simpleFormat;
	}
	public static long getOneDayMs() {
		return ONE_DAY_MS;
	}
	public static long getSingleHour() {
		return SINGLE_HOUR;
	}
}
