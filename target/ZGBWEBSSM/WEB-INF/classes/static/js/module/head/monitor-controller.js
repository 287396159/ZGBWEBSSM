/**
 * monitor 控制器對象
 */
var monitorControllerObj = {
	state: function(layer, callBack){// 查看當前的網絡監聽狀態
		$.ajax({type: "GET", url: "monitor/state", dataType: "JSON", 
			success: function(result) {	
				if(result.code == 600) {
					// 獲取網絡監聽狀態成功後，調用回調函數
					callBack(result.data);
				} else {
					layer.msg(result.msg, {icon: 0, time: 1500});
				}
			},
			error: function(result) {
				layer.alert("獲取網絡監聽狀態出現異常!", {icon: 2});
			}
		})
	},
	start: function(layer, callBack){// 開啟網絡監聽
		$.ajax({type: "GET", url: "monitor/start", dataType: "JSON", 
			success: function(result) {
				if(result.code == 600) {
					layer.msg("開啟網絡監聽成功...",{icon: 1, time: 1500});
					callBack();
				} else {
					layer.msg(result.msg, {icon: 0, time: 1500});
				}
			},
			error: function(result){
				layer.alert("開啟網絡監聽出現異常!", {icon: 2});
			}})
	},
	close: function(layer, callBack){// 結束網絡監聽
		$.ajax({type: "GET", url:"monitor/close", dataType:"JSON", 
			success: function(result){
				if(result.code == 600){
					layer.msg("關閉網絡監聽成功...", {icon: 1, time: 1500});
					callBack();
				} else {
					layer.msg(result.msg, {icon: 0, time: 1500});
				}
			},
			error: function(result){
				layer.alert("關閉網絡監聽出現異常!", {icon: 2});
			}})
	}
}