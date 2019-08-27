package com.dmatek.zgb.warn.cache.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.dmatek.zgb.comparator.BaseWarmComparator;
import com.dmatek.zgb.redis.service.RedisService;
import com.dmatek.zgb.report.warn.bean.GroupCardReportWarnInfo;
import com.dmatek.zgb.report.warn.bean.WarnType;
import com.dmatek.zgb.report.warn.bean.WarnTypeCardReportInfo;
import com.dmatek.zgb.warn.bean.base.BaseWarnMessage;
/**
 * 警告缓存
 * @author zf
 * @data 2018年12月14日 下午4:53:54
 * @Description
 */
public abstract class BaseWarnCache<T extends BaseWarnMessage> {
	protected static final long ONE_DAY_TIME = 1 * 24 * 60 * 60 * 1000;
	private RedisService redisService;// redis的服务
	private BaseWarmComparator warmDefaultComparator;
	public BaseWarnCache(RedisService redisService, BaseWarmComparator warmDefaultComparator){
		super();
		this.redisService = redisService;
		this.warmDefaultComparator = warmDefaultComparator;
	}
	public Map<String, T> getCache() throws Exception {
		Map<String, T> cache = new HashMap<String, T>();
		Map<Object, Object> maps = redisService.getHmAll(getWarnName());
		Iterator<Object> iterator = maps.keySet().iterator();
		while(iterator.hasNext()){
			String key = (String) iterator.next();
			@SuppressWarnings("unchecked")
			T obj = (T) maps.get(key);
			cache.put(key, obj);
		}
		return cache;
	}
	public abstract String getWarnName();
	/**
	 * 添加警告讯息
	 * @param tagPacket
	 * @throws Exception
	 */
	public void addWarn(T warmMsg) throws Exception{
		redisService.setHmItem(getWarnName(), warmMsg.getUuid(), warmMsg);
	}
	/**
	 * 清理缓存中所有需要清除的警告
	 * @throws Exception 
	 */
	public void clearCache() throws Exception {
		// 1. 获取所有的缓存讯息 
		Map<String, T> maps = getCache();
		Iterator<T> iterators = maps.values().iterator();
		T warm = null;
		while(iterators.hasNext()) {
			warm = iterators.next();
			if(null != warm && warm.isClear()) {
				// 说明当前的警告需要我们从redis中删除掉
				if(redisService.deleteHmItem(getWarnName(), warm.getUuid())){
					// 添加警报讯息到数据库中
					addWarnToDb(warm);
				}
				System.out.println("清除【warnName: " + getWarnName() + ", "
								 + "uuid: " + warm.getUuid() + "】");
			}
		}
	}
	/**
	 * 根据uuId获取缓存中指定的警告
	 * @param uuid
	 * @return
	 * @throws Exception 
	 */
	public T getWarn(String uuid) throws Exception{
		@SuppressWarnings("unchecked")
		T item = (T) redisService.getHmItem(getWarnName(), uuid);
		return item;
	}
	/**
	 * 根据设置ID获取设置当前警告的所有讯息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public abstract List<T> getWarnFromDeviceId(String id) throws Exception;
	/**
	 * 根据设备ID 获取第一个未被处理的警告
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public T getNotHandleWarnFromDeviceId(String id) throws Exception {
		List<T> warmsBox = getWarnFromDeviceId(id);
		for (T warm : warmsBox) {
			if(!warm.isHandle()){
				return warm;
			}
		}
		return null;
	}
	/**
	 * 获取警告总数量
	 * @return
	 * @throws Exception
	 *//*
	public int getWarmTotal() throws Exception{
		return getAllWarns().size();
	}*/
	
	/*public List<T> getPageWarm(int page, int limit) throws Exception{
		// 1.获取所有的
		List<T> allWarns = getAllWarns();
		// 2.page : 第几页， limit：每一页数量
		int fromIndex = page * limit > allWarns.size()?allWarns.size():page * limit;
		return allWarns.subList((page - 1) * limit, fromIndex);
	}*/
	
	/**
	 * 获取所有没有被清理的警报
	 * @return
	 * @throws Exception 
	 */
	public List<T> getAllWarns() throws Exception{
		LinkedList<T> allWarns = new LinkedList<T>();
		Iterator<T> allWarnsIterators = getCache().values().iterator();
		T warn = null;
		while(allWarnsIterators.hasNext()){
			warn = allWarnsIterators.next();
			if(null != warn && !warn.isClear()){
				allWarns.add(warn);
			}
		}
		Collections.sort(allWarns, warmDefaultComparator);
		return allWarns;
	}
	/**
	 * 获取所有未处理的警告讯息
	 * @return
	 * @throws Exception 
	 */
	public List<T> getAllNotHandles() throws Exception {
		List<T> allNotHandles = new ArrayList<T>();
		Iterator<T> allWarmsIterators = getCache().values().iterator();
		T warm = null;
		while(allWarmsIterators.hasNext()){
			warm = allWarmsIterators.next();
			if(null != warm && !warm.isHandle()){
				allNotHandles.add(warm);
			}
		}
		return allNotHandles;
	}
	/**
	 * 获取所有已处理未清除的警告
	 * @return
	 * @throws Exception 
	 */
	public List<T> getAllHandleNotClears() throws Exception{
		List<T> allHandleNotClears = new ArrayList<T>();
		Iterator<T> allWarmsIterators = getCache().values().iterator();
		T warm = null;
		while(allWarmsIterators.hasNext()){
			warm = allWarmsIterators.next();
			if(null != warm && warm.isHandle() && !warm.isClear()){
				allHandleNotClears.add(warm);
			}
		}
		return allHandleNotClears;
	}
	/**
	 * 批量处理警报讯息
	 * @param uuids
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public void handleWarns(String[] uuids) throws Exception{
		T warn = null;
		for (String uuid : uuids) {
			warn = (T) redisService.getHmItem(getWarnName(), uuid);
			if(null != warn && !warn.isHandle()){
				warn.handle();
				redisService.setHmItem(getWarnName(), uuid, warn);
			}
		}
	}
	/**
	 * 批量清除警报讯息
	 * @param uuids
	 * @throws Exception 
	 */
	public void clearWarns(String[] uuids) throws Exception{
		for (String uuid : uuids) {
			clearWarn(uuid);
		}
	}
	/**
	 * 清除警告
	 * @param uuid
	 * @throws Exception
	 */
	public void clearWarn(String uuid) throws Exception{
		@SuppressWarnings("unchecked")
		T warn = (T) redisService.getHmItem(getWarnName(), uuid);
		if(null != warn && !warn.isClear()){
			warn.clear();
			redisService.setHmItem(getWarnName(), uuid, warn);
		}
	}
	/**
	 * 添加警报讯息到数据库中
	 * @param warn
	 * @throws Exception
	 */
	public abstract void addWarnToDb(T warn) throws Exception;
	private WarnTypeCardReportInfo getCacheCardReportInfor(int peroid) throws Exception {
		WarnTypeCardReportInfo warnTypeCardInfor = new WarnTypeCardReportInfo(WarnType.AbnormalBaseWarn);
		List<T> personnelHelpWarns = getAllWarns();
		for (T personnelHelpWarn : personnelHelpWarns) {
			// 判断当前记录是否在最近peroid天之内的讯息
			if((new Date().getTime() - personnelHelpWarn.getCreateTime().getTime()) <= peroid * ONE_DAY_TIME){
				GroupCardReportWarnInfo groupCardReportWarnInfo = 
						warnTypeCardInfor.getGroupCardMap().get(personnelHelpWarn.getGroupId());
				// 查看当前的组别的讯息是否存在
				if(null == groupCardReportWarnInfo){
					// 当表中不存在是，我们需要重新添加
					groupCardReportWarnInfo = new GroupCardReportWarnInfo(personnelHelpWarn.getGroupId(), 
							personnelHelpWarn.getGroupName());
					warnTypeCardInfor.getGroupCardMap().put(personnelHelpWarn.getGroupId(), 
							groupCardReportWarnInfo);
				}
				warnTypeCardInfor.setTotalNumber(warnTypeCardInfor.getTotalNumber() + 1);
				groupCardReportWarnInfo.setTotalNumber(groupCardReportWarnInfo.getTotalNumber() + 1);
				if(personnelHelpWarn.isHandle()){
					warnTypeCardInfor.setHandleNumber(warnTypeCardInfor.getHandleNumber() + 1);
					groupCardReportWarnInfo.setHandleNumber(groupCardReportWarnInfo.getHandleNumber() + 1);
				} else {
					warnTypeCardInfor.setNotHandleNumber(warnTypeCardInfor.getNotHandleNumber() + 1);
					groupCardReportWarnInfo.setNotHandleNumber(groupCardReportWarnInfo.getNotHandleNumber() + 1);
				}
			}
		}
		return warnTypeCardInfor;
	}
	protected abstract WarnTypeCardReportInfo getDataBaseWarnTypeCardReportInfor(int peroid) throws Exception;
	public WarnTypeCardReportInfo getAllWarnTypeCardReportInfor(int peroid)
			throws Exception {
		WarnTypeCardReportInfo warnCacheTypeCardReportInfo = getCacheCardReportInfor(peroid);
		WarnTypeCardReportInfo warnDataBaseTypeCardReportInfo = getDataBaseWarnTypeCardReportInfor(peroid);
		if(null != warnDataBaseTypeCardReportInfo) {
			int total = warnCacheTypeCardReportInfo.getTotalNumber() +
					warnDataBaseTypeCardReportInfo.getTotalNumber();
			int handleNumber = warnCacheTypeCardReportInfo.getHandleNumber() + 
					warnDataBaseTypeCardReportInfo.getHandleNumber();
			int notHandleNumber = warnCacheTypeCardReportInfo.getNotHandleNumber() + 
					warnDataBaseTypeCardReportInfo.getNotHandleNumber();
			
			warnCacheTypeCardReportInfo.setTotalNumber(total);// 总数量
			warnCacheTypeCardReportInfo.setHandleNumber(handleNumber);// 处理数量
			warnCacheTypeCardReportInfo.setNotHandleNumber(notHandleNumber);// 未处理数量
			/////////////////////////////////////////
			Iterator<Integer> iterator = 
					warnDataBaseTypeCardReportInfo.getGroupCardMap().keySet().iterator();
			while (iterator.hasNext()) {
				int groupId = iterator.next();
				GroupCardReportWarnInfo dbGroupCardReportWarnInfo = 
						warnDataBaseTypeCardReportInfo.getGroupCardMap().get(groupId);
				int cacheGroupTotal = 0, dbGroupTotal = 0, 
					cacheGroupHandleNumber = 0, dbGroupHandleNumber = 0, 
					cacheGroupNotHandleNumber = 0, dbGroupNotHandleNumber = 0;
				dbGroupTotal = dbGroupCardReportWarnInfo.getTotalNumber();
				dbGroupHandleNumber = dbGroupCardReportWarnInfo.getHandleNumber();
				dbGroupNotHandleNumber = dbGroupCardReportWarnInfo.getNotHandleNumber();
				
				GroupCardReportWarnInfo groupReportWarnInfor = warnCacheTypeCardReportInfo
						.getGroupCardMap().get(groupId);
				if(null != groupReportWarnInfor){
					cacheGroupTotal = groupReportWarnInfor.getTotalNumber();
					cacheGroupHandleNumber = groupReportWarnInfor.getHandleNumber();
					cacheGroupNotHandleNumber = groupReportWarnInfor.getNotHandleNumber();
				} else {
					groupReportWarnInfor = new GroupCardReportWarnInfo(groupId, dbGroupCardReportWarnInfo.getGroupName());
					/*warnCacheTypeCardReportInfo.getGroupCardMap().put(groupId, groupReportWarnInfor);*/
				}
				groupReportWarnInfor.setTotalNumber(dbGroupTotal + cacheGroupTotal);
				groupReportWarnInfor.setHandleNumber(dbGroupHandleNumber + cacheGroupHandleNumber);
				groupReportWarnInfor.setNotHandleNumber(dbGroupNotHandleNumber + cacheGroupNotHandleNumber);
				warnCacheTypeCardReportInfo.getGroupCardMap().put(groupReportWarnInfor.getGroupId(), groupReportWarnInfor);
			}
		}
		return warnCacheTypeCardReportInfo;
	}
	
	public RedisService getRedisService() {
		return redisService;
	}
	public void setRedisService(RedisService redisService) {
		this.redisService = redisService;
	}
	public BaseWarmComparator getWarmDefaultComparator() {
		return warmDefaultComparator;
	}
	public void setWarmDefaultComparator(BaseWarmComparator warmDefaultComparator) {
		this.warmDefaultComparator = warmDefaultComparator;
	}
}
