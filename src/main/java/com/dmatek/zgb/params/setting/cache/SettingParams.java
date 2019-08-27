package com.dmatek.zgb.params.setting.cache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
/**
 * 
 * @author zf
 * @data 2018年12月17日 下午2:53:45
 * @Description
 */
public class SettingParams {
	public static final String PARAMS_DIR = "C:/ZGBTEMP/CONFIG";
	public static final String PARAMS_PATH = PARAMS_DIR + "/params.properties";
	public static final String PARAMS = "params";
	private Map<String,Object> paramsCache = null;
	public SettingParams() {
		super();
		this.paramsCache = new ConcurrentHashMap<String, Object>();
		try {
			createParamsFile();
			loadParams(PARAMS_PATH);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 根据键值获取参数值
	 * @param key
	 * @return
	 */
	public Object get(String key) {
		return paramsCache.get(key);
	}
	/**
	 * 获取所有的参数值
	 * @return
	 */
	public Map<String,Object> getAllParams(){
		return paramsCache;
	}
	/**
	 * 设置参数键值
	 * @param key
	 * @param val
	 */
	public void set(String key,Object val) {
		paramsCache.put(key, val);
	}
	/**
	 * 创建参数目录
	 * @throws IOException
	 */
	public void createParamsFile() throws IOException{
		File paramsDir = new File(PARAMS_DIR);
		if(!paramsDir.exists()){
			paramsDir.mkdirs();
		}
		File paramfile = new File(PARAMS_PATH);
		if(!paramfile.exists()){
			paramfile.createNewFile();
		}
	}
	/**
	 * 加载所有的参数值到缓存中
	 * @param properties
	 * @throws IOException 
	 */
	public void loadParams(String path) throws IOException{
		Properties properties = new Properties();
		FileInputStream fileInputStream = new FileInputStream(path);
		properties.load(fileInputStream);
		Iterator<Object> iterator = properties.keySet().iterator();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			if (ParamsKey.contain(key)) {
				paramsCache.put(key, properties.get(key));
			}
		}
	}
	/**
	 * 将缓存中的所有参数都保存到原始文件中
	 * @param path
	 * @throws IOException 
	 */
	public void saveParams(String path) throws IOException{
		Properties properties = new Properties();
		Iterator<String> iterator = paramsCache.keySet().iterator();
		while(iterator.hasNext()){
			String key = iterator.next();
			Object val = paramsCache.get(key);
			properties.put(key, val);
		}
		// 将properties保存到文件中去
		FileOutputStream out = new FileOutputStream(path);
		properties.store(out, null);
	}
	@Override
	public String toString() {
		StringBuffer sbuffer = new StringBuffer("[");
		Iterator<String> iterator = paramsCache.keySet().iterator();
		while(iterator.hasNext()){
			String key = iterator.next();
			String val = (String) paramsCache.get(key);
			sbuffer.append(key + " : " + val + ",");
		}
		sbuffer.append("]");
		return sbuffer.toString();
	}
}
