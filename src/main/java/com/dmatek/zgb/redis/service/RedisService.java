package com.dmatek.zgb.redis.service;
import java.util.Map;
/**
 * redis Service
 * @author zhangfu
 * @data 2019年1月11日 下午12:00:39
 * @Description
*/
public interface RedisService {
	/**
	 * 设置 键值 的失效时间
	 * @param key
	 * @param timeout
	 * @return
	 */
	boolean expire(String key,long timeout) throws Exception;
	/**
	 * 获取 键值 的过期时间
	 * @param key
	 * @return
	 */
	public long getExpire(String key) throws Exception;
	/**
	 * 判断 键值 是否存在
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public boolean hasKey(String key) throws Exception;
	/**
	 * 批量删除键值
	 * @param keys
	 * @throws Exception
	 */
	public void deleteKeys(String...keys) throws Exception;
	/**
	 * 获取键值对应的对象
	 * @param key
	 * @return
	 */
	public Object get(String key) throws Exception;
	/**
	 * 设置键值对象
	 * @param key
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public boolean set(String key,Object obj) throws Exception;
	/**
	 * 获取HashMap的指定指定项的值
	 * @param key
	 * @param item
	 * @return
	 * @throws Exception
	 */
	public Object getHmItem(String key, String item) throws Exception;
	/**
	 * 获取指定HashMap的所有值
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public Map<Object, Object> getHmAll(String key) throws Exception;
	/**
	 * 设置HashMap所有的Map
	 * @param key
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public boolean setHmAll(String key,Map<String,Object> map) throws Exception;
	/**
	 * 设置HashMap的指定Item的指定值
	 * @param key
	 * @param item
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public boolean setHmItem(String key,String item, Object obj) throws Exception;
	/**
	 * 删除HashMap中的项
	 * @param key
	 * @param item
	 * @return
	 * @throws Exception
	 */
	public boolean deleteHmItem(String key,Object...item) throws Exception;
	/**
	 * 查看HashMap是否存在指定项的值
	 * @param key
	 * @param item
	 * @return
	 * @throws Exception
	 */
	public boolean hasHmItem(String key,String item) throws Exception;
	
}
