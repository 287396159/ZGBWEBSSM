/***
 * 人员资料维护
 */
var staffMaintenance = {
	bloodType: function(bloodType) {
		// 设置血型
		$("#personnelForm select[name='bloodType']>option").prop("selected", false);
		$("#personnelForm select[name='bloodType']>option[value='" + bloodType + "']").prop("selected", true);
	},
	groups: function(data) {
		$("#personnelForm select[name='group_id']").empty();
		var options = ["<option value='-1'>請選擇工地</option>"];
		for (var i = 0; i < data.length; i++) {
			if (data[i]["id"] == $("#personnelForm select[name='group_id']")
					.attr("option")) {
				options.push("<option selected='true' value='" + data[i]["id"]
						+ "'>" + data[i]["name"] + "</option>");
			} else {
				options.push("<option value='" + data[i]["id"] + "'>"
						+ data[i]["name"] + "</option>");
			}
		}
		$("#personnelForm select[name='group_id']").append(options.join(""));
		var $option = $("#personnelForm select[name='group_id']>option[selected='true']");
		if(!$option) {
			$("#personnelForm select[name='group_id']>option:first").prop("selected", true);
		}
	},
	companys: function(data) {
		$("#personnelForm select[name='company_No']").empty();
		var options = ["<option value=''>請選擇公司</option>"];
		for (var i = 0; i < data.length; i++) {
			if (data[i]["no"] == $("#personnelForm select[name='company_No']")
					.attr("option")) {
				options.push("<option selected='true' value='" + data[i]["no"]
						+ "'>" + data[i]["name"] + "</option>");
			} else {
				options.push("<option value='" + data[i]["no"] + "'>"
						+ data[i]["name"] + "</option>");
			}
		}
		$("#personnelForm select[name='company_No']").append(options.join(""));
		var $option = $("#personnelForm select[name='company_No']>option[selected='true']");
		if(!$option) {
			$("#personnelForm select[name='company_No']>option:first").prop("selected", true);
		}
	},
	jobTypes: function(data) {// 将公司讯息加载到选项中
		$("#personnelForm select[name='jobType_No']").empty();
		var options = ["<option value=''>請選擇工种</option>"];
		for (var i = 0; i < data.length; i++) {
			if (data[i]["no"] == $("#personnelForm select[name='jobType_No']").attr("option")) {
				options.push("<option selected='true' value='" + data[i]["no"]
						+ "'>" + data[i]["name"] + "</option>");
			} else {
				options.push("<option value='" + data[i]["no"] + "'>"
						+ data[i]["name"] + "</option>");
			}
		}
		$("#personnelForm select[name='jobType_No']").append(options.join(""));
		var $option = $("#personnelForm select[name='jobType_No']>option[selected='true']");
		if(!$option) {
			$("#personnelForm select[name='jobType_No']>option:first").prop("selected", true);
		}
	},		
	personnelDataCardWin: function(layer, data) {// 显示人员资料卡窗体
		// 工地名称
		if(!data.group_Name) {
			layer.msg("你還沒有選擇工地", {icon: 5});
			return;
		}
		var imgPath = data.imgPath ? ("staff/" + data.imgPath) : "img/sysperson.jpg";
		$("#personalInfoWindow img.photo").attr("src", imgPath);
		// 人员名称
		$("#personalInfoWindow span[name]").text(getMaxLen(data.name, 7));
		// 血型
		$("#personalInfoWindow span[bloodType]").text(getMaxLen(data.bloodType, 6));
		// 工种
		$("#personalInfoWindow span[jobType]").text(getMaxLen((data.jobType_Name || data.jobType_No), 15));
		var serialNumber = PrefixInteger(data.serialNumber, 6);
		// 条形码
		$("#personalInfoWindow span[serialNumber]").text(serialNumber);
		$("#personalInfoWindow p[license]").text(getMaxLen(data.group_Name, 9) + " 識別證");
		// 生成二维码
		$("#personalInfoWindow div[qrcode]").empty();
		var qrCode = new QRCode($("#personalInfoWindow div[qrcode]")[0], serialNumber);
		layer.open({type : 1, content : $("#personalInfoWindow"), title : "查看人員資料卡"});
	}
}