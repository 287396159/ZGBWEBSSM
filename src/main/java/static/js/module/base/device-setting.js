$.namespace("devices.self.setting");
devices.self.setting = function() {
	return {
		self : {// 自身參數讀取和設置
			read : function(id, keyName, callBack) {
				$.ajax({
					type : "GET",
					url : "setting/read/" + id + "/" + keyName,
					dataType : "JSON",
					success : function(result) {
						callBack(id, result);
					},
					error : function(result) {
						console.log("讀取設備信息【id: " + id + ", keyName: " + keyName + "】出現異常...");
					}
				})
			},
			read_Param : function(id, keyName, keyValue, callBack) {
				$.ajax({
					type : "GET",
					url : "setting/read/" + id + "/" + keyName + "/" + keyValue,
					dataType : "JSON",
					success : function(result) {
						callBack(id, result);
					},
					error : function(result) {
						console.log("讀取設備信息【id: " + id + ", keyName: "
								+ keyName + ", keyValue: " + keyValue
								+ "】出現異常...");
					}
				})
			},
			set_Param : function(id, keyName, keyValue, callBack) {
				$.ajax({
					type : "GET",
					url : "setting/set/" + id + "/" + keyName,
					dataType : "JSON",
					data : {
						paramVal : keyValue
					},
					success : function(result) {
						callBack(id, result);
					},
					error : function(result) {
						console.log("設置設備訊息【id: " + id + ", keyName: "
								+ keyName + ", keyValue: " + keyValue
								+ "】出現異常...");
					}
				})
			}
		},
		arround: {
			set: function(id, routerId, keyName, keyValue ,callBack) {
				$.ajax({
					type : "GET",
					url : "arround/refer/set/" + id + "/" + keyName + "/" + routerId,
					dataType : "JSON",
					data: {
						paramValue: keyValue
					},
					success : function(result) {
						callBack(id, routerId ,result);
					},
					error : function(result) {
						console.log("通過基站讀取周圍設備訊息【id: " + id + ", KeyName: "
								+ keyName + ", routerId: " + routerId
								+ "】出現異常...");
					}
				})
			},
			read: function(id, routerId, keyName, callBack) {
				$.ajax({
					type : "GET",
					url : "arround/refer/read/" + id + "/" + keyName + "/" + routerId,
					dataType : "JSON",
					success : function(result) {
						callBack(id, routerId ,result);
					},
					error : function(result) {
						console.log("通過基站讀取周圍設備訊息【id: " + id + ", KeyName: "
								+ keyName + ", routerId: " + routerId
								+ "】出現異常...");
					}
				})
			},
			readNode: function(id, routerId, channel, keyName, callBack) {
				$.ajax({
					type : "GET",
					url : "arround/node/read/" + id + "/" + keyName + "/" + routerId + "/" + channel,
					dataType : "JSON",
					success : function(result) {
						callBack(id, routerId, channel, result);
					},
					error : function(result) {
						console.log("通過基站讀取周圍基站訊息【id: " + id + ", keyName: "
								+ keyName + ", routerId: " + routerId
								+ ", channel: " + channel + "】出現異常...");
					}
				})
			},
			readNode_Param: function(id, routerId, channel, keyName, keyParam ,callBack) {
				$.ajax({
					type : "GET",
					url : "arround/node/read/" + id + "/" + keyName + "/" + keyParam + "/" + routerId + "/" + channel,
					dataType : "JSON",
					success : function(result) {
						callBack(id, routerId, channel, result);
					},
					error : function(result) {
						console.log("通過基站讀取周圍基站訊息【id: " + id + ", keyName: "
								+ keyName + ", keyParam: " + keyParam + ", routerId: " + routerId
								+ ", channel: " + channel + "】出現異常...");
					}
				})
			},
			readNodeTimeOut_Param: function(id, routerId, channel, keyName, timeOut, keyParam ,callBack) {
				$.ajax({
					type : "GET",
					url : "arround/node/read/param/" + id + "/" + keyName + "/" + timeOut + "/" + routerId + "/" + channel,
					dataType : "JSON",
					data: {"keyParam":keyParam},
					success : function(result) {
						callBack(id, routerId, channel, result);
					},
					error : function(result) {
						console.log("通過基站讀取周圍基站訊息【id: " + id + ", keyName: "
								+ keyName + ", timeOut: " + timeOut + ", keyParam: " + keyParam + ", routerId: " + routerId
								+ ", channel: " + channel + "】出現異常...");
					}
				})
			},
			setNode: function(id, routerId, channel, keyName, keyParam,  callBack) {
				$.ajax({
					type : "GET",
					url : "arround/node/set/" + id + "/" + keyName + "/" + keyParam + "/" + routerId + "/" + channel,
					dataType : "JSON",
					success : function(result) {
						callBack(id, result);
					},
					error : function(result) {
						console.log("通過基站設置周圍基站訊息【id: " + id + ", keyName: " + keyName + 
								", keyParam: " + keyParam + ", routerId: " + routerId
								+ ", channel: " + channel + "】出現異常...");
					}
				})
			},
			search : function(id, keyName, callBack) {
				$.ajax({
					type : "GET",
					url : "arround/search/" + id + "/" + keyName,
					dataType : "JSON",
					success : function(result) {
						callBack(id, result);
					},
					error : function(result) {
						console.log("搜索周圍的設備出現異常...");
					}
				})
			}
		}
	}
}();