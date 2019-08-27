package com.dmatek.zgb.pack.parsing;
import java.io.File;
import java.io.FileInputStream;
import java.util.LinkedList;
import org.apache.log4j.Logger;
import com.dmatek.zgb.monitor.bean.TrackPacket;

public class ZGBByteFileParse implements IByteFileParse<TrackPacket>{
	private final Logger logger = Logger.getLogger(ZGBByteFileParse.class);
	private IDataPacketParse<TrackPacket> iDataPacketParse = null;
	public ZGBByteFileParse(IDataPacketParse<TrackPacket> iDataPacketParse){
		this.iDataPacketParse = iDataPacketParse;
	}
	@Override
	public TrackPacket[] ParseByteFile(File bfile) throws Exception {
		LinkedList<TrackPacket> tagMsgs = new LinkedList<TrackPacket>();
		FileInputStream in = null;
		byte[] bytes = new byte[IDataPacketParse.singleLen];
		//加上锁，防止对同一个文件进行同时读写
		synchronized (ZGBByteFileParse.class) {
			try {
				in = new FileInputStream(bfile);
				while (in.read(bytes, 0, IDataPacketParse.singleLen) != -1) {
					TrackPacket tgMsg = iDataPacketParse.ParseData(bytes);
					if (null != tgMsg) {
						tagMsgs.add(tgMsg);
					}
				}
			} catch (Exception e) {
				logger.error("读取原始文件字节流时出现异常：" + e.toString());
			}finally{
				if(null != in){
					in.close();
				}
			}
		}
		return tagMsgs.toArray(new TrackPacket[0]);
	}
}
