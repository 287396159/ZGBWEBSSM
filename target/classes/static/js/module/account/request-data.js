var requestDataObj = {
	role : {
		add : function(table, layer, role) {
			$.ajax({
				type : "PUT",
				url : "role/",
				dataType : "JSON",
				contentType : "application/json;charset=utf-8",
				data : JSON.stringify(role),
				success : function(result) {
					if (result.code == 600) {
						layer.closeAll();
						layer.msg("添加角色成功!",{icon: 6})
						// 重新加載列表
						table.reload();
					} else {
						layer.msg(result.msg, {
							icon : 2
						})
					}
				},
				error : function(result) {
					layer.msg("添加角色訊息【" + JSON.stringify(role) + "】出現異常！", {
						icon : 2
					});
				}
			})
		},
		remove: function(table, layer, name) {
			$.ajax({type: "DELETE", url:"role/delete/" + name, dataType: "JSON", 
				success: function(result){
					if(result.code == 600){
						layer.closeAll();
						layer.msg("刪除角色【name: " + name + "】成功!",{icon: 6})
						// 重新加載列表
						table.reload();
					} else {
						layer.msg(result.msg, {
							icon : 5
						});
					}
				},
				error: function(result){
					layer.msg("刪除角色訊息出現異常！", {
						icon : 2
					});
				}})
		},
		update: function(table, layer, role){
			$.ajax({
				type : "POST",
				url : "role/",
				dataType : "JSON",
				data: JSON.stringify(role),
				contentType : "application/json;charset=utf-8",
				success : function(result) {
					if(result.code == 600){
						layer.closeAll();
						layer.msg("更新角色成功!",{icon: 6});
						// 重新加載列表
						table.reload();
					} else {
						layer.msg(result.msg, {icon: 2});
					}
				},
				error : function(result) {
					layer.msg("更新角色訊息【" + JSON.stringify(role) + "】出現異常！", {
						icon : 2
					});
				}
			})
		},
		all: function(layer, callBack){
			$.ajax({type: "GET", url: "role/all", dataType: "JSON", 
				success: function(result) {
					if(result.code == 600) {
						callBack(result.data);
					} else {
						layer.msg(result.msg, {
							icon : 5
						});
					}
				},
				error: function(result){
					layer.msg("獲取所有的角色類型出現異常!", {
						icon : 2
					});
				}})
		}
	},
	account: {
		add: function(layer, table, account, callBack){
			$.ajax({type: "PUT", url:"account/", dataType: "JSON", 
					contentType:"application/json;charset=utf-8",
					data: JSON.stringify(account),
					success: function(result) {
						if(result.code == 600) {
							layer.closeAll();
							layer.msg("添加賬戶成功! ",{icon: 6});
							table.reload();
						} else {
							layer.msg(result.msg, {icon: 5});
						}
					},
					error: function(result){
						layer.msg("添加賬戶訊息【" + JSON.stringify(account) + "】出現異常！", {
							icon : 2
						})
					}})
		},
		remove: function(table, layer, name){
			$.ajax({type: "DELETE", url: "account/"+name, dataType: "JSON", 
			success: function(result) {
				if(result.code == 600) {
					layer.closeAll();
					layer.msg("刪除賬戶【name: " + name + "】成功!",{icon: 6});
					// 重新加載列表
					table.reload();
				} else {
					layer.msg(result.msg, {
							icon : 5
					});
				}
			},
			error: function(result) {
				layer.msg("刪除賬戶【name : " + name + "】出現異常！", {
					icon : 2
				});
			}})
		},
		update: function(table, layer, account){
			$.ajax({type: "POST", url: "account/", dataType: "JSON", data: JSON.stringify(account),
				contentType:"application/json;charset=utf-8",
				success: function(result){
					if(result.code == 600){
						layer.closeAll();
						// 重新加載列表
						table.reload();
					} else {
						layer.msg(result.msg, {
							icon : 5
						});
					}
				},
				error: function(result){
					layer.msg("更新賬戶訊息【" + JSON.stringify(account) + "】出現異常！", {
						icon : 2
					});
				}})
		},
		kickOut: function(layer, userName, callBack){
			$.ajax({url: "account/kickOut/"+userName, type: "GET", dataType: "JSON",
				success: function(result){
					if(result.code == 600) {
						callBack();
						layer.msg("踢出用戶【userName: " + userName + "】成功!",{icon: 6});
					} else {
						layer.msg(result.msg, {
							icon : 5
						});
					}
				},
				error: function(result){
					layer.msg("踢出用戶【userName: " + userName +  "】出現異常！",{icon: 2});
				}})
		}
	},
	permission: {
		addPermissions: function(rolePermissions, layer){ 
			// 添加角色的权限
			$.ajax({url: "role_permission/", type: "PUT", dataType: "JSON",
					contentType: "application/json;charset=utf-8",
					data: JSON.stringify(rolePermissions),
					success: function(result){
						if(result.code == 600){
							layer.msg("修改角色权限成功!",{icon: 6})
						} else {
							layer.msg(result.msg, {
								icon : 5
							});
						}
					},
					error: function(result){
						layer.msg("添加角色【 " + JSON.stringify(rolePermissions) + "】的权限出现异常!", {
							icon : 2
						});
					}})
		},
		role_Permissions: function(roleName, layer, callBack){
			$.ajax({url : "role_permission/roleName/" + roleName, type : "GET", dataType : "JSON",
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
					layer.msg("获取角色【roleName: " + roleName + "】所有的權限資料出現異常...",
							{ icon : 2 });
				}
			});
		},
		all: function(layer, callBack){
			$.ajax({url: "permission/", type: "GET", dataType: "JSON",
				success: function(result){
					if (result.code == 600) {
						callBack(result.data);
					} else {
						layer.msg(result.msg, {
							icon : 5
						});
					}
				},
				error: function(result){
					layer.msg("获取所有的權限資料出現異常...", {
						icon : 2
					})
				}})
		},
		reload: function(layer, callBack, errorCallBack) {
			$.ajax({
				url : "permission/load/",
				type : "GET",
				dataType : "JSON",
				success : function(result) {
					if (result.code == 600) {
						callBack(result.data);
					} else {
						layer.msg(result.msg, {
							icon : 5
						});
					}
					if(errorCallBack){
						errorCallBack();
					}
				},
				error : function(result) {
					if(errorCallBack){
						errorCallBack();
					}
					layer.msg("加載權限資料出現異常...", {
						icon : 2
					})
				}
			})
		}
	}
}