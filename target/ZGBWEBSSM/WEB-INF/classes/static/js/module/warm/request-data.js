var warmDataObj = {
	personHelp: {// 人員求救
		handle: function(uuids, layer, callBack){
			$.ajax({type:"POST",url:"warm/personHelp/handle/"+uuids,dataType:"JSON",
				success: function(result){
					if(result.code == 600){
						callBack();
						layer.closeAll();
					}else{
						layer.msg(result.msg, {icon: 5})
					}
				},
				error: function(result){
					layer.alert("處理人員求救資料【id: " + uuids + "】出現異常！",{icon: 2});
				}})
		},
		clear: function(uuids, layer, callBack){
			$.ajax({type:"POST",url:"warm/personHelp/clear/"+uuids,dataType:"JSON",
				success: function(result){
					if(result.code == 600){
						callBack();
						layer.closeAll();
					}else{
						layer.msg(result.msg, {icon: 5})
					}
				},
				error: function(result){
					layer.alert("清除人員求救資料【id: " + uuids + "】出現異常！",{icon: 2});
				}})
		},
	},
	areaControl: {// 區域管制
		handle: function(uuids, layer, callBack){
			$.ajax({type:"POST",url:"warm/areacontrol/handle/"+uuids,dataType:"JSON",
				success: function(result){
					if(result.code == 600){
						callBack();
						layer.closeAll();
					}else{
						layer.msg(result.msg, {icon: 5})
					}
				},
				error: function(result){
					layer.alert("處理區域管制資料【id: " + uuids + "】出現異常！",{icon: 2});
				}})
		},
		clear: function(uuids, layer, callBack){
			$.ajax({type:"POST",url:"warm/areacontrol/clear/"+uuids,dataType:"JSON",
				success: function(result){
					if(result.code == 600){
						callBack();
						layer.closeAll();
					}else{
						layer.msg(result.msg, {icon: 5})
					}
				},
				error: function(result){
					layer.alert("清除區域管制資料【id: " + uuids + "】出現異常！",{icon: 2});
				}})
		}
	},
	notMove: {// 卡片未移動
		handle: function(uuids, layer, callBack){
			$.ajax({type:"POST",url:"warm/notMove/handle/"+uuids,dataType:"JSON",
				success: function(result){
					if(result.code == 600){
						callBack();
						layer.closeAll();
					}else{
						layer.msg(result.msg, {icon: 5})
					}
				},
				error: function(result){
					layer.alert("處理未移動報警資料【id: " + uuids + "】出現異常！",{icon: 2});
				}})
		},
		clear: function(uuids, layer, callBack){
			$.ajax({type:"POST",url:"warm/notMove/clear/"+uuids,dataType:"JSON",
				success: function(result){
					if(result.code == 600){
						callBack();
						layer.closeAll();
					}else{
						layer.msg(result.msg, {icon: 5})
					}
				},
				error: function(result){
					layer.alert("清除未移動報警資料【id: " + uuids + "】出現異常！",{icon: 2});
				}})
		}
	},
	lowBat: {// 電量不足
		handle: function(uuids, layer, callBack){
			$.ajax({type:"POST",url:"warm/lowPower/handle/"+uuids,dataType:"JSON",
				success: function(result){
					if(result.code == 600){
						callBack();
						layer.closeAll();
					}else{
						layer.msg(result.msg, {icon: 5})
					}
				},
				error: function(result){
					layer.alert("處理低電量報警資料【id: " + uuids + "】出現異常！",{icon: 2});
				}})
		},
		clear: function(uuids, layer, callBack){
			$.ajax({type:"POST",url:"warm/lowPower/clear/"+uuids,dataType:"JSON",
				success: function(result){
					if(result.code == 600){
						callBack();
						layer.closeAll();
					}else{
						layer.msg(result.msg, {icon: 5})
					}
				},
				error: function(result){
					layer.alert("清除低電量報警資料【id: " + uuids + "】出現異常！",{icon: 2});
				}})
		}
	},
	abnormalTag: {// 卡片不正常
		handle: function(uuids, layer, callBack){
			$.ajax({type:"POST",url:"warm/abnormalTag/handle/"+uuids,dataType:"JSON",
				success: function(result){
					if(result.code == 600){
						callBack();
						layer.closeAll();
					}else{
						layer.msg(result.msg, {icon: 5})
					}
				},
				error: function(result){
					layer.alert("處理卡片異常報警資料【id: " + uuids + "】出現異常！",{icon: 2});
				}})
		},
		clear: function(uuids, layer, callBack){
			$.ajax({type:"POST",url:"warm/abnormalTag/clear/"+uuids,dataType:"JSON",
				success: function(result){
					if(result.code == 600){
						callBack();
						layer.closeAll();
					}else{
						layer.msg(result.msg, {icon: 5})
					}
				},
				error: function(result){
					layer.alert("清除卡片異常報警資料【id: " + uuids + "】出現異常！",{icon: 2});
				}})
		}
	},
	abnormalNode: {// 節點異常
		handle: function(uuids, layer, callBack){
			$.ajax({type:"POST",url:"warm/abnormalBase/handle/"+uuids,dataType:"JSON",
				success: function(result){
					if(result.code == 600){
						callBack();
						layer.closeAll();
					}else{
						layer.msg(result.msg, {icon: 5})
					}
				},
				error: function(result){
					layer.alert("處理數據節點異常報警資料【id: " + uuids + "】出現異常！",{icon: 2});
				}})
		},
		clear: function(uuids, layer, callBack){
			$.ajax({type:"POST",url:"warm/abnormalBase/clear/"+uuids,dataType:"JSON",
				success: function(result){
					if(result.code == 600){
						callBack();
						layer.closeAll();
					}else{
						layer.msg(result.msg, {icon: 5})
					}
				},
				error: function(result){
					layer.alert("清除數據節點異常報警資料【id: " + uuids + "】出現異常！",{icon: 2});
				}})
		}
	},
	abnormalRefer: {// 參考點異常
		handle: function(uuids, layer, callBack){
			$.ajax({type:"POST",url:"warm/abnormalRefer/handle/"+uuids,dataType:"JSON",
				success: function(result){
					if(result.code == 600){
						callBack();
						layer.closeAll();
					}else{
						layer.msg(result.msg, {icon: 5})
					}
				},
				error: function(result){
					layer.alert("處理參考點異常報警資料【id: " + uuids + "】出現異常！",{icon: 2});
				}})
		},
		clear: function(uuids, layer, callBack){
			$.ajax({type:"POST",url:"warm/abnormalRefer/clear/"+uuids,dataType:"JSON",
				success: function(result){
					if(result.code == 600){
						callBack();
						layer.closeAll();
					}else{
						layer.msg(result.msg, {icon: 5})
					}
				},
				error: function(result){
					layer.alert("清除參考點異常報警資料【id: " + uuids + "】出現異常！",{icon: 2});
				}})
		}
	}
}