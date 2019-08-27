package com.dmatek.zgb.comparator;

import java.util.Date;
import com.dmatek.zgb.warn.bean.base.BaseWarnMessage;
/**
 * 升序比較器
 * @author zhangfu
 * @data 2019年4月17日 下午6:12:32
 * @Description
 */
public class AscendingWarmComparator extends BaseWarmComparator{
	@Override
	public int compare(BaseWarnMessage o1, BaseWarnMessage o2) {
		Date time1 = (null != o1 ? o1.getCreateTime() : null);
		Date time2 = (null != o2 ? o2.getCreateTime() : null);
		if (null == time1 && null == time2)
			return 0;
		else if (null == time1)
			return -1;
		else if (null == time2)
			return 1;
		else
			return time1.getTime() - time2.getTime() < 0 ? -1 : (time1
					.getTime() - time2.getTime() > 0 ? 1 : 0);
	}
}
