/*============
 * 歷史軌跡
 * ===========*/
$(function(){
	var tagDis1 = $("ul.section").attr("tagdis1");
	var tagDis2 = $("ul.section").attr("tagdis2");
	layui.use(['laydate','form','layer','flow','carousel'],function(){
		var laydate = layui.laydate;
		var form = layui.form;
		var layer = layui.layer;
		var flow = layui.flow;
		var carousel = layui.carousel;
		var s_Start, s_End, tagId, 
			eS_Start, eS_End;
		var regionCache = null;
		$("ul.classifyContent>li").show();
		/*========== 
		 *軌跡查看
		 *=========*/
		/*默認查看當前時間前面5小時的軌跡數據*/
		var defaultStart = new Date();
		defaultStart.setHours(0);
		defaultStart.setMinutes(0);
		defaultStart.setSeconds(0);
		var defaultEnd = new Date();
		defaultEnd.setHours(23);
		defaultEnd.setMinutes(59);
		defaultEnd.setSeconds(59);
		// 列表模式時間
		s_Start = defaultStart.format("yyyy-MM-dd hh:mm:ss");
		s_End = defaultEnd.format("yyyy-MM-dd hh:mm:ss");
		// 地圖模式時間
		eS_Start = defaultStart.format("yyyy-MM-dd hh:mm:ss");
		eS_End = defaultEnd.format("yyyy-MM-dd hh:mm:ss");
		//創建(軌跡查看)日期時間選擇控件
		laydate.render({ //開始時間
			elem: '#startTime', type: 'datetime',
			value: defaultStart, done: function(value){
				s_Start = value;
			}
		})
		laydate.render({ //結束時間
			elem: '#endTime', type: 'datetime',
			value: defaultEnd, done: function(value){
				s_End = value;
			}
		})
		// 地圖模式
		laydate.render({ //開始時間
			elem: '#trackPlayStartTime', type: 'datetime',
			value: defaultStart, done: function(value){
				eS_Start = value;
			}
		})
		laydate.render({ //結束時間
			elem: '#trackPlayEndTime', type: 'datetime',
			value: defaultEnd, done: function(value, date, endDate){
				eS_End = value;
			}
		})
		var groupId, tagType;
		form.on('select(groupFilter)', function(data){
			groupId = data.value;
			// 請求當前組別的所有區域
			trackRequestObj.regions(data.value, trackDrawing.regions);
		});
		form.on('select(tagFilter)', function(data){
			tagType = data.value;
		    if(data.value === '0') {
			    $("#searchTAG").hide();
			} else {
				$("#searchTAG").show();
			}
		});
		/**
		 * 设置轮播
		 */
		var regionCarousel = carousel.render({
		    elem: '#regionSwitch',
		    autoplay: false,
		    width: '100%', //设置容器宽度
		    arrow: 'always' //始终显示箭头
		});
		/**
		 * 切換區域
		 */
		carousel.on('change(regionSwitchFilter)', function(obj){
			// 加载基站讯息
			trackRequestObj.nodes(obj.item.attr("regionId"), 
					trackDrawing.nodes);
			// 加载卡片讯息
			trackDrawing.loadTags(obj.item.attr("regionId"));
		});
		// 列表模式下搜索軌跡記錄
		$("#listMode button").off("click").click(function(){
			if(!$("#TAGNameOrID").val()) {
				layer.msg("卡片訊息不能為空",{icon: 5});
				return;
			}
			// 搜索的對象
			var searchObj = {
				tagInfo:$("#TAGNameOrID").val(),
				start: s_Start,
				end: s_End
			}
			$("#historySheetBox").empty();
			// 查詢Tag訊息
			trackRequestObj.tagInfor($("#TAGNameOrID").val(), trackDrawing.tagInfo);
			// 需要我們使用定時器實現局部加載
			trackDrawing.list(searchObj);
		})
		var player = null;
		$("#playPause").off("click").click(function() {
			var tagId = "FFFF";
			if($(this).find("i").hasClass("icon-bofang")){// 當前是播放
				if(tagType == 1) {// 单张卡片
					tagId = $("#searchTAG input[type='text']").val();
					if(!tagId){
						layer.msg("選擇單張卡片查看時, ID不能為空...",{icon: 5});
						return;
					}
				}
				if(tagType != 1 && tagType != 0) {
					layer.msg("卡片方式必須選擇",{icon: 5});
					return;
				}
				if(!groupId) {
					layer.msg("工地必須選擇",{icon: 5});
					return;
				}
				if(null == player) { // 渲染地图上的卡片
					player = playTrace(eS_Start, eS_End, tagType , tagId, 
							trackDrawing.map, 
							trackDrawing.time,
							trackDrawing.speed);
				}
				if(null != player) {
					var result = player.start();
					if(result.code == 600){// 已經啟動播放
						$(this).find("i").removeClass("icon-bofang").addClass("icon-zanting");
						$("#currentTime").parent("div").show();
						$("#currentSpeed").parent("div").show();
					}
				}
			} else {
				if(null != player) {
					var result = player.pause();
					if(result.code == 600){
						$(this).find("i").removeClass("icon-zanting").addClass("icon-bofang");
					}
				}
			}
		})
		$("#playSlow").off("click").click(function(){
			if(null != player) {
				var result = player.speedDown();
				if(result.code != 600) {
					layer.msg(result.val,{icon: 7});
				}
			}
		})
		$("#playFast").off("click").click(function(){
			if(null != player) {
				var result = player.speedUp();
				if(result.code != 600) {
					layer.msg(result.val,{icon: 7});
				}
			}
		})
		$("#playStop").off("click").click(function(){
			if(null != player) {
				var result = player.stop();
				if(result.code == 600) {
					player = null;
					$("#playPause").find("i")
								   .removeClass("icon-zanting")
								   .addClass("icon-bofang");
					$("#regionSwitch li[tagId]").remove();
					// 清除当前区域的缓存
					for ( var key in regionCache) {
						regionCache[key]={};
					}
					$("#currentTime").parent("div").hide();
					$("#currentSpeed").parent("div").hide();
				}
			}
		})
		$("div.historySheet").scroll(function(){
			var viewH =$(this).height(),//可见高度
	        contentH =$(this).get(0).scrollHeight,//内容高度
	        scrollTop =$(this).scrollTop();//滚动高度
	        if(contentH - viewH - scrollTop <= 10) { //当滚动到距离底部100px时,
		        $("#historySheetBox").find("a").click();
	        }
		})
		// 地图模式/列表模式 切换
		$("#trackViewMethods li").click(function(){
			$("#trackViewMethods>li").removeClass("activeArea").eq($(this).index()).addClass("activeArea");
			$("#historyContent>li").hide().eq($(this).index()).show();
			if ($(this).index() == 1) { 
				// 地图模式
				$("#mapMode").show();
				$("#listMode").hide();
				
			} else if ($(this).index() == 0) { 
				// 列表模式
				$("#mapMode").hide();
				$("#listMode").show();
			}
		})
		/**
		 * 軌跡渲染
		 */
		var trackDrawing = {
			time: function(iCurrent, iEnd){
				$("#currentTime").text(iCurrent);
			},
			speed: function(iSpeed){
				$("#currentSpeed").text(" × " + iSpeed);
			},
			tag: function(cacheTag) {
				//1 . 獲取當前tag需要插入的ul容器
				var $ul = $("ul.taglist[rid='" + cacheTag["rId"] + "']");
				if($ul.length > 0) {
					// 2. 查看這個ul容器裏面是否已經存在當前的卡片標簽
					// 3. 判斷ul容器裏面3個標簽是否已經裝滿	
					var $tagLi = $ul.find("li[tagId='" + cacheTag["id"] + "']");
					if($tagLi.length <= 0) {// 裏面不存在tag標簽
						var $OldTagId = $("#regionSwitch").find("li[tagId='" + cacheTag["id"] + "']");
						if($OldTagId.length > 0) {// 但是其他ul中存在
							$OldTagId.remove();
						}
						if($ul.find("li").length < 3) {// 需要向裏面添加tag li標簽
							$tagLi = $("<li tagId='" + cacheTag["id"] + "'><i class='iconfont icon-dingwei'></i>" +  (cacheTag["tagName"] || cacheTag["id"]) + "</li>").appendTo($ul);
						} else {// 我們不能在添加tag標簽了
							
						}
					} else {
						
					}
					// 判斷是否是報警訊息
					if($tagLi.length > 0) {
						if(cacheTag["alarm"]) {// 顯示紅色背景色，字體黑色
							$tagLi.css("color","red");
							$tagLi.find("i").css("color","red");
						} else if(cacheTag["abnormalTag"]) {// 卡片異常
							$tagLi.css("color","rgb(128, 125, 125)");
							$tagLi.find("i").css("color","rgb(128, 125, 125)");
						} else if(cacheTag["areaControl"]) {// 區域管制
							$tagLi.css("color","#673ab7");
							$tagLi.find("i").css("color","#673ab7");
						} else if(cacheTag["notMove"]) {// 未移動報警
							$tagLi.css("color","#03a9f4");
							$tagLi.find("i").css("color","#03a9f4");
						} else if(cacheTag["lowPower"]) {// 低電量報警
							$tagLi.css("color","rgb(90, 33, 193)");
							$tagLi.find("i").css("color","rgb(90, 33, 193)");
						} else {
							$tagLi.css("color","#009688");
							$tagLi.find("i").css("color","#009688");
						}
					}
				} else {
					
				}
			},
			loadTags: function(regionId){
				var tagCache = regionCache[regionId];
				for ( var tagId in tagCache) {
					// 判断当前的卡片上报的讯息是否超时notMoveTime
					var sleep = tagCache[tagId]["sleepTime"];
					if(tagCache[tagId]["notMoveTime"] >= 3 && tagCache[tagId]["staticSleep"] > tagCache[tagId]["sleepTime"]) {
						sleep = tagCache[tagId]["staticSleep"];
					} else {
						sleep = tagCache[tagId]["sleepTime"];
					}
					var staticSleep = tagCache[tagId]["staticSleep"];
					var report = (new Date(tagCache[tagId]["reportTime"])).getTime();
					var interval = tagDis1 * sleep * 100 + tagDis1 * 1000;
					// 获取当前播放的时间
					var currentTime = $("#currentTime").text().trim();
					if(new Date(currentTime).getTime() - report >= interval + 5 * 60 * 1000) {
						delete tagCache[tagId];
					} else if(new Date(currentTime).getTime() - report >= interval) {
						tagCache[tagId]["abnormalTag"] = true;
					}
				}
				// 遍历地图上所有的卡片节点
				var $allTagDom = $("#regionSwitch li[tagId]");
				for (var i = 0; i < $allTagDom.length; i++) {
					var viewId = $allTagDom.eq(i).attr("tagId");
					var mark = false;
					for ( var tagId in tagCache) {
						if(viewId == tagId){
							mark = true;
							break;
						}
					}
					if(!mark) {
						$allTagDom.eq(i).remove();
					}
				}
				for ( var tagId in tagCache) {
					trackDrawing.tag(tagCache[tagId]);
				}
			},
			map: function(tags) {// 将卡片渲染到地图上面去
				// 判断当前选择的是单点模式还是多点模式
				if (tagType == 1) {// 单点
					// 切换到这个regionId的区域
					var id = (tags.length > 0 ? tags[0]["regionId"]: -1);
					var index = $("#regionSwitch div[carousel-item] div[regionid='" + id + "']").index();
					if (index >= 0) {
						$("#regionSwitch div.layui-carousel-ind ul li").eq(index).click();
					}
				}
				for (var i = 0; i < tags.length; i++) {
					// 不區分區域的，這裡可以存儲區域的緩存
					if(null != regionCache) {// 將卡片訊息保存到緩存裡面
						regionCache[tags[i]["regionId"]][tags[i]["id"]] = tags[i]; 
					}
				}
				// 獲取當前的區域ID
				var regionId = $("#regionSwitch div.layui-this").attr("regionid");
				// 獲取當前區域对应的缓存
				trackDrawing.loadTags(regionId);
			},
			tagInfo: function(data){
				if(data) {
					$("#historyListTitle span:first").text(data["tagId"]);
					$("#historyListTitle span:last").text(data["name"]);
				}
			},
			groups: function(data) {
				$("#selectSearchGroup").empty();
				var array = [];
				array.push("<option value=''>請選擇</option>");
				for (var i = 0; i < data.length; i++) {
					array.push("<option value='" + data[i]["id"] + "'>" +data[i]["name"]  + "</option>")
				}
				$("#selectSearchGroup").append(array.join(""));
				form.render();
			},
			regions: function(data) {
				regionCache = getRegionCache(data);
				$("#regionSwitch>div[carousel-item]").empty();
				$("#regionSwitch>div.layui-carousel-ind ul").empty();
				// 保存一個區域數組
				var array = [],array1 = [];
				for (var i = 0; i < data.length; i++) {
					if (i == 0) {
						 array.push("<div regionId='" + data[i]["id"] + "' regionName='" + data[i]["name"] + "' " +
								    "class='layui-this'><img src='image/" + data[i]["image"] + "'/></div>");
						array1.push("<li class='layui-this'></li>");
					} else {
						 array.push("<div regionId='" + data[i]["id"] + "' regionName='" + data[i]["name"] + "'>" +
								    "<img src='image/" + data[i]["image"] + "'/></div>");
						array1.push("<li></li>");
					}
				}
				$("#regionSwitch>div[carousel-item]").append(array.join(""));
				
				$("#regionSwitch>div.layui-carousel-ind ul").append(array1.join(""));
				regionCarousel.reload();
				var $li = $("#regionSwitch>div.layui-carousel-ind ul").find("li");
				var $region = $("#regionSwitch>div[carousel-item] div[regionId]");
				for (var i = 0; i < $region.length; i++) {
					var regionId = $region.eq(i).attr("regiomId");
					var regionName = $region.eq(i).attr("regionName");
					$li.eq(i).attr("title",(regionName || regionId));
				}
				// 防止地圖後加載
				setTimeout(function() {
					var regionId = $("#regionSwitch>div[carousel-item]")
					   .find("div.layui-this").attr("regionId");
					trackRequestObj.nodes(regionId, trackDrawing.nodes);
				}, 1000);
			},
			nodes: function(data){
				if(data.length > 0) {
					$("#regionSwitch div[carousel-item]").find("div.pNode").remove();
					$("#regionSwitch div[carousel-item]").find("ul.taglist").remove();
					
					var mapWidth = $("#regionSwitch div[carousel-item] div[regionid='" + data[0]["regionId"] + "'] img").width();
					var mapHeight = $("#regionSwitch div[carousel-item] div[regionid='" + data[0]["regionId"] + "'] img").height();
					var array = [];
					for (var i = 0; i < data.length; i++) {
						if(data[i]['type'] == 1){// 如果是基站我們不在頁面上顯示
							continue;
						}
						// 3. 計算基站在當前區域中的坐標
						var nodeLeft = mapWidth *  data[i]["mapX"]/data[i]["width"];
						var nodeTop  = mapHeight * data[i]["mapY"]/data[i]["height"];
						var offSetLeft = 19 * mapWidth/data[i][ "width"];
						var offSetTop = 8 * mapHeight/data[i]["height"];
						if(data[i]['type'] == 2) {
							$("#regionSwitch div[carousel-item] div[regionid='" + data[0]["regionId"] + "']").append(
									"<div class='pNode' rId='" + data[i]["id"] + "' pname='" + data[i]["name"] + "' ptype='" + data[i]["type"] + "' " +
									"style='top: " + (nodeTop - offSetTop) + "px;left: " + (nodeLeft - offSetLeft) + "px;'>" +
									"<span class='layui-badge'>0</span><span>" + (data[i]["name"] || data[i]["id"]) + "</span></div>");
						} else {
							$("#regionSwitch div[carousel-item] div[regionid='" + data[0]["regionId"] + "']").append(
									"<div class='pNode' rId='" + data[i]["id"] + "' pname='" + data[i]["name"] + "' ptype='" + data[i]["type"] + "' " +
									"style='top: " + (nodeTop - offSetTop) + "px;left: " + (nodeLeft - offSetLeft) + "px;'>" +
									"<span class='layui-badge' style='background:#5FB878'>0</span><span>" + (data[i]["name"] || data[i]["id"]) + "</span></div>");
						}
						var nodeWidth = $("#regionSwitch div[rId='" + data[i]["id"] + "']").width();
						$("#regionSwitch div[carousel-item] div[regionid='" + data[0]["regionId"] + "']").append("<ul rId='" + data[i]["id"] + "' tagNumber='0' class='taglist' style='top: " + (nodeTop - offSetTop - 24)
								+ "px;left: " + (nodeLeft - offSetLeft + nodeWidth + 5) + "px'></ul>");
					}
				}
			},
			// 列表渲染
			list: function(searchObj) {
				flow.load({
					elem : '#historySheetBox',
					done : function(page, next) {
						var lis = [];
						// 獲取每頁顯示的數量
						$.ajax({type : "POST", url : "track/list?page=" + page + "&limit=17", 
							dataType : "JSON", contentType : "application/json;charset=utf-8",
							data : JSON.stringify(searchObj),
							success : function(result) {
								layui.each(result.data, function(index, item) {
									lis.push("<tr><td>" + item["reportTime"] + "</td>" +
											 "<td>" + (item["regionName"] || item["regionId"]) + "</td>" +
											 "<td>" + (item["referName"] || item["rId"]) + "</td>" +
											 "<td>" + item["rssi"] + " dbm</td>" +
											 "<td>" + item["sleepTime"] + " s</td>" +
											 "<td>" + item["notMoveTime"] + " s</td>" +
											 "<td>" + item["bat"] + " %</td>" +
											 "<td>" + (item["alarm"]?"√":"×") + "</td>" +
											 "<td>" + (item["notMove"]?"√":"×") + "</td>" +
											 "<td>" + (item["lowPower"]?"√":"×") + "</td>" +
											 "<td>" + (item["areaControl"]?"√":"×") + "</td></tr>");
								});
								// 執行下壹頁渲染，第二參數為：滿足“加載更多”的條件，即後面仍有分頁
								// pages為Ajax返回的總頁數，只有當前頁小於總頁數的情況下，才會繼續出現加載更多
								next(lis.join(''), page < result.pages);
							}
						})
					}
				})
			}
		}
		trackRequestObj.groups(trackDrawing.groups);
		$("#currentTime").parent("div").hide();
		$("#currentSpeed").parent("div").hide();
	})
});
