package com.dmatek.zgb.controller.setting.params.base;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.dmatek.zgb.params.setting.cache.ParamsKey;
import com.dmatek.zgb.params.setting.cache.SettingParams;
import com.dmatek.zgb.result.factory.base.BaseResultFactory;
import com.dmatek.zgb.tool.check.bean.CheckedResult;
import com.dmatek.zgb.tool.check.bean.CheckedTools;

public abstract class BaseSettingParams {
	@Autowired
	private SettingParams settingParams;
	@Autowired
	private BaseResultFactory<CheckedResult> checkedResultFactory;
	@Autowired
	private CheckedTools checkedTools;
	/**
	 * 获取所有的参数值
	 * @return
	 */
	protected Map<String, Object> getAllSettingParams(){
		return settingParams.getAllParams();
	}
	/**
	 * 更新参数信息
	 * @param key
	 * @param val
	 * @param req
	 * @throws IOException 
	 */
	protected void updateParams(String key,Object val,HttpSession session) throws IOException {
		// 将键值对设置到参数缓存中去
		settingParams.set(key, val);
		// 将缓存重新保存到文件中
		settingParams.saveParams(SettingParams.PARAMS_PATH);
		// 重新设置Session中的值
		if(null != session){
			session.setAttribute(SettingParams.PARAMS, settingParams.getAllParams());
		}
	} 
	/**
	 * 检查参数键值是否有效
	 * @param key
	 * @return
	 */
 	protected boolean checkedKey(String key) {
		if(!ParamsKey.contain(key)){
			return false;
		}
		return true;
	}
	public SettingParams getSettingParams() {
		return settingParams;
	}
	public void setSettingParams(SettingParams settingParams) {
		this.settingParams = settingParams;
	}
	public BaseResultFactory<CheckedResult> getCheckedResultFactory() {
		return checkedResultFactory;
	}
	public void setCheckedResultFactory(
			BaseResultFactory<CheckedResult> checkedResultFactory) {
		this.checkedResultFactory = checkedResultFactory;
	}
	public CheckedTools getCheckedTools() {
		return checkedTools;
	}
	public void setCheckedTools(CheckedTools checkedTools) {
		this.checkedTools = checkedTools;
	}
}
