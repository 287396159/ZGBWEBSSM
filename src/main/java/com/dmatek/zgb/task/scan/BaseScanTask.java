package com.dmatek.zgb.task.scan;

import org.apache.log4j.Logger;
/**
 * 任務線程的抽象類
 * @author zf
 * @data 2018年12月17日 上午9:58:55
 * @Description
 */
public abstract class BaseScanTask extends Thread {
	private Logger logger = Logger.getLogger(BaseScanTask.class);
	private int peroid;// 循環周期時間
	private boolean mark;
	public BaseScanTask(int peroid){
		this.peroid = peroid;
	}
	@Override
	public void run() {
		mark = true;
		while(mark) {
			try {
				scanTask();
			} catch (Exception e) {
				logger.error("掃描任務線程【" + this.getClass().getName() + "】出現異常: " + e.toString());
			}
			try {
				Thread.sleep(peroid);
			} catch (InterruptedException e) {
				
			}
		}
	}
	/**
	 * 開始執行任務線程
	 */
	public void Start(){
		this.start();
		logger.warn("開始執行任務線程【" + this.getClass().getName() + "】");
	}
	/**
	 * 結束執行任務線程
	 */
	public void Stop(){
		mark = false;
		this.interrupt();
		logger.error("結束執行任務線程【" + this.getClass().getName() + "】");
	}
	/**
	 * 執行任務
	 * @throws Exception
	 */
	public abstract void scanTask() throws Exception;
	public Logger getLogger() {
		return logger;
	}
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
	public boolean isMark() {
		return mark;
	}
	public void setMark(boolean mark) {
		this.mark = mark;
	}
	public int getPeroid() {
		return peroid;
	}
	public void setPeroid(int peroid) {
		this.peroid = peroid;
	}
}
