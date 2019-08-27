package com.dmatek.zgb.application.listener;
import java.io.File;
import java.io.IOException;
import java.net.BindException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import com.dmatek.zgb.controller.setting.params.base.SystemSettingParamTool;
import com.dmatek.zgb.db.access.service.AccessService;
import com.dmatek.zgb.db.access.work.service.AccessWorkService;
import com.dmatek.zgb.db.log.service.OperationLogService;
import com.dmatek.zgb.db.permission.service.AccountService;
import com.dmatek.zgb.db.permission.service.RoleService;
import com.dmatek.zgb.db.person.service.PersonService;
import com.dmatek.zgb.db.pojo.permission.Account;
import com.dmatek.zgb.db.pojo.permission.Role;
import com.dmatek.zgb.db.pojo.person.Person;
import com.dmatek.zgb.db.pojo.setting.Region;
import com.dmatek.zgb.db.setting.service.RegionService;
import com.dmatek.zgb.db.warn.service.AbnormalBaseService;
import com.dmatek.zgb.db.warn.service.AbnormalReferService;
import com.dmatek.zgb.db.warn.service.AbnormalTagService;
import com.dmatek.zgb.db.warn.service.AreaControllerService;
import com.dmatek.zgb.db.warn.service.LowPowerService;
import com.dmatek.zgb.db.warn.service.NotMoveService;
import com.dmatek.zgb.db.warn.service.PersonHelpService;
import com.dmatek.zgb.file.update.CardUploadProperties;
import com.dmatek.zgb.file.update.FileUploadProperteis;
import com.dmatek.zgb.file.update.StaffUploadProperties;
import com.dmatek.zgb.login.access.filter.KickoutSessionControlFilter;
import com.dmatek.zgb.monitor.listener.MonitorStarter;
import com.dmatek.zgb.params.setting.cache.ParamsKey;

@Component
public class ZGBApplicationListener implements ApplicationListener<ContextRefreshedEvent>{
	private Logger logger = Logger.getLogger(ZGBApplicationListener.class);
	@Autowired
	private MonitorStarter monitorStarter;
	@Autowired
	private AccountService accountService;
	@Autowired
	private RoleService roleService;
	@Autowired
    private FileUploadProperteis fileUploadProperteis;
	@Autowired
	private CardUploadProperties cardUploadProperties;
	@Autowired
	private RegionService regionService;
	@Autowired
	private StaffUploadProperties staffUploadProperties;
	@Autowired
	private PersonService personService;
	@Autowired
	private KickoutSessionControlFilter kickoutFilter;
	@Autowired
	private PersonHelpService personHelpService;
	@Autowired
	private AreaControllerService areaControllerService;
	@Autowired
	private NotMoveService notMoveService;
	@Autowired
	private LowPowerService lowPowerService;
	@Autowired
	private AbnormalTagService abnormalTagService;
	@Autowired
	private AbnormalReferService abnormalReferService;
	@Autowired
	private AbnormalBaseService abnormalBaseService;
	@Autowired
	private SystemSettingParamTool sysSettingParams;
	@Autowired
	private OperationLogService logService;
	// 进出记录服务
	@Autowired
	private AccessService accessService;
	// 统计每天进出的记录
	@Autowired
	private AccessWorkService accessWorkService;
	
	private final static String DEFAULT_ROLE = "super", DEFAULT_ROLE_DES = "超級管理員角色",
			DEFAULT_ACCOUNT = "Admin", DEFAULT_ACCOUNT_PSW = "123456";
	private final static String DEBUG_ACCOUNT = "Debuger", 
								DEBUG_ACCOUNT_PSW = "123456";// 調試模式
	private final static String ORIGINAL_PATH = "C:/ZGBTemp/HISTORY";
	private final static int OneDay = 24 * 60 * 60 * 1000;
	/**
	 * 應用啟動時自動執行監聽器
	 */
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (null != monitorStarter) {
			try {
				monitorStarter.start();
			} catch (BindException e) {
				logger.error("啟動監聽啟動器的監聽端口已經被占用...");
			} catch (UnknownHostException | SocketException e) {
				logger.error("啟動監聽啟動器出現異常：" + e.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			// 初始化数据库
			initAllDataBase();
			// 初始化配置文件
			initConfiguration();
			// 清除日誌文件（包括：操作日誌、數據日誌)
			clearAllLogs();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 设置同一个账号可以被多少人登陆
		int maxNumber = (int) sysSettingParams
				.getSysParamValue(ParamsKey.maxNumberAccounts);
		logger.warn("同一個賬號只能被"+maxNumber+"同時登陸...");
		kickoutFilter.setMaxSession(maxNumber>1?maxNumber:1);
	}
	private void clearAllLogs() throws IOException, ParseException {
		boolean isClearOrignData = (boolean) sysSettingParams
				.getSysParamValue(ParamsKey.autoClearDutyData);
		if(isClearOrignData){
			int clearOrignDataDays = (int) sysSettingParams
					.getSysParamValue(ParamsKey.autoClearDutyDataTime);
			clearOriginalLogs(clearOrignDataDays);
		}
		boolean isClearOperationRecords = (boolean) sysSettingParams
				.getSysParamValue(ParamsKey.autoClearOperationRecord);
		if(isClearOperationRecords){
			int clearOrignDataDays = (int) sysSettingParams
					.getSysParamValue(ParamsKey.autoClearOperationRecordTime);
			clearOperationLogs(clearOrignDataDays);
		}
	}
	
	/**
	 * 清除原始數據日誌
	 * @param days
	 * @throws IOException 
	 * @throws ParseException 
	 */
	private void clearOriginalLogs(int interval) throws ParseException {
		File orgDir = new File(ORIGINAL_PATH);
		if (!orgDir.exists() && !orgDir.isDirectory()) {
			return;
		}
		List<File> deletes = new ArrayList<File>();
		Date curDt = new Date();
		SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyyMMdd");
		for (File tagfile : orgDir.listFiles()) {
			// 遍历所有的tag文件
			File[] dtFiles = tagfile.listFiles();
			for (File file : dtFiles) {
				// 遍历tag文件中所有的日期文件
				String ymd = file.getName();
				Date fileDt = simpleFormat.parse(ymd);
				// 文件时间
				int days = (int) ((curDt.getTime() - fileDt.getTime()) / OneDay);
				if (days > interval) {
					// 我们应该清除当前的文件
					deletes.add(file);
				}
			}
		}
		deleteFiles(deletes, orgDir);
	}
	private void deleteFiles(List<File> deletes,File orgDir) {
		try {
			if (deletes.size() > 0) {
				for (File file : deletes) {
					FileUtils.deleteDirectory(file);
				}
			}
		} catch (Exception e) {
		}
		StringBuffer loggerMsg = new StringBuffer("自动清除卡片原始记录讯息[");
		for (File file : orgDir.listFiles()) {
			if(null != file.list() && file.list().length <= 0) {
				FileUtils.deleteQuietly(file);
				loggerMsg.append(file.getName() + ",");
			}
		}
		loggerMsg.append("]");
		logger.warn(loggerMsg.toString());
	}
	/**
	 * 清除操作日誌
	 * @param days
	 */
	private void clearOperationLogs(int days){
		try {
			logService.clearLogs(days);
			logger.warn("清除超出【days: " + days + "】天的人員操作資料成功...");
		} catch (Exception e) {
			logger.error("清除超出【days: " + days + "】天的人員操作資料出現異常>>>>" + e.toString());
		}
	}
	/**
	 * 初始化配置文件
	 * @throws Exception 
	 */
	private void initConfiguration() throws Exception{
		List<File> deletes = new ArrayList<File>();
		File imgsDir = new File(fileUploadProperteis.getUploadFolder());
		if (imgsDir.isAbsolute()) {
			File[] imgs = imgsDir.listFiles();
			for (File file : imgs) {
				List<Region> regions = regionService.findImageName(file
						.getName());
				if (null == regions || regions.size() <= 0) {
					// 说明此时可以进行删除操作了
					deletes.add(file);
				}
			}
		}
		File staffsDir = new File(staffUploadProperties.getUploadFolder());
		if (staffsDir.isAbsolute()) {
			File[] staffs = staffsDir.listFiles();
			for (File file : staffs) {
				List<Person> persons = personService.findImageName(file
						.getName());
				if (null == persons || persons.size() <= 0) {
					// 说明此时可以进行删除操作了
					deletes.add(file);
				}
			}
		}
		deletes.addAll(getAllFiles(cardUploadProperties.getUploadFolder()));
		for (File file : deletes) {
			if ("sysperson.jpg".equalsIgnoreCase(file.getName())) {
				continue;
			}
			file.delete();
		}
	}
	
	private List<File> getAllFiles(String dir) {
		List<File> allFiles = new ArrayList<File>();
		File allDir = new File(dir);
		if(allDir.isAbsolute() && allDir.exists()) {
			File[] cardWords = allDir.listFiles();
			for (File file : cardWords) {
				if(file.isFile()) {
					allFiles.add(file);
				} else if(file.isDirectory()) {
					allFiles.addAll(getAllFiles(file.getAbsolutePath()));
				}
			}
		}
		return allFiles;
	}
	
	/**
	 * 初始化所有的数据库
	 * @throws Exception
	 */
	private void initAllDataBase() throws Exception{
		// 初始化賬戶數據庫
		initAccountDataBase();
		// 获取是否清除数据库
		boolean isAutoClearAlarm = (boolean) sysSettingParams
				.getSysParamValue(ParamsKey.autoClearAlarmMsg);
		if(isAutoClearAlarm) {
			// 清理警告訊息數據庫 的時間
			int days = (int) sysSettingParams
					.getSysParamValue(ParamsKey.autoClearAlarmMsgTime);
			clearWarns(days);
		}
		// 
		boolean isAutoClearAccessRecords = (boolean) sysSettingParams
				.getSysParamValue(ParamsKey.autoClearAccessRecords);
		if(isAutoClearAccessRecords) {
			int days = (int) sysSettingParams
					.getSysParamValue(ParamsKey.autoClearAccessRecordsTime);
			// 默认最少30天的记录
			days = days <= 60 ? 60 : days;
			// 清除出入记录
			clearAccessRecords(days);
		}
	}
	/**
	 * 清除进出记录
	 * @param days
	 * @throws Exception 
	 */
	private void clearAccessRecords(int days) throws Exception {
		accessService.clearRecords(days);
		accessWorkService.clearRecords(days);
	}
	/**
	 * 清理警告訊息
	 * @throws Exception 
	 */
 	private void clearWarns(int days){
		// 獲取警告清理的時間間隔
		try {
			personHelpService.clearPersonHelps(days);
			areaControllerService.clearAreaControllers(days);
			notMoveService.clearNotMoves(days);
			lowPowerService.clearLowPowers(days);
			abnormalTagService.clearAbnormalTags(days);
			abnormalReferService.clearAbnormalRefers(days);
			abnormalBaseService.clearAbnormalBases(days);
			logger.warn("清除超出【days: " + days + "】天的數據庫所有警告訊息資料成功...");
		} catch (Exception e) {
			logger.error("清除超出【days: " + days + "】天的數據庫警告訊息出現異常>>>>" + e.toString());
		}
	}
	/**
	 * 初始化数据库数据
	 */
	private void initAccountDataBase() {
		// 查看Role（角色）數據庫，是否存在Super用戶
		Role role = null;
		try {
			role = roleService.findOneName(DEFAULT_ROLE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if (null == role) {
				role = new Role();
				role.setName(DEFAULT_ROLE);
				role.setDes(DEFAULT_ROLE_DES);
				if (roleService.addRole(role)) {
					role = roleService.findOneName(DEFAULT_ROLE);
					logger.warn("添加角色" + role.toString() + "成功...");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 查看Account數據庫，是否存在Account用戶
		Account account = null;
		try {
			account = accountService.findOneFromName(DEFAULT_ACCOUNT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if (null == account) {
				account = new Account();
				account.setName(DEFAULT_ACCOUNT);
				account.setPsw(DEFAULT_ACCOUNT_PSW);
				account.setRoleId(role.getId());
				if (accountService.addAccount(account)) {
					logger.warn("添加賬戶" + account.toString() + "成功...");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Account degugger = null;
		try {
			degugger = accountService.findOneFromName(DEBUG_ACCOUNT);
		} catch (Exception e) {
			
		}
		if(null == degugger) {
			degugger = new Account();
			degugger.setName(DEBUG_ACCOUNT);
			degugger.setPsw(DEBUG_ACCOUNT_PSW);
			degugger.setRoleId(role.getId());
			try {
				if (accountService.addAccount(degugger)) {
					logger.warn("添加賬戶" + degugger.toString() + "成功...");
				}
			} catch (Exception e) {
				
			}
		}
	}
}
