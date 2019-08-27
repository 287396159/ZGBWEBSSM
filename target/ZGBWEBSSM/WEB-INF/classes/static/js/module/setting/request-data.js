// 請求數據對象
var staffMaintenanceReq = {
	update: function(person, layer, table, index) {// 更新人員訊息
		$.ajax({type: "POST", url: "person/" + person["serialNumber"],
			contentType: "application/json;charset=utf-8",
			dataType: "JSON", data: JSON.stringify(person),
			success: function(result) {
				if(result.code == 600) {
					layer.msg("設置人員資料成功...", {icon: 6});
					// 重新刷新表格
					table.reload();
					// 關閉表單窗口
					layer.close(index);
				} else {
					layer.msg(result.msg, {icon: 5});
				}
			},
			error: function(result){
				console.error("設置人員資料出現異常...");
			}})
	},
	add : function(person, layer, table, index) {// 添加人員訊息
		$.ajax({
			type : "PUT",
			url : "person/",
			contentType : "application/json;charset=utf-8",
			dataType : "JSON",
			data : JSON.stringify(person),
			success : function(result) {
				if (result.code == 600) {
					layer.msg("添加人員資料成功...", {
						icon : 6
					});
					// 重新刷新表格
					table.reload();
					// 關閉表單窗口
					layer.close(index);
				} else {
					layer.msg(result.msg, {
						icon : 5
					});
				}
			},
			error : function(result) {
				console.error("添加人員資料出現異常...");
			}
		})
	},
	downloadCards: function(name) {
		window.location.href = "person/card/download/" + name;
	},
	createCardsWord: function(layer, nos, callBack) { // 根据编号
		layer.msg("正在生成人员资料卡,请稍后...", {icon: 16, time: 0});
		$.ajax({type:"GET", url:"person/export/card/" + nos, dataType:"JSON", 
			success: function(result){
				if(result.code == 600){
					layer.msg("服務器端創建資料卡文件成功, 正在下載資料卡文件", {
						icon : 6
					});
					if(callBack) {
						callBack(result.data);
					}
				} else {
					layer.msg(result.msg, {
						icon : 5
					});
				}
			}, 
			error: function(result) {
				console.log("创建资料卡文件出现异常");
		}})
		
	},
	remove: function(no, layer, table, index) {
		$.ajax({type: "DELETE", url: "person/" + no, dataType: "JSON", 
			success: function(result){
				if(result.code == 600){
					layer.msg("刪除人員訊息【no: " + no + "】成功...", {icon: 6});
					// 重新刷新頁面
					table.reload();
				} else {
					layer.msg(result.msg, {icon: 5});
				}
			},
			error: function(result){
				console.error("刪除人員訊息【no: " + data["no"] + "】出現異常...");
			}
		})
		layer.close(index);
	},
	groups: function(callBack) {
		$.ajax({
			type : "GET",
			url : "group/all/",
			dataType : "JSON",
			async : false,
			success : function(result) {
				if (result.code == 600) {
					callBack(result.data);
				}
			},
			error : function() {
				console.log("请求工种讯息出现异常...");
			}
		})
	},
	jobTypes : function(callBack) {
		$.ajax({
			type : "GET",
			url : "jobType/",
			dataType : "JSON",
			async : false,
			success : function(result) {
				if (result.code == 600) {
					callBack(result.data);
				}
			},
			error : function() {
				console.log("请求工种讯息出现异常...");
			}
		})
	},
	/*---获取公司资料---*/
	companys : function(callBack) {
		$.ajax({
			type : "GET",
			url : "company/",
			dataType : "JSON",
			async : false,
			success : function(result) {
				if (result.code == 600) {
					callBack(result.data);
				}
			},
			error : function(result) {
				console.log("请求公司讯息出现异常...");
			}
		})
	}
}
 /*---工種請求---*/
var jobTypeReq = {
   add: function(layer, layTable, winIndex, data) {
	   $.ajax({
			type : "PUT",
			url : "jobType/",
			dataType : "JSON",
			contentType : "application/json;charset=utf-8",
			data: JSON.stringify(data),
			success : function(result) {
				if(result.code == 600) {
					layer.msg("添加工種訊息成功...", {icon: 6});
					// 刷新
					layTable.reload();
					// 關閉編輯框
					layer.close(winIndex);
				} else {
					layer.msg(result.msg, {icon: 5});
				}
			},
			error : function(result) {
				console.log("添加工種訊息時出現異常...");
			}
		});
   },
   edit: function(layer, jobTypeTable , winIndex, data) {// 編輯工種
	   $.ajax({type : "POST",
			url : "jobType/" + data["no"],
			dataType : "JSON",
			data : JSON.stringify(data),
			contentType: "application/json;charset=utf-8",
			success : function(result) {
				if(result.code == 600) {
					layer.msg("修改工種訊息成功...", {icon: 6});
					// 刷新
					jobTypeTable.reload();
					// 關閉編輯框
					layer.close(winIndex);
				} else {
					layer.msg(result.msg, {icon: 5});
				}
			},
			error : function(result) {
				console.error("修改工種訊息出現異常...");
			}
		});
   },
}

var requestDataObj = {
	groups: function(layer, callBack){
		$.ajax({
			type : "GET",
			url : "group/all/",
			dataType : "JSON",
			async : false,
			success : function(result) {
				if (result.code == 600) {
					callBack(result.data);
				} else {
					layer.msg(result.msg, {
						icon : 5
					})
				}
			},
			error : function(result) {
				layer.msg("獲取所有的組別訊息出現異常!", {
					icon : 2
				})
			}
		})
	},
	regions : function(layer,callBack) { // 獲取指定組別所有的區域對象
		$.ajax({
			type : "GET",
			url : "region/all/",
			dataType : "JSON",
			async : false,
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
				layer.alert("獲取組別【id: " + groupId + "】的所有的區域訊息出現異常!", {
					icon : 2
				});
			}
		})
	},
	nodes: function(layer, callBack) {
		$.ajax({
			type : "GET",
			url : "enode/all/",
			dataType : "JSON",
			async : false,
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
				layer.alert("獲取所有基站出現異常!", {
					icon : 2
				});
			}
		})
	}, 
	companyReliableNodes: {
		add: function(layer, companyNo, ids, createName, callBack, $dom) {
			$.ajax({
				type : "POST",
				url : "reliablenodes/add/company/" + createName + "/" + companyNo + "/",
				dataType : "JSON",
				contentType : "application/json;charset=utf-8",
				data : JSON.stringify(ids),
				success : function(result) {
					if (result.code == 600) {
						callBack(companyNo, $dom);
					} else {
						layer.msg(result.msg, {
							icon : 5
						});
					}
				},
				error : function(result) {
					layer.msg("添加可靠近的參考點出現異常...", {
						icon : 2
					});
				}
			})
		},
		list: function(layer, companyNo, $dom ,callBack) {
			$.ajax({
				type : "GET",
				url : "reliablenodes/all/company/" + companyNo,
				dataType : "JSON",
				success : function(result) {
					if (result.code == 600) {
						callBack(result.data, $dom);
					} else {
						layer.msg(result.msg, {
							icon : 5
						});
					}
				},
				error : function(result) {
					layer.msg("獲取【no: " + personNo + "】可靠近的參考點出現異常", {
						icon : 2
					});
				}
			})
		}
	},
	reliableNodes: {
		add: function(layer, personNo, ids, createName, callBack, $dom){
			$.ajax({
				type : "POST",
				url : "reliablenodes/add/person/" + createName + "/" + personNo + "/",
				dataType : "JSON",
				contentType: "application/json;charset=utf-8",
				data: JSON.stringify(ids),
				success : function(result) {
					if (result.code == 600) {
						callBack(personNo, $dom);
					} else {
						layer.msg(result.msg, {
							icon : 5
						});
					}
				},
				error : function(result) {
					layer.msg("添加可靠近的參考點出現異常...", {
						icon : 2
					});
				}
			})
		},
		list: function(layer, personNo, $dom ,callBack) {
			$.ajax({
				type : "GET",
				url : "reliablenodes/all/person/" + personNo,
				dataType : "JSON",
				success : function(result) {
					if (result.code == 600) {
						callBack(result.data, $dom);
					} else {
						layer.msg(result.msg, {
							icon : 5
						});
					}
				},
				error : function(result) {
					layer.msg("獲取【no: " + personNo + "】可靠近的參考點出現異常", {
						icon : 2
					});
				}
			})
		}
	}
}