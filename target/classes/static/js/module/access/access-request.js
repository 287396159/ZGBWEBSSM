var accessRequest = {
	doorControlStatus: function(layer, callBack) {
		$.ajax({type:"GET",url:"access/doorcontrol/status",dataType:"JSON",
			success: function(result){
				if(result.code == 600) {
					callBack(result.data);
				} else {
					layer.msg(result.msg, {
						icon : 5
					});
				}
			},
			error: function(result){
				layer.alert("獲取門控狀態出現異常！", {
					icon : 1
				});
			}})
	},
	setDoorControlStatus: function(layer, status ,callBack) {
		$.ajax({type: "PUT",url:"access/doorcontrol/status/" + status,dataType:"JSON",
			success: function(result){
				if(result.code == 600) {
					callBack(result.data);
				} else {
					layer.msg(result.msg,{icon: 5});
				}
			},
			error: function(result){
				layer.alert("設置門控狀態【status:" + status + "】出現異常！", {
					icon : 1
				});
			}})
	},
	addAccess: function(accessRecord, layer, callBack){
		$.ajax({type: "PUT", url: "access/", dataType: "JSON", async: false, contentType: "application/json;charset=utf-8",
				data: JSON.stringify(accessRecord),
				success: function(result){
					if(result.code == 600){
						callBack();
					} else {
						layer.msg(result.msg, {icon: 5});
					}
				},
				error: function(result){
					layer.alert("補填上下班記錄【" + JSON.stringify(accessRecord)  + "】時出現異常!",{icon: 2});
				}})
	},
	refers: function(groupId, layer, callBack){
		$.ajax({type: "GET",url: "enode/group/refer/" + groupId, dataType: "JSON",async: false,
			success: function(result){
				if(result.code == 600){
					callBack(result.data);
				} else {
					layer.msg(result.msg, {icon: 5});
				}
			},
			error: function(result){
				console.log("獲取節點設備出現異常...");
			}})
	},
	groups: function(layer,callBack){
		$.ajax({type : "GET", url : "group/all/", dataType : "JSON",async: false,
			success : function(result) {
				if (result.code == 600) {
					callBack(result.data);
				} else {
					layer.msg(result.msg, {
						icon : 5
					});
				}
			},
			error : function(result) {
				console.log("獲取組別訊息出現異常");
			}
		})
	},
	regions: function(groupId, callBack){
		$.ajax({type: "GET",url:"region/groupId/" + groupId,dataType:"JSON",
		success: function(result) {
			if(result.code == 600) {
				callBack(result.data);
			}
		}, error: function(result) {
			console.log("獲取組別【groupId: " + groupId  + "】的所有區域出現異常...");
		}})
	},
	groupsNumber: function(layer, callBack){
		$.ajax({
			type : "GET",
			url : "tagmsg/groups/",
			dataType : "JSON",
			success : function(result) {
				if(result.code == 600) {
					callBack(result.data);
				} else {
					layer.msg(result.msg, {icon: 5});
				}
			},
			error : function(result) {
				console.log("獲取組別卡片數量訊息出現異常...");
			}
		})
	},
	OnDuty: function(groupId, accessTime, layer, callBack){
		$.ajax({
			type : "GET",
			url : "access/onduty/" + groupId+"?accessTime="+accessTime,
			dataType : "JSON",
			success : function(result) {
				if (result.code == 600) {
					callBack(result.data);
				} else {
					console.log(result.msg);
				}
			},
			error : function(result) {
				console.log("獲取組別的人數訊息出現異常...");
			}
		})
	},
	OnDutyKey: function(groupId, accessTime, key, layer, callBack){
		$.ajax({
			type : "GET",
			url : "access/key/onduty/" + groupId+"?accessTime="+accessTime+"&key="+key,
			dataType : "JSON",
			success : function(result) {
				if (result.code == 600) {
					callBack(result.data);
				} else {
					console.log(result.msg);
				}
			},
			error : function(result) {
				console.log("獲取組別的人數訊息出現異常...");
			}
		})
	},
	OffDuty: function(groupId, accessTime, layer, callBack){
		$.ajax({
			type : "GET",
			url : "access/offduty/" + groupId+"?accessTime="+accessTime,
			dataType : "JSON",
			success : function(result) {
				if (result.code == 600) {
					callBack(result.data);
				} else {
					console.log(result.msg);
				}
			},
			error : function(result) {
				console.log("獲取組別的人數訊息出現異常...");
			}
		})
	},
	OffDutyKey: function(groupId, accessTime, key, layer, callBack){
		$.ajax({
			type : "GET",
			url : "access/key/offduty/" + groupId+"?accessTime="+accessTime+"&key="+key,
			dataType : "JSON",
			success : function(result) {
				if (result.code == 600) {
					callBack(result.data);
				} else {
					console.log(result.msg);
				}
			},
			error : function(result) {
				console.log("獲取組別的人數訊息出現異常...");
			}
		})
	},
	deleteAccess: function(layer, groupId, access , callBack){
		$.ajax({
			type : "POST",
			url : "access/delete/" + groupId,
			dataType : "JSON",
			data: JSON.stringify(access), 
			contentType : "application/json;charset=utf-8",
			success : function(result) {
				if(result.code == 600){
					layer.msg("刪除山下班記錄成功!",{icon: 6});
					callBack();
				} else {
					layer.msg(result.msg, {
						icon : 5
					});
				}
			},
			error : function(result) {
				layer.msg("刪除上下班記錄【" + JSON.stringify(access) + "】出現異常！", {
					icon : 2
				});
			}
		})
	}
}