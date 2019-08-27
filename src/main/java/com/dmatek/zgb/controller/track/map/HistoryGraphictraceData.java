package com.dmatek.zgb.controller.track.map;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;

import com.dmatek.zgb.controller.tool.page.PageTool;
import com.dmatek.zgb.controller.track.TrackTool;
import com.dmatek.zgb.db.pojo.person.Person;
import com.dmatek.zgb.monitor.bean.TrackPacket;
import com.dmatek.zgb.monitor.device.detail.TagDetail;
import com.dmatek.zgb.result.factory.base.BaseResultFactory;
import com.dmatek.zgb.setting.vo.Result;
/**
 * @author zhangfu
 * @data 2019年4月10日 上午10:50:13
 * @Description
 */
@RestController
@RequestMapping("/graph/track/")
public class HistoryGraphictraceData {
	@Autowired
	private BaseResultFactory<Result> viewResultFactory;
	@Autowired
	private GraphictraceValidationTool validationTool;
	@Autowired
	private TrackTool trackTool;
	@Autowired
	private GraphictraceTool graphictraceTool;
	@Autowired
	private PageTool pageTool;
	/**
	 * 获取地图轨迹数据
	 * @param playType
	 * @param tagId
	 * @param start
	 * @param end
	 * @param limit
	 * @param page
	 * @return
	 * @throws Exception 
	 */
	@GetMapping(value = "/{playType}/{tagId}/{current}")
	public Result graphTracks(@PathVariable int playType,
			@PathVariable String tagId, 
			@PathVariable String current) throws Exception {
		//1. 验证参数是否正确
		Result result = validationTool.validateSearchCondition(playType, tagId, current);
		if(null != result) {
			return result;
		}
		// 获取所有的单点数据
		Date start = validationTool.format(current);
		Date end = new Date(start.getTime() + 5 * 60 * 1000);
		List<TagDetail> allTagDetails = null;
		List<TrackPacket> allTrackPackets = null;
		Person person = null;
		if(playType == GraphictraceTool.SINGLETAG_TYPE) {// 单点搜索
			person = trackTool.getSearchPerson(tagId);
			if (null != person && !StringUtils.isEmpty(person.getTagId())) {
				allTrackPackets = graphictraceTool.getAllSingleTrack(person.getTagId(), start, end);
			}
		} else {// 获取所有的多点记录
			allTrackPackets = graphictraceTool.getAllMultiTrack(start, end);
		}
		if(null != allTrackPackets) {
			allTagDetails = trackTool.getAllTagDetails(allTrackPackets);
		}
		return viewResultFactory.successResult(allTagDetails);
	}
}
