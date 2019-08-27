/*===============
 * 工種包商資料管理
 * ==============*/
$(function(){
	var jobTypeInit, companyInit, personnelInit, regionalAuthorityInit;
	//資料維護切換
	$("#dataMaintainance li").click(function() {
		var type = $(this).attr("type");
		$("#dataMaintainance>li").removeClass("activeSet");
		$("#dataMaintainance>li[type='" + type + "']").addClass("activeSet");
		$("#contractorDataManage>li").hide();
		$("#contractorDataManage>li[type='" + type + "']").show();
		switch(type) {
		case "1":jobTypeInit();break;
		case "2":companyInit();break;
		case "3":personnelInit();break;
		case "4":regionalAuthorityInit();break;
		}
	})
	var userName = parent.$("div.global_UWB p.user").text();
	/*=======工種資料維護模塊=======*/
	jobTypeInit = function() {
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
					
				}, done : function() {// 綁定資料上傳組件
					jobTypeMaintenanceReq.upload(userName, layer, upload);
				}
			})
			// 窗口標題
			var title; 
			// 監聽表格右側工具欄
			table.on('tool(operaJobType)',function(obj){
				var data = obj.data;
				oldJobType = data;
				switch(obj.event) {
				case 'edit':
					jobTypeMaintenance.initEditWin(data);
					jobTypeMaintenance.createColorPicker(colorpicker, data.color);
					editWinIndex = jobTypeMaintenance.editWin(layer, "編輯工種");
					break;
				case 'del':
					layer.confirm('確定刪除<strong> '+data.name+' </strong>這壹行嗎？',
						function(index) {
							jobTypeReq.remove(data.no, layer, jobTypeTable);
							layer.close(index);
						})
					break;
				}
			})
			//監聽表格頭部工具欄
			table.on('toolbar(operaJobType)',function(obj) {
				switch(obj.event) {
				case "search":
					var val = $("#jobTypeText").val();
					if(val) {
						jobTypeReq.search(jobTypeTable, val);
					} else {
						layer.msg("搜索內容為空",{icon: 7});
					}
					break;
				case "add":
					$("#jobTypeForm").attr("type", "add");
					$("#jobTypeForm input").val("");
					editWinIndex = jobTypeMaintenance.editWin(layer, "添加工種");
					break;
				case "refresh": // 刷新
					jobTypeTable.reload({url:"jobType/page/", page:{
						curr: 1 //重新從第 1 頁開始
					}});
					break;
				}
			})
			jobTypeMaintenance.createColorPicker(colorpicker, "#000");
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
			$("#exportJobTypeBtn").off("click").click(function() {
				jobTypeReq.exportTemplate(layer);
			})
		})
	};
	/*jobType()*/
	/*=======公司資料維護模塊=======*/
	companyInit = function(){
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
				parseData: function(data) {
					
				},
				done: function() {
					companyMaintenanceReq.upload(userName, layer, upload);
				}
			})
			var title, editCompanyIndex;
			var editCompany = null;
			//監聽表格右側工具欄
			table.on('tool(operaCompany)',function(obj){
				var data = obj.data;
				switch(obj.event) {
				case "edit":
					editCompany = data;
					$("#companyForm").attr("type", "edit");
					editCompanyIndex = companyMaintenance.editWin(layer, "編輯公司資料");
					for ( var key in data) {
						$("#companyForm input[name='" + key + "']").val(
								data[key]);
					}
					break;
				case "del":
					layer.confirm('確定刪除<strong> ' + data.name
							+ ' </strong>這壹行嗎？', function(index) {
						companyMaintenanceReq.remove(data.no, data.name, layer,
								companyTable);
						layer.close(index);
					})
					break;
				
				}
			})
			//監聽表格頭部工具欄
			table.on('toolbar(operaCompany)',function(obj) {
				switch (obj.event) {
				case "search":
					var val = $("#companyText").val();
					if (val) {
						companyMaintenanceReq.search(val, companyTable);
					} else {
						layer.msg("搜索內容為空", {
							icon : 7
						});
					}
					break;
				case "add":
					$("#companyForm").attr("type", "add");
					$("#companyForm input").val("");
					editCompanyIndex = companyMaintenance.editWin(layer,
							"添加公司資料");
					break;
				case "refresh": // 刷新
					companyTable.reload({
						url : "company/page/",
						page : {
							curr : 1
						//重新從第 1 頁開始
						}
					});
					break;
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
					// 修改公司訊息
					companyMaintenanceReq.update(data.field["no"], editCompany, layer, companyTable, editCompanyIndex);
				} else {// 添加
					data.field["createName"] = userName;
					companyMaintenanceReq.add(data.field, layer, companyTable, editCompanyIndex);
				}
				return false;
			})
			/**
			 * 導出公司模板
			 */
			$("#exportCompanyBtn").off("click").click(function(){
				companyMaintenanceReq.exportTemplate(layer);
			})
		})
	}	
	/*=======人員資料維護模塊=======*/
	personnelInit = function() {
		layui.use(['table','layer','form','upload','laydate'],function(){
			var table = layui.table;
			var layer = layui.layer;
			var form = layui.form;
			var upload = layui.upload;
			var laydate = layui.laydate;
			var personTable = table.render({ id: "personnel", // 人员讯息
				   elem: $("#personnel"), height: "full-23",
				toolbar: "#personnelHeadTool", defaultToolbar: ["filter", "exports"],
				    url: "person/page/", page: true,
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
						        } 
						] ],
			    parseData: function(data) {
			    	
			    }, done: function() {
			    	staffMaintenanceReq.upload.call(layui, userName);
				}
			})
			var formIndex, oldPerson;
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
						
						formIndex = staffMaintenance.editWin(form, layer, "編輯人員資料");
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
					
					formIndex = staffMaintenance.editWin(form, layer, "添加人員資料");
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
			$("#exportPersonBtn").off("click").click(function() {
				staffMaintenanceReq.exportTemplate(layer);
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
	regionalAuthorityInit = function(){
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
	Maintenance.initMaintainanceUi();
})