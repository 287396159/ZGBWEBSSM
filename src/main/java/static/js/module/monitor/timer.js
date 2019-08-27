var monitorTimer = function(time_ms, layer, singleRate_ms, isShowTagNumber) {
	var drawingTagMap = function(tags) {
		// 遍历地图上的所有卡片，判断卡片是否存在
	   var $allLiDoms = $("#monitorMap li[tagid]");
	   for (var i = 0; i < $allLiDoms.length; i++) {
			// 获取当前的节点ID
			var id = $allLiDoms.eq(i).attr("tagid");
			// 遍历所有的卡片
			var mark = false;
			for (var j = 0; j < tags.length; j++) {
				if(id == tags[j]["id"]) {
					mark = true;
					break;
				}
			}
			if(!mark) { 
				// 说明当前的卡片出现
				$allLiDoms.eq(i).remove();
			}
		}
		// 获取所有的卡片, 查看当前的卡片是否存在
		for (var i = 0; i < tags.length; i++) {
			//1 . 獲取當前tag需要插入的ul容器
			var $ul = $("#monitorMap ul.taglist[referId='" + tags[i]["rId"] + "']");
			if($ul.length > 0) {// 存在
				// 2. 查看這個ul容器裏面是否已經存在當前的卡片標簽
				// 3. 判斷ul容器裏面3個標簽是否已經裝滿	
				var $tagLi = $ul.find("li[tagId='" + tags[i]["id"] + "']");
				if($tagLi.length <= 0) {// 裏面不存在tag標簽
					var $OldTagId = $("#monitorMap").find("li[tagId='" + tags[i]["id"] + "']");
					if($OldTagId.length > 0) {// 但是其他ul中存在
						$OldTagId.remove();
					}
					if($ul.find("li").length < 3) {// 需要向裏面添加tag li標簽
						// 需要我們查看查詢標記
						var searchTagId = $("span.search>button").attr("tagid");
						if(searchTagId) {
							// 進行了搜索
							if(searchTagId == tags[i]["id"]) {
								$tagLi = $("<li tagId='" + tags[i]["id"] + "'><i class='iconfont icon-dingwei'></i>" +  (tags[i]["tagName"] || tags[i]["id"]) + "</li>").appendTo($ul);
							}
						} else {
							$tagLi = $("<li tagId='" + tags[i]["id"] + "'><i class='iconfont icon-dingwei'></i>" +  (tags[i]["tagName"] || tags[i]["id"]) + "</li>").appendTo($ul);
						}
					} else {// 我們不能在添加tag標簽了
						
					}
				}else{// 裏面已經存在tag標簽
					
				}
				// 判斷是否是報警訊息
				if($tagLi.length > 0) {
					if(tags[i]["personnelHelp"]) {// 顯示紅色背景色，字體黑色
						$tagLi.css("color","red");
						$tagLi.find("i").css("color","red");
					} else if(tags[i]["abnormalTag"]) {// 卡片異常
						$tagLi.css("color","rgb(128, 125, 125)");
						$tagLi.find("i").css("color","rgb(128, 125, 125)");
					} else if(tags[i]["areaControl"]) {// 區域管制
						$tagLi.css("color","#673ab7");
						$tagLi.find("i").css("color","#673ab7");
					} else if(tags[i]["notMove"]) {// 未移動報警
						$tagLi.css("color","#03a9f4");
						$tagLi.find("i").css("color","#03a9f4");
					} else if(tags[i]["lowPower"]) {// 低電量報警
						$tagLi.css("color","rgb(90, 33, 193)");
						$tagLi.find("i").css("color","rgb(90, 33, 193)");
					} else {
						$tagLi.css("color","#009688");
						$tagLi.find("i").css("color","#009688");
					}
				}
			}else{// 不存在，舍棄這張卡片
				
			}
		}
	}
	var drawingTagTable = function(tags) {
		for (var i = 0; i < tags.length; i++) {
			// 1. 判斷當前的卡片是否存在
			var $tagRow = $("#monitor tr[tagId='" + tags[i]["id"] + "']");
			if($tagRow.length <= 0) {
			// 2. 卡片不存在
				var searchTagId = $("span.search>button").attr("tagid");
				if(searchTagId) {
					// 進行了搜索
					if(searchTagId == tags[i]["id"]) {
						$tagRow = $(
								"<tr tagId='" + tags[i]["id"] + "'><td tagId>"
										+ tags[i]["id"] + "</td><td tagName>"
										+ tags[i]["tagName"] + "</td>"
										+ "<td company>" + (tags[i]["companyName"] || tags[i]["companyNo"]) + "</td>"
										+ "<td jobType>" + (tags[i]["jobTypeName"] || tags[i]["jobTypeNo"]) + "</td>"
										+ "<td refer>" + (tags[i]["referName"] || tags[i]["rId"]) + "</td>"
										+ "<td rssi>" + tags[i]["rssi"]
										+ " dbm" + "</td>" + "<td sleepTime>"
										+ tags[i]["sleepTime"] + " s" + "</td>"
										+ "<td bat>" + tags[i]["bat"] + " %"
										+ "</td>" + "<td notMoveTime>"
										+ tags[i]["notMoveTime"] + " s"
										+ "</td>" + "<td reportTime>"
										+ tags[i]["reportTime"] + "</td></tr>")
								.appendTo("#monitor tbody");
					}
				} else {
					$tagRow = $(
							"<tr tagId='" + tags[i]["id"] + "'><td tagId>"
									+ tags[i]["id"] + "</td><td tagName>"
									+ tags[i]["tagName"] + "</td>"
									+ "<td company>" + (tags[i]["companyName"] || tags[i]["companyNo"]) + "</td>"
									+ "<td jobType>" + (tags[i]["jobTypeName"] || tags[i]["jobTypeNo"]) + "</td>"
									+ "<td refer>" + (tags[i]["referName"] || tags[i]["rId"]) + "</td>"
									+ "<td rssi>" + tags[i]["rssi"] + " dbm"
									+ "</td>" + "<td sleepTime>"
									+ tags[i]["sleepTime"] + " s" + "</td>"
									+ "<td bat>" + tags[i]["bat"] + " %"
									+ "</td>" + "<td notMoveTime>"
									+ tags[i]["notMoveTime"] + " s" + "</td>"
									+ "<td reportTime>" + tags[i]["reportTime"]
									+ "</td></tr>").appendTo("#monitor tbody");
				}
			} else {
			// 3. 卡片存在時，只需要更新表格中的內容
				$tagRow.find("td[rssi]").text(tags[i]["rssi"] + " dbm");
				$tagRow.find("td[sleepTime]").text(tags[i]["sleepTime"] + " s");
				$tagRow.find("td[bat]").text(tags[i]["bat"] + " %");
				$tagRow.find("td[notMoveTime]").text(
						tags[i]["notMoveTime"] + " s");
				$tagRow.find("td[reportTime]").text(tags[i]["reportTime"]);
			}
			// 4. 設置當前卡片的警報類型
			$tagRow.attr({
				"personnelHelp" : tags[i]["personnelHelp"],
				"lowPower" : tags[i]["lowPower"],
				"notMove" : tags[i]["notMove"],
				"areaControl" : tags[i]["areaControl"],
				"abnormalTag" : tags[i]["abnormalTag"]
			})
			// 5. 根據報警類型顯示不同行的顏色，優先級： 人員求救 > 卡片異常報警 > 區域管制報警 > 未移動報警 > 低電量報警
			if (tags[i]["personnelHelp"]) {// 顯示紅色背景色，字體黑色
				$tagRow.css({
					"background" : "red",
					"color" : "white"
				});
			} else if (tags[i]["abnormalTag"]) {// 卡片異常
				$tagRow.css({
					"background" : "#9E9E9E",
					"color" : "white"
				});
			} else if (tags[i]["areaControl"]) {// 區域管制
				$tagRow.css({
					"background" : "#673ab7",
					"color" : "white"
				});
			} else if (tags[i]["notMove"]) {// 未移動報警
				$tagRow.css({
					"background" : "#03a9f4",
					"color" : "white"
				});
			} else if (tags[i]["lowPower"]) {// 低電量報警
				$tagRow.css({
					"background" : "rgb(90, 33, 193)",
					"color" : "white"
				});
			} else {
				$tagRow.css({
					"background" : "white",
					"color" : "#666"
				});
			}
		}
	}
	var drawingReferNumber = function(refers) {
		for (var i = 0; i < refers.length; i++) {
			var $referDiv = $("#monitorMapDiv div#PNode" + refers[i]["id"]);
			$referDiv.find("span:first").text(refers[i]["total"]);
			if ($("#monitorContent").attr("basevisible") != "true" && 
				refers[i]["total"] <= 0) {
				$referDiv.hide();
			} else {
				$referDiv.show();
			}
		}
	}
	/**
	 * 获取参考点周围卡片的数量
	 */
	var getReferArroundTagNumber = function(regionId, callBack){
		$.ajax({
			type : "GET",
			url : "refer/data/number/" + regionId,
			dataType : "JSON",
			success : function(result) {
				if (result.code == 600) {
					callBack(result.data);
				} else {
					console.log(result.msg);
				}
			},
			error : function(result) {
				console.log("獲取區域【id : " + regionId + "】中的參考點周圍的卡片數量出現異常!");
			}
		})
	}
	/**
	 * 渲染區域卡片
	 */
	var drawRegionTags = function(data) {
		// 更新區域數量
		$("#regionNumberMessage>li:last>p").text(data.length);
		// 更新左側樹狀圖
		drawingTagsTree(data, drawingSingleTag, singleRate_ms)();
		// 刷新 地圖或表格監控
		if ($("#monitorContent ul.layui-tab-title>li:last").hasClass(
				"layui-this")) {
			// 渲染表格模式
			drawingTagTable(data);
		} else {
			// 渲染地圖模式
			drawingTagMap(data);
		}
	}
	/**
	 * 获取所有的区域卡片讯息
	 */
	var getRegionTags = function(regionId, callBack){
		$.ajax({
			type : "GET",
			url : "tagmsg/" + regionId,
			dataType : "JSON",
			success : function(result) {
				if (result.code == 0) {
					callBack(result.data);
				} else {
					console.log(result.msg);
				}
			},
			error : function(result) {
				console.log("獲取區域【regionId: " + regionId + "】的卡片訊息出現異常!");
			}
		})
	}
	function isAllChecked($allCheckbox, $eachCheckbox){
		var allCheckBoxEvent = function() {
			if ($(this).is(":checked")) {
				$eachCheckbox.prop("checked", true);
			} else {
				$eachCheckbox.prop("checked", false);
			}
			$eachCheckbox.change();
		}
		$allCheckbox.off("change").change(allCheckBoxEvent);
	}
	var drawingSingleTag = function(tag) {// 單次渲染卡片
		// 1.獲取已經存在的卡片節點
		var $tagLi = $("#checkboxTree li[tagId='" + tag["id"] + "']");
		// 2.獲取卡片需要插入的工種位置
		var $jobTypeTagUl = $(
				"#checkboxTree li.jobtype[jobtypeid='" + tag["jobTypeNo"]
						+ "']").find("ul.childNodes");
		if ($tagLi.length <= 0) { // 說明當前的卡片沒有存在
			var $tagDom = $("<li class='childNode' abnormalTag='"
					+ tag["abnormalTag"]
					+ "' "
					+ "lowPower='"
					+ tag["lowPower"]
					+ "' areaControl='"
					+ tag["areaControl"]
					+ "' "
					+ "notMove='"
					+ tag["notMove"]
					+ "' personnelHelp='"
					+ tag["personnelHelp"]
					+ "'  "
					+ "tagId='"
					+ tag["id"]
					+ "'><img src='img/joinLast.gif'/><input type='checkbox' />"
					+ "<span>" + (tag["tagName"] || tag["id"]) + "</span></li>");
			// 3. 判斷工種位置是否存在
			if ($jobTypeTagUl.length <= 0) {// 不存在，我們需要插入到其它工種處
				$jobTypeTagUl = $("#checkboxTree li.jobtype[jobtypeid='-1']")
						.find("ul.childNodes");
			}
			var $lastTag = $jobTypeTagUl.find("li.childNode:last");
			if ($lastTag) {// 把樹狀圖最後壹個節點圖標做 修改
				$lastTag.find("img").attr("src", "img/join.gif");
			}
			// 4. 將卡片添加到樹狀圖的節點上
			$tagDom.appendTo($jobTypeTagUl);
			// 5. 獲取當前上報的卡片數量
			var current = $jobTypeTagUl.find("li.childNode").length;
			// 6. 獲取當前顯示的卡片數量
			var jobTypesNumAttr = $jobTypeTagUl.siblings("p.parentNode").find(
					"span:last").text().split("/");
			if (jobTypesNumAttr.length == 2) {// 存在工種，
				$jobTypeTagUl.siblings("p.parentNode").find("span:last").html(
						current + "<strong> / </strong>" + jobTypesNumAttr[1])
			} else {
				$jobTypeTagUl.siblings("p.parentNode").find("span:last").html(
						current);
			}
			// 7. 調用綁定checked
			isAllChecked($jobTypeTagUl.siblings("p.parentNode").find(
					"input[type='checkbox']"), $jobTypeTagUl
					.find("li.childNode input[type='checkbox']"));
			// 8. 判斷是否展開，當沒有展開，觸發click
			if (!$jobTypeTagUl.siblings("p.parentNode").find("span:first")
					.hasClass("stretch")) {
				$jobTypeTagUl.siblings("p.parentNode").find("span:first")
						.click();
			}
			$tagDom.find("input[type='checkbox']").prop("checked", true)
					.change(function() {
								var tagId = $(this).parents("li[tagId]").attr(
										"tagId");
								if ($(this).prop("checked")) {// 選中
									$(
											"#monitorMap ul.taglist>li[tagId='"
													+ tagId + "']").show();
									$("#monitor tr[tagid='" + tagId + "']")
											.show();
								} else {// 未選中
									$(
											"#monitorMap ul.taglist>li[tagId='"
													+ tagId + "']").hide();
									$("#monitor tr[tagid='" + tagId + "']")
											.hide();
								}
							}).change();
			// 判斷當前是否需要進行搜索，需要進行搜索時
			var searchTagId = $("span.search>button").attr("tagid");
			if (searchTagId && searchTagId != tag["id"]) {
				// 進行了搜索
				$tagDom.find("input[type='checkbox']").prop("checked", false)
						.change();
			}
		} else { // 說明當前的卡片已經存在, 我們應該更新當前的卡片狀態
			$tagLi.attr({
				"abnormalTag" : tag["abnormalTag"],
				"lowPower" : tag["lowPower"],
				"areaControl" : tag["areaControl"],
				"notMove" : tag["notMove"],
				"personnelHelp" : tag["personnelHelp"]
			});
		}
	}
	var drawingTagsTree = function(data, func, timeout) {
		var timerIndex = null;
		var start = function() {
			var obj = data.shift();
			func(obj);
		}
		return function() {
			timerIndex = setInterval(function() {
				if (data.length == 0) {
					clearInterval(timerIndex);
				} else {
					start();
				}
			}, timeout);
		}
	}
	var drawingAllRegionTotalTags = function(total){
		$("#regionNumberMessage>li:first>p").text(total);
	}
	/**
	 * 獲取所有區域總卡片數量
	 */
	var getAllRegionTotalTags = function(callBack) {
		$.ajax({type: "GET", url: "tagmsg/total/", dataType: "JSON", async: false, 
			success: function(result) {
				if(result.code == 600) {
					callBack(result.data);
				}
			},
			error: function(result) {
				console.log("獲取所有卡片的總數量時出現異常...");
			}})
	}
	var obtainAllGRegionNumber = function(callBack) {
		$.ajax({
			type : "GET",
			url : "tagmsg/all/",
			dataType : "JSON",
			async : false,
			success : function(result) {
				if (result.code == 600) {
					callBack(result.data);
				}
			},
			error : function(result) {
				console.log("獲取所有组别/区域卡片數量時出現異常...");
			}
		})
	}
	var drawAllGRegionNumber = function(data){
		var $groupDom, $regionDom;
		for (var i = 0; i < data.length; i++) {
			$groupDom = $("#areaClassify li[groupid='" + data[i]["id"] + "']");
			$groupDom.find("a span:first").text(
					data[i]["name"] + "(" + data[i]["number"] + ")");
			for (var j = 0; j < data[i]["regions"].length; j++) {
				$regionDom = $groupDom
						.find("dd[regionid='" + data[i]["regions"][j]["id"] + "']")
						.text(data[i]["regions"][j]["name"] + "(" + data[i]["regions"][j]["number"] + ")");
			}
		}
	}
	var timerCallBack = function(){
		// 获取当前的区域ID
		var regionId = $("#monitorContent").attr("regionId");
		// 获取参考点周围卡片的数量
		getReferArroundTagNumber(regionId, drawingReferNumber);
		// 获取所有区域的总人数
		getAllRegionTotalTags(drawingAllRegionTotalTags);
		// 获取当前区域的卡片讯息
		getRegionTags(regionId, drawRegionTags);
		// 獲取不同區域及組別的所有卡片數量
		if(isShowTagNumber == 'true') {
			obtainAllGRegionNumber(drawAllGRegionNumber);
		}
	}
	return function(){// 啟動定時器函數
		timerCallBack();
		setInterval(function() {
			timerCallBack();
		}, time_ms)
	}
}