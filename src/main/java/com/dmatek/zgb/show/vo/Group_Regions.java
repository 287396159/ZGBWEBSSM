package com.dmatek.zgb.show.vo;

import java.util.List;
import com.dmatek.zgb.db.pojo.setting.Group;
import com.dmatek.zgb.db.pojo.setting.Region;

public class Group_Regions extends Group {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Region> regions;
	private int number;// 组别数量
	public Group_Regions() {
		super();
	}
	public Group_Regions(Group group, List<Region> regions, int number) {
		super(group.getCreateTime(), group.getUpdateTime(), 
			  group.getCreateName(), group.getUpdateName(), group.getId(),
			  group.getName(), group.getDes());
		this.regions = regions;
		this.number = number;
	}
	public List<Region> getRegions() {
		return regions;
	}
	public void setRegions(List<Region> regions) {
		this.regions = regions;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
}
