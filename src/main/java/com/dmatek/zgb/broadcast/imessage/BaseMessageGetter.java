package com.dmatek.zgb.broadcast.imessage;
import com.dmatek.zgb.broadcast.vo.BroadCastVo;

public abstract class BaseMessageGetter implements IMessageGetter{
	public abstract BroadCastVo getBroadCastVo() throws Exception;
	@Override
	public String getBroadCastMessage() throws Exception {
		return getBroadCastVo().transformJson();
	}
}
