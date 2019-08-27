var monitorTimer = function(regionId, layer, form, time_ms){
	var timer = null;
	var drawingReferTable = function(data) {
		var $table = $("#baseStationContent table.layui-table");
		$table.find("tr[referId]").attr("update", false);
		for (var i = 0; i < data.length; i++) {
			// 1. 判斷當前的基站上報的訊息是否已經存在
			var $tr = $table.find("tr[referId='" + data[i]["id"] + "']");
			if($tr.length > 0) { // 當前基站已經上報到表格上
				$tr.attr("update", true);
				$tr.find("td[regionName]").text(data[i]["regionName"]);
				$tr.find("td[endpoint]").text(data[i]["endpoint"]);
				$tr.find("td[sleepTime]").text(data[i]["sleepTime"] + " s");
				$tr.find("td[reportTime]").text(data[i]["reportTime"]);
				$tr.find("td[version]").text(data[i]["textVersion"]);
			} else { // 當前基站沒有上報到表格上
				$table.append("<tr referId='" + data[i]["id"]  + "'><td nodeId>" + data[i]["id"] + "</td>" +
						      "<td referName='" + data[i]["referName"] + "'>" + data[i]["referName"] + "</td><td referType>參考點</td><td regionName>" + data[i]["regionName"] + "</td>" +
						      "<td endpoint>" + data[i]["endpoint"] + "</td><td sleepTime>" + data[i]["sleepTime"] + " s</td>" +
						      "<td reportTime>" + data[i]["reportTime"] + "</td><td version>" + data[i]["textVersion"] + "</td></tr>");
				// 說明當前是參考點
				$("#baseStationContent table tr[referId='"
				+ data[i]["id"] + "']").off("click").click(function() {// 點擊行打開窗體
					var isDebug = $("#baseStationContent").attr("isDebug")=="true"?true:false;
					if(!isDebug) {
						layer.alert("對不起, 只有Debuger賬戶才能設置設備參數...",
								{icon : 7});
						return;
					}
					var referName = $(this).find("td[referName]").attr("referName");
					var referId = $(this).attr("referId");
					// 設置當前節點設置屬性-nodeId-
					$("#referSetting").attr("referId", referId);
					$("#referSetting>p.device:eq(0) span:last").text((referName || referId));
					// 綁定相關單擊事件
					devices.node.drawing.refer.initEvent();
					layer.open({
						type : 1,
						maxmin : true,
						content : $("#referSetting"),
						title : "參考點設置",
						area : [ "580px","100%"]
					})
					$("#referSetting input").val("");
					$("#referSetting i.hint").text("");
					$("#referSetting>p.device:eq(1) span:last").text("");
					$("#referSetting>p.device:eq(2) span:last").text("");
					// 讀取自身訊息
					devices.self.setting.self.read(
							referId, "ReadReferTypeVersionUdpPack", devices.node.drawing.refer.typeVersion);
				})
			}
		}
		$table.find("tr[referId][update='false']").remove();
	}
	var drawingMap = function(data) {
		var isShowReportTime = $("#baseStationContent").attr("isnodemanagershowreport");
		for (var i = 0; i < data.length; i++) {
			$node = $("#baseStationMap>div#PNode" + data[i]["id"]);
			if($node.length > 0){
				if(isShowReportTime == 'false') {
					$node.find("span:last").css("color","#e91e63");
					$node.find("span:first").css("background", "#e91e63");
				}else{
					$node.find("span:last").css("color","#e91e63");
					$node.find("span:first").css("background", "#e91e63");
				}
			}
		}
	}	
	var drawingNodeTable = function(data) {
		var $table = $("#baseStationContent table.layui-table");
		$table.find("tr[nodeId]").attr("update", false);
		for (var i = 0; i < data.length; i++) {
			// 1. 判斷當前的基站上報的訊息是否已經存在
			var $tr = $table.find("tr[nodeId='" + data[i]["id"] + "']");
			if($tr.length > 0){// 當前基站已經上報到表格上
				$tr.attr("update", true);
				$tr.find("td[regionName]").text(data[i]["regionName"]);
				$tr.find("td[endpoint]").text(data[i]["endpoint"]);
				$tr.find("td[sleepTime]").text(data[i]["sleepTime"] + " s");
				$tr.find("td[reportTime]").text(data[i]["reportTime"]);
				$tr.find("td[version]").text(data[i]["textVersion"]);
			} else {// 當前基站沒有上報到表格上
				$table.append("<tr nodeId='" + data[i]["id"]  + "'><td nodeId>" + data[i]["id"] + "</td>" +
						      "<td referName='" + data[i]["referName"] + "'>" + data[i]["referName"] + "</td><td referType>數據節點</td><td regionName>" + data[i]["regionName"] + "</td>" +
						      "<td endpoint>" + data[i]["endpoint"] + "</td><td sleepTime>" + data[i]["sleepTime"] + " s</td>" +
						      "<td reportTime>" + data[i]["reportTime"] + "</td><td version>" + data[i]["textVersion"] + "</td></tr>");
				// 給每壹行綁定壹個設置自身的窗體
				$("#baseStationContent tr[nodeId='" + data[i]["id"] + "']")
					.off("click").click(function() {
						var isDebug = $("#baseStationContent").attr("isDebug")=="true"?true:false;
						if(!isDebug) {
							layer.alert("對不起, 只有Debuger賬戶才能設置設備參數...",
									{icon : 7});
							return;
						}
						var id = $(this).attr("nodeId");
						var name = $(this).attr("referName");
						// 1. 開始點擊時，默認是設置自己，其它都隱藏掉
						$("#nodeSetting>div.nodeSettingRight>div").hide();
						$("#currentNodeSetting").show();
						$("#settingSelfNode").removeClass("disableSetting");
						$("#searchDevices").removeClass("disableSetting").addClass("disableSetting");
						// 2. 設置標題內容
						$("#nodeSetting").attr("nodeId", id);
						$("#nodeSetting>div.nodeSettingLeft>p.device:eq(0)>span:last").text((name || id));
						$("#nodeSetting>div.nodeSettingLeft>p.device:eq(1)>span:last").text("");
						$("#nodeSetting>div.nodeSettingLeft>p.device:eq(2)>span:last").text("");
						// 3. 清空表格內容
						$("#searchDevicesTable>tbody").empty();
						// 4. 顯示彈窗
						layer.open({
							type: 1,
							maxmin: true,
							content: $("#nodeSetting"),
							title: "數據節點設置",
							area: ["920px","100%"]
						})
						// 5. 請求獲取當前的基站版本及類型訊息
						devices.self.setting.self.read(id, "ReadDeviceTypeVersionUdpPack",
							devices.node.drawing.arround.typeVersion);
						// 6. 綁定搜索按鈕
						devices.node.drawing.arround.initEvent();
				})
			}
		}
		$table.find("tr[nodeId][update='false']").remove();
	}
	var requestRefers = function(regionId){
		$.ajax({type : "GET", url : "refer/data/region/" + regionId, dataType : "JSON",
			success : function(result) {
				if(result.code == 600) {
					if($("#baseStationContent ul.layui-tab-title>li.layui-this").index() == 0){
						drawingMap(result.data);
					} else {
						drawingReferTable(result.data);
					}
				} else {
					layer.msg(result.msg, {icon: 5});
				}
			},
			error : function(result) {
				console.log("獲取區域【id: " + regionId + "】的數據節點和參考點訊息出現異常");
			}
		})
	}
	var requestNodes = function(regionId){
		$.ajax({type : "GET", url : "nodemsg/region/" + regionId, dataType : "JSON",
			success : function(result) {
				if(result.code == 600){
					if($("#baseStationContent ul.layui-tab-title>li.layui-this").index() == 0){
						drawingMap(result.data);
					} else {
						drawingNodeTable(result.data);
					}
				} else {
					layer.msg(result.msg, {icon: 5});
				}
			},
			error : function(result) {
				console.log("獲取區域【id: " + regionId + "】的數據節點和參考點訊息出現異常");
			}
		})
	}
	return function(){
		timer = setInterval(function() {
			// 判斷區域是否發生改變
			var curRegionId = $("#baseStationContent").attr("regionid");
			if(regionId != curRegionId) {// 停止定時器的執行
				clearInterval(timer);
			} else {
				requestNodes(curRegionId);
				requestRefers(curRegionId);
			}
		}, time_ms);
	}
}