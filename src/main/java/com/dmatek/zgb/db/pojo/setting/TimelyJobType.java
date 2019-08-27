package com.dmatek.zgb.db.pojo.setting;
/**
 * 及时工种讯息
 * @author Administrator
 * @data 2019年1月14日 上午11:12:47
 * @Description
 */
public class TimelyJobType extends JobType {
	private static final long serialVersionUID = 1L;
	private int current, total;// 工种当前人数、总人数
	public TimelyJobType() {
		super();
	}
	public TimelyJobType(int current, int total) {
		super();
		this.current = current;
		this.total = total;
	}
	public int getCurrent() {
		return current;
	}
	public void setCurrent(int current) {
		this.current = current;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}


}
