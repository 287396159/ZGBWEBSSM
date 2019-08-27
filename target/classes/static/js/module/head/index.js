$(function(){// 網頁頭部分的 js 代碼
	layui.use('layer', function(){
		var layer = layui.layer;
		// 獲取當前的監聽狀態
		monitorControllerObj.state(layer, monitorDrawingObj.stateCallBack);
		$("#connect").off("click").on("click",function(){
			// 判斷監聽狀態
			var state = $("#connect").parent("div.headRight").attr("state");
			if(state == 'true'){// 當前處在開啟連接的狀態
				monitorControllerObj.close(layer, monitorDrawingObj.closeCallBack);
			} else {// 當前處在結束連接的狀態
				monitorControllerObj.start(layer, monitorDrawingObj.startCallBack);
			}
		})
		var webSocketMessage = function(msg) {
			var result = JSON.parse(msg.data)
			if(result.type == 2) {
				monitorDrawingObj.stateCallBack(result.data);
				$("#connect").parent("div.headRight").attr("state", result.data);
			} else if(result.type == 3) {
				layer.msg(result.msg, {
					icon : 5
				});
				// 5s 刷新頁面跳轉到登陸
				setTimeout(function() {
					window.location.reload();
				}, 5000);
			}
		}
		// WebSocket
		var webSoketUserId = $(window.parent.document).find("p.user").text();
		customWebSockets("WebSocketContainer/" + webSoketUserId, webSocketMessage)();
	});
	var monitorDrawingObj = { // 監聽渲染對象
		startCallBack: function(){ // 開啟連接後的回調函數
			$("#connect").parent("div.headRight").attr("state", true);
			$("#connect>span").text("結束連接");
		},
		closeCallBack: function(){ // 結束連接後的回調函數
			$("#connect").parent("div.headRight").attr("state", false);
			$("#connect>span").text("開啟連接");
		},
		stateCallBack: function(state){ // 獲取監聽狀態後的回調函數
			$("#connect").parent("div.headRight").attr("state", state);
			if(state){// 當前處在監聽的狀態
				$("#connect>span").text("結束連接");
			}else{
				$("#connect>span").text("開啟連接");
			}
		}
	}
})