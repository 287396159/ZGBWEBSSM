package com.dmatek.zgb.task.timer;

import java.util.Timer;
import java.util.TimerTask;

public class BaseTimeDelayTimer extends Timer implements ITaskTimer {
	private int delay_s;
	private TimerTask timerTask;
	public BaseTimeDelayTimer(int delay_s, TimerTask timerTask) {
		super();
		this.delay_s = delay_s;
		this.timerTask = timerTask;
	}
	@Override
	public void start() throws Exception {
		if(getDelay_s() > 0) {// 延时器器要大于0s能进行执行操作
			this.schedule(getTimerTask(), getDelay_s() * 1000, getDelay_s() * 1000);
		}
	}
	@Override
	public void stop() throws Exception {
		this.cancel();
	}
	public int getDelay_s() {
		return delay_s;
	}
	public void setDelay_s(int delay_s) {
		this.delay_s = delay_s;
	}
	public TimerTask getTimerTask() {
		return timerTask;
	}
	public void setTimerTask(TimerTask timerTask) {
		this.timerTask = timerTask;
	}
}
