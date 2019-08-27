// 定義的WebSocket的集合
var customWebSockets = function(userId, callBack) {
	var webSocketEvent = {
		open : function(){
			console.log("webSocketOpen");
		},
		close: function(){
			console.log("webSocketClose");
		},
		error: function(){
			console.log("webSocketError");
		}
	}
	var getWebSocket = function(userId) {
		var websocket = null;
		if ("WebSocket" in window) {
			websocket = new WebSocket("ws://" + window.location.host + "/ZGBWEBSSM/websocket/" + userId);
		} else if ('MozWebSocket' in window) {
			websocket = new MozWebSocket("ws://" + window.location.host + "/ZGBWEBSSM/websocket/" + userId);
		} else if ("SockJS" in window) {
			websocket = new SockJS("ws://" + window.location.host + "/ZGBWEBSSM/websocket/" + userId);
		} else {
			alert("對不起，妳的瀏覽器版本太低請升級到最新版本，否則部分功能妳將無法使用...");
			return;
		}
		return websocket;
	}
	return function() {
		var webSocket = getWebSocket(userId);
		if(null != webSocket) {
			webSocket.onopen = webSocketEvent.open;
			webSocket.onerror = webSocketEvent.error;
			webSocket.onclose = webSocketEvent.close;
			webSocket.onmessage = callBack;
			window.onbeforeunload = function(e) {
				console.log("關閉WebSocket");
				webSocket.close();
			}
		}
	}
}