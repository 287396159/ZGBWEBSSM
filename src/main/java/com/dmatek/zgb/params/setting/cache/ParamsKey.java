package com.dmatek.zgb.params.setting.cache;
/**
 * 参数的键值
 * @author zf
 * @data 2018年12月17日 下午2:59:29
 * @Description
 */
public enum ParamsKey {
	ip("ip"), // IP地址
	port("port"), // 网络端口
	isTagDisAlarm("isTagDisAlarm"),// 卡片断开报警
	TagDisAlarmTime("tagDisAlarmTime"),// 卡片断开报警时间
	isNodeDisAlarm("isNodeDisAlarm"),// 基站断开报警
	nodeDisAlarmTime("nodeDisAlarmTime"),// 基站断开报警时间
	isPersonHelp("isPersonHelp"), // 是否启用人员求救报警
	lowBat("lowBatter"), // 低电量
	isLowBat("isLowBattery"), // 是否启用低电量报警
	isAreaAlarm("isAreaAlarm"),// 区域报警
	resWarmMode("resWarmMode"), // 滞留报警的模式
	resTime("notMoveTime"), // 滞留时间
	isBsVisible("isBsVisible"), // 基站可见性
	isNodeManagerShow("isNodeManagerShowReport"),// 基站管理中是否显示基站上报的 时间
	isShowMonitorRegionNumber("isShowMonitorRegionNumber"),// 是否显示监控区域卡片数量
	reportWarnInfoTime("reportWarnInfoTime"),// 打印警报讯息时间段
	refreshTreeTime("refreshTreeTime"),// 刷新树状图的时间
	isRefreshTreeTime("isRefreshTreeTime"),// 是否刷新树状图
	isTagVisible("isTagOverVisible"), // 卡片超时未接收时是否可见
	tagVisibleOverTime("tagVisibleOverTime"), // 卡片超时未接收时间
	checkdistime("checkedDisconnectTime"), // 检查异常断开的时间间隔
	checkedtagdisK1("checkedTagdisK1"), // 检测卡片异常断开参数1
	checkedtagdisK2("checkedTagdisK2"), // 检测卡片异常断开参数2
	checkednodedisK1("checkedDevdisK1"), // 检测节点设备异常断开参数1
	checkednodedisK2("checkedDevdisK2"), // 检测节点设备异常断开参数2
	
	autoClearAlarmMsg("autoClearAlarmMsg"),// 自动处理警告资料（文件资料）
	autoClearAlarmMsgTime("autoClearAlarmMsgTime"),// 自动处理警告资料时间
	autoClearDutyData("autoClearDutyData"),// 是否启用自动处理日常数据
	autoClearDutyDataTime("autoClearDutyDataTime"),// 启用自动处理日常数据时间
	autoClearAccessRecords("autoClearAccessRecord"),// 自动清除出入记录
	
	autoClearOperationRecord("autoClearOperationRecord"),// 是否自動清除操作記錄
	autoClearOperationRecordTime("autoClearOperationRecordTime"),// 自動清除操作記錄時間
	autoClearAccessRecordsTime("autoClearAccessRecordTime"),
	
	autoClearHandleWarn("autoClearHandleWarn"),// 自动清理已经处理的警告讯息(缓存资料)
	autoClearHandleWarnTime("autoClearHandleWarnTime"),// 自动清理已经处理的警告讯息(自动处理缓存资料)
	
	isAutoStatisticAccess("isAutoStatisticAccess"),// 启用自动统计上下班记录
	// 手動設置門控狀態的延時時間
	manualSetCurrentDoorStatusDelay("manualSetCurrentDoorStatusDelay"),
	accessStatisticTime("accessStatisticTime"),// 定时统计上下班记录
	accessOndutyStartTime("accessOndutyStartTime"),// 进出统计的上班开始时间
	accessOndutyEndTime("accessOndutyEndTime"),// 进出统计的上班结束时间
	accessOffdutyStartTime("accessOffdutyStartTime"),//进出统计的下班开始时间
	accessOffdutyEndTime("accessOffdutyEndTime"),//进出统计的下班结束时间
	maxNumberAccounts("maxNumberAccounts"),// 一个账号可以被多少人登陆
	
	optimizeMode("optimizeMode"),// 優化模式
	optimizeValue("optimizeVal"),// 優化值
	isShowDebug("isShowDebug");// 是否顯示調試賬戶訊息
	private String val;
	private ParamsKey(String val) {
		this.val = val;
	}
	public String getVal() {
		return val;
	}
	/**
	 * 根据字符串获取对应的枚举键值
	 * @param key
	 * @return
	 */
	public static ParamsKey getParamsKey(String key){
		ParamsKey[] pKeys = ParamsKey.values();
		for (ParamsKey paramsKey : pKeys) {
			if(paramsKey.val.equals(key)){
				return paramsKey;
			}
		}
		return null;
	}
	/**
	 * 判断是否包含指定的键值
	 * @param key
	 * @return
	 */
	public static boolean contain(String key){
		if(null != getParamsKey(key)){
			return true;
		}
		return false;
	}
}
