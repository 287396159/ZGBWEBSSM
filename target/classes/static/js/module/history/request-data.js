var trackRequestObj = {
	tagInfor : function(tagInfor, callBack) {
		$.ajax({
			type : "GET",
			url : "track/tag/" + tagInfor,
			dataType : "JSON",
			success : function(result) {
				if (result.code == 600) {
					callBack(result.data);
				} else {
					console.log(result.msg);
				}
			},
			error : function(result) {
				console.log("根據搜索資料獲取卡片訊息出現異常！");
			}
		})
	},
	groups: function(callBack) {
		$.ajax({
			type : "GET",
			url : "group/all/",
			dataType : "JSON",
			success : function(result) {
				if (result.code == 600) {
					callBack(result.data);
				} else {
					console.log(result.msg);
				}
			},
			error : function(result) {
				console.log("獲取所有的組別訊息出現異常...");
			}
		})
	},
	regions: function(groupId, callBack){
		$.ajax({
			type : "GET",
			url : "region/groupId/" + groupId,
			dataType : "JSON",
			async: false,
			success : function(result) {
				if (result.code == 600) {
					callBack(result.data);
				} else {
					console.log(result.msg);
				}
			},
			error : function(result) {
				console.log("獲取組別【group: 	" + groupId + "】所有的區域訊息出現異常...");
			}
		})
	},
	nodes: function(regionId, callBack){
		$.ajax({
			type : "GET",
			url : "enode/region/" + regionId,
			dataType : "JSON",
			async: false,
			success : function(result) {
				if (result.code == 600) {
					callBack(result.data);
				} else {
					console.log(result.msg);
				}
			},
			error : function(result) {
				console.log("獲取區域【region: 	" + regionId + "】所有的組別訊息出現異常...");
			}
		})
	}
}