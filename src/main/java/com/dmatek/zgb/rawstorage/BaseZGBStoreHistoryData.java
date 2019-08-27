package com.dmatek.zgb.rawstorage;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.dmatek.zgb.monitor.device.detail.TagDetail;
import com.dmatek.zgb.pack.construction.PacketConstruction;

public abstract class BaseZGBStoreHistoryData extends BaseStorageHistoryData<TagDetail> {
	public BaseZGBStoreHistoryData(
			PacketConstruction<TagDetail> packConstruction, String historyPath) {
		super(packConstruction, historyPath);
	}
	public String OriginalFormat(Date sdate,String root) throws Exception {
		//保存的格式为 y + m + d + h
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdate);
		String DirName = String.format("%04d", cal.get(Calendar.YEAR)) + 
				         String.format("%02d", cal.get(Calendar.MONTH) + 1) + 
				         String.format("%02d", cal.get(Calendar.DAY_OF_MONTH));
	    String hourFile= String.format("%02d", cal.get(Calendar.HOUR_OF_DAY)) + ".dat";
	    
		//生成日期时间文件夹
		String dirPath = "";
		if(StringUtils.isEmpty(root)) {
			dirPath = getHistoryPath() + File.separator + DirName;
		} else {
			dirPath = getHistoryPath() + File.separator + root + File.separator + DirName;
		}
		//创建目录
		CreateNewDir(dirPath);
		String filePath = dirPath + File.separator + hourFile;
		//创建储存文件
		CreateNewDir(filePath);
		return filePath;
	}
	private File CreateNewDir(String dirName) throws IOException {
		File dir = new File(dirName);
		if(!dir.exists()) {
			if(dirName.indexOf(".") < 0) {
				dir.mkdirs();
			} else {
				dir.createNewFile();
			}
		}
		return dir;
	}
}
