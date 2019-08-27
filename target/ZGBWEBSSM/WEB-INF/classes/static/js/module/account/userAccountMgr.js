/*===============
 * 權限管理
 * ==============*/
$(function() {
	var userName = parent.$("div.global_UWB p.user").text();
	layui.use(['table','layer','form'], function(){
		var table = layui.table;
		var layer = layui.layer;
		var  form = layui.form;
		var allRoles = [];
		/*============
		* 帳號類型管理
		* ============*/
		var roleTypeTable = table.render({
			elem: $('#accountType'),
			page: true,
			url: "role/",
			height: "full-10",
			id: 'accountType',
			toolbar: '#accountTypeHeadTool',
			defaultToolbar: ['filter', 'exports'],
			cols: [
				[ //表頭
					{ field: "name", title: "帳號類型", align: "center"},
					{ field: "des", title: "帳號類型描述", align: "center"},
					{ title: "操作", align: 'center', templet: '#accountTypeTableTool',align: "center"}
				]
			],
			parseData: function(obj) {
				// 添加对象数组到allRoles中
				allRoles = obj.data;
			}
		})
		//監聽頭部工具事件
		var accountTypeWindow, accountTypeTitle
		function accountTypeWindowFun(title) {
			accountTypeWindow = layer.open({ 
				type: 1,
				content: $('#accountTypeForm'),
				title: title
			})
		}
		table.on('toolbar(type)',function(obj){
			if(obj.event == 'add'){
				$("#accountTypeForm textarea,#accountTypeForm input").val('');
				accountTypeTitle = '新增帳號類型';
				accountTypeWindowFun(accountTypeTitle);
			}
		})
		//監聽表格工具事件
		table.on('tool(type)',function(obj){
			var data = obj.data;
			switch(obj.event){
				case 'permission':
					layer.open({ 
						type: 1,
						content: $('#userPermission'),
						title: "查看/修改 <strong style='color:#009688'>"+ data.name +"</strong> 權限",
						area: ['auto', '90%']
					})
					// 获取角色ID
					var roleId;
					for (var i = 0; i < allRoles.length; i++) {
						if(allRoles[i]["name"] == data.name){
							roleId = allRoles[i]["id"];
						}
					}
					if(roleId) {// 设置角色ID属性
						$("#userPermission").attr("roleId", roleId);
					}
					// 获取当前角色所具有的权限讯息
					requestDataObj.permission.role_Permissions(data.name, layer, drawingCallBack.roleNamePermissions);
				break;
				case 'edit': //編輯
					$("#accountTypeForm input[name='name']").val(data.name);
					$("#accountTypeForm textarea").val(data.des);
					accountTypeTitle = '編輯帳號類型';
					accountTypeWindowFun(accountTypeTitle);
				break;
				case 'delete': 
					layer.confirm('確定刪除該帳號類型【name : ' + data.name + '】嗎？',function(index){ 
						requestDataObj.role.remove(roleTypeTable, layer, data.name);
					})
				break;
			}
		})
		//保存權限設置
		$("#savePermission").click(function(){
			// 获取角色ID
			var roleId = $("#userPermission").attr("roleId");
			if(!roleId){
				layer.msg("无法获取角色的ID！",{icon: 5});
				return;
			}
			// 保存权限ID
			var rolePermissionIds = [];
			var $checkBox = $("#pTree input[type='checkbox']:checked");
			for (var i = 0; i < $checkBox.length; i++) {
				var permissionId = $checkBox.eq(i).parent("li").attr("permissionId");
				if(permissionId){
					var craeteName = $("div.headLeft p.user").text();
					rolePermissionIds.push({"role_id": roleId, "permissionId":permissionId, "createName": craeteName})
				}
			}
			// 实现权限的保存操作
			requestDataObj.permission.addPermissions(rolePermissionIds, layer);
		})
		//監聽表單提交
		form.on('submit(submitAccountType)',function(obj){
			var data = obj.field;
			switch(accountTypeTitle){
				case '新增帳號類型':
					data.createName = userName;
					requestDataObj.role.add(roleTypeTable, layer, data);
					break;
				case '編輯帳號類型':// 修改角色訊息
					data.updateName = userName;
					requestDataObj.role.update(roleTypeTable, layer, data);
					break;
			}
			return false;
		})
		/*============
		* 帳號管理
		* ============*/
		var accountTable = table.render({
			elem: $('#account'),
			page: true,
			url: "account/",
			height: "full-10",
			id: 'account',
			toolbar: '#accountHeadTool',
			defaultToolbar: ['filter', 'exports'],
			cols: [
				[
					{ field: "name", title: "帳號名稱", align: "center"},
					{ field: "roleName", title: "帳號類型", align: "center"},
					{ field: "status", title: "狀態",width: 100, align: "center", templet:'#stateTemplate'},
					{ field: "number", title: "人數",width: 90, align: "center"},
					{ title: "操作", align: 'center', templet: '#accountTableTool'}
				]
			],
			parseData: function(obj) {
				
			}
		})
		//監聽頭部工具事件
		var accountWindow, accountTitle
		function accountWindowFun(title){
			accountWindow = layer.open({ //帳號類型窗口
				type: 1,
				content: $('#accountForm'),
				title: title
			})
		}
		var drawingAllRoles = {
			roles: function(data) {
				var options = [];
				for (var i = 0; i < data.length; i++) {
					options.push("<option value='" + data[i].id + "'>" + data[i].name + "</option>");
				}
				$("#accountForm select[name='roleId']").empty().append(options.join(""));
				form.render();
			}
		}
		table.on('toolbar(account)',function(obj){
			if(obj.event == 'add'){
				$("#accountForm select,#accountForm input").val('');
				accountTitle = '新增帳號';
				accountWindowFun(accountTitle);
				// 加載選擇的角色
				requestDataObj.role.all(layer, drawingAllRoles.roles);
			}
		})
		//監聽表格工具事件
		table.on('tool(account)',function(obj){
			var data = obj.data;
			switch(obj.event){
				case 'offline':
					requestDataObj.account.kickOut(layer, data.name, reloadAccount);
					
				break;
				case 'edit': //編輯
					requestDataObj.role.all(layer, drawingAllRoles.roles);
					$("#accountForm input[name='name']").val(data.name);
					$("#accountForm input[name='psw']").val("******");
					$("#accountForm select[name='roleId']").val(data.roleId);
					accountTitle = '編輯帳號';
					accountWindowFun(accountTitle);
					form.render('select','accountForm');
				break;
				case 'delete': //刪除
					layer.confirm('確定刪除該帳號嗎？',function(index) {
						requestDataObj.account.remove(accountTable, layer, data.name);
					})
				break;
			}
		})
		//監聽表單提交
		form.on('submit(submitAccount)',function(obj){
			var data = obj.field;
			switch(accountTitle){
				case '新增帳號': //增加帳號
					data.createName = userName;
					requestDataObj.account.add(layer, accountTable, data);
				break;
				case '編輯帳號': //修改帳號信息
					data.updateName = userName;
					requestDataObj.account.update(accountTable, layer, data);
				break;
			}
			return false;
		})
		//"權限信息"切換
		$("#accountClassify li").click(function(){
			$("#accountClassify li").removeClass("activeSet").eq($(this).index()).addClass("activeSet");
			$("#accountContent>li").hide().eq($(this).index()).show();
			switch($(this).index()){
				case 0:table.reload('accountType');
					break;
				case 1:table.reload('account');
					break;
			}
		});
		// 復位賬戶表格
		function reloadAccount() {
			table.reload('account');
		}
		requestDataObj.permission.all(layer, drawingCallBack.loadPermissions);
		$("#reload").click(function() {
			$("#reload").text("正在加載權限...");
			requestDataObj.permission.reload(layer, drawingCallBack.loadPermissions, drawingCallBack.drawingFinishPermission);
		})
	})
	/*===權限管理樹狀圖pTree的相關操作===*/
	//全部展開
	$("#openAll").click(function(){
		$("#pTree span").addClass("jianhao").text("-");
		$("#pTree .childs").slideDown("fast");
		$("#pTree .grandChilds").slideDown("fast");
	});
	//全部收縮
	$("#closeAll").click(function(){
		$("#pTree span").removeClass("jianhao").text("+");
		$("#pTree .childs").slideUp("fast");
		$("#pTree .grandChilds").slideUp("fast");
	});
	$("#pTree span").addClass("noselect"); //禁止伸縮按鈕文本選中
	$("#pTree ul.childs:last").css("border-color","white");
	function jiaOrjian($this) { // + 或 -
		if($this.hasClass("jianhao")) {
			$this.removeClass("jianhao").text("+");
		} else {
			$this.addClass("jianhao").text("-");
		}
	}
	/*=====權限管理樹pTree全選事件=====*/
	var drawingCallBack = {
		roleNamePermissions: function(data){
			$("ul#pTree input[type='checkbox']").prop("checked", false);
			for (var i = 0; i < data.length; i++) {
				$("ul#pTree li[permissionId='" + data[i]["permissionId"] + "']").find("input[type='checkbox']").prop("checked",true);
				$("ul#pTree li[permissionId='" + data[i]["permissionId"] + "']").find("input[type='checkbox']").change();
			}
		},
		drawingFinishPermission: function(){
			$("#reload").text("加載所有權限");
		},
		loadPermissions: function(data) {
			// 遍历所有的权限
			$("ul#pTree").empty();
			for (var i = 0; i < data.length; i++) {
				if(data[i]["resource"].indexOf("*") >= 0) {
					// 说明当前资源属于叶子节点
					// 获取权限项
					var res_item = data[i]["resource"].substring(0, data[i]["resource"].indexOf(":"));
					$("<li permission_val='" + data[i]["resource"] + "' permission_item='" + res_item + "'><p class='parent'><span class='jianhao noselect'>-</span>" +
					  "<i class='shortTranLine'></i><input type='checkbox'><a>" + data[i]["name"] + "</a>" +
					  "</p><ul class='childs'></ul></li>").appendTo("ul#pTree");
				}
			}
			$("ul#pTree>li:last").find("ul.childs").css("border-color","white");
			for (var i = 0; i < data.length; i++) {
				if(data[i]["resource"].indexOf("*") < 0) {
					var res_item = data[i]["resource"].substring(0, data[i]["resource"].indexOf(":"));
					$("ul#pTree li[permission_item='" + res_item + "']").find("ul.childs")
						.append("<li permissionId='" + data[i]["id"] + "' permission_val='" + data[i]["resource"] + "' class='child'>" +
								"<i></i><i class='tranLine'></i><input type='checkbox'><a>" + data[i]["name"] + "</a></li>");
				}
			}
			var $allLi = $("ul#pTree li").find("ul.childs");
			$allLi.find("li").find("i:first").removeClass("lastLine");
			$allLi.find("li:last").find("i:first").addClass("lastLine");
			//父節點展開/收縮
			$("#pTree p.parent span").click(function(){
				$(this).parent().siblings(".childs").stop().slideToggle("fast");
				jiaOrjian($(this));
			});
			//子節點展開/收縮
			$("#pTree li.child span").click(function(){
				$(this).siblings(".grandChilds").stop().slideToggle("fast");
				jiaOrjian($(this));
			});
			//父節點的全選事件
			for(var c=0;c<$("#pTree .parent").length;c++){
				var $parentCheckbox = $("#pTree .parent").eq(c).find("input[type='checkbox']");
				var $childsCheckbox = $("#pTree .parent").eq(c).siblings().find(".child").find("input[type='checkbox']:first");
				var $grandChildsCheckbox = $("#pTree .parent").eq(c).siblings().find(".grandChilds").find("input[type='checkbox']");
				isAllChecked($parentCheckbox,$childsCheckbox);
				isAllChecked($parentCheckbox,$grandChildsCheckbox);
			}
			//子節點的全選事件
			for(var d=0;d<$("#pTree .child").length;d++){
				var $childCheckbox = $("#pTree .child").eq(d).find("input[type='checkbox']:first");
				var $grandChildsCheckbox = $("#pTree .child").eq(d).children(".grandChilds").find("input[type='checkbox']");
				isAllChecked($childCheckbox,$grandChildsCheckbox);
			}
			isAllChecked($("#pTreeAllChecked"), $("#pTree").find("input[type='checkbox']"));
		}
	}
});