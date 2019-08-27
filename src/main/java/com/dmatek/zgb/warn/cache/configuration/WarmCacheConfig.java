package com.dmatek.zgb.warn.cache.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dmatek.zgb.comparator.DescendingWarmComparator;
import com.dmatek.zgb.db.warn.service.AbnormalBaseService;
import com.dmatek.zgb.db.warn.service.AbnormalReferService;
import com.dmatek.zgb.db.warn.service.AbnormalTagService;
import com.dmatek.zgb.db.warn.service.AreaControllerService;
import com.dmatek.zgb.db.warn.service.LowPowerService;
import com.dmatek.zgb.db.warn.service.NotMoveService;
import com.dmatek.zgb.db.warn.service.PersonHelpService;
import com.dmatek.zgb.redis.service.RedisService;
import com.dmatek.zgb.warn.bean.AbnormalBaseWarn;
import com.dmatek.zgb.warn.bean.AbnormalReferWarn;
import com.dmatek.zgb.warn.bean.AbnormalTagWarn;
import com.dmatek.zgb.warn.bean.AreaControlWarn;
import com.dmatek.zgb.warn.bean.LowPowerWarn;
import com.dmatek.zgb.warn.bean.NotMoveWarn;
import com.dmatek.zgb.warn.bean.PersonnelHelpWarn;
import com.dmatek.zgb.warn.cache.AbnormalBaseWarnCache;
import com.dmatek.zgb.warn.cache.AbnormalReferWarnCache;
import com.dmatek.zgb.warn.cache.AbnormalTagWarnCache;
import com.dmatek.zgb.warn.cache.AreaControlWarnCache;
import com.dmatek.zgb.warn.cache.LowPowerWarnCache;
import com.dmatek.zgb.warn.cache.NotMoveWarnCache;
import com.dmatek.zgb.warn.cache.PersonnelHelpWarnCache;
import com.dmatek.zgb.warn.cache.base.BaseWarnCache;

@Configuration
public class WarmCacheConfig {
	/**
	 * 生成人员求救缓存
	 * @return
	 */
	@Bean
	public BaseWarnCache<PersonnelHelpWarn> getPersonnelHelpCache(RedisService redisService, 
			DescendingWarmComparator descendingWarmComparator,
			PersonHelpService personHelpService){
		return new PersonnelHelpWarnCache(redisService, descendingWarmComparator, personHelpService);
	}
	/**
	 * 区域管制缓存
	 * @return
	 */
	@Bean
	public BaseWarnCache<AreaControlWarn> getAreaControlCache(RedisService redisService, 
			DescendingWarmComparator descendingWarmComparator,
			AreaControllerService areaControllerService){
		return new AreaControlWarnCache(redisService,descendingWarmComparator,areaControllerService);
	}
	/**
	 * 卡片未移动缓存
	 * @return
	 */
	@Bean
	public BaseWarnCache<NotMoveWarn> getNotMoveCache(RedisService redisService,
			DescendingWarmComparator descendingWarmComparator,
			NotMoveService notMoveService){
		return new NotMoveWarnCache(redisService, descendingWarmComparator, notMoveService);
	}
	/**
	 * 低电量缓存
	 * @return
	 */
	@Bean
	public BaseWarnCache<LowPowerWarn> getLowPowerCache(RedisService redisService, 
			DescendingWarmComparator descendingWarmComparator,
			LowPowerService lowPowerService){
		return new LowPowerWarnCache(redisService, descendingWarmComparator, lowPowerService);
	}
	/**
	 *  卡片异常缓存
	 * @return
	 */
	@Bean
	public BaseWarnCache<AbnormalTagWarn> getAbnormalTagCache(RedisService redisService,
			DescendingWarmComparator descendingWarmComparator,
			AbnormalTagService abnormalTagService){
		return new AbnormalTagWarnCache(redisService, descendingWarmComparator, abnormalTagService);
	}
	/**
	 * 基站异常缓存
	 * @return
	 */
	@Bean
	public BaseWarnCache<AbnormalBaseWarn> getAbnormalBaseCache(RedisService redisService,
			DescendingWarmComparator descendingWarmComparator,
			AbnormalBaseService abnormalBaseService){
		return new AbnormalBaseWarnCache(redisService,descendingWarmComparator,abnormalBaseService);
	}
	/**
	 * 参考点异常缓存
	 * @return
	 */
	@Bean
	public BaseWarnCache<AbnormalReferWarn> getAbnormalReferCache(RedisService redisService, 
			DescendingWarmComparator descendingWarmComparator,
			AbnormalReferService abnormalReferService){
		return new AbnormalReferWarnCache(redisService, descendingWarmComparator, 
				abnormalReferService);
	}
}
