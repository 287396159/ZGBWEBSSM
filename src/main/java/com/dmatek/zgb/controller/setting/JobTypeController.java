package com.dmatek.zgb.controller.setting;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.druid.util.StringUtils;
import com.dmatek.zgb.controller.setting.validation.BaseSettingValidation;
import com.dmatek.zgb.db.pojo.setting.JobType;
import com.dmatek.zgb.db.pojo.setting.TimelyJobType;
import com.dmatek.zgb.db.setting.service.JobTypeService;
import com.dmatek.zgb.export_.excel.JobTypeExcelExport;
import com.dmatek.zgb.import_.excel.JobTypeExcelImport;
import com.dmatek.zgb.log.anno.Operation;
import com.dmatek.zgb.permission.anon.PermissionName;
import com.dmatek.zgb.result.factory.base.BaseResultFactory;
import com.dmatek.zgb.setting.vo.Result;

@RestController
@RequestMapping("/jobType")
@PermissionName(name="設置工種資料權限")
@RequiresPermissions(value="jobType:*")
public class JobTypeController {
	private static final int SINGLE_MAXNUMBER = 50;
	@Autowired
	private JobTypeService jobTypeService;
	@Autowired
	private BaseSettingValidation<String,JobType> baseSettingValidation;
	@Autowired
	private BaseResultFactory<Result> viewResultFactory;
	@Autowired
	private JobTypeExcelImport jobTypeExcelImport;
	@Autowired
	private JobTypeExcelExport jobTypeExcelExport;
	/**
	 * 增加職位訊息
	 * @param jobType
	 * @return
	 * @throws Exception
	 */
	@PutMapping("/")
	@PermissionName(name="添加工種資料權限")
	@RequiresPermissions(value="jobType:add")
	@Operation(description="添加工種資料")
	public Result saveJobType(@RequestBody JobType jobType) throws Exception{
		Result result = baseSettingValidation.saveValidate(jobType);
		if(null != result){
			return result;
		}
		JobType oldJobType = jobTypeService.findOne(jobType.getNo());
		if(null != oldJobType){
			return viewResultFactory.errorResult("需要添加的工種【No: " + jobType.getNo() + "】已經存在...");
		}
		if(jobTypeService.addJobType(jobType)){
			return viewResultFactory.successResult();
		}
		return viewResultFactory.errorResult("添加工種類型失敗...");
	}
	/**
	 * 刪除職位訊息
	 * @param ids
	 * @return
	 * @throws Exception 
	 */
	@DeleteMapping("/{ids}")
	@PermissionName(name="刪除工種資料權限")
	@RequiresPermissions(value="jobType:delete")
	@Operation(description="刪除工種資料")
	public Result deleteJobType(@PathVariable String[] ids) throws Exception{
		if(null == ids || ids.length <= 0){
			return viewResultFactory.errorResult("需要刪除的ID不能為空...");
		}
		if(ids.length <= SINGLE_MAXNUMBER){
			if(jobTypeService.deleteJobType(Arrays.asList(ids))){
				return viewResultFactory.successResult();
			}
			return viewResultFactory.errorResult("刪除工種類型失敗...");
		}
		return viewResultFactory.errorResult("單次最多只能刪除"+SINGLE_MAXNUMBER+"記錄...");
	}
	/**
	 * 更新職位類型訊息
	 * @param jobTypeId
	 * @param jobType
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/{jobTypeId}")
	@PermissionName(name="更新工種資料權限")
	@RequiresPermissions(value="jobType:update")
	@Operation(description="更新工種資料")
	public Result updateJobType(@PathVariable String jobTypeId,@RequestBody JobType jobType) throws Exception{
		if(StringUtils.isEmpty(jobTypeId)){
			return viewResultFactory.errorResult("更新ID不能為空...");
		}
		jobType.setNo(jobTypeId);
		Result result = baseSettingValidation.updateValidate(jobType);
		if(null != result){
			return result;
		}
		JobType oldJobType = jobTypeService.findOne(jobType.getNo());
		if(null == oldJobType){
			return viewResultFactory.errorResult("需要更新的工種【No: " + jobType.getNo() + "】已經存在...");
		}
		if(jobTypeService.updateJobType(jobType)){
			return viewResultFactory.successResult();
		}
		return viewResultFactory.errorResult("更新工種類型失敗...");
	}
	/**
	 * 根據工種ID查找工種訊息
	 * @param jobTypeId
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/{jobTypeId}")
	public Result findOneJobType(@PathVariable String jobTypeId) throws Exception {
		if(StringUtils.isEmpty(jobTypeId)){
			return viewResultFactory.errorResult("查找的ID不能為空!");
		}
		JobType jobType = jobTypeService.findOne(jobTypeId);
		if(null != jobType){
			return viewResultFactory.successResult(jobType);
		}
		return viewResultFactory.errorResult("工種【no:"+jobTypeId+"】不存在...");
	}
	/**
	 * 查找所有的工種訊息
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/")
	public Result findAllJobType() throws Exception {
		List<JobType> jobTypes = jobTypeService.findAll();
		if(null != jobTypes) {
			return viewResultFactory.successResult(jobTypes);
		}
		return viewResultFactory.errorResult("查找所有的工種訊息出現異常...");
	}
	
	
	@GetMapping("/timely/")
	public Result findTimelyJobTypes() throws Exception {
		List<TimelyJobType> timelyJobTypes = jobTypeService.findTimelyJobTypes();
		if(null != timelyJobTypes){
			return viewResultFactory.successResult(timelyJobTypes);
		}
		return viewResultFactory.errorResult("獲取所有的工種及時訊息出現異常");
	}
	/**
	 * 查詢分頁工種訊息
	 * @param page
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/page/")
	@PermissionName(name="查找工種資料權限")
	@RequiresPermissions(value="jobType:find")
	public Result findPageJobType(@RequestParam("page") int page, @RequestParam("limit") int limit) throws Exception {
		List<JobType> pageJobTypes  = jobTypeService.findPage(page, limit);
		List<JobType> totalJobTypes = jobTypeService.findAll();
		if(null != pageJobTypes && null != totalJobTypes) {
			return viewResultFactory.successResult(pageJobTypes, totalJobTypes.size());
		}
		return viewResultFactory.errorResult("查詢分頁工種訊息時出現異常");
	}
	/**
	 * 根據鍵名查詢工種訊息
	 * @param page
	 * @param limit
	 * @param name
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/page/key_name/")
	@PermissionName(name="查找工種資料權限")
	@RequiresPermissions(value="jobType:find")
	public Result findPageKeyNameJobType(@RequestParam("page") int page, 
			@RequestParam("limit") int limit,
			@RequestParam("name") String name) throws Exception {
		List<JobType> pageJobTypes = jobTypeService.findPageKeyName(page, limit, name);
		List<JobType> totalJobTypes = jobTypeService.findAll();
		if(null != pageJobTypes && null != totalJobTypes){
			return viewResultFactory.successResult(pageJobTypes, totalJobTypes.size());
		}
		return viewResultFactory.errorResult("查詢【key: " + name + "】工種訊息時出現異常");
	}
	/**
	 * 上傳工種資料
	 * @param file
	 * @return
	 * @throws Exception 
	 */
	@PostMapping("/import/{userName}")
	@PermissionName(name="上傳工種資料")
	@RequiresPermissions(value="jobType:import")
	public Result importJobTypes(MultipartFile file,@PathVariable String userName) throws Exception {
		if(null != file && !file.isEmpty()) {
			if(null != jobTypeExcelImport) {
				jobTypeExcelImport.setUserName(userName);
				Result result = jobTypeExcelImport.importExcel(file);
				if(null != result) {
					return result;
				}
				return viewResultFactory.successResult();
			}
		}
		return viewResultFactory.errorResult("上傳的文件為空...");
	}
	
	@GetMapping("/export/")
	@PermissionName(name="導出工種模板")
	@RequiresPermissions(value="jobType:export")
	public Result exportJobTypes() {
		Result result = jobTypeExcelExport.exportExcel();
		if(null != result && result.getCode() != 600) {
			return result;
		}
		return viewResultFactory.successResult(result.getData());
	}
	@GetMapping("/upload/")
	@PermissionName(name="導出工種模板")
	@RequiresPermissions(value="jobType:export")
	public void uploadJobTypesTemplate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		jobTypeExcelExport.uploadExcel(response, request);
	}
}
