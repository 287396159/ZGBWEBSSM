/*===========
 * 報警管理
 * ==========*/
$(function() {
	var time_ms = 10000;
	layui.use(['table', 'layer'], function() {
		var table = layui.table;
		var layer = layui.layer;
		var personTotal;
		//創建人員求救表格
		var personTable = 
			table.render({
			elem: $('#personForHelp'),
			url: 'warm/personHelp/',
			defaultToolbar: ['filter', 'exports'],
			toolbar: '#alarmHeadTool',
			page: true,
			height: "full-12",
			id: 'personForHelp',
			cols: [
				[ //表頭
					{
						type: 'checkbox',
						width: 40
					},
					{
						field: "tagId",
						title: "TAG ID",
						sort: true,
						align: "center",
						width: 80
					},
					{
						field: "tagName",
						title: "名稱",
						align: "center",
						sort: true,
						templet: "#tagTool"
					},
					{
						field: "regionName",
						title: "所在區域",
						align: "center",
						sort: true,
						templet: '#regionTool'
					},
					{
						field: "companyName",
						title: "公司名稱",
						align: "center",
						sort: true,
						templet: '#companyTool'
					},
					{
						field: "jobTypeName",
						title: "工種",
						align: "center",
						sort: true,
						templet: "#jobTypeTool"
					},
					{
						field: "createTime",
						title: "警報開始時間",
						sort: true,
						align: "center",
						width: 150
					},
					{
						field: "handle",
						title: "是否處理",
						sort: true,
						align: "center",
						width: 92,
						templet: '#isDealTemplate'
					},
					{
						field: "handleTime",
						title: "處理時間",
						align: "center",
						sort: true,
						templet: '#isDealDateTemplate'
					}
				]
			],
			parseData: function(data) {
				$("span.alarmType").eq(0).text("人員求救");
				$("span.alarmNum").eq(0).text(data.count);
			}
		})
		//創建區域管制表格
		var areaControlTable = table.render({
			elem: $('#regionControl'),
			url: 'warm/areacontrol/',
			defaultToolbar: ['filter', 'exports'],
			toolbar: '#alarmHeadTool',
			page: true,
			height: "full-12",
			id: 'regionControl',
			cols: [
				[ //表頭
					{
						type: 'checkbox',
						width: 40
					},
					{
						field: "tagId",
						title: "TAG ID",
						sort: true,
						align: "center",
						width: 80
					},
					{
						field: "tagName",
						title: "名稱",
						align: "center",
						sort: true,
						templet: "#tagTool"
					},
					{
						field: "regionName",
						title: "所在區域",
						align: "center",
						sort: true,
						templet: "#regionTool"
					},
					{
						field: "companyName",
						title: "公司名稱",
						align: "center",
						sort: true,
						templet: "#companyTool"
					},
					{
						field: "jobTypeName",
						title: "工種",
						align: "center",
						sort: true,
						templet: "#jobTypeTool"
					},
					{
						field: "referName",
						title: "参考点",
						align: "center",
						sort: true,
						templet: "#referNameTool"
					},
					{
						field: "createTime",
						title: "警報開始時間",
						align: "center",
						sort: true
					},
					{
						field: "handle",
						title: "是否處理",
						sort: true,
						align: "center",
						width: 92,
						templet: '#isDealTemplate'
					},
					{
						field: "handleTime",
						title: "處理時間",
						align: "center",
						sort: true,
						templet: '#isDealDateTemplate'
					}
				]
			],
			parseData: function(data) {
				$("span.alarmType").eq(1).text("區域管制");
				$("span.alarmNum").eq(1).text(data.count);
			}
		})
		//創建人員未移動表格
		var notMoveTable = table.render({
			elem: $('#personNotMove'),
			url: 'warm/notMove/',
			defaultToolbar: ['filter', 'exports'],
			toolbar: '#alarmHeadTool',
			page: true,
			height: "full-12",
			id: 'personNotMove',
			cols: [
				[ //表頭
					{
						type: 'checkbox',
						width: 40
					},
					{
						field: "tagId",
						title: "TAG ID",
						sort: true,
						align: "center",
						width: 80
					},
					{
						field: "tagName",
						title: "名稱",
						align: "center",
						sort: true,
						templet: "#tagTool"
					},
					{
						field: "regionName",
						title: "所在區域",
						sort: true,
						align: "center",
						width: 110,
						templet: "#regionTool"
					},
					{
						field: "companyName",
						title: "公司名稱",
						sort: true,
						align: "center",
						width: 100,
						templet: "#companyTool"
					},
					{
						field: "jobTypeName",
						title: "工種",
						sort: true,
						align: "center",
						width: 70,
						templet: "#jobTypeTool"
					},
					{
						field: "curNotMoveTime",
						title: "未移動時間(分)",
						align: "center",
						width: 115,
						sort: true
					},
					{
						field: "createTime",
						title: "警報開始時間",
						align: "center",
						sort: true
					},
					{
						field: "handle",
						title: "是否處理",
						align: "center",
						sort: true,
						templet: '#isDealTemplate'
					},
					{
						field: "handleTime",
						title: "處理時間",
						align: "center",
						sort: true,
						templet: '#isDealDateTemplate'
					}
				]
			],
			parseData: function(data) {
				$("span.alarmType").eq(2).text("人員未移動");
				$("span.alarmNum").eq(2).text(data.count);
			}
		})
		//創建電量不足表格
		var lowBatTable = table.render({
			elem: $('#BatteryLow'),
			url: 'warm/lowPower/',
			defaultToolbar: ['filter', 'exports'],
			toolbar: '#alarmHeadTool',
			page: true,
			height: "full-12",
			id: 'BatteryLow',
			cols: [
				[ //表頭
					{
						type: 'checkbox',
						width: 40
					},
					{
						field: "tagId",
						title: "TAG ID",
						sort: true,
						align: "center",
						width: 80
					},
					{
						field: "tagName",
						title: "名稱",
						align: "center",
						sort: true,
						templet: "#tagTool"
					},
					{
						field: "regionName",
						title: "所在區域",
						sort: true,
						align: "center",
						width: 110,
						templet: "#regionTool"
					},
					{
						field: "companyName",
						title: "公司名稱",
						sort: true,
						align: "center",
						width: 100,
						templet: "#companyTool"
					},
					{
						field: "jobTypeName",
						title: "工種",
						sort: true,
						align: "center",
						width: 70,
						templet: "#jobTypeTool"
					},
					{
						field: "curBattery",
						title: "電量(%)",
						align: "center",
						width: 70,
						sort: true
					},
					{
						field: "createTime",
						title: "警報開始時間",
						align: "center",
						sort: true
					},
					{
						field: "handle",
						title: "是否處理",
						sort: true,
						align: "center",
						width: 92,
						templet: '#isDealTemplate'
					},
					{
						field: "handleTime",
						title: "處理時間",
						align: "center",
						sort: true,
						templet: '#isDealDateTemplate'
					}
				]
			],
			parseData: function(data) {
				$("span.alarmType").eq(3).text("電量不足");
				$("span.alarmNum").eq(3).text(data.count);
			}
		})
		//創建卡片異常表格
		var abnormalTagTable = table.render({
			elem: $('#cardAbnormal'),
			url: 'warm/abnormalTag/',
			defaultToolbar: ['filter', 'exports'],
			toolbar: '#alarmHeadTool',
			page: true,
			height: "full-12",
			id: 'cardAbnormal',
			cols: [
				[ //表頭
					{
						type: 'checkbox',
						width: 40
					},
					{
						field: "tagId",
						title: "TAG ID",
						sort: true,
						align: "center",
						width: 80
					},
					{
						field: "tagName",
						title: "名稱",
						align: "center",
						sort: true,
						templet: "#tagTool"
					},
					{
						field: "regionName",
						title: "所在區域",
						align: "center",
						sort: true,
						templet: "#regionTool"
					},
					{
						field: "companyName",
						title: "公司名稱",
						align: "center",
						sort: true,
						templet: "#companyTool"
					},
					{
						field: "jobTypeName",
						title: "工種",
						align: "center",
						sort: true,
						templet: "#jobTypeTool"
					},
					{
						field: "createTime",
						title: "警報開始時間",
						align: "center",
						sort: true
					},
					{
						field: "handle",
						title: "是否處理",
						sort: true,
						align: "center",
						width: 92,
						templet: '#isDealTemplate'
					},
					{
						field: "handleTime",
						title: "處理時間",
						align: "center",
						sort: true,
						templet: '#isDealDateTemplate'
					}
				]
			],
			parseData: function(data) {
				$("span.alarmType").eq(4).text("卡片異常");
				$("span.alarmNum").eq(4).text(data.count);
			}
		})
		
		//創建節點異常表格
		var abnormalNodeTable = table.render({
			elem: $('#nodeAbnormal'),
			url: 'warm/abnormalBase/',
			defaultToolbar: ['filter', 'exports'],
			toolbar: '#alarmHeadTool',
			page: true,
			height: "full-12",
			id: 'nodeAbnormal',
			cols: [
				[ //表頭
					{
						type: 'checkbox',
						width: 40
					},
					{
						field: "nodeId",
						title: "TAG ID",
						sort: true,
						align: "center",
						width: 80
					},
					{
						field: "nodeName",
						title: "名稱",
						align: "center",
						sort: true,
						templet: '#nodeTool'
					},
					{
						field: "regionName",
						title: "所在區域",
						align: "center",
						sort: true,
						templet: "#regionTool"
					},
					{
						field: "createTime",
						title: "警報開始時間",
						align: "center",
						sort: true
					},
					{
						field: "handle",
						title: "是否處理",
						align: "center",
						sort: true,
						width: 92,
						templet: '#isDealTemplate'
					},
					{
						field: "handleTime",
						title: "處理時間",
						align: "center",
						sort: true,
						templet: '#isDealDateTemplate'
					}
				]
			],
			parseData: function(data) {
				$("span.alarmType").eq(5).text("節點異常");
				$("span.alarmNum").eq(5).text(data.count);
			}
		})
		//創建參考點異常表格
		var abnormalReferTable = table.render({
			elem: $('#referAbnormal'),
			url: 'warm/abnormalRefer/',
			defaultToolbar: ['filter', 'exports'],
			toolbar: '#alarmHeadTool',
			page: true,
			height: "full-12",
			id: 'referAbnormal',
			cols: [
				[ //表頭
					{
						type: 'checkbox',
						width: 40
					},
					{
						field: "nodeId",
						title: "TAG ID",
						align: "center",
						sort: true,
						width: 80
					},
					{
						field: "nodeName",
						title: "名稱",
						align: "center",
						sort: true,
						templet: '#nodeTool'
					},
					{
						field: "regionName",
						title: "所在區域",
						align: "center",
						sort: true,
						templet: "#regionTool"
					},
					{
						field: "createTime",
						title: "警報開始時間",
						align: "center",
						sort: true
					},
					{
						field: "handle",
						title: "是否處理",
						sort: true,
						width: 92,
						align: "center",
						templet: '#isDealTemplate'
					},
					{
						field: "handleTime",
						title: "處理時間",
						align: "center",
						sort: true,
						templet: '#isDealDateTemplate'
					}
				]
			],
			parseData: function(data) {
				$("span.alarmType").eq(6).text("參考點異常");
				$("span.alarmNum").eq(6).text(data.count);
			}
		})
		//監聽頭部工具
		function toolbar(filter, tableId, callback) {
			table.on('toolbar(' + filter + ')', function(obj) {
				var checkStatus = table.checkStatus(obj.config.id);
				var data = checkStatus.data; //獲取到選中的數據
				switch (obj.event) {
					case 'deal': //處理警報
						if (data.length == 0) {
							layer.msg('請選擇要處理的警報', {
								time: 1000,
								icon: 0
							});
						} else {
							// 處理類型警報
							var uuids = [];
							for (var i = 0; i < data.length; i++) {
								uuids.push(data[i]["uuid"]);
							}
							switch($("#alarmClassify li.activeSet").index()){
							case 0:
								warmDataObj.personHelp.handle(uuids, layer, reloadTable.personHelp);
								break;
							case 1:
								warmDataObj.areaControl.handle(uuids, layer, reloadTable.areaControl);
								break;
							case 2:
								warmDataObj.notMove.handle(uuids, layer, reloadTable.notMove);
								break;
							case 3:
								warmDataObj.lowBat.handle(uuids, layer, reloadTable.lowBat);
								break;
							case 4:
								warmDataObj.abnormalTag.handle(uuids, layer, reloadTable.abnormalTag);
								break;
							case 5:
								warmDataObj.abnormalNode.handle(uuids, layer, reloadTable.abnormalNode);
								break;
							case 6:
								warmDataObj.abnormalRefer.handle(uuids, layer, reloadTable.abnormalRefer);
								break;
							}
						}
						break;
					case 'clear': //清除警報
						if (data.length == 0) {
							layer.msg('請選擇要清除的警報', {
								time: 1000,
								icon: 0
							});
						} else {
							layer.confirm('確認清除選中行嗎？', function(index) {
								var layFilterIndex = 'LAY-table-' + obj.config.index;
								//找到table filter的索引
								var tableContainer = $('div[lay-filter="' + layFilterIndex + '"]');
								//查找選中的checkbox
								tableContainer.find('.layui-table-body input[name="layTableCheckbox"]:checked').each(function() {
									//刪除tr
									$(this).parents('tr').remove();
								});
								layer.msg('刪除中...', {
									icon: 16,
									shade: 0.3,
									time: 1000
								});
								var uuids = [];
								for (var i = 0; i < data.length; i++) {
									uuids.push(data[i]["uuid"]);
								}
								//向服務器發出請求刪除數據
								switch($("#alarmClassify li.activeSet").index()){
								case 0:
									warmDataObj.personHelp.clear(uuids, layer, reloadTable.personHelp);
									break;
								case 1:
									warmDataObj.areaControl.clear(uuids, layer, reloadTable.areaControl);
									break;
								case 2:
									warmDataObj.notMove.clear(uuids, layer, reloadTable.notMove);
									break;
								case 3:
									warmDataObj.lowBat.clear(uuids, layer, reloadTable.lowBat);
									break;
								case 4:
									warmDataObj.abnormalTag.clear(uuids, layer, reloadTable.abnormalTag);
									break;
								case 5:
									warmDataObj.abnormalNode.clear(uuids, layer, reloadTable.abnormalNode);
									break;
								case 6:
									warmDataObj.abnormalRefer.clear(uuids, layer, reloadTable.abnormalRefer);
									break;
								}
							})
						}
						break;
					case 'refresh': //刷新列表
						table.reload(tableId);
						break;
				};
			})
		}
		toolbar('person', 'personForHelp');
		toolbar('region', 'regionControl');
		toolbar('notMove', 'personNotMove');
		toolbar('low', 'BatteryLow');
		toolbar('card', 'cardAbnormal');
		toolbar('node', 'nodeAbnormal');
		toolbar('refer','referAbnormal');
		//警報類型切換
		$("#alarmClassify li").eq(0).addClass("activeSet");
		$("#alarmClassify li").click(function() {
			$("#alarmClassify li").removeClass("activeSet").eq($(this).index()).addClass("activeSet");
			$("#alarmContent li").hide().eq($(this).index()).show();
			switch ($(this).index()) { //重載表格
				case 0:
					table.reload('personForHelp');
					break;
				case 1:
					table.reload('regionControl');
					break;
				case 2:
					table.reload('personNotMove');
					break;
				case 3:
					table.reload('BatteryLow');
					break;
				case 4:
					table.reload('cardAbnormal');
					break;
				case 5:
					table.reload('nodeAbnormal');
					break;
				case 6:
					table.reload('referAbnormal');
					break;
			}
			$('.alarmType').eq($(this).index()).text($(this).text()); //顯示報警類型
		});
		
		var reloadTable = {
			personHelp: function(){
				personTable.reload();
			},
			areaControl: function(){
				areaControlTable.reload();
			},
			notMove: function(){
				notMoveTable.reload();
			},
			lowBat: function(){
				lowBatTable.reload();
			},
			abnormalTag: function(){
				abnormalTagTable.reload();
			},
			abnormalNode: function(){
				abnormalNodeTable.reload();
			},
			abnormalRefer: function(){
				abnormalReferTable.reload();
			}
		}
		
	})
});
