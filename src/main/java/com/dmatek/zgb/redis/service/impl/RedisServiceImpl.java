package com.dmatek.zgb.redis.service.impl;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.util.Strings;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.dmatek.zgb.redis.service.RedisService;
@Service("redisService")
public class RedisServiceImpl implements RedisService{
	@Autowired
	private RedisTemplate<String,Object> redisTemplate;
	@Override
	public boolean expire(String key, long timeout) throws Exception {
		if(!Strings.isEmpty(key) && timeout > 0) {
			return redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
		}
		return false;
	}
	@Override
	public long getExpire(String key) throws Exception {
		if(!Strings.isEmpty(key)) {
			return redisTemplate.getExpire(key, TimeUnit.SECONDS);
		}
		return 0;
	}
	@Override
	public boolean hasKey(String key) throws Exception {
		if(!Strings.isEmpty(key)) {
			return redisTemplate.hasKey(key);
		}
		return false;
	}
	@Override
	public void deleteKeys(String... keys) throws Exception {
		if(null != keys){
			redisTemplate.delete(CollectionUtils.asList(keys));
		}
	}
	@Override
	public Object get(String key) throws Exception {
		if(!Strings.isEmpty(key)){
			return redisTemplate.opsForValue().get(key);
		}
		return null;
	}
	@Override
	public boolean set(String key, Object obj) throws Exception {
		if(!Strings.isEmpty(key)){
			redisTemplate.opsForValue().set(key, obj);
			return true;
		}
		return false;
	}
	@Override
	public Object getHmItem(String key, String item) throws Exception {
		if(!Strings.isEmpty(key) && !Strings.isEmpty(item)){
			return redisTemplate.opsForHash().get(key, item);
		}
		return null;
	}
	@Override
	public Map<Object, Object> getHmAll(String key) throws Exception {
		if(!Strings.isEmpty(key)){
			return redisTemplate.opsForHash().entries(key);
		}
		return null;
	}
	@Override
	public boolean setHmAll(String key, Map<String, Object> map)
			throws Exception {
		if(!Strings.isEmpty(key) && null != map){
			redisTemplate.opsForHash().putAll(key, map);
			return true;
		}
		return false;
	}
	@Override
	public boolean setHmItem(String key, String item, Object obj)
			throws Exception {
		if(!Strings.isEmpty(key) && null != item){
			redisTemplate.opsForHash().put(key, item, obj);
			return true;
		}
		return false;
	}
	@Override
	public boolean deleteHmItem(String key, Object... item) throws Exception {
		if(!Strings.isEmpty(key) && null != item){
			redisTemplate.opsForHash().delete(key, item);
			return true;
		}
		return false;
	}
	@Override
	public boolean hasHmItem(String key, String item) throws Exception {
		if(!Strings.isEmpty(key) && !Strings.isEmpty(item)){
			redisTemplate.opsForHash().hasKey(key, item);
			return true;
		}
		return false;
	}
}
