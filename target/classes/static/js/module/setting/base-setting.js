var base_SettingObj = {
	// 初始化組群設置
	initGroup: function() {
		var userName = parent.$("div.global_UWB p.user").text();
		// 組別表單編輯
		var groupFormEdit = {
			empty: function() {
				$("#editGroupForm input").val("");
				$("#editGroupForm textarea").val("");
				$("#editGroupForm").removeAttr("groupId");
			},
			init: function(data) {
				$("#editGroupForm input[type='text']").val(data.name);
				$("#editGroupForm textarea").val(data.des);
				$("#editGroupForm").attr("groupId", data.id);
			}
		}
		// 初始化編輯表單
		layui.use([ 'table', 'layer', 'element', 'form' ], function() {
			var table = layui.table;
			var layer = layui.layer;
			var form = layui.form;
			var oldGroup = null;
			var editWin = null;
			// 創建組別表格
			var groupTable = table.render({
				id : "groupTable",
				elem : $("#group"),
				height : "full-23",
				toolbar : "#addGroupModule",
				defaultToolbar : [ 'filter', 'exports' ],
				url : "group/page/",
				page : true,
				cols : [ [ // 表頭
				{
					field : "name",
					title : "組別名稱",
					sort : true,
					align : 'center'
				}, {
					field : "des",
					title : "組別描述",
					align : 'center'
				}, {
					title : "操作",
					toolbar : $("#groupManipulation"),
					align : 'center'
				} ] ],
				parseData : function(data) {
					
				}
			})
			// 監聽組別操作按鈕
			table.on('tool(operateGroup)', function(obj) {
				var data = obj.data;
				if (obj.event == 'edit') {
					oldGroup = data;
					groupFormEdit.init(data)
					editWin = layer.open({
						title : "編輯",
						type : 1,
						area : [ "auto", '330px' ],
						content : $('#editGroupForm')
					});
				}
				if (obj.event == 'del') { // 刪除
					layer.confirm('確定刪除名稱<strong>[ ' + data.name + ' ]</strong>嗎？',
						function(index) {
							var groupId = data.id;
							$.ajax({type: "DELETE",url: "group/" + data.id, dataType: "JSON",contentType: "application/json;charset=utf-8", 
									success: function(result){
										if(result.code == 600){
											layer.msg("刪除【name : " + data.name + "】組別成功...", {icon: 6});
											// 重新刷新表格
											groupTable.reload();
										} else {
											layer.msg(result.msg, {icon: 5});
										}
									},
									error: function(result){
										layer.alert("刪除【" + JSON.stringify(data) + "】出現異常...", {icon: 2});
									}
							})
					})
				}
			})
			// 添加組別
			table.on('toolbar(operateGroup)', function(obj) {
				if (obj.event == 'add') {
					groupFormEdit.empty();
					editWin = layer.open({
						title : "添加",
						type : 1,
						area : [ '500px', '300px' ],
						content : $('#editGroupForm')
					});
				}
				if(obj.event == 'refresh'){
					groupTable.reload({url: "group/page/",page:{curr: 1}});
				}
				if(obj.event == 'search'){
					if ($("#groupText").val()) {
						groupTable.reload({
							url : "group/page/key_name/",
							where : {
								name : $("#groupText").val()
							},
							page : {
								curr : 1
							}
						})
					} else {
						layer.msg("搜索內容為空", {
							icon : 7
						});
					}
				}
			});
			form.on('submit(groupSubmit)', function(data) {
				// 判斷當前是添加還是修改
				var groupId = $("#editGroupForm").attr("groupId");
				if (groupId) {// 修改
					oldGroup["name"] = data.field["name"];
					oldGroup["des"] = data.field["des"];
					oldGroup['updateName'] = userName;
					// 更新函數
					$.ajax({type: "POST", url: "group/" + oldGroup.id, async: false, dataType: "JSON",contentType: "application/json;charset=utf-8", data: JSON.stringify(oldGroup),
						success: function(result){
							if(result.code == 600){
								layer.msg("更新組別訊息成功...", {icon: 6})
								// 重新刷新表格
								groupTable.reload();
								// 關閉編輯框
								layer.close(editWin);
							} else {
								layer.msg(result.msg, {icon: 5})
							}
						}, 
						error: function(result){
							layer.alert("更新【" + JSON.stringify(oldGroup) + "】出現異常！", {icon: 2});
						}
					})
				} else {// 保存
					data.field['createName'] = userName;
					$.ajax({ type:"PUT", url:"group/", async: false, dataType : "JSON",contentType : "application/json;charset=utf-8",data: JSON.stringify(data.field),
							 success: function(result){
								 if(result.code == 600){
									 layer.msg("添加組別訊息成功...",{icon: 6});
									 // 重新加載組別表格
									 groupTable.reload();
									 // 關閉組別表單
									 layer.close(editWin);
								 } else {
									 layer.msg(result.msg, {icon: 5});
								 }
							 }, 
							 error: function(result){
								 layer.alert("添加【" + JSON.stringify(data.field) + "】出現異常！", {icon: 2});
						     }
					})
				}
				return false;
			});
		})
	},
	// 初始化區域訊息
	initRegion: function() {
		var userName = parent.$("div.global_UWB p.user").text();
		// 區域表單編輯
		var regionFormEdit = 
		{ 
			initGroups: function(){
				$.ajax({url : "group/all/", type : "GET",dataType : "json",async: false,
					success : function(result) {
							if(result.code == 600) {// 獲取成功
								// 將組別選項更新
								$("#regionGroupsSelect").empty();
								var sOptions = [];
								for (var i = 0; i < result.data.length; i++) {
									sOptions.push("<option value='" + result.data[i].id + "'>"
											      + (result.data[i].name || result.data[i].id) + 
											      "</option>");
								}
								$(sOptions.join("")).appendTo("#regionGroupsSelect");
							} else {
								layer.msg(result.msg, {icon: 5})
							}
					},
					error : function(result) {
							layer.alert("獲取所有的組別訊息出現異常! ", {icon: 2});
					}
				})
			},
			empty: function() {
				$("#editRegionForm").removeAttr("regionId");
				$("#editRegionForm input[type='text']").val("");
				$("#regionImagePreview").removeAttr("imagePath");
				$('#regionImagePreview').empty();
			},
			init: function(data) {
				// 更新區域名稱
				$("#editRegionForm").attr("regionId", data.id);
				$("#editRegionForm div.layui-form-item:first input[type='text']").val(
							data.name);
				$("#editRegionForm div.layui-form-item:eq(2) input[type='text']").val(
							data.width);
				$("#editRegionForm div.layui-form-item:eq(3) input[type='text']").val(
							data.height);
				// 更新組別名稱data.groupName
				$("#editRegionForm select>option[value='" + data.groupId + "']").prop("selected", true);
				// 查看區域地圖
				$("#regionImagePreview").empty();
				$("#regionImagePreview").attr("imagePath", data.image);
				$("#regionImagePreview").append("<img src='image/" + data.image + "' alt='" + data.image + "' class='layui-upload-img'/>");
			}
		}
		layui.use([ 'table', 'layer', 'form', 'upload' ], function() {
			var table = layui.table;
			var layer = layui.layer;
			var form = layui.form;
			var upload = layui.upload;
			var editWinIndex = null, oldRegion = null;
			// 創建組別表格
			var regionTable = table.render({
				id : "regionTable",
				elem : $("#regionTable"),
				height : "full-23",
				toolbar : "#addRegionModule",
				defaultToolbar : [ 'filter', 'exports' ],
				url : "region/page/",
				page : true,
				cols : [ [ // 表頭
				{
					field : "name",
					title : "區域名稱",
					sort : true,
					align : 'center'
				}, {
					field : "groupName",
					title : "組別/工地",
					templet : "#groupTpl",
					sort : true,
					align : 'center'
				}, {
					title : "操作",
					fixed : "right",
					toolbar : $("#regionManipulation"),
					align : 'center'
				} ] ],
				parseData : function(data) {
					
				}
			})
			// 監聽組別操作按鈕
			table.on('tool(operateRegion)', function(obj) {
				var data = obj.data;
				if (obj.event == 'edit') { // 編輯
					oldRegion = data;
					// 初始化組別訊息
					regionFormEdit.initGroups();
					// 初始化組別表格訊息
					regionFormEdit.init(data);
					// 重載表格訊息
					form.render();
					// 顯示編輯框
					editWinIndex = layer.open({
						title : "編輯組別",
						type : 1,
						area : ['385px', 'auto'],
						content : $('#editRegionForm')
					})
				}
				if (obj.event == 'del') { // 刪除
					layer.confirm('確定刪除名稱<strong>[ ' + data.name + ' ]</strong>嗎？',
						function(index) {
							$.ajax({type: "DELETE",url: "region/" + data.id, dataType: "JSON", 
								success: function(result){
									if(result.code == 600){
										layer.msg("刪除【name: " + data.name + " 】成功!",{icon: 6});
										// 刷新表格
										regionTable.reload();
									} else {
										layer.msg(result.msg, {icon: 5})
									}
								},
								error: function(result){
									layer.alert("刪除【name: " + data.name + "】出現異常...",{icon: 2});
								}
						})
					})
				}
			})
			// 添加組別
			table.on('toolbar(operateRegion)', function(obj) {
				if (obj.event == 'add') {
					// 獲取組別訊息添加到編輯欄的下拉菜單
					// 初始化組別訊息
					regionFormEdit.initGroups();
					// 初始化組別表格訊息
					regionFormEdit.empty();
					// 重載表格訊息
					form.render();
					// 顯示編輯框
					editWinIndex = layer.open({title : "添加組別", type : 1, 
						area : ['385px', 'auto'], content : $('#editRegionForm')
					})
				}
				
				if(obj.event == 'refresh'){// 刷新
					regionTable.reload({url: "region/page/", 
						page: {curr: 1}})
				}
				if(obj.event == 'search'){
					if ($("#regionText").val()) {
						regionTable.reload({
							url : "region/page/key_name/",
							where : {
								name : $("#regionText").val()
							},
							page : {
								curr : 1
							}
						})
					} else {
						layer.msg("搜索內容為空", {
							icon : 7
						});
					}
				}
			})
			form.on('submit(regionSubmit)',
				function(data) {
					delete data.field.file;
					data.field["image"] = $("#regionImagePreview").attr("imagePath");
					// 判斷當前是添加還是修改
					var regionId = $("#editRegionForm").attr("regionId");
					if (regionId) {// 修改
						oldRegion["name"] = data.field["name"];
						oldRegion["groupId"] = data.field["groupId"];
						oldRegion["image"] = data.field["image"];
						oldRegion["width"] = data.field["width"];
						oldRegion["height"] = data.field["height"];
						oldRegion["updateName"] = userName;
						$.ajax({type: "POST", url: "region/" + oldRegion["id"], dataType: "JSON", data: JSON.stringify(oldRegion), contentType: "application/json;charset=utf-8",
							   success: function(result) {
								   if(result.code == 600) {
									   layer.msg("更新區域訊息成功...", {icon: 6});
									   // 重新加載表格
									   regionTable.reload();
									   // 關閉區域表單編輯框
									   layer.close(editWinIndex);
								   } else {
									   layer.msg(result.msg, {icon: 5});
								   }
							   },
							   error: function(result) {
								   layer.alert("更新區域訊息【" + JSON.stringify(oldRegion) + "】出現異常...", {icon: 2});
							   }
						})
					} else {// 增加
						data.field["createName"] = userName;
						$.ajax({type: "PUT", url: "region/", dataType: "JSON", data: JSON.stringify(data.field), contentType: "application/json;charset=utf-8",
							success: function(result){
							if(result.code == 600) {
								layer.msg("更新區域訊息成功...", {icon: 6});
									// 重新加載表格
								regionTable.reload();
								// 關閉區域表單編輯框
								layer.close(editWinIndex);
							} else {
								layer.msg(result.msg, {icon: 5});
							}
						},
						error: function(result){
								layer.alert("增加區域訊息【" + JSON.stringify(data.field) + "】出現異常...", {icon: 2});
						}
					})
				}
				return false;
			});
		})
	},
	// 初始化基站訊息
	initNode: function() {
		var editString = "編輯", addString = "添加";
		var userName = parent.$("div.global_UWB p.user").text();
		/**
		 * 基站表單編輯對象
		 */
		var nodeFormEdit = {
			init: function(data, form) {// 初始化基站表單訊息
				$("#nodeEditForm").attr({"NODE_X":data.mapX, "NODE_Y":data.mapY, "REGION_ID": data.regionId})
				$("#nodeEditForm input[type='text']:first").val(data.id);
				$("#nodeEditForm input[type='text']:last").val(data.name);
				$("#nodeEditForm select>option:eq(" + data.type + ")").prop("selected",true);
				form.render("select");
			},
			empty: function(form) {// 清空基站表單訊息
				$("#nodeEditForm input[type='text']").val("");
				// 重新生成表單
				form.render("select");
			}
		}
		var groupRegionSelect = {
			refreshGroupsInit: function(groups, form) {// 刷新組別選擇框
				$("#nodeGroupSelect").empty();
				var groupArr = [];
				for (var i = 0; i < groups.length; i++) {
					groupArr.push("<option value='" + groups[i]["id"] + "'>" + groups[i]["name"] + "</option>");
				}
				$("#nodeGroupSelect").append(groupArr.join(""));
				form.render();
			},
		 	refreshRegionsInit: function(regions, form) {
		 		$("#nodeRegionSelect").empty();
		 		var regArr = [];
		 		for (var i = 0; i < regions.length; i++) {
		 			regArr.push("<option image='" + regions[i]["image"] + "' value='" + regions[i]["id"] + "'>" + regions[i]["name"] + "</option>");
		 		}
		 		$("#nodeRegionSelect").append(regArr.join(""));
		 		form.render();
		 	}
		}
		
		layui.use(['form','element','table'], function() {
			var element = layui.element;
			var form = layui.form;
			var table = layui.table;
			var $selectDom = $("#nodeSelectRegionForm select");
			initGroups();
			var nodeEditWinIndex = null, oldNode = null;
			// 初始化組別訊息
			function initGroups() {
				$.ajax({url : "group/all/", dataType : "json", type : "GET",
					success : function(result) {
						if(result.code == 600) {//組別獲取成功
							// 將當前的組別訊息添加到組別的選擇框中
							groupRegionSelect.refreshGroupsInit(result.data, form)
							initRegions();
						} else {
							layer.msg(result.msg, {type : 5});
						}
					},
					error : function(result) {
						layer.alert("獲取所有的組別訊息時出現異常...", {icon: 2});
					}
				})
			}
			// 根據組別的 ID重新加載組別對應的區域訊息
			var initRegions = function() {
				// 獲取當前選擇的組別ID
				var groupSelectVal = $(
						"#nodeGroupSelect+div.layui-form-select").find(
						"input[type='text']").val();
				// 選擇當前選擇的組別ID
				var groupSelectId = $(
						"#nodeGroupSelect option:contains('" + 
						groupSelectVal + "')").attr("value");
				// 清除Map中所有的基站訊息
				$("#eNodeBMap>div.eNodeB").remove();
				// 重新刷新表格
				table.reload("addNodeTable",{url: 'enode/page/0'});
				if(groupSelectId){
					$.ajax({url : "region/groupId/" + groupSelectId,type : "GET", dataType : "json",
						success : function(result) {
							if(result.code == 600) {
								// 將區域訊息添加到下拉區域列表框中
								groupRegionSelect.refreshRegionsInit(result.data, form);
								// 根據當前選擇的區域顯示地圖訊息
								var regionSelectVal = $(
								"#nodeRegionSelect+div.layui-form-select").find(
								"input[type='text']").val();
								// 獲取選擇的區域ID和地圖路徑
								var regionSelectId = $(
										"#nodeRegionSelect option:contains('" + 
										regionSelectVal + "')").attr("value");
								var regionSelectImage =  $(
										"#nodeRegionSelect option:contains('" + 
										regionSelectVal + "')").attr("image");
								// 先綁定地圖，當地圖加載完以後，我們需要重新刷新基站/參考點訊息到地圖上
								$("#eNodeBMap > img")[0].onload = function(){
									requestMapNodesFun();
								}
								if(regionSelectImage) {// 改變區域地圖
									// 重新刷新表格
									table.reload("addNodeTable",{url: "enode/page/" + regionSelectId});
									$("#eNodeBMap > img").attr("src","image/" + regionSelectImage);
								} else {// 設置區域地圖為空地圖
									$("#eNodeBMap > img").attr("src","img/5c7fe97c-255b-4baf-b804-083e4fc8f9d3.png");
								}
							} else {
								layer.alert(result.msg,{type: 5});
							}
						},
						error : function(result) {
							console.error("獲取所有的區域訊息時出現異常...");
							layer.msg("獲取所有的區域訊息出現異常...",{type: 5});
						}
					})
				}
				var refreshRegionsSelect = function(regions) {
					$("#nodeRegionSelect").empty();
					var regArr = [];
					for (var i = 0; i < regions.length; i++) {
						regArr.push("<option image='" + regions[i]["image"] + "' value='" + regions[i]["id"] + "'>" + regions[i]["name"] + "</option>");
					}
					$("#nodeRegionSelect").append(regArr.join(""));
				}
			}
			//第壹個實例
			var nodeTable = table.render({
				  id: "addNodeTable",
			    elem: '#eNodeBTable',
			    toolbar : "#addNodeModule",
			    height: "full-114",
			    url: 'enode/page/0',
			    page: true,
			    cols: [[ //表頭
			       {field: 'id', title: '設備ID', sort: true},
			       {field: 'name', title: '名稱', sort: true},
			       {field: 'typeName', title: '類型', sort: true},
			       {fixed: 'right', title:'操作', toolbar: '#eNodeBTableTool', align:"center"}
			    ]]
			 });
			element.on("tab(nodeNav)",function(data){
				var index = data.index;
				if(index == 0){// 顯示地圖
					$("#eNodeBMapDiv").show();
					$("#eNodeBTableDiv").hide();
				}else if(index == 1){// 顯示列表
					var regionSelectVal = $("#nodeRegionSelect+div.layui-form-select").find("input[type='text']").val();
					var  regionSelectId = $("#nodeRegionSelect option:contains('" + regionSelectVal + "')").attr("value");
					// 更新表格的url
					if(regionSelectId) {
						table.reload("addNodeTable",{url:"enode/page/" + regionSelectId})
					}
					$("#eNodeBMapDiv").hide();
					$("#eNodeBTableDiv").show();
				}
			})
			table.on("tool(eNodeBTable)",function(obj){
				var data = obj.data;
				if (obj.event === 'del') {
					layer.confirm('確定刪除名稱<strong> ' + data.name + ' </strong>麽?', function(index) {
						$.ajax({type: "DELETE", url: "enode/" + data.id, dataType: "JSON", 
							    success: function(result){
							    	if(result.code == 600){
							    		layer.msg("刪除基站【name : " + data.name + "】成功...", {icon: 6});
								    	// 重新加載基站表格
							    		table.reload("addNodeTable");
										// 重新畫地圖區域
										drawMapFun();
							    	} else {
							    		layer.msg(result.msg, {icon: 5});
							    	}
							    },
							    error: function(result){
							    	layer.alert("刪除基站【name : " + data.name + "】出現異常...", {icon: 2});
							    }
						});
					});
				} else if (obj.event === 'edit') {
					nodeFormEdit.init(data, form);
					oldNode = data;
					// 編輯
					$("#nodeEditForm").attr("type", "edit");
					
					nodeEditWinIndex = layer.open({
						type : 1,
						title : editString,
						content : $("#nodeEditForm"),
						area : [ "400px", "300px" ]
					});
				}
			})
			
			table.on('toolbar(eNodeBTable)', function(obj) {
				if(obj.event == 'search'){
					if($("#nodeText").val()){
						var regionSelectVal = $("#nodeRegionSelect+div.layui-form-select").find("input[type='text']").val();
						var  regionSelectId = $("#nodeRegionSelect option:contains('" + regionSelectVal + "')").attr("value");
						// 更新表格的url
						if(regionSelectId) {
							nodeTable.reload({
								url:"enode/page/key_name/" + regionSelectId,
								where:{
									name: $("#nodeText").val() //此處的值是傳入後臺查詢的條件
								},
								page:{
									curr: 1 //重新從第 1 頁開始
								}
							})
						}
					}else{
						layer.msg("搜索內容為空",{icon: 7});
					}
				} else if(obj.event == 'refresh'){
					var regionSelectVal = $("#nodeRegionSelect+div.layui-form-select").find("input[type='text']").val();
					var  regionSelectId = $("#nodeRegionSelect option:contains('" + regionSelectVal + "')").attr("value");
					// 更新表格的url
					if(regionSelectId) {
						nodeTable.reload({url:"enode/page/" + regionSelectId, 
							page:{
								curr: 1 //重新從第 1 頁開始
							}
						})
					}
				}
			})
			form.on('select(nodeGroupSelectFilter)',function(elem) {
				// 更新組別對應的區域選擇框訊息
				initRegions();
			})
			form.on('select(nodeRegionSelectFilter)', function(elem) {
				var regionId = elem.value;
				// 根據區域ID切換不同的區域
				
				var image = $("#nodeRegionSelect").find("option[value='" + regionId + "']").attr("image");
				// 修改地圖
				$("#eNodeBMap > img").attr("src","image/" + image);
				// 查看是更新表格還是地圖
				var mode = $("#nodeTableMapContent li.layui-this").index();
				//  獲取當前的區域ID
				var regionSelectVal = $("#nodeRegionSelect+div.layui-form-select").find("input[type='text']").val();
				var regionSelectId = $("#nodeRegionSelect option:contains('" + regionSelectVal + "')").attr("value");
				if(mode){// 列表模式
					// 更新表格的url
					table.reload("addNodeTable",{url:"enode/page/" + regionSelectId})
				}else{// 地圖模式
					requestMapNodesFun();
				}
			});
			form.on("submit(nodeEditFilter)",function(data){
				data.field["width"] = $("#eNodeBMap").width();
				data.field["height"] = $("#eNodeBMap").height();
				// 判斷當前是編輯還是添加
				var type = $("#nodeEditForm").attr("type");
				if(type == "edit") {// 編輯
					if(oldNode["id"] != data.field["id"]){
						layer.msg("基站設備ID不能修改...", {type: 5 });
						return false;
					}
					oldNode["name"] = data.field["name"];
					oldNode["type"] = data.field["type"];
					oldNode["width"] = data.field["width"];
					oldNode["height"] = data.field["height"];
					oldNode["updateName"] = userName;
					// 更新基站表單
					$.ajax({type: "POST", url: "enode/" + oldNode["id"] ,dataType: "JSON", data: JSON.stringify(oldNode), contentType: "application/JSON;charset=utf-8",
						success: function(result){
							if(result.code == 600){
								layer.msg("更新基站【id : " + oldNode["id"]  + "】成功...",{icon: 6})
								// 重新加載基站表格
								table.reload("addNodeTable");
								// 重新畫地圖區域
								drawMapFun();
								// 關閉基站表單
								layer.close(nodeEditWinIndex);
							} else {
								layer.msg(result.msg, {icon: 5});
							}
						},
						error: function(result){
							layer.alert("更新基站【" + JSON.stringify(oldNode) + "】訊息出現異常...", {icon: 2});
						}
					})
				}else if(type == "add") {// 添加
					// 添加基站訊息
					data.field["regionId"] = $("#nodeEditForm").attr("REGION_ID");
					data.field["mapX"] = $("#nodeEditForm").attr("NODE_X");
					data.field["mapY"] = $("#nodeEditForm").attr("NODE_Y");
					data.field["createName"] = userName;
					// 添加基站
					$.ajax({type: "PUT", url: "enode/" , dataType: "JSON", contentType: "application/json;charset=utf-8", data: JSON.stringify(data.field),
							success: function(result){
								if(result.code == 600){
									layer.msg("添加基站訊息【id: " + data.field["id"] + "】成功...", {icon: 6});
									// 重新加載基站表格
									table.reload("addNodeTable");
									// 重新畫地圖區域
									drawMapFun();
									// 關閉基站表單
									layer.close(nodeEditWinIndex);
								} else {
									layer.msg(result.msg, {icon: 5});
								}
							},
							error: function(result){
								layer.alert("添加基站訊息【" + JSON.stringify(data.field) + "】出現異常...", {icon: 2});
							}
					});
				}
				return false;
			})
			$("#nodeDeleteBtn").click(function(){
				var id = $("#nodeEditForm input[name='id']").val();
				$.ajax({ type : "DELETE", url : "enode/"+id, dataType : "JSON",
						success : function(result) {
							if(result.code == 600){
								layer.msg("刪除基站/參考點【ID:" + id + "】出現成功",{icon: 6});
								layer.closeAll();
								// 重新畫地圖區域
								drawMapFun();
							} else {
								layer.msg(result.msg,{
									icon : 5
								});
							}
						}, error : function(result) {
							layer.msg("刪除基站/參考點【ID:" + id + "】出現異常!",{
								icon: 2
							});
						}
				})
			})
			var requestMapNodesFun = function(){
				var regionSelectVal = $("#nodeRegionSelect+div.layui-form-select").find("input[type='text']").val();
				var regionSelectId = $("#nodeRegionSelect option:contains('" + regionSelectVal + "')").attr("value");
				if(regionSelectId){
					$.ajax({url : "enode/region/" + regionSelectId, type : "GET", dataType : "JSON",
					success : function(result) {
						if(result.code == 600) {
							// 更新地圖區域訊息
							drawMapNodesFun(result.data);
						} else {
							layer.msg("獲取區域【ID: " + regionSelectId
									+ "】的基站訊息失敗...");
						}
					},
					error : function(result) {
						console.error("獲取區域【ID: " + regionSelectId + "】 的基站訊息出現異常...");
						layer.msg("獲取區域【ID: " + regionSelectId
										+ "】的基站訊息出現異常...");
					}
					})
				}
			}
			var drawMapFun = function() {
				// 獲取當前選擇的區域ID
				var regionSelectVal = $("#nodeRegionSelect+div.layui-form-select").find("input[type='text']").val();
				var regionSelectId = $("#nodeRegionSelect option:contains('" + regionSelectVal + "')").attr("value");
				$.ajax({url : "enode/region/" + regionSelectId, type : "GET", dataType : "JSON",
				success : function(result) {
					if(result.code == 600) {
						// 更新地圖區域訊息
						drawMapNodesFun(result.data);
					} else {
						layer.msg("獲取區域【ID: " + regionId + "】失敗...");
					}
				},
				error : function(result) {
					console.error("獲取區域訊息時出現異常...");
					layer.msg("獲取區域訊息時出現異常...", {type: 1});
				}
				})
			}
			// 雙擊地圖，彈出編輯框
			$("#eNodeBMap").off("dblclick").dblclick(function(event){
				// 獲取當前鼠標在地圖上的坐標
				var offsetX = event.offsetX, offsetY = event.offsetY;
				// 獲取當前的區域ID
				var regionSelectVal = $("#nodeRegionSelect+div.layui-form-select").find("input[type='text']").val();
				var regionSelectId = $("#nodeRegionSelect option:contains('" + regionSelectVal + "')").attr("value");
				// 設置基站坐標
				$("#nodeEditForm").attr({"NODE_X":offsetX, "NODE_Y":offsetY, "REGION_ID": regionSelectId});
				
				$("#nodeEditForm").attr("type","add");
				// 清除編輯框中的內容
				nodeFormEdit.empty(form);
				nodeEditWinIndex = layer.open({
					type : 1,
					title : addString,
					content : $("#nodeEditForm"),
					area : [ "400px", "300px" ]
				});
			})
			var mapNodesEventFun = function($mapDiv){
				$mapDiv.find("div.eNodeB").off("dblclick").on("dblclick",function() {
					// 屏蔽掉外面MapDiv的雙擊事件
					return false;
				})
				$mapDiv.find("div.eNodeB").off("click").click(function() {
					if($(this).attr("drag")){
						return false;
					}
					// 編輯表單中的內容初始化，需要我們構建
					var nodeId = $(this).attr("id").replace("PNode", "");
					var nodeName = $(this).attr("pname");
					var nodeType = $(this).attr("ptype");
					var nodeX = parseInt($(this).css("left")) + 19;
					var nodeY = parseInt($(this).css("top")) + 8;
					var regionId = $(this).attr("regionId");
					var createId = $(this).attr("createId");
					var createTime = $(this).attr("createTime");
					var nodeObj = {
						"id" : nodeId,"name" : nodeName,"type" : nodeType,"mapX" : nodeX,"mapY" : nodeY,
						"regionId" : regionId,"createId" : createId,"createTime" : createTime
					};
					oldNode = nodeObj;
					nodeFormEdit.init(nodeObj,form);
					$("#nodeEditForm").attr("type", "edit");
					// 讓表單編輯框可見
					nodeEditWinIndex = layer.open({
						type : 1,
						title : editString,
						content : $("#nodeEditForm"),
						area : [ "400px", "300px" ]
					});
					return false;
				})
			}
			// 畫出地圖模式下的基站訊息
			var drawMapNodesFun = function(nodes) {
				var $mapDiv = $("#eNodeBMap");
				$mapDiv.find("div.eNodeB").remove();
				var eNodeAttr = [];
				for ( var key in nodes) {
					//根據相對寬度計算當前的基站在現在的地圖上的位置
					if(nodes[key]["type"] == 1){// 基站
						eNodeAttr.push("<div id='PNode" + nodes[key]["id"] + "' " +
								"class='eNodeB eRefer' pname='" + nodes[key]["name"] + "' " +
								"ptype='" + nodes[key]["type"] + "' " +
								"nodeX='" + nodes[key]["mapX"] + "' " +
								"nodeY='" + nodes[key]["mapY"] + "' " +
								"regionId='" + nodes[key]["regionId"] + "'" + 
								"createId='" + nodes[key]["createId"] + "'" + 
								"createTime='" + nodes[key]["createTime"] + "'" + 
								"style='left:" + (nodes[key]["mapX"] - 19) + "px;" +
								"top:" + (nodes[key]["mapY"] - 8) + "px;'>" +
								"<span class='layui-badge'>基站</span><span>" + (nodes[key]["name"] || nodes[key]["id"])  + 
								"</span></div>");
					} else if(nodes[key]["type"] == 2){
						eNodeAttr.push("<div id='PNode" + nodes[key]["id"] + "' " +
								"class='eNodeB InOutNodeB' pname='" + nodes[key]["name"] + "' " +
								"ptype='" + nodes[key]["type"] + "' " +
								"nodeX='" + nodes[key]["mapX"] + "' " +
								"nodeY='" + nodes[key]["mapY"] + "' " +
								"regionId='" + nodes[key]["regionId"] + "'" + 
								"createId='" + nodes[key]["createId"] + "'" + 
								"createTime='" + nodes[key]["createTime"] + "'" + 
								"style='left:" + (nodes[key]["mapX"] - 19) + "px;" +
								"top:" + (nodes[key]["mapY"] - 8) + "px;'>" +
								"<span class='layui-badge'>進出</span><span>" + (nodes[key]["name"] || nodes[key]["id"])  + 
								"</span></div>");
					} 
					else{// 參考點
						eNodeAttr.push("<div id='PNode" + nodes[key]["id"] + "' " +
								"class='eNodeB' pname='" + nodes[key]["name"] + "' " +
								"ptype='" + nodes[key]["type"] + "' " +
								"nodeX='" + nodes[key]["mapX"] + "' " +
								"nodeY='" + nodes[key]["mapY"] + "' " +
								"regionId='" + nodes[key]["regionId"] + "'" + 
								"createId='" + nodes[key]["createId"] + "'" + 
								"createTime='" + nodes[key]["createTime"] + "'" + 
								"style='left:" + (nodes[key]["mapX"] - 19) + "px;" +
								"top:" + (nodes[key]["mapY"] - 8) + "px;'>" +
								"<span class='layui-badge'>參考點</span><span>" + (nodes[key]["name"] || nodes[key]["id"])  + 
								"</span></div>");
					}
				}
				$mapDiv.append(eNodeAttr.join(""));
				mapNodesEventFun($mapDiv);
				var nodes = $mapDiv.find("div.eNodeB");
				for (var i = 0; i < nodes.length; i++) {
					drag(nodes.eq(i) , $mapDiv, updateNodePoint);
				}
			}
			var updateNodePoint = function(data){
				data.updateName = userName;
				$.ajax({type : "post",url : "enode/" + data.id,async: false,dataType : "json",contentType : "application/json;charset=utf-8",data: JSON.stringify(data),
				success : function(result) {
					if(result.code == 600){
						layer.msg("修改基站設備【ID : " + data.id + "】位置成功...",{icon: 6, time: 800});
					} else {
						layer.msg("修改基站設備【ID : " + data.id + "】位置失敗...",{icon: 5, time: 2000});
					}
				},
				error: function(){
					layer.msg("修改基站設備位置出現異常...",{icon: 2});
				}
				})
			}
		});
	},
	// 初始化顯示設置
	initShowSetting: function(){
		var userName = parent.$("div.global_UWB p.user").text();
		layui.use(['layer','form'], function() {
			var layer = layui.layer;
			$("#isShowENodeB~input[type='button']").off("click").click(function() {
				// 獲取按鈕值
				var val = $("#isShowENodeB").prop("checked");
				$.ajax({type : "POST", url : "showparams/key_value/?key=isBsVisible&value=" + val, dataType : "json",contentType : "application/json;charset=utf-8",
				success : function(result) {
					if (result.code == 600) {
						layer.msg("設置基站在地圖上是否可見成功...",
							{icon: 6});
					} else {
						layer.msg(result.msg,
							{icon: 5});
					}
				},
				error : function(result) {
					layer.alert("設置基站在地圖上是否可見【val： " + val + "】出現異常...",
							{icon: 2});
				}})
			})
			$("#TAGTimeout~input[type='button']").off("click").click(
					function() {
						// 獲取
						var isVal = $("#TAGTimeout").prop("checked");
						var val = $("#TAGTimeout~input[type='text']").val();
						if(!val){
							layer.msg("卡片超時時間不能為空...",{icon: 5});
							return;
						}
						$.ajax({type: "POST",url: "showparams/key_enable_value/?key1=isTagOverVisible&enable=" + isVal + "&key2=tagVisibleOverTime&value=" + val, dataType : "json", contentType : "application/json;charset=utf-8",
						success : function(result) {
							if (result.code == 600) {
								layer.msg("設置卡片超時未接收是否可見成功...",{icon: 6});
							} else {
								layer.msg(result.msg,{icon: 5});
							}
						},
						error : function(result) {
							layer.alert("設置卡片超過指定時間未接收到訊息是否可見【isVal：" + isVal + ", " +
										"val: " + val + "】出現異常...",{icon: 2});
						}
						})
			})
			$("#treeConnectIntervalCb~input[type='button']").off("click").click(function(){
				var isVal = $("#treeConnectIntervalCb").prop("checked");
				var val = $("#treeConnectIntervalCb~input[type='text']").val();
				if(!val){
					layer.msg("基站/参考点连接图刷新间隔时间不能为空...",{icon: 5});
					return;
				}
				$.ajax({type: "POST",url: "showparams/key_enable_value/?key1=isRefreshTreeTime&enable=" + isVal + "&key2=refreshTreeTime&value=" + val, dataType : "json", contentType : "application/json;charset=utf-8",
				success : function(result) {
					if (result.code == 600) {
						layer.msg("設置基站/参考点连接图刷新间隔成功...",{icon: 6});
					} else {
						layer.msg(result.msg,{icon: 5});
					}
				},
				error : function(result) {
					layer.alert("設置基站/参考点连接图刷新间隔时间【isVal：" + isVal + ", " +
								"val: " + val + "】出現異常...",{icon: 2});
				}
				})
			})
			// 是否顯示調試賬戶訊息
			$("#isShowDebugerCb~input[type='button']").off("click").click(function(){
				var val = $("#isShowDebugerCb").prop("checked");
				$.ajax({type: "POST", url: "showparams/key_value/?key=isShowDebug&value=" + val, dataType: "JSON", contentType:"application/json;charset=utf-8",
					success: function(result) {
						if(result.code == 600) {
							layer.msg("設置是否顯示調試賬戶訊息參數成功!",{icon: 6});
						} else {
							layer.msg(result.msg, {icon : 5});
						}
					},
					error: function(result){
						layer.msg("設置是否顯示調試賬戶訊息參數【monitorRegionNumShow： " + 
								val + "】出現異常...", {icon: 2});
				}})
			})
			
			$("#monitorRegionNumShowCb~input[type='button']").off("click").click(function(){
				var val = $("#monitorRegionNumShowCb").prop("checked");
				$.ajax({type: "POST", url: "showparams/key_value/?key=isShowMonitorRegionNumber&value=" + val, dataType: "JSON", contentType:"application/json;charset=utf-8",
						success: function(result){
							if(result.code == 600){
								layer.msg("設置監控頁面區域切換欄是否顯示人員數量參數成功!",{icon: 6})
							} else {
								layer.msg(result.msg, {icon : 5});
							}
						},
						error: function(result){
							layer.msg("設置監控頁面區域切換是否顯示人員數量參數【monitorRegionNumShow： " + 
									val + "】出現異常...", {icon: 2});
				 }})
			})
			
			$("#nodeManagerShow~input[type='button']").off("click").click(function(){
				var isVal = $("#nodeManagerShow").prop("checked");
				$.ajax({type: "POST", url: "showparams/key_value/?key=isNodeManagerShowReport&value=" + isVal, dataType: "JSON", contentType: "application/json;charset=utf-8",
					    success: function(result){
					    	if(result.code == 600){
					    		layer.msg("設置基站管理地圖模式中是否顯示基站上報時間成功...",{icon: 6});
					    	} else {
					    		layer.msg(result.msg,{icon: 5});
					    	}
					    },
					    error: function(result){
					    	layer.alert("設置基站管理地圖模式中是否顯示基站上報時間【isVal：" + isVal + "】出現異常...",{icon: 2});
					    }})
			})
			/*
			 * 打印警報訊息設置默認時間段按鈕
			 */
			$("#reportWarnTimeBtn").off("click").click(function(){
				var time = $(this).siblings("select").val();
				$.ajax({type: "POST", url:"showparams/key_value/?key=reportWarnInfoTime&value="+time, dataType: "JSON", 
					success: function(result){
						if(result.code == 600){
							layer.msg("報表輸出警告默認時間段成功...",{icon: 6});
						} else {
							layer.msg(result.msg, {
										icon : 5
									  });
						}
					},
					error: function(result){
						layer.alert("報表輸出警告默認時間段【time："
								+ time + "】出現異常...", {
								icon : 2
								});
					}})
			})
		});
	},
	initWarmingSetting: function(){
		layui.use('layer', function() {
			var layer = layui.layer;
			// 卡片斷開報警
			$("#TAGoff~input[type='button']").off("click").click(function(){
				var isVal = $("#TAGoff").prop("checked");
				var val = $("#TAGoff~input[type='text']").val();
				if(!val){
					layer.msg("設置卡片斷開報警時間不能為空...",{icon: 5});
					return;
				}
				$.ajax({type: "POST", url:"warmparams/key_enable_value/?key1=isTagDisAlarm&enable="+isVal+"&key2=tagDisAlarmTime&value="+val, dataType:"JSON",
				success: function(result){
					if(result.code == 600){
						layer.msg("設置卡片斷開報警成功...", {icon: 6});
					}else{
						layer.msg(result.msg, {icon: 5})
					}
				},
				error: function(result){
					layer.alert("設置卡片斷開報警【isVal： " + isVal + ", val： " + val + "】出現異常...",{icon: 2});
				}
				})
			})
			// 基站斷開報警
			$("#eNodeBoff~input[type='button']").off("click").click(function(){
				var isVal = $("#eNodeBoff").prop("checked");
				var val = $("#eNodeBoff~input[type='text']").val();
				if(!val){// 
					layer.msg("設置基站斷開報警時間不能為空...",{icon: 5});
					return;
				}
				$.ajax({type:"POST", url:"warmparams/key_enable_value/?key1=isNodeDisAlarm&enable=" + isVal + "&key2=nodeDisAlarmTime&value=" + val, dataType : "JSON",
				success : function(result) {
					if(result.code == 600){
							layer.msg("設置基站斷開報警時間成功...",{icon: 6});
					}else{
							layer.msg(result.msg,{icon: 5});
					}
				},
				error : function(result) {
					layer.alert("設置基站斷開報警時間【isVal： " + isVal + ", " +
								"val: " + val + "】出現異常...",{icon: 2});
				}
				})
			})
			// 電量不足報警
			$("#batteryLow~input[type='button']").off("click").click(function(){
				var isVal = $("#batteryLow").prop("checked");
				var val = $("#batteryLow~input[type='text']").val();
				$.ajax({ type:"POST", url:"warmparams/key_enable_value/?key1=isLowBattery&enable="+isVal+"&key2=lowBatter&value="+val, dataType: "JSON",
				success: function(result){
					if(result.code == 600){
						layer.msg("設置低電量報警成功...",{icon: 6});
					}else{
						layer.msg(result.msg,{icon: 5});
					}
				},
				error: function(result){
					layer.alert("設置低電量報警【isVal： " + isVal + 
								", val: " + val + "】出現異常...",{icon: 2});
				}
				});
			})
			// 是否啟用人員求救報警
			$("#personHelpAlarm~input[type='button']").off("click").click(function(){
				var isVal = $("#personHelpAlarm").prop("checked");
				$.ajax({type : "POST", url : "warmparams/key_enable/?key=isPersonHelp&value="+isVal, dataType : "JSON",
				success : function(result) {
					if(result.code==600) {
						layer.msg("設置是否啟用人員求救報警成功...",{icon: 6});
					} else {
						layer.msg(result.msg,{icon: 5});
					}
				},
				error : function(result) {
					layer.alert("設置是否啟用人員求救報警【isVal： " + isVal + "】出現異常...",{icon: 2});
				}
				})
			})
			// 是否啟用區域報警
			$("#areaAlarm~input[type='button']").off("click").click(function(){
				var enable = $("#areaAlarm").prop("checked");
				$.ajax({type: "POST", url: "warmparams/key_enable/?key=isAreaAlarm&value="+enable, dataType: "JSON",
				success: function(result) {
					if(result.code==600) {
						layer.msg("設置是否啟用區域報警成功...",{icon: 6});
					} else {
						layer.msg(result.msg, {icon: 5});
					}
				},
				error: function(result) {
					layer.alert("設置是否啟用區域報警【enable：" + enable + "】出現異常...",{icon: 2});
				}
				})
			})
			// 定時處理日常已經處理的 數據
			$("#autoClearHandledAlarm~input[type='button']").off("click").click(function(){
				var autoClearHandledAlarm = $("#autoClearHandledAlarm").prop("checked");
				var autoClearHandledAlarmTime = $("#autoClearHandledAlarm~input[type='text']").val();
				$.ajax({type: "POST", url: "warmparams/key_enable_value/?key1=autoClearHandleWarn&enable="+autoClearHandledAlarm+"&key2=autoClearHandleWarnTime&value="+autoClearHandledAlarmTime, dataType: "JSON",
						success: function(result){
							if(result.code==600){
								layer.msg("設置自動清除已處理警報成功...",{icon: 6});
							}else{
								layer.msg(result.msg, {icon: 5});
							}
						},error: function(result) {
							layer.alert("設置自動清除已處理警報[isAutoClearHandleAlarm: " + autoClearHandledAlarm + ", " +
									  "autoClearHandledAlarmTime: " + autoClearHandledAlarmTime + "]出現異常...",{icon: 2});
						}
				});
			})
			
			// 滯留報警
			$("#selectRetentionModel~input[type='button']").off("click").click(function(){
				var url = null;
				var option = $("#selectRetentionModel").val();
				if(option == 2) {// 啟用
					var val = $("#selectRetentionModel~div>input[type='text']").val();
					if(!val){
						layer.msg("設置系統滯留時間不能為空...",{icon: 5});
						return;
					}
					url = "warmparams/key_model_value/?key1=resWarmMode&model="+option+"&key2=notMoveTime&val="+val;
				} else {
					url = "warmparams/key_enable/?key=resWarmMode&value="+option;
				}
				$.ajax({type: "POST", url: url, dataType: "JSON",
					success: function(result){
						if(result.code == 600){
							layer.msg("設置系統滯留模式成功...", {icon: 6})
						}else{
							layer.msg(result.msg, {icon: 5});
						}
					},
					error: function(result){
						layer.alert("設置系統滯留模式【option： " + option + "】出現異常...",{icon: 2});
					} 
				});
			})
		})
	},//netWorkInit
	initNetWorkSetting: function(){
		layui.use('layer', function() {
			var layer = layui.layer;
			// 設置Ip地址
			$("#saveIP").off("click").click(function(){
				var ip = $("#saveIP").siblings("select").find("option:selected").val();
				$.ajax({type: "POST", url: "networkparams/key_value/?key=ip&value="+ip, dataType: "JSON",
				success: function(result){
					if(result.code==600){
						layer.msg("設置網絡IP成功...", {icon: 6});
					} else {
						layer.msg(result.msg, {icon: 5});
					}
				},
				error: function(result){
					layer.alert("設置網絡IP【IP： " + ip + "】出現異常...",{icon: 2});
				}
				})
			})
			// 設置端口
			$("#savePort").off("click").click(function(){
				var port = $("#savePort").siblings("input[type='text']").val();
				$.ajax({type: "POST",url:"networkparams/key_value/?key=port&value="+port,dataType: "JSON",success: function(result){
					if(result.code == 600){
						layer.msg("設置端口成功...", {icon: 6});
					}else{
						layer.msg(result.msg, {icon: 5});
					}
				},
				error: function(result){
					layer.alert("設置端口【Port： " + port + "】出現異常...",{icon: 2});
				}})
			})
			
		})
	},
	initSysSetting: function(){
		layui.use(['layer','laydate'], function() {
			// 設置掃描斷開的間隔時間
		    var laydate = layui.laydate;
			var layer = layui.layer;
			$("#scanDisIntervalBtn").off("click").click(function(){
				var scanDisInterval = $("#scanDisIntervalBtn").siblings("input[type='text']").val();
				$.ajax({type: "POST",url: "sysparams/key_value/?key=checkedDisconnectTime&value="+scanDisInterval,dataType: "JSON", 
				success: function(result){
					if(result.code==600){
						layer.msg("設置檢測異常斷開的時間間隔成功...", {icon: 6})
					}else{
						layer.msg(result.msg, {icon: 5})
					}
				},
				error: function(result){
					layer.alert("設置檢測異常斷開的時間間隔【scanDisInterval： " + scanDisInterval + "】出現異常...",{icon: 2});
				}})
			})
			
			// 卡片斷開判斷時間
			$("#tagDisTimeBtn").off("click").click(function(){
				var tagDisTimeParam1 = $("#tagDisTimeBtn").siblings("input[type='text']:first").val();
				var tagDisTimeParam2 = $("#tagDisTimeBtn").siblings("input[type='text']:last").val();
				$.ajax({type: "POST", url: "sysparams/key_param/?key1=checkedTagdisK1&param1="+tagDisTimeParam1+"&key2=checkedTagdisK2&param2="+tagDisTimeParam2,dataType: "JSON",
						success: function(result){
							if(result.code == 600){
								layer.msg("設置判斷卡片斷開時間參數成功...", {icon : 6});
							}else{
								layer.msg(result.msg, {icon: 5});
							}
						},
						error: function(result){
							layer.alert("設置判斷卡片斷開時間參數【tagDisTimeParam1：" + tagDisTimeParam1 + ", " +
									    "tagDisTimeParam2: " + tagDisTimeParam2 + "】出現異常...",{icon: 2});
					}})
			})
			
			// 基站的斷開判斷時間
			$("#baseStationDisTimeBtn").off("click").click(function(){
				var bsDisTimeParam1 = $("#baseStationDisTimeBtn").siblings("input[type='text']:first").val();
				var bsDisTimeParam2 = $("#baseStationDisTimeBtn").siblings("input[type='text']:last").val();
				$.ajax({type: "POST",url: "sysparams/key_param/?key1=checkedDevdisK1&param1="+bsDisTimeParam1+"&key2=checkedDevdisK2&param2="+bsDisTimeParam2,dataType: "JSON",
					    success: function(result){
					    	if(result.code == 600){
					    		layer.msg("設置判斷基站斷開時間參數成功...",  {icon: 6});
					    	} else {
					    		layer.msg(result.msg, {icon: 5});
					    	}
					    },
					    error: function(result){
					    	layer.alert("設置判斷基站斷開時間參數【bsDisTimeParam1：" + bsDisTimeParam1 + ", " +
					    				"bsDisTimeParam2: " + bsDisTimeParam2 + "】出現異常...",{icon: 2});
					    }
				})
			})
			//  清理資料的時間
			$("#timerClearAlarmBtn").off("click").click(function() {
				var timerClearChecked = $("#timerClearAlarmBtn").siblings("input[type='checkbox']").prop("checked");
				var   timerClearDuty = $("#timerClearAlarmBtn").siblings("input[type='text']").val();
				$.ajax({type: "POST",url: "sysparams/key_param/?key1=autoClearAlarmMsg&param1="+timerClearChecked+"&key2=autoClearAlarmMsgTime&param2=" + timerClearDuty,dataType: "JSON",
						success: function(result) {
							if(result.code == 600) {
								layer.msg("設置清理資料的時間成功...",{icon: 6});
							} else {
								layer.msg(result.msg, {icon: 5});
							}
						},
						error: function(result){
							layer.alert("設置清理資料的時間【timerClearChecked： " + timerClearChecked + ", " +
										"timerClearDuty: " + timerClearDuty + "】出現異常...",{icon: 2});
					    }
				})
			})
			// 定時清理日常數據
			$("#timerClearDutyDataBtn").off("click").click(function() {
				var isAutoClearDutyData = $("#timerClearDutyDataBtn").siblings("input[type='checkbox']").prop("checked");
				var autoClearDutyDataTime = $("#timerClearDutyDataBtn").siblings("input[type='text']").val();
				$.ajax({type: "POST",url: "sysparams/key_param/?key1=autoClearDutyData&param1=" + isAutoClearDutyData + "&key2=autoClearDutyDataTime&param2=" + autoClearDutyDataTime,dataType: "JSON",
					success : function(result){
					    if(result.code == 600){
					    	layer.msg("設置自動清理日常數據成功...",{icon: 6});	
					    } else {
					    	layer.msg(result.msg,{icon: 5})	
					    }
				    },
				    error: function(result){
				    	layer.alert("設置自動清理日常數據【isAutoClearDutyData： " + isAutoClearDutyData + ", " +
				    			"autoClearDutyDataTime: " + autoClearDutyDataTime + "】出現異常...",{icon: 2})
				    }})
			})
			
			$("#timerClearDutyOperationBtn").off("click").click(function(){
				var isAutoClearOperationRecord   = $(this).siblings("input[type='checkbox']").prop("checked");
				var autoClearOperationRecordTime = $(this).siblings("input[type='text']").val();
				$.ajax({type: "POST",url: "sysparams/key_param/?key1=autoClearOperationRecord&param1=" + isAutoClearOperationRecord + "&key2=autoClearOperationRecordTime&param2=" + autoClearOperationRecordTime,dataType: "JSON",
					success : function(result){
					    if(result.code == 600){
					    	layer.msg("設置自動清理日常數據成功...",{icon: 6});	
					    } else {
					    	layer.msg(result.msg,{icon: 5})	
					    }
				    },
				    error: function(result){
				    	layer.alert("設置自動清理日常操作【isAutoClearOperationRecord： " + isAutoClearOperationRecord + ", " +
				    			"autoClearOperationRecordTime: " + autoClearOperationRecordTime + "】出現異常...",{icon: 2})
				 }})
			})
			
			$("#timerClearAccessRecordBtn").off("click").click(function(){
				var isAutoClearAccessRecord   = $(this).siblings("input[type='checkbox']").prop("checked");
				var autoClearAccessRecordTime = $(this).siblings("input[type='text']").val();
				$.ajax({type: "POST",url: "sysparams/key_param/?key1=autoClearAccessRecord&param1=" + 
						isAutoClearAccessRecord + "&key2=autoClearAccessRecordTime&param2=" + 
						autoClearAccessRecordTime, dataType: "JSON",
					success : function(result){
					    if(result.code == 600){
					    	layer.msg("設置自動清理出入記錄成功...",{icon: 6});	
					    } else {
					    	layer.msg(result.msg,{icon: 5})	
					    }
				    },
				    error: function(result){
				    	layer.alert("設置自動清理出入記錄操作【isAutoClearOperationRecord： " + isAutoClearOperationRecord + ", " +
				    			"autoClearOperationRecordTime: " + autoClearOperationRecordTime + "】出現異常...",{icon: 2})
				 }})
			})
			
			$("#optimizeModeBtn").off("click").click(function() {
				// 优化模式_优化值
				var optimizeMode = $(this).siblings("select[name='optimizeMode']").val();
				var optimizeVal = $(this).siblings("select[name='optimizeValue']").val();
				$.ajax({type: "POST", url: "sysparams/key_param/?key1=optimizeMode&param1=" + optimizeMode + "&key2=optimizeVal&param2=" + optimizeVal, 
					dataType:"JSON", contentType: "application/json;charset=utf-8",
					success: function(result) {
						if(result.code == 600) {
							layer.msg("設置跳點優化模式和優化值成功!",{icon: 6});
						} else {
							layer.msg(result.msg, {icon : 5});
						}
					},
					error: function(result) {
						layer.msg("設置跳點優化模式和優化值【optimizeMode: " + optimizeMode + 
								", optimizeVal: " + optimizeVal + "】時出現異常!",{icon: 2});
				}})
			})
			$("#maxNumberAccountsBtn").off("click").click(function(){
				var maxAccountsNumber = $(this).siblings("input[type='text']").val();
				if(maxAccountsNumber < 1){
					layer.msg("對不起, 一個賬號至少能被1個人登錄！",{icon: 5});
					return;
				}
				$.ajax({type: "POST",url:"sysparams/key_value/?key=maxNumberAccounts&value="+maxAccountsNumber,
					dataType:"JSON", contentType: "application/json;charset=utf-8",
					success: function(result){
						if(result.code == 600){
							layer.msg("設置一個賬號能被" + maxAccountsNumber + "人登錄成功...",{icon: 6});
						} else {
							layer.msg(result.msg, {icon: 5});
						}
					},
					error: function(result){
						layer.alert("設置一個賬號能被"+ maxAccountsNumber+ "人登陸出現異常...",
									{icon : 2});
					}
				})
			})
			
			var ondutyStartTime = $("#accessOndutyParam").attr("defaultstart"), 
				ondutyEndTime = $("#accessOndutyParam").attr("defaultEnd"),
				offdutyStartTime = $("#accessOffdutyParam").attr("defaultstart"),
				offdutyEndTime = $("#accessOffdutyParam").attr("defaultEnd");
			var accessStatisticTime = $("#accessStatisticTimeParam").attr("accessStatisticTime");;
			// 綁定上班時間段選擇
			laydate.render({
			    elem: "#accessOndutyTimeinterval",
			    type: 'time',
			    range: true,
			    value: ondutyStartTime + " - " + ondutyEndTime,// 設置出入統計的默認開始時間和結束時間
			    done: function(value, date, endDate) {
			    	ondutyStartTime = value.substr(0, 8);
			    	ondutyEndTime = value.substr(11, 8);
			     }
			});
			// 綁定下班時間段選擇
			laydate.render({
			    elem: "#accessOffdutyTimeinterval",
			    type: 'time',
			    range: true,
			    value: offdutyStartTime + " - " + offdutyEndTime,// 設置出入統計的默認開始時間和結束時間
			    done: function(value, date, endDate) {
			    	offdutyStartTime = value.substr(0, 8);
			    	offdutyEndTime = value.substr(11, 8);
			     }
			});
			
			laydate.render({
			    elem: "#accessStatisticTimeInput",
			    type: 'time',
			    value: accessStatisticTime,
			    done: function(value, date, endDate) {
			    	accessStatisticTime = value;
			     }
			});
			
			$("#accessOndutyTimeIntervalBtn").off("click").click(function(){
				if(!ondutyStartTime || !ondutyEndTime){
					layer.msg("出入統計的上班時間段不能為空...",{icon: 2});
					return;
				}
				$.ajax({type: "POST", url:"sysparams/key_param/?key1=accessOndutyStartTime&param1="+ondutyStartTime+"&key2=accessOndutyEndTime&param2="+ondutyEndTime, 
						dataType: "JSON", contentType: "application/json;charset=utf-8",
						success: function(result){
							if(result.code == 600){
								layer.msg("設置出入統計的上班時間段成功...",{icon: 6});
							}else{
								layer.msg(result.msg, {icon: 5});
							}
						},
						error: function(result){
							layer.alert("設置出入統計的上班時間段【startTime: " + ondutyStartTime + 
									    ", endTime: " + ondutyEndTime + "】出現異常...",{icon: 2});
						}})
			})
			
			$("#accessOffdutyTimeIntervalBtn").off("click").click(function(){
				if(!offdutyStartTime || !offdutyEndTime){
					layer.msg("出入統計的下班時間段不能為空...",{icon: 2});
					return;
				}
				$.ajax({type: "POST", url:"sysparams/key_param/?key1=accessOffdutyStartTime&param1="+offdutyStartTime+"&key2=accessOffdutyEndTime&param2="+offdutyEndTime, 
						dataType: "JSON", contentType: "application/json;charset=utf-8",
						success: function(result){
							if(result.code == 600){
								layer.msg("設置出入統計的下班時間段成功...",{icon: 6});
							}else{
								layer.msg(result.msg, {icon: 5});
							}
						},
						error: function(result){
							layer.alert("設置出入統計的下班時間段【startTime: " + ondutyStartTime + 
									    ", endTime: " + ondutyEndTime + "】出現異常...",{icon: 2});
						}})
						
				$.ajax({type: "POST",url:"",dataType:"JSON", contentType: "application/json;charset=utf-8",
						success: function(result){
							
						},
						error: function(result){
							
						}})
			})
			// 設置自動統計上下班
			$("#isAutoStatisticAccessBtn").off("click").click(function(){
				var isAuto = $("#isAutoStatisticAccess").prop("checked");
				$.ajax({type: "POST",url:"sysparams/key_value/?key=isAutoStatisticAccess&value="+isAuto,dataType:"JSON", contentType: "application/json;charset=utf-8",
					success: function(result){
						if(result.code == 600){
							layer.msg("設置是否自動統計上下班功能成功...",{icon: 6});
						} else {
							layer.msg(result.msg, {icon: 5});
						}
					},
					error: function(result){
						layer.alert("設置是否自動啟用上下班統計功能【isAutoStatisticAccess： " + isAuto + 
									"】出現異常",{icon: 2});
					}
				})
			})
			
			$("#manualSetCurrentDoorStatusTimeBtn").off("click").click(function(){
				var manualSetCurrentDoorStatusTime = $("#manualSetCurrentDoorStatusTimeInput").val();
				$.ajax({type: "POST", url:"sysparams/key_value/?key=manualSetCurrentDoorStatusDelay&value=" + manualSetCurrentDoorStatusTime,dataType: "JSON", 
					success: function(result){
						if(result.code == 600){
							layer.msg("手動設置門控狀態的延時時間成功...",{icon: 6});
						}else{
							layer.msg(result.msg, {
									icon : 5 });
						}
					},
					error: function(result){
						console.log("手動設置門控狀態的延時時間出現異常...");
					}})
			})
			$("#accessStatisticTimeBtn").off("click").click(function(){
				$.ajax({type: "POST", url:"sysparams/key_value/?key=accessStatisticTime&value=" + accessStatisticTime,dataType: "JSON", 
					success: function(result){
						if(result.code == 600){
							layer.msg("設置出入統計時刻成功...",{icon: 6});
						}else{
							layer.msg(result.msg, {
									icon : 5 });
						}
					},
					error: function(result){
						console.log("設置出入統計時刻出現異常...");
					}})
			})
		})
	}
}