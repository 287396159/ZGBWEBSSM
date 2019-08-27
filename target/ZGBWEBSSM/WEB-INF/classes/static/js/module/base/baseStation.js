/*===============
 * 基站管理
 * ==============*/
$(function(){
	layui.use(['element','table','layer','form'],function(){
		var element = layui.element;
		var table = layui.table;
		var layer = layui.layer;
		var form = layui.form;
		// 請求組訊息，並渲染
		baseDataObj.groups(layer, drawingDataObj.groupsCallBack);
		// 渲染區域訊息，渲染
		baseDataObj.regions(layer, drawingDataObj.regionsCallBack);
		bindingEventObj.groupSwitch();
		
		$("#baseStationClassify li").each(function(index) {
			bindingEventObj.regionSwitch(index, layer, form, drawingDataObj.nodesCallBack);
		})
		// 第壹次默認選擇第壹組的第壹個區域
		$("ul#baseStationClassify>li:first a").click();
		$("ul#baseStationClassify>li:first>dl>dd:first").click();
		//監聽地圖/列表模式切換
		element.on('tab(baseStation)',function(obj) {
			if(obj.index == 0) {// 參考點設置參數
				$("#baseStationClassify dd.selected[regionid]").click();
			} else if(obj.index == 2) {// 基站設置參數
				baseDataObj.allnodes(layer, drawingDataObj.nodesTree);
				baseDataObj.allrefers(layer, drawingDataObj.refersTree);
				var $parentNode = $("#checkboxTree p.parentNode");
				for (var i = 0; i < $parentNode.length; i++) {
					$parentNode.eq(i).find("span:eq(0)").off("click").click(stretch);
				}
			}
		})
		$("#refreshNodeTreeBtn").click(function(){
			baseDataObj.allnodes(layer, drawingDataObj.nodesTree);
			baseDataObj.allrefers(layer, drawingDataObj.refersTree);
			var $parentNode = $("#checkboxTree p.parentNode");
			for (var i = 0; i < $parentNode.length; i++) {
				$parentNode.eq(i).find("span:eq(0)").off("click").click(stretch);
			}
		})
		function stretch(){ //子節點顯示/隱藏
			$(this).parent().siblings(".childNodes").stop().slideToggle("fast");
			if($(this).hasClass("stretch")) {
				$(this).removeClass("stretch").text("+");
			} else {
				$(this).addClass("stretch").text("-");
			}
		}
		var refreshTreeTime = $("#refreshNodeTreeBtn").attr("refreshTreeTime");
		var isAutoRefreshTreeTime = $("#refreshNodeTreeBtn").attr("isAutoRefresh");
		var refreshTimerIndex = null;
		if(refreshTimerIndex) {
			clearInterval(refreshTimerIndex);
		}
		if(isAutoRefreshTreeTime == 'true') {
			var ms =  parseInt(refreshTreeTime) * 1000;
			refreshTimerIndex = setInterval(function() {
				baseDataObj.allnodes(layer, drawingDataObj.nodesTree);
			    baseDataObj.allrefers(layer, drawingDataObj.refersTree);
			    var $parentNode = $("#checkboxTree p.parentNode");
				for (var i = 0; i < $parentNode.length; i++) {
					$parentNode.eq(i).find("span:eq(0)").off("click").click(stretch);
				}
			}, ms);
		}
	})
	/**
	 * 綁定事件對象
	 */
	var bindingEventObj = {
		groupSwitch: function() {// 組別切換函數
			$("#baseStationClassify li a").off("click").click(function(){
				if($(this).find('i').hasClass('icon-zhankai')){
					$(this).removeClass('zhankai');
					$(this).find('i').removeClass('icon-zhankai').addClass('icon-shoulong');
					$(this).siblings('dl').hide();
				} else {
					$("#baseStationClassify li a i").removeClass('icon-zhankai').addClass('icon-shoulong');
					$("#baseStationClassify li a").removeClass('zhankai').siblings('dl').hide();
					$(this).addClass('zhankai');
					$(this).find('i').removeClass('icon-shoulong').addClass('icon-zhankai');
					$(this).siblings('dl').show();
				}
			});
		},
		regionSwitch: function(index, layer, form ,callBack) {
			$("#baseStationClassify li").eq(index).find("dl dd").off("click").click(function(){
				$("#baseStationClassify li dl dd").removeClass('selected');
				$("#baseStationClassify li").eq(index).find("dl dd").eq($(this).index()).addClass('selected');
				var $regionDom = $("#baseStationClassify li").eq(index).find("dl dd").eq($(this).index());
				var regionId = $(this).attr("regionId");
				$("#baseStationContent tbody").empty();
				if(regionId == '-1') {
					$("#baseStationContent").find("ul>li:first").hide();
					$("#baseStationContent").find("ul>li:eq(1)").click();
					$("#baseStationContent").attr({"regionId":$regionDom.attr("regionId"),
						  "groupId":$regionDom.attr("groupId")});
					monitorTimer(regionId, layer, form, 1000)();
					return;
				} else {
					$("#baseStationContent").find("ul>li:first").show();
				}
				$("#place").text($(this).parents("dl").siblings("a").text() + " / " + $regionDom.text());
				// 切換區域地圖
				var absPath = "image/" + $regionDom.attr("image");
				$("#baseStationMap>img")[0].onload = function(){
					// 地圖加載完成
				}
				$("#baseStationMap>img").attr("src" , absPath);
				$("#baseStationContent").attr({"regionId":$regionDom.attr("regionId"),
								  "groupId":$regionDom.attr("groupId")});
				// 清除基站和參考點訊息
				$("#baseStationContent table.layui-table tr[nodeid]").remove();
				$("#baseStationContent table.layui-table tr[referid]").remove();
				// 切換區域時，加載基站訊息
				var regionId = $regionDom.attr("regionId");
				monitorTimer(regionId, layer, form, 1000)();
				$.ajax({type: "GET",url: "enode/region/" + regionId, dataType: "JSON", 
					success: function(result){
						if(result.code == 600){// 獲取區域的節點訊息成功
							callBack(result.data, layer);
						} else {// 獲取區域的節點訊息失敗
							layer.msg(result.msg, {icon: 5});
						}
					},
					error: function(result){
						console.log("獲取區域【id: " + regionId
								+ "】" + "的所有基站訊息出現異常！");
					}
				})
			})
		}
	}
	/**
	 * 渲染數據對象
	 */
	var drawingDataObj = {
		groupsCallBack: function(groups) {
			$("#baseStationClassify").empty();
			var options = [];
			for (var i = 0; i < groups.length; i++) {
				// 獲取當前組別的所有區域
				options.push("<li groupId='" + groups[i]["id"] + "'><a class=''>" + (groups[i]["name"] || groups[i]["id"])  + "<i class='iconfont icon-shoulong'></i></a><dl style='display: none;'></dl></li>");
			}
			options.push("<li groupId='-1'><a class=''>其他組別<i class='iconfont icon-shoulong'></i></a><dl style='display: none;'></dl></li>");
			$("#baseStationClassify").append(options.join(""));
		},
		regionsCallBack: function(regions) { // 渲染區域
			for (var i = 0; i < regions.length; i++) {
				var $regDlDom = $("#baseStationClassify>li[groupid='" + regions[i]["groupId"] + "']").find("dl");
				// 將當前的區域添加到dl中
				$regDlDom.append("<dd regionId='" + regions[i]["id"] + "' image='" + regions[i]["image"] + "' groupId='" + regions[i]["groupId"] + "'>" + (regions[i]["name"] || regions[i]["id"]) + "</dd>");
			}
			var $regDlDom = $("#baseStationClassify>li[groupid='-1']").find("dl");
			$regDlDom.append("<dd regionId='-1' groupId='-1'>其他區域</dd>");
		},
		nodesCallBack: function(nodes) {
			$("#baseStationMap div.pRefer").remove();
			var mapWidth = $("#baseStationMap").width();
			var mapHeight = $("#baseStationMap").height();
			for (var i = 0; i < nodes.length; i++) {
				// 3. 計算基站在當前區域中的坐標
				var nodeLeft = mapWidth * nodes[i]["mapX"]/nodes[i][ "width"];
				var nodeTop  = mapHeight * nodes[i]["mapY"]/nodes[i]["height"];
				// 4. 將基站添加到地圖的容器中
				var typeString = "參考點";
				if(nodes[i]["type"] == 1) {
					typeString = "基站";
				} else if(nodes[i]["type"] == 2) {
					typeString = "進出";
				}
				var $pNode = $("<div id='PNode" + nodes[i]["id"] + "' class='pRefer' pname='" + 
							nodes[i]["name"] + "' ptype='" + nodes[i]["type"] + "'><span class='layui-badge'>" + typeString + "</span><span>" + 
							nodes[i]["name"]  + " </span></div>").appendTo("#baseStationMap");
				// 5. 設置基站在地圖中的位置left - 19 ， top - 8
				var offSetLeft = 19 * mapWidth/nodes[i][ "width"];
				var offSetTop = 8 * mapHeight/nodes[i]["height"];
				$pNode.css({"top": (nodeTop - offSetTop)+"px","left": (nodeLeft - offSetLeft)+"px"});
				$pNode.off("click").click(function() {
					// 判斷當前是否處在調試狀態
					var isDebug = $("#baseStationContent")
						.attr("isDebug")=="true"?true:false;
					if(!isDebug) {
						layer.alert("對不起, 只有Debuger賬戶才能設置設備參數...",
									{icon : 7});
						return;
					}
					// 獲取id
					var id = $(this).attr("id").replace("PNode", "");
					var name = $(this).attr("pname");
					//數據節點設置窗口
					if($(this).attr("ptype") == 1) {// 基站
						// 1. 開始點擊時，默認是設置自己，其它都隱藏掉
						$("#nodeSetting>div.nodeSettingRight>div").hide();
						$("#currentNodeSetting").show();
						$("#currentNodeSetting i.hint").text("");
						$("#currentNodeSetting input").val("");
						// 2. 設置標題內容
						$("#nodeSetting").attr("nodeId", id);
						$("#nodeSetting>div.nodeSettingLeft>p.device:eq(0)>span:last").text((name || id));
						$("#nodeSetting>div.nodeSettingLeft>p.device:eq(1)>span:last").text("");
						$("#nodeSetting>div.nodeSettingLeft>p.device:eq(2)>span:last").text("");
						
						$("#settingSelfNode").removeClass("disableSetting");
						$("#searchDevices").removeClass("disableSetting").addClass("disableSetting");
						
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
					} else {
						var isDebug = $("#baseStationContent")
						.attr("isDebug")=="true"?true:false;
						if(!isDebug) {
							layer.alert("對不起, 只有Debuger賬戶才能設置設備參數...",
									{icon : 7});
							return;
						}
						$("#referSetting").attr("referId", id);
						$("#referSetting>p:first>span:last").text(id);
						$("#referSetting input").val("");
						$("#referSetting i.hint").text("");
						$("#referSetting>p.device:eq(1) span:last").text("");
						$("#referSetting>p.device:eq(2) span:last").text("");
						layer.open({
							type: 1,
							maxmin: true,
							content: $("#referSetting"),
							title: "參考點設置",
							area: ["580px","100%"]
						})
						// 讀取自身訊息
						devices.self.setting.self.read(id, "ReadReferTypeVersionUdpPack",
								devices.node.drawing.refer.typeVersion);
						// 綁定參考點的相關事件
						devices.node.drawing.refer.initEvent();
					}
				})
			}
		},
		nodesTree: function(nodes) {
			var $allNode = $("#checkboxTree li[nodeId]");
			for (var i = 0; i < $allNode.length; i++) {
				var nodeId = $allNode.eq(i).attr("nodeId");
				var isMark = false;
				for (var i = 0; i < nodes.length; i++) {
					if(nodeId == nodes[i]["id"]){// 說明兩個基站的ID相同
						isMark = true;
						break;
					}
				}
				if(!isMark){// 刪除不能存在的基站
					$allNode.eq(i).remove();
				}
			}
			var attr = [];
			for (var i = 0; i < nodes.length; i++) {
				// 獲取
				var $dom = $("#checkboxTree li[nodeId='" + nodes[i]["id"] + "']");
				if($dom.length > 0) { // 存在時，我們需要設置
					$dom.find("i[endpoint]").text("(" + nodes[i]["endpoint"] + ")");
				} else {// 說明此時不能存在
					attr.push("<li nodeId='" + nodes[i]["id"] + "'><p class='parentNode'><span class='stretch'>-</span>" +
							  "<i class='iconfont icon-jizhan1'></i><span>" + 
							   (nodes[i]["referName"] || nodes[i]["id"]) + "</span>" +
							  "<i endpoint>(" + nodes[i]["endpoint"] + ")</i></p>" +
							  "<ul class='childNodes'></ul></li>");
				}
			}
			$("#checkboxTree").append(attr.join(""));
		},
		refersTree: function(refers){
			var $allRefer = $("#checkboxTree li[referId]");
			for (var i = 0; i < $allRefer.length; i++) {
				var referId = $allRefer.eq(i).attr("referId");
				var isMark = false;
				for (var i = 0; i < refers.length; i++) {
					if(referId == refers[i]["id"]) {// 說明兩個基站的ID相同
						isMark = true;
						break;
					}
				}
				if(!isMark) {// 刪除不能存在的基站
					$allRefer.eq(i).remove();
				}
			}
			var attr = [];
			for (var i = 0; i < refers.length; i++) {
				var $dom = $("#checkboxTree").find("li[nodeId='" + refers[i]["bId"] + "']");
				if($dom.length > 0) {
					// 前面壹個設置圖片
					// 查找referId
					var $referDom = $("#checkboxTree").find("li[referId='" + refers[i]["id"] + "']");
					if($referDom.length <= 0) {
						$dom.find("ul.childNodes").find("li.childNode:last img").attr("src", "img/join.gif");
						$dom.find("ul.childNodes").append("<li referId='" + refers[i]["id"] + "' class='childNode'>" +
								  "<img src='img/joinLast.gif'><i class='iconfont icon-cankaodian'></i>" +
								  "<span>" + (refers[i]["referName"] || refers[i]["id"]) + "</span></li>");
					}
				}
			}
		}
	}
})