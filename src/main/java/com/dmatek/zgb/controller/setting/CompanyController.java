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
import com.dmatek.zgb.db.pojo.setting.Company;
import com.dmatek.zgb.db.setting.service.CompanyService;
import com.dmatek.zgb.export_.excel.CompanyExcelExport;
import com.dmatek.zgb.import_.excel.CompanyExcelImport;
import com.dmatek.zgb.log.anno.Operation;
import com.dmatek.zgb.permission.anon.PermissionName;
import com.dmatek.zgb.result.factory.base.BaseResultFactory;
import com.dmatek.zgb.setting.vo.Result;

@PermissionName(name="公司資料設置")
@RequiresPermissions(value="companys:*")
@RequestMapping("/company")
@RestController
public class CompanyController {
	private static final int SINGLE_MAXNumber = 50;
	@Autowired
	private CompanyService companyService;// 公司服务
	// 设置参数验证
	@Autowired
	private BaseSettingValidation<String, Company> baseSettingValidation;
	@Autowired
	private BaseResultFactory<Result> viewResultFactory;
	@Autowired
	private CompanyExcelImport companyExcelImport;
	@Autowired
	private CompanyExcelExport companyExcelExport;
	/**
	 * 添加公司訊息
	 * @param company
	 * @return
	 * @throws Exception
	 */
	@PermissionName(name="添加公司訊息權限")
	@RequiresPermissions(value="companys:add")
	@Operation(description="添加公司訊息")
	@PutMapping("/")
	public Result saveCompany(@RequestBody Company company) throws Exception{
		Result result = baseSettingValidation.saveValidate(company);
		if(null != result) {
			return result;
		}
		Company oldCompany = companyService.findOne(company.getNo());
		if(null != oldCompany) {
			return viewResultFactory.errorResult("需要添加的公司【No: " + company.getNo() + "】已經存在...");
		}
		if(companyService.addCompany(company)) {
			return viewResultFactory.successResult(company);
		}
		return viewResultFactory.errorResult("添加公司訊息失敗...");
	}
	/**
	 * 刪除公司記錄訊息
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	@PermissionName(name="刪除公司訊息權限")
	@RequiresPermissions(value="companys:delete")
	@Operation(description="删除公司訊息")
	@DeleteMapping("/{ids}")
	public Result deleteCompany(@PathVariable String[] ids) throws Exception{
		if(null == ids || ids.length <= 0){
			return viewResultFactory.errorResult("ID不能為空...");
		}
		if(ids.length <= SINGLE_MAXNumber){
			companyService.deleteCompany(Arrays.asList(ids));
			return viewResultFactory.successResult();
		}
		return viewResultFactory.errorResult("單次最多只能刪除"+SINGLE_MAXNumber+"記錄...");
	}
	/**
	 * 更新指定ID的公司記錄
	 * @param companyId
	 * @param company
	 * @return
	 * @throws Exception
	 */
	@PermissionName(name="更新公司訊息權限")
	@RequiresPermissions(value="companys:update")
	@Operation(description="更新公司訊息")
	@PostMapping("/{companyId}")
	public Result updateCompany(@PathVariable String companyId,
			@RequestBody Company company) throws Exception{
		company.setNo(companyId);
		Result result = baseSettingValidation.updateValidate(company);
		if(null != result){
			return result;
		}
		Company oldCompany = companyService.findOne(companyId);
		if(null == oldCompany){
			return viewResultFactory.errorResult("需要更新的公司【No: " + companyId + "】不存在...");
		}
		companyService.updateCompany(company);
		return viewResultFactory.successResult(company);
	}
	/**
	 * 獲取指定ID的公司記錄
	 * @param companyId
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/{companyId}")
	public Result findOneCompany(@PathVariable String companyId) throws Exception {
		if(StringUtils.isEmpty(companyId)) {
			return viewResultFactory.errorResult("Id不能為空...");
		}
		Company company = companyService.findOne(companyId);
		if(null != company) {
			return viewResultFactory.successResult(company);
		}
		return viewResultFactory.errorResult("公司【no:"+companyId+"】不存在...");
	}
	/**
	 * 獲取所有公司的記錄
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/")
	public Result findAllCompany() throws Exception {
		List<Company> companys = companyService.findAll();
		if(null != companys) {
			return viewResultFactory.successResult(companys);
		}
		return viewResultFactory.errorResult("查詢所有公司記錄出現異常...");
	}
	/**
	 * 獲取分頁的公司訊息
	 * @param page
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	@PermissionName(name="查看公司訊息權限")
	@RequiresPermissions(value="companys:find")
	@GetMapping("/page/")
	public Result findPageCompany(int page, int limit) throws Exception {
		List<Company> totalCompanys = companyService.findAll();
		List<Company>  pageCompanys = companyService.findPage(page, limit);
		if(null != totalCompanys && null != pageCompanys){
			return viewResultFactory.successResult(pageCompanys, totalCompanys.size());
		}
		return viewResultFactory.errorResult("查詢分頁公司記錄出現異常...");
	}
	/**
	 * 獲取公司資訊訊息
	 * @param page
	 * @param limit
	 * @param name
	 * @return
	 * @throws Exception
	 */
	@PermissionName(name="查看公司訊息權限")
	@RequiresPermissions(value="companys:find")
	@GetMapping("/page/key_name/")
	public Result findPageKeyNameCompany(@RequestParam("page") int page, @RequestParam("limit") int limit,
										 @RequestParam("name") String name) throws Exception{
		List<Company> totalCompanys = companyService.findAll();
		List<Company> pageCompanys = companyService.findPageKeyName(page, limit, name);
		if(null != totalCompanys && null != pageCompanys){
			return viewResultFactory.successResult(pageCompanys, totalCompanys.size());
		}
		return viewResultFactory.errorResult("查詢公司資料訊息【key : " + name + "】出現異常...");
	}
	
	/**
	 * 上傳公司資料
	 * @param file
	 * @return
	 * @throws Exception 
	 */
	@PostMapping("/import/{userName}")
	@PermissionName(name="上傳公司資料")
	@RequiresPermissions(value="companys:import")
	public Result importCompanys(MultipartFile file,@PathVariable String userName) throws Exception {
		if(null != file && !file.isEmpty()) {
			if(null != companyExcelImport) {
				companyExcelImport.setUserName(userName);
				Result result = companyExcelImport.importExcel(file);
				if(null != result) {
					return result;
				}
				return viewResultFactory.successResult();
			}
		}
		return viewResultFactory.errorResult("上傳的文件為空...");
	}
	
	@GetMapping("/export/")
	@PermissionName(name="導出公司模板")
	@RequiresPermissions(value="companys:export")
	public Result exportCompanys(HttpServletResponse response, HttpServletRequest request) {
		Result result = companyExcelExport.exportExcel();
		if(null != result && result.getCode() != 600) {
			return result;
		}
		return viewResultFactory.successResult(result.getData());
	}
	
	@GetMapping("/upload/")
	@PermissionName(name="導出公司模板")
	@RequiresPermissions(value="companys:export")
	public void uploadCompanysTemplate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		companyExcelExport.uploadExcel(response, request);
	}
}
