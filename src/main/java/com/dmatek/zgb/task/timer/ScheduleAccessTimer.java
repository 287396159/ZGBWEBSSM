package com.dmatek.zgb.task.timer;

import java.util.Date;
import java.util.TimerTask;

public class ScheduleAccessTimer extends BaseTimePointTimer {
	private TimerTask timerTask;
	public ScheduleAccessTimer(String sScheduleTime, TimerTask timerTask) {
		super(sScheduleTime);
		this.timerTask = timerTask;
	}
	@Override
	public void start() throws Exception {
		Date scheduleTime = parseScheduleTime();
		this.schedule(timerTask, scheduleTime, getOneDayMs());
	}
	@Override
	public void stop() throws Exception {
		this.cancel();
	}
	public TimerTask getTimerTask() {
		return timerTask;
	}
	public void setTimerTask(TimerTask timerTask) {
		this.timerTask = timerTask;
	}
}
