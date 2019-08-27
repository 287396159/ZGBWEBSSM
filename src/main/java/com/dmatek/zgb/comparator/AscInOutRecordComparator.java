package com.dmatek.zgb.comparator;

import java.util.Date;

import com.dmatek.zgb.access.bean.TagAccessRecord;

public class AscInOutRecordComparator extends BaseAccessComparator {
	@Override
	public int compare(TagAccessRecord record1, TagAccessRecord record2) {
		Date time1 = (null != record1 ? record1.getAccessTime() : null);
		Date time2 = (null != record2 ? record2.getAccessTime() : null);
		if (null == time1 && null == time2)
			return 0;
		else if (null == time1)
			return -1;
		else if (null == time2)
			return 1;
		else {
			return (time1.getTime() - time2.getTime() < 0) ? -1 : ((time1
					.getTime() - time2.getTime() > 0) ? 1 : 0);
		}
	}
}
