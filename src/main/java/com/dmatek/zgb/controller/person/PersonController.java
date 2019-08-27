package com.dmatek.zgb.controller.person;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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

import com.dmatek.zgb.controller.setting.validation.BaseSettingValidation;
import com.dmatek.zgb.db.person.service.PersonService;
import com.dmatek.zgb.db.pojo.person.Person;
import com.dmatek.zgb.export_.excel.IExcelExport;
import com.dmatek.zgb.export_.word.IDrawImage;
import com.dmatek.zgb.export_.word.IWordExport;
import com.dmatek.zgb.file.update.StaffUploadProperties;
import com.dmatek.zgb.import_.excel.PersonExcelImport;
import com.dmatek.zgb.log.anno.Operation;
import com.dmatek.zgb.permission.anon.PermissionName;
import com.dmatek.zgb.result.factory.base.BaseResultFactory;
import com.dmatek.zgb.setting.vo.Result;
import com.dmatek.zgb.utils.StringUtil;
@RestController
@RequestMapping("/person")
@PermissionName(name="人員信息設置權限")
@RequiresPermissions(value="person:*")
public class PersonController {
	private static final int SINGLE_MAXNumber = 50;
	@Autowired
	private PersonService personService;
	@Autowired
	private BaseSettingValidation<String, Person> baseSettingValidation;
	@Autowired
	private BaseResultFactory<Result> viewResultFactory;
	@Autowired
	private StaffUploadProperties staffUploadProperties;
	@Autowired
	private PersonExcelImport personExcelImport;
	@Autowired
	private IExcelExport<Person> personExcelExport;
	@Autowired
	private IDrawImage<Person> drawPersonnelCard;
	/***
	 * word 文档导出
	 */
	@Autowired
	private IWordExport iWordExport;
	/**
	 * 添加人員信息
	 * @param person
	 * @return
	 * @throws Exception
	 */
	@PutMapping("/")
	@PermissionName(name="添加人員信息權限")
	@RequiresPermissions(value="person:add")
	@Operation(description="添加人員信息")
	public Result savePerson(@RequestBody Person person) throws Exception{
		Result result = baseSettingValidation.saveValidate(person);
		if(null != result) {
			return result;
		}
		Person ePerson = personService.findOneFromNo(person.getNo());
		if(null != ePerson){
			return viewResultFactory.errorResult("人員資料【no: " + person.getNo() + "】已經存在...");
		}
		if(personService.addPerson(person)) {
			return viewResultFactory.successResult();
		}
		return viewResultFactory.errorResult("添加人員信息失敗...");
	}
	/**
	 * 刪除人員信息
	 * @param nos
	 * @return
	 * @throws Exception
	 */
	@DeleteMapping("/{nos}")
	@PermissionName(name="刪除人員信息權限")
	@RequiresPermissions(value="person:delete")
	@Operation(description="刪除人員信息")
	public Result deletePerson(@PathVariable("nos") int[] serials) throws Exception {
		if(null == serials || serials.length <= 0) {
			return viewResultFactory.errorResult("需要刪除的人員編號不能為空...");
		}
		if(serials.length <= SINGLE_MAXNumber) {
			if(personService.deletePersons(serials)){
				return viewResultFactory.successResult();
			}else{
				return viewResultFactory.errorResult("刪除人員記錄失敗...");
			}
		}
		return viewResultFactory.errorResult("單次最多只能刪除"+SINGLE_MAXNumber+"記錄");
	}
	/**
	 * 修改人員信息
	 * @param person
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/{serialNumber}")
	@PermissionName(name="修改人員信息權限")
	@RequiresPermissions(value="person:update")
	@Operation(description="更新人員信息")
	public Result updatePerson(@PathVariable int serialNumber, @RequestBody Person person) throws Exception {
		person.setSerialNumber(serialNumber);
		Result result = baseSettingValidation.updateValidate(person);
		if (null != result) {
			return result;
		}
		Person ePerson = personService.findOneFromSerialNumber(serialNumber);
		if (null == ePerson) {
			return viewResultFactory.errorResult("需要修改的人員【serialNumber : "
					+ serialNumber + "】不存在...");
		}
		if (personService.updatePerson(person)) {
			return viewResultFactory.successResult();
		}
		return viewResultFactory.errorResult("修改人員信息失敗...");
	}
	/**
	 * 查詢所有的人員信息
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/")
	@PermissionName(name="查詢人員信息權限")
	@RequiresPermissions(value="person:find")
	public Result findAllPersons() throws Exception {
		List<Person> persons = personService.findAll();
		if(null != persons){
			return viewResultFactory.successResult(persons);
		}
		return viewResultFactory.errorResult("查詢所有的人員信息時出現異常...");
	}
	/**
	 * 查詢分頁人數訊息
	 * @param page
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/page/")
	@PermissionName(name="查詢人員信息權限")
	@RequiresPermissions(value="person:find")
	public Result findPagePerson(@RequestParam("page") int page,@RequestParam("limit") int limit) throws Exception {
		List<Person> totalPersons = personService.findAll();
		List<Person> pagePersons = personService.findPage(page, limit);
		if(null != totalPersons && null != pagePersons){
			return viewResultFactory.successResult(pagePersons, totalPersons.size());
		}
		return viewResultFactory.errorResult("查詢分頁人員訊息時出現異常...");
	}
	/**
	 * 查詢分頁人員訊息
	 * @param page
	 * @param limit
	 * @param name
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/page/key_name/")
	@PermissionName(name="查詢人員信息權限")
	@RequiresPermissions(value="person:find")
	public Result findPageKeyName(@RequestParam("page") int page,@RequestParam("limit") int limit,@RequestParam("name") String name) throws Exception{
		List<Person> totalPersons = personService.findAll();
		List<Person> pagePersons = personService.findPageKeyName(page, limit, name);
		if(null != totalPersons && null != pagePersons){
			return viewResultFactory.successResult(pagePersons, totalPersons.size());
		}
		return viewResultFactory.errorResult("查詢分頁人員【key : " + name + "】訊息出現異常...");
	}
	/**
	 * 根據員工編號查找員工信息
	 * @param no
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/{no}")
	@PermissionName(name="查詢人員信息權限")
	@RequiresPermissions(value="person:find")
	public Result findOneFromNo(@PathVariable("no") String no) throws Exception{
		if(StringUtils.isEmpty(no)) {
			return viewResultFactory.errorResult("員工編號不能為空...");
		}
		Person person = personService.findOneFromNo(no);
		if(null != person) {
			return viewResultFactory.successResult(person);
		}
		return viewResultFactory.errorResult("員工【編號：" + no + "】不存在...");
	}
	/**
	 * 根據員工姓名查找員工信息
	 * @param name
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/{name}")
	@PermissionName(name="查詢人員信息權限")
	@RequiresPermissions(value="person:find")
	public Result findOneFromName(@PathVariable("name") String name) throws Exception{
		if(StringUtils.isEmpty(name)){
			return viewResultFactory.errorResult("員工姓名不能為空...");
		}
		Person person = personService.findOneFromName(name);
		if(null != person) {
			return viewResultFactory.successResult(person);
		}
		return viewResultFactory.errorResult("員工【姓名：" + name + "】不存在...");
	}
	/**
	 * 根據員工tagId查找員工信息
	 * @param tagId
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/{tagId}")
	@PermissionName(name="查詢人員信息權限")
	@RequiresPermissions(value="person:find")
	public Result findOneFromTagId(@PathVariable("tagId") String tagId) throws Exception{
		if (StringUtils.isEmpty(tagId)) {
			return viewResultFactory.errorResult("員工的卡片編號不能為空...");
		}
		Person person = personService.findOneFromTagId(tagId);
		if (null != person) {
			return viewResultFactory.successResult(person);
		}
		return viewResultFactory.errorResult("員工【tagId：" + tagId + "】不能存在...");
	}
	/**
	 * 上傳人員圖片資料權限
	 * @param file
	 * @param request
	 * @return
	 */
	@PostMapping("/upload/")
	@PermissionName(name="上傳人員圖像資料權限")
	@RequiresPermissions(value="person:loadImage")
	public Result uploadPersonImage(@RequestParam("file") MultipartFile file,
			HttpServletRequest request){
		//獲取上傳圖片後保存的根路徑
		File uwbregionimg = new File(staffUploadProperties.getUploadFolder());
		if (!uwbregionimg.exists()) {
			uwbregionimg.mkdirs();
		}
		if (null != file && !file.isEmpty()) {
			String originName = file.getOriginalFilename();
			// 獲取後綴
			String stfux = originName.substring(originName.lastIndexOf("."),
					originName.length());
			String copyName = UUID.randomUUID() + stfux;
			String copyPath = staffUploadProperties.getUploadFolder()
					+ File.separator + copyName;
			File desfile = new File(copyPath);
			if (!desfile.exists()) {
				try {
					desfile.createNewFile();
				} catch (IOException e) {
					return viewResultFactory.errorResult("生成新圖片文件失敗...");
				}
			}
			try {
				file.transferTo(desfile);
			} catch (IllegalStateException | IOException e) {
				return viewResultFactory.errorResult("圖片數據傳輸失敗...");
			}
			return viewResultFactory.successResult(copyName);
		}
		return viewResultFactory.errorResult("上傳文件為空....");
	}
	/**
	 * 上傳公司資料
	 * @param file
	 * @return
	 * @throws Exception 
	 */
	@PostMapping("/import/{userName}")
	@PermissionName(name="上傳人員資料")
	@RequiresPermissions(value="person:import")
	public Result importPersons(MultipartFile file,@PathVariable String userName) throws Exception {
		if (null != file && !file.isEmpty()) {
			if (null != personExcelImport) {
				personExcelImport.setUserName(userName);
				Result result = personExcelImport.importExcel(file);
				if (null != result) {
					return result;
				}
				return viewResultFactory.successResult();
			}
		}
		return viewResultFactory.errorResult("上傳的文件為空...");
	}
	/***
	 * 生成人員資料模板
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/export/")
	@PermissionName(name="導出人員模板")
	@RequiresPermissions(value="person:export")
	public Result exportPersons() throws Exception {
		Result result = personExcelExport.exportExcel();
		if(null != result && result.getCode() != 600) {
			return result;
		}
		return viewResultFactory.successResult(result.getData());
	}
	/***
	 * 導出人員模板
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/upload/")
	@PermissionName(name="導出人員模板")
	@RequiresPermissions(value="person:export")
	public Result uploadPersonsTemplate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		personExcelExport.uploadExcel(response, request);
		return viewResultFactory.successResult();
	}
	/***
	 * 生成人员资料卡
	 * @param serialNumber
	 * @return
	 * @throws Exception 
	 */
	@GetMapping("/card/create/{serialNumber}")
	public Result createPersonCard(@PathVariable int serialNumber) throws Exception {
		Person person = null;
		person = personService.findOneFromSerialNumber(serialNumber);
		if (null == person) {
			return viewResultFactory.errorResult("人員[serialNumber: "
					+ serialNumber + "]不存在");
		}
		Image image = drawPersonnelCard.draw(person);
		// 将图片内容进行输出
		if (null != image) {
			return viewResultFactory.successResult(image);
		}
		return viewResultFactory.errorResult("創建人員資料卡[serialNumber: "
				+ serialNumber + "]失敗");
	}
	/***
	 * 根據選擇的人員ID创建资料卡
	 * @param ids
	 * @return
	 */
	@GetMapping("/export/card/{serialNumbers}")
	@PermissionName(name="導出人員資料卡")
	@RequiresPermissions(value="person:exportCard")
	public Result exportPersonsCard(@PathVariable int[] serialNumbers) {
		if (null != iWordExport) {
			List<Image> images = new ArrayList<Image>();
			for (int serialNumber : serialNumbers) {
				try {
					Person person = personService.findOneFromSerialNumber(serialNumber);
					if(null == person) {
						continue;
					}
					/**
					 * -  画出人员资料卡   -
					 */
					Image image = drawPersonnelCard.draw(person);
					if (null != image) {
						images.add(image);
					}
				} catch (Exception e) {
					return viewResultFactory.errorResult("生成卡片資料卡出現異常: "
							+ e.toString());
				}
			}
			if(images.size() <= 0) {
				return viewResultFactory.errorResult("沒有生成人員資料卡, 原因可能是選擇的資料卡沒有設置工地");
			}
			return iWordExport.createWord(StringUtil.getLocalTimeAllString()
					+ ".doc", images.toArray(new Image[0]));
		}
		return viewResultFactory.errorResult("導出Word工具沒有創建");
	}
	/***
	 * 根據選擇的人員ID導出人員資料卡
	 * {filename:.+} 说明参数是一个文件名, 我们必须保证带有后缀才行
	 * @param ids
	 * @return
	 */
	@GetMapping("/card/download/{filename:.+}")
	@PermissionName(name="導出人員資料卡")
	@RequiresPermissions(value="person:exportCard")
	public void downloadPersonCard(@PathVariable String filename,HttpServletRequest request, HttpServletResponse res) throws Exception {
		iWordExport.downloadWord(res, filename);
	}
}
