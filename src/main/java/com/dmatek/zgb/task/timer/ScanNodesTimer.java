package com.dmatek.zgb.task.timer;

import java.util.TimerTask;

public class ScanNodesTimer extends BaseTimeDelayTimer {
	public ScanNodesTimer(int delay_s, TimerTask timerTask) {
		super(delay_s, timerTask);
	}
}
