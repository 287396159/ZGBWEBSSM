// 請求數據對象
var staffMaintenanceReq = {
	exportTemplate: function(layer) {
		$.ajax({
			type : "GET",
			url : "person/export/",
			dataType : "JSON",
			success : function(result) {
				if (result.code == 600) {
					window.location.href = "person/upload/";
					layer.alert(result.data, {
						icon : 6
					});
				} else {
					layer.alert(result.msg, {
						icon : 5
					});
				}
			},
			error : function(result) {
				layer.alert("導出模板文件出現異常...", {
					icon : 2
				});
			}
		})
	},
	upload: function(userName) {// 上傳人員資料
		var waitIndex;
		// 運行的上下文必須是layui對象, 需要用call或apply改變運行時的上下文對象
		this.upload.render({
			elem : '#importPersonBtn', // 绑定元素
			url : 'person/import/' + userName, // 上传接口
			accept: 'file',
			exts: 'xls|xlsx',
			before: function(obj) {
				waitIndex = layer.msg("正在導入人員資料訊息, 請稍後...", {icon: 16});
			},
			done : function(res) {// 上传完毕回调
				if(res.code == 600) {
					layer.msg("導入人員記錄成功！",{icon: 6});
				} else {
					layer.alert(res.msg, {icon: 5});
				}
				layer.close(waitIndex);
			}, error : function() {// 请求异常回调
				layer.alert("導入人員記錄出現異常...");
			}
		});
	},
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
   exportTemplate: function(layer) {// 導出工程模板
		   $.ajax({ type : "GET", url : "jobType/export/", dataType : "JSON",
			success : function(result) {
				if (result.code == 600) {
					window.location.href = "jobType/upload/";
					layer.alert(result.data, {
						icon : 6
					});
				} else {
					layer.alert(result.msg, {
						icon : 5
					});
				}
			},
			error : function(result) {
				layer.alert("導出模板文件出現異常...", {
					icon : 2
				});
			}
		})
   },
   search: function(table, val) {
	   table.reload({
			url:"jobType/page/key_name/",
			where:{
				name: val //此處的值是傳入後臺查詢的條件
			},
			page:{
				curr: 1 //重新從第 1 頁開始
			}
		})
   },
   remove: function(no, layer, table, index) {
		   $.ajax({ type : "DELETE", url : "jobType/" + no, dataType : "JSON",
			success : function(result) {
				if (result.code == 600) {
					layer.msg("刪除工種【no: " + no + "成功...】", {
						icon : 6
					});
					// 刷新
					table.reload();
				} else {
					layer.msg(result.msg, {
						icon : 5
					});
				}
			},
			error : function(result) {
				console.error("刪除工種【no: " + data.no + "】出現異常...");
			}
		});
   },
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
				layer.alert("獲取組別的所有的區域訊息出現異常!", {
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
					layer.msg("獲取【companyNo: " + companyNo + "】可靠近的參考點出現異常", {
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
					layer.msg("獲取【personNo: " + personNo + "】可靠近的參考點出現異常", {
						icon : 2
					});
				}
			})
		}
	}
}
var companyMaintenanceReq = {
	exportTemplate: function(layer) {// 導出公司模板
		$.ajax({
			type : "GET",
			url : "company/export/",
			dataType : "JSON",
			success : function(result) {
				if (result.code == 600) {
					window.location.href = "company/upload/";
					layer.alert(result.data, {
						icon : 6
					});
				} else {
					layer.alert(result.msg, {
						icon : 5
					});
				}
			},
			error : function(result) {
				layer.alert("導出模板文件出現異常...", {
					icon : 2
				});
			}
		})
	},
	add: function(data, layer, table, index) {
		$.ajax({
			type : "PUT",
			url : "company/",
			dataType : "JSON",
			data : JSON.stringify(data),
			contentType : "application/json;charset=utf-8",
			success : function(result) {
				if (result.code == 600) {
					layer.msg("添加公司資料訊息成功...", {
						icon : 6
					});
					// 重新加載列表
					table.reload();
					// 關閉編輯窗
					layer.close(index);
				} else {
					layer.msg(result.msg, {
						icon : 5
					});
				}
			},
			error : function(result) {
				console.error("添加公司資料訊息出現異常...");
			}
		})
	},
	update: function(no, data, layer, table, index) {
		$.ajax({ type : "POST", url : "company/" + no, dataType : "JSON",
			data : JSON.stringify(data), contentType : "application/json;charset=utf-8",
			success : function(result) {
				if (result.code == 600) {
					layer.msg("公司資料設置成功...", {
						icon : 6
					});
					// 重新加載列表
					table.reload();
					// 關閉編輯窗
					layer.close(index);
				} else {
					layer.msg(result.msg, {
						icon : 5
					});
				}
			},
			error : function(result) {
				console.error("公司資料設置出現異常...");
			}
		})
	},
	search: function(val, table) {
		table.reload({
			url : "company/page/key_name/",
			where : {
				name : val
			},
			page : {
				curr : 1
			}
		})
	},
	remove: function(no, name, layer, table) {
		$.ajax({type : "DELETE", url : "company/" + no, dataType : "JSON",
			success : function(result) {
				if (result.code == 600) {
					layer.msg("刪除公司【name: " + name + "】成功...", {
						icon : 6
					});
					// 重新加載列表
					table.reload();
				} else {
					layer.msg(result.msg, {
						icon : 5
					});
				}
			},
			error : function(result) {
				console.error("刪除公司【name: " + name + "】出現異常...");
			}
		});
	},
	upload: function(userName, layer, upload) {
		// 缓冲弹窗标志
		var waitIndex;
		upload.render({
			elem : '#importCompanyBtn', // 绑定元素
			url : 'company/import/' + userName, // 上传接口
			accept: 'file',
			exts: 'xls|xlsx',
			before : function(obj) {
				waitIndex = layer.msg("正在導入公司資料訊息, 請稍後...", {
					icon : 16
				});
			},
			done : function(res) {// 上传完毕回调
				if(res.code == 600) {
					layer.msg("導入公司記錄成功！",{icon: 6});
				} else {
					layer.alert(res.msg,{icon: 5});
				}
				layer.close(waitIndex);
			},error : function() {// 请求异常回调
				layer.alert("導入公司記錄出現異常...");
			}
		});
	}
}
var jobTypeMaintenanceReq = {
	upload: function(userName, layer, upload) { // 上傳工種訊息
		var waitIndex;
		upload.render({
			elem : '#importJobTypeBtn', // 绑定元素
			url : 'jobType/import/' + userName, // 上传接口
			accept : 'file',
			exts : 'xls|xlsx',
			before : function(obj) {
				waitIndex = layer.msg("正在導入工種資料訊息, 請稍後...", {
					icon : 16
				});
			},
			done : function(res) { // 上传完毕回调
				if (res.code == 600) {
					layer.msg("導入工種記錄成功！", {
						icon : 6
					});
				} else {
					layer.alert(res.msg, {
						icon : 5
					});
				}
				layer.close(waitIndex);
			},
			error : function() {// 请求异常回调
				layer.alert("導入工種記錄出現異常...");
			}
		});
	}
}