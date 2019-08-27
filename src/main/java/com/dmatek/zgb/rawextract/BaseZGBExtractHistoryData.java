package com.dmatek.zgb.rawextract;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.dmatek.zgb.monitor.bean.TrackPacket;
import com.dmatek.zgb.pack.parsing.IByteFileParse;

public abstract class BaseZGBExtractHistoryData extends BaseExtractHistoryData<TrackPacket> {
	public BaseZGBExtractHistoryData(IByteFileParse<TrackPacket> iDataParse) {
		super(iDataParse);
	}
	/**
	 * 获取指定根目录下的指定TagID的文件
	 * @param root
	 * @param sTagId
	 * @return
	 */
	protected final File getTagFile(String root, String sTagId){
		File rootDir = new File(root);
		if(!rootDir.exists()){
			return null;
		}
		File[] tagfiles = rootDir.listFiles();
		for (File file : tagfiles) {
			if (file.getName().equalsIgnoreCase(sTagId)) {
				return file;
			}
		}
		return null;
	}
	/**
	 * 获取指定时间段里符合开始时间和结束时间的所有时间文件
	 * @param root
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	protected final File[] getDuringFile(File root,Date start,Date end) throws Exception{
		// 遍历指定tag目录下的所有日期数据
		List<File> dtFile = new ArrayList<File>(100);
		SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
		int iStart, iEnd, iCurrent;
		iStart = Integer.parseInt(ymdFormat.format(start));
		iEnd = Integer.parseInt(ymdFormat.format(end));
		if(null != root) {
			File[] dtFiles = root.listFiles();
			for (File file : dtFiles) {
				iCurrent = Integer.parseInt(file.getName());
				if (iCurrent >= iStart && iCurrent <= iEnd) {
					// 说明当前的y+m+d在范围之内
					dtFile.add(file);
				}
			}
			return dtFile.toArray(new File[0]);
		}
		return null;
	}
	/**
	 * 根据满足yy+MM+dd要求的时间集合获取所有满足添加的小时的.dat文件集合
	 * @param dtDirs
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	protected File[] getAllHours(File[] dtDirs, Date start, Date end) throws Exception{
		List<File> hours = new ArrayList<File>(100);
		SimpleDateFormat sdFomrat = new SimpleDateFormat("yyyyMMddHH");
		int iStart, iEnd, iCurrent;
		iStart = Integer.parseInt( sdFomrat.format(start) );
		iEnd = Integer.parseInt( sdFomrat.format(end) );
		for (File dtDir : dtDirs) {
			String curYMD = dtDir.getName();
			for (File hour : dtDir.listFiles()) {
				if(hour.isFile()){
					String curYMDH = curYMD + hour.getName().substring(0, 2);
					iCurrent = Integer.parseInt(curYMDH);
					if(iCurrent>=iStart && iCurrent <= iEnd){
						hours.add(hour);
					}
				}
			}
		}
		return hours.toArray(new File[0]);
	}

	protected List<TrackPacket> siftingCondition(TrackPacket[] alltags,Date start, Date end) throws Exception{
		List<TrackPacket> sifts = new ArrayList<TrackPacket>(200);
		for (int i = 0; i < alltags.length; i++) {
			if(alltags[i].getReportTime().compareTo(start) >= 0 && 
			   alltags[i].getReportTime().compareTo(end) < 0) {
				sifts.add(alltags[i]);
			}
		}
		return sifts;
	}
}
