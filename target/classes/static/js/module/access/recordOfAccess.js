$(function(){
	layui.use(['element','table','form',"layer",'laydate'],function(){
		var element = layui.element;
		var table = layui.table;
		var form = layui.form;
		var layer = layui.layer;
		var laydate = layui.laydate;
		
		var accessTable, fillAccessIndex, personTable;
		laydate.render({
		    elem: '#cardModeDtSelect', //指定元素
		    value: new Date(),
		    isInitValue: true, //是否允許填充初始值，默認為 true
		    done: function(value, date){
		    	// 1. 獲取組別
				var groupId = $("#groupClassify>li.active").attr("groupid");
				// 2. 獲取當前選擇的日期
				var accessTime = cardModeDtSelect.value;
				if(!accessTime){
					accessTime = new Date().format("yyyy-MM-dd");
				}
				accessRequest.OnDuty(groupId, accessTime, layer, accessDrawingCallBack.OnDuty);
				accessRequest.OffDuty(groupId, accessTime, layer, accessDrawingCallBack.OffDuty);
		    }
		});
		laydate.render({
		    elem: '#listModeDtSelect', //指定元素
		    value: new Date(),
		    isInitValue: true, //是否允許填充初始值，默認為 true
		    done: function(value, date) {
		    	refreshAccess(value);
		    }
		});
		//讀卡記錄補登
		laydate.render({ //創建日期選擇
			elem: '#clockInTime',
			type: 'datetime'
		})
		var refreshAccess = function(value){
			// 1. 獲取組別
			var groupId = $("#groupClassify>li.active").attr("groupid");
			if(accessTable){
				accessTable.reload({
					url : "access/" + groupId + "?accessTime="
					+ value
				})
			}
		}
		var refreshAccessKey = function(value, key){
			// 1. 獲取組別
			var groupId = $("#groupClassify>li.active").attr("groupid");
			if(accessTable) {
				accessTable.reload({
					url : "access/key/" + groupId + "?accessTime="
					+ value + "&key="+ key,
					page : {
						curr : 1
					}
				})
			}
		}
		// 進出統計綁定時間函數
		var accessBindingEventFun = {
			groups : function(layer) {
				$("ul#groupClassify>li").off("click").click(function() {
					$("#groupClassify>li.active").removeClass("active");
					$(this).addClass("active");
					// 1. 獲取組別
					var groupId = $("#groupClassify>li.active").attr("groupid");
					// 2. 獲取當前選擇的日期
					var accessTime = cardModeDtSelect.value;
					if(!accessTime){
						accessTime = new Date().format("yyyy-MM-dd");
					}
					accessRequest.OnDuty(groupId, accessTime, layer, accessDrawingCallBack.OnDuty);
					accessRequest.OffDuty(groupId, accessTime, layer, accessDrawingCallBack.OffDuty);
					var accessTime = listModeDtSelect.value;
					if(!accessTime){
						accessTime = new Date().format("yyyy-MM-dd");
					}
					refreshAccess(accessTime);
					$("#regionNumber").empty();
					accessRequest.regions(groupId, accessDrawingCallBack.regions);
				})
			}
		}
		// 出入統計渲染回調函數
		var accessDrawingCallBack = {
			doorControlStatus: function(status){
				switch(status) {
				case 0:// 進入狀態
					$("#doorStatusGroup").find("button").removeClass("DisableDoorStatus");
					$("#doorStatusGroup button:last").addClass("DisableDoorStatus");
					break;
				case 1:// 離開狀態
					$("#doorStatusGroup").find("button").removeClass("DisableDoorStatus");
					$("#doorStatusGroup button:first").addClass("DisableDoorStatus");
					break;
				case 2:// 都禁用狀態
					$("#doorStatusGroup").find("button").removeClass("DisableDoorStatus");
					$("#doorStatusGroup button").addClass("DisableDoorStatus");
					break;
				}
			},
			regions: function(regions) {
				var array = [];
				array.push("<li regionTotal><span>工地總人數</span><p>0</p><span>人</span></li>");
				for (var i = 0; i < regions.length; i++) {
					array.push("<li regionId='" + regions[i]["id"] + "'><span>" + (regions[i]["name"] || regions[i]["id"]) + "</span><p>0</p><span>人</span></li>");
				}
				$("#regionNumber").append(array.join(""));
			},
			addAccess: function(){
				if(fillAccessIndex){
					layer.msg("補填上下班訊息成功...", {icon: 6});
					layer.close(fillAccessIndex);
				}
			},
			nodes: function(data){
				var referOptions = [];
				for (var i = 0; i < data.length; i++) {
					referOptions.push("<option value='" + data[i]["id"] + "'>" + (data[i]["name"] || data[i]["id"]) + "</option>");
				}
				$("#groupPlace select[name='referId']").empty().append(referOptions.join(""));
			},
			groups : function(data) {
				// 1.清除li組別訊息
				var groupsAttr = [], groupsOption = [];
				for (var i = 0; i < data.length; i++) {
					groupsAttr.push("<li groupId='" + data[i]["id"] + "'>"+ (data[i]["name"] || data[i]["id"]) + "</li>");
					groupsOption.push("<option value='" +  data[i]["id"] + "'>" +  (data[i]["name"] || data[i]["id"]) + "</option>")
				}
				// 2.將li添加到ul上
				$("ul#groupClassify").empty().append(groupsAttr.join(""));
				$("#groupPlace select[name='groupId']").empty().append(groupsOption.join(""));
				$("#groupPlace select[name='groupId'] option:first").prop("checked",true);
				accessRequest.refers(data[0]["id"] , layer, accessDrawingCallBack.nodes);
				// 重新刷新
				form.render();
				$("ul#groupClassify>li:first").addClass("active");
				accessBindingEventFun.groups();
			},
			OffDuty: function(data){
				$("ul#offDuty").empty();
				var offDutyAttr = [];
				for (var i = 0; i < data.length; i++) {
					var $li = $("#offDuty>li[uuid='" + data[i]["uuid"] + "']");
					if($li.length <= 0){// 不存在，我們需要進行添加
						var imgPath = data[i]["image"]?"staff/"+data[i]["image"]:"img/sysperson.jpg";
						$li = $("<li uuid='" + data[i]["uuid"] + "'><dl><dd>姓名：<span>" + data[i]["tagName"] + "</span>" +
						  "</dd><dd>卡片ID：<span>" + data[i]["tagId"] + "</span></dd><dd>工種：<span>" + (data[i]["jobTypeName"] || data[i]["jobTypeNo"]) + "</span></dd>" +
						  "<dd>參考點：<span>" + (data[i]["referName"] || data[i]["referId"]) + "</span></dd>" +
						  "<dd>公司名稱：<span>" + (data[i]["companyName"] || data[i]["companyNo"]) + "</span></dd><dd>離開時間：<span>" + data[i]["accessTime"] + "</span></dd></dl>" +
						  "<img src='" + imgPath + "'></li>")
						.appendTo("ul#offDuty");
					} else {// 已經存在
						
					}
					if(data[i]["eol"]) {
						$li.css({"border-color":"red"});
					}
				}
				$("#offNumber>span").text(data.length);
			},
			OnDuty: function(data){
				$("ul#enter").empty();
				var offDutyAttr = [];
				for (var i = 0; i < data.length; i++) {
					var $li = $("#enter>li[uuid='" + data[i]["uuid"] + "']");
					if($li.length <= 0) {// 不存在，我們需要進行添加
						var imgPath = data[i]["image"]?"staff/"+data[i]["image"]:"img/sysperson.jpg";
						$li = $("<li uuid='" + data[i]["uuid"] + "'><dl><dd>姓名：<span>" + data[i]["tagName"] + "</span>" +
									"</dd><dd>卡片ID：<span>" + data[i]["tagId"] + "</span></dd><dd>工種：<span>" + (data[i]["jobTypeName"] || data[i]["jobTypeNo"]) + "</span></dd>" +
									"<dd>參考點：<span>" + (data[i]["referName"] || data[i]["referId"]) + "</span></dd>" +
									"<dd>公司名稱：<span>" + (data[i]["companyName"] || data[i]["companyNo"]) + "</span></dd><dd>進入時間：<span>" + data[i]["accessTime"] + "</span></dd></dl>" +
									"<img src='" + imgPath + "'></li>")
								.appendTo("ul#enter");
					} else {// 已經存在
						
					}
					if(data[i]["eol"]) {
						$li.css({"border-color":"red"});
					}
				}
				$("#enterNumber>span").text(data.length);
			},
			webSocketMessage : function(result) {
				var warnObj = JSON.parse(result.data);
				if(warnObj.type == 1){
					$("ul#alarmMsg>li:first p").text(
							warnObj.data["personnelHelpWarnCache"]);
					$("ul#alarmMsg>li:eq(1) p").text(
							warnObj.data["AreaControlWarnCache"]);
					$("ul#alarmMsg>li:eq(2) p").text(warnObj.data["lowPowerWarnCache"]);
					$("ul#alarmMsg>li:eq(3) p").text(
							warnObj.data["abnormalTagWarnCache"]);
					$("ul#alarmMsg>li:eq(4) p").text(warnObj.data["notMoveWarnCache"]);
					$("ul#alarmMsg>li:eq(5) p").text(
							warnObj.data["abnormalReferWarnCache"]);
					$("ul#alarmMsg>li:eq(6) p").text(
							warnObj.data["abnormalBaseWarnCache"]);
				} else if(warnObj.type == 4) {
					accessDrawingCallBack.doorControlStatus(warnObj.data);
					layer.msg(warnObj.msg,{icon: 0});
				}
			},
			accessList: function(){
				accessTable.reload();
			}
		}
		// 獲取組別訊息並渲染到頁面上
		accessRequest.groups(layer, accessDrawingCallBack.groups);
		$("ul#groupClassify>li:first").click();
		//監聽模式切換
		element.on("tab(access)",function(obj){
			if(obj.index==1) {
				var accessTime = listModeDtSelect.value;
				if(!accessTime){
					accessTime = new Date().format("yyyy-MM-dd");
				}
				refreshAccess(accessTime);
			} else if(obj.index==0) {
				// 1. 獲取組別
				var groupId = $("#groupClassify>li.active").attr("groupid");
				// 2. 獲取當前選擇的日期
				var accessTime = cardModeDtSelect.value;
				if(!accessTime) {
					accessTime = new Date().format("yyyy-MM-dd");
				}
				accessRequest.OnDuty(groupId, accessTime, layer, accessDrawingCallBack.OnDuty);
				accessRequest.OffDuty(groupId, accessTime, layer, accessDrawingCallBack.OffDuty);
			}
		})
		var groupId = $("#groupClassify>li.active").attr("groupid");
		//創建表格
		accessTable = table.render({
			elem: $("#access"),
			url: "access/" + groupId,
			page: true,
			height: "full-102",
			id: 'access',
			cols: [[ //表頭
				{field:"tagName", title:"姓名", align: 'center', sort: true, width: 72, templet: '#accessTagNameTool'},
				{field:"tagId", title:"ID", align: 'center', sort: true,width: 65},
				{field:"jobTypeName", title:"工種名稱", align: 'center', sort: true,  templet: '#accessJobTypeNameTool'},
				{field:"companyName", title:"公司名稱", align: 'center', sort: true, templet: '#accessCompanyNameTool'},
				{field:"enterReferName", title:"進入參考點名稱", align: 'center', sort: true, templet: '#enterReferNameTool'},
				{field:"leaveReferName", title:"離開參考點名稱", align: 'center', sort: true, templet: '#leaveReferNameTool'},
				{field:"onDutyTime", title:"進入時間", align: 'center',templet: '#onDutyTemplate', sort: true, width: 160},
				{field:"offDutyTime", title:"離開時間", align: 'center',templet: '#offDutyTemplate', sort: true, width: 160},
				{title:'操作', toolbar: $('#accessTableTool'),align:"center"}
			]],
			parseData: function(data){
				$("#totalAccessNumber>span").text(data.count);
			}
		})
		table.on('tool(accessTable)',function(obj){
			var data = obj.data;
			if(obj.event == "delete"){ //增加記錄
				var groupId = $("#groupClassify>li.active").attr("groupId");
				accessRequest.deleteAccess(layer, groupId, data, accessDrawingCallBack.accessList);
			}
		})
		// 連接服務器WebSocket，獲取警報訊息
		var webSoketUserId = $(window.parent.document).find("p.user").text();
		customWebSockets("WarnWebSocketContainer/" + webSoketUserId, accessDrawingCallBack.webSocketMessage)();
		//創建表格
		personTable = table.render({
			elem: $("#clockInSupplement"),
			url: 'person/page/',
			page: true,
			height: "full-102",
			id: 'clockInSupplement',
			cols: [[ //表頭
				{field:"no", title:"工號", sort: true},
				{field:"name", title:"姓名", sort: true},
				{field:"tagId", title:"卡片ID", sort: true},
				{field:"company_Name", title:"公司名稱", sort: true},
				{field:"jobType_Name", title:"工種名稱", sort: true},
				{title:"記錄補登", templet: '#supplementTableTool', align: 'center', width: 105}
			]],
			parseData: function(data){
				$("#totalPersonNumber>span").text(data.count);
			}
		})
		table.on('tool(supplement)',function(obj){
			var data = obj.data;
			if(obj.event == "add"){ //增加記錄
				fillAccessIndex = layer.open({
					type: 1,
					content: $('#supplementForm'),
					title: "上下班資料補登",
					area: ["910px",'auto'],
					maxWidth: '1200px',
					offset: ['100px','275px']
				})
				for(var key in data){
					$('#supplementForm input[name="'+key+'"]').val(data[key]);
				}
			}
		})
		form.on('select(groupFilter)', function(data){
			accessRequest.refers(data.value, layer, accessDrawingCallBack.nodes);
			// 重新刷新
			form.render();
		});
		//監聽提交事件
		form.on('submit(submitSupplement)',function(obj){
			var data = obj.field;
			// 補登陸上下班情況
			accessRequest.addAccess(data, layer, accessDrawingCallBack.addAccess);
			return false;
		})
		$("#refreshAccessCardBtn").off("click").click(function(){
			$("#cardAcessSearchText").val("")
			// 1. 獲取組別
			var groupId = $("#groupClassify>li.active").attr("groupid");
			// 2. 獲取當前選擇的日期
			var accessTime = cardModeDtSelect.value;
			if(!accessTime) {
				accessTime = new Date().format("yyyy-MM-dd");
			}
			accessRequest.OnDuty(groupId, accessTime, layer, accessDrawingCallBack.OnDuty);
			accessRequest.OffDuty(groupId, accessTime, layer, accessDrawingCallBack.OffDuty);
		});
		$("#refreshAccessListBtn").off("click").click(function(){
			$("#listAcessSearchText").val("")
			var accessTime = listModeDtSelect.value;
			if(!accessTime){
				accessTime = new Date().format("yyyy-MM-dd");
			}
			refreshAccess(accessTime);
		});
		$("#cardAcessSearchBtn").off("click").click(function(){
			// 1. 獲取組別
			var groupId = $("#groupClassify>li.active").attr("groupid");
			// 2. 獲取當前選擇的日期
			var accessTime = cardModeDtSelect.value;
			if(!accessTime){
				accessTime = new Date().format("yyyy-MM-dd");
			}
			// 3. 獲取關鍵字
			var key = $("#cardAcessSearchText").val();
			accessRequest.OnDutyKey(groupId, accessTime, key, layer, accessDrawingCallBack.OnDuty);
			accessRequest.OffDutyKey(groupId, accessTime, key, layer, accessDrawingCallBack.OffDuty);
		})
		$("#listAcessSearchBtn").off("click").click(function() {
			var accessTime = listModeDtSelect.value;
			if(!accessTime){
				accessTime = new Date().format("yyyy-MM-dd");
			}
			// 3. 獲取關鍵字
			var key = $("#listAcessSearchText").val();
			refreshAccessKey(accessTime, key);
		})
		$("#fillAccessRecordSearchBtn").off("click").click(function(){
			var key = $("#fillAccessRecordSearchText").val();
			personTable.reload({url: "person/page/key_name/?name="+key, page : {
				curr : 1
			}})
		})
		/**
		 * 刷新補錄列表
		 */
		$("#refreshfillAccessRecordBtn").off("click").click(function(){
			$("#fillAccessRecordSearchText").val("");
			personTable.reload({url: "person/page/"});
		})
		/**
		 * 進入
		 */
		$("#doorStatusGroup button:first").off("click").click(function(){
			accessRequest.setDoorControlStatus(layer, 0, accessDrawingCallBack.doorControlStatus);
		})
		/**
		 * 離開
		 */
		$("#doorStatusGroup button:last").off("click").click(function(){
			accessRequest.setDoorControlStatus(layer, 1, accessDrawingCallBack.doorControlStatus);
		})
		accessTimer(1000)();
		accessRequest.doorControlStatus(layer,accessDrawingCallBack.doorControlStatus);
	})
});