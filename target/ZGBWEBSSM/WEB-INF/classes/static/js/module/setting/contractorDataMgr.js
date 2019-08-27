/*===============
 * 工種包商資料管理
 * ==============*/
$(function(){
	//資料維護切換
	$("#dataMaintainance li").click(function() {
		$("#dataMaintainance li").removeClass("activeSet").eq($(this).index()).addClass("activeSet");
		$("#contractorDataManage>li").hide().eq($(this).index()).show();
		if($(this).index()==0) {
			jobType();
		} else if($(this).index()==1) {
			company();
		} else if($(this).index()==2) {
			personnel();
		} else if($(this).index()==3) {
			regionalAuthority();
		}
	})
	var userName = parent.$("div.global_UWB p.user").text();
	/*=======工種資料維護模塊=======*/
	var jobType = function() {
		var oldJobType = null;
		var editWinIndex = null;
		layui.use(['table','layer','form','colorpicker','upload'],function(){
			var table = layui.table; var layer = layui.layer;
			var form = layui.form; var colorpicker = layui.colorpicker;
			var upload = layui.upload;
			var jobTypeTable = table.render({
						elem : $("#jobType"),
						height : "full-23",
						toolbar : "#jobTypeHeadTool",
						defaultToolbar : [ 'filter', 'exports' ],
						url : "jobType/page/",
						page : true,
						cols : [ [ // 表頭
						{
							field : "no", title : "工種代碼",sort : true
						}, {
							field : "name", title : "工種名稱", sort : true
						}, {
							field : "color", title : "工種顏色", templet : $("#showJobTypeColor")
						}, {
							title : "操作", toolbar : $("#jobTypeTool"), align : 'center', width : 112
						} ] ],
						parseData : function(data) {
						},
						done : function() {
							upload.render({
								elem : '#importJobTypeBtn', // 绑定元素
								url : 'jobType/import/' + userName, // 上传接口
								accept : 'file',
								exts : 'xls|xlsx',
								done : function(res) { // 上传完毕回调
									if (res.code == 600) {
										layer.msg("導入工種記錄成功！", {icon : 6});
									} else {
										layer.alert(res.msg, { icon : 5 });
									}
								},
								error : function() {// 请求异常回调
									layer.alert("導入工種記錄出現異常...");
								}
							  });
							}
						})
			var title; //窗口標題
			function jobTypeWindow(title) { 
				return layer.open({type:1, content:$("#jobTypeForm"), area:'auto', title:title});
			}
			//監聽表格右側工具欄
			table.on('tool(operaJobType)',function(obj){
				var data = obj.data;
				oldJobType = data;
				if(obj.event == 'edit'){ //編輯
					title = "編輯工種";
					$("#jobTypeForm").attr("type", "edit");
					editWinIndex = jobTypeWindow(title);
					createColorPicker(data.color);
					$("#jobTypeForm input[name='no']").val(data.no);
					$("#jobTypeForm input[name='name']").val(data.name);
					$("#jobTypeForm input[name='color']").val(data.color);
				}
				if(obj.event == 'del'){ //刪除
					layer.confirm('確定刪除<strong> '+data.name+' </strong>這壹行嗎？',
					function(index) {
						$.ajax({ type: "DELETE", url: "jobType/"+data.no, dataType: "JSON",
						success: function(result){
							if(result.code==600){
								layer.msg("刪除工種【no: " + data.no + "成功...】", {icon: 6});
								// 刷新
								jobTypeTable.reload();
							} else {
								layer.msg(result.msg, {icon: 5});
							}
						},error: function(result){
							console.error("刪除工種【no: " + data.no + "】出現異常...");
						}});
						layer.close(index);
					})
				}
			})
			//監聽表格頭部工具欄
			table.on('toolbar(operaJobType)',function(obj){
				if(obj.event == 'search'){ //搜索
					if($("#jobTypeText").val()){
						jobTypeTable.reload({
							url:"jobType/page/key_name/",
							where:{
								name: $("#jobTypeText").val() //此處的值是傳入後臺查詢的條件
							},
							page:{
								curr: 1 //重新從第 1 頁開始
							}
						})
					}else{
						layer.msg("搜索內容為空",{icon: 7});
					}
				}
				if(obj.event == 'add'){ //添加
					title = "添加工種";
					$("#jobTypeForm").attr("type","add");
					$("#jobTypeForm input").val("");
					editWinIndex = jobTypeWindow(title);
				}
				if(obj.event == 'refresh'){ //刷新
					// 刷新
					jobTypeTable.reload({url:"jobType/page/", page:{
						curr: 1 //重新從第 1 頁開始
					}});
				} 
			})
			//創建顏色選擇器
			function createColorPicker(showColor){
				colorpicker.render({
					elem: $("#selectJobColor"),
					color: showColor, //默認顯示顏色
					done: function(color) { //顏色選擇後回調
						$("input[name='color']").val(color);
					}
				})
			}
			createColorPicker('#000');
			//監聽提交事件
			form.on('submit(submitJobType)',function(data){
				// 此時判斷是添加還是修改資料
				var type = $("#jobTypeForm").attr("type");
				if(type == "edit") { // 編輯工種訊息
					oldJobType['no'] = data.field['no'];
					oldJobType['name'] = data.field['name'];
					oldJobType['color'] = data.field['color'];
					oldJobType['updateName'] = userName;
					jobTypeReq.edit(layer, jobTypeTable, editWinIndex,  oldJobType);
				} else { // 添加工種訊息
					data.field['createName'] = userName;
					jobTypeReq.add(layer, jobTypeTable, editWinIndex, data.field);
				}
				return false;
			})
			/**
			 * 導出工種模板
			 */
			$("#exportJobTypeBtn").off("click").click(function(){
				  $.ajax({type: "GET",url:"jobType/export/", dataType:"JSON",
					success: function(result) {
						if(result.code == 600) {
							window.location.href="jobType/upload/";
							layer.alert(result.data,{icon: 6});
						} else {
							layer.alert(result.msg,{icon: 5});
						}
					}, error: function(result){
						layer.alert("導出模板文件出現異常...",{icon: 2});
					}})
			})
		})
	};
	jobType()
	/*=======公司資料維護模塊=======*/
	var company = function(){
		layui.use(['table','layer','form','upload'],function(){
			var table = layui.table;
			var layer = layui.layer;
			var form = layui.form;
			var upload = layui.upload;
			var companyTable = table.render({
				elem: $("#company"),
				height: "full-23",
				toolbar: "#companyHeadTool",
				defaultToolbar: ['filter', 'exports'],
				url: "company/page/",
				page: true,
				cols: [[ //表頭
					{field:"no", title:"公司編號", sort: true},
					{field:"name", title:"公司名稱", sort: true},
					{field:"phone", title:"電話", sort: true},
					{field:"address", title:"地址", sort: true},
					{title:"操作", toolbar:$("#companyTool"), align:'center', width:115}
				]],
				parseData: function(data){
					
				},
				done: function(){
					upload.render({
						elem : '#importCompanyBtn', // 绑定元素
						url : 'company/import/' + userName, // 上传接口
						accept: 'file',
						exts: 'xls|xlsx',
						done : function(res) {// 上传完毕回调
							if(res.code == 600) {
								layer.msg("導入公司記錄成功！",{icon: 6});
							} else {
								layer.alert(res.msg,{icon: 5});
							}
						},error : function() {// 请求异常回调
							layer.alert("導入公司記錄出現異常...");
						}
					});
				}
			})
			var title, editCompanyIndex;
			function companyWindow(title){ 
				return layer.open({
					type: 1,
					content: $("#companyForm"),
					area: 'auto',
					title: title
				});
			}
			var editCompany = null;
			//監聽表格右側工具欄
			table.on('tool(operaCompany)',function(obj){
				var data = obj.data;
				if(obj.event == 'edit'){ //編輯
					title = "編輯公司資料";
					editCompany = data;
					$("#companyForm").attr("type", "edit");
					editCompanyIndex = companyWindow(title);
					for(var key in data){
						$("#companyForm input[name='"+key+"']").val(data[key]);
					}
				}
				if(obj.event == 'del'){ //刪除
					layer.confirm('確定刪除<strong> '+data.name+' </strong>這壹行嗎？',function(index){
						$.ajax({type: "DELETE",
							url: "company/" + data.no,
					   dataType: "JSON",
						success: function(result){
							if(result.code == 600) {
								layer.msg("刪除公司【name: " + data.name + "】成功...", {icon: 6});
								// 重新加載列表
								companyTable.reload();
							} else {
								layer.msg(result.msg, {icon: 5});
							}
						},
						error: function(result){
							console.error("刪除公司【name: " + data.name + "】出現異常...");
						}});
						layer.close(index);
					})
				}
			})
			//監聽表格頭部工具欄
			table.on('toolbar(operaCompany)',function(obj){
				if(obj.event == 'search'){ //搜索
					if($("#companyText").val()){
						companyTable.reload({
							url : "company/page/key_name/",
							where : {
								name : $("#companyText").val()
							},
							page : {
								curr : 1
							}
						})
					} else {
						layer.msg("搜索內容為空",{icon: 7});
					}
				}
				if(obj.event == 'add'){ //添加
					title = "添加公司資料";
					$("#companyForm").attr("type","add");
					$("#companyForm input").val("");
					editCompanyIndex = companyWindow(title);
				}
				if(obj.event == 'refresh'){
					// 刷新
					companyTable.reload({url:"company/page/", page:{
						curr: 1 //重新從第 1 頁開始
					}});
				}
			})
			//監聽提交事件
			form.on('submit(submitCompany)',function(data){
				var type = $("#companyForm").attr("type");
				if(type == "edit"){// 編輯
					editCompany["no"] = data.field["no"];
					editCompany["name"] = data.field["name"];
					editCompany["phone"] = data.field["phone"];
					editCompany["address"] = data.field["address"];
					editCompany["updateName"] = userName;
					$.ajax({type: "POST", url: "company/" + data.field["no"], dataType: "JSON", data: JSON.stringify(editCompany), contentType: "application/json;charset=utf-8",
							success: function(result) {
								if(result.code == 600) {
									layer.msg("公司資料設置成功..." ,{icon: 6});
									// 重新加載列表
									companyTable.reload();
									// 關閉編輯窗
									layer.close(editCompanyIndex);
								} else {
									layer.msg(result.msg, {icon: 5});
								}
							}, error: function(result){
								console.error("公司資料設置出現異常...");
							}})
				} else {// 添加
					data.field["createName"] = userName;
					$.ajax({type: "PUT", url: "company/", dataType: "JSON", data: JSON.stringify(data.field), contentType: "application/json;charset=utf-8",
							success: function(result){
								if(result.code == 600){
									layer.msg("添加公司資料訊息成功...", {icon: 6});
									// 重新加載列表
									companyTable.reload();
									// 關閉編輯窗
									layer.close(editCompanyIndex);
								} else {
									layer.msg(result.msg, {icon: 5});
								}
							},
							error: function(result){
								console.error("添加公司資料訊息出現異常...");
							}
					})
				}
				return false;
			})
			/**
			 * 導出公司模板
			 */
			$("#exportCompanyBtn").off("click").click(function(){
				$.ajax({type: "GET",url:"company/export/", dataType:"JSON",
				success: function(result){
					if(result.code == 600){
						window.location.href="company/upload/";
						layer.alert(result.data,{icon: 6});
					} else {
						layer.alert(result.msg,{icon: 5});
					}
				}, error: function(result){
					layer.alert("導出模板文件出現異常...",{icon: 2});
				}})
			})
		})
	}	
	/*=======人員資料維護模塊=======*/
	var personnel = function() {
		layui.use(['table','layer','form','upload','laydate'],function(){
			var table = layui.table;
			var layer = layui.layer;
			var form = layui.form;
			var upload = layui.upload;
			var laydate = layui.laydate;
			var personTable = table.render({
					id: "personnel", // 人员讯息
				   elem: $("#personnel"),
				 height: "full-23",
				toolbar: "#personnelHeadTool",
		 defaultToolbar: ["filter", "exports"],
				    url: "person/page/",
				   page: true,
				  cols : [ [ // 表頭
				                {
				                	type: "checkbox"
				                },
				                {
									field : "no",
									title : "工號",
									sort : true
								}, {
									field : "name",
									title : "姓名",
									sort : true
								}, {
									field : "tagId",
									title : "卡號",
									sort : true
								}, {
									field : "company_Name",
									title : "公司",
									sort : true
								}, {
									field : "jobType_Name",
									title : "工種",
									sort : true
								}, {
									title : "操作",
									toolbar : $("#personTool"),
									align : 'center',
									width : 160,
									fixed : 'right'
								} ] ],
			    parseData: function(data) {
			    	
			    }, done: function() {
					upload.render({
						elem : '#importPersonBtn', // 绑定元素
						url : 'person/import/' + userName, // 上传接口
						accept: 'file',
						exts: 'xls|xlsx',
						done : function(res) {// 上传完毕回调
							if(res.code == 600) {
								layer.msg("導入人員記錄成功！",{icon: 6});
							} else {
								layer.alert(res.msg, {icon: 5});
							}
						}, error : function() {// 请求异常回调
							layer.alert("導入人員記錄出現異常...");
						}
					});
				}
			})
			var title, formIndex;
			function personnelWindow(title) { 
				//顯示時我們先獲取所有的公司訊息及工種訊息
				staffMaintenanceReq.jobTypes(staffMaintenance.jobTypes);
				// 獲取所有的公司訊息
				staffMaintenanceReq.companys(staffMaintenance.companys);
				// 获取工地讯息
				staffMaintenanceReq.groups(staffMaintenance.groups);
				// 重新渲染表單
				form.render();
				return layer.open({ type: 1, content: $("#personnelForm"), maxmin: true, area: 'auto', maxWidth: 900, title: title})
			}
			var oldPerson = null;
			//監聽表格右側工具欄
			table.on('tool(operaPersonnel)',function(obj){
				var data = obj.data;
				switch(obj.event) {
					case "show": 
						staffMaintenance.personnelDataCardWin(layer, data);
						break;
					case "edit": 
						oldPerson = data;
						$("#personnelForm").attr("type","edit");
						for(var key in data) {
							if(key == "serialNumber") {
								$("#personnelForm input[name='" + key + "']").val(PrefixInteger(data[key], 6));
							} else {
								$("#personnelForm input[name='" + key + "']").val(data[key]).prop("disabled", false);
							}
						}
						$("#personnelForm div.layui-form-item:first>label:first").show();
						$("#personnelForm div.layui-form-item:first>div:first").show();
						$("#personnelForm textarea[name='des']").val(data.des).prop("disabled", false);
						$("#personnelForm select[name='company_No']").attr("option", data.company_No);
						$("#personnelForm select[name='jobType_No']").attr("option", data.jobType_No);
						$("#personnelForm select[name='group_id']").attr("option", data.group_id);
						// 选择血型
						staffMaintenance.bloodType(data.bloodType);
						$("#personnelForm button").prop("disabled",false);
						$("#personnelForm button[lay-submit]").show();
						$("#imagePreview").attr({"imagePath":data.imgPath,"src":data.imgPath?("staff/" + data.imgPath):"img/sysperson.jpg"});
						formIndex = personnelWindow("編輯人員資料");
						break;
					case "del":
						layer.confirm('確定刪除<strong> '+data.serialNumber+' </strong>這壹行嗎？',function(index){
							staffMaintenanceReq.remove(data["serialNumber"], layer, personTable, index);
						})
						break;
				}
			})
			//監聽表格頭部工具欄
			table.on('toolbar(operaPersonnel)',function(obj) {
				switch(obj.event) {
				case "search":// 进行搜索
					if($("#personnelText").val()){
						personTable.reload({
							url: "person/page/key_name/?name=" + $("#personnelText").val(),
							page:{
								curr: 1 //重新從第 1 頁開始
							}
						})
					} else {
						layer.msg("搜索內容為空",{icon: 7});
					}
					break;
				case "add":
					$("#personnelForm").attr("type","add");
					$("#personnelForm input,#personnelForm textarea").not("input[type='file']").val("");
					$("#personnelForm input").prop("disabled",false);
					$("#personnelForm select").prop("disabled",false);
					$("#personnelForm textarea").prop("disabled",false);
					$("#personnelForm button").prop("disabled",false);
					$("#personnelForm button[lay-submit]").show();
					$("#imagePreview").attr("src", "img/sysperson.jpg");
					$("#imagePreview").removeAttr("imagePath");
					$("#imagePreview").removeAttr("alt");
					// 添加人员讯息时, 人员编号不可见
					$("#personnelForm div.layui-form-item:first>label:first").hide();
					$("#personnelForm div.layui-form-item:first>div:first").hide();
					formIndex = personnelWindow("添加人員資料");
					break;
				case "refresh":
					personTable.reload({url: "person/page/", page:{curr: 1}});
					break;
				case "exportCard":// 获取当前已经选择的卡片
					var checkStatus = layui.table.checkStatus(obj.config.id);
					if(checkStatus.data.length <= 0) {
						layer.msg("必須選擇需要導出的資料卡");
						return;
					}
					var nos = [];
					for (var i = 0, size = checkStatus.data.length; i < size; i++) {
						nos.push(checkStatus.data[i].serialNumber);
					}
					staffMaintenanceReq.createCardsWord(layer, nos, staffMaintenanceReq.downloadCards);
					break;
				}
			})
			//創建日期實例
			laydate.render({
			    elem: "#birthdate"
		  	});
			//監聽提交事件
			form.on('submit(submitPersonnel)',function(data){
				var type = $("#personnelForm").attr("type");
				if(type == "edit"){// 編輯
					oldPerson["no"] = data.field["no"]; oldPerson["name"] = data.field["name"];
					oldPerson["tagId"] = data.field["tagId"]; oldPerson["imgPath"] = data.field["imgPath"];
					oldPerson["company_No"] = data.field["company_No"]; oldPerson["jobType_No"] = data.field["jobType_No"];
					oldPerson["des"] = data.field["des"]; oldPerson["birthDay"] = data.field["birthDay"];
					oldPerson["resTime"] = data.field["resTime"]; oldPerson["stopTime"] = data.field["stopTime"];
					oldPerson["imgPath"] = $("#imagePreview").attr("imagePath");
					oldPerson["updateName"] = userName;
					oldPerson["group_id"] = data.field["group_id"];
					oldPerson["bloodType"] = data.field["bloodType"];
					staffMaintenanceReq.update(oldPerson, layer, personTable, formIndex);
				} else {// 添加
					data.field["imgPath"] = $("#imagePreview").attr("imagePath");
					data.field["createName"] = userName;
					staffMaintenanceReq.add(data.field, layer, personTable, formIndex);
				}
				return false;
			})
			/**
			 * 導出人員模板
			 */
			$("#exportPersonBtn").off("click").click(function(){
				$.ajax({type: "GET",url:"person/export/", dataType:"JSON",
					success: function(result) {
						if(result.code == 600) {
							window.location.href="person/upload/";
							layer.alert(result.data,{icon: 6});
						} else {
							layer.alert(result.msg,{icon: 5});
						}
					}, error: function(result){
						layer.alert("導出模板文件出現異常...",{icon: 2});
					}})
			})
			// 导出人员资料卡
			$("#generateCardBtn").off("click").click(function() {
				var serialNumber = $("#personalInfoWindow span[serialnumber]").text();
				html2canvas($("#personalInfoWindow div[generateCard]")[0])
						.then(function(canvas) 
						{
							var imgUrl= canvas.toDataURL("png");
							var a = document.createElement("a");
							a.href = imgUrl;
							a.download = serialNumber+"";
							a.click();
						}
				);
			})			
		})
	}
	/*========人員區域權限設定模塊=========*/
	var regionalAuthority = function(){
		layui.use(['table','layer','form','element'],function(){
			var table = layui.table;
			var layer = layui.layer;
			var form = layui.form;
			var element = layui.element;
			var requestRegionalAuthority = {
				addReliableCall: function(personNo, $dom) {
					requestDataObj.reliableNodes.list(layer, personNo, $dom ,drawingRegionalAuthority.reliableNodes);
				},
				addCompanyReliableCall: function(companyNo, $dom) {
					requestDataObj.companyReliableNodes.list(layer, companyNo, $dom, drawingRegionalAuthority.reliableNodes);
				}
			}
			var drawingRegionalAuthority = {
				groups: function(data) {
					$("#checkboxTree").empty();
					$("#companyCheckboxTree").empty();
					var attr = [];
					for (var i = 0; i < data.length; i++) {
						attr.push("<li groupId='" + data[i]["id"] + "'><p class='parentNode'>" +
								  "<span class='stretch'>-</span><input type='checkbox'>" +
								  "<span>" + data[i]["name"] + "</span></p>" +
								  "<ul class='childNodes' style='display: block;'></ul></li>");
					}
					$("#checkboxTree").append(attr.join(""));
					$("#companyCheckboxTree").append(attr.join(""));
				},
				regions: function(data){
					for (var i = 0; i < data.length; i++) {
						var sDom = "<li regionId='" + data[i]["id"] + "' class='childNode'>" +
						"<span class='stretch'>-</span><input type='checkbox' />" +
						"<span>" + data[i]["name"] + "</span><ul class='grandChildNodes'></ul></li>";
						$("#checkboxTree li[groupId='" + data[i]["groupId"] + "']>ul.childNodes").append(sDom);
						$("#companyCheckboxTree li[groupId='" + data[i]["groupId"] + "']>ul.childNodes").append(sDom);
					}
				},
				nodes: function(data, id){
					var $nodeDom = null;
					for (var i = 0; i < data.length; i++) {
						var sDom = "<li referId='" + data[i]["id"] + "' class='grandChildNode'><img src='img/joinLast.gif'/>" +
						  "<input type='checkbox' /><span>" + data[i]["name"] + "</span></li>";
						$(sDom).appendTo("#checkboxTree li[regionId='" + data[i]["regionId"] + "']>ul.grandChildNodes")
							   .prev().find("img").attr("src","img/join.gif");
						$(sDom).appendTo("#companyCheckboxTree li[regionId='" + data[i]["regionId"] + "']>ul.grandChildNodes")
						   .prev().find("img").attr("src","img/join.gif");
					}
				},
				reliableNodes: function(data, $dom) {
					var attr = [];
					for (var i = 0; i < data.length; i++) {
						attr.push("<tr><td>" + data[i]["id"] + "</td><td>" +
								   data[i]["name"] + "</td></tr>")
					}
					$dom.append(attr.join(""));
				},
				treeReliableNodes: function(data, $dom) {
					for (var i = 0; i < data.length; i++) {
						$dom.find("li[referid='" + data[i]["id"] + "'] input[type='checkbox']").prop("checked",true);
					}
				}
			}
			
			table.on('tool(regionAuthorCompanyFilter)',function(obj){
				var data = obj.data;
				if(obj.event == 'setCompanyRegion'){
					layer.open({
						type: 1,
						content: $("#setCompanyRegion"),
						area: ['614px','auto'],
						title: '設定 <strong no="' + data.no + '" style="color: #1E9FFF;">'+ data.name +'</strong> 可靠近的工地'
					})
					$("#companyNodeList>tbody").empty();
					$("#companyCheckAllRegion").prop("checked",false).change();
					requestDataObj.companyReliableNodes.list(layer, data.no, $("#companyNodeList>tbody"), drawingRegionalAuthority.reliableNodes);
					requestDataObj.companyReliableNodes.list(layer, data.no, $("#companyCheckboxTree"), drawingRegionalAuthority.treeReliableNodes);
					
				}
			})
			table.on('toolbar(regionAuthorCompanyFilter)',function(obj){
				var data = obj.data;
				if(obj.event == 'search') {// 搜索
					if($("#companyKeyword").val()) {
						table.reload("regAuthorCompanyTable", {
							url: "company/page/key_name/?name="+$("#companyKeyword").val(),
							page : {
								curr : 1
							}
						});
					} else {
						layer.msg("搜索內容為空", {icon: 7});
					}
				} else if(obj.event == 'refresh') {// 刷新
					table.reload("regAuthorCompanyTable", {
						url: "company/page/",
						page:{
							curr: 1 //重新從第 1 頁開始
						}
					})
				}
			})
			// 獲取所有的組別訊息，並加載到樹狀圖上
			requestDataObj.groups(layer, drawingRegionalAuthority.groups);
			// 獲取所有的區域訊息，並加載到樹狀圖上
			requestDataObj.regions(layer,drawingRegionalAuthority.regions);
			// 獲取所有的參考點訊息，並加載到樹狀圖上
			requestDataObj.nodes(layer, drawingRegionalAuthority.nodes);
			var regionTable = table.render({
				elem: $("#regionalAuthority"),
				height: "full-83",
				toolbar: "#regionalAuthorityHeadTool",
				defaultToolbar: ['filter', 'exports'],
				url: "person/page/",
				page: true,
				cols: [[ //表頭
					{field:"no", title:"身份證號", sort: true},
					{field:"name", title:"姓名", sort: true},
					{field:"tagId", title:"卡片號碼", sort: true},
					{field:"company_Name", title:"公司名稱", sort: true},
					{field:"jobType_Name", title:"工種名稱", sort: true},
					{title:"操作", toolbar:$("#regionalAuthorityTool"), align:'center'}
				]],
				parseData: function(data) {
				}
			})
			//監聽表格右側工具欄
			table.on('tool(operaRegional)',function(obj){
				var data = obj.data;
				if(obj.event == 'setRegion'){
					layer.open({
						type: 1,
						content: $("#setRegion"),
						area: ['614px','auto'],
						title: '設定 <strong no="' + data.no + '" style="color: #1E9FFF;">'+ data.name +'</strong> 可靠近的工地'
					})
					$("#nodeList>tbody").empty();
					$("#checkAllRegion").prop("checked",false).change();
					requestDataObj.reliableNodes.list(layer, data.no, $("#nodeList>tbody"), drawingRegionalAuthority.reliableNodes);
					requestDataObj.reliableNodes.list(layer, data.no, $("#checkboxTree"), drawingRegionalAuthority.treeReliableNodes);
				}
			})
			//監聽表格頭部工具欄
			table.on('toolbar(operaRegional)',function(obj){
				if(obj.event == 'search'){ //搜索
					if($("#keyword").val()){
						regionTable.reload({
							url: "person/page/key_name/?name="+$("#keyword").val(),
							page : {
								curr : 1
							}
						})
					}else{
						layer.msg("搜索內容為空",{icon: 7});
					}
				} else if(obj.event == 'refresh'){
					regionTable.reload({
						url: "person/page/",
						page:{
							curr: 1 //重新從第 1 頁開始
						}
					})
				}
			})
			$("#saveCompanyRegionalAuthority").off("click").click(function(){
				// 獲取所有的參考點
				var $checkDom = $("#companyCheckboxTree li[referid] input[type='checkbox']:checked");
				var checkReferIds = [];
				for (var i = 0; i < $checkDom.length; i++) {
					checkReferIds.push($checkDom.eq(i).parent("li[referid]").attr("referid"));
				}
				var companyNo = $("div.layui-layer-title strong[no]").attr("no");
				// 添加人員基站
				var createName = userName;
				$("#companyNodeList>tbody").empty();
				// 設置
				requestDataObj.companyReliableNodes.add(layer, companyNo, checkReferIds, createName, requestRegionalAuthority.addCompanyReliableCall, $("#companyNodeList>tbody"));
				
			})
			//創建參考點表格
			$("#saveRegionalAuthority").off("click").click(function(){
				// 獲取所有的參考點
				var $checkDom = $("#checkboxTree li[referid] input[type='checkbox']:checked");
				var checkReferIds = [];
				for (var i = 0; i < $checkDom.length; i++) {
					checkReferIds.push($checkDom.eq(i).parent("li[referid]").attr("referid"));
				}
				var personNo = $("div.layui-layer-title strong[no]").attr("no");
				// 添加人員基站
				var createName = userName;
				$("#nodeList>tbody").empty();
				requestDataObj.reliableNodes.add(layer, personNo, checkReferIds, createName, requestRegionalAuthority.addReliableCall, $("#nodeList>tbody"));
			})
			$(".parentNode").on("click","span:eq(0)",stretch);
			$(".childNode").on("click","span:eq(0)",grandstretch);
			function stretch() { //子節點顯示/隱藏
				$(this).parent().siblings(".childNodes").stop().slideToggle("fast");
				if($(this).hasClass("stretch")){
					$(this).removeClass("stretch").text("+");
				} else {
					$(this).addClass("stretch").text("-");
				}
			}
			function grandstretch(){ 
				$(this).siblings(".grandChildNodes").stop().slideToggle("fast");
				if($(this).hasClass("stretch")){
					$(this).removeClass("stretch").text("+");
				}else{
					$(this).addClass("stretch").text("-");
				}
			}
			$(".childNodes:last").css("border-color","white");
			$("ul.childNodes").each(function(index){
				$("ul.childNodes").eq(index).find(".grandChildNodes:last").css("border-color","white");
			})
			isAllChecked($("#checkAllRegion"), $("#checkboxTree input[type='checkbox']"));
			isAllChecked($("#companyCheckAllRegion"), $("#companyCheckboxTree input[type='checkbox']"));
			var $companyGroupLi = $("#companyCheckboxTree li[groupId]");
			for (var i = 0; i < $companyGroupLi.length; i++) {
				isAllChecked($companyGroupLi.eq(i).children("p.parentNode").find("input[type='checkbox']"), 
						$companyGroupLi.eq(i).children("ul.childNodes").find("input[type='checkbox']"));
			}
			var $groupLi = $("#checkboxTree li[groupId]");
			for (var i = 0; i < $groupLi.length; i++) {
				isAllChecked($groupLi.eq(i).children("p.parentNode").find("input[type='checkbox']"), 
						$groupLi.eq(i).children("ul.childNodes").find("input[type='checkbox']"));
			}
			var $companyRegionLi = $("#companyCheckboxTree li[regionId]");
			for (var i = 0; i < $companyRegionLi.length; i++) {
				isAllChecked($companyRegionLi.eq(i).children("input[type='checkbox']"), 
						$companyRegionLi.eq(i).children("ul.grandChildNodes").find("input[type='checkbox']"));
			}
			var $regionLi = $("#checkboxTree li[regionId]");
			for (var i = 0; i < $regionLi.length; i++) {
				isAllChecked($regionLi.eq(i).children("input[type='checkbox']"), 
						$regionLi.eq(i).children("ul.grandChildNodes").find("input[type='checkbox']"));
			}
		})
	}
})