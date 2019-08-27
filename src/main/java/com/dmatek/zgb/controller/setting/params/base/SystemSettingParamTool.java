package com.dmatek.zgb.controller.setting.params.base;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.stereotype.Component;
import com.dmatek.zgb.params.setting.cache.ParamsKey;
import com.dmatek.zgb.tool.check.bean.CheckedResult;

@Component
public class SystemSettingParamTool extends BaseSettingParamsTemplate {
	/**
	 * 獲取系統參數值
	 * 
	 * @param paramsKey
	 * @return
	 */
	public Object getSysParamValue(ParamsKey paramsKey) {
		String val = (String) getSettingParams().get(paramsKey.getVal());
		switch (paramsKey) {
		case checkdistime:
			int checkedDisTime = 0;
			try {
				checkedDisTime = Integer.parseInt(val);
			} catch (Exception e) {
			}
			return checkedDisTime;
		case checkednodedisK1:
			int checkedNodeDisK1 = 0;
			try {
				checkedNodeDisK1 = Integer.parseInt(val);
			} catch (Exception e) {
			}
			return checkedNodeDisK1;
		case checkednodedisK2:
			int checkedNodeDisK2 = 0;
			try {
				checkedNodeDisK2 = Integer.parseInt(val);
			} catch (Exception e) {
			}
			return checkedNodeDisK2;
		case checkedtagdisK1:
			int checkedTagDisK1 = 0;
			try {
				checkedTagDisK1 = Integer.parseInt(val);
			} catch (Exception e) {
			}
			return checkedTagDisK1;
		case checkedtagdisK2:
			int checkedTagDisK2 = 0;
			try {
				checkedTagDisK2 = Integer.parseInt(val);
			} catch (Exception e) {
			}
			return checkedTagDisK2;
		case autoClearHandleWarn:
			boolean autoClearHandleWarn = false;
			try {
				autoClearHandleWarn = Boolean.parseBoolean(val);
			} catch (Exception e) {
			}
			return autoClearHandleWarn;
		case autoClearHandleWarnTime:
			int autoClearHandleWarnTime = 5 * 60;// 默認5min
			try {
				autoClearHandleWarnTime = Integer.parseInt(val);
			} catch (Exception e) {
			}
			return autoClearHandleWarnTime;
		case accessOndutyStartTime:
			Date startTime = null;
			try {
				startTime = new SimpleDateFormat("HH:mm:ss").parse(val);
			} catch (ParseException e) {
			}
			return startTime;
		case accessOndutyEndTime:
			Date endTime = null;
			try {
				endTime = new SimpleDateFormat("HH:mm:ss").parse(val);
			} catch (ParseException e) {
			}
			return endTime;
		case accessOffdutyStartTime:
			Date offstartTime = null;
			try {
				offstartTime = new SimpleDateFormat("HH:mm:ss").parse(val);
			} catch (ParseException e) {
			}
			return offstartTime;
		case accessOffdutyEndTime:
			Date offendTime = null;
			try {
				offendTime = new SimpleDateFormat("HH:mm:ss").parse(val);
			} catch (ParseException e) {
			}
			return offendTime;
		case isAutoStatisticAccess:
			boolean isAuto = false;
			try {
				isAuto = Boolean.parseBoolean(val);
			} catch (Exception e) {
				
			}
			return isAuto;
		case accessStatisticTime:
			Date statisticTime = null;
			try {
				statisticTime = new SimpleDateFormat("HH:mm:ss").parse(val);
			} catch (ParseException e) {
			}
			return statisticTime;
		case autoClearAlarmMsg:
			boolean isAutoClearAlarmMsg = false;
			try {
				isAutoClearAlarmMsg = Boolean.parseBoolean(val);
			} catch (Exception e) {

			}
			return isAutoClearAlarmMsg;
		case autoClearAlarmMsgTime:
			int days = 365;
			try {
				days = Integer.parseInt(val);
			} catch (Exception e) {
				// TODO: handle exception
			}
			return days;
		case autoClearDutyData: 
			boolean isAutoClearDutyData = false;
			try {
				isAutoClearDutyData = Boolean.parseBoolean(val);
			} catch (Exception e) {
				// TODO: handle exception
			}
			return isAutoClearDutyData;
		case autoClearDutyDataTime:
			int clearDays = 365;
			try {
				clearDays = Integer.parseInt(val);
			} catch (Exception e) {
				// TODO: handle exception
			}
			return clearDays;
		case autoClearAccessRecordsTime:
			int clearAccessDays = 365;
			try {
				clearAccessDays = Integer.parseInt(val);
			} catch (Exception e) {
			}
			return clearAccessDays;
		case autoClearAccessRecords:
			boolean isAutoClearAccessRecords = false;
			try {
				isAutoClearAccessRecords = Boolean.parseBoolean(val);
			} catch (Exception e) {
			}
			return isAutoClearAccessRecords;
		case autoClearOperationRecord:
			boolean isAutoClearOperationRecord = false;
			try {
				isAutoClearOperationRecord = Boolean.parseBoolean(val);
			} catch (Exception e) {
			}
			return isAutoClearOperationRecord;
		case autoClearOperationRecordTime:
			int autoClearOperationDays = 365;
			try {
				autoClearOperationDays = Integer.parseInt(val);
			} catch (Exception e) {
			}
			return autoClearOperationDays;
		case maxNumberAccounts:
			int maxNumber = 10;
			try {
				maxNumber = Integer.parseInt(val);
			} catch (Exception e) {
			}
			return maxNumber;
		case optimizeMode:
			int optimizeModel = 1;
			try {
				optimizeModel = Integer.parseInt(val);
			} catch (Exception e) {
				
			}
			return optimizeModel;
		case optimizeValue:
			int optimizeVal = 3;
			try {
				optimizeVal = Integer.parseInt(val);
			} catch (Exception e) {
				
			}
			return optimizeVal;
		case manualSetCurrentDoorStatusDelay:
			int delay = 30;
			try {
				delay = Integer.parseInt(val);
			} catch (Exception e) {
			}
			return delay;
		default:
			break;
		}
		return null;
	}
	
	/**
	 * 驗證系統參數值是否有效
	 * @param key
	 * @param val
	 * @return
	 */
	@Override
	protected CheckedResult checkedSettingParams(String key, String val) {
		ParamsKey paramsKey = ParamsKey.getParamsKey(key);
		switch(paramsKey){
		case checkdistime:
			return getCheckedTools().checkedDisconnectTimeVal(val);
		case checkedtagdisK1:
			return getCheckedTools().checkedTagDisconnectTimeVal1(val);
		case checkedtagdisK2:
			return getCheckedTools().checkedTagDisconnectTimeVal2(val);
		case checkednodedisK1:
			return getCheckedTools().checkedNodeDisconnectTimeVal1(val);
		case checkednodedisK2:
			return getCheckedTools().checkedNodeDisconnectTimeVal2(val);
		case autoClearAlarmMsg:
			return getCheckedTools().checkedIsAutoClear(val);
		case autoClearAlarmMsgTime:
			return getCheckedTools().checkedAutoClearTime(val);
		case autoClearDutyData:
			return getCheckedTools().checkedIsAutoClear(val);
		case autoClearDutyDataTime:
			return getCheckedTools().checkedAutoClearTime(val);
		case autoClearOperationRecord:
			return getCheckedTools().checkedIsAutoClear(val);
		case autoClearOperationRecordTime:
			return getCheckedTools().checkedAutoClearTime(val);
		case autoClearAccessRecords:
			return getCheckedTools().checkedIsAutoClear(val);
		case autoClearAccessRecordsTime:
			return getCheckedTools().checkedAutoClearTime(val);
		case accessOndutyStartTime:
			return getCheckedTools().checkedAccessStart_EndTime(val);
		case accessOndutyEndTime:
			return getCheckedTools().checkedAccessStart_EndTime(val);
		case accessOffdutyStartTime:
			return getCheckedTools().checkedAccessStart_EndTime(val);
		case accessOffdutyEndTime:
			return getCheckedTools().checkedAccessStart_EndTime(val);
		case isAutoStatisticAccess:
			return getCheckedTools().checkedIsAutoStatisticAccess(val);
		case accessStatisticTime:
			return getCheckedTools().checkedAccessStart_EndTime(val);
		case maxNumberAccounts:
			return getCheckedTools().checkedMaxAccountsNumber(val);
		case optimizeMode:
			return getCheckedTools().checkedOptimizeMode(val);
		case optimizeValue:
			return getCheckedTools().checkedOptimizeValue(val);
		case manualSetCurrentDoorStatusDelay:
			return getCheckedTools().checkedManualSetCurrentDoorStatusDelay(val);
		default:
			break;
		}
		return getCheckedResultFactory().errorResult("鍵值【key :" + paramsKey + ",val :" + val + "】不是設置系統參數...");
	}
}
