package com.dmatek.zgb.controller.setting;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
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
import com.dmatek.zgb.controller.setting.validation.BaseSettingValidation;
import com.dmatek.zgb.controller.tool.RegionTool;
import com.dmatek.zgb.db.pojo.setting.Region;
import com.dmatek.zgb.db.setting.service.RegionService;
import com.dmatek.zgb.file.update.FileUploadProperteis;
import com.dmatek.zgb.log.anno.Operation;
import com.dmatek.zgb.permission.anon.PermissionName;
import com.dmatek.zgb.result.factory.base.BaseResultFactory;
import com.dmatek.zgb.setting.vo.Result;

@RestController
@RequestMapping("/region")
@PermissionName(name="設置區域資料權限")
@RequiresPermissions(value="region:*")
public class RegionController {
	@Autowired
	private FileUploadProperteis fileUploadProperties;
	private static final int SINGLE_MAXNUMBER = 100;
	@Autowired
	private RegionService regionService;
	@Autowired
	private BaseSettingValidation<Integer, Region> baseSettingValidation;
	@Autowired
	private BaseResultFactory<Result> viewResultFactory;
	@Autowired
	private RegionTool regionTool;
	/**
	 * 添加區域訊息
	 * @param region
	 * @return
	 * @throws Exception
	 */
	@PutMapping("/")
	@PermissionName(name="添加區域資料權限")
	@RequiresPermissions(value="region:add")
	@Operation(description="添加區域資料")
	public Result saveRegion(@RequestBody Region region) throws Exception{
		// 驗證 區域訊息是否有效
		Result result = baseSettingValidation.saveValidate(region);
		if(null != result) {
			return result;
		}
		Region oldRegion = regionService.findOne(region.getId());
		if(null != oldRegion) {
			return viewResultFactory.errorResult("需要添加的區域【ID: " + region.getId() + "】已經存在...");
		}
		if(regionService.insertRegion(region)) {
			return viewResultFactory.successResult();
		}
		return viewResultFactory.errorResult("添加區域訊息失敗...");
	}
	/**
	 * 刪除區域訊息
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	@DeleteMapping("/{ids}")
	@PermissionName(name="刪除區域資料權限")
	@RequiresPermissions(value="region:delete")
	@Operation(description="刪除區域資料")
	public Result deleteRegion(@PathVariable("ids")Integer[] ids) throws Exception{
		if(null == ids || ids.length <= 0) {
			return viewResultFactory.errorResult("需要刪除的id不能為空...");
		}
		if(ids.length <= SINGLE_MAXNUMBER) {
			regionService.deleteRegion(Arrays.asList(ids));
			return viewResultFactory.successResult();
		}
		return viewResultFactory.errorResult("單次最多刪" + SINGLE_MAXNUMBER + "條記錄...");	
	}
	/**
	 * 更新區域訊息
	 * @param regionId
	 * @param region
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/{regionId}")
	@PermissionName(name="更新區域資料權限")
	@RequiresPermissions(value="region:update")
	@Operation(description="更新區域資料")
	public Result updateRegion(@PathVariable int regionId,@RequestBody Region region) throws Exception{
		region.setId(regionId);
		Result result = baseSettingValidation.updateValidate(region);
		if(null != result) {
			return result;
		}
		Region oldRegion = regionService.findOne(regionId);
		if (null == oldRegion) {
			return viewResultFactory.errorResult("需要更新的區域不能為空...");
		}
		if(regionService.updateRegion(region)) {
			return viewResultFactory.successResult();
		}
		return viewResultFactory.errorResult("更新區域信息失敗...");
	}
	/**
	 * 根據區域ID查找區域訊息
	 * @param regionId
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/{regionId}")
	public Result findOneRegion(@PathVariable int regionId) throws Exception{
		if(regionId <= 0) {
			return viewResultFactory.errorResult("ID不能小於0...");
		}
		Region region = regionService.findOne(regionId);
		if(null == region) {
			viewResultFactory.errorResult("對不起，沒有查詢到【id: " + regionId + "】的任何記錄...");
		}
		return viewResultFactory.successResult(region);
	}
	/**
	 * 查找分頁的區域訊息
	 * @param page
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/page/")
	@PermissionName(name="查找區域資料權限")
	@RequiresPermissions(value="region:find")
	public Result findPageRegion(@RequestParam("page") int page,@RequestParam("limit") int limit) throws Exception{
		List<Region> regions = regionService.findPage(page, limit);
		List<Region> allRegions = regionService.findAll();
		if(null != regions && null != allRegions) {
			return viewResultFactory.successResult(regions, allRegions.size());
		}
		return viewResultFactory.errorResult("查詢分頁區域記錄出現異常...");
	}
	/**
	 * 根據鍵值查找分頁的區域訊息
	 * @param page
	 * @param limit
	 * @param name
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/page/key_name/")
	@PermissionName(name="查找區域資料權限")
	@RequiresPermissions(value="region:find")
	public Result findPageRegionKeyName(@RequestParam("page") int page,@RequestParam("limit") int limit,@RequestParam("name") String name) throws Exception{
		List<Region> allRegions = regionService.findAll();
		List<Region> keyRegions = regionService.findPageKeyName(page, limit, name);
		if(null != allRegions && null != keyRegions) {
			return viewResultFactory.successResult(keyRegions, allRegions.size());
		}
		return viewResultFactory.errorResult("查詢分頁區域記錄出現異常...");
	}
	/**
	 * 查找所有的區域訊息
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/all/")
	public Result findAllRegion() throws Exception {
		List<Region> regions = regionService.findAll();
		if(null != regions) {
			return viewResultFactory.successResult(regions);
		}
		return viewResultFactory.errorResult("查詢所有區域記錄出現異常...");
	}
	/**
	 * 獲取組別的所有區域訊息
	 * @param groupId
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/number/{groupId}")
	public Result getGroupAllRegionsNumber(@PathVariable int groupId) throws Exception {
		// 獲取
		List<Region> regions = regionTool.getGroupAllRegionsNumber(groupId);
		if (null != regions) {
			return viewResultFactory.successResult(regions);
		}
		return viewResultFactory.errorResult("獲取組別【groupId: " + groupId
				+ "】的所有區域數量失敗...");
	}
	/**
	 * 根據組別ID查找區域訊息
	 * @param groupId
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/groupId/{groupId}")
	public Result findAllFromGroupId(@PathVariable int groupId) throws Exception {
		List<Region> regions = regionService.findAllFromGroupId(groupId);
		if(null != regions) {
			return viewResultFactory.successResult(regions);
		}
		return viewResultFactory.errorResult("根據ID查找區域記錄出現異常...");
	}
	/**
	 * 上傳區域圖片
	 * @param file
	 * @param request
	 * @return
	 */
	@PostMapping(value="/uploadImage/")
	@PermissionName(name="上傳區域地圖權限")
	@RequiresPermissions(value="region:upload")
	public Result UploadRegionImage(@RequestParam("file") MultipartFile file,
									HttpServletRequest request) throws Exception{
		//獲取上傳圖片後保存的根路徑
		File uwbregionimg = new File(fileUploadProperties.getUploadFolder());
		if(!uwbregionimg.exists()){
			uwbregionimg.mkdirs();
		}
		if(null != file && !file.isEmpty()) {
			String originName = file.getOriginalFilename();
			//獲取後綴
			String stfux = originName.substring(originName.lastIndexOf("."), originName.length());
			String copyName = UUID.randomUUID() + stfux;
			String copyPath = fileUploadProperties.getUploadFolder() + File.separator + 
							  copyName;
			File desfile = new File(copyPath);
			if(!desfile.exists()) {
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
}
