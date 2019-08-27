package com.dmatek.zgb.comparator;

import java.util.Comparator;
import java.util.Date;

import com.dmatek.zgb.show.vo.AccessView;

public class AccessViewComparator implements Comparator<AccessView> {
	@Override
	public int compare(AccessView o1, AccessView o2) {
		/* 这种方式可能会导致异常：
		 * java.lang.IllegalArgumentException: Comparison method violates its general contract!
		 * */
		Date time1, time2;
		time1 = (null != o1) ? ((null != o1.getOnDutyTime()) ? o1
				.getOnDutyTime() : o1.getOffDutyTime()) : null;
		time2 = (null != o2) ? ((null != o2.getOnDutyTime()) ? o2
				.getOnDutyTime() : o2.getOffDutyTime()) : null;
		if (null == time1 && null == time2) return 0;
		else if (null == time1) return -1;
		else if (null == time2) return 1;
		else {
			return (time1.getTime() - time2.getTime() < 0) ? -1 : ((time1
					.getTime() - time2.getTime() > 0) ? 1 : 0);
		}
	}
}
