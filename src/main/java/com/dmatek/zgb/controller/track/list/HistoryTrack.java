package com.dmatek.zgb.controller.track.list;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.dmatek.zgb.controller.tool.page.PageTool;
import com.dmatek.zgb.controller.track.TrackTool;
import com.dmatek.zgb.controller.track.TrackValidationTool;
import com.dmatek.zgb.db.person.service.PersonService;
import com.dmatek.zgb.db.pojo.person.Person;
import com.dmatek.zgb.log.anno.Operation;
import com.dmatek.zgb.monitor.bean.TrackPacket;
import com.dmatek.zgb.monitor.device.detail.TagDetail;
import com.dmatek.zgb.rawextract.ZGBExtractHistoryData;
import com.dmatek.zgb.result.factory.base.BaseResultFactory;
import com.dmatek.zgb.setting.vo.Result;
import com.dmatek.zgb.show.vo.SearchTrack;

@RestController
@RequestMapping("/track")
public class HistoryTrack {
	@Autowired
	private PersonService personService;
	@Autowired
	private TrackValidationTool trackValidationTool;
	@Autowired
	private PageTool pageTool;
	@Autowired
	private ZGBExtractHistoryData zgbExtractHistoryData;
	@Autowired
	private BaseResultFactory<Result> viewResultFactory;
	@Autowired
	private TrackTool trackTool;
	/**
	 * 获取卡片讯息
	 * @param taginfor
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/tag/{tagInfor}")
	public Result getTagInfor(@PathVariable String tagInfor) throws Exception {
		// 根据搜索条件查询
		Person current = trackTool.getSearchPerson(tagInfor);
		return viewResultFactory.successResult(current);
	}
	/**
	 * 获取轨迹数据
	 * @param searchTrack
	 * @return
	 * @throws Exception 
	 */
	@PostMapping("/data")
	public Result getTrackData(@RequestBody SearchTrack searchTrack) throws Exception {
		Result result = trackValidationTool.validateSearchCondition(searchTrack);
		if(null != result) {
			return result;
		}
		// 根据搜索条件查询
		Person current = trackTool.getSearchPerson(searchTrack.getTagInfo());
		// 获取卡片的ID
		String tagId = (null == current) ? searchTrack.getTagInfo() : current
				.getTagId();
		// 获取指定时间段的讯息
		List<TrackPacket> tagTracks = zgbExtractHistoryData.ExtractOrigin(
				tagId, searchTrack.getStart(), searchTrack.getEnd());
		// 重新获取卡片的详细讯息
		List<TagDetail> detailPages = trackTool.getAllTagDetails(tagTracks);
		return viewResultFactory.successResult(detailPages);
	}
	/**
	 * 获取轨迹列表
	 * @param searchTrack
	 * @return
	 * @throws Exception 
	 */
	@PostMapping("/list")
	@Operation(description="獲取軌跡數據")
	public Result getTrackList(
			@RequestParam("page") int page,
			@RequestParam("limit") int limit,
			@RequestBody SearchTrack searchTrack) throws Exception {
		Result result = trackValidationTool.validateSearchCondition(searchTrack);
		if(null != result) {
			return result;
		}
		// 根据搜索条件查询
		Person current = trackTool.getSearchPerson(searchTrack.getTagInfo());
		// 获取卡片的ID
		String tagId = (null == current) ? searchTrack.getTagInfo() : current
				.getTagId();
		// 获取指定时间段的讯息
		List<TrackPacket> tagTracks = zgbExtractHistoryData.ExtractOrigin(tagId, 
				searchTrack.getStart(), searchTrack.getEnd());
		// 重新获取卡片的详细讯息
		List<TrackPacket> pages = null;
		List<TagDetail> detailPages = null;
		int pageNumber = 0;
		if(null != tagTracks) {
			pageNumber = pageTool.getPages(tagTracks, limit);
			pages = pageTool.operate(tagTracks, page, limit);
			detailPages = trackTool.getAllTagDetails(pages);
		}
		return viewResultFactory.successPagesResult(detailPages, pageNumber);
	}
}
