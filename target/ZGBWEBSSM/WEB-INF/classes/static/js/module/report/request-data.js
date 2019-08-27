var requestDataObj = {
	printerTable: function(layTable, layer, searchUrl ,tableId){
		$.ajax({type: "GET", url:searchUrl, dataType:"JSON", 
				success: function(result){
					if(result.code == 600){
						layTable.exportFile(tableId, result.data, 'xls');
					}else{
						layer.msg(result.msg, {
								icon : 5
						});
					}
				},
				error: function(result){
					layer.alert("獲取[searchUrl: " + searchUrl + "]搜索訊息出現異常!", {
					icon : 2
				});
			}})
	},
	printer: function(layer, searchUrl, callBack){
		$.ajax({type: "GET", url:searchUrl, dataType:"JSON", 
				success: function(result){
					if(result.code == 600){
						callBack(result.data);
					}else{
						layer.msg(result.msg, {
							icon : 5
						});
					}
				},
				error: function(result){
					layer.alert("獲取[searchUrl: " + searchUrl + "]搜索訊息出現異常!", {
					icon : 2
				});
		}})
	},
	warn: {
		personHelp: function(layer, callBack){
			$.ajax({type: "GET", url:"report/warn/personhelp/", dataType:"JSON", 
    			success: function(result){
    				if(result.code == 600){
    					callBack(result.data);
    				} else {
    					layer.alert(result.msg,{icon: 5})
    				}
    			},
    			error: function(result){
    				console.log("獲取報表輸出人員求救訊息出現出現異常...");
    			}})
		},
		areaController: function(layer, callBack){
			$.ajax({type: "GET", url:"report/warn/areacontroller/", dataType:"JSON", 
    			success: function(result){
    				if(result.code == 600){
    					callBack(result.data);
    				} else {
    					layer.alert(result.msg,{icon: 5})
    				}
    			},
    			error: function(result){
    				console.log("獲取報表輸出人員求救訊息出現出現異常...");
    			}})
		},
		notMove: function(layer, callBack){
			$.ajax({type: "GET", url:"report/warn/notmove/", dataType:"JSON", 
    			success: function(result){
    				if(result.code == 600){
    					callBack(result.data);
    				} else {
    					layer.alert(result.msg,{icon: 5})
    				}
    			},
    			error: function(result){
    				console.log("獲取報表輸出人員求救訊息出現出現異常...");
    			}})
		},
		total: function(layer, url, callBack){// 获取记录总数量
			$.ajax({type: "GET", url: url, dataType:"JSON", 
    			success: function(result){
    				if(result.code == 600) {
    					callBack(result.data);
    				} else {
    					layer.alert(result.msg,{icon: 5})
    				}
    			},
    			error: function(result){
    				console.log("獲取報表輸出人員求救訊息出現出現異常...");
    		}})
		},
		search: function(layer, url, $table, callBack) {// 获取搜索到的记录讯息
			$.ajax({type: "GET", url: url, dataType:"JSON", 
    			success: function(result){
    				if(result.code == 0){
    					callBack($table, result.data);
    				} else {
    					layer.alert(result.msg,{icon: 5})
    				}
    			},
    			error: function(result){
    				console.log("獲取報表輸出人員求救訊息出現出現異常...");
    			}})
		},
		lowPower: function(layer, callBack){
			$.ajax({type: "GET", url:"report/warn/lowpower/", dataType:"JSON", 
    			success: function(result){
    				if(result.code == 600){
    					callBack(result.data);
    				} else {
    					layer.alert(result.msg,{icon: 5})
    				}
    			},
    			error: function(result){
    				console.log("獲取報表輸出人員求救訊息出現出現異常...");
    			}})
		},
		abnormalTag: function(layer, callBack){
			$.ajax({type: "GET", url:"report/warn/abnormaltag/", dataType:"JSON", 
    			success: function(result){
    				if(result.code == 600){
    					callBack(result.data);
    				} else {
    					layer.alert(result.msg,{icon: 5})
    				}
    			},
    			error: function(result){
    				console.log("獲取報表輸出人員求救訊息出現出現異常...");
    			}})
		},
		abnormalRefer: function(layer, callBack){
			$.ajax({type: "GET", url:"report/warn/abnormalrefer/", dataType:"JSON", 
    			success: function(result){
    				if(result.code == 600){
    					callBack(result.data);
    				} else {
    					layer.alert(result.msg,{icon: 5})
    				}
    			},
    			error: function(result){
    				console.log("獲取報表輸出人員求救訊息出現出現異常...");
    			}})
		},
		abnormalBase: function(layer, callBack){
			$.ajax({type: "GET", url:"report/warn/abnormalbase/", dataType:"JSON", 
    			success: function(result){
    				if(result.code == 600){
    					callBack(result.data);
    				} else {
    					layer.alert(result.msg,{icon: 5})
    				}
    			},
    			error: function(result){
    				console.log("獲取報表輸出人員求救訊息出現出現異常...");
    			}})
		}
	},
	initUi: {
		companys: function(layer, callBack){// 獲取所有的公司訊息
			$.ajax({type: "GET", url:"company/", dataType:"JSON",async: false,
					success: function(result){
						if(result.code == 600){
							callBack(result.data);
						} else {
							layer.msg(result.msg, {
								icon : 5
							});
						}
					},
					error: function(result){
						console.log("獲取所有的公司訊息出現異常...");
					}})
		},
		jobtypes: function(layer, callBack) {
			$.ajax({type: "GET", url:"jobType/",dataType: "JSON", async: false,
				success: function(result){
					if(result.code == 600){
						callBack(result.data);
					} else {
						layer.msg(result.msg,{icon: 5});
					}
				},
				error: function(result){
					console.log("獲取工種訊息出現異常...");
				}})
		},
		groups: function(layer, callBack) {
			$.ajax({type: "GET", url:"group/all/",dataType: "JSON", async: false,
				success: function(result){
					if(result.code == 600){
						callBack(result.data);
					} else {
						layer.msg(result.msg,{icon: 5});
					}
				},
				error: function(result){
					console.log("獲取工地訊息出現異常...");
				}})
		}
	},
	search: {
		personHelp: function(layer, callBack){// 搜索人員求救
			$.ajax({type: "GET", url:"report/personhelp/search", dataType:"JSON",
					success: function(result){
						if(result.code == 600){
							callBack(result.data);
						} else {
							layer.msg(result.msg, {
								icon : 5
							});
						}
					},
					error: function(result){
						console.log("查詢人員求救時出現異常");
					}})
		},
		access: function(layer, callBack){
			$.ajax({
				type : "POST",
				url : "",
				dataType : "JSON",
				contentType : "application/json;charset=utf-8",
				success : function(result) {
					if (result.code == 600) {
						callBack(data);
					} else {
						layer.msg(result.msg, {
							icon : 2
						})
					}
				},
				error : function(result) {
					layer.msg("搜索上下班記錄時出現異常!", {
						icon : 2
					})
				}
			})
		}
	}
}