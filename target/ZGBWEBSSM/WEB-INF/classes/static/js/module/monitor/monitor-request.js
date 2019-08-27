var monitorRequestObj = {// 監聽數據請求對象
	groups : function(layer, callBack) {// 獲取所有的組別對象
		$.ajax({type : "GET", url : "group/all/", dataType : "JSON", async: false,
			success : function(result) {
				if (result.code == 600) {
					callBack(result.data);
				} else {
					layer.msg(result.msg, {
						icon : 5,
						time : 1500
					});
				}
			},
			error : function(result) {
				layer.alert("獲取所有的組別訊息出現異常!", {
					icon : 2
				});
			}
		})
	},
	regions : function(layer, callBack) { // 獲取指定組別所有的區域對象
		$.ajax({type : "GET", url : "region/all/", dataType : "JSON", async: false,
			success : function(result) {
				if (result.code == 600) {
					callBack(result.data);
				} else {
					layer.msg(result.msg, {
						icon : 5,
						time : 1500
					});
				}
			},
			error : function(result) {
				layer.alert("獲取組別【id: " + groupId + "】的所有的區域訊息出現異常!", {
					icon : 2
				});
			}
		})
	},
	jobTypes : function(layer, callBack) {// 獲取所有的工種類型
		$.ajax({type : "GET", url : "jobType/timely/", dataType : "JSON",
			success : function(result) {
				if (result.code == 600) {
					callBack(result.data);
				} else {
					layer.msg(result.msg, {
						icon : 0,
						time : 1500
					});
				}
			},
			error : function(result) {
				layer.alert("獲取所有的工種訊息出現異常!", {
					icon : 2
				});
			}
		})
	},
	nodes: function(regionId, layer, callBack) {
		$.ajax({type : "GET", url : "enode/region/"+regionId, dataType : "JSON", async: false,
			success : function(result) {
				if(result.code == 600){
					callBack(result.data);
				} else {
					layer.msg(result.msg, {icon: 5});
				}
			},
			error : function(result) {
				layer.alert("獲取區域【id: " + regionId + "】的所有基站出現異常!", {
					icon : 2
				});
			}
		})
	},
	searchTag: function(searchText, layer, callBack){
		$.ajax({type: "GET", url: "tagmsg/search/"+searchText, dataType: "JSON", async: false,
			success: function(result){
					if(result.code == 600){
						callBack(result.data, layer);
					} else {
						layer.msg(result.msg, {icon: 5});
					}
			},
			error: function(error){
					layer.alert("搜索卡片【searchText: " + searchText + "】出現異常...",{icon: 2});
			}})
	}
}