package com.dmatek.zgb.rawextract;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.dmatek.zgb.monitor.bean.TrackPacket;
import com.dmatek.zgb.pack.parsing.IByteFileParse;

public abstract class RootZGBExtractHistoryData extends BaseZGBExtractHistoryData{
	public RootZGBExtractHistoryData(IByteFileParse<TrackPacket> iDataParse) {
		super(iDataParse);
	}
	/**
	 * 获取原始记录文件
	 * @return
	 * @throws Exception
	 */
	protected abstract File[] getOriginalRecords(String tagId, Date start, Date end) throws Exception;
	@Override
	public List<TrackPacket> ExtractOrigin(Object... objects) throws Exception {
		// tagID
		String tagId = getTagId(objects);
		// 开始时间、结束时间
		Date start = getStartDate(objects),
		end = getEndDate(objects);
		File[] hourFiles = getOriginalRecords(tagId, start, end);
		if(null != hourFiles && hourFiles.length > 0) {
			//将小时.dat文件的字节读取出来
			List<TrackPacket> alltagMsgs = new ArrayList<TrackPacket>();
			for (File file : hourFiles) {
				// 将文件全部转化为tag轨迹数据
				TrackPacket[] tgs = (TrackPacket[]) getiDataParse().ParseByteFile(file);
				if(null != tgs) {
					//将文件中的数据提取出来
					alltagMsgs.addAll( siftingCondition(tgs, start, end));
				}
			}
			return alltagMsgs;
		}
		return null;
	}
	protected String getTagId(Object... objes) {
		 for (Object object : objes) {
			if(object instanceof String){
				return (String) object;
			}
		}
		return null;
	}
	protected Date getStartDate(Object... objes) {
		for (Object object : objes) {
			if (object instanceof Date) {
				return (Date) object;
			}
		}
		return null;
	}
	protected Date getEndDate(Object... objes) {
		Collections.reverse(Arrays.asList(objes));
		for (Object object : objes) {
			if (object instanceof Date) {
				return (Date) object;
			}
		}
		return null;
	}
}
