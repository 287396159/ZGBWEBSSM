$(function() {
	layui.use(['table', 'form', 'laydate','layer','element','laypage'], function() {
        var table = layui.table;
        var form = layui.form;
        var laydate = layui.laydate;
        var layer = layui.layer;
        var element = layui.element;
        var laypage = layui.laypage;
        var urlParams = {
        	personnelHelp: function(){
        		//此處查詢到的數據可用於導出報表
            	var name = $("#personForHelpWindow input[name='name']").val();
            	var params = (name?("name="+name+"&"):"") + 
				(personHelpCompany?("company=" + personHelpCompany + "&"):"") + 
				(personHelpJobType?("jobType=" + personHelpJobType + "&"):"") + 
				(personHelpGroup?("group=" + personHelpGroup + "&"):"") + 
				(startTime?("start="+startTime + "&"):"") + 
				(endTime?("end="+endTime+"&"):"");
            	return params;
        	},
        	areaControl: function(){
        		//此處查詢到的數據可用於導出報表
    			var name = $("#regionControlWindow input[name='name']").val();
    			var params = (name?("name="+name+"&"):"") + 
					(regionControlCompany?("company=" + regionControlCompany + "&"):"") + 
					(regionControlJobType?("jobType=" + regionControlJobType + "&"):"") + 
					(regionControlGroup?("group=" + regionControlGroup + "&"):"") + 
					(regionControlStart?("start="+regionControlStart + "&"):"") + 
					(regionControlEnd?("end="+regionControlEnd+"&"):"");
    			return params;
        	},
        	notMove: function() {
        		var name = $("#personNotMoveWindow input[name='name']").val();
				var params = (name?("name="+name+"&"):"") + 
					(notMoveCompany?("company=" + notMoveCompany + "&"):"") + 
					(notMoveJobType?("jobType=" + notMoveJobType + "&"):"") + 
					(notMoveGroup?("group=" + notMoveGroup + "&"):"") + 
					(notMoveStart?("start="+notMoveStart + "&"):"") + 
					(notMoveEnd?("end="+notMoveEnd+"&"):"");
				return params;
        	},
        	lowPower: function(){
        		//此處查詢到的數據可用於導出報表
    			var name = $("#batteryLowWindow input[name='name']").val();
    			var params = (name?("name="+name+"&"):"") + 
					(lowPowerCompany?("company=" + lowPowerCompany + "&"):"") + 
					(lowPowerJobType?("jobType=" + lowPowerJobType + "&"):"") + 
					(lowPowerGroup?("group=" + lowPowerGroup + "&"):"") + 
					(lowPowerStart?("start="+lowPowerStart + "&"):"") + 
					(lowPowerEnd?("end="+lowPowerEnd+"&"):"");
    			return params;
        	},
        	abnormalTag: function(){
        		var name = $("#cardAbnormalWindow input[name='name']").val();
        		var params = (name?("name="+name+"&"):"") + 
					(abnormalTagCompany?("company=" + abnormalTagCompany + "&"):"") + 
					(abnormalTagJobType?("jobType=" + abnormalTagJobType + "&"):"") + 
					(abnormalTagGroup?("group=" + abnormalTagGroup + "&"):"") + 
					(abnormalTagStart?("start="+abnormalTagStart + "&"):"") + 
					(abnormalTagEnd?("end="+abnormalTagEnd+"&"):"");
        		return params;
        	},
        	abnormalNode: function(){
        		var name = $("#nodeAbnormalWindow input[name='name']").val();
        		var params = (name?("name="+name+"&"):"") + 
					(abnormalNodeGroup?("group=" + abnormalNodeGroup + "&"):"") + 
					(abnormalNodeStart?("start="+abnormalNodeStart + "&"):"") + 
					(abnormalNodeEnd?("end="+abnormalNodeEnd+"&"):"");
        		return params;
        	},
        	abnormalRefer: function(){
        		var name = $("#pointAbnormalWindow input[name='name']").val();
        		var params = (name?("name="+name+"&"):"") + 
					(abnormalReferGroup?("group=" + abnormalReferGroup + "&"):"") + 
					(abnormalReferStart?("start="+abnormalReferStart + "&"):"") + 
					(abnormalReferEnd?("end="+abnormalReferEnd+"&"):"");
        		return params;
        	}
        }
        var warnTotal = {
        	personHelp: function(callBack){
        		var totalUrl = "report/personhelp/total?" + urlParams.personnelHelp();
        		requestDataObj.warn.total(layer, totalUrl, warnPageTool.personnelHelp);
        	},
        	areaControl: function(callBack){
        		var totalUrl = "report/regionControl/total?" + urlParams.areaControl();
        		requestDataObj.warn.total(layer, totalUrl, warnPageTool.areaControl);
        	},
        	notMove: function(callBack){
				var totalUrl = "report/notmove/total?" + urlParams.notMove();
				requestDataObj.warn.total(layer, totalUrl, warnPageTool.notMove);
        	},
        	lowPower: function(callBack){
        		var totalUrl = "report/lowpower/total?" + urlParams.lowPower();
        		requestDataObj.warn.total(layer, totalUrl, warnPageTool.lowPower);
        	},
        	abnormalTag: function(callBack){
        		var totalUrl = "report/abnormaltag/total?" + urlParams.abnormalTag();
        		requestDataObj.warn.total(layer, totalUrl, warnPageTool.abnormalTag);
        	},
        	abnormalNode: function(callBack){
        		var totalUrl = "report/abnormalnode/total?" + urlParams.abnormalNode();
        		requestDataObj.warn.total(layer, totalUrl, warnPageTool.abnormalNode);
        	},
        	abnormalRefer: function(callBack){
        		var totalUrl = "report/abnormalrefer/total?" + urlParams.abnormalRefer();
        		requestDataObj.warn.total(layer, totalUrl, warnPageTool.abnormalRefer);
        	}
        }
		// 警告的分页工具
		var warnPageTool = {
        	personnelHelp: function(total) {
        		var searchUrl = "report/personhelp/search?" + urlParams.personnelHelp();
				laypage.render({
					elem : 'statisticPersonnelHelpPageTool',
					count : total,
					layout : ['count','prev','page','next','limit','refresh','skip'],
					jump : function(obj, first) {
						queryWarns.personnelHelp(searchUrl, obj.curr, obj.limit);
					}
				});
        	},
        	areaControl: function(total){
        		var searchUrl = "report/regionControl/search?" + urlParams.areaControl();
				laypage.render({
					elem : 'statisticAreaControlPageTool',
					count : total,
					layout : ['count','prev','page','next','limit','refresh','skip'],
					jump : function(obj, first) {
						queryWarns.areaControl(searchUrl, obj.curr, obj.limit);
					}
				});
        	},
			notMove: function(total) { // 未移动分页插件初始化
				var searchUrl = "report/notmove/search?" + urlParams.notMove();
				laypage.render({
					elem : 'statisticNotMovePageTool',
					count : total,
					layout : ['count','prev','page','next','limit','refresh','skip'],
					jump : function(obj, first) {
						queryWarns.nowMove(searchUrl, obj.curr, obj.limit);
					}
				});
			},
			lowPower: function(total){
				var searchUrl = "report/lowpower/search?" + urlParams.lowPower();
				laypage.render({
					elem : 'statisticLowPowerPageTool',
					count : total,
					layout : ['count','prev','page','next','limit','refresh','skip'],
					jump : function(obj, first) {
						queryWarns.lowPower(searchUrl, obj.curr, obj.limit);
					}
				});
			},
			abnormalTag: function(total){
				var searchUrl = "report/abnormaltag/search?" + urlParams.abnormalTag();
				laypage.render({
					elem : 'statisticAbnormalTagPageTool',
					count : total,
					layout : ['count','prev','page','next','limit','refresh','skip'],
					jump : function(obj, first) {
						queryWarns.abnormalTag(searchUrl, obj.curr, obj.limit);
					}
				});
			},
			abnormalNode: function(total){
				var searchUrl = "report/abnormalnode/search?" + urlParams.abnormalNode();
				laypage.render({
					elem : 'statisticAbnormalNodePageTool',
					count : total,
					layout : ['count','prev','page','next','limit','refresh','skip'],
					jump : function(obj, first) {
						queryWarns.abnormalNode(searchUrl, obj.curr, obj.limit);
					}
				});
			},
			abnormalRefer: function(total){
				var searchUrl = "report/abnormalrefer/search?" + urlParams.abnormalRefer();
				laypage.render({
					elem : 'statisticAbnormalReferPageTool',
					count : total,
					layout : ['count','prev','page','next','limit','refresh','skip'],
					jump : function(obj, first) {
						queryWarns.abnormalRefer(searchUrl, obj.curr, obj.limit);
					}
				});
			}
		}
        var createStatisticTable = {
        	personnelHelp: function(data){
        		// 生成表格字符串
        		var $table = $("<table><thead><tr>" + 
        				     "<th>ID</th><th>姓名</th>" + 
        				     "<th>編號</th><th>公司</th>" + 
        				     "<th>工種</th><th>組別</th>" + 
        				     "<th>區域</th><th>參考點</th>" + 
        				     "<th>產生時間</th><th>處理時間</th>" + 
							 "<th>清理時間</th><th>記錄數(條)</th>" + 
							 "</tr></thead><tbody></tbody></table>");
        		drawingStatisticTable.personnelHelp($table, data);
        		// 開始生成excel表格
        		$table.table2excel({
					filename: "人員求救警報"+".xls",
					exclude_img: true,
        		});
        	},
        	areaControl: function(data){
        		// 生成表格字符串
        		var $table = $("<table><thead><tr>" + 
        				     "<th>ID</th><th>姓名</th>" + 
        				     "<th>編號</th><th>公司</th>" + 
        				     "<th>工種</th><th>組別</th>" + 
        				     "<th>區域</th><th>參考點</th>" + 
        				     "<th>產生時間</th><th>處理時間</th>" + 
							 "<th>清理時間</th><th>記錄數(條)</th>" + 
							 "</tr></thead><tbody></tbody></table>");
        		drawingStatisticTable.areaControl($table, data);
        		// 開始生成excel表格
        		$table.table2excel({
					filename: "區域管制警報"+".xls",
					exclude_img: true,
        		});
        	},
        	notMove: function(data) {
        		// 生成表格字符串
        		var $table = $("<table><thead><tr><th>ID</th>" +
        				"<th>姓名</th><th>編號</th><th>公司</th>" +
        				"<th>工種</th><th>組別</th><th>區域</th>" +
        				"<th>參考點</th><th>未移動時間(min)</th>" +
        				"<th>產生時間</th><th>處理時間</th><th>清理時間</th>" +
        				"<th>記錄數(條)</th><th>未移動總時間(min)</th>" +
        				"</tr></thead><tbody></tbody></table>");
        		drawingStatisticTable.notMoveWarn($table, data);
        		// 開始生成excel表格
        		$table.table2excel({
					filename: "人員未移動警報"+".xls",
					exclude_img: true,
        		});
        	},
        	lowPower: function(data) {
        		// 生成表格字符串
        		var $table = $("<table><thead><tr><th>ID</th>" +
        				    "<th>姓名</th><th>編號</th><th>公司</th>" +
        				    "<th>工種</th><th>組別</th><th>區域</th>" +
        				    "<th>參考點</th><th>電量(%)</th><th>產生時間</th>" +
        				    "<th>處理時間</th><th>清理時間</th>" +
        				    "<th>記錄數(條)</th></tr>" +
        				    "</thead><tbody></tbody></table>");
        		drawingStatisticTable.lowPowerWarn($table, data);
        		// 開始生成excel表格
        		$table.table2excel({
					filename: "低電量警報"+".xls",
					exclude_img: true,
        		});
        	},
        	abnormalTag: function(data){
        		// 生成表格字符串
        		var $table = $("<table><thead><tr><th>ID</th><th>姓名</th>" +
	        				"<th>編號</th><th>公司</th><th>工種</th><th>組別</th>" +
	        				"<th>區域</th><th>參考點</th><th>產生時間</th>" +
	        				"<th>處理時間</th><th>清理時間</th><th>記錄數(條)</th>" +
	        				"</tr></thead><tbody></tbody></table>");
        		drawingStatisticTable.abnormalTagWarn($table, data);
        		// 開始生成excel表格
        		$table.table2excel({
					filename: "卡片異常警報"+".xls",
					exclude_img: true,
        		});
        	},
        	abnormalRefer: function(data){
        		// 生成表格字符串
        		var $table = $("<table><thead><tr><th>ID</th><th>名稱</th>" +
        				"<th>組別</th><th>區域</th><th>休眠時間(s)</th><th>斷開時間(min)</th>" +
        				"<th>產生時間</th><th>處理時間</th><th>清理時間</th><th>記錄數(條)</th>" +
        				"</tr></thead><tbody></tbody></table>");
        		drawingStatisticTable.abnormalReferWarn($table, data);
        		// 開始生成excel表格
        		$table.table2excel({
					filename: "參考點異常警報"+".xls",
					exclude_img: true,
        		});
        	},
        	abnormalNode: function(data){
        		// 生成表格字符串
        		var $table = $("<table><thead><tr><th>ID</th><th>名稱</th>" +
        				"<th>組別</th><th>區域</th><th>休眠時間(s)</th><th>斷開時間(min)</th>" +
        				"<th>產生時間</th><th>處理時間</th><th>清理時間</th><th>記錄數(條)</th>" +
        				"</tr></thead><tbody></tbody></table>");
        		drawingStatisticTable.abnormalNodeWarn($table, data);
        		// 開始生成excel表格
        		$table.table2excel({
					filename: "節點異常警報"+".xls",
					exclude_img: true,
        		});
        	}
        	
        }
		var drawingStatisticTable = {
        	personnelHelp: function($table, data) {
        		$table.find("tbody").empty();
				var array = [];
				for (var i = 0; i < data.length; i++) {
						//1 . 獲取當前行數
					var id = data[i]["id"];
					var statisticRow = data[i]["statisticalWarns"];
					var rowNumber = statisticRow.length;
					for (var j = 0; j < rowNumber; j++) {
						var rowCommonStr = "<td>" + statisticRow[j]["tagName"] + "</td>" +
					  		"<td>" + statisticRow[j]["tagNo"] + "</td>" + 
					  		"<td>" + (statisticRow[j]["companyName"] || statisticRow[j]["companyNo"]) + "</td>" + 
					  		"<td>" + (statisticRow[j]["jobTypeName"] || statisticRow[j]["jobTypeNo"]) + "</td>" + 
					  		"<td>" + (statisticRow[j]["groupName"] || statisticRow[j]["groupId"]) + "</td>" + 
					  		"<td>" + (statisticRow[j]["regionName"] || statisticRow[j]["regionId"]) + "</td>" + 
					  		"<td>" + (statisticRow[j]["referName"] || statisticRow[j]["referId"]) + "</td>" + 
					  		"<td>" + (statisticRow[j]["createTime"] || "") + "</td>" + 
					  		"<td>" + (statisticRow[j]["handleTime"] || "") + "</td>" + 
					  		"<td>" + (statisticRow[j]["clearTime"] || "") + "</td>";
						if(j == 0) {
							var firstRow = data
							array.push("<tr><td rowspan='" + rowNumber + "'>" + id + "</td>" + rowCommonStr + 
						  	"<td rowspan='" + rowNumber + "'>" + rowNumber + "</td></tr>");
						} else {
								array.push("<tr>" + rowCommonStr + "</tr>");
						}
					}
				}
				$table.find("tbody").append(array.join(""));
        	},
        	areaControl: function($table, data){
        		$table.find("tbody").empty();
				var array = [];
				for (var i = 0; i < data.length; i++) {
						//1 . 獲取當前行數
					var id = data[i]["id"];
					var statisticRow = data[i]["statisticalWarns"];
					var rowNumber = statisticRow.length;
					for (var j = 0; j < rowNumber; j++) {
						var rowCommonStr = "<td>" + statisticRow[j]["tagName"] + "</td>" +
					  		"<td>" + statisticRow[j]["tagNo"] + "</td>" + 
					  		"<td>" + (statisticRow[j]["companyName"] || statisticRow[j]["companyNo"]) + "</td>" + 
					  		"<td>" + (statisticRow[j]["jobTypeName"] || statisticRow[j]["jobTypeNo"]) + "</td>" + 
					  		"<td>" + (statisticRow[j]["groupName"] || statisticRow[j]["groupId"]) + "</td>" + 
					  		"<td>" + (statisticRow[j]["regionName"] || statisticRow[j]["regionId"]) + "</td>" + 
					  		"<td>" + (statisticRow[j]["referName"] || statisticRow[j]["referId"]) + "</td>" + 
					  		"<td>" + (statisticRow[j]["createTime"] || "") + "</td>" + 
					  		"<td>" + (statisticRow[j]["handleTime"] || "") + "</td>" + 
					  		"<td>" + (statisticRow[j]["clearTime"] || "") + "</td>";
						if(j == 0) {
							var firstRow = data
							array.push("<tr><td rowspan='" + rowNumber + "'>" + id + "</td>" + rowCommonStr + 
						  	"<td rowspan='" + rowNumber + "'>" + rowNumber + "</td></tr>");
						} else {
								array.push("<tr>" + rowCommonStr + "</tr>");
						}
					}
				}
				$table.find("tbody").append(array.join(""));
        	},
			notMoveWarn: function($table, data) {
				$table.find("tbody").empty();
				var array = [];
				for (var i = 0; i < data.length; i++) {
						//1 . 獲取當前行數
					var id = data[i]["id"];
					var statisticRow = data[i]["statisticalWarns"];
					var totalTime = data[i]["totalTime"];
					var rowNumber = statisticRow.length;
					for (var j = 0; j < rowNumber; j++) {
						var rowCommonStr = "<td>" + statisticRow[j]["tagName"] + "</td>" +
					  		"<td>" + statisticRow[j]["tagNo"] + "</td>" + 
					  		"<td>" + (statisticRow[j]["companyName"] || statisticRow[j]["companyNo"]) + "</td>" + 
					  		"<td>" + (statisticRow[j]["jobTypeName"] || statisticRow[j]["jobTypeNo"]) + "</td>" + 
					  		"<td>" + (statisticRow[j]["groupName"] || statisticRow[j]["groupId"]) + "</td>" + 
					  		"<td>" + (statisticRow[j]["regionName"] || statisticRow[j]["regionId"]) + "</td>" + 
					  		"<td>" + (statisticRow[j]["referName"] || statisticRow[j]["referId"]) + "</td>" + 
					  		"<td>" + statisticRow[j]["curNotMoveTime"] + "</td>" +
					  		"<td>" + (statisticRow[j]["createTime"] || "") + "</td>" + 
					  		"<td>" + (statisticRow[j]["handleTime"] || "") + "</td>" + 
					  		"<td>" + (statisticRow[j]["clearTime"] || "") + "</td>";
						if(j == 0) {
							var firstRow = data
							array.push("<tr><td rowspan='" + rowNumber + "'>" + id + "</td>" + rowCommonStr + 
						  	"<td rowspan='" + rowNumber + "'>" + rowNumber + "</td>" + 
						 	"<td rowspan='" + rowNumber +  "'>" + totalTime + "</td></tr>");
						} else {
								array.push("<tr>" + rowCommonStr + "</tr>");
						}
					}
				}
				$table.find("tbody").append(array.join(""));
			},
			lowPowerWarn: function($table, data){
				$table.find("tbody").empty();
				var array = [];
				for (var i = 0; i < data.length; i++) {
						//1 . 獲取當前行數
					var id = data[i]["id"];
					var statisticRow = data[i]["statisticalWarns"];
					var rowNumber = statisticRow.length;
					for (var j = 0; j < rowNumber; j++) {
						var rowCommonStr = "<td>" + statisticRow[j]["tagName"] + "</td>" +
					  		"<td>" + statisticRow[j]["tagNo"] + "</td>" + 
					  		"<td>" + (statisticRow[j]["companyName"] || statisticRow[j]["companyNo"]) + "</td>" + 
					  		"<td>" + (statisticRow[j]["jobTypeName"] || statisticRow[j]["jobTypeNo"]) + "</td>" + 
					  		"<td>" + (statisticRow[j]["groupName"] || statisticRow[j]["groupId"]) + "</td>" + 
					  		"<td>" + (statisticRow[j]["regionName"] || statisticRow[j]["regionId"]) + "</td>" + 
					  		"<td>" + (statisticRow[j]["referName"] || statisticRow[j]["referId"]) + "</td>" + 
					  		"<td>" + statisticRow[j]["curBattery"] + "</td>" +
					  		"<td>" + (statisticRow[j]["createTime"] || "") + "</td>" + 
					  		"<td>" + (statisticRow[j]["handleTime"] || "") + "</td>" + 
					  		"<td>" + (statisticRow[j]["clearTime"] || "") + "</td>";
						if(j == 0) {
							var firstRow = data
							array.push("<tr><td rowspan='" + rowNumber + "'>" + id + "</td>" + rowCommonStr + 
						  	"<td rowspan='" + rowNumber + "'>" + rowNumber + "</td></tr>");
						} else {
								array.push("<tr>" + rowCommonStr + "</tr>");
						}
					}
				}
				$table.find("tbody").append(array.join(""));
			},
			abnormalTagWarn: function($table, data){
				$table.find("tbody").empty();
				var array = [];
				for (var i = 0; i < data.length; i++) {
						//1 . 獲取當前行數
					var id = data[i]["id"];
					var statisticRow = data[i]["statisticalWarns"];
					var rowNumber = statisticRow.length;
					for (var j = 0; j < rowNumber; j++) {
						var rowCommonStr = "<td>" + statisticRow[j]["tagName"] + "</td>" +
					  		"<td>" + statisticRow[j]["tagNo"] + "</td>" + 
					  		"<td>" + (statisticRow[j]["companyName"] || statisticRow[j]["companyNo"]) + "</td>" + 
					  		"<td>" + (statisticRow[j]["jobTypeName"] || statisticRow[j]["jobTypeNo"]) + "</td>" + 
					  		"<td>" + (statisticRow[j]["groupName"] || statisticRow[j]["groupId"]) + "</td>" + 
					  		"<td>" + (statisticRow[j]["regionName"] || statisticRow[j]["regionId"]) + "</td>" + 
					  		"<td>" + (statisticRow[j]["referName"] || statisticRow[j]["referId"]) + "</td>" + 
					  		"<td>" + (statisticRow[j]["createTime"] || "") + "</td>" + 
					  		"<td>" + (statisticRow[j]["handleTime"] || "") + "</td>" + 
					  		"<td>" + (statisticRow[j]["clearTime"] || "") + "</td>";
						if(j == 0) {
							var firstRow = data
							array.push("<tr><td rowspan='" + rowNumber + "'>" + id + "</td>" + rowCommonStr + 
						  	"<td rowspan='" + rowNumber + "'>" + rowNumber + "</td></tr>");
						} else {
								array.push("<tr>" + rowCommonStr + "</tr>");
						}
					}
				}
				$table.find("tbody").append(array.join(""));
			},
			abnormalNodeWarn: function($table, data){
				$table.find("tbody").empty();
				var array = [];
				for (var i = 0; i < data.length; i++) {
						//1 . 獲取當前行數
					var id = data[i]["id"];
					var statisticRow = data[i]["statisticalWarns"];
					var rowNumber = statisticRow.length;
					for (var j = 0; j < rowNumber; j++) {
						var rowCommonStr = "<td>" + statisticRow[j]["nodeName"] + "</td>" +
					  		"<td>" + (statisticRow[j]["groupName"] || statisticRow[j]["groupId"]) + "</td>" + 
					  		"<td>" + (statisticRow[j]["regionName"] || statisticRow[j]["regionId"]) + "</td>" + 
					  		"<td>" + (statisticRow[j]["sleepTime"] || "") + "</td>" + 
					  		"<td>" + (statisticRow[j]["curDisTime"] || "") + "</td>" + 
					  		"<td>" + (statisticRow[j]["createTime"] || "") + "</td>" + 
					  		"<td>" + (statisticRow[j]["handleTime"] || "") + "</td>" + 
					  		"<td>" + (statisticRow[j]["clearTime"] || "") + "</td>";
						if(j == 0) {
							var firstRow = data
							array.push("<tr><td rowspan='" + rowNumber + "'>" + id + "</td>" + rowCommonStr + 
						  	"<td rowspan='" + rowNumber + "'>" + rowNumber + "</td></tr>");
						} else {
								array.push("<tr>" + rowCommonStr + "</tr>");
						}
					}
				}
				$table.find("tbody").append(array.join(""));
			},
			abnormalReferWarn: function($table, data){
				$table.find("tbody").empty();
				var array = [];
				for (var i = 0; i < data.length; i++) {
						//1 . 獲取當前行數
					var id = data[i]["id"];
					var statisticRow = data[i]["statisticalWarns"];
					var rowNumber = statisticRow.length;
					for (var j = 0; j < rowNumber; j++) {
						var rowCommonStr = "<td>" + statisticRow[j]["nodeName"] + "</td>" +
					  		"<td>" + (statisticRow[j]["groupName"] || statisticRow[j]["groupId"]) + "</td>" + 
					  		"<td>" + (statisticRow[j]["regionName"] || statisticRow[j]["regionId"]) + "</td>" + 
					  		"<td>" + (statisticRow[j]["sleepTime"] || "") + "</td>" + 
					  		"<td>" + (statisticRow[j]["curDisTime"] || "") + "</td>" + 
					  		"<td>" + (statisticRow[j]["createTime"] || "") + "</td>" + 
					  		"<td>" + (statisticRow[j]["handleTime"] || "") + "</td>" + 
					  		"<td>" + (statisticRow[j]["clearTime"] || "") + "</td>";
						if(j == 0) {
							var firstRow = data
							array.push("<tr><td rowspan='" + rowNumber + "'>" + id + "</td>" + rowCommonStr + 
						  	"<td rowspan='" + rowNumber + "'>" + rowNumber + "</td></tr>");
						} else {
								array.push("<tr>" + rowCommonStr + "</tr>");
						}
					}
				}
				$table.find("tbody").append(array.join(""));
			}
		}
		// 请求查询警告讯息
		var queryWarns = {
        	personnelHelp: function(url , page, limit) {
        		// 请求网络数据
				$("#statisticPersonnelHelpPageTool").addClass("align");
				requestDataObj.warn.search(layer, url + "&page=" + page + "&limit=" + limit, $("#personnelHelpStatictisTable"),
										drawingStatisticTable.personnelHelp);
        	},
        	areaControl: function(url , page, limit) {
        		$("#statisticAreaControlPageTool").addClass("align");
				requestDataObj.warn.search(layer, url + "&page=" + page + "&limit=" + limit, $("#areaControlStatictisTable"),
										drawingStatisticTable.areaControl);
        	},
			nowMove: function(url , page, limit) {
				// 请求网络数据
				$("#statisticNotMovePageTool").addClass("align");
				requestDataObj.warn.search(layer, url + "&page=" + page + "&limit=" + limit, $("#nowMoveStatictisTable"),
										drawingStatisticTable.notMoveWarn);
			},
			lowPower: function(url , page, limit) {
				$("#statisticLowPowerPageTool").addClass("align");
				requestDataObj.warn.search(layer, url + "&page=" + page + "&limit=" + limit, $("#lowPowerStatictisTable"),
										drawingStatisticTable.lowPowerWarn);
			},
			abnormalTag: function(url , page, limit) {
				$("#statisticAbnormalTagPageTool").addClass("align");
				requestDataObj.warn.search(layer, url + "&page=" + page + "&limit=" + limit, $("#abnormalTagStatictisTable"),
										drawingStatisticTable.abnormalTagWarn);
			},
			abnormalNode: function(url , page, limit) {
				$("#statisticAbnormalNodePageTool").addClass("align");
				requestDataObj.warn.search(layer, url + "&page=" + page + "&limit=" + limit, $("#abnormalNodeStatictisTable"),
										drawingStatisticTable.abnormalNodeWarn);
			},
			abnormalRefer: function(url , page, limit) {
				$("#statisticAbnormalReferPageTool").addClass("align");
				requestDataObj.warn.search(layer, url + "&page=" + page + "&limit=" + limit, $("#abnormalReferStatictisTable"),
						drawingStatisticTable.abnormalReferWarn);
			}
		}
        var drawingExportReport = {
    		on_OffDuty: {
    			companys: function(data){// 公司
    			$("#onOffDutyQuery select[name='companyName'] option:not(:first)").remove();
    			$("#onOffDutyQuery1 select[name='companyName'] option:not(:first)").remove();
    			$("#onOffDutyQuery2 select[name='companyName'] option:not(:first)").remove();		
    			$("#inOutQuery select[name='companyName'] option:not(:first)").remove();
    			$("#totalHoursWorked select[name='companyName'] option:not(:first)").remove();
    			var attr = [];
    			for (var i = 0; i < data.length; i++) {
    				attr.push("<option value='" + data[i]["no"] + "'>" +
    						 (data[i]["name"] || data[i]["no"]) + "</option>");
    			}
    			$("#onOffDutyQuery select[name='companyName']").append(attr.join(""));
    			$("#onOffDutyQuery1 select[name='companyName']").append(attr.join(""));
    			$("#onOffDutyQuery2 select[name='companyName']").append(attr.join(""));
    			$("#inOutQuery select[name='companyName']").append(attr.join(""));
    			$("#totalHoursWorked select[name='companyName']").append(attr.join(""));
    			form.render();
    			},
    			jobTypes: function(data){// 工種
    				$("#onOffDutyQuery select[name='jobTypeName'] option:not(:first)").remove();
    				$("#onOffDutyQuery1 select[name='jobTypeName'] option:not(:first)").remove();
    				$("#onOffDutyQuery2 select[name='jobTypeName'] option:not(:first)").remove();
    				$("#inOutQuery select[name='jobTypeName'] option:not(:first)").remove();
    				$("#totalHoursWorked select[name='jobTypeName'] option:not(:first)").remove();
    				var attr = [];
    				for (var i = 0; i < data.length; i++) {
    					attr.push("<option value='" + data[i]["no"] + "'>" +
    						  (data[i]["name"] || data[i]["no"]) + "</option>");
    				}
    				$("#onOffDutyQuery select[name='jobTypeName']").append(attr.join(""));
    				$("#onOffDutyQuery1 select[name='jobTypeName']").append(attr.join(""));
    				$("#onOffDutyQuery2 select[name='jobTypeName']").append(attr.join(""));
    				$("#inOutQuery select[name='jobTypeName']").append(attr.join(""));
    				$("#totalHoursWorked select[name='jobTypeName']").append(attr.join(""));
    				form.render();
    			},
    			groups: function(data){// 工地
    				$("#onOffDutyQuery select[name='groupName'] option:not(:first)").remove();
    				$("#onOffDutyQuery1 select[name='groupName'] option:not(:first)").remove();
    				$("#onOffDutyQuery2 select[name='groupName'] option:not(:first)").remove();
    				$("#inOutQuery select[name='groupName'] option:not(:first)").remove();
    				$("#totalHoursWorked select[name='groupName'] option:not(:first)").remove();
    				var attr = [];
    				for (var i = 0; i < data.length; i++) {
    					attr.push("<option value='" + data[i]["id"] + "'>" +
    								   (data[i]["name"] || data[i]["id"]) + "</option>");
    				}
    				$("#onOffDutyQuery select[name='groupName']").append(attr.join(""));
    				$("#onOffDutyQuery1 select[name='groupName']").append(attr.join(""));
    				$("#onOffDutyQuery2 select[name='groupName']").append(attr.join(""));
    				$("#inOutQuery select[name='groupName']").append(attr.join(""));
    				$("#totalHoursWorked select[name='groupName']").append(attr.join(""));
    				form.render();
    			}
    		},
    		personHelp: function(data){
    			$("#warnType>dd:first>p.warnTotal span").text(data["totalNumber"]);
    			$("#warnType>dd:first>p.warnHandle span").text(data["handleNumber"]);
    			$("#warnType>dd:first>p.warnNotHandle span").text(data["notHandleNumber"]);
    			var myChart = echarts.init(document.getElementById('personHelpChart'));
    		    // 指定图表的配置项和数据
    		    var option = {
		    		 legend: {selectedMode:false},
		    		 tooltip: {},
		    		 dataset: {
		    		        source: [
		    		            ['type', '未處理', '已處理']
		    		        ]
		    		  },
		    		  xAxis: {type: 'category'},
		    		  yAxis: {},
		    		  series: [
		    		        {type: 'bar', color: '#FF5722'},
		    		        {type: 'bar', color: '#009688'}
		    		  ]
    		    };
    			for ( var key in data["groupCardMap"]) {
					var name = (data["groupCardMap"][key]["groupName"] || data["groupCardMap"][key]["groupId"]);
					var notNumber = data["groupCardMap"][key]["notHandleNumber"];
					var number = data["groupCardMap"][key]["handleNumber"];
					option['dataset']['source'].push([name, notNumber, number]);
    			}
    		    // 使用刚指定的配置项和数据显示图表。
    		    myChart.setOption(option);
    		},
    		areaController: function(data){
    			$("#warnType>dd:eq(1)>p.warnTotal span").text(data["totalNumber"]);
    			$("#warnType>dd:eq(1)>p.warnHandle span").text(data["handleNumber"]);
    			$("#warnType>dd:eq(1)>p.warnNotHandle span").text(data["notHandleNumber"]);
    			var myChart = echarts.init(document.getElementById('regionControlChart'));
    		    // 指定图表的配置项和数据
    		    var option = {
		    		 legend: {selectedMode:false},
		    		 tooltip: {},
		    		 dataset: {
		    		        source: [
		    		            ['type', '未處理', '已處理']
		    		        ]
		    		  },
		    		  xAxis: {type: 'category'},
		    		  yAxis: {},
		    		  series: [
		    		        {type: 'bar', color: '#FF5722'},
		    		        {type: 'bar', color: '#009688'}
		    		  ]
    		    };
    			for ( var key in data["groupCardMap"]) {
					var name = (data["groupCardMap"][key]["groupName"] || data["groupCardMap"][key]["groupId"]);
					var notNumber = data["groupCardMap"][key]["notHandleNumber"];
					var number = data["groupCardMap"][key]["handleNumber"];
					option['dataset']['source'].push([name, notNumber, number]);
    			}
    		    // 使用刚指定的配置项和数据显示图表。
    		    myChart.setOption(option);
    		},
    		notMove: function(data){
    			$("#warnType>dd:eq(2)>p.warnTotal span").text(data["totalNumber"]);
    			$("#warnType>dd:eq(2)>p.warnHandle span").text(data["handleNumber"]);
    			$("#warnType>dd:eq(2)>p.warnNotHandle span").text(data["notHandleNumber"]);
    			var myChart = echarts.init(document.getElementById('notMoveChart'));
    		    // 指定图表的配置项和数据
    		    var option = {
		    		 legend: {selectedMode:false},
		    		 tooltip: {},
		    		 dataset: {
		    		        source: [
		    		            ['type', '未處理', '已處理']
		    		        ]
		    		  },
		    		  xAxis: {type: 'category'},
		    		  yAxis: {},
		    		  series: [
		    		        {type: 'bar', color: '#FF5722'},
		    		        {type: 'bar', color: '#009688'}
		    		  ]
    		    };
    			for ( var key in data["groupCardMap"]) {
					var name = (data["groupCardMap"][key]["groupName"] || data["groupCardMap"][key]["groupId"]);
					var notNumber = data["groupCardMap"][key]["notHandleNumber"];
					var number = data["groupCardMap"][key]["handleNumber"];
					option['dataset']['source'].push([name, notNumber, number]);
    			}
    		    // 使用刚指定的配置项和数据显示图表。
    		    myChart.setOption(option);
    		},
    		lowPower:  function(data){
    			$("#warnType>dd:eq(3)>p.warnTotal span").text(data["totalNumber"]);
    			$("#warnType>dd:eq(3)>p.warnHandle span").text(data["handleNumber"]);
    			$("#warnType>dd:eq(3)>p.warnNotHandle span").text(data["notHandleNumber"]);
    			var myChart = echarts.init(document.getElementById('lowBatteryChart'));
    		    // 指定图表的配置项和数据
    		    var option = {
		    		 legend: {selectedMode:false},
		    		 tooltip: {},
		    		 dataset: {
		    		        source: [
		    		            ['type', '未處理', '已處理']
		    		        ]
		    		  },
		    		  xAxis: {type: 'category'},
		    		  yAxis: {},
		    		  series: [
		    		        {type: 'bar', color: '#FF5722'},
		    		        {type: 'bar', color: '#009688'}
		    		  ]
    		    };
    			for ( var key in data["groupCardMap"]) {
    				var name = (data["groupCardMap"][key]["groupName"] || data["groupCardMap"][key]["groupId"]);
    				var notNumber = data["groupCardMap"][key]["notHandleNumber"];
    				var number = data["groupCardMap"][key]["handleNumber"];
    				option['dataset']['source'].push([name, notNumber, number]);
    			}
    		    // 使用刚指定的配置项和数据显示图表。
    		    myChart.setOption(option);
    		},
    		abnormalTag: function(data){
    			$("#warnType>dd:eq(4)>p.warnTotal span").text(data["totalNumber"]);
    			$("#warnType>dd:eq(4)>p.warnHandle span").text(data["handleNumber"]);
    			$("#warnType>dd:eq(4)>p.warnNotHandle span").text(data["notHandleNumber"]);
    			
    			var myChart = echarts.init(document.getElementById('tagAbnormalChart'));
    		    // 指定图表的配置项和数据
    		    var option = {
		    		 legend: {selectedMode:false},
		    		 tooltip: {},
		    		 dataset: {
		    		        source: [
		    		            ['type', '未處理', '已處理']
		    		        ]
		    		  },
		    		  xAxis: {type: 'category'},
		    		  yAxis: {},
		    		  series: [
		    		        {type: 'bar', color: '#FF5722'},
		    		        {type: 'bar', color: '#009688'}
		    		  ]
    		    };
    			for ( var key in data["groupCardMap"]) {
    				var name = (data["groupCardMap"][key]["groupName"] || data["groupCardMap"][key]["groupId"]);
    				var notNumber = data["groupCardMap"][key]["notHandleNumber"];
    				var number = data["groupCardMap"][key]["handleNumber"];
    				option['dataset']['source'].push([name, notNumber, number]);
    			}
    		    // 使用刚指定的配置项和数据显示图表。
    		    myChart.setOption(option);
    		},
    		abnormalRefer: function(data){
    			$("#warnType>dd:eq(5)>p.warnTotal span").text(data["totalNumber"]);
    			$("#warnType>dd:eq(5)>p.warnHandle span").text(data["handleNumber"]);
    			$("#warnType>dd:eq(5)>p.warnNotHandle span").text(data["notHandleNumber"]);
    			var myChart = echarts.init(document.getElementById('referAbnormalChart'));
    		    // 指定图表的配置项和数据
    		    var option = {
		    		 legend: {selectedMode:false},
		    		 tooltip: {},
		    		 dataset: {
		    		        source: [
		    		            ['type', '未處理', '已處理']
		    		        ]
		    		  },
		    		  xAxis: {type: 'category'},
		    		  yAxis: {},
		    		  series: [
		    		        {type: 'bar', color: '#FF5722'},
		    		        {type: 'bar', color: '#009688'}
		    		  ]
    		    };
    			for ( var key in data["groupCardMap"]) {
    				var name = (data["groupCardMap"][key]["groupName"] || data["groupCardMap"][key]["groupId"]);
    				var notNumber = data["groupCardMap"][key]["notHandleNumber"];
    				var number = data["groupCardMap"][key]["handleNumber"];
    				option['dataset']['source'].push([name, notNumber, number]);
    			}
    		    // 使用刚指定的配置项和数据显示图表。
    		    myChart.setOption(option);
    		},
    		abnormalBase: function(data){
    			$("#warnType>dd:eq(6)>p.warnTotal span").text(data["totalNumber"]);
    			$("#warnType>dd:eq(6)>p.warnHandle span").text(data["handleNumber"]);
    			$("#warnType>dd:eq(6)>p.warnNotHandle span").text(data["notHandleNumber"]);
    			var myChart = echarts.init(document.getElementById('nodeAbnormalChart'));
    		    // 指定图表的配置项和数据
    		    var option = {
		    		 legend: {selectedMode:false},
		    		 tooltip: {},
		    		 dataset: {
		    		        source: [
		    		            ['type', '未處理', '已處理']
		    		        ]
		    		  },
		    		  xAxis: {type: 'category'},
		    		  yAxis: {},
		    		  series: [
		    		        {type: 'bar', color: '#FF5722'},
		    		        {type: 'bar', color: '#009688'}
		    		  ]
    		    };
    			for ( var key in data["groupCardMap"]) {
    				var name = (data["groupCardMap"][key]["groupName"] || data["groupCardMap"][key]["groupId"]);
    				var notNumber = data["groupCardMap"][key]["notHandleNumber"];
    				var number = data["groupCardMap"][key]["handleNumber"];
    				option['dataset']['source'].push([name, notNumber, number]);
    			}
    		    // 使用刚指定的配置项和数据显示图表。
    		    myChart.setOption(option);
    			}
        	}
    		var queryStatementsDrawing = {
    			personHelpWarns: {
    				companys: function(data) {
    					$("#personForHelpWindow select[name='companyName']").empty();
    					var companysAttr = [];
    					companysAttr.push("<option value=''>請選擇</option>");
    					for (var i = 0; i < data.length; i++) {
    						companysAttr.push("<option value='" + data[i]["name"] + "'>" + data[i]["name"] + "</option>");
    					}
    					$("#personForHelpWindow select[name='companyName']").append(companysAttr.join(""));
    				},
    				jobtypes: function(data) {
    					$("#personForHelpWindow select[name='jobTypeName']").empty();
    					var companysAttr = [];
    					companysAttr.push("<option value=''>請選擇</option>");
    					for (var i = 0; i < data.length; i++) {
    						companysAttr.push("<option value='" + data[i]["name"] + "'>" + data[i]["name"] + "</option>");
    					}
    					$("#personForHelpWindow select[name='jobTypeName']").append(companysAttr.join(""));
    				},
    				groups: function(data) {
    					$("#personForHelpWindow select[name='groupName']").empty();
    					var companysAttr = [];
    					companysAttr.push("<option value=''>請選擇</option>");
    					for (var i = 0; i < data.length; i++) {
    						companysAttr.push("<option value='" + data[i]["name"] + "'>" + data[i]["name"] + "</option>");
    					}
    					$("#personForHelpWindow select[name='groupName']").append(companysAttr.join(""));
    				}
    			},
    			regionControls: {
    				companys: function(data) {
    					$("#regionControlWindow select[name='companyName']").empty();
    					var companysAttr = [];
    					companysAttr.push("<option value=''>請選擇</option>");
    					for (var i = 0; i < data.length; i++) {
    						companysAttr.push("<option value='" + data[i]["name"] + "'>" + data[i]["name"] + "</option>");
    					}
    					$("#regionControlWindow select[name='companyName']").append(companysAttr.join(""));
    				},
    				jobtypes: function(data) {
    					$("#regionControlWindow select[name='jobTypeName']").empty();
    					var companysAttr = [];
    					companysAttr.push("<option value=''>請選擇</option>");
    					for (var i = 0; i < data.length; i++) {
    						companysAttr.push("<option value='" + data[i]["name"] + "'>" + data[i]["name"] + "</option>");
    					}
    					$("#regionControlWindow select[name='jobTypeName']").append(companysAttr.join(""));
    				},
    				groups: function(data) {
    					$("#regionControlWindow select[name='groupName']").empty();
    					var companysAttr = [];
    					companysAttr.push("<option value=''>請選擇</option>");
    					for (var i = 0; i < data.length; i++) {
    						companysAttr.push("<option value='" + data[i]["name"] + "'>" + data[i]["name"] + "</option>");
    					}
    					$("#regionControlWindow select[name='groupName']").append(companysAttr.join(""));
    				}
    			},
    			notMoveWarns: {
    				companys: function(data) {
    					$("#personNotMoveWindow select[name='companyName']").empty();
    					var companysAttr = [];
    					companysAttr.push("<option value=''>請選擇</option>");
    					for (var i = 0; i < data.length; i++) {
    						companysAttr.push("<option value='" + data[i]["name"] + "'>" + data[i]["name"] + "</option>");
    					}
    					$("#personNotMoveWindow select[name='companyName']").append(companysAttr.join(""));
    				},
    				jobtypes: function(data) {
    					$("#personNotMoveWindow select[name='jobTypeName']").empty();
    					var companysAttr = [];
    					companysAttr.push("<option value=''>請選擇</option>");
    					for (var i = 0; i < data.length; i++) {
    						companysAttr.push("<option value='" + data[i]["name"] + "'>" + data[i]["name"] + "</option>");
    					}
    					$("#personNotMoveWindow select[name='jobTypeName']").append(companysAttr.join(""));
    				},
    				groups: function(data) {
    					$("#personNotMoveWindow select[name='groupName']").empty();
    					var companysAttr = [];
    					companysAttr.push("<option value=''>請選擇</option>");
    					for (var i = 0; i < data.length; i++) {
    						companysAttr.push("<option value='" + data[i]["name"] + "'>" + data[i]["name"] + "</option>");
    					}
    					$("#personNotMoveWindow select[name='groupName']").append(companysAttr.join(""));
    				}
    			},
    			lowPowers: {
    				companys: function(data) {
    					$("#batteryLowWindow select[name='companyName']").empty();
    					var companysAttr = [];
    					companysAttr.push("<option value=''>請選擇</option>");
    					for (var i = 0; i < data.length; i++) {
    						companysAttr.push("<option value='" + data[i]["name"] + "'>" + data[i]["name"] + "</option>");
    					}
    					$("#batteryLowWindow select[name='companyName']").append(companysAttr.join(""));
    				},
    				jobtypes: function(data) {
    					$("#batteryLowWindow select[name='jobTypeName']").empty();
    					var companysAttr = [];
    					companysAttr.push("<option value=''>請選擇</option>");
    					for (var i = 0; i < data.length; i++) {
    						companysAttr.push("<option value='" + data[i]["name"] + "'>" + data[i]["name"] + "</option>");
    					}
    					$("#batteryLowWindow select[name='jobTypeName']").append(companysAttr.join(""));
    				},
    				groups: function(data) {
    					$("#batteryLowWindow select[name='groupName']").empty();
    					var companysAttr = [];
    					companysAttr.push("<option value=''>請選擇</option>");
    					for (var i = 0; i < data.length; i++) {
    						companysAttr.push("<option value='" + data[i]["name"] + "'>" + data[i]["name"] + "</option>");
    					}
    					$("#batteryLowWindow select[name='groupName']").append(companysAttr.join(""));
    				}
    			},
    			abnormalTags: {
    				companys: function(data) {
    					$("#cardAbnormalWindow select[name='companyName']").empty();
    					var companysAttr = [];
    					companysAttr.push("<option value=''>請選擇</option>");
    					for (var i = 0; i < data.length; i++) {
    						companysAttr.push("<option value='" + data[i]["name"] + "'>" + data[i]["name"] + "</option>");
    					}
    					$("#cardAbnormalWindow select[name='companyName']").append(companysAttr.join(""));
    				},
    				jobtypes: function(data) {
    					$("#cardAbnormalWindow select[name='jobTypeName']").empty();
    					var companysAttr = [];
    					companysAttr.push("<option value=''>請選擇</option>");
    					for (var i = 0; i < data.length; i++) {
    						companysAttr.push("<option value='" + data[i]["name"] + "'>" + data[i]["name"] + "</option>");
    					}
    					$("#cardAbnormalWindow select[name='jobTypeName']").append(companysAttr.join(""));
    				},
    				groups: function(data) {
    					$("#cardAbnormalWindow select[name='groupName']").empty();
    					var companysAttr = [];
    					companysAttr.push("<option value=''>請選擇</option>");
    					for (var i = 0; i < data.length; i++) {
    						companysAttr.push("<option value='" + data[i]["name"] + "'>" + data[i]["name"] + "</option>");
    					}
    					$("#cardAbnormalWindow select[name='groupName']").append(companysAttr.join(""));
    				}
    			},
    			abnormalNodes: {
    				groups: function(data) {
    					$("#nodeAbnormalWindow select[name='groupName']").empty();
    					var groupsAttr = [];
    					groupsAttr.push("<option value=''>請選擇</option>");
    					for (var i = 0; i < data.length; i++) {
    						groupsAttr.push("<option value='" + data[i]["name"] + "'>" + data[i]["name"] + "</option>");
    					}
    					$("#nodeAbnormalWindow select[name='groupName']").append(groupsAttr.join(""));
    				},
    				regions: function(data) {
    					$("#nodeAbnormalWindow select[name='regionName']").empty();
    					var regionsAttr = [];
    					regionsAttr.push("<option value=''>請選擇</option>");
    					for (var i = 0; i < data.length; i++) {
    						regionsAttr.push("<option value='" + data[i]["name"] + "'>" + data[i]["name"] + "</option>");
    					}
    					$("#nodeAbnormalWindow select[name='regionName']").append(regionsAttr.join(""));
    				}
    			},
    			abnormalRefers: {
    				groups: function(data) {
    					$("#pointAbnormalWindow select[name='groupName']").empty();
    					var groupsAttr = [];
    					groupsAttr.push("<option value=''>請選擇</option>");
    					for (var i = 0; i < data.length; i++) {
    						groupsAttr.push("<option value='" + data[i]["name"] + "'>" + data[i]["name"] + "</option>");
    					}
    					$("#pointAbnormalWindow select[name='groupName']").append(groupsAttr.join(""));
    				}
    			}
    		}
        	var initDrawingAllWarns = {
        		drawingAllCompanys: function(data){
        			queryStatementsDrawing.personHelpWarns.companys(data);
        			queryStatementsDrawing.regionControls.companys(data);
        			queryStatementsDrawing.notMoveWarns.companys(data);
        			queryStatementsDrawing.lowPowers.companys(data);
        			queryStatementsDrawing.abnormalTags.companys(data);
        		},
        		drawingAllJobtypes: function(data){
        			queryStatementsDrawing.personHelpWarns.jobtypes(data);
        			queryStatementsDrawing.regionControls.jobtypes(data);
        			queryStatementsDrawing.notMoveWarns.jobtypes(data);
        			queryStatementsDrawing.lowPowers.jobtypes(data);
        			queryStatementsDrawing.abnormalTags.jobtypes(data);
        		},
        		drawingAllGroups: function(data){
        			queryStatementsDrawing.personHelpWarns.groups(data);
        			queryStatementsDrawing.regionControls.groups(data);
        			queryStatementsDrawing.notMoveWarns.groups(data);
        			queryStatementsDrawing.lowPowers.groups(data);
        			queryStatementsDrawing.abnormalNodes.groups(data);
        			queryStatementsDrawing.abnormalRefers.groups(data);
        			queryStatementsDrawing.abnormalTags.groups(data);
        		}
        	}
    		var initExportReport = {
    	    	warning: function() {// 初始化警報報表
    	    		requestDataObj.warn.personHelp(layer, drawingExportReport.personHelp);
    	    		requestDataObj.warn.areaController(layer, drawingExportReport.areaController);
    	    		requestDataObj.warn.notMove(layer, drawingExportReport.notMove);
    	    		requestDataObj.warn.lowPower(layer, drawingExportReport.lowPower);
    	    		requestDataObj.warn.abnormalTag(layer, drawingExportReport.abnormalTag);
    	    		requestDataObj.warn.abnormalRefer(layer, drawingExportReport.abnormalRefer);
    	    		requestDataObj.warn.abnormalBase(layer, drawingExportReport.abnormalBase);
    	    		// 人員求救
    	    		requestDataObj.initUi.companys(layer, initDrawingAllWarns.drawingAllCompanys);
    	    		requestDataObj.initUi.jobtypes(layer, initDrawingAllWarns.drawingAllJobtypes);
    	    		requestDataObj.initUi.groups(layer, initDrawingAllWarns.drawingAllGroups);
    	    		form.render();
    	    }
    	}
		/* =============
		 * 上下班記錄查詢
		 ==============*/
		var onOffDutyStart, onOffDutyEnd, onOffDutyCompany,
		onOffDutyJobType, onOffDutyGroup;
		// 公司名稱
		form.on('select(onOffDutyCompanyFilter)', function(data){
			onOffDutyCompany = data.value;
		});
		// 工種名稱
		form.on('select(onOffDutyJobTypeFilter)', function(data){
			onOffDutyJobType = data.value;
		});
		form.on('select(onOffDutyGroupFilter)', function(data){
			onOffDutyGroup = data.value;
		});
		
		var onOffDutyStart1, onOffDutyEnd1,onOffDutyStart1, 
		onOffDutyEnd1, onOffDutyCompany1,
		onOffDutyJobType1, onOffDutyGroup1;
		// 公司名稱
		form.on('select(onOffDutyCompanyFilter1)', function(data){
			onOffDutyCompany1 = data.value;
		});
		// 工種名稱
		form.on('select(onOffDutyJobTypeFilter1)', function(data){
			onOffDutyJobType1 = data.value;
		});
		form.on('select(onOffDutyGroupFilter1)', function(data){
			onOffDutyGroup1 = data.value;
		});
		
		var onOffDutyStart2, onOffDutyEnd2, onOffDutyCompany2,
		onOffDutyJobType2, onOffDutyGroup2;
		// 公司名稱
		form.on('select(onOffDutyCompanyFilter2)', function(data){
			onOffDutyCompany2 = data.value;
		});
		// 工種名稱
		form.on('select(onOffDutyJobTypeFilter2)', function(data){
			onOffDutyJobType2 = data.value;
		});
		form.on('select(onOffDutyGroupFilter2)', function(data){
			onOffDutyGroup2 = data.value;
		});
		
		var inoutStart, inoutEnd, inoutCompany,inoutJobType, inoutGroup;
		// 公司名稱
		form.on('select(inOutCompanyFilter)', function(data){
			inoutCompany = data.value;
		});
		// 工種名稱
		form.on('select(inOutJobTypeFilter)', function(data){
			inoutJobType = data.value;
		});
		form.on('select(inOutGroupFilter)', function(data){
			inoutGroup = data.value;
		});
		// 獲取當前的時間
		var now = (new Date()).format("yyyy-MM-dd");
		var nowStart = now + " 00:00:00";
		var nowEnd = now + " 23:59:59";
		onOffDutyStart = nowStart;
		onOffDutyEnd = nowEnd;
		//創建日期時間範圍(上下班記錄)
        laydate.render({
            elem: '#accessDateInterval',
            type: 'datetime',
            value: nowStart + " - " + nowEnd,
            range: true,
            done: function(value, date, endDate) {
            	// 設置開始時間/結束時間
            	onOffDutyStart = value.substring(0, 19);
            	onOffDutyEnd = value.substring(21, 21 + 20);
            }
        });
        onOffDutyStart1 = nowStart;
		onOffDutyEnd1 = nowEnd;
        laydate.render({
            elem: '#accessDateInterval1',
            type: 'datetime',
            value: nowStart + " - " + nowEnd,
            range: true,
            done: function(value, date, endDate) {
            	// 設置開始時間/結束時間
            	onOffDutyStart1 = value.substring(0, 19);
            	onOffDutyEnd1 = value.substring(21, 21 + 20);
            }
        });
        
        onOffDutyStart2 = nowStart;
		onOffDutyEnd2 = nowEnd;
        laydate.render({
            elem: '#accessDateInterval2',
            type: 'datetime',
            value: nowStart + " - " + nowEnd,
            range: true,
            done: function(value, date, endDate) {
            	// 設置開始時間/結束時間
            	onOffDutyStart2 = value.substring(0, 19);
            	onOffDutyEnd2 = value.substring(21, 21 + 20);
            }
        });
        
        inoutStart = nowStart;
        inoutEnd = nowEnd;
        laydate.render({
            elem: '#inOutDateInterval',
            type: 'datetime',
            value: nowStart + " - " + nowEnd,
            range: true,
            done: function(value, date, endDate) {
            	// 設置開始時間/結束時間
            	inoutStart = value.substring(0, 19);
            	inoutEnd = value.substring(21, 21 + 20);
            }
        });
		//查詢經過出入口基站上下班記錄數據
        $("#searchCommuteRecord").click(function() {
            //此處查詢到的數據可用於導出報表
        	var name = $("#onOffDutyQuery input[name='name']").val();
        	table.reload('accessTableId',{
        			url: "report/access/search/in-outBase/?name=" + (name || "")+
        				 "&company=" + (onOffDutyCompany || "") + "&jobType=" + (onOffDutyJobType || "") +
        				 "&group=" + (onOffDutyGroup || 0) + "&start=" + (onOffDutyStart || "") + 
        				 "&end=" + (onOffDutyEnd || ""), 
        		    page : { curr : 1 }
        	});
        })
        //查詢沒有經過出入口基站上下班記錄數據
        $("#searchCommuteRecord1").click(function() {
            //此處查詢到的數據可用於導出報表
        	var name = $("#onOffDutyQuery1 input[name='name']").val();
        	table.reload('accessTableId1',{
        			url: "report/access/search/noBase/?name=" + (name || "")+
        				  "&company=" + (onOffDutyCompany1 || "") + "&jobType=" + (onOffDutyJobType1 || "")+
        				  "&group=" + (onOffDutyGroup1 || 0) + "&start=" + onOffDutyStart1 + "&end="+onOffDutyEnd1, 
        		    page : { curr : 1 }
        	});
        })
        //查詢所有上下班記錄數據
        $("#searchCommuteRecord2").click(function() {
            //此處查詢到的數據可用於導出報表
        	var name = $("#onOffDutyQuery2 input[name='name']").val();
        	table.reload('accessTableId2',{
        			url: "report/access/search/all/?name=" + (name || "")+
        				  "&company=" + (onOffDutyCompany2 || "") + "&jobType=" + (onOffDutyJobType2 || "")+
        				  "&group=" + (onOffDutyGroup2 || 0) + "&start=" + onOffDutyStart2 + "&end="+onOffDutyEnd2, 
        		    page : { curr : 1 }
        	});
        })
        // 查詢所有進出記錄數據
        $("#searchInOutRecord").click(function() {
            //此處查詢到的數據可用於導出報表
        	var name = $("#inOutQuery input[name='name']").val();
        	table.reload('in-OutTableId',{
        			url: "in-out/record/search/?name=" + (name || "")+
        				  "&company=" + (inoutCompany || "") + "&jobType=" + (inoutJobType || "")+
        				  "&group=" + (inoutGroup || 0) + "&start=" + inoutStart + "&end="+inoutEnd, 
        		    page : { curr : 1 }
        	});
        })
        //導出上下班記錄報表
        $('#exportCommuteRecord').click(function() {
        	// 導出所有的數據
        	var name = $("#onOffDutyQuery input[name='name']").val();
        	var searchUrl = "report/access/print/in-outBase/?name=" + (name || "") +
			  				"&company=" + (onOffDutyCompany || "") + "&jobType=" + (onOffDutyJobType || "") +
			  				"&group=" + (onOffDutyGroup || 0) + "&start=" + onOffDutyStart + "&end="+onOffDutyEnd;
        	requestDataObj.printerTable(table, layer, searchUrl ,'accessTableId');
		})
		$('#exportCommuteRecord1').click(function() {
        	// 導出所有的數據
        	var name = $("#onOffDutyQuery1 input[name='name']").val();
        	var searchUrl = "report/access/print/noBase/?name=" + (name || "") +
			  				"&company=" + (onOffDutyCompany1 || "") + "&jobType=" + (onOffDutyJobType1 || "") +
			  				"&group=" + (onOffDutyGroup1 || 0) + "&start=" + onOffDutyStart1 + "&end="+onOffDutyEnd1;
        	requestDataObj.printerTable(table, layer, searchUrl ,'accessTableId1');
		})
		$('#exportCommuteRecord2').click(function() {
        	// 導出所有的數據
        	var name = $("#onOffDutyQuery2 input[name='name']").val();
        	var searchUrl = "report/access/print/all/?name=" + (name || "") +
			  "&company=" + (onOffDutyCompany2 || "") + "&jobType=" + (onOffDutyJobType2 || "") +
			  "&group=" + (onOffDutyGroup2 || 0) + "&start=" + onOffDutyStart2 + "&end="+onOffDutyEnd2;
        	requestDataObj.printerTable(table, layer, searchUrl ,'accessTableId2');
		})
		$('#exportinOutRecord').click(function() {
        	// 導出所有的數據
        	var name = $("#inOutQuery input[name='name']").val();
        	var searchUrl = "in-out/record/print/?name=" + (name || "")+
			  "&company=" + (inoutCompany || "") + "&jobType=" + (inoutJobType || "")+
			  "&group=" + (inoutGroup || 0) + "&start=" + inoutStart + "&end="+inoutEnd;
        	requestDataObj.printerTable(table, layer, searchUrl ,'in-OutTableId');
		})
		/* =============
		 * 警告訊息查詢
		 ==============*/
		var startTime = nowStart, 
			endTime = nowEnd, 
			personHelpCompany,
			personHelpJobType, 
			personHelpGroup, 
			printerData;
        // 選擇時間段
        laydate.render({
            elem: '#personForHelpDateInterval',
            type: 'datetime',
            value: nowStart + " - " + nowEnd,
            range: true,
            done: function(value, date, endDate) {
            	startTime = value.substring(0, 19);
            	endTime = value.substring(21, 21 + 20);
            }
        });
        // 公司名稱
		form.on('select(personHelpCompanyFilter)', function(data){
			personHelpCompany = data.value;
		});
		// 工種名稱
		form.on('select(personHelpJobTypesFilter)', function(data){
			personHelpJobType = data.value;
		});
		form.on('select(personHelpGroupsFilter)', function(data){
			personHelpGroup = data.value;
		});
		//查詢人員求救數據
        $("#searchPersonForHelp").off("click").click(function() {
        	warnTotal.personHelp(queryWarns.personnelHelp);
        })
        //導出人員求救報表
        $('#exportPersonForHelp').click(function() {
			var searchUrl = "report/personhelp/print?" + urlParams.personnelHelp();
			requestDataObj.printer(layer, searchUrl, createStatisticTable.personnelHelp);
		})
		/*======區域管制======*/
		var regionControlTable, 
			regionControlCompany, 
			regionControlJobType, 
			regionControlGroup, 
			regionControlPrinterData, 
			regionControlStart = nowStart, 
			regionControlEnd = nowEnd;
		//創建日期時間範圍(區域管制)
		laydate.render({
		    elem: '#regionControlDateInterval',
		    type: 'datetime',
		    value: nowStart + " - " + nowEnd,
		    range: true,
		    done: function(value, date, endDate) {
		    	regionControlStart = value.substring(0, 19);
		    	regionControlEnd = value.substring(21, 21 + 20);
		    }
		});
		// 公司名稱
		form.on('select(regionControlCompanyFilter)', function(data){
			regionControlCompany = data.value;
		});
			// 工種名稱
		form.on('select(regionControlJobTypeFilter)', function(data){
			regionControlJobType = data.value;
		});
			// 組別
		form.on('select(regionControlGroupFilter)', function(data){
			regionControlGroup = data.value;
		});
		//查詢區域管制數據
		$("#searchRegionControl").click(function() {
		    //此處查詢到的數據可用於導出報表
			warnTotal.areaControl(queryWarns.areaControl);
		})
		//導出區域管制報表
		$('#exportRegionControl').click(function() {
			var searchUrl = "report/regionControl/print?" + urlParams.areaControl();
			requestDataObj.printer(layer, searchUrl , createStatisticTable.areaControl);
		})
		/*======人員未移動======*/
		//創建日期時間範圍(人員未移動)
		var notMoveCompany, notMoveJobType, notMoveGroup, 
		notMovePrinterData, 
		notMoveStart = nowStart, 
		notMoveEnd = nowEnd;
		// 公司名稱
		form.on('select(notMoveCompanyFilter)', function(data){
			notMoveCompany = data.value;
		});
		// 工種名稱
		form.on('select(notMoveJobTypeFilter)', function(data){
			notMoveJobType = data.value;
		});
		// 組別
		form.on('select(notMoveGroupFilter)', function(data){
			notMoveGroup = data.value;
		});
		laydate.render({
		    elem: '#personNotMoveDateInterval',
		    type: 'datetime',
		    value: nowStart + " - " + nowEnd,
		    range: true,
		    done: function(value, date, endDate) {
		    	notMoveStart = value.substring(0, 19);
		    	notMoveEnd = value.substring(21, 21 + 20);
		    }
		});
		//查詢人員未移動數據
		$("#searchPersonNotMove").click(function() {
			// 获取当前分页的
			warnTotal.notMove(queryWarns.nowMove);
		})
		//導出人員未移動報表
		$('#exportPersonNotMove').click(function() {
			var searchUrl = "report/notmove/print?" + urlParams.notMove();
			requestDataObj.printer(layer, searchUrl , createStatisticTable.notMove);
		})
		
		/*======電量不足======*/
		var lowPowerCompany, 
		lowPowerJobType, 
		lowPowerGroup, 
		lowPowerPrinterData, 
		lowPowerStart = nowStart, 
		lowPowerEnd = nowEnd;
		// 公司名稱
		form.on('select(lowPowerCompanyFilter)', function(data){
			lowPowerCompany = data.value;
		});
		// 工種名稱
		form.on('select(lowPowerJobTypeFilter)', function(data){
			lowPowerJobType = data.value;
		});
		// 組別
		form.on('select(lowPowerGroupFilter)', function(data){
			lowPowerGroup = data.value;
		});
		//創建日期時間範圍(電量不足)
		laydate.render({
		    elem: '#batteryLowDateInterval',
		    type: 'datetime',
		    value: nowStart + " - " + nowEnd,
		    range: true,
		    done: function(value, date, endDate) {
		    	lowPowerStart = value.substring(0, 19);
		    	lowPowerEnd = value.substring(21, 21 + 20);
		    }
		});
		//查詢電量不足數據
		$("#searchBatteryLow").click(function() {
			warnTotal.lowPower(queryWarns.lowPower);
		})
		//導出電量不足報表
		$('#exportBatteryLow').click(function() {
			var searchUrl = "report/lowpower/print?" + urlParams.lowPower();
			requestDataObj.printer(layer, searchUrl , createStatisticTable.lowPower);
		})
		/*======卡片異常======*/
		var abnormalTagCompany, 
		abnormalTagJobType,
		abnormalTagGroup, 
		abnormalTagPrinterData, 
		abnormalTagStart = nowStart, 
		abnormalTagEnd = nowEnd;
		// 公司名稱
		form.on('select(abnormalCompanyFilter)', function(data){
			abnormalTagCompany = data.value;
		});
		// 工種名稱
		form.on('select(abnormalJobTypeFilter)', function(data){
			abnormalTagJobType = data.value;
		});
		// 組別
		form.on('select(abnormalGroupFilter)', function(data){
			abnormalTagGroup = data.value;
		});
		//創建日期時間範圍(卡片異常)
		laydate.render({
		    elem: '#cardAbnormalDateInterval',
		    type: 'datetime',
		    value: nowStart + " - " + nowEnd,
		    range: true,
		    done: function(value, date, endDate) {
		    	abnormalTagStart = value.substring(0, 19);
		    	abnormalTagEnd = value.substring(21, 21 + 20); 
		    }
		});
		//查詢卡片異常數據
		$("#searchCardAbnormal").click(function() {
			warnTotal.abnormalTag(queryWarns.abnormalTag);
		})
		//導出卡片異常報表
		$('#exportCardAbnormal').click(function() {
			var searchUrl = "report/abnormaltag/print?" + urlParams.abnormalTag();
			requestDataObj.printer(layer, searchUrl , createStatisticTable.abnormalTag);
		})
		/*======節點異常======*/
		var abnormalNodeGroup, 
			abnormalNodePrinterData, 
			abnormalNodeStart = nowStart, 
			abnormalNodeEnd = nowEnd;
		// 工地
		form.on('select(abnormalNodeGroupFilter)', function(data){
			abnormalNodeGroup = data.value;
		});
		//創建日期時間範圍(節點異常)
		laydate.render({
		    elem: '#nodeAbnormalDateInterval',
		    type: 'datetime',
		    value: nowStart + " - " + nowEnd,
		    range: true,
		    done: function(value, date, endDate) {
		    	abnormalNodeStart = value.substring(0, 21);
		    	abnormalNodeEnd = value.substring(21, 21 + 20);
		    }
		});
		//查詢節點異常數據
		$("#searchNodeAbnormal").click(function() {
			warnTotal.abnormalNode(queryWarns.abnormalNode);
		})
		//導出節點異常報表
		$('#exportNodeAbnormal').click(function() {
			var searchUrl = "report/abnormalnode/print?" + urlParams.abnormalNode();
			requestDataObj.printer(layer, searchUrl , createStatisticTable.abnormalNode);
		})
		/*======參考點異常======*/
		var abnormalReferGroup, 
			abnormalReferPrinterData, 
			abnormalReferStart = nowStart, 
			abnormalReferEnd = nowEnd;
		// 工地
		form.on('select(abnormalReferGroupFilter)', function(data){
			abnormalReferGroup = data.value;
		});
		//創建日期時間範圍(參考點異常)
		laydate.render({
		    elem: '#pointAbnormalDateInterval',
		    type: 'datetime',
		    value: nowStart + " - " + nowEnd,
		    range: true,
		    done: function(value, date, endDate) {
		    	abnormalReferStart = value.substring(0, 19);
		    	abnormalReferEnd = value.substring(21, 21 + 20); 
		    }
		});
		//查詢參考點異常數據
		$("#searchPointAbnormal").click(function() {
			warnTotal.abnormalRefer(queryWarns.abnormalRefer);
		})
		//導出參考點異常報表
		$('#exportPointAbnormal').click(function() {
			var searchUrl = "report/abnormalrefer/print?" + urlParams.abnormalRefer();
			requestDataObj.printer(layer, searchUrl , createStatisticTable.abnormalRefer);
		})
		/* =============
		 * 人員操作記錄
		 ==============*/
		var sStart = nowStart, sEnd = nowEnd;
		//創建日期時間範圍(人員操作記錄)
        laydate.render({
            elem: '#logPeriod',
            type: 'datetime',
            value: nowStart + " - " + nowEnd,
            range: true,
            done: function(value, date, endDate) {
            	sStart = value.substring(0, 19);
            	sEnd = value.substring(21, 21 + 20);
            }
        });
		//查詢人員操作記錄數據
        $("#searchLog").click(function() {
        	var searchUrl = "report/operation/search?"
        	var userName = $("#logSearchContent input[name='userName']").val();
        	if(userName){
        		searchUrl += ("userName="+userName+"&");
        	}
        	if(sStart){
        		searchUrl += ("start="+sStart+"&");
        	}
        	if(sEnd){
        		searchUrl += ("end="+sEnd+"&");
        	}
        	table.reload('logTableId', {url: searchUrl, page : {
				curr : 1
			}});
		})
        //導出人員操作記錄報表
        $('#exportLog').click(function() {
        	var searchUrl = "report/operation/print?"
            var userName = $("#logSearchContent input[name='userName']").val();
            if(userName){
            	searchUrl += ("userName="+userName+"&");
            }
            if(sStart){
            	searchUrl += ("start="+sStart+"&");
            }
            if(sEnd){
            	searchUrl += ("end="+sEnd+"&");
            }
            requestDataObj.printerTable(table, layer, searchUrl ,'logTableId');
        })
        /*
         * 總工時統計
         */
        // 监听公司
        var totalHoursCompany;
        form.on('select(totalHoursCompanyFilter)', function(data){
        	totalHoursCompany = data.value;
        });
        // 监听工种
        var totalHoursJobType;
        form.on('select(totalHoursJobTypeFilter)', function(data){
        	totalHoursJobType = data.value;
        })
        // 监听工地
        var totalHoursGroup;
        form.on('select(totalHoursGroupFilter)', function(data){
        	totalHoursGroup = data.value;
        })
        // 监听选择的开始时间与结束时间
        var totalHoursStart = nowStart, 
        	totalHoursEnd = nowEnd;
        laydate.render({
            elem: '#totalHoursDateInterval',
            type: 'datetime',
            value: nowStart + " - " + nowEnd,
            range: true,
            done: function(value, date, endDate) {
            	totalHoursStart = value.substring(0, 19);
            	totalHoursEnd = value.substring(21, 21 + 20);
            }
        });
        // 工时统计查询
        $("#searchTotalHours").off("click").click(function(){
        	var name = $("#totalHoursWorked input[name='name']").val();
        	var searchUrl = "report/totalHours/search/?" + (name?("name=" + name + "&"):'') + 
        			(totalHoursCompany?("companyNo="+totalHoursCompany + "&"):'') + 
        			(totalHoursJobType?("jobTypeNo="+totalHoursJobType + "&"):'') + 
        			(totalHoursGroup?("groupId="+totalHoursGroup + "&"):'') + 
        			(totalHoursStart?("start="+totalHoursStart + "&"):'') + 
        			(totalHoursEnd?("end="+totalHoursEnd):'');
        	table.reload('totalHoursTable', { url: searchUrl, page : {
        		curr : 1
        	}});
        })
        // 工时统计打印
        $("#exportTotalHours").off("click").click(function(){
        	var name = $("#totalHoursWorked input[name='name']").val();
        	var searchUrl = "report/totalHours/print/?" + (name?("name=" + name + "&"):'') + 
        			(totalHoursCompany?("companyNo="+totalHoursCompany + "&"):'') + 
        			(totalHoursJobType?("jobTypeNo="+totalHoursJobType + "&"):'') + 
        			(totalHoursGroup?("groupId="+totalHoursGroup + "&"):'') + 
        			(totalHoursStart?("start="+totalHoursStart + "&"):'') + 
        			(totalHoursEnd?("end="+totalHoursEnd):'');
        	requestDataObj.printerTable(table, layer, searchUrl ,'totalHoursTable');
        })
        //報表項目切換
        $('#reportClassify>li').off("click").click(function() {
            var index = $(this).index();
            $('#reportClassify>li').removeClass("activeSet").eq(index).addClass("activeSet");
            $("#reportContent>li").hide().eq(index).show();
            var type = $(this).attr("type");
            switch (type) {
                case '0':
                    table.reload('accessTableId');
                    table.reload('accessTableId1');
                    break;
                case '1':
                	 table.reload('in-OutTableId');
                	break;
                case '2':// 初始化當前表格內容
                	initExportReport.warning();
                	break;
                case '3':
                    table.reload('logTableId');
                    break;
            }
        })
		element.on('tab(accessStaticFilter)', function(data){

		});
		//彈出警告類型窗口
		function warnTypeWindow($dom,warnType){
			layer.open({
				type: 1,
				content: $dom,
				maxmin: true,
				area: ['930px','100%'],
				offset: ['0px','263px'],
				title: '<strong>'+warnType+'</strong> 查詢',
				success: function(){ //窗口彈出後的回調
				}
			})
		}
		$('#warnType>dd').click(function(){
			var index = $(this).index();
			switch(index){
				case 0: //人員求救
					warnTypeWindow($('#personForHelpWindow'), '人員求救');
					break;
				case 1: //區域管制
					warnTypeWindow($('#regionControlWindow'),'區域管制');
				break;
				case 2: //人員未移動
					warnTypeWindow($('#personNotMoveWindow'),'人員未移動');
				break;
				case 3: //電量不足
					warnTypeWindow($('#batteryLowWindow'),'電量不足');
				break;
				case 4: //卡片異常
					warnTypeWindow($('#cardAbnormalWindow'),'卡片異常');
				break;
				case 6: //節點異常
					warnTypeWindow($('#nodeAbnormalWindow'),'節點異常');
				break;
				case 5: //參考點異常
					warnTypeWindow($('#pointAbnormalWindow'),'參考點異常');
				break;
			}
		})
        requestDataObj.initUi.companys(layer, drawingExportReport.on_OffDuty.companys);
        requestDataObj.initUi.jobtypes(layer, drawingExportReport.on_OffDuty.jobTypes);
        requestDataObj.initUi.groups(layer, drawingExportReport.on_OffDuty.groups);
    })
});