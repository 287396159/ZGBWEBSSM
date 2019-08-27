$(function(){
	/*=====================
	 * 動態監控
	 * ====================*/
	//區域選擇/TAG管理切換
	layui.use(['element','table','layer','form'],function(){
		var element = layui.element;
		var table = layui.table;
		var layer = layui.layer;
		var  form = layui.form;
		// 加載所有的工種訊息
		monitorRequestObj.jobTypes(layer, monitorDrawing.jobTypesCallBack);
		// 獲取組別訊息
		monitorRequestObj.groups(layer, monitorDrawing.groupsCallBack);
		monitorBinding.groupSwitch();
		// 獲取區域訊息
		monitorRequestObj.regions(layer, monitorDrawing.regionsCallBack);
		
		$("#areaClassify li").each(function(index) {
			monitorBinding.regionSwitch(index, layer, monitorDrawing.nodesCallBack);
		})
		// 第壹次默認選擇第壹組的第壹個區域
		$("ul#areaClassify>li:first a").click();
		$("ul#areaClassify>li:first>dl>dd:first").click();
		//監聽模式切換
		element.on('tab(monitor)',function(obj){
			if(obj.index == 0) {// 重新加載地圖
				$("#areaClassify dd.selected[regionid]").click();
				// 加載所有的工種訊息
				monitorRequestObj.jobTypes(layer, monitorDrawing.jobTypesCallBack);
			}
		})
		var isShowTagNumber = $("#areaSelect>li:first").attr("monitorregionshownumber");
		// 需要我們執行請求卡片的操作
		monitorTimer(1000 , layer, 10, isShowTagNumber)();
		var filtrateForm = null;
		//篩選
		$("#filtrate").click(function(){
			$('#filtrateForm>div.filtrateDiv:first').empty();
			$("#checkboxTreeDiv li.jobtype").each(function(){
				// 遍歷所有的組別
				var jobTypeId = $(this).attr("jobTypeId");
				var jobTypeName = $(this).attr("jobTypeName");
				var sfilterCheck = "<input type='checkbox' jobtypeId='" + jobTypeId + "' " +
								   "title='" + (jobTypeName || jobTypeId)  + "' name='jobType-" + jobTypeId + "' " +
								   "lay-skin='primary' checked> ";
				$('#filtrateForm>div.filtrateDiv:first').append(sfilterCheck);
			})
			form.render();
			filtrateForm = layer.open({
				type: 1,
				content: $('#filtrateForm'),
				title: false
			})
		})
		//監聽確定按鈕
		form.on('submit(confirm)',function(obj){
			// 選擇到裏面符合要求的卡片
			$("#checkboxTree input[type='checkbox']").prop("checked",false).change();
			// 選擇工種
			for ( var key in obj.field) {
				if(key.indexOf("jobType-") >= 0) {// 說明當前是選擇工種卡片
					$("#checkboxTree>li.jobtype[jobtypeid='" + key.replace("jobType-", "") + "']>p input[type='checkbox']").prop("checked",true).change();
				}
			}
			// 選擇報警類型
			for ( var key in obj.field) {
				if(key.indexOf("jobType-") < 0){// 說明當前是選擇工種卡片
					$("#checkboxTree>li.jobtype>p.parentNode input[type='checkbox']:checked").each(function(){
						// 選擇工種裏面的報警類型
						$(this).parents("p.parentNode").siblings("ul.childNodes").find("li input[type='checkbox']").prop("checked", false).change();
						$(this).parents("p.parentNode").siblings("ul.childNodes").find("li[" + key + "='true'] input[type='checkbox']").prop("checked",true).change();
					})
				}
			}	
			layer.close(filtrateForm);
		})
		// 區域選擇 / TAG管理 切換
		$("#areaSelect li").eq(0).addClass("activeArea");
		$("#areaSelect li").off("click").click(function(){ 
			$("#areaSelect li").removeClass("activeArea").eq($(this).index()).addClass("activeArea");
			if($(this).index()==0){// 區域選擇
				$("#areaClassifyDiv").show();
				$("#TAGMgr").hide();
			} else {// Tag管理
				$("#areaClassifyDiv").hide();
				$("#TAGMgr").show();
				var regionName = $("#areaClassify dd.selected").attr("regionname");
				$("#regionNumberMessage li:last span:first").text(regionName+"人數：");
				// 加載所有的工種訊息
				monitorRequestObj.jobTypes(layer, monitorDrawing.jobTypesCallBack);
			}
		})
		// 搜索按鈕
		$("span.search>button").off("click").click(function(){
			// 獲取卡片訊息
			var searchText = $(this).siblings("input[type='text']").val();
			if(!searchText){
				layer.msg("搜尋信息不能為空...", {icon: 2});
				return;
			}
			// 開始搜索卡片訊息
			monitorRequestObj.searchTag(searchText, layer, monitorDrawing.searchTagCallBack);
		})
		$("span.search>input[type='text']").off("input propertychange").on("input propertychange", function(){
			// 判斷屬性值是否存在
			if($(this).siblings("button[tagId]").length > 0){// 當前正處在搜索的狀態
				$(this).siblings("button").removeAttr("tagId");
				$("#isAllChecked").prop("checked",true).change();
			}
		})
		// 綁定webSocket
		var webSocketMessage = function(msg) {
			var result = JSON.parse(msg.data)
			if(result.type == 1) {// 說明此時是警報訊息
				$("#warnNumberMessage>li:eq(0)>p").text(result.data["personnelHelpWarnCache"]);// 人員求救
				$("#warnNumberMessage>li:eq(1)>p").text(result.data["AreaControlWarnCache"]);// 區域管制
				$("#warnNumberMessage>li:eq(2)>p").text(result.data["lowPowerWarnCache"]);// 電量不足
				$("#warnNumberMessage>li:eq(3)>p").text(result.data["abnormalTagWarnCache"]);// 卡片異常
				$("#warnNumberMessage>li:eq(4)>p").text(result.data["notMoveWarnCache"]);// 未移動報警
				$("#warnNumberMessage>li:eq(5)>p").text(result.data["abnormalReferWarnCache"]);// 參考點異常報警
				$("#warnNumberMessage>li:eq(6)>p").text(result.data["abnormalBaseWarnCache"]);// 數據節點異常報警
			}
		}
		// webSocket綁定 admin: 表示用戶名
		var webSoketUserId = $(window.parent.document).find("p.user").text();
		// 獲取SessionId
		customWebSockets("WarnWebSocketContainer/" + webSoketUserId, webSocketMessage)();
	})
	//全選
	$("#isAllChecked").off("change").change(function(){
		$("#inverse").prop("checked",false);
		if($(this).is(":checked")){
			$("#checkboxTree>li.jobtype input[type='checkbox']").prop("checked",true).change();
		}else{
			$("#checkboxTree>li.jobtype input[type='checkbox']").prop("checked",false).change();
		}
	});
	//反選
	$("#inverse").off("change").change(function(){
		$("#isAllChecked").prop("checked",false);
		$("#checkboxTree >li.jobtype input[type='checkbox']").each(function(){
			if($(this).prop("checked")){
				$(this).prop("checked", false).change();
			} else {
				$(this).prop("checked", true).change();
			}
		});
	});
});
/*===========自定義滾輪事件=========*/
var Scrolling=false;
function _$(o){
	return document.getElementById(o);
};
function ScroMove(){
	Scrolling=true;
};
document.onmousemove=function(e){
	if(Scrolling==false){
		return;
	}
	ScroNow(e);
};
document.onmouseup=function(e){
	Scrolling=false;
};
function ScroNow(event){
	var event=event?event:(window.event?window.event:null);
	var Y=event.clientY-_$("checkboxTreeDiv").getBoundingClientRect().top-_$("bar").clientHeight/2;
	var H=_$("scroll").clientHeight-_$("bar").clientHeight;
	var SH=Y/H*(_$("checkboxTree").scrollHeight-_$("checkboxTree").clientHeight);
	if(Y<0){ Y=0; }; if(Y>H){ Y=H; };
	_$("bar").style.top=Y+"px";
	_$("checkboxTree").scrollTop=SH;
};
//註冊事件
if(document.addEventListener){   
   document.addEventListener('DOMMouseScroll',ScrollWheel,false); //FF  
};
document.onmousewheel=ScrollWheel;//IE/Opera/Chrome  
function ScrollWheel(e){
	if(e.target.clientWidth>250){ return; }
	e = e || window.event;
	var Y=_$("checkboxTree").scrollTop;
	var H=_$("checkboxTree").scrollHeight-_$("checkboxTree").clientHeight;
	if(e.wheelDelta){
		if(e.wheelDelta>=120){ Y=Y-10 }else{ Y=Y+10 }; //FF
	}else if(e.detail){
		if(e.detail<=-3){ Y=Y-10 }else{ Y=Y+10 }; //IE/Opera/Chrome
	}
	if(Y<0)Y=0; if(Y>H)Y=H;
	_$("checkboxTree").scrollTop=Y;
	var SH=Y/H*_$("scroll").clientHeight-_$("bar").clientHeight;
	if(SH<0)SH=0;
	_$("bar").style.top=SH+"px";
	return false;
}
/*===========自定義滾輪事件end=========*/
var monitorBinding = {
	groupSwitch: function() {// 組別切換函數
		$("#areaClassify li a").off("click").click(function(){
			if($(this).find('i').hasClass('icon-zhankai')){
				$(this).removeClass('zhankai');
				$(this).find('i').removeClass('icon-zhankai').addClass('icon-shoulong');
				$(this).siblings('dl').hide();
			}else{
				$("#areaClassify li a i").removeClass('icon-zhankai').addClass('icon-shoulong');
				$("#areaClassify li a").removeClass('zhankai').siblings('dl').hide();
				$(this).addClass('zhankai');
				$(this).find('i').removeClass('icon-shoulong').addClass('icon-zhankai');
				$(this).siblings('dl').show();
			}
		});
	},
	regionSwitch: function(index, layer, callBack) {
		$("#areaClassify li").eq(index).find("dl dd").off("click").click(function(){
			$("#areaClassify li dl dd").removeClass('selected');
			$("#areaClassify li").eq(index).find("dl dd").eq($(this).index()).addClass('selected');
			var $regionDom = $("#areaClassify li").eq(index).find("dl dd").eq($(this).index());
			$("#place").text($(this).parents("li[groupName]").attr("groupName") + " / " + $(this).attr("regionName"));
			// 切換區域地圖
			var absPath = "image/" + $regionDom.attr("image");
			// 清空卡片區域
			$("#monitorMap div.pRefer").remove();
			// 清空樹狀圖的工種列表和卡片列表
			$("#checkboxTreeDiv li.jobtype").remove();
			$("#checkboxTreeDiv li[tagid]").remove();
			$("table#monitor tr[tagid]").remove();
			$("#monitorMap>img")[0].onload = function(){
				// 地圖加載完成
				// 切換區域時，加載基站訊息
				var regionId = $regionDom.attr("regionId");
				$.ajax({ type: "GET",url: "enode/region/" + regionId, dataType: "JSON", 
					success: function(result) {
						if(result.code == 600) {// 獲取區域的節點訊息成功
							callBack(result.data, layer);
						} else {// 獲取區域的節點訊息失敗
							layer.msg(result.msg, {icon: 5});
						}
					},
					error: function(result) {
						layer.alert("獲取區域【id: " + regionId + "】" +
								    "的所有基站訊息出現異常！", {icon : 2});
					}
				})
			}
			$("#monitorMap>img").attr("src" , absPath);
			$("#monitorContent").attr({"regionId":$regionDom.attr("regionId"),
							            "groupId":$regionDom.attr("groupId")});
		})
	}
}
var showLocalIndex = null;
var monitorDrawing = {
	groupsCallBack: function(groups){ // 渲染組別訊息
		$("#areaClassify").empty();
		var options = [];
		for (var i = 0; i < groups.length; i++) {
			// 獲取當前組別的所有區域
			options.push("<li groupId='" + groups[i]["id"] + "' groupName='" + groups[i]["name"] + "'><a class=''><span>" + (groups[i]["name"] || groups[i]["id"]) + "</span><i class='iconfont icon-shoulong'></i></a><dl style='display: none;'></dl></li>");
		}
		$("#areaClassify").append(options.join(""));
	},
	regionsCallBack: function(regions){ // 渲染區域
		for (var i = 0; i < regions.length; i++) {
			var $regDlDom = $("#areaClassify>li[groupid='" + regions[i]["groupId"] + "']").find("dl");
			// 將當前的區域添加到dl中
			$regDlDom.append("<dd regionId='" + regions[i]["id"] + "' regionName='" + regions[i]["name"] + "' image='" + regions[i]["image"] + "' groupId='" + regions[i]["groupId"] + "'>" + (regions[i]["name"] || regions[i]["id"]) + "</dd>");
		}
	},
	jobTypesCallBack: function(jobTypes){ // 刷新工種
		$("#checkboxTree").empty();
		var jobType = [], filtrateJobType = [];
		for (var i = 0; i < jobTypes.length; i++) {
			jobType.push("<li class='jobtype' jobTypeName='" + jobTypes[i]["name"] + "' jobTypeId='" + jobTypes[i]["no"] + "'>" +
					     "<p class='parentNode'><span class=''>+</span><input type='checkbox'><span>" + (jobTypes[i]["name"] || jobTypes[i]["no"])  +"</span>" +
					     "<i>(</i><span>" + jobTypes[i]["current"] + "<strong> / </strong>" + jobTypes[i]["total"] + "</span><i>)</i></p><ul class='childNodes'></ul></li>");
		}
		jobType.push("<li class='jobtype' jobTypeName='其它工種' jobTypeId='-1'>" +
					     "<p class='parentNode'><span class=''>+</span><input type='checkbox'><span>其它工種</span>" +
					     "<i>(</i><span>0</span><i>)</i></p><ul class='childNodes'></ul></li>");
		$("#checkboxTree").append(jobType.join(""));
		// 綁定
		// 工種復選框綁定click、change事件
		$(".parentNode").on("click","span:eq(0)", function(){ //子節點顯示/隱藏
			$(this).parent().siblings(".childNodes").stop().slideToggle("fast");
			if($(this).hasClass("stretch")){
				$(this).removeClass("stretch").text("+");
			}else{
				$(this).addClass("stretch").text("-");
			}
		});
	},
	nodesCallBack: function(nodes, layer){// 基站回調函數
		var referArroundTimer = function(time_ms) {
			var timerIndex = null;
			var referDrawingTags = function(tags) {
				for (var i = 0; i < tags.length; i++) {
					var $showTag = $("#showReferTags table tr[tagId='" + tags[i]["id"] + "']");
					if($showTag.length <= 0){// 沒有存在，需要我們添加到裏面
						$showTag = $("<tr tagId='" + tags[i]["id"]  + "'><td tagId>" + tags[i]["id"] + "</td><td tagName>" + tags[i]["tagName"] + "</td>" +
						  "<td company>" + (tags[i]["companyName"] || tags[i]["companyNo"]) + "</td>" +
						  "<td jobType>" + (tags[i]["jobTypeName"] || tags[i]["jobTypeNo"]) + "</td>" + 
						  "<td rssi>" + tags[i]["rssi"] + " dbm" + "</td>" + "<td sleepTime>" + tags[i]["sleepTime"] +" s" +  "</td>" +
						  "<td bat>" + tags[i]["bat"] + " %" + "</td>" + "<td notMoveTime>" + tags[i]["notMoveTime"] + " s" + "</td>" +
						  "<td reportTime>" + tags[i]["reportTime"]  + "</td></tr>").appendTo("#showReferTags table");
					} else {
						$showTag.find("td[rssi]").text(tags[i]["rssi"] + " dbm");
						$showTag.find("td[sleepTime]").text(tags[i]["sleepTime"] + " s");
						$showTag.find("td[bat]").text(tags[i]["bat"] + " %");
						$showTag.find("td[notMoveTime]").text(tags[i]["notMoveTime"] + " s");
						$showTag.find("td[reportTime]").text(tags[i]["reportTime"]);
					}
					$showTag.attr({
						"personnelHelp" : tags[i]["personnelHelp"],
						"lowPower" : tags[i]["lowPower"],
						"notMove" : tags[i]["notMove"],
						"areaControl" : tags[i]["areaControl"],
						"abnormalTag" : tags[i]["abnormalTag"]
					})
					if(tags[i]["personnelHelp"]){// 顯示紅色背景色，字體黑色
						$showTag.css({"background": "red", "color": "white"});
					} else if(tags[i]["abnormalTag"]){// 卡片異常
						$showTag.css({"background": "#9E9E9E", "color": "white"});
					} else if(tags[i]["areaControl"]){// 區域管制
						$showTag.css({"background": "#673ab7", "color": "white"});
					} else if(tags[i]["notMove"]){// 未移動報警
						$showTag.css({"background": "#03a9f4", "color": "white"});
					} else if(tags[i]["lowPower"]){// 低電量報警
						$showTag.css({"background": "rgb(90, 33, 193)", "color": "white"});
					} else {
						$showTag.css({"background": "white", "color": "#666"});
					}
				}
			}
			var requestTags = function(referId){
				$.ajax({type: "GET", url: "tagmsg/refer/" + referId, dataType: "JSON", async: false,
					success: function(result){
						if(result.code == 600){
							referDrawingTags(result.data);
						}
					}, 
					error: function(){
						console.log("獲取參考點【id: " + referId + "】周圍的卡片出現異常!");
					}})
			}
			return function(){
				var referId = $("#showReferTags").attr("referId");
				requestTags(referId);
				var index = setInterval(function() {
					// 判斷是否可見
					if($("#showReferTags").is(":visible")) {// 可見
						requestTags(referId);
					} else {
						clearInterval(index);
					}
				}, time_ms);
				return index;
			}
		}
		// 1. 清空之前所有的節點和卡片列表
		$("#monitorMap>div.eNodeB").remove();
		$("#monitorMap ul.taglist").remove();
		// 2. 獲取當前地圖的大小
		var mapWidth = $("#monitorMap").width();
		var mapHeight = $("#monitorMap").height();
		for (var i = 0; i < nodes.length; i++) {
			if(nodes[i]['type'] == 1){// 如果是基站我們不在頁面上顯示
				continue;
			}
			// 3. 計算基站在當前區域中的坐標
			var nodeLeft = mapWidth * nodes[i]["mapX"]/nodes[i][ "width"];
			var nodeTop  = mapHeight * nodes[i]["mapY"]/nodes[i]["height"];
			// 4. 將基站添加到地圖的容器中
			var $pNode = null;
			if($("#monitorContent").attr("basevisible")=="true"){
				$pNode = $("<div id='PNode" + nodes[i]["id"] + "' class='pRefer' pname='" + 
						nodes[i]["name"] + "' ptype='" + nodes[i]["type"] + "'><span class='layui-badge'>0</span><span>" + 
						nodes[i]["name"]  + " </span></div>").appendTo("#monitorMap").hide();
			} else {
				$pNode = $("<div id='PNode" + nodes[i]["id"] + "' class='pRefer' pname='" + 
						nodes[i]["name"] + "' ptype='" + nodes[i]["type"] + "'><span class='layui-badge'>0</span></div>").appendTo("#monitorMap").hide();
			}
			if(nodes[i]["type"] == 2) {// 出入參考點
				$pNode.addClass("InOutNodeB");
			}
			// 5. 設置基站在地圖中的位置left - 19 ， top - 8
			var offSetLeft = 19 * mapWidth/nodes[i][ "width"];
			var offSetTop = 8 * mapHeight/nodes[i]["height"];
			$pNode.css({"top": (nodeTop - offSetTop) + "px","left": (nodeLeft - offSetLeft) + "px"});
			// 查看參考點和節點是否可見
			var nodeWidth = $pNode.width();
			var $tagList = $("<ul referId='" + nodes[i]["id"] + "' tagNumber='0' class='taglist'></ul>").appendTo("#monitorMap")
						.css({"top": (nodeTop - offSetTop - 24) + "px","left": (nodeLeft - offSetLeft + nodeWidth + 5) + "px"})
			// 參考點時我們需要綁定, 判斷當前的節點是否可見
			$pNode.off("click").click(function(){
				var referId = $(this).attr("id").replace("PNode", "");
				var referName = $(this).attr("pname");
				$("#showReferTags").attr("referId", referId);
				layer.open({type: 1, content:$("#showReferTags"), 
					   title: false, area: ['750px', '400px'], 
					   offset: ["150px","355px"]});
				// 開啟定時器，每隔壹段時間請求壹次refer周圍的卡片數量
				$("#showReferTags tr[tagId]").remove();
				if(showLocalIndex) {
					clearInterval(showLocalIndex);
				} 
				showLocalIndex = referArroundTimer(1000)();
		   })
		}
	},
	searchTagCallBack: function(tag, layer) {// 搜索卡片回調函數
		// 判斷當前 選擇 的 組是否相同
		var $groupa = $("ul.areaClassify li[groupid='" + tag["groupId"] + "'] a");
		if(!$groupa.hasClass("zhankai")){
			$("ul.areaClassify li[groupid='" + tag["groupId"] + "'] a").click();
		}
		// 判斷當前 選擇的區域是否相同
		if($("#monitorContent").attr("regionId") != tag["regionId"]){
			$("ul.areaClassify dd[regionid='" + tag["regionId"] + "']").click();
		}
		// 在搜索按鈕上做壹個搜索標記
		$("span.search>button").attr("tagId", tag["id"]);
		// 註意更新選擇框
		$("#checkboxTreeDiv").find("input[type='checkbox']").prop("checked", false).change();
		$("#checkboxTreeDiv li[tagid='" + tag["id"]  + "']").find("input[type='checkbox']").prop("checked", true).change();
	}
}