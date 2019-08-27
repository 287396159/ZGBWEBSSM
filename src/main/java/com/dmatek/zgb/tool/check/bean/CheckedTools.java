package com.dmatek.zgb.tool.check.bean;

import java.net.SocketException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.dmatek.zgb.login.access.filter.KickoutSessionControlFilter;
import com.dmatek.zgb.result.factory.base.BaseResultFactory;
@Component
public class CheckedTools {
	@Autowired
	private BaseResultFactory<CheckedResult> checkedResultFactory;
	@Autowired
	private KickoutSessionControlFilter kickoutFilter;
	/**
	 * 檢查Ip地址是否有效
	 * @param ip
	 * @return
	 * @throws SocketException 
	 */
	public CheckedResult checkedIp(String ip){
		if(StringUtils.isEmpty(ip)){
			return checkedResultFactory.errorResult("ip地址不能為空...");
		}
		Pattern pattern = Pattern.compile("\\b((?!\\d\\d\\d)\\d+"
				+ "|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+"
				+ "|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+"
				+ "|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+"
				+ "|1\\d\\d|2[0-4]\\d|25[0-5])\\b");
		if(pattern.matcher(ip).matches()){
			// 判斷是否是本機Ip
			return checkedResultFactory.successResult(ip);
		}else{
			return checkedResultFactory.errorResult("ip地址格式有誤");
		}
	}
	/**
	 * 檢查監聽端口是否有效
	 * @param port
	 * @return
	 */
	public CheckedResult checkedPort(String sport){
		int port = 0;
		try {
			port = Integer.parseInt(sport);
		} catch (Exception e) {
			return checkedResultFactory.errorResult("網絡監聽端口必須是數字...");
		}
		if(port <= 1024 || port > 65535){
			return checkedResultFactory.errorResult("監聽端口範圍為1024~65535之間...");
		}
		return checkedResultFactory.successResult(port);
	}
	/**
	 * 驗證設置的是否啟用人員求救報警參數
	 * @param personHelpMode
	 * @return
	 */
	public CheckedResult checkedPersonHelpMode(String personHelpMode){
		if("true".equalsIgnoreCase(personHelpMode) || "false".equalsIgnoreCase(personHelpMode)){
			return checkedResultFactory.successResult(personHelpMode);
		}
		return checkedResultFactory.errorResult("是否啟用人員求救報警參數必須是true或false...");
	}
	/**
	 * 驗證設置的是否啟用低電量報警參數
	 * @param lowValMode
	 * @return
	 * @throws Exception
	 */
	public CheckedResult checkedLowBatMode(String lowValMode){
		if("true".equalsIgnoreCase(lowValMode) || "false".equalsIgnoreCase(lowValMode)){
			return checkedResultFactory.successResult(lowValMode);
		}
		return checkedResultFactory.errorResult("是否啟用低電量報警參數必須是true或false...");
	}
	/**
	 * 驗證設置的低電量是否有效
	 * @param lowVal
	 * @return
	 */
	public CheckedResult checkedLowBat(String lowVal){
		int lowBat = 20;
		try {
			lowBat = Integer.parseInt(lowVal);
		} catch (Exception e) {
			return checkedResultFactory.errorResult("低電量設置的值必須是壹個數值...");
		}
		if(lowBat < 0 || lowBat > 100){
			return checkedResultFactory.errorResult("低電量設置值範圍為0~100...");
		}
		return checkedResultFactory.successResult(lowBat);
	}
	/**
	 * 驗證 未移動時間是否有效
	 * @param notMoveTimeVal
	 * @return
	 */
	public CheckedResult checkedNotMoveTime(String notMoveTimeVal){
		int notMoveTime = 1;// 未移動時間以分鐘為單位
		try {
			notMoveTime = Integer.parseInt(notMoveTimeVal);
		} catch (Exception e) {
			return checkedResultFactory.errorResult("未移動時間值必須是數值...");
		}
		if (notMoveTime < 1 || notMoveTime > 1092) {
			return checkedResultFactory.errorResult("未移動時間值範圍為1~1092min...");
		}
		return checkedResultFactory.successResult(notMoveTime);
	}
	/**
	 * 校驗人員滯留報警模式是否有效
	 * @param resWarmModeVal
	 * @return
	 */
	public CheckedResult checkedResWarmMode(String resWarmModeVal){
		int resWarmMode = 1;
		try {
			resWarmMode = Integer.parseInt(resWarmModeVal);
		} catch (Exception e) {
			return checkedResultFactory.errorResult("滯留警告模式參數必須是數值...");
		}
		if(resWarmMode != 0 && resWarmMode!= 1 && resWarmMode != 2){
			return checkedResultFactory.errorResult("滯留警告模式參數只能是0,1,2,其中0：不產生人員滯留報警；1：系統設置的警報滯留時間；2：每個人員設置滯留報警時間...");
		}
		return checkedResultFactory.successResult(resWarmMode);
	}
	/**
	 * 檢測異常斷開的檢測時間值是否有效
	 * @param disconnectTimeVal
	 * @return
	 */
	public CheckedResult checkedDisconnectTimeVal(String disconnectTimeVal) {
		int disconnectTime = 60;
		try {
			disconnectTime = Integer.parseInt(disconnectTimeVal);
		} catch (Exception e) {
			return checkedResultFactory.errorResult("檢查異常斷開時間間隔必須是數值...");
		}
		if(disconnectTime <= 0 || disconnectTime > 60000){
			return checkedResultFactory.errorResult("檢查異常斷開時間間隔範圍0~60000s...");
		}
		return checkedResultFactory.successResult(disconnectTime);
	}
	/**
	 * 檢測卡片異常斷開參數1是否有效
	 * @param disconnectTimeVal1
	 * @return
	 */
	public CheckedResult checkedTagDisconnectTimeVal1(String disconnectTimeVal1) {
		int disconnectTime1 = 5;
		try {
			disconnectTime1 = Integer.parseInt(disconnectTimeVal1);
		} catch (Exception e) {
			return checkedResultFactory.errorResult("檢測卡片異常斷開參數1必須是數值...");
		}
		if(disconnectTime1 <= 0 || disconnectTime1 > 65535){
			return checkedResultFactory.errorResult("檢測卡片異常斷開參數1範圍為0~65535...");
		}
		return checkedResultFactory.successResult(disconnectTime1);
	}
	/**
	 * 檢測卡片異常斷開參數2是否有效
	 * @param disconnectTimeVal1
	 * @return
	 */
	public CheckedResult checkedTagDisconnectTimeVal2(String disconnectTimeVal2) {
		int disconnectTime2 = 5;
		try {
			disconnectTime2 = Integer.parseInt(disconnectTimeVal2);
		} catch (Exception e) {
			return checkedResultFactory.errorResult("檢測卡片異常斷開參數2必須是數值...");
		}
		if(disconnectTime2 <= 0 || disconnectTime2 > 65535){
			return checkedResultFactory.errorResult("檢測卡片異常斷開參數2範圍為0~65535...");
		}
		return checkedResultFactory.successResult(disconnectTime2);
	}
	/**
	 * 檢測節點異常斷開參數1是否有效
	 * @param disconnectTimeVal1
	 * @return
	 */
	public CheckedResult checkedNodeDisconnectTimeVal1(String disconnectTimeVal1) {
		int disconnectTime1 = 5;
		try {
			disconnectTime1 = Integer.parseInt(disconnectTimeVal1);
		} catch (Exception e) {
			return checkedResultFactory.errorResult("檢測節點設備異常斷開參數1必須是數值...");
		}
		if(disconnectTime1 <= 0 || disconnectTime1 > 65535){
			return checkedResultFactory.errorResult("檢測節點設備異常斷開參數1範圍為0~65535...");
		}
		return checkedResultFactory.successResult(disconnectTime1);
	}
	/**
	 * 檢測節點異常斷開參數1是否有效
	 * @param disconnectTimeVal1
	 * @return
	 */
	public CheckedResult checkedNodeDisconnectTimeVal2(String disconnectTimeVal2) {
		int disconnectTime2 = 5;
		try {
			disconnectTime2 = Integer.parseInt(disconnectTimeVal2);
		} catch (Exception e) {
			return checkedResultFactory.errorResult("檢測節點設備異常斷開參數2必須是數值...");
		}
		if(disconnectTime2 <= 0 || disconnectTime2 > 65535){
			return checkedResultFactory.errorResult("檢測節點設備異常斷開參數2範圍為0~65535...");
		}
		return checkedResultFactory.successResult(disconnectTime2);
	}
	/**
	 * 檢查基站是否顯示上報時間
	 * @param strShow
	 * @return
	 */
	public CheckedResult checkedNodeShowReport(String strShow){
		if(!"true".equalsIgnoreCase(strShow) && !"false".equalsIgnoreCase(strShow)){
			return checkedResultFactory.errorResult("基站管理地圖模式中是否顯示基站上報時間參數必須是true或false...");
		}
		boolean isShow = false;
		if("true".equalsIgnoreCase(strShow)){
			isShow = true;
		} else {
			isShow = false;
		}
		return checkedResultFactory.successResult(isShow);
	}
	public CheckedResult checkedReportWarnTime(String sReportWarnTime){
		int reportWarnTime = 0;
		try {
			reportWarnTime = Integer.parseInt(sReportWarnTime);
		} catch (Exception e) {
			return checkedResultFactory.errorResult("警報報表輸出選擇卡默認時間必須是整形...");
		}
		return checkedResultFactory.successResult(reportWarnTime);
	}
	public CheckedResult checkedTreeRefreshTime(String sRefreshTime) {
		int treeRefreshTime = 0;
		try {
			treeRefreshTime = Integer.parseInt(sRefreshTime);
		} catch (Exception e) {
			return checkedResultFactory.errorResult("定时刷新基站/参考点连接图時間必須是整形...");
		}
		return checkedResultFactory.successResult(treeRefreshTime);
	}
	
	public CheckedResult checkedShowDebug(String sShowDebug) {
		boolean isShowDebug = false;
		try {
			isShowDebug = Boolean.parseBoolean(sShowDebug);
		} catch (Exception e) {
		}
		return checkedResultFactory.successResult(isShowDebug);
	}
	
	public CheckedResult checkedShowMonitorRegionNumber(String sShowMonitorRegionNumber) {
		boolean isShowMonitorRegionNumber = false;
		try {
			isShowMonitorRegionNumber = Boolean.parseBoolean(sShowMonitorRegionNumber);
		} catch (Exception e) {
		}
		return checkedResultFactory.successResult(isShowMonitorRegionNumber);
	}
	public CheckedResult checkedTreeIsAutoRefresh(String sIsRefreshTime) {
		if(!"true".equalsIgnoreCase(sIsRefreshTime) && !"false".equalsIgnoreCase(sIsRefreshTime)){
			return checkedResultFactory.errorResult("是否定时刷新基站/参考点连接图必須是true或false...");
		}
		boolean isRefreshTime = false;
		if("true".equalsIgnoreCase(sIsRefreshTime)){
			isRefreshTime = true;
		} else {
			isRefreshTime = false;
		}
		return checkedResultFactory.successResult(isRefreshTime);
	}
	/**
	 * 檢查基站設備在地圖上是否可見
	 * @param visible
	 * @return
	 */
	public CheckedResult checkedNodeVisible(String visible){
		if(!"true".equalsIgnoreCase(visible) && !"false".equalsIgnoreCase(visible)){
			return checkedResultFactory.errorResult("檢測節點設備是否可見必須是true或false...");
		}
		boolean isVisible = false;
		if("true".equalsIgnoreCase(visible)){
			isVisible = true;
		} else {
			isVisible = false;
		}
		return checkedResultFactory.successResult(isVisible);
	}
	/**
	 * 檢查卡片設備在地圖上超時是否可見
	 * @param visible
	 * @return
	 */
	public CheckedResult checkedTagOverVisible(String visible){
		if(!"true".equalsIgnoreCase(visible) && !"false".equalsIgnoreCase(visible)) {
			return checkedResultFactory.errorResult("檢測節點設備是否可見必須是true或false...");
		}
		boolean isVisible = false;
		if("true".equalsIgnoreCase(visible)) {
			isVisible = true;
		} else {
			isVisible = false;
		}
		return checkedResultFactory.successResult(isVisible);
	}
	/**
	 * 檢查卡片設備在地圖上
	 * @param sOverTime
	 * @return
	 */
	public CheckedResult checkedTagOverTime(String sOverTime){
		int overTime = 0;
		try {
			overTime = Integer.parseInt(sOverTime);
		} catch (Exception e) {
			return checkedResultFactory.errorResult("超時未接收超時時間必須是整型...");
		}
		if(overTime < 30 || overTime > 255) {
			return checkedResultFactory.errorResult("超時未接收超時時間範圍為 30 ~ 255s 之間");
		}
		return checkedResultFactory.successResult(overTime);
	}
	/**
	 * 檢測滯留報警模式
	 * @param sresWarmModel
	 * @return
	 */
	public CheckedResult checkedResWarmModel(String sresWarmModel) {
		int resWarmModel = 0;
		try {
			resWarmModel = Integer.parseInt(sresWarmModel);
		} catch (Exception e) {
			return checkedResultFactory.errorResult("滯留報警模式值必須是數值類型...");
		}
		if(resWarmModel < 0 || resWarmModel > 2){
			return checkedResultFactory.errorResult("滯留報警模式值只能為0,1,2...");
		}
		return checkedResultFactory.successResult(resWarmModel);
	}
	public CheckedResult checkedResWarmTime(String sresWarmTime){
		int resWarmTime = 0;
		try {
			resWarmTime = Integer.parseInt(sresWarmTime);
		} catch (Exception e) {
			return checkedResultFactory.errorResult("滯留時間值必須是數值類型...");
		}
		if(resWarmTime < 0){
			return checkedResultFactory.errorResult("滯留時間不能小於等於0 min");
		}
		return checkedResultFactory.successResult();
		
	}
	/**
	 * 檢測使能警告訊息的參數
	 * @param sEnabled
	 * @return
	 */
	public CheckedResult checkedAlarmEnabled(String sEnabled) {
		if(!"true".equalsIgnoreCase(sEnabled) && !"false".equalsIgnoreCase(sEnabled)) {
			return checkedResultFactory.errorResult("警報訊息使能必須是true或false...");
		}
		boolean enabled = false;
		if("true".equalsIgnoreCase(sEnabled)) {
			enabled = true;
		} else {
			enabled = false;
		}
		return checkedResultFactory.successResult(enabled);
	}
	/**
	 * 檢測卡片斷開的報警時間
	 * @param stagDisAlarmTime
	 * @return
	 */
	public CheckedResult checkedDisAlarmTime(String stagDisAlarmTime) {
		int tagDisAlarmTime = 30;
		try {
			tagDisAlarmTime = Integer.parseInt(stagDisAlarmTime);
		} catch (Exception e) {
			return checkedResultFactory.errorResult("斷開報警時間只能為數值...");
		}
		return checkedResultFactory.successResult(tagDisAlarmTime);
	}
	public CheckedResult checkedIsAutoClear(String sisAuto){
		if(!"true".equalsIgnoreCase(sisAuto) && !"false".equalsIgnoreCase(sisAuto)) {
			return checkedResultFactory.errorResult("是否啟用自動清除必須是true或false...");
		}
		boolean isAuto = false;
		if("true".equalsIgnoreCase(sisAuto)){
			isAuto = true;
		} else {
			isAuto = false;
		}
		return checkedResultFactory.successResult(isAuto);
	}
	public CheckedResult checkedAutoClearTime(String sautoClearAlarmMsgTime) {
		int autoClearAlarmMsgTime = 0;
		try {
			autoClearAlarmMsgTime = Integer.parseInt(sautoClearAlarmMsgTime);
		} catch (Exception e) {
			return checkedResultFactory.errorResult("自動清除時間必須是數值類型...");
		}
		if(autoClearAlarmMsgTime <= 0){
			return checkedResultFactory.errorResult("自動清除時間大於等於0...");
		}
		return checkedResultFactory.successResult(autoClearAlarmMsgTime);
	}
	public CheckedResult checkedAccessStart_EndTime(String strAccessTime) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
		Date accessTime = null;
		try {
			accessTime = simpleDateFormat.parse(strAccessTime);
		} catch (ParseException e) {
			return checkedResultFactory.errorResult("進出管理的統計時間格式必須是HH:mm:ss...");
		}
		return checkedResultFactory.successResult(accessTime);
	}
	public CheckedResult checkedIsAutoStatisticAccess(String strisAutoStatistic) {
		if(!"true".equalsIgnoreCase(strisAutoStatistic) 
		&& !"false".equalsIgnoreCase(strisAutoStatistic)) {
			return checkedResultFactory.errorResult("是否啟用自動清除必須是true或false...");
		}
		boolean isAuto = false;
		if("true".equalsIgnoreCase(strisAutoStatistic)){
			isAuto = true;
		} else {
			isAuto = false;
		}
		return checkedResultFactory.successResult(isAuto);
	}
	/**
	 * 检测同一个账号最多能被多少人登陆
	 * @param sMaxAccountsNumber
	 * @return
	 */
	public CheckedResult checkedMaxAccountsNumber(String sMaxAccountsNumber){
		int maxAccoutsNumber = 10;
		try {
			maxAccoutsNumber = Integer.parseInt(sMaxAccountsNumber);
		} catch (Exception e) {
		}
		// 设置登陆时的最大账号数
		kickoutFilter.setMaxSession(maxAccoutsNumber);
		return checkedResultFactory.successResult(maxAccoutsNumber);
	}
	public CheckedResult checkedOptimizeMode(String sOptimizeMode) {
		int optimizeMode = 1;
		try {
			optimizeMode = Integer.parseInt(sOptimizeMode);
		} catch (Exception e) {
		}
		return checkedResultFactory.successResult(optimizeMode);
	}
	public CheckedResult checkedOptimizeValue(String sOptimizeValue) {
		int optimizeValue = 1;
		try {
			optimizeValue = Integer.parseInt(sOptimizeValue);
		} catch (Exception e) {
		}
		return checkedResultFactory.successResult(optimizeValue);
	}
	public CheckedResult checkedManualSetCurrentDoorStatusDelay(String sDelay) {
		int delay = 30;
		try {
			delay = Integer.parseInt(sDelay);
		} catch (Exception e) {
		}
		return checkedResultFactory.successResult(delay);
	}
}
