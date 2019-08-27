package com.dmatek.zgb.redis.cache;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.dmatek.zgb.monitor.bean.NodePack;
import com.dmatek.zgb.monitor.bean.ReferPack;
import com.dmatek.zgb.monitor.device.detail.TagDetail;
import com.dmatek.zgb.redis.service.RedisService;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * 自定义redis配置类
 * @author Administrator
 * @data 2019年1月11日 上午11:52:18
 * @Description
 */
@Configuration
public class RedisConfig {
	/**
	 * 将Redis的缓存模板放到IOC容器里面
	 * @param factory
	 * @return
	 */
	@Bean
	public RedisTemplate<String, Object> getRedisTemplate(RedisConnectionFactory factory){
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
		redisTemplate.setConnectionFactory(factory);
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(om);
		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		// key采用String的序列化方式
		redisTemplate.setKeySerializer(stringRedisSerializer);
		// hash的key也采用String的序列化方式
		redisTemplate.setHashKeySerializer(stringRedisSerializer);
		// value序列化方式采用jackson
		redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
		// hash的value序列化方式采用jackson
		redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}
	/**
	 * 生成进出统计的缓存对象
	 * @param redisService
	 * @return
	 */
	@Bean
	public AccessRedisCache getAccessRedisCache(RedisService redisService){
		return new AccessRedisCache(redisService);
	}
	/**
	 * 生成卡片的缓存对象
	 * @param redisService
	 * @return
	 */
	@Bean
	public IRedisMapCache<String, TagDetail> getTagPackRedisCache(RedisService redisService) {
		return new TagPackRedisCache(redisService);
	}
	/**
	 * 生成基站的缓存对象
	 * @param redisService
	 * @return
	 */
	@Bean
	public IRedisMapCache<String, NodePack> getNodePackRedisCache(RedisService redisService) {
		return new NodePackRedisCache(redisService);
	}
	/**
	 * 获取参考点的缓存对象
	 * @param redisService
	 * @return
	 */
	@Bean
	public IRedisMapCache<String, ReferPack> getReferPackRedisCache(RedisService redisService) {
		return new ReferPackRedisCache(redisService);
	}
}
