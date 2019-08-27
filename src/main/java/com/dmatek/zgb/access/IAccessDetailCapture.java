package com.dmatek.zgb.access;

import com.dmatek.zgb.access.bean.FillAccessRecord;
import com.dmatek.zgb.access.bean.TagAccessRecord;

public interface IAccessDetailCapture {
	TagAccessRecord obtainAccessRecord(FillAccessRecord fillAccessRecord) throws Exception;
}
