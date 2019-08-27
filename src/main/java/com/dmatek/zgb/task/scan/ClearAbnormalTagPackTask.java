package com.dmatek.zgb.task.scan;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import org.apache.log4j.Logger;
import com.dmatek.zgb.cache.tool.detail.CaptureTagCacheData;
import com.dmatek.zgb.monitor.device.detail.TagDetail;

public class ClearAbnormalTagPackTask extends BaseScanTask {
	private Logger logger = Logger.getLogger(ClearAbnormalTagPackTask.class);
	private static final long MILLISECONDS_PER_MINUTE = 1 * 60 * 1000;
	private static final long MILLISECONDS_PER_DAY = 24 * 60 * 60 * 1000;
	private CaptureTagCacheData captureTagCacheData;
	// 删除断开卡片的时间
	private int removeDisCard = 5;
	public ClearAbnormalTagPackTask(int peroid,int removeDisCard, CaptureTagCacheData captureTagCacheData) {
		super(peroid);
		this.captureTagCacheData = captureTagCacheData;
		this.removeDisCard = removeDisCard;
	}
	public ClearAbnormalTagPackTask(int peroid, CaptureTagCacheData captureTagCacheData) {
		super(peroid);
		this.captureTagCacheData = captureTagCacheData;
	}
	public CaptureTagCacheData getCaptureTagCacheData() {
		return captureTagCacheData;
	}
	public void setCaptureTagCacheData(CaptureTagCacheData captureTagCacheData) {
		this.captureTagCacheData = captureTagCacheData;
	}
	
	public int getRemoveDisCard() {
		return removeDisCard;
	}
	public void setRemoveDisCard(int removeDisCard) {
		this.removeDisCard = removeDisCard;
	}
	@Override
	public void scanTask() throws Exception {
		// 获取间隔时间
		long intervalTime = MILLISECONDS_PER_DAY + MILLISECONDS_PER_MINUTE * getRemoveDisCard();
		// 遍历所有的卡片
		Map<Object, Object> tagsMap = getCaptureTagCacheData().getCacheData();
		Iterator<Object> iterator = tagsMap.values().iterator();
		while(iterator.hasNext()) {
			TagDetail tagPacket = (TagDetail) iterator.next();
			if(!tagPacket.isStatus()) {
				/*说明当前的卡片处在断开的状态，判断是否从缓存中清除掉*/
				if((new Date()).getTime() - tagPacket.getReportTime().getTime()
					>= getRemoveDisCard() * MILLISECONDS_PER_MINUTE) {
					// 超过断开时间, 我们需要将其从缓存里面清除掉
					logger.warn("將卡片訊息從緩存中清除" + tagPacket.toString());
					// 做上将要清除的标记
					tagPacket.setClear(true);
					getCaptureTagCacheData().getRedisService()
						.setHmItem(getCaptureTagCacheData().getCacheName(),tagPacket.getId(), tagPacket);
				}
				// 判断处在清除状态的时间是否超过1天，超过1天我们应该将它从缓存中清除掉
				// 说明当前的卡片处在清除状态
				if (tagPacket.isClear() && ((new Date()).getTime() - 
					tagPacket.getReportTime().getTime() >= intervalTime)) {
					// 可以将 卡片数据包从缓存中清除掉才行
					getCaptureTagCacheData().getRedisService().deleteHmItem(
							getCaptureTagCacheData().getCacheName(),
							tagPacket.getId());
				}
			}
		}
	}
}
